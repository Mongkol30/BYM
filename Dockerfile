# Stage 1: Build JAR ด้วย Java 25
FROM eclipse-temurin:25-jdk-alpine AS builder

RUN apk add --no-cache maven

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -q

COPY src ./src
RUN mvn package -DskipTests -q

# ────────────────────────────────────────
# Stage 2: Run JAR
FROM eclipse-temurin:25-jdk-alpine

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]