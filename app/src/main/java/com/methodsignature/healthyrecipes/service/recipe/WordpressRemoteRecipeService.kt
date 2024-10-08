package com.methodsignature.healthyrecipes.service.recipe

import com.methodsignature.healthyrecipes.language.value.EntityId
import com.methodsignature.healthyrecipes.language.value.NonBlankString
import com.methodsignature.healthyrecipes.service._api.errors.EntityNotFoundException
import com.methodsignature.healthyrecipes.service._api.errors.MalformedDataException
import com.methodsignature.healthyrecipes.service._api.recipe.RemoteRecipeService
import com.methodsignature.healthyrecipes.service._api.recipe.model.Ingredient
import com.methodsignature.healthyrecipes.service._api.recipe.model.Recipe
import com.methodsignature.healthyrecipes.service.recipe.WordpressRemoteRecipeService.Acf.Companion.toRecipe
import com.methodsignature.healthyrecipes.service.recipe.WordpressRemoteRecipeService.Ingredient.Companion.toIngredient
import com.methodsignature.healthyrecipes.service.recipe.WordpressRemoteRecipeService.RecipeResponseItem.Companion.toRecipe
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import fuel.HttpLoader
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalStdlibApi::class)
class WordpressRemoteRecipeService @Inject constructor(
    private val fuel: HttpLoader,
    private val moshi: Moshi,
    baseServiceUrl: NonBlankString,
    private val ioDispatcher: CoroutineDispatcher,
) : RemoteRecipeService {

    private val recipesEndpoint = "${baseServiceUrl.value}/recipe"

    override suspend fun getAllRecipes(): List<Recipe> = withContext(ioDispatcher) {
        val raw: String = fuel.get(request = { url = recipesEndpoint }).body.string()
        val data = moshi.adapter<List<RecipeResponseItem>>().fromJson(raw)
            ?: throw EntityNotFoundException("Unable to get recipes from endpoint.")
        data.map { it.toRecipe() }
    }

    override suspend fun getRecipe(id: EntityId): Recipe = withContext(ioDispatcher) {
        val raw: String = fuel.get(request = { url = "recipesEndpoint/${id.value}" }).body.string()
        val data = moshi.adapter<RecipeResponseItem>().fromJson(raw)
            ?: throw MalformedDataException("Unable to parse recipe with id ${id.value}.")
        data.toRecipe()
    }

    @JsonClass(generateAdapter = true)
    data class RecipeResponseItem(
        val id: Int,
        @field:Json(name = "acf") val acf: Acf
    ) {
        companion object {
            fun RecipeResponseItem.toRecipe(): Recipe {
                val recipeId = EntityId.from(id.toString())
                    ?: throw MalformedDataException("Unable to parse `id` from recipe.")
                return acf.toRecipe(recipeId)
            }
        }
    }

    @JsonClass(generateAdapter = true)
    data class Acf(
        val name: String,
        val description: String,
        val servings: String,
        val instructions: String,
        @field:Json(name = "ingredients") val ingredients: List<Ingredient>
    ) {
        companion object {
            fun Acf.toRecipe(recipeId: EntityId): Recipe {
                return Recipe(
                    id = recipeId,
                    name = NonBlankString.from(name)
                        ?: throw MalformedDataException("Unable to `parse` name from recipe ${recipeId.value}"),
                    description = NonBlankString.from(description)
                        ?: throw MalformedDataException("Unable to parse `description` from recipe ${recipeId.value}"),
                    servings = NonBlankString.from(servings),
                    instructions = NonBlankString.from(instructions),
                    ingredients = ingredients.map { it.toIngredient(recipeId) },
                )
            }
        }
    }

    @JsonClass(generateAdapter = true)
    data class Ingredient(
        val units: String,
        val unitType: String,
        val name: String,
    ) {
        companion object {
            fun Ingredient.toIngredient(recipeId: EntityId): com.methodsignature.healthyrecipes.service._api.recipe.model.Ingredient {
                return Ingredient(
                    units = NonBlankString.from(units)
                        ?: throw MalformedDataException("Unable to parse ingredient `units` for recipe ${recipeId.value}."),
                    unitType = NonBlankString.from(unitType)
                        ?: throw MalformedDataException("Unable to parse ingredient `unitType` for recipe ${recipeId.value}."),
                    name = NonBlankString.from(name)
                        ?: throw MalformedDataException("Unable to parse ingredient `name` for recipe ${recipeId.value}."),
                )
            }
        }
    }
}