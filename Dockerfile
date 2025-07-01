# Stage 1: Build the JAR
FROM gradle:8.4-jdk17 AS builder
WORKDIR /DriverMileageTracker-backend
COPY . .
RUN gradle build

# Stage 2: Run the JAR
FROM eclipse-temurin:17-jre
WORKDIR /DriverMileageTracker-backend

EXPOSE 8080 

# 👇 COPY the built JAR from the builder stage to /app/app.jar
COPY --from=builder /DriverMileageTracker-backend/build/libs/DriverMileageTracker-backend-1.0-SNAPSHOT.jar app.jar

# 👇 Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
