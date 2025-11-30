# Instrucciones para levantar BD

## 1. Levantar contenedor
```bash
cd ~/Documents/Java/inventario-api
podman-compose up -d
```

## 2. Copiar y ejecutar script SQL
```bash
podman cp src/main/resources/schema.sql coppel_sql_server:/tmp/schema.sql

podman exec -it coppel_sql_server /opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P 'Coppel2024_Test!' -C -i /tmp/schema.sql
```

## 3. Verificar que se creó la BD
```bash
podman exec -it coppel_sql_server /opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P 'Coppel2024_Test!' -C -Q "USE InventarioCoppel; SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES;"
```

## 4. Ejecutar la aplicación Spring Boot
```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080/api/v1/polizas`