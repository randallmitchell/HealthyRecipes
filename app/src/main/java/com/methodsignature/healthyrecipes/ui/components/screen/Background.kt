package com.methodsignature.healthyrecipes.ui.components.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import com.methodsignature.healthyrecipes.ui.theme.Colors

@Composable
fun Background(
    color: Color
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = color,
                shape = RectangleShape
            )
    )
}

@Preview
@Composable
fun BackgroundWithColorPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        Background(color = Colors.Chutney)
    }
}
