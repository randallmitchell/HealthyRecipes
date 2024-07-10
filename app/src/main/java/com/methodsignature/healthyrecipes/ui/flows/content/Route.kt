package com.methodsignature.healthyrecipes.ui.flows.content

import com.methodsignature.healthyrecipes.value.EntityId
import com.methodsignature.healthyrecipes.value.NonBlankString

sealed class Route {
    abstract val path: NonBlankString

    data object RecipeList : Route() {
        override val path: NonBlankString
            get() = NonBlankString.from("/recipes")!!
    }

    data object RecipeDetail : Route() {
        const val RECIPE_ID_KEY = "recipeId"
        override val path: NonBlankString
            get() = NonBlankString.from("/recipes/{$RECIPE_ID_KEY}")!!
    }

    companion object {
        fun createRecipeDetailRoute(fromRecipeId: EntityId) : String {
            return RecipeDetail.path.value.replace("{${Route.RecipeDetail.RECIPE_ID_KEY}}", fromRecipeId.value)
        }
    }
}