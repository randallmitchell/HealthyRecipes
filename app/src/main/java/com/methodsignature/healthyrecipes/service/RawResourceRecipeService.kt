package com.methodsignature.healthyrecipes.service

import android.content.Context
import com.methodsignature.healthyrecipes.service.api.RecipeService
import com.methodsignature.healthyrecipes.service.api.RecipeService.Recipe
import com.methodsignature.healthyrecipes.service.errors.EntityNotFoundException
import com.methodsignature.healthyrecipes.value.EntityId
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RawResourceRecipeService @Inject constructor(
    private val context: Context,
    private val recipesResId: Int,
    private val moshi: Moshi,
) : RecipeService {

    private var recipes: List<Recipe>? = null

    @JsonClass(generateAdapter = true)
    data class Recipes(
        @field:Json(name = "recipes") val recipes: List<Recipe>,
    )

    override suspend fun getRecipes(): List<Recipe> = withContext(Dispatchers.IO) {
        fetchRecipesIfNeededAndReturn()
    }

    override suspend fun getRecipe(id: EntityId): Recipe {
        val recipes = fetchRecipesIfNeededAndReturn()
        val recipe = recipes.firstOrNull() { recipe ->
            recipe.id.value == id.value
        }
        if (recipe == null) {
            throw EntityNotFoundException("Recipe with ID ${id.value} not found.")
        }
        return recipe
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun fetchRecipesIfNeededAndReturn(): List<Recipe> {
        val recipes = this@RawResourceRecipeService.recipes
        return if (recipes == null) {
            val rawJson = context.resources.openRawResource(recipesResId).reader().readText()
            val adapter = moshi.adapter<Recipes>()
            val fetched = adapter.fromJson(rawJson)?.recipes ?: listOf()
            this@RawResourceRecipeService.recipes = fetched
            fetched
        } else {
            recipes
        }
    }
}