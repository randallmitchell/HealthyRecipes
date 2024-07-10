package com.methodsignature.healthyrecipes.ui.flows.onboarding

import com.methodsignature.healthyrecipes.value.NonBlankString

sealed class Route {
    abstract val path: NonBlankString

    data object SplashScreen : Route() {
        override val path: NonBlankString
            get() = NonBlankString.from("/splash")!!
    }
}