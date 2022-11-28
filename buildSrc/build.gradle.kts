repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}

plugins {
    `kotlin-dsl`
}

dependencies {
    val agpVersion = "7.2.2"
    val kotlinVersion = "1.7.10"
    implementation("com.android.tools.build:gradle:${agpVersion}")
    implementation("com.android.tools.build:gradle-api:${agpVersion}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin-api:${kotlinVersion}")
    // https://asm.ow2.io/index.html
    implementation("org.ow2.asm:asm:9.3")
    implementation("org.ow2.asm:asm-tree:9.3")
    implementation("org.ow2.asm:asm-commons:9.3")
    implementation("commons-io:commons-io:2.6")
}