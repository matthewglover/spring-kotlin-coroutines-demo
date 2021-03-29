import io.gitlab.arturbosch.detekt.Detekt

plugins {
    `java-platform`
    id("io.gitlab.arturbosch.detekt")
}

group = "com.matthewglover.springboot.demo"

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

val gradleVersion: String by project
val kotlinVersion: String by project
val kotlinXImmutableCollectionsVersion: String by project
val openApiWebFluxUIVersion: String by project
val r2dbcVersion: String by project
val springBootVersion: String by project
val springKofuVersion: String by project

tasks.withType<Wrapper> { gradleVersion = gradleVersion }

javaPlatform { allowDependencies() }

dependencies {
    constraints {
        api("org.springdoc:springdoc-openapi-webflux-ui:$openApiWebFluxUIVersion")
        api("org.springframework.fu:spring-fu-kofu:$springKofuVersion")
        api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinVersion")
        api("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$kotlinVersion")
        api("org.jetbrains.kotlinx:kotlinx-collections-immutable:$kotlinXImmutableCollectionsVersion")
    }

    api(enforcedPlatform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))
    api(enforcedPlatform("io.r2dbc:r2dbc-bom:$r2dbcVersion"))
}

tasks.register<Detekt>("detektAll") {
    parallel = true
    buildUponDefaultConfig = true
    setSource(files(projectDir))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
    config.setFrom(files("$rootDir/config/detekt.yaml"))
    reports {
        xml.enabled = false
    }
}
