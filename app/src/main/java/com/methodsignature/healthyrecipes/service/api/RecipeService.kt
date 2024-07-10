package com.methodsignature.healthyrecipes.service.api

import com.methodsignature.healthyrecipes.value.EntityId
import com.methodsignature.healthyrecipes.value.NonBlankString

interface RecipeService {

    data class Recipe(
        val id: EntityId,
        val description: NonBlankString,
        val servings: NonBlankString? = null,
        val instructions: NonBlankString? = null,
        val ingredients: List<Ingredient>,
    )

    data class Ingredient(
        val units: NonBlankString,
        val unitType: NonBlankString,
        val name: NonBlankString,
    )

    suspend fun getRecipes(): List<Recipe>

    suspend fun getRecipe(id: EntityId): Recipe
}