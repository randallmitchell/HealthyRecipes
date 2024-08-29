package com.methodsignature.healthyrecipes.usecase

import com.methodsignature.healthyrecipes.BaseTest
import com.methodsignature.healthyrecipes.service.api.ConfigurationService
import com.methodsignature.healthyrecipes.service.api.HardCodedSeedDataService
import com.methodsignature.healthyrecipes.service.api.RecipeService
import com.methodsignature.healthyrecipes.value.NonBlankString
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class SeedRecipeListUseCaseTest : BaseTest() {

    private val recipeService = mockk<RecipeService>(relaxed = true)
    private val configurationService = mockk<ConfigurationService>(relaxed = true)
    private val hardCodedSeedDataService = mockk<HardCodedSeedDataService>(relaxed = true)

    private val tested = SeedRecipeListUseCase(
        recipeService = recipeService,
        configurationService = configurationService,
        hardCodedSeedDataService = hardCodedSeedDataService,
    )

    private object TestData {
        val ingredient = RecipeService.Ingredient(
            units = NonBlankString.from("1 1/2")!!,
            unitType = NonBlankString.from("cups")!!,
            name = NonBlankString.from("magic")!!,
        )
        val ingredients = listOf(ingredient)

        val recipe = RecipeService.Recipe(
            id = NonBlankString.from("1")!!,
            description = NonBlankString.from("description")!!,
            servings = null,
            instructions = null,
            ingredients = ingredients
        )
        val recipes = listOf(recipe)
    }

    @Test
    fun `GIVEN the recipe list is not yet seeded THEN seeds the recipes`() = runTest {
        coEvery { configurationService.observeIsRecipeServiceSeeded() } returns flow { emit(false) }
        coEvery { configurationService.setRecipeServiceAsSeeded() } returns Unit
        coEvery { hardCodedSeedDataService.getInitialRecipeList() } returns TestData.recipes
        coEvery { recipeService.saveRecipes(any()) } returns Unit

        val tested = SeedRecipeListUseCase(
            recipeService = recipeService,
            configurationService = configurationService,
            hardCodedSeedDataService = hardCodedSeedDataService,
        )

        tested.run()

        coVerify(exactly = 1) {
            recipeService.saveRecipes(TestData.recipes)
        }
    }

    @Test
    fun `WHEN the recipe list is seeded THEN marks as seeded`() = runTest {
        coEvery { configurationService.observeIsRecipeServiceSeeded() } returns flow { emit(false) }
        coEvery { configurationService.setRecipeServiceAsSeeded() } returns Unit
        coEvery { hardCodedSeedDataService.getInitialRecipeList() } returns listOf()
        coEvery { recipeService.saveRecipes(any()) } returns Unit

        tested.run()

        coVerify(exactly = 1) {
            configurationService.setRecipeServiceAsSeeded()
        }
    }

    @Test
    fun `GIVEN the recipes are already seeded THEN does not seed the recipes`() = runTest {
        coEvery { configurationService.observeIsRecipeServiceSeeded() } returns flow { emit(true) }
        coEvery { recipeService.saveRecipes(any()) }

        tested.run()

        coVerify(exactly = 0) {
            recipeService.saveRecipes(any())
        }
    }
}