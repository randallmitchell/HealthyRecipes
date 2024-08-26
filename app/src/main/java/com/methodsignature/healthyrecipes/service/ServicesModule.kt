package com.methodsignature.healthyrecipes.service

import android.content.Context
import com.methodsignature.healthyrecipes.service.api.RecipeService
import com.methodsignature.healthyrecipes.service.recipe.RealmDbRecipeService
import com.methodsignature.healthyrecipes.service.recipe._models.RealmIngredient
import com.methodsignature.healthyrecipes.service.recipe._models.RealmRecipe
import com.methodsignature.healthyrecipes.value.NonBlankStringMoshiAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    fun provideRecipeService(
        @ApplicationContext context: Context,
        realm: Realm,
    ): RecipeService {
        return RealmDbRecipeService(context, realm)
    }

    @Provides
    @Singleton
    fun provideRealm(): Realm {
        return Realm.open(
            RealmConfiguration.create(
                schema = setOf(RealmRecipe::class, RealmIngredient::class)
            )
        )
    }
}