

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS tienda_gestion;
USE tienda_gestion;

-- Tabla: PRODUCTOS
CREATE TABLE IF NOT EXISTS productos (
    codigo VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    precio_compra DECIMAL(15,2) NOT NULL,
    precio_venta DECIMAL(15,2) NOT NULL,
    stock_actual INT NOT NULL DEFAULT 0,
    stock_minimo INT NOT NULL DEFAULT 0,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla: CLIENTES
CREATE TABLE IF NOT EXISTS clientes (
    codigo VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tipo_identificacion VARCHAR(3) NOT NULL,
    numero_identificacion VARCHAR(20) NOT NULL UNIQUE,
    direccion VARCHAR(100),
    telefono VARCHAR(15),
    tipo_cliente VARCHAR(20) DEFAULT 'Minorista',
    activo BOOLEAN DEFAULT TRUE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla: PROVEEDORES
CREATE TABLE IF NOT EXISTS proveedores (
    codigo VARCHAR(10) PRIMARY KEY,
    razon_social VARCHAR(150) NOT NULL,
    nit VARCHAR(20) NOT NULL UNIQUE,
    direccion VARCHAR(100),
    telefono VARCHAR(15),
    email VARCHAR(100),
    activo BOOLEAN DEFAULT TRUE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla: VENTAS
CREATE TABLE IF NOT EXISTS ventas (
    numero_factura VARCHAR(20) PRIMARY KEY,
    fecha_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    codigo_cliente VARCHAR(10),
    subtotal DECIMAL(15,2) NOT NULL,
    iva DECIMAL(15,2) NOT NULL,
    total DECIMAL(15,2) NOT NULL,
    forma_pago VARCHAR(20) NOT NULL,
    estado VARCHAR(20) DEFAULT 'Activa',
    usuario_registro VARCHAR(50),
    FOREIGN KEY (codigo_cliente) REFERENCES clientes(codigo)
);

-- Tabla: DETALLE_VENTAS
CREATE TABLE IF NOT EXISTS detalle_ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero_factura VARCHAR(20),
    codigo_producto VARCHAR(10),
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(15,2) NOT NULL,
    subtotal DECIMAL(15,2) NOT NULL,
    FOREIGN KEY (numero_factura) REFERENCES ventas(numero_factura) ON DELETE CASCADE,
    FOREIGN KEY (codigo_producto) REFERENCES productos(codigo)
);

-- Tabla: COMPRAS
CREATE TABLE IF NOT EXISTS compras (
    factura_proveedor VARCHAR(30) PRIMARY KEY,
    fecha_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    codigo_proveedor VARCHAR(10),
    subtotal DECIMAL(15,2) NOT NULL,
    iva DECIMAL(15,2) NOT NULL,
    total DECIMAL(15,2) NOT NULL,
    estado VARCHAR(20) DEFAULT 'Activa',
    usuario_registro VARCHAR(50),
    FOREIGN KEY (codigo_proveedor) REFERENCES proveedores(codigo)
);

-- Tabla: DETALLE_COMPRAS
CREATE TABLE IF NOT EXISTS detalle_compras (
    id INT AUTO_INCREMENT PRIMARY KEY,
    factura_proveedor VARCHAR(30),
    codigo_producto VARCHAR(10),
    cantidad INT NOT NULL,
    precio_compra DECIMAL(15,2) NOT NULL,
    subtotal DECIMAL(15,2) NOT NULL,
    FOREIGN KEY (factura_proveedor) REFERENCES compras(factura_proveedor) ON DELETE CASCADE,
    FOREIGN KEY (codigo_producto) REFERENCES productos(codigo)
);

-- Tabla: ASIENTOS_CONTABLES
CREATE TABLE IF NOT EXISTS asientos_contables (
    codigo_asiento VARCHAR(30) PRIMARY KEY,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    descripcion VARCHAR(200) NOT NULL,
    referencia VARCHAR(50),
    usuario_registro VARCHAR(50),
    balanceado BOOLEAN DEFAULT FALSE
);

-- Tabla: MOVIMIENTOS_CONTABLES
CREATE TABLE IF NOT EXISTS movimientos_contables (
    codigo_transaccion VARCHAR(30) PRIMARY KEY,
    codigo_asiento VARCHAR(30),
    tipo_movimiento VARCHAR(10) NOT NULL,
    cuenta VARCHAR(50) NOT NULL,
    valor DECIMAL(15,2) NOT NULL,
    descripcion VARCHAR(200),
    FOREIGN KEY (codigo_asiento) REFERENCES asientos_contables(codigo_asiento)
);


-- DATOS DE PRUEBA

-- Insertar productos
INSERT INTO productos (codigo, nombre, categoria, precio_compra, precio_venta, stock_actual, stock_minimo) VALUES
('P001', 'Arroz 1kg', 'Víveres', 2500, 3000, 100, 20),
('P002', 'Aceite 1L', 'Víveres', 8000, 10000, 50, 10),
('P003', 'Jabón Rey', 'Aseo', 2000, 3500, 80, 15),
('P004', 'Cuaderno 100h', 'Papelería', 5000, 8000, 30, 5),
('P005', 'Arroz 2kg', 'Víveres', 4800, 5800, 45, 10);

-- Insertar clientes
INSERT INTO clientes (codigo, nombre, tipo_identificacion, numero_identificacion, direccion, telefono, tipo_cliente) VALUES
('C001', 'Juan Perez', 'CC', '12345678', 'Calle 1 #2-3', '3001234567', 'Minorista'),
('C002', 'Maria Gomez', 'CC', '87654321', 'Carrera 4 #5-6', '3007654321', 'Mayorista'),
('C003', 'Tienda XYZ', 'NIT', '900123456', 'Avenida Siempre Viva', '6011234567', 'Mayorista');

-- Insertar proveedores
INSERT INTO proveedores (codigo, razon_social, nit, direccion, telefono, email) VALUES
('PROV001', 'Distribuidora La Campiña', '900123456-1', 'Calle 10 #20-30', '3101234567', 'ventas@lacampina.com'),
('PROV002', 'Alimentos SAS', '800987654-2', 'Carrera 15 #45-60', '3209876543', 'pedidos@alimentossas.com'),
('PROV003', 'Papelería Central', '700456789-3', 'Avenida 5 #12-08', '6012345678', 'compras@papeleriacentral.com');


-- VERIFICAR

SELECT '✅ Base de datos creada exitosamente' AS Mensaje;
SELECT COUNT(*) AS Total_Productos FROM productos;
SELECT COUNT(*) AS Total_Clientes FROM clientes;
SELECT COUNT(*) AS Total_Proveedores FROM proveedores;