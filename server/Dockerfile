FROM openjdk:21-jdk-slim

ARG JAR_FILE=target/snapverse-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} snapverse.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "snapverse.jar" ]
