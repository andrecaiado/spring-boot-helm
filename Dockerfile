# Use an official Maven image as the base image
FROM maven:3.9.9-eclipse-temurin-17 AS build
# Set the working directory in the container
WORKDIR /app
# Copy the pom.xml and the project files to the container
COPY pom.xml .
COPY src ./src
# Package the application
RUN mvn clean package -DskipTests

# Use an official OpenJDK image as the base image
FROM eclipse-temurin:17-jre-jammy
# Set the working directory in the container
WORKDIR /app
# Copy the packaged JAR file to the container
COPY --from=build /app/target/*.jar app.jar
# Run the JAR file
CMD ["java", "-jar", "app.jar"]