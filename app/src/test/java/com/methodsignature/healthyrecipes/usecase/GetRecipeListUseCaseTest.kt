package com.methodsignature.healthyrecipes.usecase

import app.cash.turbine.test
import com.methodsignature.healthyrecipes.BaseTest
import com.methodsignature.healthyrecipes.service.api.RecipeService
import com.methodsignature.healthyrecipes.value.NonBlankString
import io.mockk.coEvery
import io.mockk.coJustAwait
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import kotlin.test.Test

class GetRecipeListUseCaseTest : BaseTest() {

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
            servings = null,
            instructions = null,
            ingredients = ingredients
        )
        val recipes = listOf(recipe)
    }

    @Test
    fun `GIVEN recipe service is empty THEN returns an empty list`() = runTest {
        coEvery { recipeService.observeAllRecipes() } returns flow { emit(listOf()) }
        tested.observe().test {
            awaitItem() shouldBeEqualTo listOf()
            awaitComplete()
        }

    }

    @Test
    fun `GIVEN recipes are available THEN they are returned`() = runTest {
        coEvery { recipeService.observeAllRecipes() } returns flow { emit(TestData.recipes) }
        tested.observe().test {
            awaitItem().size shouldBeEqualTo 1
            awaitComplete()
        }
    }
}
