package com.methodsignature.healthyrecipes.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.methodsignature.healthyrecipes.ui.theme.Colors

@Composable
fun ClickableLink(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Colors.Lotus,
    linkPadding: Dp = 16.dp,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    Box(modifier = modifier
        .padding(linkPadding)
        .clickable {
            onClick()
        }
    ) {
        Link(
            text = text,
            color = color,
            maxLines = maxLines,
            overflow = overflow,
            modifier = Modifier.padding(linkPadding)
        )
    }
}

@Preview
@Composable
fun ClickableLinkPreview() {
    ClickableLink(text = "clickable link", onClick = {})
}