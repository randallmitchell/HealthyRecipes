package com.methodsignature.healthyrecipes.ui.flows.content

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.methodsignature.healthyrecipes.ui.flows.content.recipelist.RecipeListContent
import com.methodsignature.healthyrecipes.ui.flows.content.recipelist.RecipeListScreen

@Composable
fun ContentFlow(
    modifier: Modifier = Modifier,
) {
    RecipeListScreen(modifier = modifier)
}