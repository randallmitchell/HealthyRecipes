package com.methodsignature.healthyrecipes.usecase

import com.methodsignature.healthyrecipes.service.api.recipe.LocalRecipeService
import com.methodsignature.healthyrecipes.value.EntityId
import com.methodsignature.healthyrecipes.value.NonBlankString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRecipeDetailUseCase @Inject constructor(
    private val localRecipeService: LocalRecipeService,
) {
    data class Recipe(
        val id: EntityId,
        val name: NonBlankString,
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
        return localRecipeService.observeRecipe(id).map {
            it.toRecipe()
        }
    }

    private fun com.methodsignature.healthyrecipes.service.api.recipe.model.Recipe.toRecipe(): Recipe {
        val dao = this
        return Recipe(
            id = dao.id,
            name = dao.name,
            description = dao.description,
            servings = dao.servings,
            instructions = dao.instructions,
            ingredients = dao.ingredients.toIngredientList()
        )
    }

    private fun List<com.methodsignature.healthyrecipes.service.api.recipe.model.Ingredient>.toIngredientList(): List<Ingredient> {
        return map { dao ->
            Ingredient(
                units = dao.units,
                unitType = dao.unitType,
                name = dao.name,
            )
        }
    }
}