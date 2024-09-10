package com.methodsignature.healthyrecipes.seed_data

import android.content.Context
import com.methodsignature.healthyrecipes.service.api.HardCodedSeedDataService
import com.methodsignature.healthyrecipes.service.api.Recipe
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import javax.inject.Inject

class RawResourcesHardCodedSeedDataService @Inject constructor(
    private val context: Context,
    private val recipesResId: Int,
    private val moshi: Moshi,
) : HardCodedSeedDataService {

    @JsonClass(generateAdapter = true)
    data class Recipes(
        @field:Json(name = "recipes") val recipes: List<Recipe>
    )

    @OptIn(ExperimentalStdlibApi::class)
    override suspend fun getInitialRecipeList(): List<Recipe> {
        val rawJson = context.resources.openRawResource(recipesResId).reader().readText()
        return moshi.adapter<Recipes>().fromJson(rawJson)?.recipes ?: listOf()
    }
}