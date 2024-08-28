package com.methodsignature.healthyrecipes.service.api

import kotlinx.coroutines.flow.Flow

/**
 * Persists app configuration information such as the latest state of key values.
 */
interface ConfigurationService {

    /**
     * call when the [RecipeService] has been seeded on app's first run.
     */
    suspend fun setRecipeServiceAsSeeded()

    /**
     * @return true when [setRecipeServiceAsSeeded] has been called.
     */
    suspend fun observeIsRecipeServiceSeeded() : Flow<Boolean>
}