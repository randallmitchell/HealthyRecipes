apply(from = "../gradle/remote_services_configs.gradle.kts")

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.google.dagger.hilt.android)
    alias(libs.plugins.newrelic)
    alias(libs.plugins.realm.kotlin)
}

android {
    namespace = "com.methodsignature.healthyrecipes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.methodsignature.healthyrecipes"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("boolean", "ENABLE_DEBUG_LOGGING", "false")
            buildConfigField("boolean", "ENABLE_OBSERVABILITY_LOGGING", "true")
            buildConfigField("String", "NEW_RELIC_API_KEY", rootProject.extra.get("NEW_RELIC_API_KEY").toString())
            buildConfigField("String", "WORDPRESS_API_BASE_URL", rootProject.extra.get("WORDPRESS_API_BASE_URL").toString())
        }
        debug {
            buildConfigField("boolean", "ENABLE_DEBUG_LOGGING", "true")
            buildConfigField("boolean", "ENABLE_OBSERVABILITY_LOGGING", "true")
            buildConfigField("String", "NEW_RELIC_API_KEY", rootProject.extra.get("NEW_RELIC_API_KEY").toString())
            buildConfigField("String", "WORDPRESS_API_BASE_URL", rootProject.extra.get("WORDPRESS_API_BASE_URL").toString())
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.constraint.layout)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.fuel)
    implementation(libs.google.dagger.hilt.android)
    kapt(libs.google.dagger.hilt.android.compiler)
    implementation(libs.newrelic)
    implementation(libs.realm.base)
    implementation(libs.realm.sync)
    implementation(libs.square.moshi.kotlin)
    implementation(libs.timber)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    testImplementation(libs.cash.turbine)
    testImplementation(libs.junit)
    testImplementation(libs.kluent)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
}

kapt {
    correctErrorTypes = true
}