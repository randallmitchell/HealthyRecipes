package com.methodsignature.healthyrecipes.service.recipe

import com.methodsignature.healthyrecipes.service.api.RecipeService
import com.methodsignature.healthyrecipes.service.errors.EntityNotFoundException
import com.methodsignature.healthyrecipes.service.recipe._models.RealmRecipe
import com.methodsignature.healthyrecipes.service.recipe._models.RealmRecipe.Companion.toRecipe
import com.methodsignature.healthyrecipes.value.EntityId
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmUUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RealmDbRecipeService @Inject constructor(
    private val realm: Realm,
) : RecipeService {

    override suspend fun observeAllRecipes(): Flow<List<RecipeService.Recipe>> {
        return realm.query<RealmRecipe>().find().asFlow().map { resultsChange ->
            resultsChange.list.map {
                it.toRecipe()
            }
        }
    }

    override suspend fun observeRecipe(id: EntityId): Flow<RecipeService.Recipe> {
        return realm.query<RealmRecipe>(
            "_id == $0",
            RealmUUID.from(id.value)
        ).find().asFlow().map { resultsChange ->
            resultsChange.list.firstOrNull()?.toRecipe()
                ?: throw EntityNotFoundException(
                    "Recipe with ID `${id.value}` not found."
                )
        }
    }

    override suspend fun saveRecipe(recipe: RecipeService.Recipe) {
        realm.writeBlocking {
            copyToRealm(
                RealmRecipe.fromRecipe(recipe)
            )
        }
    }

    override suspend fun saveRecipes(withRecipes: List<RecipeService.Recipe>) {
        realm.writeBlocking {
            withRecipes.forEach {
                copyToRealm(
                    RealmRecipe.fromRecipe(it)
                )
            }
        }
    }
}