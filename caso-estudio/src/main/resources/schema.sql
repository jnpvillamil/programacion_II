-- =============================================================================
-- Basado en Dump20260527.sql (tu base real en Clever Cloud)
-- =============================================================================
-- YA EXISTEN (no volver a crear):
--   compras, detalles_compras, movimientos_contables,
--   productos, proveedores, usuarios
-- =============================================================================
-- FALTAN para el código actual — ejecuta solo si SHOW TABLES no las muestra:
-- =============================================================================

CREATE TABLE IF NOT EXISTS clientes (
    codigo_cliente VARCHAR(50) NOT NULL,
    nombre_completo VARCHAR(200) DEFAULT NULL,
    tipo_identificacion VARCHAR(20) DEFAULT NULL,
    numero_identificacion VARCHAR(50) DEFAULT NULL,
    direccion VARCHAR(200) DEFAULT NULL,
    telefono VARCHAR(50) DEFAULT NULL,
    tipo_cliente VARCHAR(20) DEFAULT NULL,
    activo TINYINT(1) DEFAULT 1,
    PRIMARY KEY (codigo_cliente)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS ventas (
    numero_factura VARCHAR(50) NOT NULL,
    fecha DATE NOT NULL,
    codigo_cliente VARCHAR(50) DEFAULT NULL,
    nombre_cliente VARCHAR(200) DEFAULT NULL,
    subtotal DOUBLE DEFAULT NULL,
    iva DOUBLE DEFAULT NULL,
    total DOUBLE DEFAULT NULL,
    forma_pago VARCHAR(50) DEFAULT NULL,
    estado VARCHAR(20) DEFAULT 'ACTIVA',
    PRIMARY KEY (numero_factura),
    KEY codigo_cliente (codigo_cliente),
    CONSTRAINT ventas_ibfk_1 FOREIGN KEY (codigo_cliente)
        REFERENCES clientes (codigo_cliente)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS detalles_ventas (
    id_detalle INT NOT NULL AUTO_INCREMENT,
    numero_factura VARCHAR(50) NOT NULL,
    codigo_producto VARCHAR(50) NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DOUBLE NOT NULL,
    subtotal DOUBLE DEFAULT NULL,
    PRIMARY KEY (id_detalle),
    KEY numero_factura (numero_factura),
    KEY codigo_producto (codigo_producto),
    CONSTRAINT detalles_ventas_ibfk_1 FOREIGN KEY (numero_factura)
        REFERENCES ventas (numero_factura) ON DELETE CASCADE,
    CONSTRAINT detalles_ventas_ibfk_2 FOREIGN KEY (codigo_producto)
        REFERENCES productos (codigo_producto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Si ya creaste ventas sin estado, ejecuta:
-- ALTER TABLE ventas ADD COLUMN estado VARCHAR(20) DEFAULT 'ACTIVA';

-- Opcional: categoría en productos (la GUI la pide pero tu dump no la tiene)
-- ALTER TABLE productos ADD COLUMN categoria VARCHAR(50) DEFAULT NULL AFTER nombre_producto;
