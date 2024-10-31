FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/snapverse-0.0.1-SNAPSHOT.jar snapverse.jar

EXPOSE 8080


ENTRYPOINT ["java", "-jar", "snapverse.jar"]
