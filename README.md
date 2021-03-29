# Spring Boot Kofu

## What
A multi-project sample Spring KoFu application, using:

- Spring KoFu
- Kotlin 1.4.x
- Spring Reactor
- Kotlin Coroutines
- R2DBC
- Postgres
- Liquibase

Built using:

- Gradle 6.8
- Java Platform for dependency management
- Gradle Pre-compiled script plugins

## How

### Setup database
To setup a local development database: `docker-compose -f docker/docker-compose.yaml up -d`

To view database logs: `docker-compose -f docker/docker-compose.yaml logs -f`

To run migrations: `./gradlew :databaseConfig:dev`

### Build application

To build the application: `./gradlew [clean] :application:bootJar`

To run the jar: `java -jar ./application/build/libs/application.jar`
