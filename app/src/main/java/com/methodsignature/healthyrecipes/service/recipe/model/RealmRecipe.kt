package com.methodsignature.healthyrecipes.service.recipe.model

import com.methodsignature.healthyrecipes.service.api.recipe.model.Recipe
import com.methodsignature.healthyrecipes.service.errors.MalformedDataException
import com.methodsignature.healthyrecipes.service.recipe.model.RealmIngredient.Companion.toIngredient
import com.methodsignature.healthyrecipes.value.EntityId
import com.methodsignature.healthyrecipes.value.NonBlankString
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmRecipe : RealmObject {

    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var description: String = ""
    var servings: String = ""
    var instructions: String = ""
    var ingredients: RealmList<RealmIngredient> = realmListOf()

    companion object {
        fun fromRecipe(recipe: Recipe): RealmRecipe {
            return RealmRecipe().apply {
                id = recipe.id.value
                name = recipe.name.value
                description = recipe.description.value
                servings = recipe.servings?.value ?: ""
                instructions = recipe.instructions?.value ?: ""
                ingredients = realmListOf(
                    *recipe.ingredients.map {
                        RealmIngredient.fromIngredient(it)
                    }.toTypedArray()
                )
            }
        }

        fun RealmRecipe.toRecipe(): Recipe {
            val rawRecipeId = id
            return Recipe(
                id = EntityId.from(rawRecipeId)
                    ?: throw MalformedDataException("invalid or missing recipe id: `$rawRecipeId`"),
                name = NonBlankString.from(name)
                    ?: throw MalformedDataException("invalid `name`: `$name`"),
                description = NonBlankString.from(description)
                    ?: throw MalformedDataException("invalid `description`: `$description`"),
                servings = NonBlankString.from(servings),
                instructions = NonBlankString.from(instructions),
                ingredients = ingredients.map {
                    it.toIngredient()
                }
            )
        }
    }
}