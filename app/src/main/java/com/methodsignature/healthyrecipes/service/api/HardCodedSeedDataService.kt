package com.methodsignature.healthyrecipes.service.api

/**
 * Provides seed data for local services. The seed data comes from source code and other resources
 * hard-coded into the application binary.
 */
interface HardCodedSeedDataService {

    /**
     * Provides an initial list or recipes for which
     */
    suspend fun getInitialRecipeList(): List<Recipe>
}