package com.methodsignature.healthyrecipes.service.api.recipe.model

import com.methodsignature.healthyrecipes.value.NonBlankString

data class Ingredient(
    val units: NonBlankString,
    val unitType: NonBlankString,
    val name: NonBlankString,
)