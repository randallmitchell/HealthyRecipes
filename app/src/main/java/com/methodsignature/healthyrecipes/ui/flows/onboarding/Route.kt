package com.methodsignature.healthyrecipes.ui.flows.onboarding

sealed class Route {
    abstract val path: String

    object SplashScreen : Route() {
        override val path: String
            get() = "/splash"
    }
}