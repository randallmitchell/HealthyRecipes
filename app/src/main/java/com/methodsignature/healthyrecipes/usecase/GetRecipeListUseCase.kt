package com.methodsignature.healthyrecipes.usecase

import com.methodsignature.healthyrecipes.service.api.LocalRecipeService
import com.methodsignature.healthyrecipes.value.EntityId
import com.methodsignature.healthyrecipes.value.NonBlankString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRecipeListUseCase @Inject constructor(
    private val recipeService: LocalRecipeService,
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

    suspend fun observe(): Flow<List<Recipe>> {
        return recipeService.observeAllRecipes().map {
            it.toRecipeList()
        }
    }

    private fun List<com.methodsignature.healthyrecipes.service.api.Recipe>.toRecipeList(): List<Recipe> {
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

    private fun List<com.methodsignature.healthyrecipes.service.api.Ingredient>.toIngredientList(): List<Ingredient> {
        return map { dao ->
            Ingredient(
                units = dao.units,
                unitType = dao.unitType,
                name = dao.name,
            )
        }
    }
}
