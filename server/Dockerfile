# Fase de construcción
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Fase de ejecución
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/snapverse-0.0.1-SNAPSHOT.jar snapverse.jar

# Crear un directorio para las llaves JWT
RUN mkdir -p /app/jwtkeys

# Comando para generar las llaves si no existen
RUN if [ ! -f /app/jwtkeys/private_key.pem ]; then \
      echo "Generando llaves JWT..."; \
      openssl genrsa -out /app/jwtkeys/private_key.pem 2048 && \
      openssl rsa -pubout -in /app/jwtkeys/private_key.pem -out /app/jwtkeys/public_key.pem; \
    else \
      echo "Las llaves JWT ya existen."; \
    fi


RUN chmod -R 755 /app/jwtkeys
RUN ls -l /app/jwtkeys


EXPOSE 8080
ENTRYPOINT ["java", "-jar", "snapverse.jar", "--spring.profiles.active=docker"]
