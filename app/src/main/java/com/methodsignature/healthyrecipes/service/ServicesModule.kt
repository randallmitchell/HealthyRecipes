package com.methodsignature.healthyrecipes.service

import android.content.Context
import com.methodsignature.healthyrecipes.BuildConfig
import com.methodsignature.healthyrecipes.R
import com.methodsignature.healthyrecipes.language.IoDispatcher
import com.methodsignature.healthyrecipes.language.value.NonBlankString
import com.methodsignature.healthyrecipes.language.value.NonBlankStringMoshiAdapter
import com.methodsignature.healthyrecipes.service._api.configuration.ConfigurationService
import com.methodsignature.healthyrecipes.service._api.recipe.HardCodedSeedDataService
import com.methodsignature.healthyrecipes.service._api.recipe.LocalRecipeService
import com.methodsignature.healthyrecipes.service._api.recipe.RemoteRecipeService
import com.methodsignature.healthyrecipes.service.configuration.DataStoreConfigurationService
import com.methodsignature.healthyrecipes.service.recipe.RawResourcesHardCodedSeedDataService
import com.methodsignature.healthyrecipes.service.recipe.RealmDbLocalRecipeService
import com.methodsignature.healthyrecipes.service.recipe.WordpressRemoteRecipeService
import com.methodsignature.healthyrecipes.service.recipe.model.RealmIngredient
import com.methodsignature.healthyrecipes.service.recipe.model.RealmRecipe
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fuel.FuelBuilder
import fuel.HttpLoader
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun provideRecipeService(
        realm: Realm,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): LocalRecipeService {
        return RealmDbLocalRecipeService(
            realm,
            ioDispatcher,
        )
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
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): ConfigurationService {
        return DataStoreConfigurationService(
            context = context,
            ioDispatcher = ioDispatcher,
        )
    }

    @Provides
    fun provideHardCodedSeedDataService(
        @ApplicationContext context: Context,
        moshi: Moshi,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): HardCodedSeedDataService {
        return RawResourcesHardCodedSeedDataService(
            context = context,
            recipesResId = R.raw.local_recipes,
            moshi = moshi,
            ioDispatcher = ioDispatcher,
        )
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .add(NonBlankStringMoshiAdapter())
            .build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Provides
    fun provideFuel(okHttpClient: OkHttpClient): HttpLoader {
        return FuelBuilder().config(okHttpClient).build()
    }

    @Provides
    fun provideRemoteRecipeService(
        fuel: HttpLoader,
        moshi: Moshi,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): RemoteRecipeService {
        return WordpressRemoteRecipeService(
            fuel = fuel,
            moshi = moshi,
            baseServiceUrl = NonBlankString.from(BuildConfig.WORDPRESS_API_BASE_URL)!!,
            ioDispatcher = ioDispatcher,
        )
    }
}