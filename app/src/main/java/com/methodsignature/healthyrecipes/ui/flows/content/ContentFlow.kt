package com.methodsignature.healthyrecipes.ui.flows.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.methodsignature.healthyrecipes.language.DoNothing
import com.methodsignature.healthyrecipes.ui.flows.content.recipe.detail.RecipeDetailScreen
import com.methodsignature.healthyrecipes.ui.flows.content.recipe.list.RecipeListScreen
import com.methodsignature.healthyrecipes.value.EntityId

@Composable
fun ContentFlow(
    modifier: Modifier = Modifier,
    viewModel: ContentFlowViewModel = hiltViewModel(),
    onFlowComplete: () -> Unit,
) {
    val navController = rememberNavController()
    val startDestination = Route.RecipeList

    val navigationEvent = viewModel.navigationEvent.collectAsState()
    when (val value = navigationEvent.value) {
        ContentFlowViewModel.NavigationEvent.Initialized -> DoNothing
        ContentFlowViewModel.NavigationEvent.Navigated -> DoNothing
        ContentFlowViewModel.NavigationEvent.CloseRecipe -> {
            navController.popBackStack()
        }

        is ContentFlowViewModel.NavigationEvent.ViewRecipe -> {
            val route = Route.createRecipeDetailRoute(value.recipeId)
            navController.navigate(route)
        }

        ContentFlowViewModel.NavigationEvent.FlowComplete -> {
            onFlowComplete()
        }
    }

    ContentFlowContent(
        modifier = modifier,
        navController = navController,
        onCloseRecipeListRequested = { viewModel.onCloseRecipeListRequested() },
        onRecipeSelected = { viewModel.onRecipeSelected(it) },
        onCloseRecipeDetailRequested = { viewModel.onCloseRecipeDetailRequested() },
        startDestination = startDestination
    )
}

@Composable
fun ContentFlowContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onCloseRecipeListRequested: () -> Unit,
    onRecipeSelected: (recipeId: EntityId) -> Unit,
    onCloseRecipeDetailRequested: () -> Unit,
    startDestination: Route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.path.value
    ) {
        composable(Route.RecipeList.path.value) {
            RecipeListScreen(
                modifier = modifier,
                onRecipeSelected = onRecipeSelected,
                onCloseRecipeListRequested = onCloseRecipeListRequested,
            )
        }
        composable(
            route = Route.RecipeDetail.path.value,
            arguments = listOf(navArgument(Route.RecipeDetail.RECIPE_ID_KEY) {
                type = NavType.StringType
            }),
        ) {
            RecipeDetailScreen(
                onRecipeCloseRequested = onCloseRecipeDetailRequested,
            )
        }
    }
}