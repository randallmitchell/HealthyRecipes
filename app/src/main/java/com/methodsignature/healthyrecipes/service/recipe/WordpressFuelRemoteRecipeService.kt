package com.methodsignature.healthyrecipes.service.recipe

import com.methodsignature.healthyrecipes.service.api.Ingredient
import com.methodsignature.healthyrecipes.service.api.Recipe
import com.methodsignature.healthyrecipes.service.api.RemoteRecipeService
import com.methodsignature.healthyrecipes.service.errors.EntityNotFoundException
import com.methodsignature.healthyrecipes.service.errors.MalformedDataException
import com.methodsignature.healthyrecipes.service.recipe.WordpressFuelRemoteRecipeService.RecipeResponseItem.Companion.toRecipe
import com.methodsignature.healthyrecipes.service.recipe.WordpressFuelRemoteRecipeService.acf.Companion.toRecipe
import com.methodsignature.healthyrecipes.service.recipe.WordpressFuelRemoteRecipeService.ingredients.Companion.toIngredient
import com.methodsignature.healthyrecipes.value.EntityId
import com.methodsignature.healthyrecipes.value.NonBlankString
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import fuel.HttpLoader
import javax.inject.Inject

@OptIn(ExperimentalStdlibApi::class)
class WordpressFuelRemoteRecipeService @Inject constructor(
    val fuel: HttpLoader,
    val moshi: Moshi,
    baseServiceUrl: NonBlankString,
) : RemoteRecipeService {

    private val recipesEndpoint = "${baseServiceUrl.value}/recipes"

    @JsonClass(generateAdapter = true)
    data class RecipeResponseItem(
        val id: Int,
        val acf: acf
    ) {
        companion object {
            fun RecipeResponseItem.toRecipe(): Recipe {
                val recipeId = EntityId.from(id.toString())
                    ?: throw MalformedDataException("Unable to parse id from recipe.")
                return acf.toRecipe(recipeId)
            }
        }
    }

    @JsonClass(generateAdapter = true)
    data class acf(
        val name: String,
        val description: String,
        val servings: String,
        val instructions: String,
        val ingredients: List<ingredients>
    ) {
        companion object {
            fun acf.toRecipe(recipeId: EntityId): Recipe {
                return Recipe(
                    id = recipeId,
                    description = NonBlankString.from(description)
                        ?: throw MalformedDataException("Unable to parse description from recipe ${recipeId.value}"),
                    servings = NonBlankString.from(servings),
                    instructions = NonBlankString.from(instructions),
                    ingredients = ingredients.map { it.toIngredient(recipeId) },
                )
            }
        }
    }

    @JsonClass(generateAdapter = true)
    data class ingredients(
        val units: String,
        val unitType: String,
        val name: String,
    ) {
        companion object {
            fun ingredients.toIngredient(recipeId: EntityId): Ingredient {
                return Ingredient(
                    units = NonBlankString.from(units)
                        ?: throw MalformedDataException("Unable to parse ingredient units for recipe ${recipeId.value}."),
                    unitType = NonBlankString.from(unitType)
                        ?: throw MalformedDataException("Unable to parse ingredient unitType for recipe ${recipeId.value}."),
                    name = NonBlankString.from(name)
                        ?: throw MalformedDataException("Unable to parse ingredient name for recipe ${recipeId.value}."),
                )
            }
        }
    }

    override suspend fun getAllRecipes(): List<Recipe> {
        val raw: String = fuel.get(request = { url = recipesEndpoint }).body.string()
        val data = moshi.adapter<List<RecipeResponseItem>>().fromJson(raw)
            ?: throw EntityNotFoundException("Unable to get recipes from endpoint.")
        return data.map { it.toRecipe() }
    }

    override suspend fun getRecipe(id: EntityId): Recipe {
        val raw: String = fuel.get(request = { url = "recipesEndpoint/${id.value}" }).body.string()
        val data = moshi.adapter<RecipeResponseItem>().fromJson(raw)
            ?: throw MalformedDataException("Unable to parse recipe with id ${id.value}.")
        return data.toRecipe()
    }
}