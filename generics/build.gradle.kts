plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    // https://asm.ow2.io/index.html
    implementation("org.ow2.asm:asm:9.3")
    implementation("org.ow2.asm:asm-commons:9.3")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlin_version")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.10")
}
repositories {
    mavenCentral()
}