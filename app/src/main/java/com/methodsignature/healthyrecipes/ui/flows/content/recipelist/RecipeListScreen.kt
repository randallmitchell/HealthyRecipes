package com.methodsignature.healthyrecipes.ui.flows.content.recipelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.methodsignature.healthyrecipes.ui.components.screen.Background
import com.methodsignature.healthyrecipes.ui.components.screen.Screen
import com.methodsignature.healthyrecipes.ui.theme.Colors

@Composable
fun RecipeListScreen(
    modifier: Modifier = Modifier,
    recipeListViewModel: RecipeListViewModel = hiltViewModel(),
) {
    RecipeListContent(modifier = modifier)
}

@Composable
fun RecipeListContent(
    modifier: Modifier,
) {
    Screen(
        background = { Background(color = Colors.Naan) }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "recipe list",
                style = TextStyle(
                    fontSize = 32.sp,
                    color = Colors.Lotus,
                    fontWeight = FontWeight.ExtraBold,
                )
            )
        }
    }
}

@Composable
@Preview
fun RecipeListContentPreview() {
    RecipeListContent(modifier = Modifier)
}