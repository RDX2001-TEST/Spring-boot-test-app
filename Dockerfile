# ---------- Stage 1: Build ----------
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Stage 2: Run ----------
FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Run on port 9191
EXPOSE 9191

ENTRYPOINT ["java", "-jar", "app.jar"]