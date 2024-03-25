plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 24
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
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
    // https://github.com/Kotlin/kotlinx.coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
    // implementation("org.jetbrains.kotlinx:kotlinx.coroutines.flow:$kotlin_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")
    implementation("androidx.core:core-ktx:$kotlin_version")
    implementation("androidx.appcompat:appcompat:$appcompat_version")
    implementation("com.google.android.material:material:$material_version")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")

    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.10")
    implementation("org.seleniumhq.selenium:selenium-java:4.13.0")
    implementation("io.github.bonigarcia:webdrivermanager:5.5.3")

}