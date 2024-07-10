package com.methodsignature.healthyrecipes.ui.flows.content

import androidx.lifecycle.ViewModel
import com.methodsignature.healthyrecipes.value.EntityId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ContentFlowViewModel @Inject constructor() : ViewModel() {

    sealed class NavigationEvent {
        data object Initialized : NavigationEvent()
        data class ViewRecipe(val recipeId: EntityId) : NavigationEvent()
        data object FlowComplete : NavigationEvent()
        data object CloseRecipe : NavigationEvent()
        data object Navigated : NavigationEvent()
    }

    private val _navigationEvent: MutableStateFlow<NavigationEvent> =
        MutableStateFlow(NavigationEvent.Initialized)
    val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent

    fun onRecipeSelected(recipeId: EntityId) {
        _navigationEvent.value = NavigationEvent.ViewRecipe(recipeId)
    }

    fun onCloseRecipeDetailRequested() {
        _navigationEvent.value = NavigationEvent.CloseRecipe
    }

    fun onNavigationComplete() {
        _navigationEvent.value = NavigationEvent.Navigated
    }

    fun onCloseRecipeListRequested() {
        _navigationEvent.value = NavigationEvent.FlowComplete
    }
}