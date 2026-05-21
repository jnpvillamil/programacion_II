-- 1. Crear tabla de productos (por seguridad)
CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DOUBLE NOT NULL,
    stock INT NOT NULL
);

-- 2. Crear tabla de clientes
CREATE TABLE IF NOT EXISTS clientes (
    codigo_cliente VARCHAR(50) PRIMARY KEY,
    tipo_id VARCHAR(10) NOT NULL,
    identificacion VARCHAR(50) NOT NULL UNIQUE,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    telefono VARCHAR(30),
    direccion VARCHAR(200),
    tipo_cliente VARCHAR(50) NOT NULL,
    estado VARCHAR(20) DEFAULT 'Activo'
);

-- 3. Limpiar inserciones previas para evitar duplicidad en las pruebas
DELETE FROM clientes WHERE codigo_cliente IN ('CLI001', 'CLI002', 'CLI003');

-- 4. Insertar 3 clientes reales de prueba
INSERT INTO clientes (codigo_cliente, tipo_id, identificacion, nombres, apellidos, telefono, direccion, tipo_cliente, estado) 
VALUES 
('CLI001', 'CC', '1012345678', 'Juan Carlos', 'Pérez Gómez', '3101234567', 'Calle 45 # 12-34', 'MINORISTA', 'Activo'),
('CLI002', 'CC', '1023456789', 'María Camila', 'Rodríguez Silva', '3209876543', 'Carrera 15 # 88-10', 'MINORISTA', 'Activo'),
('CLI003', 'CC', '987654321', 'John', 'Smith', '3155554433', 'Avenida Siempre Viva 123', 'MAYORISTA', 'Activo');