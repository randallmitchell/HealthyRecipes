package com.methodsignature.healthyrecipes.ui.flows.content

import app.cash.turbine.test
import com.methodsignature.healthyrecipes.BaseTest
import com.methodsignature.healthyrecipes.ui.flows.content.ContentFlowViewModel.NavigationEvent
import com.methodsignature.healthyrecipes.value.EntityId
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class ContentFlowViewModelTest : BaseTest() {

    @Test
    fun onCreated_sendInitializedEvent() = runTest {
        // GIVEN the Content Flow is initialized
        val tested = ContentFlowViewModel()

        // THEN an Initialized event should be triggered
        tested.navigationEvent.test {
            expectMostRecentItem() shouldBeEqualTo NavigationEvent.Initialized
        }
    }

    @Test
    fun onRecipeSelected_sendViewRecipeEvent() = runTest {
        // GIVEN a recipe is selected event is sent
        val tested = ContentFlowViewModel()
        val entityId = EntityId.from("1")!!
        tested.onRecipeSelected(entityId)

        // THEN
        tested.navigationEvent.test {
            val mostRecentItem = expectMostRecentItem()
            mostRecentItem::class shouldBeEqualTo NavigationEvent.ViewRecipe::class
            (mostRecentItem as NavigationEvent.ViewRecipe).recipeId.value shouldBeEqualTo entityId.value
        }
    }

    @Test
    fun onNavigationCompleteSent_sendNavigatedEvent() = runTest {
        // GIVEN navigation complete event is sent
        val tested = ContentFlowViewModel()
        tested.onRecipeSelected(EntityId.from("1")!!)
        tested.onNavigationComplete()

        // THEN navigated event is sent
        tested.navigationEvent.test {
            expectMostRecentItem() shouldBeEqualTo NavigationEvent.Navigated
        }

    }

    @Test
    fun onRecipeCloseRequested_sendCloseRecipeEvent() = runTest {
        // GIVEN recipe close requested
        val tested = ContentFlowViewModel()
        tested.onRecipeSelected(EntityId.from("1")!!)
        tested.onNavigationComplete()
        tested.onCloseRecipeDetailRequested()

        // THEN recipe close requested event is sent
        tested.navigationEvent.test {
            expectMostRecentItem() shouldBeEqualTo NavigationEvent.CloseRecipe
        }
    }

    @Test
    fun onCloseRecipeList_sendFlowCompleteEvent() = runTest {
        // GIVEN recipe list close requested
        val tested = ContentFlowViewModel()
        tested.onRecipeSelected(EntityId.from("1")!!)
        tested.onNavigationComplete()
        tested.onCloseRecipeDetailRequested()
        tested.onNavigationComplete()
        tested.onCloseRecipeListRequested()

        // THEN send flow complete event
        tested.navigationEvent.test {
            expectMostRecentItem() shouldBeEqualTo NavigationEvent.FlowComplete
        }
    }
}