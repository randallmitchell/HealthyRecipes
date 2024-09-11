package com.methodsignature.healthyrecipes.ui.flow.onboarding.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.methodsignature.healthyrecipes.R
import com.methodsignature.healthyrecipes.language.DoNothing
import com.methodsignature.healthyrecipes.ui.component.screen.Background
import com.methodsignature.healthyrecipes.ui.component.screen.Screen
import com.methodsignature.healthyrecipes.ui.flow.onboarding.screens.splash.SplashScreenViewModel
import com.methodsignature.healthyrecipes.ui.theme.Colors

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashScreenViewModel = hiltViewModel(),
    onSplashScreenComplete: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    SplashScreenContent(
        modifier = modifier,
        uiState = uiState,
        onSplashScreenComplete = onSplashScreenComplete,
        onNavigated = {
            viewModel.onNavigated()
        }
    )
}

@Composable
fun SplashScreenContent(
    modifier: Modifier,
    uiState: SplashScreenViewModel.UiState,
    onSplashScreenComplete: () -> Unit,
    onNavigated: () -> Unit,
) {
    Screen(
        background = { Background(color = Colors.Lotus) }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(id = R.string.splash_message),
                style = TextStyle(
                    fontSize = 24.sp,
                    color = Colors.Saffron,
                ),
                textAlign = TextAlign.Center
            )
        }

        when (uiState) {
            SplashScreenViewModel.UiState.Displaying -> DoNothing
            SplashScreenViewModel.UiState.Navigated -> DoNothing
            is SplashScreenViewModel.UiState.Navigating -> {
                onSplashScreenComplete()
                onNavigated()
            }
        }
    }
}

@Preview(showBackground = true, name = "Displaying")
@Composable
fun SplashScreenPreview() {
    SplashScreenContent(
        modifier = Modifier,
        uiState = SplashScreenViewModel.UiState.Displaying,
        onSplashScreenComplete = DoNothing,
        onNavigated = {},
    )
}
