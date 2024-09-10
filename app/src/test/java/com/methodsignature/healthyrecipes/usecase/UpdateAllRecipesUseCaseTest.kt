package com.methodsignature.healthyrecipes.usecase

import com.methodsignature.healthyrecipes.BaseTest
import com.methodsignature.healthyrecipes.service.api.ConfigurationService
import com.methodsignature.healthyrecipes.service.api.ConfigurationService.RecipeSeedState
import com.methodsignature.healthyrecipes.service.api.recipe.HardCodedSeedDataService
import com.methodsignature.healthyrecipes.service.api.recipe.model.Ingredient
import com.methodsignature.healthyrecipes.service.api.recipe.LocalRecipeService
import com.methodsignature.healthyrecipes.service.api.recipe.model.Recipe
import com.methodsignature.healthyrecipes.service.api.recipe.RemoteRecipeService
import com.methodsignature.healthyrecipes.value.NonBlankString
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class UpdateAllRecipesUseCaseTest : BaseTest() {

    private val recipeService = mockk<LocalRecipeService>()
    private val configurationService = mockk<ConfigurationService>()
    private val hardCodedSeedDataService = mockk<HardCodedSeedDataService>()
    private val remoteRecipeService = mockk<RemoteRecipeService>()

    private val tested = UpdateAllRecipesUseCase(
        localRecipeService = recipeService,
        configurationService = configurationService,
        hardCodedSeedDataService = hardCodedSeedDataService,
        remoteRecipeService = remoteRecipeService,
    )

    private object TestData {
        val ingredient = Ingredient(
            units = NonBlankString.from("1 1/2")!!,
            unitType = NonBlankString.from("cups")!!,
            name = NonBlankString.from("magic")!!,
        )
        val ingredients = listOf(ingredient)

        val recipe = Recipe(
            id = NonBlankString.from("1")!!,
            name = NonBlankString.from("name")!!,
            description = NonBlankString.from("description")!!,
            servings = null,
            instructions = null,
            ingredients = ingredients
        )
        val recipes = listOf(recipe)
    }

    @Test
    fun `GIVEN not seeded THEN seed from remote`() = runTest {
        val recipes = TestData.recipes

        coEvery { configurationService.getRecipeSeedState() } returns flow { emit(RecipeSeedState.NOT_SEEDED) }
        coEvery { configurationService.setRecipeSeedState(any()) } returns Unit
        coEvery { remoteRecipeService.getAllRecipes() } returns TestData.recipes
        coEvery { recipeService.deleteAllRecipes() } returns Unit
        coEvery { recipeService.upsertRecipes(any()) } returns Unit

        tested.run()

        coVerify(exactly = 1) {
            recipeService.upsertRecipes(recipes)
        }
        coVerify {
            configurationService.setRecipeSeedState(RecipeSeedState.SEED_PROCESSES_COMPLETE)
        }
    }

    @Test
    fun `GIVEN not seeded and remote error THEN seed from hard coded`() = runTest {
        val recipes = TestData.recipes

        coEvery { configurationService.getRecipeSeedState() } returns flow { emit(RecipeSeedState.NOT_SEEDED) }
        coEvery { configurationService.setRecipeSeedState(any()) } returns Unit
        coEvery { remoteRecipeService.getAllRecipes() } throws Exception()
        coEvery { hardCodedSeedDataService.getInitialRecipeList() } returns recipes
        coEvery { recipeService.upsertRecipes(any()) } returns Unit

        tested.run()

        coVerify {
            recipeService.upsertRecipes(recipes)
        }
        coVerify {
            configurationService.setRecipeSeedState(RecipeSeedState.HARD_CODED)
        }
    }

    @Test
    fun `GIVEN hard coded THEN seed from remote`() = runTest {
        val recipes = TestData.recipes

        coEvery { configurationService.getRecipeSeedState() } returns flow { emit(RecipeSeedState.HARD_CODED) }
        coEvery { configurationService.setRecipeSeedState(any()) } returns Unit
        coEvery { remoteRecipeService.getAllRecipes() } returns recipes
        coEvery { recipeService.deleteAllRecipes() } returns Unit
        coEvery { recipeService.upsertRecipes(any()) } returns Unit

        tested.run()

        coVerify {
            recipeService.upsertRecipes(recipes)
        }
        coVerify {
            configurationService.setRecipeSeedState(RecipeSeedState.SEED_PROCESSES_COMPLETE)
        }
    }

    @Test
    fun `GIVEN hard coded and remote fails THEN do nothing`() = runTest {
        coEvery { configurationService.getRecipeSeedState() } returns flow { emit(RecipeSeedState.HARD_CODED) }
        coEvery { remoteRecipeService.getAllRecipes() } throws Exception()

        tested.run()

        coVerify(exactly = 0) {
            recipeService.deleteAllRecipes()
        }
        coVerify(exactly = 0) {
            recipeService.upsertRecipes(any())
        }
        coVerify(exactly = 0) {
            configurationService.setRecipeSeedState(any())
        }
    }

    @Test
    fun `GIVEN seeding complete THEN updates recipes`() = runTest {
        val recipes = TestData.recipes

        coEvery { configurationService.getRecipeSeedState() } returns flow { emit(RecipeSeedState.SEED_PROCESSES_COMPLETE) }
        coEvery { configurationService.setRecipeSeedState(any()) } returns Unit
        coEvery { remoteRecipeService.getAllRecipes() } returns recipes
        coEvery { recipeService.upsertRecipes(any()) } returns Unit

        tested.run()

        coVerify(exactly = 1) {
            recipeService.upsertRecipes(recipes)
        }
    }
}