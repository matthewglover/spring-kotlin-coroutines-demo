plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    maven { url = uri("https://repo.spring.io/snapshot") }
    maven { url = uri("https://repo.spring.io/release") }
}

val kotlinVersion: String by project
val springBootVersion: String by project
val detektVersion: String by project
val ktfmtVersion: String by project

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$detektVersion")
    implementation("com.ncorti.ktfmt.gradle:plugin:$ktfmtVersion")
    implementation("org.springframework.experimental.aot:org.springframework.experimental.aot.gradle.plugin:0.10.0")
}
