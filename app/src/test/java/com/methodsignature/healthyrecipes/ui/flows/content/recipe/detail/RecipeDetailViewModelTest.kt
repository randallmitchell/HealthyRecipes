package com.methodsignature.healthyrecipes.ui.flows.content.recipe.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.methodsignature.healthyrecipes.BaseTest
import com.methodsignature.healthyrecipes.service.errors.EntityNotFoundException
import com.methodsignature.healthyrecipes.ui.flows.content.Route
import com.methodsignature.healthyrecipes.ui.flows.content.recipe.detail.RecipeDetailViewModel.MessageBarState
import com.methodsignature.healthyrecipes.ui.flows.content.recipe.detail.RecipeDetailViewModel.UiState
import com.methodsignature.healthyrecipes.usecase.GetRecipeDetailUseCase
import com.methodsignature.healthyrecipes.value.NonBlankString
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Test

class RecipeDetailViewModelTest : BaseTest() {

    private object TestData {
        val ingredient = GetRecipeDetailUseCase.Ingredient(
            units = NonBlankString.from("1 1/2")!!,
            unitType = NonBlankString.from("cups")!!,
            name = NonBlankString.from("magic")!!,
        )
        val ingredient2 = GetRecipeDetailUseCase.Ingredient(
            units = NonBlankString.from("1")!!,
            unitType = NonBlankString.from("tablespoon")!!,
            name = NonBlankString.from("love")!!,
        )
        val ingredient3 = GetRecipeDetailUseCase.Ingredient(
            units = NonBlankString.from("1")!!,
            unitType = NonBlankString.from("piece")!!,
            name = NonBlankString.from("determination")!!,
        )
        val ingredients = listOf(ingredient, ingredient2, ingredient3)

        val recipe = GetRecipeDetailUseCase.Recipe(
            id = NonBlankString.from("1")!!,
            name = NonBlankString.from("name")!!,
            description = NonBlankString.from("description")!!,
            servings = NonBlankString.from("4 on the floor"),
            instructions = NonBlankString.from("instructions"),
            ingredients = ingredients,
        )
    }

    @Test
    fun `GIVEN recipe detail received THEN show recipe`() = runTest {
        val expected = TestData.recipe
        val useCase = mockk<GetRecipeDetailUseCase>()
        coEvery { useCase.observe(expected.id) } returns flow { emit(expected) }
        val tested = RecipeDetailViewModel(
            savedStateHandle = SavedStateHandle(
                mapOf(Route.RecipeDetail.RECIPE_ID_KEY to expected.id.value)
            ),
            getRecipeDetailUseCase = useCase,
        )

        tested.uiState.test {
            val item = awaitItem()
            item shouldBeInstanceOf UiState.RecipeDetail::class

            val actual = (item as UiState.RecipeDetail)
            actual.id.value shouldBeEqualTo "1"
            actual.description.value shouldBeEqualTo "description"
            actual.instructions?.value shouldBeEqualTo "instructions"
            actual.ingredients shouldBeEqualTo listOf(
                NonBlankString.from("1 1/2 cups magic")!!,
                NonBlankString.from("1 tablespoon love")!!,
                NonBlankString.from("1 piece determination")!!,
            )
        }
    }

    @Test
    fun `WHEN back is pressed THEN request the screen be closed`()  = runTest {
        val recipe = TestData.recipe
        val useCase = mockk<GetRecipeDetailUseCase>()
        coEvery { useCase.observe(recipe.id) } returns flow { emit(recipe) }
        val tested = RecipeDetailViewModel(
            savedStateHandle = SavedStateHandle(
                mapOf(Route.RecipeDetail.RECIPE_ID_KEY to recipe.id.value)
            ),
            getRecipeDetailUseCase = useCase,
        )

        tested.onBackPress()

        val expected = UiState.RequestingClose
        tested.uiState.test {
            val actual = awaitItem()
            actual shouldBe expected
        }
    }

    @Test
    fun `GIVEN an error occurs when fetching recipe THEN a generic error message is shown`() = runTest {
        val recipe = TestData.recipe
        val useCase = mockk<GetRecipeDetailUseCase>()
        coEvery { useCase.observe(recipe.id) } returns flow {
            throw EntityNotFoundException("")
        }
        val tested = RecipeDetailViewModel(
            savedStateHandle = SavedStateHandle(
                mapOf(Route.RecipeDetail.RECIPE_ID_KEY to recipe.id.value)
            ),
            getRecipeDetailUseCase = useCase
        )

        val expected = MessageBarState.GenericError
        tested.messageBarState.test {
            val actual = awaitItem()
            actual shouldBe expected
        }
    }

    @Test
    fun `GIVEN a request to dismiss the message bar THEN the message bar is dismissed`() = runTest {
        val recipe = TestData.recipe
        val useCase = mockk<GetRecipeDetailUseCase>()
        coEvery { useCase.observe(recipe.id) } returns flow { throw Exception("") }
        val tested = RecipeDetailViewModel(
            savedStateHandle = SavedStateHandle(
                mapOf(Route.RecipeDetail.RECIPE_ID_KEY to recipe.id.value)
            ),
            getRecipeDetailUseCase = useCase
        )

        tested.onDismissMessageBar()

        val expected = MessageBarState.None
        tested.messageBarState.test {
            val actual = awaitItem()
            actual shouldBe expected
        }
    }
}