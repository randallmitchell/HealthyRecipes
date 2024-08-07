package com.methodsignature.healthyrecipes.ui.flows.content.recipe.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.methodsignature.healthyrecipes.usecase.GetRecipeListUseCase
import com.methodsignature.healthyrecipes.value.EntityId
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
        val id: EntityId,
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
                    val ingredientsAsList = it.ingredients.foldIndexed(
                        initial = ""
                    ) { index, acc, item ->
                        acc.plus(if (index > 0) ", " else "").plus(item.name.value)
                    }
                    NonBlankString.from(ingredientsAsList)
                }
            )
        }
    }
}