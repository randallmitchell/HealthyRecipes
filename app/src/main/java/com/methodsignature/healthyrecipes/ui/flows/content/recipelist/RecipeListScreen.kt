package com.methodsignature.healthyrecipes.ui.flows.content.recipelist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.methodsignature.healthyrecipes.ui.components.Body
import com.methodsignature.healthyrecipes.ui.components.CircularProgressIndicator
import com.methodsignature.healthyrecipes.ui.components.Heading2
import com.methodsignature.healthyrecipes.ui.components.screen.Background
import com.methodsignature.healthyrecipes.ui.components.screen.Screen
import com.methodsignature.healthyrecipes.ui.theme.Colors
import com.methodsignature.healthyrecipes.value.NonBlankString

@Composable
fun RecipeListScreen(
    modifier: Modifier = Modifier,
    recipeListViewModel: RecipeListViewModel = hiltViewModel(),
) {
    val uiState = recipeListViewModel.uiState.collectAsState()
    RecipeListContent(
        uiState = uiState.value,
        modifier = modifier,
    )
}

@Composable
fun RecipeListContent(
    uiState: RecipeListViewModel.UiState,
    modifier: Modifier,
) {
    Screen(
        background = { Background(color = Colors.Naan) }
    ) {
        when (uiState) {
            RecipeListViewModel.UiState.Loading -> {
                Box(
                    modifier = modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is RecipeListViewModel.UiState.RecipeList -> {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.background(color = Colors.Corriander)
                ) {
                    items(uiState.recipeList) { recipe ->
                        Column(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .background(color = Colors.Naan)
                                .padding(all = 12.dp),
                        ) {
                            Heading2(text = recipe.heading.value)
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
        modifier = Modifier,
    )
}