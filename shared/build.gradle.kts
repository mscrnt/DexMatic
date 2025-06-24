plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

kotlin {
    android()
    ios()
    sourceSets {
        val commonMain by getting { dependencies {} }
        val androidMain by getting
        val iosMain by getting
    }
}

android {
    namespace = "com.dexmatic.shared"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}
