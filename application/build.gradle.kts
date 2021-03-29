plugins {
  id("spring-boot-app-application")
//  id("org.springframework.experimental.aot")
}

dependencies {
  implementation("org.springframework.fu:spring-fu-kofu")
  implementation("org.springframework.data:spring-data-r2dbc")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-log4j2")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

  // Database
  implementation("io.r2dbc:r2dbc-h2")
  implementation("io.r2dbc:r2dbc-postgresql")
}

configurations.all {
//   spring-boot-starter-logging module causes a conflict with spring-boot-starter-log4j2
//   As spring-boot-starter-logging is included by multiple dependencies:
//   spring-boot-starter-webflux, spring-boot-starter-actuator, spring-boot-starter-validation
//   we globally exclude it here, rather than in each dependency
  exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootBuildImage>("bootBuildImage") {
  builder = "paketobuildpacks/builder:tiny"
  environment = mapOf(
    "BP_NATIVE_IMAGE" to "true"
  )
}
