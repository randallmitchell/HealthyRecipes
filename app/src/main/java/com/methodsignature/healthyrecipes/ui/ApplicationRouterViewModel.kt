package com.methodsignature.healthyrecipes.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ApplicationRouterViewModel @Inject constructor() : ViewModel() {

    sealed class NavigationEvent {
        data object Initialized : NavigationEvent()
        data object ContentFlow : NavigationEvent()
    }

    private val _navigationEvent: MutableStateFlow<NavigationEvent> =
        MutableStateFlow(NavigationEvent.Initialized)
    val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent

    fun onOnboardingComplete() {
        _navigationEvent.value = NavigationEvent.ContentFlow
    }
}