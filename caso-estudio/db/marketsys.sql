-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 29-05-2026 a las 22:49:12
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `marketsys`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `codigo` int(11) NOT NULL,
  `documento` varchar(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `telefono` bigint(20) NOT NULL,
  `direccion` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`codigo`, `documento`, `nombre`, `telefono`, `direccion`) VALUES
(1, '123456', 'anna torres', 3213212211, 'km2'),
(2, '344567', 'pedro arenas', 345321, 'km4'),
(3, '567890', 'Andres Rodriguez', 123432, 'km6'),
(4, '147477', 'sara vargas', 6589987, 'km2'),
(5, '1234', 'Sara Maria Torres', 8585, '8745');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compra`
--

CREATE TABLE `compra` (
  `id_compra` int(11) NOT NULL,
  `numero_factura_prov` varchar(50) DEFAULT NULL,
  `fecha` datetime DEFAULT current_timestamp(),
  `codigo_proveedor` int(11) DEFAULT NULL,
  `total_compra` double DEFAULT NULL,
  `impuestos_aplicados` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `compra`
--

INSERT INTO `compra` (`id_compra`, `numero_factura_prov`, `fecha`, `codigo_proveedor`, `total_compra`, `impuestos_aplicados`) VALUES
(1, NULL, '2026-05-29 00:00:00', 34434, 238000, 38000),
(2, NULL, '2026-05-29 00:00:00', 2, 4569600, 729600),
(3, NULL, '2026-05-29 00:00:00', 1, 89250, 14250),
(4, NULL, '2026-05-29 00:00:00', 2, 3750000, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_compra`
--

CREATE TABLE `detalle_compra` (
  `id_detalle` int(11) NOT NULL,
  `id_compra` int(11) DEFAULT NULL,
  `codigo_producto` int(11) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `costo_unitario` double DEFAULT NULL,
  `subtotal` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detalle_compra`
--

INSERT INTO `detalle_compra` (`id_detalle`, `id_compra`, `codigo_producto`, `cantidad`, `costo_unitario`, `subtotal`) VALUES
(1, 1, 34567, 10, 4500, 45000),
(2, 1, 32, 44, 2000, 88000),
(3, 1, 2, 10, 6700, 67000),
(4, 2, 32, 32, 120000, 3840000),
(5, 3, 25, 25, 3000, 75000),
(6, 4, 23, 25, 150000, 3750000);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_venta`
--

CREATE TABLE `detalle_venta` (
  `id_detalle` int(11) NOT NULL,
  `numero_factura` int(11) DEFAULT NULL,
  `codigo_producto` int(11) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `precio_unitario` double DEFAULT NULL,
  `subtotal` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detalle_venta`
--

INSERT INTO `detalle_venta` (`id_detalle`, `numero_factura`, `codigo_producto`, `cantidad`, `precio_unitario`, `subtotal`) VALUES
(1, 1, 103, 1, 15000, 15000),
(2, 1, 106, 1, 21000, 21000),
(3, 1, 106, 1, 21000, 21000),
(4, 1, 324, 1, 1100, 1100),
(5, 2, 106, 1, 21000, 21000),
(6, 2, 110, 1, 6500, 6500),
(7, 2, 101, 1, 4200, 4200),
(8, 2, 108, 1, 8500, 8500),
(9, 3, 106, 1, 21000, 21000),
(10, 3, 300, 1, 5000, 5000),
(11, 3, 103, 1, 15000, 15000),
(12, 3, 107, 1, 3800, 3800),
(13, 4, 103, 1, 15000, 15000),
(14, 4, 345, 1, 5000, 5000),
(15, 5, 102, 1, 5500, 5500),
(16, 5, 345, 2, 5000, 10000),
(17, 5, 109, 1, 7000, 7000);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimientos`
--

CREATE TABLE `movimientos` (
  `id_movimiento` int(11) NOT NULL,
  `codigo_producto` int(11) NOT NULL,
  `tipo` varchar(10) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimiento_contable`
--

CREATE TABLE `movimiento_contable` (
  `id_movimiento` int(11) NOT NULL,
  `codigo_transaccion` varchar(50) DEFAULT NULL,
  `fecha` datetime DEFAULT current_timestamp(),
  `tipo_movimiento` varchar(20) DEFAULT NULL,
  `cuenta_afectada` varchar(100) DEFAULT NULL,
  `valor` double DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `movimiento_contable`
--

INSERT INTO `movimiento_contable` (`id_movimiento`, `codigo_transaccion`, `fecha`, `tipo_movimiento`, `cuenta_afectada`, `valor`, `descripcion`) VALUES
(1, NULL, '2026-05-29 00:00:00', 'EGRESO', 'Cuentas por pagar - Proveedores', 238000, 'Compra factura N° null - Proveedor: bimbo'),
(2, NULL, '2026-05-29 00:00:00', 'EGRESO', 'Cuentas por pagar - Proveedores', 4569600, 'Compra factura N° null - Proveedor: licores de boyaca'),
(3, NULL, '2026-05-29 00:00:00', 'EGRESO', 'Cuentas por pagar - Proveedores', 89250, 'Compra factura N° null - Proveedor: bavaria'),
(4, NULL, '2026-05-29 00:00:00', 'EGRESO', 'Cuentas por pagar - Proveedores', 3750000, 'Compra factura N° null - Proveedor: licores');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `codigo` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `categoria` varchar(50) DEFAULT NULL,
  `precio_compra` double DEFAULT NULL,
  `precio_venta` double DEFAULT NULL,
  `stock_actual` int(11) DEFAULT NULL,
  `stock_minimo` int(11) DEFAULT NULL,
  `stock_maximo` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`codigo`, `nombre`, `categoria`, `precio_compra`, `precio_venta`, `stock_actual`, `stock_minimo`, `stock_maximo`) VALUES
(101, 'Leche Entera 1L', 'Lácteos', 3500, 4200, 99, 20, 120),
(102, 'Pan Tajado 500g', 'Panadería', 4000, 5500, 49, 15, 60),
(103, 'Huevos Tipo A (30 und)', 'Canasta Familiar', 12000, 15000, 37, 10, 100),
(104, 'Aceite de Girasol 1L', 'Abarrotes', 8000, 11500, 80, 25, 100),
(105, 'Azúcar Blanca 1kg', 'Abarrotes', 3000, 4000, 150, 30, 300),
(106, 'Café Tostado y Molido 500g', 'Bebidas', 15000, 21000, 56, 15, 150),
(107, 'Pasta Espagueti 500g', 'Abarrotes', 2500, 3800, 119, 20, 500),
(108, 'Queso Doble Crema 250g', 'Lácteos', 6000, 8500, 44, 10, 100),
(109, 'COCA Cola 2L', 'Bebidas', 5000, 7000, 89, 20, 400),
(110, 'Jabón de Baño (3 und)', 'Aseo Personal', 4500, 6500, 69, 15, 120),
(300, ' Oreo', 'dulces', 3000, 5000, 29, 10, 50),
(324, 'chocolatina JET', 'dulces', 800, 1100, 49, 24, 90),
(345, 'Piña', 'frutas', 2000, 5000, 97, 10, 101);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedor`
--

CREATE TABLE `proveedor` (
  `codigo` int(11) NOT NULL,
  `nit` varchar(20) NOT NULL,
  `razon_social` varchar(100) NOT NULL,
  `direccion` varchar(150) DEFAULT NULL,
  `telefono` bigint(20) DEFAULT NULL,
  `correo` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `proveedor`
--

INSERT INTO `proveedor` (`codigo`, `nit`, `razon_social`, `direccion`, `telefono`, `correo`) VALUES
(1, '123-234', 'bavaria sas', 'km6', 3456789, 'bavaria@colombia.co'),
(2, '123-232', 'licores de boyaca', 'km4', 343423, 'bavaria@colombia.co');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta`
--

CREATE TABLE `venta` (
  `numero_factura` int(11) NOT NULL,
  `fecha` datetime DEFAULT current_timestamp(),
  `codigo_cliente` int(11) DEFAULT NULL,
  `forma_pago` varchar(50) DEFAULT NULL,
  `aplica_iva` tinyint(1) DEFAULT NULL,
  `subtotal` double DEFAULT NULL,
  `total` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `venta`
--

INSERT INTO `venta` (`numero_factura`, `fecha`, `codigo_cliente`, `forma_pago`, `aplica_iva`, `subtotal`, `total`) VALUES
(1, '2026-05-29 09:00:11', 1235844, 'Efectivo', 0, 58100, 58100),
(2, '2026-05-29 09:16:13', 1, 'Efectivo', 1, 40200, 47838),
(3, '2026-05-29 09:21:57', 1, 'Efectivo', 1, 44800, 53312),
(4, '2026-05-29 09:22:27', 1, 'Efectivo', 1, 20000, 23800),
(5, '2026-05-29 15:41:14', 1, 'Efectivo', 1, 22500, 26775);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`codigo`),
  ADD UNIQUE KEY `documento` (`documento`);

--
-- Indices de la tabla `compra`
--
ALTER TABLE `compra`
  ADD PRIMARY KEY (`id_compra`);

--
-- Indices de la tabla `detalle_compra`
--
ALTER TABLE `detalle_compra`
  ADD PRIMARY KEY (`id_detalle`);

--
-- Indices de la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  ADD PRIMARY KEY (`id_detalle`);

--
-- Indices de la tabla `movimientos`
--
ALTER TABLE `movimientos`
  ADD PRIMARY KEY (`id_movimiento`),
  ADD KEY `codigo_producto` (`codigo_producto`);

--
-- Indices de la tabla `movimiento_contable`
--
ALTER TABLE `movimiento_contable`
  ADD PRIMARY KEY (`id_movimiento`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`codigo`);

--
-- Indices de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  ADD PRIMARY KEY (`codigo`),
  ADD UNIQUE KEY `nit` (`nit`);

--
-- Indices de la tabla `venta`
--
ALTER TABLE `venta`
  ADD PRIMARY KEY (`numero_factura`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `compra`
--
ALTER TABLE `compra`
  MODIFY `id_compra` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `detalle_compra`
--
ALTER TABLE `detalle_compra`
  MODIFY `id_detalle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  MODIFY `id_detalle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de la tabla `movimientos`
--
ALTER TABLE `movimientos`
  MODIFY `id_movimiento` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `movimiento_contable`
--
ALTER TABLE `movimiento_contable`
  MODIFY `id_movimiento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `venta`
--
ALTER TABLE `venta`
  MODIFY `numero_factura` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `movimientos`
--
ALTER TABLE `movimientos`
  ADD CONSTRAINT `movimientos_ibfk_1` FOREIGN KEY (`codigo_producto`) REFERENCES `producto` (`codigo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
