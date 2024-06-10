package com.methodsignature.healthyrecipes.ui.flows.content.recipe.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.methodsignature.healthyrecipes.ui.flows.content.Route
import com.methodsignature.healthyrecipes.usecase.GetRecipeDetailUseCase
import com.methodsignature.healthyrecipes.value.EntityId
import com.methodsignature.healthyrecipes.value.NonBlankString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getRecipeDetailUseCase: GetRecipeDetailUseCase,
) : ViewModel() {

    sealed class UiState {
        data object Loading : UiState()
        data class RecipeDetail(
            val id: EntityId,
            val description: NonBlankString,
            val servings: NonBlankString? = null,
            val instructions: NonBlankString? = null,
            val ingredients: List<NonBlankString>,
        ) : UiState()
    }

    private val recipeId: EntityId =
        checkNotNull(EntityId.from(savedStateHandle.get<String>(Route.RecipeDetail.RECIPE_ID_KEY)))

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        viewModelScope.launch {
            val recipe = getRecipeDetailUseCase.run(recipeId)
            _uiState.value = recipe.toViewModel()
        }
    }

    private fun GetRecipeDetailUseCase.Recipe.toViewModel(): UiState.RecipeDetail {
        return UiState.RecipeDetail(
            id = this.id,
            description = this.description,
            servings = this.servings,
            instructions = this.instructions,
            ingredients = this.ingredients.map { ingredient ->
                NonBlankString.from("${ingredient.units.value} ${ingredient.unitType.value} ${ingredient.name.value}")!!
            }
        )
    }
}