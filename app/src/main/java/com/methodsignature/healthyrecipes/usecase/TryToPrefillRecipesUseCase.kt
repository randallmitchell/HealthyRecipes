package com.methodsignature.healthyrecipes.usecase

import com.methodsignature.healthyrecipes.service.api.ConfigurationService
import com.methodsignature.healthyrecipes.service.api.HardCodedSeedDataService
import com.methodsignature.healthyrecipes.service.api.RecipeService
import javax.inject.Inject

class TryToPrefillRecipesUseCase @Inject constructor(
    private val recipeService: RecipeService,
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