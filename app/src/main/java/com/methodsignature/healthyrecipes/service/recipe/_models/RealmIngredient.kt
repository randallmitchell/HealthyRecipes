package com.methodsignature.healthyrecipes.service.recipe._models

import com.methodsignature.healthyrecipes.service.api.RecipeService
import com.methodsignature.healthyrecipes.service.errors.MalformedDataException
import com.methodsignature.healthyrecipes.value.NonBlankString
import io.realm.kotlin.types.EmbeddedRealmObject

class RealmIngredient() : EmbeddedRealmObject {

    var units: String = ""
    var unitType: String = ""
    var name: String = ""

    companion object {
        fun fromIngredient(
            ingredient: RecipeService.Ingredient
        ): RealmIngredient {
            return RealmIngredient().apply {
                units = ingredient.units.value
                unitType = ingredient.unitType.value
                name = ingredient.name.value
            }
        }

        fun RealmIngredient.toIngredient() : RecipeService.Ingredient {
            return RecipeService.Ingredient(
                units = NonBlankString.from(units)
                    ?: throw MalformedDataException("invalid `units`: `$units`"),
                unitType = NonBlankString.from(unitType)
                    ?: throw MalformedDataException("invalid `unitType`: `$unitType`"),
                name = NonBlankString.from(name)
                    ?: throw MalformedDataException("invalid `name`: `$name`"),
            )
        }
    }
}