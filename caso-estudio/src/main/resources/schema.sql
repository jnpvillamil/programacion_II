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
    fecha_hora DATETIME NOT NULL,
    cliente_id VARCHAR(50) NOT NULL,
    subtotal DECIMAL(15,2) NOT NULL,
    iva_aplicado DECIMAL(15,2) NOT NULL,
    total_venta DECIMAL(15,2) NOT NULL,
    forma_pago VARCHAR(50) NOT NULL,
    estado VARCHAR(20) DEFAULT 'ACTIVA',
    PRIMARY KEY (numero_factura),
    KEY cliente_id (cliente_id),
    CONSTRAINT ventas_ibfk_1 FOREIGN KEY (cliente_id)
        REFERENCES clientes (codigo_cliente)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS detalles_ventas (
    id INT NOT NULL AUTO_INCREMENT,
    numero_factura VARCHAR(50) NOT NULL,
    producto_codigo VARCHAR(50) NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(15,2) NOT NULL,
    subtotal DECIMAL(15,2) NOT NULL,
    PRIMARY KEY (id),
    KEY numero_factura (numero_factura),
    KEY producto_codigo (producto_codigo),
    CONSTRAINT detalles_ventas_ibfk_1 FOREIGN KEY (numero_factura)
        REFERENCES ventas (numero_factura) ON DELETE CASCADE,
    CONSTRAINT detalles_ventas_ibfk_2 FOREIGN KEY (producto_codigo)
        REFERENCES productos (codigo_producto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
