plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

kotlin {
    android()
    jvm()
    ios()
    sourceSets {
        val commonMain by getting { dependencies {} }
        val commonTest by getting { dependencies { implementation(kotlin("test")) } }
        val androidMain by getting
        val iosMain by getting
        val jvmMain by getting
        val jvmTest by getting
    }
}

android {
    namespace = "com.dexmatic.shared"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}
