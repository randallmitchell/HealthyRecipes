package com.methodsignature.healthyrecipes.service.recipe

import com.methodsignature.healthyrecipes.language.value.EntityId
import com.methodsignature.healthyrecipes.service._api.errors.EntityNotFoundException
import com.methodsignature.healthyrecipes.service._api.recipe.LocalRecipeService
import com.methodsignature.healthyrecipes.service._api.recipe.model.Recipe
import com.methodsignature.healthyrecipes.service.recipe.model.RealmRecipe
import com.methodsignature.healthyrecipes.service.recipe.model.RealmRecipe.Companion.toRecipe
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RealmDbLocalRecipeService @Inject constructor(
    private val realm: Realm,
    private val ioDispatcher: CoroutineDispatcher,
) : LocalRecipeService {

    override suspend fun observeAllRecipes(): Flow<List<Recipe>> = withContext(ioDispatcher) {
        realm.query<RealmRecipe>().find().asFlow().map { resultsChange ->
            resultsChange.list.map {
                it.toRecipe()
            }
        }
    }

    override suspend fun observeRecipe(id: EntityId): Flow<Recipe> = withContext(ioDispatcher) {
        realm.query<RealmRecipe>(
            "id == $0",
            id.value
        ).find().asFlow().map { resultsChange ->
            resultsChange.list.firstOrNull()?.toRecipe()
                ?: throw EntityNotFoundException(
                    "Recipe with ID `${id.value}` not found."
                )
        }
    }

    override suspend fun upsertRecipe(recipe: Recipe) = withContext(ioDispatcher) {
        realm.writeBlocking {
            copyToRealm(
                RealmRecipe.fromRecipe(recipe),
                UpdatePolicy.ALL
            )
            Unit
        }
    }

    override suspend fun upsertRecipes(withRecipes: List<Recipe>) = withContext(ioDispatcher) {
        realm.writeBlocking {
            withRecipes.forEach {
                copyToRealm(
                    RealmRecipe.fromRecipe(it),
                    UpdatePolicy.ALL
                )
            }
        }
    }

    override suspend fun deleteAllRecipes() = withContext(ioDispatcher) {
        realm.writeBlocking {
            val allRecipes = query<RealmRecipe>().find()
            delete(allRecipes)
        }
    }
}