package com.methodsignature.healthyrecipes.ui.features.onboarding.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    SplashScreenContent(modifier = modifier)
}

@Composable
fun SplashScreenContent(modifier: Modifier) {
    Text(text = "Healthy Recipes", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreenContent(modifier = Modifier)
}

