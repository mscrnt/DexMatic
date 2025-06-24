pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        val kotlinVersion: String by settings
        id("org.jetbrains.kotlin.android") version kotlinVersion
        id("org.jetbrains.kotlin.multiplatform") version kotlinVersion
        id("com.android.application") version "8.2.2"
        id("com.android.library") version "8.2.2"
    }
}

rootProject.name = "DexMatic"
include(":shared")
include(":androidApp")
