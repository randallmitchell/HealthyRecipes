package com.methodsignature.healthyrecipes.ui.flow.content.recipe.list

import app.cash.turbine.test
import com.methodsignature.healthyrecipes.BaseTest
import com.methodsignature.healthyrecipes.ui.flow.content.recipe.list.RecipeListViewModel.MessageBarState
import com.methodsignature.healthyrecipes.ui.flow.content.recipe.list.RecipeListViewModel.UiState
import com.methodsignature.healthyrecipes.usecase.GetRecipeListUseCase
import com.methodsignature.healthyrecipes.language.value.NonBlankString
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
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
            name = NonBlankString.from("name")!!,
            description = NonBlankString.from("description")!!,
        )
        val recipes = listOf(recipe)
    }

    @Test
    fun `GIVEN the recipe list is received THEN it is shown`() = runTest {
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
    fun `GIVEN the use case throws an error THEN a generic error message is shown`() = runTest {
        val useCase = mockk<GetRecipeListUseCase>()
        coEvery { useCase.observe() } returns flow { throw Exception("") }
        val tested = RecipeListViewModel(
            recipesUseCase = useCase,
        )

        val expected = MessageBarState.GenericError
        tested.messageBarState.test {
            val actual = awaitItem()
            actual shouldBe expected
        }
    }

    @Test
    fun `GIVEN a request to dismiss the message bar THEN the message bar is dismissed`() = runTest {
        val useCase = mockk<GetRecipeListUseCase>()
        coEvery { useCase.observe() } returns flow { throw Exception("") }
        val tested = RecipeListViewModel(
            recipesUseCase = useCase,
        )

        tested.onDismissMessageBar()

        val expected = MessageBarState.None
        tested.messageBarState.test {
            val actual = awaitItem()
            actual shouldBe expected
        }

    }
}