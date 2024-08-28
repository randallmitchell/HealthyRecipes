package com.methodsignature.healthyrecipes.usecase

import com.methodsignature.healthyrecipes.service.api.RecipeService
import com.methodsignature.healthyrecipes.value.EntityId
import com.methodsignature.healthyrecipes.value.NonBlankString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRecipeDetailUseCase @Inject constructor(
    private val recipeService: RecipeService,
) {
    data class Recipe(
        val id: EntityId,
        val description: NonBlankString,
        val servings: NonBlankString?,
        val instructions: NonBlankString?,
        val ingredients: List<Ingredient>,
    )

    data class Ingredient(
        val units: NonBlankString,
        val unitType: NonBlankString,
        val name: NonBlankString,
    )

    suspend fun observe(id: EntityId): Flow<Recipe> {
        return recipeService.observeRecipe(id).map {
            it.toRecipe()
        }
    }

    private fun RecipeService.Recipe.toRecipe(): Recipe {
        val dao = this
        return Recipe(
            id = dao.id,
            description = dao.description,
            servings = dao.servings,
            instructions = dao.instructions,
            ingredients = dao.ingredients.toIngredientList()
        )
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