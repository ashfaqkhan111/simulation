# ---------- Stage 1: build the jar ----------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build

# Copy only pom.xml first so dependencies are cached between builds
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src
# Tests are skipped here because they need a live database
RUN mvn -B -q clean package -DskipTests

# ---------- Stage 2: small runtime image ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Run as a non-root user
RUN groupadd --system app && useradd --system --gid app --no-create-home app

COPY --from=build /build/target/*.jar app.jar
RUN chown app:app app.jar
USER app

EXPOSE 8080

# Size the heap from the container's memory limit
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]
