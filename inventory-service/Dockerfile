# -------------------------------------
# ETAPA 1: BUILD (Compilación y Empaquetado)
# -------------------------------------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copia los archivos de configuración de Maven para descargar dependencias
COPY pom.xml .

# Descarga las dependencias primero (aprovecha la caché de Docker)
RUN mvn dependency:go-offline

# Copia el código fuente
COPY src ./src

# Empaqueta la aplicación en un archivo JAR ejecutable
RUN mvn clean package -DskipTests

# -------------------------------------
# ETAPA 2: RUNTIME (Ejecución)
# -------------------------------------
# Usa una imagen base ligera para ejecución (solo el JRE)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Expón el puerto de la aplicación (8080 o el que uses)
EXPOSE 8080

# Copia el JAR generado en la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Define el comando para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]