package com.methodsignature.healthyrecipes.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.methodsignature.healthyrecipes.language.DoNothing
import com.methodsignature.healthyrecipes.ui.flows.content.ContentFlow
import com.methodsignature.healthyrecipes.ui.flows.onboarding.OnboardingFlow
import com.methodsignature.healthyrecipes.ui.theme.HealthyRecipesTheme

@Composable
fun ApplicationRouter(
    viewModel: ApplicationRouterViewModel = hiltViewModel(),
    onContentFlowComplete: () -> Unit,
) {
    val navController = rememberNavController()
    val onOnboardingComplete = { viewModel.onOnboardingComplete() }

    val navigationEvent = viewModel.navigationEvent.collectAsState()
    when (navigationEvent.value) {
        ApplicationRouterViewModel.NavigationEvent.ContentFlow -> {
            navController.navigate(Route.ContentFlow.path)
        }

        ApplicationRouterViewModel.NavigationEvent.Initialized -> DoNothing
    }

    ApplicationRouterContent(
        navController = navController,
        startDestination = Route.OnboardingFlow,
        onOnboardingComplete = onOnboardingComplete,
        onContentFlowComplete = onContentFlowComplete,
    )
}

@Composable
fun ApplicationRouterContent(
    navController: NavHostController,
    startDestination: Route,
    onOnboardingComplete: () -> Unit,
    onContentFlowComplete: () -> Unit,
) {
    HealthyRecipesTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = startDestination.path,
            ) {
                composable(Route.OnboardingFlow.path) {
                    OnboardingFlow(
                        modifier = Modifier.padding(innerPadding),
                        onOnboardingComplete = {
                            onOnboardingComplete()
                        }
                    )
                }
                composable(Route.ContentFlow.path) {
                    ContentFlow(
                        modifier = Modifier.padding(innerPadding),
                        onFlowComplete = {
                            onContentFlowComplete()
                        }
                    )
                }
            }
        }
    }
}