
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

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`codigo_producto`, `nombre_producto`, `categoria`, 
`precio_compra`, `precio_venta`, `stock_actual`, `stock_minimo`, `stock_maximo`, `activo`) VALUES
('PROD001', 'Arroz Diana', 'ALIMENTOS', 2500.00, 3200.00, 100, 10, 500, 1),
('PROD002', 'Aceite Premier', 'ALIMENTOS', 8000.00, 9800.00, 50, 5, 200, 1),
('PROD003', 'Jabon Rey', 'ASEO', 1800.00, 2500.00, 80, 10, 300, 1),
('PROD004', 'CARNE', 'ALIMENTOS', 1000.00, 1500.00, 5, 5, 100, 1);
