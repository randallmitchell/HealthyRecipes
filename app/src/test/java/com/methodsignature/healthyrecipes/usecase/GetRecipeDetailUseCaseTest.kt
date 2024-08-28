package com.methodsignature.healthyrecipes.usecase

import com.methodsignature.healthyrecipes.BaseTest
import com.methodsignature.healthyrecipes.service.api.RecipeService
import com.methodsignature.healthyrecipes.service.errors.EntityNotFoundException
import com.methodsignature.healthyrecipes.value.EntityId
import com.methodsignature.healthyrecipes.value.NonBlankString
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class GetRecipeDetailUseCaseTest : BaseTest() {

    private val recipeService = mockk<RecipeService>()
    private val tested = GetRecipeDetailUseCase(recipeService)

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
    }

    @Test
    fun onEntityNotFound_raise() = runTest {
        // GIVEN the recipe service returns entity not found exception
        coEvery { recipeService.observeRecipe(any()) } throws EntityNotFoundException("Entity not found.")

        // THEN raise that exception
        try {
            tested.run(EntityId.from("123")!!)
        } catch (exception: Exception) {
            exception::class shouldBeEqualTo EntityNotFoundException::class
        }
    }

    @Test
    fun onRecipe_returnRecipe() = runTest {
        // GIVEN a recipe is returned
        coEvery { recipeService.observeRecipe(TestData.recipe.id) } returns TestData.recipe

        // THEN map and return that recipe
        val result = tested.run(TestData.recipe.id)
        TestData.recipe.let {
            result shouldBeEqualTo GetRecipeDetailUseCase.Recipe(
                id = it.id,
                description = it.description,
                servings = it.servings,
                instructions = it.instructions,
                ingredients = listOf(
                    GetRecipeDetailUseCase.Ingredient(
                        units = TestData.ingredient.units,
                        unitType = TestData.ingredient.unitType,
                        name = TestData.ingredient.name,
                    )
                ),
            )
        }
    }
}