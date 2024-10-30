# Etapa de construcción para crear el JAR
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /snapverse
COPY . .
RUN mvn clean package -DskipTests

# Etapa final para el contenedor de aplicación
FROM openjdk:17-jdk-slim
WORKDIR /snapverse

# Copia el JAR desde la etapa de construcción
COPY --from=build /snapverse/target/snapverse-0.0.1-SNAPSHOT.jar snapverse.jar

# Crea y copia las llaves dentro de una carpeta accesible en el classpath
RUN mkdir -p /snapverse/jwtKeys && \
    openssl genrsa -out /snapverse/jwtKeys/private_key.pem 2048 && \
    openssl rsa -pubout -in /snapverse/jwtKeys/private_key.pem -out /snapverse/jwtKeys/public_key.pem

# Expone el puerto
EXPOSE 8080

# Ejecuta la aplicación con el perfil Docker
ENTRYPOINT ["java", "-jar", "snapverse.jar"]