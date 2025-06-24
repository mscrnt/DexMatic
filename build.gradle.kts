buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // bump to AGP 8.6.0 (supports compileSdk 35)
        classpath("com.android.tools.build:gradle:8.6.0")
        // Kotlin Gradle plugin
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${property("kotlinVersion")}")
    }
}

plugins {
    base
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.named<Delete>("clean") {
    delete(layout.buildDirectory)
}
