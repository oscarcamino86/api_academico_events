FROM eclipse-temurin:17-jre

# Metadatos
LABEL maintainer="OscarCamino"
LABEL version="1.0.0"
LABEL description="API REST para eventos académicos con Kafka"

# Crear usuario no-root para seguridad e instalar curl para health check
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/* && \
    addgroup --system spring && adduser --system spring --ingroup spring

# Configurar directorio de trabajo
WORKDIR /app

# Copiar el JAR de la aplicación
COPY target/api-academico-events-*.jar app.jar

# Cambiar ownership del archivo
RUN chown spring:spring app.jar

# Cambiar al usuario no-root
USER spring:spring

# Exponer el puerto
EXPOSE 8089

# Variables de entorno
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV SPRING_PROFILES_ACTIVE=prod

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8089/actuator/health || exit 1

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]