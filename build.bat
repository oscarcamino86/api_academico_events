@echo off
echo === API Academico Events - Build ^& Run Script ===

if "%1"=="" goto help
if "%1"=="help" goto help
if "%1"=="build" goto build
if "%1"=="test" goto test
if "%1"=="run" goto run
if "%1"=="docker-build" goto docker_build
if "%1"=="docker-run" goto docker_run
if "%1"=="docker-stop" goto docker_stop
if "%1"=="clean" goto clean
goto invalid

:help
echo Uso: %0 [OPCION]
echo.
echo Opciones:
echo   build          Compilar la aplicacion
echo   test           Ejecutar tests
echo   run            Ejecutar la aplicacion localmente
echo   docker-build   Construir imagen Docker
echo   docker-run     Ejecutar con Docker Compose
echo   docker-stop    Detener contenedores Docker
echo   clean          Limpiar archivos compilados
echo   help           Mostrar esta ayuda
echo.
goto end

:build
echo 📦 Compilando la aplicacion...
call mvn clean package -DskipTests
if %ERRORLEVEL% EQU 0 (
    echo ✅ Compilacion exitosa
) else (
    echo ❌ Error en la compilacion
    exit /b 1
)
goto end

:test
echo 🧪 Ejecutando tests...
call mvn test
if %ERRORLEVEL% EQU 0 (
    echo ✅ Tests ejecutados exitosamente
) else (
    echo ❌ Error en los tests
    exit /b 1
)
goto end

:run
echo 🚀 Ejecutando la aplicacion...
call mvn spring-boot:run
goto end

:docker_build
echo 🐳 Construyendo imagen Docker...
call :build
docker build -t api-academico-events:latest .
if %ERRORLEVEL% EQU 0 (
    echo ✅ Imagen Docker construida exitosamente
) else (
    echo ❌ Error al construir imagen Docker
    exit /b 1
)
goto end

:docker_run
echo 🐳 Ejecutando con Docker Compose...
call :docker_build
docker-compose up -d
if %ERRORLEVEL% EQU 0 (
    echo ✅ Servicios iniciados exitosamente
    echo 📍 API disponible en: http://localhost:8080
    echo 📍 Kafka UI disponible en: http://localhost:8081
    echo 📍 Para ver logs: docker-compose logs -f api-academico-events
) else (
    echo ❌ Error al iniciar servicios
    exit /b 1
)
goto end

:docker_stop
echo 🛑 Deteniendo contenedores Docker...
docker-compose down
if %ERRORLEVEL% EQU 0 (
    echo ✅ Contenedores detenidos exitosamente
) else (
    echo ❌ Error al detener contenedores
    exit /b 1
)
goto end

:clean
echo 🧹 Limpiando archivos compilados...
call mvn clean
docker-compose down >nul 2>&1
echo ✅ Limpieza completada
goto end

:invalid
echo ❌ Opcion no valida: %1
goto help

:end