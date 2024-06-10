package com.methodsignature.healthyrecipes.ui.flows.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.methodsignature.healthyrecipes.language.DoNothing
import com.methodsignature.healthyrecipes.ui.flows.onboarding.OnboardingFlowViewModel.NavigationEvent
import com.methodsignature.healthyrecipes.ui.flows.onboarding.screens.SplashScreen

@Composable
fun OnboardingFlow(
    modifier: Modifier = Modifier,
    onOnboardingComplete: () -> Unit,
    viewModel: OnboardingFlowViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val startDestination = Route.SplashScreen

    val navigationEvent = viewModel.navigationEvent.collectAsState()
    when (navigationEvent.value) {
        NavigationEvent.Initialized -> DoNothing
        NavigationEvent.Navigated -> DoNothing
        NavigationEvent.OnboardingComplete -> {
            onOnboardingComplete()
        }
    }

    OnboardingFlowContent(
        modifier = modifier,
        navController = navController,
        onSplashComplete = {
            viewModel.onSplashComplete()
        },
        startDestination = startDestination,
    )
}

@Composable
fun OnboardingFlowContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onSplashComplete: () -> Unit,
    startDestination: Route,
) {
    NavHost(navController = navController, startDestination = startDestination.path.value) {
        composable(Route.SplashScreen.path.value) {
            SplashScreen(
                modifier = modifier,
                onSplashScreenComplete = onSplashComplete,
            )
        }
    }
}
