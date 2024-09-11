package com.methodsignature.healthyrecipes.ui.component.dialog

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.methodsignature.healthyrecipes.R
import com.methodsignature.healthyrecipes.ui.animation.slideInFromBottom
import com.methodsignature.healthyrecipes.ui.animation.slideOutPastBottom
import com.methodsignature.healthyrecipes.ui.component.element.Body
import com.methodsignature.healthyrecipes.ui.component.element.ClickableLink
import com.methodsignature.healthyrecipes.ui.theme.Colors

object MessageBar {

    @Composable
    fun DismissableSlideUpMessageBar(
        message: String,
        isVisible: Boolean,
        modifier: Modifier = Modifier,
        linkText: String = stringResource(id = R.string.message_bar_dismissable_link_default_text),
        onDismiss: () -> Unit,
    ) {
        SlideUpMessageBar(
            isVisible = isVisible,
            modifier = modifier,
        ) {
            ConstraintLayout(
                modifier = modifier.fillMaxWidth()
            ) {
                val (text, link) = createRefs()
                Body(
                    text = message,
                    color = Colors.Naan,
                    modifier = Modifier
                        .constrainAs(text) {
                            start.linkTo(parent.start, margin = 16.dp)
                            top.linkTo(parent.top, margin = 8.dp)
                            end.linkTo(link.start)
                            bottom.linkTo(parent.bottom, margin = 8.dp)
                            width = Dimension.fillToConstraints
                        },
                )
                ClickableLink(
                    text = linkText,
                    onClick = onDismiss,
                    color = Colors.Basmati,
                    linkPadding = 16.dp,
                    modifier = Modifier
                        .constrainAs(link) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        },
                )
            }
        }
    }

    @Composable
    private fun SlideUpMessageBar(
        isVisible: Boolean,
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit,
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = modifier.fillMaxSize()
        ) {
            AnimatedContent(
                targetState = isVisible,
                transitionSpec = {
                    if (isVisible) {
                        slideInFromBottom
                    } else {
                        slideOutPastBottom
                    }
                },
                label = "Slide up message bar",
                modifier = Modifier.padding(8.dp)
            ) {
                when (it) {
                    true -> {
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
                            colors = CardColors(
                                containerColor = Colors.Chutney,
                                contentColor = Colors.Naan,
                                disabledContainerColor = Colors.Chutney,
                                disabledContentColor = Colors.Naan
                            )
                        ) {
                            content()
                        }
                    }
                    false -> {
                        Box(modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun SlideUpMessageBarPreview() {
    MessageBar.DismissableSlideUpMessageBar(
        message = "DismissableSlideUpMessageBar",
        isVisible = true,
        onDismiss = {},
    )
}