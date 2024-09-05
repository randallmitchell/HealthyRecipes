package com.methodsignature.healthyrecipes.ui.components.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.methodsignature.healthyrecipes.ui.theme.Colors

@Composable
fun Screen(
    background: @Composable () -> Unit = { Background(color = Colors.Basmati) },
    content: @Composable (() -> Unit),
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        background()
        content()
    }
}

@Preview
@Composable
fun ScreenPreview() {
    Screen(
        background = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Colors.Ceramic)
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "CONTENT", textAlign = TextAlign.Center)
            }
        },
    )
}