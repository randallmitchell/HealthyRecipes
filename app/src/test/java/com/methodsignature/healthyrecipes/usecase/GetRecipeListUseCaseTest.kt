package com.methodsignature.healthyrecipes.usecase

import com.methodsignature.healthyrecipes.service.api.RecipeService
import com.methodsignature.healthyrecipes.value.NonBlankString
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import kotlin.test.Test

class GetRecipeListUseCaseTest {

    private val recipeService = mockk<RecipeService>()
    private val tested = GetRecipeListUseCase(recipeService)

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
        results.size shouldBeEqualTo 0
    }

    @Test
    fun `GIVEN recipes are available THEN they are returned`() = runTest {
        coEvery { recipeService.getRecipes() } returns TestData.recipes
        val results = tested.run()
        results.size shouldBeEqualTo 1
    }
}
