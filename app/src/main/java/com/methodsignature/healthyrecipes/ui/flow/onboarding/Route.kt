package com.methodsignature.healthyrecipes.ui.flow.onboarding

import com.methodsignature.healthyrecipes.language.value.NonBlankString

sealed class Route {
    abstract val path: NonBlankString

    data object SplashScreen : Route() {
        override val path: NonBlankString
            get() = NonBlankString.from("/splash")!!
    }
}