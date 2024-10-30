FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

RUN apt-get update && \
    apt-get install -y openssl && \
    mkdir -p /app/src/main/resources/jwtKeys && \
    openssl genrsa -out /app/src/main/resources/jwtKeys/private_key.pem 2048 && \
    openssl rsa -pubout -in /app/src/main/resources/jwtKeys/private_key.pem -out /app/src/main/resources/jwtKeys/public_key.pem

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/snapverse-0.0.1-SNAPSHOT.jar snapverse.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "snapverse.jar", "--spring.profiles.active=docker"]