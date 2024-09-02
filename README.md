# Spring Boot Template project
This is a template project for Spring Boot applications. 

# Contents

- [Features](#features)
- [Requirements](#requirements)
- [Getting Started](#getting-started)
  - [Installation](#installation)
  - [Try it out with the postman collection](#try-it-out-with-the-postman-collection)
- [PostgreSQL database](#postgresql-database)
  - [Database migrations with Flyway](#database-migrations-with-flyway)
- [Spring Boot Docker Compose](#spring-boot-docker-compose)
- [In-memory database for testing](#in-memory-database-for-testing)
- [Running the application](#running-the-application)

# Features
- Exposes an API to perform CRUD operations on a single entity
- Persists the data in a PostgreSQL database
- Uses Spring Data JPA for database operations
- Uses Flyway for database migrations
- Uses Spring Boot Docker Compose to start and stop a Docker container running the PostgreSQL database
- Includes a datasource configuration for testing purposes that uses the H2 in-memory database

# Requirements

- Java 17
- Docker
- Docker Compose

# Getting Started

This section provides a step-by-step guide on how to run the project.

## Installation

1. Clone the repository by executing the following command:

```shell
git clone git@github.com:andrecaiado/spring-boot-template.git
```

2. Navigate into the project directory:

```
cd your-repository-name
```

3. Install the dependencies by executing the following command:

```shell
./mvnw clean install
```

4. Run the application by executing the following command:

```shell 
./mvnw spring-boot:run
```

## Try it out with the postman collection

The Postman collection is available here: [spring-boot-template-rest-api.postman_collection.json](spring-boot-template-rest-api.postman_collection.json)


# PostgreSQL database
The PostrgreSQL configuration is located in `src/main/resources/application.yaml`. 

The datasource configuration is located in the `docker-compose.yml` file so it can be picked up by Spring Boot Docker Compose.

## Database migrations with Flyway
Flyway configuration is located in `src/main/resources/application.yaml`.

The migration files are located at `src/main/resources/db/migration`.

# Spring Boot Docker Compose
With Spring Boot Docker Compose, the container running the PostgreSQL database will be automatically started when the application is started and stopped when the application is stopped.

Spring Boot Docker Compose will detect and use the `docker-compose.yml` file located in the root of the project. 

The data source will be configured with the properties defined in the `docker-compose.yml` file.

# In-memory database for testing
The datasource configuration is located in `src/test/resources/application.yaml`.

When a test loads the application context or explicitly calls this configuration, the H2 in-memory database will be initialized.

The migrations will be applied from the files located at `src/main/resources/db/migration`. To apply a different migration to the test database, create the migration files in the `scr/test/resources` and specify the configuration in the `scr/test/resources/application.yaml` file.

```yaml
spring:
  application:
      flyway:
        locations: classpath:/db/migration
        schemas: employees
        baselineOnMigrate: true
        enabled: true
```
