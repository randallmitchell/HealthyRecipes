package com.methodsignature.healthyrecipes.ui.features.onboarding.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.methodsignature.healthyrecipes.ui.components.screen.Background
import com.methodsignature.healthyrecipes.ui.components.screen.Screen
import com.methodsignature.healthyrecipes.ui.theme.Colors

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier
) {
    SplashScreenContent(modifier = modifier)
}

@Composable
fun SplashScreenContent(modifier: Modifier) {
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
                text = "show one of many random silly hellos",
                style = TextStyle(
                    fontSize = 24.sp,
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreenContent(modifier = Modifier)
}
