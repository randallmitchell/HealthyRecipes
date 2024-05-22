package com.methodsignature.healthyrecipes.ui.features.onboarding.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor() : ViewModel() {

    companion object {
        const val SPLASH_DELAY_IN_MILLIS = 3000L // 3 seconds
    }

    sealed class NavigationEvent {
        data object SplashComplete : NavigationEvent()
    }

    sealed class UiState {
        data object Displaying : UiState()
        data class Navigating(
            val navigationEvent: NavigationEvent,
        ) : UiState()

        data object Navigated : UiState()
    }

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Displaying)
    val uiState: StateFlow<UiState> = _uiState

    init {
        viewModelScope.launch {
            startTransitionTime()
        }
    }

    private suspend fun startTransitionTime() {
        delay(SPLASH_DELAY_IN_MILLIS)
        _uiState.value = UiState.Navigating(NavigationEvent.SplashComplete)
    }

    fun onNavigated() {
        _uiState.value = UiState.Navigated
    }
}