plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "com.jthou.pro.crazy"
        minSdk = 24
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        ndk {
            abiFilters += listOf("armeabi-v7a", "arm64-v8a")
        }
        externalNativeBuild {
            cmake {
                arguments("-DANDROID_STL=c++_shared")
            }
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    packagingOptions {
        jniLibs.pickFirsts.add("lib/x86/libc++_shared.so")
        jniLibs.pickFirsts.add("lib/x86_64/libc++_shared.so")
        jniLibs.pickFirsts.add("lib/armeabi-v7a/libc++_shared.so")
        jniLibs.pickFirsts.add("lib/arm64-v8a/libc++_shared.so")
    }

    namespace = "com.jthou.pro.crazy"
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlin_version")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    // https://github.com/Kotlin/kotlinx.coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
    // implementation("org.jetbrains.kotlinx:kotlinx.coroutines.flow:$kotlin_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")

    implementation("androidx.startup:startup-runtime:1.1.1")

    implementation("androidx.databinding:databinding-ktx:7.1.2") {
        exclude(group = "androidx.fragment", module = "fragment")
        exclude(group = "androidx.lifecycle", module = "lifecycle-extensions")
    }

    // Annotation processor
    // kapt("androidx.lifecycle:lifecycle-compiler:$lifecycle_version")
    // optional - ProcessLifecycleOwner provides a lifecycle for the whole application process
    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-process:$lifecycle_version")
    // implementation("androidx.lifecycle:lifecycle-extensions:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    // optional - ProcessLifecycleOwner provides a lifecycle for the whole application process
    implementation("androidx.lifecycle:lifecycle-process:$lifecycle_version")
    // optional - helpers for implementing LifecycleOwner in a Service
    implementation("androidx.lifecycle:lifecycle-service:$lifecycle_version")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    // ViewModel utilities for Compose
    // implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    // 使用 "by viewModels()" 进行创建 ViewModel
    implementation("androidx.activity:activity-ktx:$activity_version") {
        exclude(group = "androidx.fragment", module = "fragment-ktx")
    }
    implementation("androidx.fragment:fragment-ktx:$fragment_version") {
        exclude(group = "androidx.fragment", module = "fragment")
        exclude(group = "androidx.lifecycle", module = "lifecycle-extensions")
    }
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.core:core-splashscreen:1.0.0-beta02")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.1.0")
    implementation("com.google.android.material:material:1.6.0") {
        exclude(group = "androidx.fragment", module = "fragment")
        exclude(group = "androidx.appcompat", module = "appcompat")
    }
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation(project(mapOf("path" to ":fetch")))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    implementation("com.louiscad.splitties:splitties-dimensions:$splitties_version")
    implementation("com.louiscad.splitties:splitties-views:$splitties_version")
    implementation("com.louiscad.splitties:splitties-activities:$splitties_version")

    implementation("com.flyco.tablayout:FlycoTabLayout_Lib:2.1.2@aar")

    implementation("com.blankj:utilcodex:1.29.0")

    implementation("com.scwang.smartrefresh:SmartRefreshLayout:1.1.2")

    implementation("com.google.android:flexbox:2.0.1")

    implementation("com.github.barteksc:android-pdf-viewer:3.2.0-beta.1")

    implementation("com.github.bumptech.glide:glide:4.13.1") {
        exclude(group = "androidx.fragment", module = "fragment")
    }
    kapt("com.github.bumptech.glide:compiler:4.12.0")

    implementation("org.jsoup:jsoup:1.15.2")

    // https://github.com/Tencent/MMKV/blob/master/README_CN.md
    implementation("com.tencent:mmkv:1.2.14")

    // https://developer.android.com/topic/libraries/architecture/datastore
    implementation("androidx.datastore:datastore:1.0.0")

//    // https://developer.android.google.cn/jetpack/androidx/releases/security
//    implementation("androidx.security:security-crypto:1.0.0")
//    // For Identity Credential APIs
//    implementation("androidx.security:security-identity-credential:1.0.0-alpha03")
//    // For App Authentication APIs
//    implementation("androidx.security:security-app-authenticator:1.0.0-alpha02")
//    // For App Authentication API testing
//    androidTestImplementation("androidx.security:security-app-authenticator:1.0.0-alpha02")

    // https://github.com/Tencent/mars#mars_cn
    implementation("com.tencent.mars:mars-xlog:1.2.6")

    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.10")
    // https://github.com/square/retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")

    // https://github.com/markzhai/AndroidPerformanceMonitor
    debugImplementation("com.github.markzhai:blockcanary-android:1.5.0")
    releaseImplementation("com.github.markzhai:blockcanary-no-op:1.5.0")

    // https://github.com/SalomonBrys/ANR-WatchDog
    implementation("com.github.anrwatchdog:anrwatchdog:1.4.0")

    // https://github.com/davemorrissey/subsampling-scale-image-view
    implementation("com.davemorrissey.labs:subsampling-scale-image-view-androidx:3.10.0")

    // https://asm.ow2.io/index.html
    implementation("org.ow2.asm:asm:9.3")
}
