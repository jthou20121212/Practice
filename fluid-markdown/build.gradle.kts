plugins {
    id("com.android.library")
}

android {
    namespace = "com.fluid.afm"
    compileSdk = 35

    defaultConfig {
        minSdk = 21

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.4.2")
    api(project(":markwon-core"))
    api(project(":markwon-ext-latex"))
    api(project(":markwon-ext-strikethrough"))
    api(project(":markwon-ext-tables"))
    api(project(":markwon-ext-tasklist"))
    api(project(":markwon-image"))
    api(project(":markwon-html"))
    api(project(":markwon-inline-parser"))
    api(project(":markwon-syntax-highlight"))
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.vdurmont:emoji-java:5.1.1")
}
