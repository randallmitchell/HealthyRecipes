package com.methodsignature.healthyrecipes.usecase

import app.cash.turbine.test
import com.methodsignature.healthyrecipes.BaseTest
import com.methodsignature.healthyrecipes.service.api.recipe.model.Ingredient
import com.methodsignature.healthyrecipes.service.api.recipe.model.Recipe
import com.methodsignature.healthyrecipes.service.api.recipe.LocalRecipeService
import com.methodsignature.healthyrecipes.service.errors.EntityNotFoundException
import com.methodsignature.healthyrecipes.value.EntityId
import com.methodsignature.healthyrecipes.value.NonBlankString
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class GetRecipeDetailUseCaseTest : BaseTest() {

    private val recipeService = mockk<LocalRecipeService>()
    private val tested = GetRecipeDetailUseCase(recipeService)

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
    }

    @Test
    fun onEntityNotFound_raise() = runTest {
        // GIVEN the recipe service returns entity not found exception
        coEvery { recipeService.observeRecipe(any()) } throws EntityNotFoundException("Entity not found.")

        // THEN raise that exception
        try {
            tested.observe(EntityId.from("123")!!)
        } catch (exception: Exception) {
            exception::class shouldBeEqualTo EntityNotFoundException::class
        }
    }

    @Test
    fun onRecipe_returnRecipe() = runTest {
        // GIVEN a recipe is returned
        coEvery { recipeService.observeRecipe(TestData.recipe.id) } returns flow { emit(TestData.recipe) }

        // THEN map and return that recipe
        val expected = TestData.recipe.run {
            GetRecipeDetailUseCase.Recipe(
                id = id,
                name = name,
                description = description,
                servings = servings,
                instructions = instructions,
                ingredients = listOf(
                    GetRecipeDetailUseCase.Ingredient(
                        units = TestData.ingredient.units,
                        unitType = TestData.ingredient.unitType,
                        name = TestData.ingredient.name,
                    )
                ),
            )
        }
        tested.observe(TestData.recipe.id).test {
            awaitItem() shouldBeEqualTo expected
            awaitComplete()
        }
    }
}