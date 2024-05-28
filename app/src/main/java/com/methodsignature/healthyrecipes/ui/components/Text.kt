package com.methodsignature.healthyrecipes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.methodsignature.healthyrecipes.ui.theme.Colors

@Composable
fun Heading1(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Colors.Tamarind,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = 18.sp,
    )
}

@Composable
fun Heading2(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Colors.Corriander,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = 24.sp,
    )
}

@Composable
fun Body(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Colors.Steel,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = 12.sp,
        maxLines = maxLines,
        overflow = overflow,
    )
}


@Composable
@Preview
fun TextPreview() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(color = Colors.Naan),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Heading1(text = "Heading 1")
        Heading2(text = "Heading 2")
        Body(text = "Body | This is some short body text.")
        Body(text = "Body | This is some significantly longer text that is meant to be the body of some type of page or screen or list item.")
    }
}