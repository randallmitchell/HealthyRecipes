package com.methodsignature.healthyrecipes.ui.flows.content

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.methodsignature.healthyrecipes.ui.flows.content.recipe.list.RecipeListScreen

@Composable
fun ContentFlow(
    modifier: Modifier = Modifier,
) {
    RecipeListScreen(
        modifier = modifier,
        onRecipeSelected = { TODO("Implement recipe detail navigation") }
    )
}