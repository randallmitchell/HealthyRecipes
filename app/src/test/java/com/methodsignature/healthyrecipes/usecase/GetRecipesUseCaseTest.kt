package com.methodsignature.healthyrecipes.usecase

import com.methodsignature.healthyrecipes.service.api.RecipeService
import com.methodsignature.healthyrecipes.value.NonEmptyString
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.`should be equal to`
import org.junit.Test

class GetRecipesUseCaseTest {

    private val recipeService = mockk<RecipeService>()
    private val tested = GetRecipesUseCase(recipeService)

    private object TestData {
        val ingredient = RecipeService.Ingredient(
            units = NonEmptyString.from("1 1/2")!!,
            unitType = NonEmptyString.from("cups")!!,
            name = NonEmptyString.from("magic")!!,
        )
        val ingredients = listOf(ingredient)

        val recipe = RecipeService.Recipe(
            id = NonEmptyString.from("1")!!,
            description = NonEmptyString.from("description")!!,
            servings = "",
            instructions = "",
            ingredients = ingredients
        )
        val recipes = listOf(recipe)
    }

    @Test
    fun `GIVEN recipe service is empty THEN returns an empty list`() = runTest {
        coEvery { recipeService.getRecipes() } returns listOf()
        val results = tested.run()
        results.size `should be equal to` 0
    }

    @Test
    fun `GIVEN recipes are available THEN they are returned`() = runTest {
        coEvery { recipeService.getRecipes() } returns TestData.recipes
        val results = tested.run()
        results.size `should be equal to` 1
    }
}
