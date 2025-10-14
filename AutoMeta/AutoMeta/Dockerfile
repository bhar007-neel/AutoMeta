# ===========================
# Stage 1: Build the application
# ===========================
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app
# Copy project files from nested folder
COPY AutoMeta/pom.xml .
COPY AutoMeta/src ./src

# Build the JAR without running tests
RUN mvn clean package -DskipTests

# ===========================
# Stage 2: Run the application
# ===========================
FROM eclipse-temurin:17-jdk

WORKDIR /app
# Copy the built JAR file from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the Spring Boot app port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
