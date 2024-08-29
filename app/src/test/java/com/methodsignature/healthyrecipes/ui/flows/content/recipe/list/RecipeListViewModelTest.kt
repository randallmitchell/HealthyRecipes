package com.methodsignature.healthyrecipes.ui.flows.content.recipe.list

import app.cash.turbine.test
import com.methodsignature.healthyrecipes.BaseTest
import com.methodsignature.healthyrecipes.ui.flows.content.recipe.list.RecipeListViewModel.UiState
import com.methodsignature.healthyrecipes.usecase.GetRecipeListUseCase
import com.methodsignature.healthyrecipes.value.NonBlankString
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Test

class RecipeListViewModelTest : BaseTest() {

    private object TestData {
        val ingredient = GetRecipeListUseCase.Ingredient(
            units = NonBlankString.from("1 1/2")!!,
            unitType = NonBlankString.from("cups")!!,
            name = NonBlankString.from("magic")!!,
        )
        val ingredient2 = GetRecipeListUseCase.Ingredient(
            units = NonBlankString.from("1")!!,
            unitType = NonBlankString.from("tablespoon")!!,
            name = NonBlankString.from("love")!!,
        )
        val ingredient3 = GetRecipeListUseCase.Ingredient(
            units = NonBlankString.from("1")!!,
            unitType = NonBlankString.from("piece")!!,
            name = NonBlankString.from("determination")!!,
        )
        val ingredients = listOf(ingredient, ingredient2, ingredient3)

        val recipe = GetRecipeListUseCase.Recipe(
            id = NonBlankString.from("1")!!,
            description = NonBlankString.from("description")!!,
            servings = null,
            instructions = null,
            ingredients = ingredients
        )
        val recipes = listOf(recipe)
    }

    @Test
    fun onRecipeListReceived_showRecipeList() = runTest {
        // GIVEN the recipe list is returned
        val useCase = mockk<GetRecipeListUseCase>()
        coEvery { useCase.observe() } returns flow { emit(TestData.recipes) }
        val tested = RecipeListViewModel(
            recipesUseCase = useCase,
        )

        // THEN the recipe list is shown
        tested.uiState.test {
            val item = awaitItem()

            item shouldBeInstanceOf UiState.RecipeList::class

            val recipes = (item as UiState.RecipeList).recipeList
            recipes.size shouldBeEqualTo 1
        }
    }

    @Test
    fun onRecipeListReceived_concatenatesIngredients() = runTest {
        // GIVEN the recipe list is returned
        val useCase = mockk<GetRecipeListUseCase>()
        coEvery { useCase.observe() } returns flow { emit(TestData.recipes) }
        val tested = RecipeListViewModel(
            recipesUseCase = useCase,
        )

        // THEN the recipe ingredients are concatenated
        tested.uiState.test {
            val item = awaitItem()
            item shouldBeInstanceOf UiState.RecipeList::class.java
            val recipe = (item as UiState.RecipeList).recipeList[0]
            recipe.body?.value shouldBeEqualTo "magic, love, determination"
        }
    }
}