/* ===================================================================
   ADVERTENCIA DE SEGURIDAD - SOLO USO DOCUMENTAL O RESPALDO
   ===================================================================
   ESTE SCRIPT CONTIENE SENTENCIAS 'DROP TABLE IF EXISTS'. 
   NO EJECUTAR EN LA BASE DE DATOS ACTIVA DE PRODUCCIÓN O AIVEN CLOUD, 
   YA QUE BORRARÁ PERMANENTEMENTE TODOS LOS DATOS INGRESADOS MANUALMENTE.
   ===================================================================
   */

-- =============================================================================
-- Tienda Minorista G4 | Estructura maestra (DDL)
-- Motor: MySQL 8.4 | Destino: Aiven Cloud | Base de datos: defaultdb
-- Contenido: 9 tablas, vista y trigger
-- Alineado con: co.edu.uptc.persistencia.* y co.edu.uptc.modelo.*
-- =============================================================================

USE defaultdb;

-- =============================================================================
-- SECCIÓN 1: ELIMINACIÓN CONTROLADA DEL ESQUEMA PREVIO
-- =============================================================================
--
-- ¿Qué hace DROP TABLE IF EXISTS?
--   Elimina la tabla solo si ya existe. Evita errores al re-ejecutar el script
--   en entornos de respaldo o reconstrucción documental.
--
-- ¿Por qué el orden de borrado es INVERSO al de creación?
--   Las claves foráneas (FK) exigen que una tabla hija no quede referenciando
--   a una tabla padre que ya fue eliminada.
--
-- SET FOREIGN_KEY_CHECKS = 0 desactiva temporalmente la validación FK durante
-- el barrido completo. Se reactiva con = 1 antes de crear el nuevo esquema.
-- =============================================================================

SET FOREIGN_KEY_CHECKS = 0;

DROP VIEW IF EXISTS cliente_activo;
DROP TRIGGER IF EXISTS trg_venta_cliente_activo;

DROP TABLE IF EXISTS detalle_compra;
DROP TABLE IF EXISTS compra;
DROP TABLE IF EXISTS detalle_venta;
DROP TABLE IF EXISTS venta;
DROP TABLE IF EXISTS movimiento_contable;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS producto;
DROP TABLE IF EXISTS proveedor;
DROP TABLE IF EXISTS cliente;

SET FOREIGN_KEY_CHECKS = 1;

-- =============================================================================
-- SECCIÓN 2: TABLAS MAESTRAS
-- Orden de creación: cliente → proveedor → producto → usuario
-- =============================================================================

CREATE TABLE cliente (
    codigo_cliente      VARCHAR(50)  NOT NULL COMMENT 'PK lógica usada por PersistenciaCliente',
    tipo_id             VARCHAR(10)  NOT NULL COMMENT 'Enum TipoIdentificacion: CC, NIT, CE, PA',
    identificacion      VARCHAR(50)  NOT NULL COMMENT 'Documento único; referenciado por venta',
    nombres             VARCHAR(100) NOT NULL,
    apellidos           VARCHAR(100) NOT NULL,
    telefono            VARCHAR(30)  NULL,
    direccion           VARCHAR(200) NULL,
    tipo_cliente        VARCHAR(50)  NOT NULL COMMENT 'Enum TipoCliente: MINORISTA, MAYORISTA',
    estado              VARCHAR(20)  NOT NULL DEFAULT 'Activo',
    PRIMARY KEY (codigo_cliente),
    UNIQUE KEY uk_cliente_identificacion (identificacion),
    INDEX idx_cliente_estado (estado),
    CONSTRAINT chk_cliente_estado CHECK (estado IN ('Activo', 'Inactivo')),
    CONSTRAINT chk_cliente_tipo CHECK (tipo_cliente IN ('MINORISTA', 'MAYORISTA')),
    CONSTRAINT chk_cliente_tipo_id CHECK (tipo_id IN ('CC', 'NIT', 'CE', 'PA'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Maestro de clientes del punto de venta';

CREATE TABLE proveedor (
    codigo_proveedor    VARCHAR(50)  NOT NULL,
    nit                 VARCHAR(50)  NOT NULL COMMENT 'Documento único; referenciado por compra',
    razon_social        VARCHAR(150) NOT NULL,
    representante_legal VARCHAR(100) NOT NULL,
    correo_electronico  VARCHAR(100) NOT NULL,
    telefono            VARCHAR(30)  NULL,
    direccion           VARCHAR(200) NULL,
    estado              VARCHAR(20)  NOT NULL DEFAULT 'Activo',
    PRIMARY KEY (codigo_proveedor),
    UNIQUE KEY uk_proveedor_nit (nit),
    INDEX idx_proveedor_estado (estado),
    CONSTRAINT chk_proveedor_estado CHECK (estado IN ('Activo', 'Inactivo'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Maestro de proveedores para el módulo de compras';

CREATE TABLE producto (
    codigo_interno   VARCHAR(50)    NOT NULL COMMENT 'PK; referenciado por detalle_venta y detalle_compra',
    nombre_producto  VARCHAR(100)   NOT NULL,
    categoria        VARCHAR(50)    NOT NULL COMMENT 'Enum CategoriaProducto',
    precio_compra    DECIMAL(12, 2) NOT NULL,
    precio_venta     DECIMAL(12, 2) NOT NULL,
    stock_actual     INT            NOT NULL,
    stock_minimo     INT            NOT NULL,
    stock_maximo     INT            NOT NULL,
    activo           TINYINT(1)     NOT NULL DEFAULT 1,
    PRIMARY KEY (codigo_interno),
    INDEX idx_producto_activo (activo),
    INDEX idx_producto_categoria (categoria),
    CONSTRAINT chk_producto_precio_compra CHECK (precio_compra > 0),
    CONSTRAINT chk_producto_precio_venta CHECK (precio_venta > 0),
    CONSTRAINT chk_producto_stock_rango CHECK (stock_minimo < stock_maximo),
    CONSTRAINT chk_producto_stock_positivo CHECK (stock_actual >= 0),
    CONSTRAINT chk_producto_categoria CHECK (categoria IN ('VIVERES', 'ASEO', 'PAPELERIA'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Inventario central; control de stock en PersistenciaProducto';

CREATE TABLE usuario (
    usuario_login  VARCHAR(50)  NOT NULL COMMENT 'PK; credencial de autenticación',
    clave          VARCHAR(255) NOT NULL,
    rol            VARCHAR(20)  NOT NULL COMMENT 'Enum RolUsuario: ADMINISTRADOR, CAJERO',
    nombres        VARCHAR(100) NOT NULL,
    apellidos      VARCHAR(100) NOT NULL,
    identificacion VARCHAR(50)  NULL,
    telefono       VARCHAR(30)  NULL,
    activo         TINYINT(1)   NOT NULL DEFAULT 1,
    PRIMARY KEY (usuario_login),
    INDEX idx_usuario_activo (activo),
    CONSTRAINT chk_usuario_rol CHECK (rol IN ('ADMINISTRADOR', 'CAJERO'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Usuarios del sistema y control de acceso RBAC';

-- =============================================================================
-- SECCIÓN 3: TABLA CONTABLE INDEPENDIENTE
-- movimiento_contable no tiene FK hacia venta/compra; se vincula por descripción
-- y codigo_transaccion según GestionContable (partida doble).
-- =============================================================================

CREATE TABLE movimiento_contable (
    codigo_transaccion VARCHAR(50)     NOT NULL COMMENT 'PK única por línea de asiento',
    fecha_movimiento   TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tipo_movimiento    VARCHAR(20)     NOT NULL COMMENT 'Enum TipoMovimiento: INGRESO, EGRESO',
    cuenta_contable    VARCHAR(100)    NOT NULL,
    valor_movimiento   DECIMAL(12, 2)  NOT NULL,
    descripcion        VARCHAR(255)    NULL,
    PRIMARY KEY (codigo_transaccion),
    INDEX idx_movimiento_fecha (fecha_movimiento),
    INDEX idx_movimiento_tipo (tipo_movimiento),
    CONSTRAINT chk_movimiento_tipo CHECK (tipo_movimiento IN ('INGRESO', 'EGRESO')),
    CONSTRAINT chk_movimiento_valor CHECK (valor_movimiento >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Libro auxiliar de partida doble (GestionContable)';

-- =============================================================================
-- SECCIÓN 4: TABLAS TRANSACCIONALES CON CLAVES FORÁNEAS
-- =============================================================================

CREATE TABLE venta (
    numero_factura          INT            NOT NULL AUTO_INCREMENT,
    identificacion_cliente  VARCHAR(50)    NOT NULL,
    fecha_hora              TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    forma_pago              VARCHAR(20)    NOT NULL COMMENT 'Enum FormaPago',
    subtotal                DECIMAL(12, 2) NOT NULL,
    iva                     DECIMAL(12, 2) NOT NULL COMMENT '19% sobre subtotal (GestionVenta)',
    total                   DECIMAL(12, 2) NOT NULL,
    estado                  VARCHAR(20)    NOT NULL DEFAULT 'ACTIVA',
    PRIMARY KEY (numero_factura),
    INDEX idx_venta_cliente (identificacion_cliente),
    INDEX idx_venta_estado (estado),
    INDEX idx_venta_fecha (fecha_hora),
    CONSTRAINT fk_venta_cliente
        FOREIGN KEY (identificacion_cliente) REFERENCES cliente (identificacion),
    CONSTRAINT chk_venta_estado CHECK (estado IN ('ACTIVA', 'ANULADA')),
    CONSTRAINT chk_venta_forma_pago CHECK (forma_pago IN ('EFECTIVO', 'TRANSFERENCIA', 'TARJETA', 'CREDITO')),
    CONSTRAINT chk_venta_subtotal CHECK (subtotal >= 0),
    CONSTRAINT chk_venta_total CHECK (total >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Encabezado de factura de venta';

CREATE TABLE detalle_venta (
    id_detalle       INT            NOT NULL AUTO_INCREMENT,
    numero_factura   INT            NOT NULL,
    codigo_producto  VARCHAR(50)    NOT NULL,
    cantidad         INT            NOT NULL,
    precio_unitario  DECIMAL(12, 2) NOT NULL,
    subtotal         DECIMAL(12, 2) NOT NULL,
    PRIMARY KEY (id_detalle),
    CONSTRAINT fk_detalle_venta
        FOREIGN KEY (numero_factura) REFERENCES venta (numero_factura)
        ON DELETE CASCADE,
    CONSTRAINT fk_detalle_venta_producto
        FOREIGN KEY (codigo_producto) REFERENCES producto (codigo_interno),
    CONSTRAINT chk_detalle_venta_cantidad CHECK (cantidad > 0),
    CONSTRAINT chk_detalle_venta_precio CHECK (precio_unitario >= 0),
    CONSTRAINT chk_detalle_venta_subtotal CHECK (subtotal >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Líneas de producto por factura de venta';

CREATE TABLE compra (
    numero_factura_proveedor VARCHAR(50)    NOT NULL COMMENT 'PK; factura del proveedor',
    nit_proveedor            VARCHAR(50)    NOT NULL,
    fecha                    TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total                    DECIMAL(12, 2) NOT NULL COMMENT 'Subtotal + IVA 19%',
    PRIMARY KEY (numero_factura_proveedor),
    INDEX idx_compra_proveedor (nit_proveedor),
    INDEX idx_compra_fecha (fecha),
    CONSTRAINT fk_compra_proveedor
        FOREIGN KEY (nit_proveedor) REFERENCES proveedor (nit),
    CONSTRAINT chk_compra_total CHECK (total >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Encabezado de factura de compra a proveedor';

CREATE TABLE detalle_compra (
    id_detalle               INT            NOT NULL AUTO_INCREMENT,
    numero_factura_proveedor VARCHAR(50)    NOT NULL,
    codigo_producto          VARCHAR(50)    NOT NULL,
    cantidad                 INT            NOT NULL,
    costo_unitario           DECIMAL(12, 2) NOT NULL,
    subtotal                 DECIMAL(12, 2) NOT NULL,
    PRIMARY KEY (id_detalle),
    CONSTRAINT fk_detalle_compra
        FOREIGN KEY (numero_factura_proveedor) REFERENCES compra (numero_factura_proveedor)
        ON DELETE CASCADE,
    CONSTRAINT fk_detalle_compra_producto
        FOREIGN KEY (codigo_producto) REFERENCES producto (codigo_interno),
    CONSTRAINT chk_detalle_compra_cantidad CHECK (cantidad > 0),
    CONSTRAINT chk_detalle_compra_costo CHECK (costo_unitario >= 0),
    CONSTRAINT chk_detalle_compra_subtotal CHECK (subtotal >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Líneas de producto por factura de compra';

-- =============================================================================
-- SECCIÓN 5: OBJETOS AUXILIARES DE NEGOCIO
-- =============================================================================

CREATE VIEW cliente_activo AS
SELECT codigo_cliente, tipo_id, identificacion, nombres, apellidos,
       telefono, direccion, tipo_cliente, estado
FROM cliente
WHERE estado = 'Activo';

DELIMITER //
CREATE TRIGGER trg_venta_cliente_activo
BEFORE INSERT ON venta
FOR EACH ROW
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM cliente
        WHERE identificacion = NEW.identificacion_cliente AND estado = 'Activo'
    ) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'El cliente no existe o se encuentra inactivo.';
    END IF;
END//
DELIMITER ;
