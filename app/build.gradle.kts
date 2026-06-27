plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)

    // Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:34.15.0"))

// Firebase Authentication
    implementation("com.google.firebase:firebase-auth")

// Cloud Firestore
    implementation("com.google.firebase:firebase-firestore")

// Firebase Realtime Database
    implementation("com.google.firebase:firebase-database")

// Firebase Storage
    implementation("com.google.firebase:firebase-storage")

// Firebase Analytics
    implementation("com.google.firebase:firebase-analytics")

// Firebase Cloud Messaging (Push Notifications)
    implementation("com.google.firebase:firebase-messaging")

// Firebase Remote Config
    implementation("com.google.firebase:firebase-config")

// Google Sign-In
    implementation("com.google.android.gms:play-services-auth:21.4.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}