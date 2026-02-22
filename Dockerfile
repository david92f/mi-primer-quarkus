# Multi-stage Dockerfile for Quarkus JVM mode
# Build stage - compiles the application
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy only dependency files first for better caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build
COPY src ./src
RUN mvn package -DskipTests -B

# Runtime stage
FROM registry.access.redhat.com/ubi9/openjdk-21-runtime:1.24

ENV LANGUAGE='en_US:en'
ENV QUARKUS_PROFILE=prod

# Copy built artifacts from builder
COPY --from=builder /app/target/quarkus-app/lib/ /deployments/lib/
COPY --from=builder /app/target/quarkus-app/*.jar /deployments/
COPY --from=builder /app/target/quarkus-app/app/ /deployments/app/
COPY --from=builder /app/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
USER 185

ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

ENTRYPOINT ["/opt/jboss/container/java/run/run-java.sh"]
