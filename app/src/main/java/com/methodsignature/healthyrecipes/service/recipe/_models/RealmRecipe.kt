package com.methodsignature.healthyrecipes.service.recipe._models

import com.methodsignature.healthyrecipes.service.api.RecipeService
import com.methodsignature.healthyrecipes.service.errors.MalformedDataException
import com.methodsignature.healthyrecipes.service.recipe._models.RealmIngredient.Companion.toIngredient
import com.methodsignature.healthyrecipes.value.EntityId
import com.methodsignature.healthyrecipes.value.NonBlankString
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmRecipe : RealmObject {

    @PrimaryKey
    var _id: RealmUUID = RealmUUID.random()

    var description: String = ""
    var servings: String = ""
    var instructions: String = ""
    var ingredients: RealmList<RealmIngredient> = realmListOf()

    companion object {
        fun fromRecipe(recipe: RecipeService.Recipe): RealmRecipe {
            return RealmRecipe().apply {
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

        fun RealmRecipe.toRecipe(): RecipeService.Recipe {
            val rawRecipeId = _id.toString()
            return RecipeService.Recipe(
                id = EntityId.from(rawRecipeId)
                    ?: throw MalformedDataException("invalid entity id: `$rawRecipeId`"),
                description = NonBlankString.from(description)
                    ?: throw MalformedDataException("invalid `description`: `$description`"),
                servings = NonBlankString.from(servings),
                instructions = NonBlankString.from(description),
                ingredients = ingredients.map {
                    it.toIngredient()
                }
            )
        }
    }
}