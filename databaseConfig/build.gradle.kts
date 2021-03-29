plugins {
  id("spring-boot-app-library")
  id("org.liquibase.gradle") version "2.0.3"
}

dependencies {
  liquibaseRuntime("org.liquibase:liquibase-core:3.8.1")
  liquibaseRuntime("org.postgresql:postgresql:42.2.5")
  liquibaseRuntime("org.yaml:snakeyaml:1.15")
  liquibaseRuntime("javax.xml.bind:jaxb-api:2.3.1")
}

liquibase {
  activities.register("main") {
    this.arguments = mapOf(
      "logLevel" to "info",
      "changeLogFile" to "liquibase/liquibase-changelog.yaml",
      "url" to "jdbc:postgresql://localhost:5433/devdb",
      "username" to "devdb",
      "password" to "devpassword"
    )
  }
}

tasks.register("dev") {
  // depend on the liquibase status task
  dependsOn("update")
}
