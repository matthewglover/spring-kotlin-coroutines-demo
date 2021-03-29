plugins {
  kotlin("jvm")
  kotlin("kapt")
  `java-library`
  id("spring-boot-app-repositories")
  id("spring-boot-app-code-quality")
}

dependencies {
  kapt(enforcedPlatform(rootProject))
  api(enforcedPlatform(rootProject))
  api(enforcedPlatform(kotlin("bom")))
  implementation(kotlin("stdlib-jdk8"))
  implementation(kotlin("reflect"))
  implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable")
}

tasks {
  compileKotlin {
    kotlinOptions {
      freeCompilerArgs += "-Xuse-experimental=kotlin.Experimental"
      jvmTarget = "1.8"
      useIR = true
      kapt.includeCompileClasspath = false
    }
  }
  compileTestKotlin {
    kotlinOptions {
      freeCompilerArgs += "-Xuse-experimental=kotlin.Experimental"
      jvmTarget = "1.8"
      useIR = true
    }
  }
}

configurations.all {
  exclude(module = "javax.annotation-api")
  exclude(module = "hibernate-validator")
  if (project.hasProperty("graal")) {
    exclude(module = "netty-transport-native-epoll")
    exclude(module = "netty-transport-native-unix-common")
    exclude(module = "netty-codec-http2")
  }
}
