package com.methodsignature.healthyrecipes.ui.features.onboarding.screens.splash

import app.cash.turbine.test
import com.methodsignature.healthyrecipes.BaseTest
import com.methodsignature.healthyrecipes.MainDispatcherRule
import com.methodsignature.healthyrecipes.ui.features.onboarding.screens.splash.SplashScreenViewModel.NavigationEvent
import com.methodsignature.healthyrecipes.ui.features.onboarding.screens.splash.SplashScreenViewModel.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Rule
import kotlin.test.Test

class SplashScreenViewModelTest: BaseTest() {

    @Test
    fun onSplashStart_sendSplashCompleteEvent() =
        runTest {
            // GIVEN the Splash screen is launched
            val tested = SplashScreenViewModel()

            // THEN SplashComplete is called after some seconds
            tested.uiState.test {
                delay(5000)
                expectMostRecentItem() shouldBeEqualTo UiState.Navigating(NavigationEvent.SplashComplete)
            }
        }

    @Test
    fun onSplashNavigated_sendNavigatedEvent() =
        runTest {
            // GIVEN onNavigated is called
            val tested = SplashScreenViewModel()
            tested.onNavigated()

            // THEN Navigated is called
            tested.uiState.test {
                expectMostRecentItem() shouldBeEqualTo UiState.Navigated
            }
        }
}