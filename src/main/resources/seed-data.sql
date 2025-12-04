-- ============================================
-- Script de Datos de Prueba
-- Sistema Inventario Coppel
-- ============================================

USE InventarioCoppel;
GO

-- Verificar si ya hay datos
IF NOT EXISTS (SELECT 1 FROM Empleado)
BEGIN
    PRINT '[INFO] Insertando empleados de prueba...';
    
    -- Empleados
    INSERT INTO Empleado (nombre, apellido, puesto) VALUES
    ('Juan', 'Pérez García', 'Gerente de Almacén'),
    ('María', 'López Martínez', 'Supervisor de Inventario'),
    ('Carlos', 'Ramírez Silva', 'Auxiliar de Almacén'),
    ('Ana', 'González Ruiz', 'Coordinadora Logística'),
    ('Luis', 'Hernández Torres', 'Operador de Inventario'),
    ('Laura', 'Díaz Morales', 'Jefa de Bodega'),
    ('Miguel', 'Sánchez Castro', 'Auxiliar de Recepción'),
    ('Patricia', 'Jiménez Flores', 'Analista de Inventario'),
    ('Roberto', 'Cruz Mendoza', 'Operador de Almacén'),
    ('Elena', 'Vargas Ortiz', 'Supervisora de Calidad');
    
    PRINT '[OK] 10 empleados insertados';
END
ELSE
BEGIN
    PRINT '[SKIP] Empleados ya existen, omitiendo insercion';
END
GO

IF NOT EXISTS (SELECT 1 FROM Inventario)
BEGIN
    PRINT '[INFO] Insertando productos de inventario...';
    
    -- Inventario - Electrónica
    INSERT INTO Inventario (sku, nombre, cantidad) VALUES
    ('ELEC-TV-001', 'Televisor Samsung 55" 4K', 45),
    ('ELEC-TV-002', 'Televisor LG 50" Smart TV', 38),
    ('ELEC-TV-003', 'Televisor Sony 65" OLED', 22),
    ('ELEC-CEL-001', 'iPhone 15 Pro 256GB', 120),
    ('ELEC-CEL-002', 'Samsung Galaxy S24 Ultra', 95),
    ('ELEC-CEL-003', 'Xiaomi Redmi Note 13', 150),
    ('ELEC-LAP-001', 'Laptop HP Pavilion 15.6"', 60),
    ('ELEC-LAP-002', 'Laptop Dell Inspiron 14"', 48),
    ('ELEC-LAP-003', 'MacBook Air M2', 30),
    ('ELEC-AUD-001', 'Audífonos Sony WH-1000XM5', 75);
    
    -- Línea Blanca
    INSERT INTO Inventario (sku, nombre, cantidad) VALUES
    ('LB-REF-001', 'Refrigerador Samsung 18 pies', 35),
    ('LB-REF-002', 'Refrigerador LG 21 pies', 28),
    ('LB-LAV-001', 'Lavadora Whirlpool 18kg', 42),
    ('LB-LAV-002', 'Lavadora Samsung Carga Frontal', 38),
    ('LB-MICRO-001', 'Microondas Samsung 1.1 pies', 65),
    ('LB-ESTUFA-001', 'Estufa Mabe 6 quemadores', 25),
    ('LB-AIRE-001', 'Aire Acondicionado LG 1.5 ton', 18),
    ('LB-CALENT-001', 'Calentador de Agua Bosch 38L', 30);
    
    -- Muebles
    INSERT INTO Inventario (sku, nombre, cantidad) VALUES
    ('MUE-SALA-001', 'Sala Modular 3 piezas Gris', 15),
    ('MUE-SALA-002', 'Sofá Cama Matrimonial', 20),
    ('MUE-REC-001', 'Recámara Queen Size Chocolate', 12),
    ('MUE-REC-002', 'Colchón King Size Restonic', 18),
    ('MUE-COM-001', 'Comedor 6 sillas Moderno', 22),
    ('MUE-COM-002', 'Mesa de Centro Minimalista', 35),
    ('MUE-OFIC-001', 'Escritorio Ejecutivo Nogal', 28);
    
    -- Ferretería
    INSERT INTO Inventario (sku, nombre, cantidad) VALUES
    ('FERR-HERR-001', 'Taladro Inalámbrico DeWalt', 55),
    ('FERR-HERR-002', 'Set Herramientas Stanley 120pz', 40),
    ('FERR-PINT-001', 'Pintura Vinílica Blanca 19L', 80),
    ('FERR-LAMP-001', 'Lámpara LED 15W Pack 4', 120),
    ('FERR-CABLE-001', 'Cable Eléctrico Cal 12 Rollo', 90);
    
    -- Deportes
    INSERT INTO Inventario (sku, nombre, cantidad) VALUES
    ('DEP-BICI-001', 'Bicicleta Montaña R29 Benotto', 25),
    ('DEP-BICI-002', 'Bicicleta Ruta R700 Mercurio', 18),
    ('DEP-FIT-001', 'Caminadora Eléctrica ProForm', 12),
    ('DEP-FIT-002', 'Mancuernas Ajustables 24kg', 45),
    ('DEP-BAL-001', 'Balón Fútbol Profesional Nike', 60);
    
    PRINT '[OK] 35 productos insertados';
END
ELSE
BEGIN
    PRINT '[SKIP] Inventario ya existe, omitiendo insercion';
END
GO

-- Resumen de datos
PRINT '';
PRINT '============================================================';
PRINT '  RESUMEN DE DATOS INSERTADOS';
PRINT '============================================================';

DECLARE @empleados INT, @productos INT;
SELECT @empleados = COUNT(*) FROM Empleado;
SELECT @productos = COUNT(*) FROM Inventario;

PRINT 'Empleados:        ' + CAST(@empleados AS VARCHAR);
PRINT 'Productos:        ' + CAST(@productos AS VARCHAR);
PRINT '';
PRINT '[SUCCESS] Base de datos lista para usar';
PRINT '============================================================';
GO
