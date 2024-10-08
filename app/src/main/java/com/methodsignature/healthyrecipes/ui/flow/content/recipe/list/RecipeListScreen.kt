package com.methodsignature.healthyrecipes.ui.flow.content.recipe.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.methodsignature.healthyrecipes.R
import com.methodsignature.healthyrecipes.ui.component.element.Body
import com.methodsignature.healthyrecipes.ui.component.element.CenterInSpaceProgressIndicator
import com.methodsignature.healthyrecipes.ui.component.element.Heading1
import com.methodsignature.healthyrecipes.ui.component.dialog.MessageBar
import com.methodsignature.healthyrecipes.ui.component.screen.Background
import com.methodsignature.healthyrecipes.ui.component.screen.Screen
import com.methodsignature.healthyrecipes.ui.flow.content.recipe.list.RecipeListViewModel.MessageBarState
import com.methodsignature.healthyrecipes.ui.theme.Colors
import com.methodsignature.healthyrecipes.language.value.EntityId
import com.methodsignature.healthyrecipes.language.value.NonBlankString

@Composable
fun RecipeListScreen(
    modifier: Modifier = Modifier,
    viewModel: RecipeListViewModel = hiltViewModel(),
    onRecipeSelected: (EntityId) -> Unit,
    onCloseRecipeListRequested: () -> Unit,
) {
    // TODO implement close recipe list
    val uiState by viewModel.uiState.collectAsState()
    val messageBarState by viewModel.messageBarState.collectAsState()

    RecipeListContent(
        uiState = uiState,
        messageBarState = messageBarState,
        modifier = modifier,
        onRecipeSelected = onRecipeSelected,
        onDismissMessageBarRequested = { viewModel.onDismissMessageBar() }
    )
}

@Composable
fun RecipeListContent(
    uiState: RecipeListViewModel.UiState,
    messageBarState: MessageBarState,
    modifier: Modifier,
    onRecipeSelected: (EntityId) -> Unit,
    onDismissMessageBarRequested: () -> Unit,
) {
    Screen(background = { Background(color = Colors.Naan) }) {
        when (uiState) {
            RecipeListViewModel.UiState.Loading -> CenterInSpaceProgressIndicator(
                modifier = modifier,
            )

            is RecipeListViewModel.UiState.RecipeList -> {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 1.dp),
                    verticalArrangement = Arrangement.spacedBy(1.dp),
                    modifier = modifier.background(color = Colors.Steel),
                ) {
                    items(uiState.recipeList) { recipe ->
                        Box(modifier = Modifier.clickable { onRecipeSelected(recipe.id) }) {
                            Column(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .background(color = Colors.Basmati)
                                    .padding(all = 12.dp),
                            ) {
                                Heading1(text = recipe.heading.value)
                                recipe.body?.let {
                                    Body(
                                        text = it.value,
                                        maxLines = 2,
                                    )
                                }
                            }
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
            onDismiss = onDismissMessageBarRequested
        )
    }
}

@Composable
@Preview
fun RecipeListContentPreview() {
    RecipeListContent(
        uiState = RecipeListViewModel.UiState.RecipeList(
            listOf(
                RecipeListViewModel.Recipe(
                    id = NonBlankString.from("1")!!,
                    heading = NonBlankString.from("Item 1")!!,
                    body = NonBlankString.from("This is a description"),
                ),
                RecipeListViewModel.Recipe(
                    id = NonBlankString.from("2")!!,
                    heading = NonBlankString.from("Item 2")!!,
                    body = NonBlankString.from("This is a description"),
                ),
                RecipeListViewModel.Recipe(
                    id = NonBlankString.from("3")!!,
                    heading = NonBlankString.from("Item 3")!!,
                    body = NonBlankString.from("This is a description"),
                ),
            )
        ),
        messageBarState = MessageBarState.None,
        modifier = Modifier,
        onRecipeSelected = {},
        onDismissMessageBarRequested = {},
    )
}