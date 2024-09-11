package com.methodsignature.healthyrecipes.ui.flow.content.recipe.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.methodsignature.healthyrecipes.R
import com.methodsignature.healthyrecipes.ui.component.Body
import com.methodsignature.healthyrecipes.ui.component.CenterInSpaceProgressIndicator
import com.methodsignature.healthyrecipes.ui.component.Emphasis
import com.methodsignature.healthyrecipes.ui.component.Heading1
import com.methodsignature.healthyrecipes.ui.component.Heading2
import com.methodsignature.healthyrecipes.ui.component.dialog.MessageBar
import com.methodsignature.healthyrecipes.ui.component.screen.Background
import com.methodsignature.healthyrecipes.ui.component.screen.Screen
import com.methodsignature.healthyrecipes.ui.flow.content.recipe.detail.RecipeDetailViewModel.MessageBarState
import com.methodsignature.healthyrecipes.ui.flow.content.recipe.detail.RecipeDetailViewModel.UiState
import com.methodsignature.healthyrecipes.ui.theme.Colors
import com.methodsignature.healthyrecipes.language.value.EntityId
import com.methodsignature.healthyrecipes.language.value.NonBlankString

@Composable
fun RecipeDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: RecipeDetailViewModel = hiltViewModel(),
    onRecipeCloseRequested: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    val errorState by viewModel.messageBarState.collectAsState()

    RecipeDetailContent(
        modifier = modifier,
        uiState = uiState,
        onBackPress = { viewModel.onBackPress() },
        onClose = onRecipeCloseRequested,
        messageBarState = errorState,
        onDismissMessageBarRequested = { viewModel.onDismissMessageBar() },
    )
}

@Composable
fun RecipeDetailContent(
    modifier: Modifier,
    uiState: UiState,
    messageBarState: MessageBarState,
    onBackPress: () -> Unit,
    onClose: () -> Unit,
    onDismissMessageBarRequested: () -> Unit,
) {
    BackHandler {
        onBackPress()
    }

    Screen(
        background = { Background(color = Colors.Naan) },
    ) {
        Box(modifier = modifier.padding(12.dp)) {
            when (uiState) {
                UiState.Loading -> CenterInSpaceProgressIndicator()
                UiState.RequestingClose -> onClose()
                is UiState.RecipeDetail -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(6.dp)
                    ) {
                        Heading1(text = uiState.name.value)
                        Heading2(text = uiState.description.value)
                        uiState.servings?.let { Emphasis(text = it.value) }
                        uiState.instructions?.let { Body(text = it.value) }
                        uiState.ingredients.forEach { ingredient ->
                            Body(
                                text = ingredient.value,
                            )
                        }
                    }
                }
            }
        }
        MessageBar.DismissableSlideUpMessageBar(
            message = stringResource(id = R.string.generic_screen_error_message),
            isVisible = when (messageBarState) {
                MessageBarState.GenericError -> true
                MessageBarState.None -> false
            },
            onDismiss = onDismissMessageBarRequested,
        )
    }
}

@Composable
@Preview
fun RecipeDetailContentPreview() {
    RecipeDetailContent(
        modifier = Modifier,
        uiState = UiState.RecipeDetail(
            id = EntityId.from("1")!!,
            name = NonBlankString.from("Chicken Rotini")!!,
            description = NonBlankString.from("A flavorful dish of grilled chicken and pasta inspired by italian cooking")!!,
            instructions = NonBlankString.from("15 min @ 350 degrees")!!,
            servings = NonBlankString.from("1 pan stan"),
            ingredients = listOf(
                NonBlankString.from("2 cup cherry tomatoes")!!,
                NonBlankString.from("1 can chickpeas")!!,
                NonBlankString.from("1/2 bulb onion")!!,
                NonBlankString.from("1 tablespoon garlic")!!,
                NonBlankString.from("1 teaspoon italian seasoning")!!,
                NonBlankString.from("1 teaspoon oregano")!!,
                NonBlankString.from("1 to taste salt and pepper")!!,
                NonBlankString.from("1 tablespoon olive oil")!!,
            )
        ),
        onBackPress = {},
        messageBarState = MessageBarState.GenericError,
        onClose = {},
        onDismissMessageBarRequested = {},
    )
}