FROM gradle:jdk17 AS builder

WORKDIR /app

COPY src ./src
COPY build.gradle.kts ./build.gradle.kts
COPY settings.gradle.kts ./settings.gradle.kts
COPY gradle.properties ./gradle.properties


RUN gradle build -x test -Dquarkus.profile=env


FROM registry.access.redhat.com/ubi8/openjdk-17:1.14 AS runner

ENV LANGUAGE='en_US:en'

ENV OPENAPI_PATH="/doc" \
    DB_KIND="postgresql" \
    DB_USERNAME="postgres" \
    DB_PASSWORD="postgres" \
    DB_HOST="db" \
    DB_PORT=5432 \
    DB_NAME="bingemate" \
    DB_GNERATION="update"

COPY --from=builder --chown=1000 /app/build/quarkus-app/lib/ /deployments/lib/
COPY --from=builder --chown=1000 /app/build/quarkus-app/*.jar /deployments/
COPY --from=builder --chown=1000 /app/build/quarkus-app/app/ /deployments/app/
COPY --from=builder --chown=1000 /app/build/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
USER 1000

ENV JAVA_OPTS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

