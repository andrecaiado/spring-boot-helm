# Spring Boot Helm project

This is a Spring Boot project that demos the use of Helm to deploy the application in a Kubernetes cluster.

# Contents

- [Features](#features)
- [Requirements](#requirements)
  - [Running the project for development purposes](#running-the-project-for-development-purposes)
  - [Installing the Helm chart in a Kubernetes cluster](#installing-the-helm-chart-in-a-kubernetes-cluster)
- [Getting Started](#getting-started)
- [Implementation](#implementation)
  - [K8s cluster resources overview](#k8s-cluster-resources-overview)
  - [Pre-requisites](#pre-requisites)
    - [Containerize the application](#containerize-the-application)
    - [Install the required tools](#install-the-required-tools)
    - [Add the bitnami repository to local Helm](#add-the-bitnami-repository-to-local-helm)
  - [Create the helm chart](#create-the-helm-chart)
  - [Update the Helm chart](#update-the-helm-chart)
    - [Delete the default resource manifest files](#delete-the-default-resource-manifest-files)
    - [Add the Postgresql dependency to the Helm chart](#add-the-postgresql-dependency-to-the-helm-chart)
    - [Create the resource manifest files](#create-the-resource-manifest-files)
      - [ConfigMap](#configmap)
      - [Deployment](#deployment)
      - [Service](#service)
    - [Update the values.yaml file](#update-the-valuesyaml-file)
- [Install the Helm chart](#install-the-helm-chart)
- [Uninstall the Helm chart](#uninstall-the-helm-chart)
- [Access the application](#access-the-application)
- [Try it out the application API](#try-it-out-the-application-api)

# Features
- Deploys a Spring Boot application in a Kubernetes cluster using Helm.
- The values for the k8s resources can be defined in different files according to the environment (dev, test, prod).
- The k8s deployment loads application properties from a ConfigMap and sensitive data from a Secret.
- Spring Boot Actuator is enabled to provide health check endpoints.

# Requirements

This section provides a list of requirements for different purposes.

## Running the project for development purposes

To run this project, you will need the following tools:

- Java 17
- Docker
- Docker Compose

## Installing the Helm chart in a Kubernetes cluster

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

These resources will be created when the Helm chart is installed in the Kubernetes cluster.

## Pre-requisites

This section describes some pre-requisites for the Helm chart creation and installation.

### Containerize the application

In order to deploy the application in a Kubernetes cluster, we need to containerize the application. The application is containerized using Docker. The [Dockerfile](Dockerfile) is located in the root directory of the project.

In this project, we are not pushing the Docker image to a Docker registry. Instead, we are building the Docker image and storing it in the local Docker registry. This approach is useful for development purposes.

### Install the required tools

Make sure you have installed the tools mentioned in the [Installing the Helm chart in a Kubernetes cluster](#installing-the-helm-chart-in-a-kubernetes-cluster) section.

### Add the bitnami repository to local Helm

The Helm chart will have a dependency on the Bitnami Postgresql chart, for that, we have to add the Bitnami repository to the local Helm.

To add the Bitnami repository to the local Helm, execute the following command:
```shell
helm repo add bitnami https://charts.bitnami.com/bitnami
```

## Create the helm chart

The Helm chart contains all the k8s resources manifest files that will be used by Helm to create the resources in the Kubernetes cluster. 

To create a new Helm chart, execute the following command in the root directory of the project:

```shell
helm create spring-boot-helm
```

This command will generate the Helm chart directory structure. In this project, the Helm chart is located in the [spring-boot-helm](spring-boot-helm) directory.

To have a better understanding of the Helm chart directory structure, refer to the [Helm documentation](https://helm.sh/docs/topics/charts/).

## Update the Helm chart

After creating the Helm chart using the `helm create` command, we need to update the Helm chart according to our project requirements.

### Delete the default resource manifest files

The first step is to delete all the files in the `templates` directory, as we will create new files according to those requirements.

### Add the Postgresql dependency to the Helm chart

In the [Chart.yaml](spring-boot-helm/Chart.yaml) file, add the Postgresql dependency to the Helm chart:

```yaml
dependencies:
  - name: postgresql
    version: 15.5.27
    repository: https://charts.bitnami.com/bitnami
```

### Create the resource manifest files

Create the following resource manifest files in the `templates` directory:

- ConfigMap: [configmap.yaml](spring-boot-helm/templates/configmap.yaml)
- Deployment: [deployment.yaml](spring-boot-helm/templates/deployment.yaml)
- Service: [service.yaml](spring-boot-helm/templates/service.yaml)

#### ConfigMap

The config map will contain properties that will be loaded into the Spring Boot application. These properties are mostly the database connection properties. 

#### Deployment

The deployment manifest will create a deployment in the Kubernetes cluster. The deployment will load the application properties from the ConfigMap and sensitive data from the Secret and make them available to the application as environment variables.

As it was mentioned in the [Containerize the application](#containerize-the-application) section, the application Docker image is not pushed to a Docker registry. Instead, the Docker image is built and stored in the local Docker registry. For this, the `imagePullPolicy` is set to `Never` in the deployment manifest.

#### Service

The service manifest will create a service in the Kubernetes cluster. The service will expose the application, so we can access it from outside the cluster.

### Update the values.yaml file

The above-mentioned resource manifest files will have placeholders for the values that will be defined in the [values.yaml](spring-boot-helm%2Fvalues.yaml) file. This approach has the following advantages:

- It allows us to define the values in a single file and use them in the resource manifest files.
- The values defined in this file are considered default values and can be overridden by  values defined in the environment-specific values files (e.g., values-test.yaml, values-staging, values-prod.yaml).

**Note:**

Values for the Postgres dependency helm chart will also be defined in the values.yaml file. The placeholder values for the Postgres dependency are well-defined, so we must use them as they are.
For more details about the Postgres dependency values, refer to the [Bitnami Postgresql chart documentation](https://artifacthub.io/packages/helm/bitnami/postgresql?modal=values)

# Install the Helm chart

This section provides a step-by-step guide on how to install the Helm chart in a Kubernetes cluster.

**Note:**

Make sure you have built the application Docker image and tag it according to the image name defined in the Helm chart [values.yaml](spring-boot-helm%2Fvalues.yaml) file. Also make sure that Docker is running and the image is available in the local Docker registry.

To install the Helm chart, execute the following command in the root directory of the project:

Install the Helm chart:
```shell
helm install spring-boot-helm ./spring-boot-helm
```

To check the status of the Helm release, execute the following command:
```shell
helm list
```

The output will be similar to the following:
![helm-list.png](src%2Fmain%2Fresources%2Fhelm-list.png)

To check the status of the k8s resources created by the Helm chart, execute the following command:
```shell
kubectl get all
```

The output will be similar to the following:
![k-get-all.png](src%2Fmain%2Fresources%2Fk-get-all.png)

To check the logs of the application pod, execute the following command:
```shell
kubectl logs <pod-name>
```

# Uninstall the Helm chart

To uninstall the Helm chart, execute the following command:
```shell
helm uninstall spring-boot-helm
```

# Access the application

If you are using Minikube (like in this project), in order to get the external IP of the service, you need to expose the service using the `minikube service` command.

If you need to get the service name you can also use the kubectl command:
```shell
kubectl get svc
```

And them use the service name in the `minikube service` command:
```shell
minikube service spring-boot-helm-service --url
```

The command output will be the URL to access the application:

![mk-service-url.png](src%2Fmain%2Fresources%2Fmk-service-url.png)

For more information about this topic, refer to the [Minikube Accessing apps](https://minikube.sigs.k8s.io/docs/handbook/accessing/) page.

# Try it out the application API

The API can be tested using the Swagger UI or Postman.
- The API documentation is available at [http://replace-with-service-ip:port/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).
- The Postman collection is available here: [spring-boot-helm-rest-api.postman_collection.json](spring-boot-helm-rest-api.postman_collection.json)
