buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id ("com.google.dagger.hilt.android") version "2.52" apply false
    id ("androidx.room") version "2.6.1" apply false
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin) apply false


}