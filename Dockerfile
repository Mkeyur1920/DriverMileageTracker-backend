FROM gradle:8.4-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/build/libs/DriverMileageTracker-backend-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
