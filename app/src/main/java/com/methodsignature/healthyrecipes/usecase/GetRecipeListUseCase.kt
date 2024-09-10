package com.methodsignature.healthyrecipes.usecase

import com.methodsignature.healthyrecipes.service.api.recipe.LocalRecipeService
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
        val name: NonBlankString,
        val description: NonBlankString,
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

    private fun List<com.methodsignature.healthyrecipes.service.api.recipe.model.Recipe>.toRecipeList(): List<Recipe> {
        return map { dao ->
            Recipe(
                id = dao.id,
                name = dao.name,
                description = dao.description,
            )
        }
    }
}
