package com.methodsignature.healthyrecipes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.methodsignature.healthyrecipes.observability.logError
import com.methodsignature.healthyrecipes.usecase.UpdateAllRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HealthyRecipesApplicationViewModel @Inject constructor(
    private val updateAllRecipesUseCase: UpdateAllRecipesUseCase,
) : ViewModel() {

    sealed class NavigationEvent {
        data object Initialized : NavigationEvent()
        data object ApplicationComplete : NavigationEvent()
    }

    private val _navigationEvent: MutableStateFlow<NavigationEvent> =
        MutableStateFlow(NavigationEvent.Initialized)
    val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent

    fun onApplicationLaunch() {
        viewModelScope.launch {
            try {
                updateAllRecipesUseCase.run()
            } catch (throwable: Throwable) {
                logError("Unable to update recipes on launch", throwable)
            }
        }
    }

    fun onContentFlowComplete() {
        _navigationEvent.value = NavigationEvent.ApplicationComplete
    }
}