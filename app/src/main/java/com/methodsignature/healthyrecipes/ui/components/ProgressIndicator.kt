package com.methodsignature.healthyrecipes.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.methodsignature.healthyrecipes.ui.theme.Colors

@Composable
fun CircularProgressIndicator(
    modifier: Modifier = Modifier,
) {
    androidx.compose.material3.CircularProgressIndicator(
        color = Colors.Saffron,
        modifier = modifier.size(100.dp),
    )
}