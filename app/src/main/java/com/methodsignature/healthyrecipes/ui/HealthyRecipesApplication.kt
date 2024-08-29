package com.methodsignature.healthyrecipes.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.methodsignature.healthyrecipes.language.DoNothing

/**
 * TODO ingest the list of recipes on first launch.
 */
@Composable
fun HealthyRecipesApplication(
    viewModel: HealthyRecipesApplicationViewModel = hiltViewModel(),
    onApplicationComplete: () -> Unit
) {
    viewModel.onApplicationLaunch()


    val navigationEvent = viewModel.navigationEvent.collectAsState()
    when (navigationEvent.value) {
        HealthyRecipesApplicationViewModel.NavigationEvent.Initialized -> DoNothing
        HealthyRecipesApplicationViewModel.NavigationEvent.ApplicationComplete -> {
            onApplicationComplete()
        }
    }

    ApplicationRouter(
        onContentFlowComplete = {
            viewModel.onContentFlowComplete()
        }
    )
}