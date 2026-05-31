CREATE DATABASE IF NOT EXISTS proyecto_sistema_gestion_contable
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE proyecto_sistema_gestion_contable;

CREATE TABLE IF NOT EXISTS productos (
  codigoProducto VARCHAR(30) NOT NULL,
  nombreProducto VARCHAR(120) NOT NULL,
  categoria VARCHAR(50) NOT NULL,
  precioCompra DECIMAL(12,2) NOT NULL DEFAULT 0,
  precioVenta DECIMAL(12,2) NOT NULL DEFAULT 0,
  stockActual INT NOT NULL DEFAULT 0,
  stockMinimo INT NOT NULL DEFAULT 0,
  stockMaximo INT NOT NULL DEFAULT 0,
  aplicaIva BOOLEAN NOT NULL DEFAULT TRUE,
  estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
  PRIMARY KEY (codigoProducto),
  INDEX idx_productos_estado (estado),
  INDEX idx_productos_stock_minimo (stockActual, stockMinimo)
);

CREATE TABLE IF NOT EXISTS clientes (
  codigoCliente VARCHAR(30) NOT NULL,
  nombre VARCHAR(120) NOT NULL,
  tipoIdentificacion VARCHAR(10) NOT NULL,
  numeroIdentificacion VARCHAR(30) NOT NULL,
  direccion VARCHAR(160),
  telefono VARCHAR(30),
  tipoCliente VARCHAR(30) NOT NULL,
  estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
  PRIMARY KEY (codigoCliente),
  UNIQUE KEY uk_clientes_identificacion (tipoIdentificacion, numeroIdentificacion),
  INDEX idx_clientes_estado (estado)
);

CREATE TABLE IF NOT EXISTS proveedores (
  codigoProveedor VARCHAR(30) NOT NULL,
  razonSocial VARCHAR(150) NOT NULL,
  nit VARCHAR(30) NOT NULL,
  direccion VARCHAR(160),
  telefono VARCHAR(30),
  correoElectronico VARCHAR(120),
  estado VARCHAR(20) DEFAULT 'ACTIVO',
  PRIMARY KEY (codigoProveedor),
  UNIQUE KEY uk_proveedores_nit (nit),
  INDEX idx_proveedores_estado (estado)
);

CREATE TABLE IF NOT EXISTS ventas (
  numeroFactura VARCHAR(30) NOT NULL,
  fechaHora DATETIME NOT NULL,
  cliente VARCHAR(180) NOT NULL,
  codigoCliente VARCHAR(30) NOT NULL,
  formaPago VARCHAR(30) NOT NULL,
  subtotal DECIMAL(12,2) NOT NULL DEFAULT 0,
  impuestos DECIMAL(12,2) NOT NULL DEFAULT 0,
  total DECIMAL(12,2) NOT NULL DEFAULT 0,
  estado VARCHAR(30) NOT NULL DEFAULT 'ACTIVA',
  motivoAnulacion VARCHAR(255),
  fechaAnulacion DATETIME,
  PRIMARY KEY (numeroFactura),
  INDEX idx_ventas_fecha_hora (fechaHora),
  INDEX idx_ventas_estado (estado),
  INDEX idx_ventas_forma_pago (formaPago),
  INDEX idx_ventas_cliente (cliente),
  INDEX idx_ventas_codigo_cliente (codigoCliente),
  CONSTRAINT fk_ventas_cliente
    FOREIGN KEY (codigoCliente) REFERENCES clientes (codigoCliente)
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS detalle_ventas (
  idDetalle BIGINT NOT NULL AUTO_INCREMENT,
  numeroFactura VARCHAR(30) NOT NULL,
  codigoProducto VARCHAR(30) NOT NULL,
  cantidad INT NOT NULL,
  precioUnitario DECIMAL(12,2) NOT NULL DEFAULT 0,
  subtotal DECIMAL(12,2) NOT NULL DEFAULT 0,
  PRIMARY KEY (idDetalle),
  INDEX idx_detalle_ventas_factura (numeroFactura),
  INDEX idx_detalle_ventas_producto (codigoProducto),
  CONSTRAINT fk_detalle_ventas_venta
    FOREIGN KEY (numeroFactura) REFERENCES ventas (numeroFactura)
    ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_detalle_ventas_producto
    FOREIGN KEY (codigoProducto) REFERENCES productos (codigoProducto)
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS compras (
  idCompra BIGINT NOT NULL AUTO_INCREMENT,
  numeroFacturaProveedor VARCHAR(40) NOT NULL,
  fecha DATE NOT NULL,
  codigoProveedor VARCHAR(30) NOT NULL,
  formaPago VARCHAR(30) NOT NULL,
  subtotal DECIMAL(12,2) NOT NULL DEFAULT 0,
  impuestos DECIMAL(12,2) NOT NULL DEFAULT 0,
  totalCompra DECIMAL(12,2) NOT NULL DEFAULT 0,
  estado VARCHAR(30),
  motivoAnulacion VARCHAR(255),
  PRIMARY KEY (idCompra),
  UNIQUE KEY uk_compras_numero_factura (numeroFacturaProveedor),
  INDEX idx_compras_proveedor_fecha (codigoProveedor, fecha),
  INDEX idx_compras_fecha (fecha),
  CONSTRAINT fk_compras_proveedor
    FOREIGN KEY (codigoProveedor) REFERENCES proveedores (codigoProveedor)
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS detalle_compras (
  idDetalle BIGINT NOT NULL AUTO_INCREMENT,
  idCompra BIGINT NOT NULL,
  codigoProducto VARCHAR(30),
  cantidad INT NOT NULL,
  costoUnitario DECIMAL(12,2) NOT NULL DEFAULT 0,
  subtotal DECIMAL(12,2) NOT NULL DEFAULT 0,
  impuestos DECIMAL(12,2) NOT NULL DEFAULT 0,
  total DECIMAL(12,2) NOT NULL DEFAULT 0,
  PRIMARY KEY (idDetalle),
  INDEX idx_detalle_compras_compra (idCompra),
  INDEX idx_detalle_compras_producto (codigoProducto),
  CONSTRAINT fk_detalle_compras_compra
    FOREIGN KEY (idCompra) REFERENCES compras (idCompra)
    ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_detalle_compras_producto
    FOREIGN KEY (codigoProducto) REFERENCES productos (codigoProducto)
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS movimientos_inventario (
  idMovimiento BIGINT NOT NULL AUTO_INCREMENT,
  codigoProducto VARCHAR(30) NOT NULL,
  tipoMovimiento VARCHAR(30) NOT NULL,
  cantidad INT NOT NULL,
  fecha DATE NOT NULL,
  descripcion VARCHAR(255),
  PRIMARY KEY (idMovimiento),
  INDEX idx_mov_inv_producto_fecha (codigoProducto, fecha),
  INDEX idx_mov_inv_tipo_fecha (tipoMovimiento, fecha),
  CONSTRAINT fk_mov_inv_producto
    FOREIGN KEY (codigoProducto) REFERENCES productos (codigoProducto)
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS movimientos_contables (
  codigoTransaccion VARCHAR(30) NOT NULL,
  fecha DATE NOT NULL,
  tipoMovimiento VARCHAR(30),
  cuentaContable VARCHAR(80) NOT NULL,
  valor DECIMAL(12,2) NOT NULL DEFAULT 0,
  descripcion VARCHAR(255),
  origen VARCHAR(80),
  referencia VARCHAR(80),
  PRIMARY KEY (codigoTransaccion),
  INDEX idx_mov_cont_fecha (fecha),
  INDEX idx_mov_cont_cuenta_fecha (cuentaContable, fecha),
  INDEX idx_mov_cont_referencia (referencia)
);

CREATE TABLE IF NOT EXISTS devoluciones_venta (
  codigoDevolucion VARCHAR(30) NOT NULL,
  numeroFactura VARCHAR(30) NOT NULL,
  codigoProducto VARCHAR(30) NOT NULL,
  nombreProducto VARCHAR(120) NOT NULL,
  cantidadDevuelta INT NOT NULL,
  valorDevuelto DECIMAL(12,2) NOT NULL DEFAULT 0,
  fechaHora DATETIME NOT NULL,
  motivo VARCHAR(255),
  PRIMARY KEY (codigoDevolucion),
  INDEX idx_dev_venta_factura_fecha (numeroFactura, fechaHora),
  INDEX idx_dev_venta_factura_producto (numeroFactura, codigoProducto),
  CONSTRAINT fk_dev_venta_venta
    FOREIGN KEY (numeroFactura) REFERENCES ventas (numeroFactura)
    ON UPDATE CASCADE,
  CONSTRAINT fk_dev_venta_producto
    FOREIGN KEY (codigoProducto) REFERENCES productos (codigoProducto)
    ON UPDATE CASCADE
);
