package com.methodsignature.healthyrecipes.ui

import app.cash.turbine.test
import com.methodsignature.healthyrecipes.BaseTest
import com.methodsignature.healthyrecipes.ui.flow.ApplicationRouterViewModel
import com.methodsignature.healthyrecipes.ui.flow.ApplicationRouterViewModel.NavigationEvent
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import kotlin.test.Test

class ApplicationRouterViewModelTest : BaseTest() {
    @Test
    fun onCreated_sendInitializationEvent() = runTest {
        // GIVEN the Application is initialized
        val tested = ApplicationRouterViewModel()

        // THEN an initialization event should be triggered
        tested.navigationEvent.test {
            expectMostRecentItem() shouldBeEqualTo NavigationEvent.Initialized
        }
    }

    @Test
    fun onOnboardingCompleted_sendContentFlowEvent() = runTest {
        // GIVEN onboarding complete is called
        val tested = ApplicationRouterViewModel()
        tested.onOnboardingComplete()

        // THEN a content flow event is sent
        tested.navigationEvent.test {
            expectMostRecentItem() shouldBeEqualTo NavigationEvent.ContentFlow
        }
    }
}