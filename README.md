# API Academico Events

API REST desarrollado en Java con Spring Boot para registrar eventos CRUD de **estudiantes y matrículas** y enviarlos a Apache Kafka. Este microservicio está diseñado específicamente para **recibir eventos de operaciones CRUD** desde aplicaciones backend y publicarlos en Kafka, **sin manejar base de datos directamente**.

## 🚀 Características

- **API REST** para recibir eventos de operaciones CRUD de estudiantes y matrículas
- **Integración con Apache Kafka** para envío de eventos
- **Patrón MVC** bien estructurado
- **Validación de datos** con Bean Validation
- **Manejo global de excepciones**
- **Dockerización completa** con Docker Compose
- **Logging estructurado**
- **Health checks** y monitoreo
- **Sin manejo de base de datos** - Solo procesamiento y envío de eventos

## 🛠️ Tecnologías

- **Java 17**
- **Spring Boot 3.2.0**
- **Apache Kafka**
- **Maven**
- **Docker & Docker Compose**

## 📋 Arquitectura

```
Aplicación Backend → API Academico Events → Apache Kafka
```

La API actúa como un **Event Publisher** que:
1. Recibe datos del estudiante desde aplicaciones backend
2. Valida los datos de entrada
3. Crea eventos estructurados
4. Publica los eventos en Apache Kafka

## 🗄️ Modelo de Datos

### Estructura del Estudiante

```java
{
  "estudianteId": 1,
  "nombre": "Juan",
  "apellido": "Perez", 
  "dni": "12345678",
  "email": "juan.perez@example.com",
  "telefono": "+51987654321",
  "fechaNacimiento": "1995-05-15",
  "direccion": "Av. Principal 123, Lima",
  "fechaRegistro": "2024-01-15T10:30:00",
  "activo": true
}
```

### Estructura de la Matrícula

```java
{
  "matriculaId": 1,
  "estudianteId": 1,
  "seccionId": 10,
  "fechaMatricula": "2024-01-15",
  "estado": "PENDIENTE",
  "costo": 250.00,
  "metodoPago": "TARJETA_CREDITO",
  "fechaRegistro": "2024-01-15T10:30:00"
}
```

#### Estados de Matrícula Válidos:
- `PENDIENTE`: Matrícula registrada pero no confirmada
- `CONFIRMADA`: Matrícula confirmada y activa
- `CANCELADA`: Matrícula cancelada
- `COMPLETADA`: Matrícula completada exitosamente

## 🔧 Instalación y Configuración

### Prerrequisitos

- Java 17+
- Maven 3.6+
- Docker & Docker Compose

### 1. Clonar el proyecto

```bash
git clone https://github.com/oscarcamino86/api_academico_events.git
cd api_academico_events
```

### 2. Compilar la aplicación

```bash
mvn clean package -DskipTests
```

### 3. Ejecutar con Docker Compose

```bash
docker-compose up -d
```

Este comando levantará:
- **Zookeeper** (puerto 2181)
- **Kafka** (puerto 9092)
- **API Academico Events** (puerto 8089)
- **Kafka UI** (puerto 8189)

### 4. Verificar el estado

```bash
# Verificar containers
docker-compose ps

# Verificar logs
docker-compose logs api-academico-events

# Health check
curl http://localhost:8089/actuator/health
```

## 📚 API Endpoints

### Eventos de Estudiantes

| Método | Endpoint | Descripción | Requiere ID |
|--------|----------|-------------|-------------|
| `POST` | `/api/estudiante/events/create` | Publicar evento de creación | ❌ |
| `PUT` | `/api/estudiante/events/update` | Publicar evento de actualización | ✅ |
| `DELETE` | `/api/estudiante/events/delete` | Publicar evento de eliminación | ✅ |
| `PATCH` | `/api/estudiante/events/deactivate` | Publicar evento de desactivación | ✅ |

### Eventos de Matrícula

| Método | Endpoint | Descripción | Requiere ID |
|--------|----------|-------------|-------------|
| `POST` | `/api/matricula/events/create` | Publicar evento de creación | ❌ |
| `PUT` | `/api/matricula/events/update` | Publicar evento de actualización | ✅ |
| `DELETE` | `/api/matricula/events/delete` | Publicar evento de eliminación | ✅ |
| `PATCH` | `/api/matricula/events/cancel` | Publicar evento de cancelación | ✅ |

## 🧪 Ejemplos de Uso

### Configuración Inicial (PowerShell)

```powershell
# Configurar headers para todas las peticiones
$headers = @{"Content-Type" = "application/json"}
```

### 👨‍🎓 Eventos de Estudiantes

#### Crear Estudiante

```powershell
$body = '{"nombre": "Juan", "apellido": "Perez", "dni": "12345678", "email": "juan.perez@example.com", "telefono": "+51987654321", "fechaNacimiento": "1995-05-15", "direccion": "Av. Principal 123, Lima", "activo": true}'
Invoke-WebRequest -Uri "http://localhost:8089/api/estudiante/events/create" -Method POST -Headers $headers -Body $body -ContentType "application/json"
```

#### Actualizar Estudiante

```powershell
$body = '{"estudianteId": 1, "nombre": "Juan Carlos", "apellido": "Perez", "dni": "12345678", "email": "juan.carlos.perez@example.com", "telefono": "+51987654321", "fechaNacimiento": "1995-05-15", "direccion": "Av. Principal 456, Lima", "activo": true}'
Invoke-WebRequest -Uri "http://localhost:8089/api/estudiante/events/update" -Method PUT -Headers $headers -Body $body -ContentType "application/json"
```

#### Eliminar Estudiante

```powershell
$body = '{"estudianteId": 1, "nombre": "Juan Carlos", "apellido": "Perez", "dni": "12345678", "email": "juan.carlos.perez@example.com", "fechaNacimiento": "1995-05-15"}'
Invoke-WebRequest -Uri "http://localhost:8089/api/estudiante/events/delete" -Method DELETE -Headers $headers -Body $body -ContentType "application/json"
```

#### Desactivar Estudiante

```powershell
$body = '{"estudianteId": 1, "nombre": "Juan Carlos", "apellido": "Perez", "dni": "12345678", "email": "juan.carlos.perez@example.com", "fechaNacimiento": "1995-05-15"}'
Invoke-WebRequest -Uri "http://localhost:8089/api/estudiante/events/deactivate" -Method PATCH -Headers $headers -Body $body -ContentType "application/json"
```

### 📝 Eventos de Matrícula

#### Crear Matrícula

```powershell
$body = '{"estudianteId": 1, "seccionId": 10, "fechaMatricula": "2024-01-15", "estado": "PENDIENTE", "costo": 250.00, "metodoPago": "TARJETA_CREDITO"}'
Invoke-WebRequest -Uri "http://localhost:8089/api/matricula/events/create" -Method POST -Headers $headers -Body $body -ContentType "application/json"
```

#### Actualizar Matrícula

```powershell
$body = '{"matriculaId": 1, "estudianteId": 1, "seccionId": 10, "fechaMatricula": "2024-01-15", "estado": "CONFIRMADA", "costo": 275.00, "metodoPago": "TRANSFERENCIA"}'
Invoke-WebRequest -Uri "http://localhost:8089/api/matricula/events/update" -Method PUT -Headers $headers -Body $body -ContentType "application/json"
```

#### Eliminar Matrícula

```powershell
$body = '{"matriculaId": 1, "estudianteId": 1, "seccionId": 10, "fechaMatricula": "2024-01-15", "estado": "CONFIRMADA", "costo": 275.00, "metodoPago": "TRANSFERENCIA"}'
Invoke-WebRequest -Uri "http://localhost:8089/api/matricula/events/delete" -Method DELETE -Headers $headers -Body $body -ContentType "application/json"
```

#### Cancelar Matrícula

```powershell
$body = '{"matriculaId": 1, "estudianteId": 1, "seccionId": 10, "fechaMatricula": "2024-01-15", "estado": "PENDIENTE", "costo": 250.00}'
Invoke-WebRequest -Uri "http://localhost:8089/api/matricula/events/cancel" -Method PATCH -Headers $headers -Body $body -ContentType "application/json"
```

### 📋 Respuestas Esperadas

- **Creación exitosa**: `HTTP 201 Created - "Evento de creación publicado exitosamente"`
- **Actualización exitosa**: `HTTP 200 OK - "Evento de actualización publicado exitosamente"`
- **Eliminación exitosa**: `HTTP 200 OK - "Evento de eliminación publicado exitosamente"`
- **Cancelación exitosa**: `HTTP 200 OK - "Evento de cancelación publicado exitosamente"`

## 📨 Eventos Kafka

### Topic: `estudiante-events`

Eventos de estudiantes se envían automáticamente a Kafka con la siguiente estructura:

```json
{
  "eventType": "CREATE",
  "timestamp": "2024-01-15T10:30:00",
  "estudianteId": 1,
  "nombre": "Juan",
  "apellido": "Perez",
  "dni": "12345678",
  "email": "juan.perez@example.com",
  "telefono": "+51987654321",
  "fechaNacimiento": "1995-05-15",
  "direccion": "Av. Principal 123, Lima",
  "fechaRegistro": "2024-01-15T10:30:00",
  "activo": true
}
```

### Topic: `matricula-events`

Eventos de matrícula se envían automáticamente a Kafka con la siguiente estructura:

```json
{
  "eventType": "CREATE",
  "timestamp": "2024-01-15T10:30:00",
  "matriculaId": 1,
  "estudianteId": 1,
  "seccionId": 10,
  "fechaMatricula": "2024-01-15",
  "estado": "PENDIENTE",
  "costo": 250.00,
  "metodoPago": "TARJETA_CREDITO",
  "fechaRegistro": "2024-01-15T10:30:00"
}
```

### Tipos de Eventos

**Estudiantes:**
- `CREATE`: Estudiante creado
- `UPDATE`: Estudiante actualizado  
- `DELETE`: Estudiante eliminado
- `DEACTIVATE`: Estudiante desactivado

**Matrículas:**
- `CREATE`: Matrícula creada
- `UPDATE`: Matrícula actualizada
- `DELETE`: Matrícula eliminada
- `CANCEL`: Matrícula cancelada

### Key Strategy
Los eventos se envían con keys basadas en:
- `estudiante-{estudianteId}` si el ID está presente
- `estudiante-{timestamp}` para eventos de creación

## 🖥️ Monitoreo

### Kafka UI
Accede a `http://localhost:8189` para monitorear:
- Topics de Kafka
- Mensajes enviados
- Configuración de brokers
- Consumer groups

### Health Check
```bash
curl http://localhost:8089/actuator/health
```

### Métricas
```bash
curl http://localhost:8089/actuator/metrics
```

## 🔬 Testing y Validación

### Ejecutar Tests Unitarios

```bash
mvn test
```

### Script de Prueba Completo

**PowerShell - Prueba de todos los endpoints:**

```powershell
# Configuración inicial
$headers = @{"Content-Type" = "application/json"}
$baseUrl = "http://localhost:8089"

# Test de salud del servicio
Write-Host "🏥 Verificando health check..."
Invoke-WebRequest -Uri "$baseUrl/actuator/health" -Method GET

# === CICLO COMPLETO DE ESTUDIANTE ===
Write-Host "👨‍🎓 Probando eventos de estudiante..."

# 1. Crear estudiante
$estudianteBody = '{"nombre": "Maria", "apellido": "Garcia", "dni": "87654321", "email": "maria@example.com", "fechaNacimiento": "1997-08-22", "telefono": "+51999888777", "direccion": "Lima, Peru", "activo": true}'
Invoke-WebRequest -Uri "$baseUrl/api/estudiante/events/create" -Method POST -Headers $headers -Body $estudianteBody -ContentType "application/json"

# 2. Actualizar estudiante  
$estudianteUpdateBody = '{"estudianteId": 1, "nombre": "Maria Elena", "apellido": "Garcia", "dni": "87654321", "email": "maria.elena@example.com", "fechaNacimiento": "1997-08-22", "telefono": "+51999888777", "direccion": "Lima, Peru", "activo": true}'
Invoke-WebRequest -Uri "$baseUrl/api/estudiante/events/update" -Method PUT -Headers $headers -Body $estudianteUpdateBody -ContentType "application/json"

# === CICLO COMPLETO DE MATRÍCULA ===
Write-Host "📝 Probando eventos de matrícula..."

# 1. Crear matrícula
$matriculaBody = '{"estudianteId": 1, "seccionId": 10, "fechaMatricula": "2024-01-15", "estado": "PENDIENTE", "costo": 250.00, "metodoPago": "TARJETA_CREDITO"}'
Invoke-WebRequest -Uri "$baseUrl/api/matricula/events/create" -Method POST -Headers $headers -Body $matriculaBody -ContentType "application/json"

# 2. Actualizar matrícula
$matriculaUpdateBody = '{"matriculaId": 1, "estudianteId": 1, "seccionId": 10, "fechaMatricula": "2024-01-15", "estado": "CONFIRMADA", "costo": 275.00, "metodoPago": "TRANSFERENCIA"}'
Invoke-WebRequest -Uri "$baseUrl/api/matricula/events/update" -Method PUT -Headers $headers -Body $matriculaUpdateBody -ContentType "application/json"

# 3. Cancelar matrícula
$matriculaCancelBody = '{"matriculaId": 1, "estudianteId": 1, "seccionId": 10, "fechaMatricula": "2024-01-15", "estado": "CONFIRMADA", "costo": 275.00}'
Invoke-WebRequest -Uri "$baseUrl/api/matricula/events/cancel" -Method PATCH -Headers $headers -Body $matriculaCancelBody -ContentType "application/json"

Write-Host "✅ Pruebas completadas. Revisa Kafka UI en http://localhost:8189"
```

### Ejemplos para Linux/macOS (Bash/Curl)

```bash
#!/bin/bash
BASE_URL="http://localhost:8089"

# Función de test
test_endpoint() {
    echo "🧪 Probando: $1"
    curl -X $2 "$BASE_URL$3" \
        -H "Content-Type: application/json" \
        -d "$4" \
        -w "\nStatus: %{http_code}\n\n"
}

# Test de estudiantes
test_endpoint "Crear Estudiante" "POST" "/api/estudiante/events/create" \
  '{"nombre": "Ana", "apellido": "Lopez", "dni": "11223344", "email": "ana@example.com", "fechaNacimiento": "1998-03-15"}'

test_endpoint "Actualizar Estudiante" "PUT" "/api/estudiante/events/update" \
  '{"estudianteId": 1, "nombre": "Ana Maria", "apellido": "Lopez", "dni": "11223344", "email": "ana.maria@example.com", "fechaNacimiento": "1998-03-15"}'

# Test de matrículas
test_endpoint "Crear Matrícula" "POST" "/api/matricula/events/create" \
  '{"estudianteId": 1, "seccionId": 15, "fechaMatricula": "2024-02-01", "estado": "PENDIENTE", "costo": 300.00, "metodoPago": "EFECTIVO"}'

test_endpoint "Cancelar Matrícula" "PATCH" "/api/matricula/events/cancel" \
  '{"matriculaId": 1, "estudianteId": 1, "seccionId": 15, "fechaMatricula": "2024-02-01", "estado": "PENDIENTE", "costo": 300.00}'
```

### Verificar Eventos en Kafka

1. **Accede a Kafka UI**: `http://localhost:8189`
2. **Revisa los topics**: `estudiante-events` y `matricula-events`
3. **Verifica los mensajes**: Cada operación debe generar un evento

### Comandos de Validación

```bash
# Verificar containers activos
docker-compose ps

# Revisar logs de la aplicación
docker-compose logs api-academico-events

# Listar topics de Kafka
docker exec kafka kafka-topics.sh --list --bootstrap-server localhost:9092
```

## 🔧 Configuración

### Notas Importantes por Plataforma

#### Windows/PowerShell
- Usa comillas dobles para headers y simples para JSON body
- Mantén el JSON en una sola línea para evitar problemas de parsing
- Los campos obligatorios son requeridos para DELETE y DEACTIVATE
- **IMPORTANTE**: Siempre incluye `-ContentType "application/json"` para evitar errores de Content-Type

#### Linux/macOS/Bash
- Usa comillas simples para JSON para evitar problemas de escape
- Puedes usar formato multi-línea con backslashes (\)
- curl está disponible por defecto en la mayoría de distribuciones

### Variables de Entorno

| Variable | Descripción | Valor por defecto |
|----------|-------------|-------------------|
| `KAFKA_BOOTSTRAP_SERVERS` | Servidores Kafka | `localhost:9092` |
| `SPRING_PROFILES_ACTIVE` | Perfil de Spring Boot | `dev` |

### Perfiles

- **default**: Desarrollo
- **prod**: Producción con configuración optimizada
- **test**: Testing con configuración aislada

## 🚀 Despliegue

### Solo la aplicación

```bash
# Compilar
mvn clean package

# Construir imagen Docker
docker build -t api-academico-events:latest .

# Ejecutar
docker run -p 8089:8089 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e KAFKA_BOOTSTRAP_SERVERS=your-kafka:9092 \
  api-academico-events:latest
```

### Stack completo

```bash
docker-compose up -d
```

## 🏗️ Integración con Aplicaciones Backend

### Flujo Recomendado

1. **Aplicación Backend** realiza operación CRUD en su base de datos
2. **Aplicación Backend** invoca API Academico Events con los datos
3. **API Academico Events** valida y publica evento en Kafka
4. **Otros microservicios** consumen eventos de Kafka para sincronización

### Ejemplo de Integración

```java
// En tu aplicación backend
@Service
public class EstudianteService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    public Estudiante createEstudiante(Estudiante estudiante) {
        // 1. Guardar en BD local
        Estudiante saved = estudianteRepository.save(estudiante);
        
        // 2. Publicar evento
        publishCreateEvent(saved);
        
        return saved;
    }
    
    private void publishCreateEvent(Estudiante estudiante) {
        String eventUrl = "http://api-academico-events:8089/api/estudiante/events/create";
        restTemplate.postForEntity(eventUrl, estudiante, String.class);
    }
}
```

## ✨ Ventajas del Diseño

- **Separación de responsabilidades**: Base de datos y eventos están desacoplados
- **Escalabilidad**: La API de eventos puede escalarse independientemente
- **Confiabilidad**: Los eventos se publican de forma asíncrona
- **Flexibilidad**: Múltiples aplicaciones pueden usar la misma API de eventos
- **Mantenibilidad**: Lógica de eventos centralizada y reutilizable

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## ✨ Autor

**Oscar Camino** - [OscarCamino](https://github.com/OscarCamino)

---

### 📞 Soporte

Si tienes alguna pregunta o problema, por favor abre un issue en el repositorio.
