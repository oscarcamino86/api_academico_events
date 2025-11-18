#!/bin/bash

echo "=== API Academico Events - Build & Run Script ==="

# Función para mostrar ayuda
show_help() {
    echo "Uso: $0 [OPCIÓN]"
    echo ""
    echo "Opciones:"
    echo "  build          Compilar la aplicación"
    echo "  test           Ejecutar tests"
    echo "  run            Ejecutar la aplicación localmente"
    echo "  docker-build   Construir imagen Docker"
    echo "  docker-run     Ejecutar con Docker Compose"
    echo "  docker-stop    Detener contenedores Docker"
    echo "  clean          Limpiar archivos compilados"
    echo "  help           Mostrar esta ayuda"
    echo ""
}

# Función para compilar
build() {
    echo "📦 Compilando la aplicación..."
    mvn clean package -DskipTests
    if [ $? -eq 0 ]; then
        echo "✅ Compilación exitosa"
    else
        echo "❌ Error en la compilación"
        exit 1
    fi
}

# Función para ejecutar tests
test() {
    echo "🧪 Ejecutando tests..."
    mvn test
    if [ $? -eq 0 ]; then
        echo "✅ Tests ejecutados exitosamente"
    else
        echo "❌ Error en los tests"
        exit 1
    fi
}

# Función para ejecutar localmente
run() {
    echo "🚀 Ejecutando la aplicación..."
    mvn spring-boot:run
}

# Función para construir imagen Docker
docker_build() {
    echo "🐳 Construyendo imagen Docker..."
    build
    docker build -t api-academico-events:latest .
    if [ $? -eq 0 ]; then
        echo "✅ Imagen Docker construida exitosamente"
    else
        echo "❌ Error al construir imagen Docker"
        exit 1
    fi
}

# Función para ejecutar con Docker Compose
docker_run() {
    echo "🐳 Ejecutando con Docker Compose..."
    docker_build
    docker-compose up -d
    if [ $? -eq 0 ]; then
        echo "✅ Servicios iniciados exitosamente"
        echo "📍 API disponible en: http://localhost:8080"
        echo "📍 Kafka UI disponible en: http://localhost:8081"
        echo "📍 Para ver logs: docker-compose logs -f api-academico-events"
    else
        echo "❌ Error al iniciar servicios"
        exit 1
    fi
}

# Función para detener contenedores
docker_stop() {
    echo "🛑 Deteniendo contenedores Docker..."
    docker-compose down
    if [ $? -eq 0 ]; then
        echo "✅ Contenedores detenidos exitosamente"
    else
        echo "❌ Error al detener contenedores"
        exit 1
    fi
}

# Función para limpiar
clean() {
    echo "🧹 Limpiando archivos compilados..."
    mvn clean
    docker-compose down 2>/dev/null || true
    echo "✅ Limpieza completada"
}

# Procesar argumentos
case "$1" in
    build)
        build
        ;;
    test)
        test
        ;;
    run)
        run
        ;;
    docker-build)
        docker_build
        ;;
    docker-run)
        docker_run
        ;;
    docker-stop)
        docker_stop
        ;;
    clean)
        clean
        ;;
    help)
        show_help
        ;;
    *)
        echo "❌ Opción no válida: $1"
        show_help
        exit 1
        ;;
esac