package com.methodsignature.healthyrecipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.methodsignature.healthyrecipes.ui.features.onboarding.OnboardingFlow
import com.methodsignature.healthyrecipes.ui.theme.HealthyRecipesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HealthyRecipesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    OnboardingFlow(
                        modifier = Modifier.padding(innerPadding),
                        onOnboardingComplete = {
                            TODO("Navigate out of onboarding flow.")
                        }
                    )
                }
            }
        }
    }
}