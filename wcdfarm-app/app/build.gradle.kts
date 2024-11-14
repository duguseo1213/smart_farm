plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    kotlin("kapt")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.wcd.farm"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.wcd.farm"
        minSdk = 26
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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.firebase.messaging)
    val composeBom = platform("androidx.compose:compose-bom:2024.09.03")
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation("androidx.work:work-runtime-ktx:2.9.1")

    //ui
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    //preview
    implementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.tooling.preview)

    //di
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    //mvi framework
    implementation(libs.mavericks)

    //remote
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation("com.squareup.retrofit2:converter-scalars:2.11.0")
    implementation("com.squareup.okhttp3:okhttp-urlconnection:4.12.0")

    implementation("org.locationtech.proj4j:proj4j:1.3.0")
    implementation("org.locationtech.proj4j:proj4j-epsg:1.3.0")

    //kakao login
    implementation("com.kakao.sdk:v2-all:2.20.6")

    implementation("com.airbnb.android:mavericks:3.0.9")
    implementation("com.airbnb.android:mavericks-compose:3.0.9")
    implementation("com.airbnb.android:mavericks-hilt:3.0.9")

    implementation("androidx.compose.material:material-icons-extended-android")

    // chart
    implementation ("io.github.ehsannarmani:compose-charts:0.0.14")

    // shadow
    implementation("com.github.GIGAMOLE:ComposeShadowsPlus:1.0.4")

    //camera
    implementation("androidx.camera:camera-camera2:1.4.0")
    implementation("androidx.camera:camera-core:1.4.0")
    implementation("androidx.camera:camera-lifecycle:1.4.0")
    implementation("androidx.camera:camera-view:1.4.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")

    //permission
    implementation("io.github.ParkSangGwon:tedpermission-normal:3.4.2")
    implementation("io.github.ParkSangGwon:tedpermission-coroutine:3.4.2")

    //lottie
    implementation ("com.airbnb.android:lottie-compose:6.0.0")

    //image loading
    implementation("io.coil-kt.coil3:coil-compose:3.0.2")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

kapt {
    correctErrorTypes = true
}