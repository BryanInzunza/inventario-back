-- Creación de Base de Datos
--CREATE DATABASE InventarioCoppel;
--GO

--USE InventarioCoppel;
--GO

-- Tabla: Inventario
-- Restricción: No puede existir 2 registros con el mismo SKU (UNIQUE)
CREATE TABLE Inventario (
    SKU VARCHAR(50) PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL,
    Cantidad INT NOT NULL CHECK (Cantidad >= 0) -- Validación extra: no negativos
);
GO

-- Tabla: Empleado
-- Restricción: No puede existir 2 empleados con el mismo ID (PK)
CREATE TABLE Empleado (
    IdEmpleado INT IDENTITY(1,1) PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL,
    Apellido VARCHAR(100) NOT NULL,
    Puesto VARCHAR(50) NOT NULL
);
GO

-- Tabla: Polizas
-- Restricción: Relaciones foráneas y ID único
CREATE TABLE Polizas (
    IdPolizas INT IDENTITY(1,1) PRIMARY KEY,
    EmpleadoGenero INT NOT NULL,
    SKU VARCHAR(50) NOT NULL,
    Cantidad INT NOT NULL CHECK (Cantidad > 0),
    Fecha DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_Poliza_Empleado FOREIGN KEY (EmpleadoGenero) REFERENCES Empleado(IdEmpleado),
    CONSTRAINT FK_Poliza_Inventario FOREIGN KEY (SKU) REFERENCES Inventario(SKU)
);
GO

-- Índices para optimizar búsquedas
CREATE INDEX IDX_Polizas_Fecha ON Polizas(Fecha);
GO
