package com.methodsignature.healthyrecipes.ui.animation

import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith

val slideInFromBottom = slideInVertically { height -> height } togetherWith
        slideOutVertically { height -> -height }

val slideOutPastBottom = slideInVertically { height -> -height } togetherWith
        slideOutVertically { height -> height }