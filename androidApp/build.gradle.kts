// File: androidApp/build.gradle.kts

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.dexmatic.android"
    compileSdk = 35

    defaultConfig {
        applicationId     = "com.dexmatic.android"
        minSdk            = 24
        targetSdk         = 35
        versionCode       = 1
        versionName       = "1.0"
        multiDexEnabled   = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        // Compiler extension matching our BOM
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildTypes {
        debug {
            // Include full native debug symbols in debug builds
            ndk {
                debugSymbolLevel = "FULL"  // options: "FULL" or "SYMBOL_TABLE"
            }
        }
        release {
            // Your release config here…
            isMinifyEnabled = false
        }
    }

    packaging {
        // Exclude license files
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.activity:activity-compose:1.8.2")

    // Material Components for Android (styles, themes)
    implementation("com.google.android.material:material:1.9.0")

    // Compose BOM for consistent Compose library versions
    implementation(platform("androidx.compose:compose-bom:2025.04.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.ui:ui-tooling-preview")

    // Navigation for Compose
    implementation("androidx.navigation:navigation-compose:2.6.0")

    // ML Kit Text Recognition
    implementation("com.google.mlkit:text-recognition:16.0.0")

    // Tooling support (Previews, @Preview, etc.)
    debugImplementation("androidx.compose.ui:ui-tooling")
}
