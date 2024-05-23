package com.methodsignature.healthyrecipes.ui.features.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.methodsignature.healthyrecipes.ui.features.onboarding.screens.SplashScreen

@Composable
fun OnboardingFlow(
    modifier: Modifier = Modifier,
    onOnboardingComplete: () -> Unit,
    viewModel: OnboardingFlowViewModel = viewModel(),
) {
    val navController = rememberNavController()
    val startDestination = Route.SplashScreen

    OnboardingFlowContent(
        navController = navController,
        onOnboardingComplete = onOnboardingComplete,
        startDestination = startDestination,
    )
}

@Composable
fun OnboardingFlowContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onOnboardingComplete: () -> Unit,
    startDestination: Route,
) {
    NavHost(navController = navController, startDestination = startDestination.path) {
        composable(startDestination.path) {
            SplashScreen(
                modifier = modifier,
                onSplashScreenComplete = onOnboardingComplete,
            )
        }
    }
}
