package com.methodsignature.healthyrecipes.service._api.recipe

import com.methodsignature.healthyrecipes.service._api.recipe.model.Recipe
import com.methodsignature.healthyrecipes.language.value.EntityId
import kotlinx.coroutines.flow.Flow

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