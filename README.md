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
- Deploys a Spring Boot application in a Kubernetes cluster using Helm.
- The values for the k8s resources can be defined in different files according to the environment (dev, test, prod).
- The k8s deployment loads application properties from a ConfigMap and sensitive data from a Secret.
- Spring Boot Actuator is enabled to provide health check endpoints.

# Requirements

To run this project, you will need the following tools:

- Java 17
- Docker
- Docker Compose

To install the Helm chart, you will need the following tools:

- A local Kubernetes cluster (e.g. Minikube)
- Helm: https://helm.sh/docs/intro/install/
- Docker (the application image is built and stored in the local Docker registry)

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

This section provides an overview of the implementation details.

## K8s cluster resources overview

The following diagram provides an overview of the k8s resources used to deploy the application in a Kubernetes cluster.

![K8s resources overview](k8s-resources-overview.png)

## Containerize the application

In order to deploy the application in a Kubernetes cluster, we need to containerize the application. The application is containerized using Docker. The [Dockerfile](Dockerfile) is located in the root directory of the project.

## Create the helm chart

The Helm chart is used to deploy the application in a Kubernetes cluster. The Helm chart is located in the [spring-boot-helm](spring-boot-helm) directory.

To create a new Helm chart, execute the following command in the root directory of the project:

```shell
helm create spring-boot-helm
```

To have a better understanding of the Helm chart directory structure, refer to the [Helm documentation](https://helm.sh/docs/topics/charts/).

## Add the Postgresql dependency to the Helm chart

In the [Chart.yaml](spring-boot-helm/Chart.yaml) file, add the Postgresql dependency to the Helm chart:

```yaml
dependencies:
  - name: postgresql
    version: 15.5.27
    repository: https://charts.bitnami.com/bitnami
```

### Add the bitnami repository to local Helm

To add the Bitnami repository to the local Helm, execute the following command:
```shell
helm repo add bitnami https://charts.bitnami.com/bitnami
```

## Liveness and readiness probe

The application is configured with liveness and readiness probes. The liveness probe checks if the application is running, and the readiness probe checks if the application is ready to receive requests.

UPDATE THE IMPLEMENTATION: where do we define the liveness and readiness probes in the Helm chart?

# Deploy the application in a Kubernetes cluster

# Try it out the API

The API can be tested using the Swagger UI or Postman.
- The API documentation is available at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).
- The Postman collection is available here: [spring-boot-helm-rest-api.postman_collection.json](spring-boot-helm-rest-api.postman_collection.json)

If running the application in a Kubernetes cluster, you need to find the external IP of the service to access the API. To do that, execute the following command:

TODO: Add the command to get the external IP of the service