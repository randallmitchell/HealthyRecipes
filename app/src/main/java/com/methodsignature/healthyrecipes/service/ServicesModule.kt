package com.methodsignature.healthyrecipes.service

import android.content.Context
import com.methodsignature.healthyrecipes.R
import com.methodsignature.healthyrecipes.seed_data.RawResourcesHardCodedSeedDataService
import com.methodsignature.healthyrecipes.service.api.ConfigurationService
import com.methodsignature.healthyrecipes.service.api.HardCodedSeedDataService
import com.methodsignature.healthyrecipes.service.api.RecipeService
import com.methodsignature.healthyrecipes.service.configuration.DataStoreConfigurationService
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
        realm: Realm,
    ): RecipeService {
        return RealmDbRecipeService(realm)
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

    @Provides
    @Singleton
    fun provideConfigurationService(
        @ApplicationContext context: Context,
    ): ConfigurationService {
        return DataStoreConfigurationService(
            context = context,
        )
    }

    @Provides
    fun provideHardCodedSeedDataService(
        @ApplicationContext
        context: Context,
        moshi: Moshi,
    ): HardCodedSeedDataService {
        return RawResourcesHardCodedSeedDataService(
            context = context,
            recipesResId = R.raw.local_recipes,
            moshi = moshi,
        )
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .add(NonBlankStringMoshiAdapter())
            .build()
    }
}