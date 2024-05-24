package com.methodsignature.healthyrecipes.ui

sealed class Route {
    abstract val path: String

    data object OnboardingFlow : Route() {
        override val path: String
            get() = "/onboarding"
    }

    data object ContentFlow : Route() {
        override val path: String
            get() = "/content"
    }
}