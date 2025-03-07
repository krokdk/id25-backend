# Stage 1: Build the application using OpenJDK 17
FROM openjdk:17-jdk-slim AS build
WORKDIR /app

# Copy necessary files for building
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
COPY src src

# Set execution permission for the Maven wrapper
RUN chmod +x ./mvnw

# Build the application without running tests
RUN apt-get update && apt-get install -y maven && mvn clean package -DskipTests

# Stage 2: Create the final runtime image
FROM openjdk:17-jdk-slim
VOLUME /tmp

# Copy the built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Copy the dummy data file into the container (adjust path if necessary)
COPY ./dummyData.txt /app/dummyData.txt

# Kopier credentials-filen til containeren
COPY google-credentials.json /app/google-credentials.json

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
