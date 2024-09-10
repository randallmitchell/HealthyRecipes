package com.methodsignature.healthyrecipes.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

object Colors {
    val Basmati = Color(0xFFFDFDFD)
    val Naan = Color(0xFFFFFFF0)
    val Ceramic = Color(0xFF5C868E)
    val Chutney = Color(0xFF7B7917)
    val Corriander = Color(0xFFAD801C)
    val Lotus = Color(0xFF894963)
    val Saffron = Color(0xFFF0C00D)
    val Steel = Color(0xAC303237)
    val Tamarind = Color(0xFF000000)
}

@Preview
@Composable
fun ColorsPreview() {
    val colors = listOf(
        "Basmati" to Colors.Basmati,
        "Naan" to Colors.Naan,
        "Ceramic" to Colors.Ceramic,
        "Chutney" to Colors.Chutney,
        "Corriander" to Colors.Corriander,
        "Lotus" to Colors.Lotus,
        "Saffron" to Colors.Saffron,
        "Steel" to Colors.Steel,
        "Tamarind" to Colors.Tamarind,
    )

    LazyColumn {
        items(colors.size) { i ->
            Text(
                text = colors[i].first,
                color = if (
                    colors[i].second == Colors.Steel ||
                    colors[i].second == Colors.Tamarind
                ) {
                    Colors.Basmati
                } else {
                    Colors.Tamarind
                },
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(colors[i].second)
                    .padding(all = 20.dp)
                    .fillParentMaxWidth()
            )
        }
    }
}
