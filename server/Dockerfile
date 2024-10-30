FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .

RUN mkdir -p src/main/resources/jwtKeys && \
    openssl genrsa -out src/main/resources/jwtKeys/private_key.pem 2048 && \
    openssl rsa -pubout -in src/main/resources/jwtKeys/private_key.pem -out src/main/resources/jwtKeys/public_key.pem && \
    mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/snapverse-0.0.1-SNAPSHOT.jar snapverse.jar
COPY --from=build /app/src/main/resources/jwtKeys /app/src/main/resources/jwtKeys

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "snapverse.jar", "--spring.profiles.active=docker"]
