package com.methodsignature.healthyrecipes.service

import android.content.Context
import com.methodsignature.healthyrecipes.service.api.RecipeService
import com.methodsignature.healthyrecipes.service.api.RecipeService.Recipe
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RawResourceRecipeService @Inject constructor(
    private val context: Context,
    private val recipesResId: Int,
    private val moshi: Moshi
) : RecipeService {
    @OptIn(ExperimentalStdlibApi::class)
    override suspend fun getRecipes(): List<Recipe> = withContext(Dispatchers.IO) {
        val rawJson = context.resources.openRawResource(recipesResId).reader().readText()
        val adapter = moshi.adapter<List<Recipe>>()
        adapter.fromJson(rawJson) ?: listOf()
    }
}
