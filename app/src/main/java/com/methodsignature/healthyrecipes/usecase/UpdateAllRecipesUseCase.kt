package com.methodsignature.healthyrecipes.usecase

import com.methodsignature.healthyrecipes.language.DoNothing
import com.methodsignature.healthyrecipes.service.api.ConfigurationService
import com.methodsignature.healthyrecipes.service.api.ConfigurationService.RecipeSeedState
import com.methodsignature.healthyrecipes.service.api.HardCodedSeedDataService
import com.methodsignature.healthyrecipes.service.api.LocalRecipeService
import com.methodsignature.healthyrecipes.service.api.RemoteRecipeService
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class UpdateAllRecipesUseCase @Inject constructor(
    private val localRecipeService: LocalRecipeService,
    private val configurationService: ConfigurationService,
    private val hardCodedSeedDataService: HardCodedSeedDataService,
    private val remoteRecipeService: RemoteRecipeService,
) {
    suspend fun run() {
        configurationService.getRecipeSeedState().take(1).collect { seedState ->
            when (seedState) {
                RecipeSeedState.NOT_SEEDED -> {
                    try {
                        val recipes = remoteRecipeService.getAllRecipes()
                        localRecipeService.upsertRecipes(recipes)
                        configurationService.setRecipeSeedState(RecipeSeedState.SEED_PROCESSES_COMPLETE)
                    } catch (t: Throwable) {
                        val recipes = hardCodedSeedDataService.getInitialRecipeList()
                        localRecipeService.upsertRecipes(recipes)
                        configurationService.setRecipeSeedState(RecipeSeedState.HARD_CODED)
                    }
                }
                RecipeSeedState.HARD_CODED -> {
                    try {
                        val recipes = remoteRecipeService.getAllRecipes()
                        localRecipeService.deleteAllRecipes()
                        localRecipeService.upsertRecipes(recipes)
                        configurationService.setRecipeSeedState(RecipeSeedState.SEED_PROCESSES_COMPLETE)
                    } catch (t: Throwable) {
                        DoNothing("Do not seed again.")
                    }
                }
                RecipeSeedState.SEED_PROCESSES_COMPLETE -> {
                    val recipes = remoteRecipeService.getAllRecipes()
                    localRecipeService.upsertRecipes(recipes)
                }
            }
        }
    }
}