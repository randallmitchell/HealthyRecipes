package com.methodsignature.healthyrecipes.ui.flows.onboarding

import app.cash.turbine.test
import com.methodsignature.healthyrecipes.BaseTest
import com.methodsignature.healthyrecipes.ui.flows.onboarding.OnboardingFlowViewModel.NavigationEvent
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import kotlin.test.Test

class OnboardingFlowViewModelTest : BaseTest() {

    @Test
    fun onCreated_sendInitializedEvent() = runTest {
        // GIVEN the Onboarding flow is initialized
        val tested = OnboardingFlowViewModel()

        // THEN an Initialized event should be triggered
        tested.navigationEvent.test {
            expectMostRecentItem() shouldBeEqualTo NavigationEvent.Initialized
        }
    }

    @Test
    fun onSplashComplete_sendOnboardingCompleteEvent() = runTest {
        // GIVEN the Splash screen sends a complete event
        val tested = OnboardingFlowViewModel()
        tested.onSplashComplete()

        // THEN an Onboarding complete event is sent
        tested.navigationEvent.test {
            expectMostRecentItem() shouldBeEqualTo NavigationEvent.OnboardingComplete
        }
    }

    @Test
    fun onOnboardingCompletedNavigationCompleted_sendNavigatedEvent() = runTest {
        // GIVEN the OnboardingFlow calls navigation completed
        val tested = OnboardingFlowViewModel()
        tested.onNavigated()

        // THEN a navigated event is sent
        tested.navigationEvent.test {
            expectMostRecentItem() shouldBeEqualTo NavigationEvent.Navigated
        }
    }
}