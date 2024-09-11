package com.methodsignature.healthyrecipes.service._api.recipe.model

import com.methodsignature.healthyrecipes.language.value.EntityId
import com.methodsignature.healthyrecipes.language.value.NonBlankString

data class Recipe(
    val id: EntityId,
    val name: NonBlankString,
    val description: NonBlankString,
    val servings: NonBlankString? = null,
    val instructions: NonBlankString? = null,
    val ingredients: List<Ingredient>,
)