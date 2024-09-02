# Spring Boot Helm project

This is a Spring Boot project that demos the use of Helm to deploy the application in a Kubernetes cluster.

# Contents

- [Features](#features)
- [Requirements](#requirements)
- [Getting Started](#getting-started)
- [Implementation](#implementation)
- [Deploy the application in a Kubernetes cluster](#deploy-the-application-in-a-kubernetes-cluster)
- [Try it out the API](#try-it-out-the-api)

# Features
- Deploy the application in a Kubernetes cluster using Helm
- Uses config maps to store the application configuration
- Uses secrets to store sensitive information
- Uses persistent volumes to store the PostgreSQL data
- Uses persistent volume claims to request storage
- Uses a service to expose the application
- Uses a deployment to manage the application

# Requirements

To run this project, you will need the following tools:

- Java 17
- Docker
- Docker Compose

To install the Helm chart, you will need the following tools:

- A local Kubernetes cluster (e.g. Minikube)
- Docker
- Helm
- kubectl ??

# Getting Started

This section provides a step-by-step guide on how to run the project.

1. Clone the repository by executing the following command:

```shell
git clone git@github.com:andrecaiado/spring-boot-helm.git
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

# Implementation



# Deploy the application in a Kubernetes cluster

# Try it out the API

The API can be tested using the Swagger UI or Postman.
- The API documentation is available at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).
- The Postman collection is available here: [spring-boot-template-rest-api.postman_collection.json](spring-boot-helm-rest-api.postman_collection.json)

If running the application in a Kubernetes cluster, you need to find the external IP of the service to access the API. To do that, execute the following command:

TODO: Add the command to get the external IP of the service