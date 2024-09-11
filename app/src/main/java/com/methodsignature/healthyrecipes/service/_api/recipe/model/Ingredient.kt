package com.methodsignature.healthyrecipes.service._api.recipe.model

import com.methodsignature.healthyrecipes.language.value.NonBlankString

data class Ingredient(
    val units: NonBlankString,
    val unitType: NonBlankString,
    val name: NonBlankString,
)