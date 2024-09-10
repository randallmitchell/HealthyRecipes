package com.methodsignature.healthyrecipes.service.api

import com.methodsignature.healthyrecipes.value.EntityId
import com.methodsignature.healthyrecipes.value.NonBlankString
import kotlinx.coroutines.flow.Flow

data class Recipe(
    val id: EntityId,
    val description: NonBlankString,
    val servings: NonBlankString? = null,
    val instructions: NonBlankString? = null,
    val ingredients: List<Ingredient>,
)

data class Ingredient(
    val units: NonBlankString,
    val unitType: NonBlankString,
    val name: NonBlankString,
)

interface LocalRecipeService {

    /**
     * Fetches all locally saved recipes.
     */
    suspend fun observeAllRecipes(): Flow<List<Recipe>>

    /**
     * Fetches a locally saved recipe.
     *
     * @return null if recipe is not available.
     */
    suspend fun observeRecipe(id: EntityId): Flow<Recipe>

    /**
     * Saves a recipe locally.
     */
    suspend fun saveRecipe(recipe: Recipe)

    /**
     * Saves a list of recipes locally.
     */
    suspend fun saveRecipes(withRecipes: List<Recipe>)
}

interface RemoteRecipeService {

    suspend fun getAllRecipes(): List<Recipe>

    suspend fun getRecipe(id: EntityId): Recipe
}