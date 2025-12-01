# Stage 1: Compilar con Maven
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copiar archivos de configuración
COPY pom.xml .
COPY src ./src

# Compilar y generar JAR
RUN mvn clean package -DskipTests -q

# Stage 2: Runtime
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copiar JAR compilado del stage anterior
COPY --from=builder /app/target/app.jar /app/app.jar

# Crear directorio de logs
RUN mkdir -p /app/logs && chmod 777 /app/logs

# Exponer puerto
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "/app/app.jar"]
