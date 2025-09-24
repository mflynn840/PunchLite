# Step 1: Build the backend application
FROM maven:3.8.4-openjdk-11 AS build

# Set working directory
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY PunchLite-backend/pom.xml ./
RUN mvn dependency:go-offline

# Copy the entire backend source code and build the application
COPY PunchLite-backend/src ./src
RUN mvn clean package -DskipTests

# Step 2: Create the runtime image
FROM openjdk:11-jre-slim

# Set working directory for the application
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (if your Spring Boot app runs on port 8080)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
