-- Creación de Base de Datos
CREATE DATABASE InventarioCoppel;
GO

-- Seleccionar la base de datos para usarla
USE InventarioCoppel;
GO

-- Tabla: Inventario
CREATE TABLE Inventario (
    sku VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad >= 0)
);
GO

-- Tabla: Empleado (id_empleado NO es autoincremental para permitir inserción manual en pruebas)
CREATE TABLE Empleado (
    id_empleado INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    puesto VARCHAR(50) NOT NULL
);
GO

-- Tabla: Polizas
CREATE TABLE Polizas (
    id_poliza INT IDENTITY(1,1) PRIMARY KEY,
    empleado_genero INT NOT NULL,
    sku VARCHAR(50) NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    fecha DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_Poliza_Empleado FOREIGN KEY (empleado_genero) REFERENCES Empleado(id_empleado),
    CONSTRAINT FK_Poliza_Inventario FOREIGN KEY (sku) REFERENCES Inventario(SKU)
);
GO

-- Índices para optimizar búsquedas
CREATE INDEX IDX_Polizas_Fecha ON Polizas(Fecha);
GO