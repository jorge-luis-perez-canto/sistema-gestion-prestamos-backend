# Usar la imagen oficial de Maven para compilar el proyecto en la primera etapa
FROM maven:3.8.4-openjdk-17 as builder

# Directorio de trabajo donde se colocarán los archivos del proyecto
WORKDIR /app

# Copiar el archivo pom.xml y descargar las dependencias para aprovechar la caché de Docker
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el resto del proyecto y construir el archivo JAR
COPY src ./src
RUN mvn package -DskipTests

# En la segunda etapa, usar la imagen oficial de OpenJDK para ejecutar la aplicación
FROM openjdk:17-jdk-slim

# Directorio de trabajo en la imagen de Docker
WORKDIR /app

# Copiar el archivo JAR del builder al directorio de trabajo
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto en el que se ejecutará la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
