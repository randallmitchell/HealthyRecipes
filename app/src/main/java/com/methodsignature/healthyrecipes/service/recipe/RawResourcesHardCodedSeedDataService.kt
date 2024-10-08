package com.methodsignature.healthyrecipes.service.recipe

import android.content.Context
import com.methodsignature.healthyrecipes.service._api.recipe.HardCodedSeedDataService
import com.methodsignature.healthyrecipes.service._api.recipe.model.Recipe
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RawResourcesHardCodedSeedDataService @Inject constructor(
    private val context: Context,
    private val recipesResId: Int,
    private val moshi: Moshi,
    private val ioDispatcher: CoroutineDispatcher,
) : HardCodedSeedDataService {

    @JsonClass(generateAdapter = true)
    data class Recipes(
        @field:Json(name = "recipes") val recipes: List<Recipe>
    )

    @OptIn(ExperimentalStdlibApi::class)
    override suspend fun getInitialRecipeList(): List<Recipe> = withContext(ioDispatcher) {
        val rawJson = context.resources.openRawResource(recipesResId).reader().readText()
        moshi.adapter<Recipes>().fromJson(rawJson)?.recipes ?: listOf()
    }
}