# API REST - Sistema de Inventario Coppel

## Resumen técnico

Backend REST desarrollado en Spring Boot 3.5.8 con Java 21. Implementa operaciones CRUD completas para gestión de empleados, inventario y pólizas de movimiento con persistencia automática en SQL Server y transacciones ACID.

Características clave:
- Validaciones de entrada en todas las entidades (DTOs con @Valid)
- Manejo centralizado de excepciones con respuestas estandarizadas
- Descuento automático de inventario al crear pólizas
- Logs persistentes en archivo
- CORS habilitado para integración con frontend Angular
- Containerizado con Docker/Podman para reproducibilidad

---

## Stack tecnológico

- **Framework**: Spring Boot 3.5.8
- **Lenguaje**: Java 21
- **BD**: SQL Server 2022 Express
- **ORM**: Hibernate + Spring Data JPA
- **Build**: Maven 3.9
- **Container**: Docker/Podman multi-stage
- **Logging**: Logback con archivo rotativo

---

## Requisitos previos

**Software necesario:**
- Git
- Java 21 JDK o superior
- Maven 3.9+
- Docker o Podman
- curl (para testing de endpoints)


**Verificar instalación:**
```bash
java --version        # Java 21+
mvn --version         # Maven 3.9+
podman --version      # o podman --version
curl --version        # curl 7.x+
```

---

## Construcción e instalación

```bash
# Clonar repositorio
git clone https://github.com/BryanInzunza/inventario-back
cd inventario-api

# Compilar con Maven (genera target/app.jar)
mvn clean install

# Construir y ejecutar con Docker/Podman (en background)
podman-compose up -d &

# Esperar a que compile y levante (~2-3 minutos)
sleep 180

# Verificar estado
podman ps
```

El archivo `target/app.jar` se genera automáticamente y es utilizado por el Dockerfile en el build de la imagen. No se incluye en el repositorio (ver `.gitignore`).

Nota: El primer levantamiento tarda más porque Maven compila el proyecto dentro del contenedor. Los siguientes levantamientos son más rápidos.

---

## Ejecución

### Primer levantamiento

```bash
# Iniciar contenedores
podman-compose up -d

# Esperar a que SQL Server esté listo (~40s)
sleep 40

# Cargar esquema de BD (una sola vez)
podman cp src/main/resources/schema.sql coppel_sql_server:/tmp/schema.sql
podman exec -it coppel_sql_server /opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P 'Coppel2024_Test!' -C -i /tmp/schema.sql

# Reiniciar API
podman restart coppel_inventario_api

# Verificar
curl http://localhost:8080/api/v1/empleados
```

### Levantamientos posteriores

```bash
podman-compose up -d
sleep 40
curl http://localhost:8080/api/v1/empleados
```

---

## API Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/v1/empleados` | Listar empleados |
| POST | `/api/v1/empleados` | Crear empleado |
| PUT | `/api/v1/empleados/{id}` | Actualizar empleado |
| DELETE | `/api/v1/empleados/{id}` | Eliminar empleado |
| GET | `/api/v1/inventario` | Listar artículos |
| POST | `/api/v1/inventario` | Crear artículo |
| PUT | `/api/v1/inventario/{sku}` | Actualizar artículo |
| DELETE | `/api/v1/inventario/{sku}` | Eliminar artículo |
| GET | `/api/v1/polizas` | Listar pólizas |
| POST | `/api/v1/polizas` | Crear póliza (descuenta inventario) |
| PUT | `/api/v1/polizas/{id}` | Actualizar póliza |
| DELETE | `/api/v1/polizas/{id}` | Eliminar póliza (devuelve inventario) |

---

## Configuración

| Parámetro | Valor |
|-----------|-------|
| URL API | http://localhost:8080/api/v1 |
| BD Host | localhost:1433 |
| BD Usuario | sa |
| BD Contraseña | Coppel2024_Test! |
| BD Nombre | InventarioCoppel |

Logs disponibles en: `./logs/inventario-api.log`

---

## Validaciones

Todas las entidades validan entrada mediante DTOs:
- Campos requeridos (nombres, apellidos, SKU)
- Longitudes mínimas/máximas
- Valores numéricos positivos (cantidades)
- Respuesta 400 BAD_REQUEST con detalles del error

---

## Persistencia

- Datos de BD se persisten en volumen `sql_data`
- Logs se escriben en `./logs/` del host
- Usar `podman-compose down` (sin `-v`) para mantener datos entre reinicios

---

## Test de funcionalidad

```bash
# Crear empleado
curl -X POST http://localhost:8080/api/v1/empleados \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Juan","apellido":"Pérez","puesto":"Gerente"}'

# Crear inventario
curl -X POST http://localhost:8080/api/v1/inventario \
  -H "Content-Type: application/json" \
  -d '{"sku":"SKU-001","nombre":"Laptop","cantidad":100}'

# Crear póliza (descuenta inventario)
curl -X POST http://localhost:8080/api/v1/polizas \
  -H "Content-Type: application/json" \
  -d '{"idEmpleado":1,"sku":"SKU-001","cantidad":5}'

# Verificar descuento
curl http://localhost:8080/api/v1/inventario/SKU-001
# Esperado: cantidad = 95
```

---

**Versión**: 1.0.0 | **Fecha**: 01/12/2025
