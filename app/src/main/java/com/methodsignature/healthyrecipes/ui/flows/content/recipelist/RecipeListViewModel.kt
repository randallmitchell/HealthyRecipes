package com.methodsignature.healthyrecipes.ui.flows.content.recipelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.methodsignature.healthyrecipes.usecase.GetRecipeListUseCase
import com.methodsignature.healthyrecipes.value.NonBlankString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
        val id: NonBlankString,
        val heading: NonBlankString,
        val body: NonBlankString?,
    )

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        viewModelScope.launch {
            val recipeList = recipesUseCase.run().toViewModel()
            _uiState.value = UiState.RecipeList(recipeList)
        }
    }

    private fun List<GetRecipeListUseCase.Recipe>.toViewModel(): List<Recipe> {
        return this.map {
            Recipe(
                id = it.id,
                heading = it.description,
                body = it.run {
                    val ingredientsAsTwoLines = it.ingredients.take(2).foldIndexed(
                        initial = ""
                    ) { index, acc, item ->
                        acc.plus(if (index > 0) "\n" else "").plus(item.name.value)
                    }
                    NonBlankString.from(ingredientsAsTwoLines)
                }
            )
        }
    }
}