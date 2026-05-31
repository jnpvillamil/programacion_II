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

-- crear tabla `productos`

CREATE TABLE `productos` (
  `codigo_producto` varchar(20) NOT NULL,
  `nombre_producto` varchar(120) NOT NULL,
  `categoria` varchar(50) NOT NULL,
  `precio_compra` decimal(10,2) NOT NULL,
  `precio_venta` decimal(10,2) NOT NULL,
  `stock_actual` int(11) NOT NULL DEFAULT 0,
  `stock_minimo` int(11) NOT NULL DEFAULT 0,
  `stock_maximo` int(11) NOT NULL DEFAULT 0,
  `activo` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


INSERT INTO `productos` (`codigo_producto`, `nombre_producto`, `categoria`, 
`precio_compra`, `precio_venta`, `stock_actual`, `stock_minimo`, `stock_maximo`, `activo`) VALUES
('PROD001', 'Arroz Diana', 'ALIMENTOS', 2500.00, 3200.00, 100, 10, 500, 1),
('PROD002', 'Aceite Premier', 'ALIMENTOS', 8000.00, 9800.00, 50, 5, 200, 1),
('PROD003', 'Jabon Rey', 'ASEO', 1800.00, 2500.00, 80, 10, 300, 1),
('PROD004', 'CARNE', 'ALIMENTOS', 1000.00, 1500.00, 5, 5, 100, 1);

-- --------------------------------------------------------
