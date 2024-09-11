package com.methodsignature.healthyrecipes.ui.flow.content.recipe.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.methodsignature.healthyrecipes.language.coroutines.catchWithLogging
import com.methodsignature.healthyrecipes.usecase.GetRecipeListUseCase
import com.methodsignature.healthyrecipes.language.value.EntityId
import com.methodsignature.healthyrecipes.language.value.NonBlankString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    recipesUseCase: GetRecipeListUseCase,
) : ViewModel() {

    sealed class UiState {
        data object Loading : UiState()
        data class RecipeList(
            val recipeList: List<Recipe>,
        ) : UiState()
    }

    data class Recipe(
        val id: EntityId,
        val heading: NonBlankString,
        val body: NonBlankString?,
    )

    sealed class MessageBarState {
        data object GenericError : MessageBarState()
        data object None : MessageBarState()
    }

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _messageBarState: MutableStateFlow<MessageBarState> =
        MutableStateFlow(MessageBarState.None)
    val messageBarState: StateFlow<MessageBarState> = _messageBarState

    init {
        viewModelScope.launch {
            recipesUseCase.observe()
                .catchWithLogging("Error fetching recipe list.") {
                    _messageBarState.value = MessageBarState.GenericError
                }
                .map {
                    it.toViewModel()
                }.collect { recipes ->
                    _uiState.value = UiState.RecipeList(recipes)
                }
        }
    }

    private fun List<GetRecipeListUseCase.Recipe>.toViewModel(): List<Recipe> {
        return this.map {
            Recipe(
                id = it.id,
                heading = it.name,
                body = it.description,
            )
        }
    }

    fun onDismissMessageBar() {
        _messageBarState.value = MessageBarState.None
    }
}