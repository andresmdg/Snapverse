# Etapa de construcción para crear el JAR
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa final para el contenedor de aplicación
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copia el JAR desde la etapa de construcción
COPY --from=build /app/target/snapverse-0.0.1-SNAPSHOT.jar snapverse.jar

# Crea y copia las llaves dentro de una carpeta accesible en el classpath
RUN mkdir -p /app/jwtKeys && \
    openssl genrsa -out /app/jwtKeys/private_key.pem 2048 && \
    openssl rsa -pubout -in /app/jwtKeys/private_key.pem -out /app/jwtKeys/public_key.pem

# Expone el puerto
EXPOSE 8080

# Ejecuta la aplicación con el perfil Docker
ENTRYPOINT ["java", "-jar", "snapverse.jar", "--spring.config.location=classpath:/"]