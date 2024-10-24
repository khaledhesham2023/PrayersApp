plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
//    id("androidx.room")


}

android {
    namespace = "com.khaledamin.prayerapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.khaledamin.prayerapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    // For Hilt instrumentation tests
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.compiler)
    // For Hilt local unit tests
    testImplementation(libs.hilt.android.testing)
    kaptTest(libs.hilt.compiler)
    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    // Navigation and Navigation Testing
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    androidTestImplementation(libs.androidx.navigation.testing)
    // Room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    kapt("androidx.room:room-compiler:2.6.1")
    testImplementation(libs.androidx.room.testing)
    // Google Maps
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    // Mockito
    // For Mockito
    testImplementation(libs.mockito.core.v451)
    androidTestImplementation(libs.mockito.android)

// Mockito with Kotlin support (optional, if you are writing Kotlin tests)
    testImplementation(libs.mockito.kotlin)
// fragment testing
    androidTestImplementation(libs.androidx.fragment.testing)
    androidTestImplementation(libs.androidx.navigation.testing)
    testImplementation (libs.assertj.core)




}
kapt {
    correctErrorTypes = true
}