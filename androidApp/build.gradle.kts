// File: androidApp/build.gradle.kts

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.dexmatic.android"
    compileSdk = 35

    defaultConfig {
        applicationId       = "com.dexmatic.android"
        minSdk              = 24
        targetSdk           = 35
        versionCode         = 1
        versionName         = "1.0"
        multiDexEnabled     = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
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
            ndk {
                debugSymbolLevel = "FULL"
            }
        }
        release {
            isMinifyEnabled = false
        }
    }

    packaging {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

repositories {
    // Must include Google first, so ML Kit artifacts resolve correctly.
    google()
    mavenCentral()
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.activity:activity-compose:1.8.2")

    // Material Components
    implementation("com.google.android.material:material:1.9.0")

    // Compose BOM & core libraries
    implementation(platform("androidx.compose:compose-bom:2025.04.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.ui:ui-tooling-preview")

    // Navigation for Compose
    implementation("androidx.navigation:navigation-compose:2.6.0")

    // ── ML Kit Document Scanner (viewfinder + auto‐crop) ──
    implementation("com.google.android.gms:play-services-mlkit-document-scanner:16.0.0-beta1")

    // ── ML Kit on-device Text Recognition ──
    implementation("com.google.mlkit:text-recognition:16.0.0")

    // Preview tooling
    debugImplementation("androidx.compose.ui:ui-tooling")
}
