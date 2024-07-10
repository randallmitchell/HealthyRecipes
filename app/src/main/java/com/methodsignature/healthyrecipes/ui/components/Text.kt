package com.methodsignature.healthyrecipes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.methodsignature.healthyrecipes.ui.theme.Colors

@Composable
fun Title(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Colors.Tamarind,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = 16.sp,
    )
}

@Composable
fun Heading1(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Colors.Chutney,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = 28.sp,
    )
}

@Composable
fun Heading2(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Colors.Steel,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun Emphasis(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Colors.Steel,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = 10.sp,
        fontStyle = FontStyle.Italic,
        maxLines = maxLines,
        overflow = overflow,
    )
}

@OptIn(ExperimentalTextApi::class)
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
            .background(color = Colors.Naan)
            .padding(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Title(text = "Title")
        Heading1(text = "Heading 1")
        Heading2(text = "Heading 2")
        Body(text = "Body | This is some short body text.")
        Body(text = "Body | This is some significantly longer text that is meant to be the body of some type of page or screen or list item.")
    }
}