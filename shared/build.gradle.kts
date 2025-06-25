// File: shared/build.gradle.kts

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    androidTarget {
        publishAllLibraryVariants()
    }
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    applyDefaultHierarchyTemplate()

    sourceSets {
        val commonMain by getting {
            dependencies {
                // commonMain dependencies
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                // Document Scanner UI + auto-crop flow (dynamic, Play Services)
                implementation("com.google.android.gms:play-services-mlkit-document-scanner:16.0.0-beta1")
                // Dynamic OCR engine (downloaded at runtime from Play Services)
                implementation("com.google.android.gms:play-services-mlkit-text-recognition:19.0.1")
                // Kotlin coroutines integration for Tasks.await()
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")
            }
        }

        val jvmMain by getting
        val jvmTest by getting
        val iosMain by getting
        val iosTest by getting
    }
}

android {
    namespace = "com.dexmatic.shared"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

// Ensure all Kotlin compilations target Java 1.8
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
}

repositories {
    google()
    mavenCentral()
}
