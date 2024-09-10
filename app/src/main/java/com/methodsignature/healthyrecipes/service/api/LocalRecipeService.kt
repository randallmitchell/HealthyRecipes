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
     * Saves a recipe locally. Updates the recipe if it already exists.
     */
    suspend fun upsertRecipe(recipe: Recipe)

    /**
     * Saves a list of recipes locally. Updates any recipe that already exists.
     */
    suspend fun upsertRecipes(withRecipes: List<Recipe>)

    /**
     * Empties the list of recipes.
     */
    suspend fun deleteAllRecipes()
}

interface RemoteRecipeService {

    suspend fun getAllRecipes(): List<Recipe>

    suspend fun getRecipe(id: EntityId): Recipe
}