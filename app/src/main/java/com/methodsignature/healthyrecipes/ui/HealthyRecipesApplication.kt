package com.methodsignature.healthyrecipes.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * TODO ingest the list of recipes on first launch.
 */
@Composable
fun HealthyRecipesApplication(
    viewModel: HealthyRecipesApplicationViewModel = hiltViewModel(),
    onApplicationComplete: () -> Unit
) {
    viewModel.onApplicationLaunch()

    ApplicationRouter(
        onContentFlowComplete = {
            onApplicationComplete()
        }
    )
}