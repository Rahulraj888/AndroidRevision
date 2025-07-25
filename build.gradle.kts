buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Safe Args Gradle plugin
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.6.0")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}