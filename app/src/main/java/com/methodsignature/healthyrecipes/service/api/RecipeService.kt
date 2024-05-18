package com.methodsignature.healthyrecipes.service.api

import com.methodsignature.healthyrecipes.value.NonEmptyString

interface RecipeService {

    data class Recipe(
        val id: NonEmptyString,
        val description: NonEmptyString,
        val servings: String? = null,
        val instructions: String? = null,
        val ingredients: List<Ingredient>
    )

    data class Ingredient(
        val units: NonEmptyString,
        val unitType: NonEmptyString,
        val name: NonEmptyString
    )

    suspend fun getRecipes(): List<Recipe>
}