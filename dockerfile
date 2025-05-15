WORKDIR /Backend

COPY src/ /Backend/src
COPY build.gradle /Backend/build.gradle
COPY settings.gradle /Backend/settings.gradle
COPY gradlew /Backend/gradlew

RUN ["./gradlew", "build"]

FROM openjdk:17-jdk-buster

COPY --from=build /Backend/src/main/resources /src/main/resources
COPY --from=build /Backend/target/ConsumeAPI-0.0.1-SNAPSHOT-jar-with-dependencies.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

