# syntax=docker/dockerfile:1

FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml ./
RUN --mount=type=cache,target=/root/.m2 mvn -B -ntp -Dmaven.test.skip=true dependency:go-offline

COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -B -ntp -Dmaven.test.skip=true package

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /app/target/speakup-*.jar app.jar

ENV SPRING_MAIN_LAZY_INITIALIZATION=true

EXPOSE 8080

ENTRYPOINT ["java", \
    "-XX:+UseSerialGC", \
    "-XX:TieredStopAtLevel=1", \
    "-Xss512k", \
    "-XX:MaxRAMPercentage=75.0", \
    "-jar", "/app/app.jar"]
