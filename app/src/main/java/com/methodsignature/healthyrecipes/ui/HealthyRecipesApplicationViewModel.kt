package com.methodsignature.healthyrecipes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.methodsignature.healthyrecipes.usecase.SeedRecipeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HealthyRecipesApplicationViewModel @Inject constructor(
    private val seedRecipeListUseCase: SeedRecipeListUseCase,
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
            seedRecipeListUseCase.run()
        }
    }

    fun onContentFlowComplete() {
        _navigationEvent.value = NavigationEvent.ApplicationComplete
    }
}