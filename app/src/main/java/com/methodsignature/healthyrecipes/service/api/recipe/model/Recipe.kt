package com.methodsignature.healthyrecipes.service.api.recipe.model

import com.methodsignature.healthyrecipes.service.api.recipe.model.Ingredient
import com.methodsignature.healthyrecipes.value.EntityId
import com.methodsignature.healthyrecipes.value.NonBlankString

data class Recipe(
    val id: EntityId,
    val name: NonBlankString,
    val description: NonBlankString,
    val servings: NonBlankString? = null,
    val instructions: NonBlankString? = null,
    val ingredients: List<Ingredient>,
)