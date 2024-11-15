plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    kotlin("kapt")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
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

    val composeBom = platform("androidx.compose:compose-bom:2024.09.03")
    implementation(composeBom)
    androidTestImplementation(composeBom)

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

    //
    /*implementation("com.google.android.exoplayer:exoplayer:2.19.1")
    implementation("com.google.android.exoplayer:exoplayer-core:2.19.1")
    implementation("com.google.android.exoplayer:exoplayer-dash:2.19.1")
    implementation("com.google.android.exoplayer:exoplayer-ui:2.19.1")
*/
    implementation("androidx.media3:media3-exoplayer:1.4.1")
    implementation("androidx.media3:media3-ui:1.4.1")
    implementation("androidx.media3:media3-exoplayer-rtsp:1.4.1")
    implementation("androidx.media3:media3-datasource-rtmp:1.4.1")
    implementation("androidx.media3:media3-exoplayer-hls:1.4.1")

    implementation("org.videolan.android:libvlc-all:3.5.1")

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