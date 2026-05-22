-- Esquema de ventas e inventario (MySQL / Aiven)
-- Ejecutar después de script_clientes.sql

CREATE TABLE IF NOT EXISTS productos (
    codigo_interno VARCHAR(50) PRIMARY KEY,
    nombre_producto VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    precio_compra DECIMAL(12, 2) NOT NULL,
    precio_venta DECIMAL(12, 2) NOT NULL,
    stock_actual INT NOT NULL,
    stock_minimo INT NOT NULL,
    stock_maximo INT NOT NULL,
    activo TINYINT(1) NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS ventas (
    numero_factura INT AUTO_INCREMENT PRIMARY KEY,
    identificacion_cliente VARCHAR(50) NOT NULL,
    fecha_hora TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    forma_pago VARCHAR(20) NOT NULL,
    subtotal DECIMAL(12, 2) NOT NULL,
    iva DECIMAL(12, 2) NOT NULL,
    total DECIMAL(12, 2) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVA',
    INDEX idx_ventas_cliente (identificacion_cliente),
    INDEX idx_ventas_estado (estado)
);

CREATE TABLE IF NOT EXISTS detalles_ventas (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    numero_factura INT NOT NULL,
    codigo_producto VARCHAR(50) NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(12, 2) NOT NULL,
    subtotal DECIMAL(12, 2) NOT NULL,
    CONSTRAINT fk_detalle_venta FOREIGN KEY (numero_factura) REFERENCES ventas (numero_factura) ON DELETE CASCADE,
    CONSTRAINT fk_detalle_producto FOREIGN KEY (codigo_producto) REFERENCES productos (codigo_interno)
);
