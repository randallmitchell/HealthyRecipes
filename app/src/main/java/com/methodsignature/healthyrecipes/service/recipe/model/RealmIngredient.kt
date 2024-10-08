package com.methodsignature.healthyrecipes.service.recipe.model

import com.methodsignature.healthyrecipes.service._api.recipe.model.Ingredient
import com.methodsignature.healthyrecipes.service._api.errors.MalformedDataException
import com.methodsignature.healthyrecipes.language.value.NonBlankString
import io.realm.kotlin.types.EmbeddedRealmObject

class RealmIngredient() : EmbeddedRealmObject {

    var units: String = ""
    var unitType: String = ""
    var name: String = ""

    companion object {
        fun fromIngredient(
            ingredient: Ingredient
        ): RealmIngredient {
            return RealmIngredient().apply {
                units = ingredient.units.value
                unitType = ingredient.unitType.value
                name = ingredient.name.value
            }
        }

        fun RealmIngredient.toIngredient() : Ingredient {
            return Ingredient(
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