package com.methodsignature.healthyrecipes.service.recipe

import android.content.Context
import com.methodsignature.healthyrecipes.service.api.RecipeService
import com.methodsignature.healthyrecipes.service.errors.EntityNotFoundException
import com.methodsignature.healthyrecipes.service.recipe._models.RealmRecipe
import com.methodsignature.healthyrecipes.service.recipe._models.RealmRecipe.Companion.toRecipe
import com.methodsignature.healthyrecipes.value.EntityId
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import javax.inject.Inject

class RealmDbRecipeService @Inject constructor(
    private val context: Context,
    private val realm: Realm,
) : RecipeService {

    override suspend fun getRecipes(): List<RecipeService.Recipe> {
        return realm.query<RealmRecipe>().find().map {
            it.toRecipe()
        }
    }

    override suspend fun getRecipe(id: EntityId): RecipeService.Recipe {
        val result: RealmResults<RealmRecipe> = realm.query<RealmRecipe>(
            "_id == $0",
            id.value
        ).find()
        val rawResult = result.firstOrNull()
            ?: throw EntityNotFoundException(
                "Recipe with ID `${id.value}` not found."
            )
        return rawResult.toRecipe()
    }

    override suspend fun saveRecipe(recipe: RecipeService.Recipe) {
        realm.writeBlocking {
            copyToRealm(
                RealmRecipe.fromRecipe(recipe)
            )
        }
    }
}