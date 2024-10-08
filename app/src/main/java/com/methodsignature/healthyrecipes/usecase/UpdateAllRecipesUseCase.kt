package com.methodsignature.healthyrecipes.usecase

import com.methodsignature.healthyrecipes.language.DoNothing
import com.methodsignature.healthyrecipes.service._api.configuration.ConfigurationService
import com.methodsignature.healthyrecipes.service._api.configuration.ConfigurationService.RecipeSeedState
import com.methodsignature.healthyrecipes.service._api.recipe.HardCodedSeedDataService
import com.methodsignature.healthyrecipes.service._api.recipe.LocalRecipeService
import com.methodsignature.healthyrecipes.service._api.recipe.RemoteRecipeService
import kotlinx.coroutines.CancellationException
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
                        if (t is CancellationException) {
                            throw t
                        }
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
                        if (t is CancellationException) {
                            throw t
                        }
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