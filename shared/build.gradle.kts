// File: shared/build.gradle.kts

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    // Android target using new DSL
    androidTarget {
        publishAllLibraryVariants()
    }

    // JVM target
    jvm()

    // iOS targets
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    // Apply default Apple hierarchy template
    applyDefaultHierarchyTemplate()

    sourceSets {
        val commonMain by getting {
            dependencies {
                // commonMain dependencies
            }
        }
        val commonTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test")
            }
        }

        val androidMain by getting {
            dependencies {
                // androidMain dependencies
            }
        }

        val jvmMain by getting {
            dependencies {
                // jvmMain dependencies
            }
        }
        val jvmTest by getting

        val iosMain by getting {
            dependencies {
                // iosMain dependencies
            }
        }
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
