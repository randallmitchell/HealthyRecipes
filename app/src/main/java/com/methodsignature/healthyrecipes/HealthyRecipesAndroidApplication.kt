package com.methodsignature.healthyrecipes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.mongodb.kbson.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class HealthyRecipesAndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            TODO("Plant crash reporting tree")
        }
    }
}