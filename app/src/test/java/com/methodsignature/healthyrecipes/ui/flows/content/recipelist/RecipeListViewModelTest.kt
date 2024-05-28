package com.methodsignature.healthyrecipes.ui.flows.content.recipelist

import app.cash.turbine.test
import com.methodsignature.healthyrecipes.BaseTest
import com.methodsignature.healthyrecipes.ui.flows.content.recipelist.RecipeListViewModel.UiState
import com.methodsignature.healthyrecipes.usecase.GetRecipeListUseCase
import com.methodsignature.healthyrecipes.value.NonBlankString
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Test

class RecipeListViewModelTest : BaseTest() {


    private object TestData {
        val ingredient = GetRecipeListUseCase.Ingredient(
            units = NonBlankString.from("1 1/2")!!,
            unitType = NonBlankString.from("cups")!!,
            name = NonBlankString.from("magic")!!,
        )
        val ingredients = listOf(ingredient)

        val recipe = GetRecipeListUseCase.Recipe(
            id = NonBlankString.from("1")!!,
            description = NonBlankString.from("description")!!,
            servings = "",
            instructions = "",
            ingredients = ingredients
        )
        val recipes = listOf(recipe)
    }

    @Test
    fun onRecipeListReceived_showRecipeList() = runTest {
        // GIVEN the recipe list is returned
        val useCase = mockk<GetRecipeListUseCase>()
        coEvery { useCase.run() } returns (TestData.recipes)
        val tested = RecipeListViewModel(
            recipesUseCase = useCase,
        )

        // THEN the recipe list is shown
        tested.uiState.test {
            expectMostRecentItem() shouldBeInstanceOf UiState.RecipeList::class.java
        }
    }
}