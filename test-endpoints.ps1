# Script de prueba para los endpoints del API Academico Events
# Asegúrate de que la aplicación esté corriendo en el puerto 8080

Write-Host "=== Probando API Academico Events ===" -ForegroundColor Green
Write-Host ""

# Verificar que la aplicación esté corriendo
try {
    $healthCheck = Invoke-RestMethod -Uri "http://localhost:8080/actuator/health" -Method GET
    Write-Host "✓ Aplicación corriendo correctamente" -ForegroundColor Green
    Write-Host "Status: $($healthCheck.status)" -ForegroundColor Cyan
} catch {
    Write-Host "✗ Error: La aplicación no está corriendo en el puerto 8080" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "=== PRUEBAS DE ESTUDIANTE ===" -ForegroundColor Yellow

# 1. Crear evento de estudiante
Write-Host "1. Creando evento de estudiante..." -ForegroundColor Cyan
try {
    $estudianteData = @{
        nombre = "Juan Carlos"
        apellido = "Pérez González"
        dni = "12345678"
        email = "juan.perez@universidad.edu"
        telefono = "+51987654321"
        direccion = "Av. Universitaria 123, Lima"
        fechaNacimiento = "1995-03-15"
        carrera = "Ingeniería de Sistemas"
        activo = $true
    } | ConvertTo-Json

    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/estudiante/events/create" -Method POST -ContentType "application/json" -Body $estudianteData
    Write-Host "✓ $response" -ForegroundColor Green
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}

# 2. Actualizar evento de estudiante
Write-Host "2. Actualizando evento de estudiante..." -ForegroundColor Cyan
try {
    $estudianteUpdateData = @{
        estudianteId = 1
        nombre = "Juan Carlos"
        apellido = "Pérez Rodríguez"
        dni = "12345678"
        email = "juan.perez.updated@universidad.edu"
        telefono = "+51987654321"
        direccion = "Av. Universitaria 456, Lima"
        fechaNacimiento = "1995-03-15"
        carrera = "Ingeniería de Sistemas"
        activo = $true
    } | ConvertTo-Json

    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/estudiante/events/update" -Method PUT -ContentType "application/json" -Body $estudianteUpdateData
    Write-Host "✓ $response" -ForegroundColor Green
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "=== PRUEBAS DE MATRÍCULA ===" -ForegroundColor Yellow

# 3. Crear evento de matrícula
Write-Host "3. Creando evento de matrícula..." -ForegroundColor Cyan
try {
    $matriculaData = @{
        estudianteId = 1
        seccionId = 101
        fechaMatricula = "2025-11-17"
        estado = "PENDIENTE"
        costo = 150.50
        metodoPago = "TARJETA_CREDITO"
    } | ConvertTo-Json

    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/matricula/events/create" -Method POST -ContentType "application/json" -Body $matriculaData
    Write-Host "✓ $response" -ForegroundColor Green
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}

# 4. Actualizar evento de matrícula
Write-Host "4. Actualizando evento de matrícula..." -ForegroundColor Cyan
try {
    $matriculaUpdateData = @{
        matriculaId = 1
        estudianteId = 1
        seccionId = 101
        fechaMatricula = "2025-11-17"
        estado = "CONFIRMADA"
        costo = 150.50
        metodoPago = "TARJETA_CREDITO"
    } | ConvertTo-Json

    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/matricula/events/update" -Method PUT -ContentType "application/json" -Body $matriculaUpdateData
    Write-Host "✓ $response" -ForegroundColor Green
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}

# 5. Cancelar matrícula
Write-Host "5. Cancelando matrícula..." -ForegroundColor Cyan
try {
    $matriculaCancelData = @{
        matriculaId = 1
        estudianteId = 1
        seccionId = 101
        fechaMatricula = "2025-11-17"
        estado = "CANCELADA"
        costo = 150.50
        metodoPago = "TARJETA_CREDITO"
    } | ConvertTo-Json

    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/matricula/events/cancel" -Method PATCH -ContentType "application/json" -Body $matriculaCancelData
    Write-Host "✓ $response" -ForegroundColor Green
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}

# 6. Eliminar matrícula
Write-Host "6. Eliminando matrícula..." -ForegroundColor Cyan
try {
    $matriculaDeleteData = @{
        matriculaId = 1
        estudianteId = 1
        seccionId = 101
        fechaMatricula = "2025-11-17"
        estado = "CANCELADA"
        costo = 150.50
        metodoPago = "TARJETA_CREDITO"
    } | ConvertTo-Json

    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/matricula/events/delete" -Method DELETE -ContentType "application/json" -Body $matriculaDeleteData
    Write-Host "✓ $response" -ForegroundColor Green
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "=== PRUEBAS DE VALIDACIÓN ===" -ForegroundColor Yellow

# 7. Probar validación - matrícula sin datos requeridos
Write-Host "7. Probando validación (datos faltantes)..." -ForegroundColor Cyan
try {
    $matriculaInvalidData = @{
        fechaMatricula = "2025-11-17"
        estado = "PENDIENTE"
        metodoPago = "EFECTIVO"
    } | ConvertTo-Json

    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/matricula/events/create" -Method POST -ContentType "application/json" -Body $matriculaInvalidData
    Write-Host "✗ No se detectó error de validación" -ForegroundColor Red
} catch {
    if ($_.Exception.Response.StatusCode -eq 400) {
        Write-Host "✓ Validación funcionando correctamente (Error 400)" -ForegroundColor Green
    } else {
        Write-Host "✗ Error inesperado: $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "=== PRUEBAS COMPLETADAS ===" -ForegroundColor Green
Write-Host "Todas las pruebas han sido ejecutadas. Revisa los logs de la aplicación para ver los eventos publicados." -ForegroundColor Cyan