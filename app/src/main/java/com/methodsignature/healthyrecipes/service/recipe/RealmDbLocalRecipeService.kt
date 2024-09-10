package com.methodsignature.healthyrecipes.service.recipe

import com.methodsignature.healthyrecipes.service.api.recipe.LocalRecipeService
import com.methodsignature.healthyrecipes.service.api.recipe.model.Recipe
import com.methodsignature.healthyrecipes.service.errors.EntityNotFoundException
import com.methodsignature.healthyrecipes.service.recipe.model.RealmRecipe
import com.methodsignature.healthyrecipes.service.recipe.model.RealmRecipe.Companion.toRecipe
import com.methodsignature.healthyrecipes.value.EntityId
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RealmDbLocalRecipeService @Inject constructor(
    private val realm: Realm,
) : LocalRecipeService {

    override suspend fun observeAllRecipes(): Flow<List<Recipe>> {
        return realm.query<RealmRecipe>().find().asFlow().map { resultsChange ->
            resultsChange.list.map {
                it.toRecipe()
            }
        }
    }

    override suspend fun observeRecipe(id: EntityId): Flow<Recipe> {
        return realm.query<RealmRecipe>(
            "id == $0",
            id.value
        ).find().asFlow().map { resultsChange ->
            resultsChange.list.firstOrNull()?.toRecipe()
                ?: throw EntityNotFoundException(
                    "Recipe with ID `${id.value}` not found."
                )
        }
    }

    override suspend fun upsertRecipe(recipe: Recipe) {
        realm.writeBlocking {
            copyToRealm(
                RealmRecipe.fromRecipe(recipe),
                UpdatePolicy.ALL
            )
        }
    }

    override suspend fun upsertRecipes(withRecipes: List<Recipe>) {
        realm.writeBlocking {
            withRecipes.forEach {
                copyToRealm(
                    RealmRecipe.fromRecipe(it),
                    UpdatePolicy.ALL
                )
            }
        }
    }

    override suspend fun deleteAllRecipes() {
        realm.writeBlocking {
            val allRecipes = query<RealmRecipe>().find()
            delete(allRecipes)
        }
    }
}