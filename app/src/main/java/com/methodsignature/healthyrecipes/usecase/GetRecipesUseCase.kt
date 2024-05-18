package com.methodsignature.healthyrecipes.usecase

import com.methodsignature.healthyrecipes.service.api.RecipeService
import com.methodsignature.healthyrecipes.value.NonBlankString
import javax.inject.Inject


class GetRecipesUseCase @Inject constructor(
    private val recipeService: RecipeService,
) {
    data class Recipe(
        val id: NonBlankString,
        val description: NonBlankString,
        val servings: String?,
        val instructions: String?,
        val ingredients: List<Ingredient>,
    )

    data class Ingredient(
        val units: NonBlankString,
        val unitType: NonBlankString,
        val name: NonBlankString,
    )
    suspend fun run() : List<Recipe> {
        return recipeService.getRecipes().toRecipeList()
    }

    private fun List<RecipeService.Recipe>.toRecipeList(): List<Recipe> {
        return map { dao ->
            Recipe(
                id = dao.id,
                description = dao.description,
                servings = dao.servings,
                instructions = dao.instructions,
                ingredients = dao.ingredients.toIngredientList()
            )
        }
    }

    private fun List<RecipeService.Ingredient>.toIngredientList(): List<Ingredient> {
        return map { dao ->
            Ingredient(
                units = dao.units,
                unitType = dao.unitType,
                name = dao.name,
            )
        }
    }
}
