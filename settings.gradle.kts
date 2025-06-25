pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        // Read kotlinVersion from gradle.properties or settings
        val kotlinVersion: String by settings

        // Kotlin plugins
        kotlin("multiplatform") version kotlinVersion
        kotlin("android") version kotlinVersion

        // Android Gradle plugins (match the version you’re using)
        id("com.android.application") version "8.3.0"
        id("com.android.library")     version "8.3.0"
    }
}

rootProject.name = "DexMatic"
include(":shared")
include(":androidApp")
