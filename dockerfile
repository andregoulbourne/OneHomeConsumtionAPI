FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /Backend

COPY src/ /Backend/src
COPY build.gradle /Backend/build.gradle
COPY settings.gradle /Backend/settings.gradle
COPY gradlew /Backend/gradlew

RUN ["mvn", "install:install-file", "-Dfile=./src/main/resources/JSONUtil-0.0.1-SNAPSHOT.jar", "-DgroupId=com.andre", "-DartifactId=JSONUtil", "-Dversion=0.0.1-SNAPSHOT", "-Dpackaging=jar", "-DgeneratePom=true"]
RUN ["./gradlew", "build"]

FROM openjdk:17-jdk-buster

COPY --from=build /Backend/src/main/resources /src/main/resources
COPY --from=build /Backend/target/ConsumeAPI-0.0.1-SNAPSHOT-jar-with-dependencies.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

