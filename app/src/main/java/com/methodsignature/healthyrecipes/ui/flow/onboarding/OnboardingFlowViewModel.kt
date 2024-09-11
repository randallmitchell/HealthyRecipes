package com.methodsignature.healthyrecipes.ui.flow.onboarding

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class OnboardingFlowViewModel @Inject constructor() : ViewModel() {

    sealed class NavigationEvent {
        data object Initialized : NavigationEvent()
        data object OnboardingComplete : NavigationEvent()
        data object Navigated : NavigationEvent()
    }

    private val _navigationEvent: MutableStateFlow<NavigationEvent> =
        MutableStateFlow(NavigationEvent.Initialized)
    val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent

    fun onSplashComplete() {
        _navigationEvent.value = NavigationEvent.OnboardingComplete
    }

    fun onNavigated() {
        _navigationEvent.value = NavigationEvent.Navigated
    }
}