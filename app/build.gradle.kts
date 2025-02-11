plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.mgj"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mgj"
        minSdk = 28
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.firebase:firebase-firestore-ktx:25.1.1")
    implementation("com.firebaseui:firebase-ui-firestore:8.0.2")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.activity:activity:1.9.3")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.3.0")
    implementation("com.google.firebase:firebase-firestore:24.10.1")
    implementation(libs.car.ui.lib)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation("com.github.skydoves:progressview:1.1.3")
    implementation ("com.github.orbitalsonic:Water-Wave-Animation-Android:1.0.0")


}