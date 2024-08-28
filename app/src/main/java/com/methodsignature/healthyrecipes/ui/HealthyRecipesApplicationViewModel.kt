package com.methodsignature.healthyrecipes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.methodsignature.healthyrecipes.usecase.TryToPrefillRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HealthyRecipesApplicationViewModel @Inject constructor(
    private val tryToPrefillRecipesUseCase: TryToPrefillRecipesUseCase,
) : ViewModel() {

    fun onApplicationLaunch() {
        viewModelScope.launch {
            tryToPrefillRecipesUseCase.run()
        }
    }
}