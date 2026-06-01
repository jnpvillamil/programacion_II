-- ============================================================
--  SISTEMA TIENDA MINORISTA - Script de base de datos MySQL
--  Instrucciones:
--    1. Abrir XAMPP y arrancar Apache + MySQL
--    2. Ir a http://localhost/phpmyadmin en el navegador
--    3. Clic en "Importar" (menú superior)
--    4. Seleccionar este archivo y pulsar "Continuar"
--    5. Ejecutar el proyecto Java normalmente
-- ============================================================

-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS tienda_minorista
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_spanish_ci;

USE tienda_minorista;

-- ─────────────────────────────────────────────────────────────
-- TABLA: clientes
-- ─────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS clientes (
    codigo               VARCHAR(50)  NOT NULL,
    nombre               VARCHAR(150) NOT NULL,
    tipoIdentificacion   VARCHAR(20)  DEFAULT NULL,
    numeroIdentificacion VARCHAR(30)  DEFAULT NULL,
    direccion            VARCHAR(200) DEFAULT NULL,
    telefono             VARCHAR(30)  DEFAULT NULL,
    tipoCliente          VARCHAR(50)  DEFAULT NULL,
    activo               TINYINT(1)   NOT NULL DEFAULT 1,
    PRIMARY KEY (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ─────────────────────────────────────────────────────────────
-- TABLA: proveedores
-- ─────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS proveedores (
    codigo    VARCHAR(50)  NOT NULL,
    nombre    VARCHAR(150) NOT NULL,
    nit       VARCHAR(30)  DEFAULT NULL,
    correo    VARCHAR(100) DEFAULT NULL,
    direccion VARCHAR(200) DEFAULT NULL,
    telefono  VARCHAR(30)  DEFAULT NULL,
    activo    TINYINT(1)   NOT NULL DEFAULT 1,
    PRIMARY KEY (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ─────────────────────────────────────────────────────────────
-- TABLA: productos
-- ─────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS productos (
    codigo        VARCHAR(50)  NOT NULL,
    nombre        VARCHAR(150) NOT NULL,
    categoria     VARCHAR(50)  DEFAULT NULL,
    precio_compra DOUBLE       NOT NULL DEFAULT 0,
    precio_venta  DOUBLE       NOT NULL DEFAULT 0,
    stock_actual  INT          NOT NULL DEFAULT 0,
    stock_minimo  INT          NOT NULL DEFAULT 0,
    activo        TINYINT(1)   NOT NULL DEFAULT 1,
    PRIMARY KEY (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ─────────────────────────────────────────────────────────────
-- TABLA: compras_proveedor
-- ─────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS compras_proveedor (
    id              INT          NOT NULL AUTO_INCREMENT,
    fecha           DATE         DEFAULT NULL,
    proveedor       VARCHAR(150) DEFAULT NULL,
    producto        VARCHAR(150) DEFAULT NULL,
    cantidad        INT          NOT NULL DEFAULT 0,
    precio_unitario DOUBLE       NOT NULL DEFAULT 0,
    total           DOUBLE       NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ─────────────────────────────────────────────────────────────
-- TABLA: ventas_cliente
-- ─────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS ventas_cliente (
    id               INT          NOT NULL AUTO_INCREMENT,
    codigo_cliente   VARCHAR(50)  DEFAULT NULL,
    nombre_cliente   VARCHAR(150) DEFAULT NULL,
    codigo_producto  VARCHAR(50)  DEFAULT NULL,
    nombre_producto  VARCHAR(150) DEFAULT NULL,
    cantidad         INT          NOT NULL DEFAULT 0,
    total            DOUBLE       NOT NULL DEFAULT 0,
    fecha            VARCHAR(60)  DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ─────────────────────────────────────────────────────────────
-- TABLA: empleados
-- ─────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS empleados (
    correo         VARCHAR(100) NOT NULL,
    password       VARCHAR(100) NOT NULL,
    tipo_empleado  VARCHAR(50)  DEFAULT 'Empleado',
    PRIMARY KEY (correo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ─────────────────────────────────────────────────────────────
-- TABLA: movimientos_contables
-- ─────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS movimientos_contables (
    codigo               VARCHAR(20)  NOT NULL,
    fecha                DATE         DEFAULT NULL,
    tipo                 VARCHAR(20)  DEFAULT NULL,
    cuenta_contable      VARCHAR(100) DEFAULT NULL,
    debito               DOUBLE       NOT NULL DEFAULT 0,
    credito              DOUBLE       NOT NULL DEFAULT 0,
    descripcion          VARCHAR(255) DEFAULT NULL,
    documento_relacionado VARCHAR(100) DEFAULT NULL,
    PRIMARY KEY (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ============================================================
--  DATOS INICIALES (tomados del proyecto original)
-- ============================================================

-- Clientes
INSERT IGNORE INTO clientes VALUES ('CLI1','1','CE','1','1','1','Minorista',1);
INSERT IGNORE INTO clientes VALUES ('CLI2','1','CC','13','1','1','Minorista',0);
INSERT IGNORE INTO clientes VALUES ('CLI3','1','CC','1','1','1','Minorista',1);
INSERT IGNORE INTO clientes VALUES ('CLI4','1','CC','12','1','1','Minorista',1);
INSERT IGNORE INTO clientes VALUES ('CLI5','Juan David','CC','12121212','121212','12121212','Minorista',1);

-- Proveedores
INSERT IGNORE INTO proveedores VALUES ('PRO1','1','1','1','11','1',0);
INSERT IGNORE INTO proveedores VALUES ('PRO2','rgeger','ergewrgrgwe','egrwerg','egrwgrwerg','egrwwerg',0);
INSERT IGNORE INTO proveedores VALUES ('PRO3','12','12','11','12','2',1);

-- Productos
INSERT IGNORE INTO productos VALUES ('PRD1','Arroz Diana','GRANOS',1000.0,2000.0,20,10,1);
INSERT IGNORE INTO productos VALUES ('PRD2','Leche','LACTEOS',1500.0,2500.0,15,8,1);
INSERT IGNORE INTO productos VALUES ('PRD3','1','OTROS',1.0,1.0,1,1,0);
INSERT IGNORE INTO productos VALUES ('PRD4','12','OTROS',12.0,12.0,12,12,0);
INSERT IGNORE INTO productos VALUES ('PRD5','12','OTROS',12.0,12.0,12,12,1);
INSERT IGNORE INTO productos VALUES ('PRD6','1','OTROS',1.0,11.0,11,1,1);
INSERT IGNORE INTO productos VALUES ('fghfghfgh','34564564','GRANOS',678678.0,678678.0,678,678,1);
INSERT IGNORE INTO productos VALUES ('dfgyhjghjghjd','gdhjghjddghj','GRANOS',456546.0,645456456.0,456456,456564,0);
INSERT IGNORE INTO productos VALUES ('PRD9','cxzczxc','EMBUTIDOS',543453.0,543345453.0,534534,435543345,0);

-- Empleados (usuarios del sistema)
INSERT IGNORE INTO empleados VALUES ('1','1','Administrador');
INSERT IGNORE INTO empleados VALUES ('3','4','Administrador');
INSERT IGNORE INTO empleados VALUES ('21','2','Empleado');

-- Compras a proveedor
INSERT IGNORE INTO compras_proveedor (fecha, proveedor, producto, cantidad, precio_unitario, total)
VALUES ('2026-05-27','1','Agua',1,2000.0,2000.0);

-- Movimientos contables
INSERT IGNORE INTO movimientos_contables VALUES ('MOV00001','2026-05-27','Egreso','Compra de mercancía',24000.0,0.0,'Compra de Agua','1');
INSERT IGNORE INTO movimientos_contables VALUES ('MOV00002','2026-05-27','Egreso','Compra de mercancía',24000.0,0.0,'Compra de Agua','1');
INSERT IGNORE INTO movimientos_contables VALUES ('MOV00003','2026-05-27','Egreso','Compra de mercancía',22000.0,0.0,'Compra de Agua','1');
INSERT IGNORE INTO movimientos_contables VALUES ('MOV00004','2026-05-27','Egreso','Compra de mercancía',2000.0,0.0,'Compra de Agua','1');

-- Ventas a clientes
INSERT IGNORE INTO ventas_cliente (codigo_cliente, nombre_cliente, codigo_producto, nombre_producto, cantidad, total, fecha)
VALUES ('CLI1','1','576675','765675',2,1512.0,'May 30, 2026, 11:15:19 PM');
INSERT IGNORE INTO ventas_cliente (codigo_cliente, nombre_cliente, codigo_producto, nombre_producto, cantidad, total, fecha)
VALUES ('CLI1','1','1920','Agua',4,12000.0,'May 30, 2026, 11:15:55 PM');

-- ============================================================
--  FIN DEL SCRIPT
-- ============================================================
