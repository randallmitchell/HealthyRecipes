package com.methodsignature.healthyrecipes.usecase

import com.methodsignature.healthyrecipes.service.api.ConfigurationService
import com.methodsignature.healthyrecipes.service.api.HardCodedSeedDataService
import com.methodsignature.healthyrecipes.service.api.LocalRecipeService
import javax.inject.Inject

class SeedRecipeListUseCase @Inject constructor(
    private val recipeService: LocalRecipeService,
    private val configurationService: ConfigurationService,
    private val hardCodedSeedDataService: HardCodedSeedDataService,
) {
    suspend fun run() {
        configurationService.observeIsRecipeServiceSeeded().collect { isAlreadySeeded ->
            if (!isAlreadySeeded) {
                val seedData = hardCodedSeedDataService.getInitialRecipeList()
                recipeService.saveRecipes(seedData)
                configurationService.setRecipeServiceAsSeeded()
            }
        }
    }
}