package com.methodsignature.healthyrecipes

import android.app.Application
import com.methodsignature.healthyrecipes.observability.NewRelicObservabilityLogTree
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import timber.log.Timber

@HiltAndroidApp
class HealthyRecipesAndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        runBlocking {
            withContext(Dispatchers.IO) {
                // ensure logging reliability by bypassing app architecture concerns for log initialization
                if (BuildConfig.ENABLE_DEBUG_LOGGING) {
                    Timber.plant(Timber.DebugTree())
                }
                if (BuildConfig.ENABLE_OBSERVABILITY_LOGGING) {
                    Timber.plant(
                        NewRelicObservabilityLogTree.Builder(
                            this@HealthyRecipesAndroidApplication
                        ).build()
                    )
                }
            }
        }
    }
}