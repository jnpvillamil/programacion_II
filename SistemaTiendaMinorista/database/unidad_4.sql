-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.4.32-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.17.0.7270
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Volcando datos para la tabla tienda_minorista.clientes: ~2 rows (aproximadamente)
INSERT INTO `clientes` (`codigo`, `nombre`, `tipo_identificacion`, `numero_identificacion`, `direccion`, `telefono`, `tipo_cliente`, `activo`) VALUES
	('C001', 'Sebastian', 'CC', '123456', 'Sogamoso', '3200000000', 'Minorista', 1),
	('C002', 'santiago', 'CC', '1057611252', 'calle 4 #25-50', '3155112709', 'Mayorista', 1);

-- Volcando datos para la tabla tienda_minorista.compras: ~0 rows (aproximadamente)
INSERT INTO `compras` (`numero_factura`, `fecha`, `proveedor_codigo`, `subtotal`, `iva`, `total`) VALUES
	('03242', '2026-05-30', 'P001', 9332.00, 1773.08, 11105.08);

-- Volcando datos para la tabla tienda_minorista.detalle_compras: ~0 rows (aproximadamente)
INSERT INTO `detalle_compras` (`id`, `numero_factura`, `codigo_producto`, `nombre_producto`, `cantidad`, `costo_unitario`, `subtotal`) VALUES
	(2, '03242', '213', 'lechuga', 4, 2333.00, 9332.00);

-- Volcando datos para la tabla tienda_minorista.productos: ~8 rows (aproximadamente)
INSERT INTO `productos` (`codigo`, `nombre`, `categoria`, `precio_compra`, `precio_venta`, `stock_actual`, `stock_minimo`, `estado`) VALUES
	('213', 'lechuga', 'Víveres', 2333.00, 3032.90, 4, 5, 'Activo'),
	('2356', 'cajas', 'Víveres', 200.00, 260.00, 2, 5, 'Activo'),
	('555', 'Arroz nuevo', 'Víveres', 3555.00, 4000.00, 11, 6, 'Activo'),
	('asd', 'papa', 'Víveres', 455555.00, 233.00, 56, 12, 'Inactivo'),
	('C0009', 'pap', 'Víveres', 2000.00, 2600.00, 60, 5, 'Activo'),
	('C003', 'garbanzos libras', 'Víveres', 3000.00, 3900.00, 4, 5, 'Activo'),
	('C0045', 'lentejas', 'Víveres', 23500.00, 2990.00, 5, 5, 'Activo'),
	('C005', 'leche', 'Aseo', 1200.00, 1560.00, 50, 5, 'Activo');

-- Volcando datos para la tabla tienda_minorista.proveedores: ~0 rows (aproximadamente)
INSERT INTO `proveedores` (`codigo`, `razon_social`, `nit`, `direccion`, `telefono`, `correo`, `activo`) VALUES
	('P001', 'No se sabe', '1057611252', 'calle 5 # 26-50', '3155112709', 'srodrigu@email.com', 1),
	('P002', 'malo', 'no se', 'calle 045-56', '3104104099', 'malo@email.com', 1);

-- Volcando datos para la tabla tienda_minorista.ventas: ~2 rows (aproximadamente)
INSERT INTO `ventas` (`id`, `fecha`, `cliente_codigo`, `codigo_producto`, `cantidad`, `precio`, `subtotal`) VALUES
	(1, '2026-05-30 00:00:00', 'C001', '2356', 2, 260.00, 520.00),
	(2, '2026-05-30 00:00:00', 'C002', '555', 4, 4000.00, 16000.00),
	(3, '2026-05-30 00:00:00', 'C002', 'C003', 2, 3900.00, 7800.00);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
