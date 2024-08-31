package com.methodsignature.healthyrecipes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class HealthyRecipesAndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // ensure logging reliability by bypassing app architecture concerns for log initialization
        if (BuildConfig.ENABLE_DEBUG_LOGGING) {
            Timber.plant(Timber.DebugTree())
        }
        // TODO implement New Relic log tree
        // if (BuildConfig.ENABLE_OBSERVABILITY_LOGGING) {
        //     Timber.plant(ObservabilityLoggingTree)
        // }
    }
}