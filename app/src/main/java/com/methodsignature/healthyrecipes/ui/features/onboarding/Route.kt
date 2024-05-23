package com.methodsignature.healthyrecipes.ui.features.onboarding

sealed class Route {
    abstract val path: String

    object SplashScreen : Route() {
        override val path: String
            get() = "/splash"
    }
}