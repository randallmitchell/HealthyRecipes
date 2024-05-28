package com.methodsignature.healthyrecipes.service

import android.content.Context
import com.methodsignature.healthyrecipes.R
import com.methodsignature.healthyrecipes.service.api.RecipeService
import com.methodsignature.healthyrecipes.value.NonBlankString
import com.methodsignature.healthyrecipes.value.NonBlankStringMoshiAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.addAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    fun provideRecipeService(
        @ApplicationContext context: Context,
        moshi: Moshi,
    ): RecipeService {
        return RawResourceRecipeService(
            context = context,
            R.raw.local_recipes,
            moshi = moshi,
        )
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .add(NonBlankStringMoshiAdapter())
            .build()
    }
}