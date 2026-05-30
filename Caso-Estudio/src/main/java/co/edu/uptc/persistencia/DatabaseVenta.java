package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.IGestionVenta;
import co.edu.uptc.modelo.*;
import co.edu.uptc.Dtos.ReporteVentasDTO;
import co.edu.uptc.Util.ConexionBD;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DatabaseVenta implements IGestionVenta {
    
    private ConexionBD conexionBD = new ConexionBD();
    
    @Override
    public void crearVenta(Venta venta) {
        Connection conn = null;
        try {
            conn = conexionBD.getConexion();
            conn.setAutoCommit(false); 
            
           
            String sqlVenta = "INSERT INTO ventas (numero_factura, fecha_hora, codigo_cliente, subtotal, iva, total, forma_pago, estado, usuario_registro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement ps = conn.prepareStatement(sqlVenta)) {
                ps.setString(1, venta.getNumeroFactura());
                ps.setTimestamp(2, Timestamp.valueOf(venta.getFechaHora()));
                ps.setString(3, venta.getCliente().getCodigo());
                ps.setDouble(4, venta.getSubtotal());
                ps.setDouble(5, venta.getIva());
                ps.setDouble(6, venta.getTotal());
                ps.setString(7, venta.getFormaPago());
                ps.setString(8, venta.getEstado());
                ps.setString(9, "admin");
                ps.executeUpdate();
            }
            
           
            String sqlDetalle = "INSERT INTO detalle_ventas (numero_factura, codigo_producto, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
            String sqlUpdateStock = "UPDATE productos SET stock_actual = stock_actual - ? WHERE codigo = ?";
            
            for (DetalleVenta detalle : venta.getDetalles()) {
                
                try (PreparedStatement ps = conn.prepareStatement(sqlDetalle)) {
                    ps.setString(1, venta.getNumeroFactura());
                    ps.setString(2, detalle.getProducto().getCodigo());
                    ps.setInt(3, detalle.getCantidad());
                    ps.setDouble(4, detalle.getPrecioUnitario());
                    ps.setDouble(5, detalle.getSubtotal());
                    ps.executeUpdate();
                }
                
             
                try (PreparedStatement ps = conn.prepareStatement(sqlUpdateStock)) {
                    ps.setInt(1, detalle.getCantidad());
                    ps.setString(2, detalle.getProducto().getCodigo());
                    ps.executeUpdate();
                }
            }
            
            conn.commit();
            System.out.println(" Venta registrada en BD: " + venta.getNumeroFactura());
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
                System.err.println(" Error al registrar venta, transacción revertida: " + e.getMessage());
            } catch (SQLException ex) {
                System.err.println(" Error en rollback: " + ex.getMessage());
            }
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println(" Error restaurando autoCommit: " + e.getMessage());
            }
        }
    }
    
    @Override
    public Venta buscarVenta(String numeroFactura) {
        String sql = "SELECT * FROM ventas WHERE numero_factura = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, numeroFactura);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
             
                DatabaseCliente daoCliente = new DatabaseCliente();
                Cliente cliente = daoCliente.buscar(rs.getString("codigo_cliente"));
                
                Venta venta = new Venta(
                    rs.getString("numero_factura"),
                    cliente,
                    rs.getString("forma_pago"),
                    rs.getString("usuario_registro")
                );
                
            
                cargarDetallesVenta(venta);
                
                return venta;
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al buscar venta: " + e.getMessage());
        }
        return null;
    }
    
    private void cargarDetallesVenta(Venta venta) {
        String sql = "SELECT * FROM detalle_ventas WHERE numero_factura = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, venta.getNumeroFactura());
            ResultSet rs = ps.executeQuery();
            
            DatabaseProducto daoProducto = new DatabaseProducto();
            
            while (rs.next()) {
                String codigoProducto = rs.getString("codigo_producto");
                Producto producto = daoProducto.buscar(codigoProducto);
                
                
                if (producto == null) {
                    System.err.println("⚠️ Producto no encontrado: " + codigoProducto);
                    continue;  
                }
                
                int cantidad = rs.getInt("cantidad");
                double precioUnitario = rs.getDouble("precio_unitario");
                
            
                if (producto.getStockActual() < cantidad) {
                    System.err.println("⚠ Stock insuficiente para: " + producto.getNombre());
                    continue;
                }
                
                venta.agregarDetalle(producto, cantidad);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al cargar detalles: " + e.getMessage());
        }
    }
    
    @Override
    public List<Venta> listarVentas() {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM ventas ORDER BY fecha_hora DESC";
        
        try (Connection conn = conexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            DatabaseCliente daoCliente = new DatabaseCliente();
            
            while (rs.next()) {
                Cliente cliente = daoCliente.buscar(rs.getString("codigo_cliente"));
                
                Venta venta = new Venta(
                    rs.getString("numero_factura"),
                    cliente,
                    rs.getString("forma_pago"),
                    rs.getString("usuario_registro")
                );
                
                
                cargarDetallesVenta(venta);
                ventas.add(venta);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al listar ventas: " + e.getMessage());
        }
        return ventas;
    }
    
    @Override
    public void anularVenta(String numeroFactura) {
        Connection conn = null;
        try {
            conn = conexionBD.getConexion();
            conn.setAutoCommit(false);
            
           
            String sqlSelect = "SELECT * FROM detalle_ventas WHERE numero_factura = ?";
            List<DetalleVenta> detalles = new ArrayList<>();
            
            try (PreparedStatement ps = conn.prepareStatement(sqlSelect)) {
                ps.setString(1, numeroFactura);
                ResultSet rs = ps.executeQuery();
                
                DatabaseProducto daoProducto = new DatabaseProducto();
                while (rs.next()) {
                    Producto producto = daoProducto.buscar(rs.getString("codigo_producto"));
                    int cantidad = rs.getInt("cantidad");
                    double precioUnitario = rs.getDouble("precio_unitario");
                    detalles.add(new DetalleVenta(producto, cantidad, precioUnitario));
                }
            }
            
     
            String sqlUpdateStock = "UPDATE productos SET stock_actual = stock_actual + ? WHERE codigo = ?";
            for (DetalleVenta detalle : detalles) {
                try (PreparedStatement ps = conn.prepareStatement(sqlUpdateStock)) {
                    ps.setInt(1, detalle.getCantidad());
                    ps.setString(2, detalle.getProducto().getCodigo());
                    ps.executeUpdate();
                }
            }
            
           
            String sqlUpdate = "UPDATE ventas SET estado = 'Anulada' WHERE numero_factura = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
                ps.setString(1, numeroFactura);
                ps.executeUpdate();
            }
            
            conn.commit();
            System.out.println(" Venta anulada en BD: " + numeroFactura);
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
                System.err.println(" Error al anular venta, transacción revertida: " + e.getMessage());
            } catch (SQLException ex) {
                System.err.println(" Error en rollback: " + ex.getMessage());
            }
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println(" Error restaurando autoCommit: " + e.getMessage());
            }
        }
    }
    
    @Override
    public List<Venta> listarVentasPorFecha(LocalDate fecha) {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM ventas WHERE DATE(fecha_hora) = ? AND estado = 'Activa'";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, fecha.toString());
            ResultSet rs = ps.executeQuery();
            
            DatabaseCliente daoCliente = new DatabaseCliente();
            
            while (rs.next()) {
                Cliente cliente = daoCliente.buscar(rs.getString("codigo_cliente"));
                Venta venta = new Venta(
                    rs.getString("numero_factura"),
                    cliente,
                    rs.getString("forma_pago"),
                    rs.getString("usuario_registro")
                );
                cargarDetallesVenta(venta);
                ventas.add(venta);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al listar ventas por fecha: " + e.getMessage());
        }
        return ventas;
    }
    
    @Override
    public List<Venta> listarVentasPorCliente(String codigoCliente) {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM ventas WHERE codigo_cliente = ? AND estado = 'Activa'";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, codigoCliente);
            ResultSet rs = ps.executeQuery();
            
            DatabaseCliente daoCliente = new DatabaseCliente();
            Cliente cliente = daoCliente.buscar(codigoCliente);
            
            while (rs.next()) {
                Venta venta = new Venta(
                    rs.getString("numero_factura"),
                    cliente,
                    rs.getString("forma_pago"),
                    rs.getString("usuario_registro")
                );
                cargarDetallesVenta(venta);
                ventas.add(venta);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al listar ventas por cliente: " + e.getMessage());
        }
        return ventas;
    }
    
    @Override
    public List<Venta> listarVentasPorFormaPago(String formaPago) {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM ventas WHERE forma_pago = ? AND estado = 'Activa'";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, formaPago);
            ResultSet rs = ps.executeQuery();
            
            DatabaseCliente daoCliente = new DatabaseCliente();
            
            while (rs.next()) {
                Cliente cliente = daoCliente.buscar(rs.getString("codigo_cliente"));
                Venta venta = new Venta(
                    rs.getString("numero_factura"),
                    cliente,
                    rs.getString("forma_pago"),
                    rs.getString("usuario_registro")
                );
                cargarDetallesVenta(venta);
                ventas.add(venta);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al listar ventas por forma de pago: " + e.getMessage());
        }
        return ventas;
    }
    
    @Override
    public List<Venta> listarVentasPorPeriodo(LocalDate inicio, LocalDate fin) {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM ventas WHERE DATE(fecha_hora) BETWEEN ? AND ? AND estado = 'Activa'";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, inicio.toString());
            ps.setString(2, fin.toString());
            ResultSet rs = ps.executeQuery();
            
            DatabaseCliente daoCliente = new DatabaseCliente();
            
            while (rs.next()) {
                Cliente cliente = daoCliente.buscar(rs.getString("codigo_cliente"));
                Venta venta = new Venta(
                    rs.getString("numero_factura"),
                    cliente,
                    rs.getString("forma_pago"),
                    rs.getString("usuario_registro")
                );
                cargarDetallesVenta(venta);
                ventas.add(venta);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al listar ventas por período: " + e.getMessage());
        }
        return ventas;
    }
    
    @Override
    public co.edu.uptc.Dtos.ReporteDiarioJSONDTO generarDatosJSONDiario(LocalDate fecha) {
        List<Venta> ventasDelDia = listarVentasPorFecha(fecha);
        
        double totalVentas = 0;
        double totalCompras = 0;  
        Map<String, Double> ventasPorFormaPago = new HashMap<>();
        Map<String, Integer> productosVendidos = new HashMap<>();
        
        for (Venta v : ventasDelDia) {
            totalVentas += v.getTotal();
            ventasPorFormaPago.merge(v.getFormaPago(), v.getTotal(), Double::sum);
            
            for (DetalleVenta d : v.getDetalles()) {
                productosVendidos.merge(d.getProducto().getNombre(), d.getCantidad(), Integer::sum);
            }
        }
        
   
        double costoVentas = 0;
        for (Venta v : ventasDelDia) {
            for (DetalleVenta d : v.getDetalles()) {
                costoVentas += d.getCantidad() * d.getProducto().getPrecioCompra();
            }
        }
        double utilidadBruta = totalVentas - costoVentas;
        
    
        List<Map<String, Object>> ventasPorPagoList = new ArrayList<>();
        for (Map.Entry<String, Double> entry : ventasPorFormaPago.entrySet()) {
            Map<String, Object> item = new HashMap<>();
            item.put("tipo", entry.getKey());
            item.put("valor", entry.getValue());
            ventasPorPagoList.add(item);
        }
        
  
        List<Map<String, Object>> productosTopList = new ArrayList<>();
        productosVendidos.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(5)
            .forEach(entry -> {
                Map<String, Object> item = new HashMap<>();
                item.put("codigo", "P" + entry.getKey().substring(0, Math.min(3, entry.getKey().length())).toUpperCase());
                item.put("nombre", entry.getKey());
                item.put("cantidad_vendida", entry.getValue());
                productosTopList.add(item);
            });
        
      
        Map<String, Double> resumenContable = new HashMap<>();
        resumenContable.put("Ingresos", totalVentas);
        resumenContable.put("Utilidad", utilidadBruta);
        
        co.edu.uptc.Dtos.ReporteDiarioJSONDTO reporte = new co.edu.uptc.Dtos.ReporteDiarioJSONDTO();
        reporte.setFecha(fecha.toString());
        reporte.setTotal_ventas(totalVentas);
        reporte.setTotal_compras(totalCompras);
        reporte.setUtilidad_bruta(utilidadBruta);
        reporte.setVentas_por_forma_pago(ventasPorPagoList);
        reporte.setProductos_mas_vendidos(productosTopList);
        reporte.setResumen_contable(resumenContable);
        
        return reporte;
    }
    @Override
    public double calcularTotalVentasPeriodo(LocalDate inicio, LocalDate fin) {
        double total = 0;
        String sql = "SELECT SUM(total) as total FROM ventas WHERE DATE(fecha_hora) BETWEEN ? AND ? AND estado = 'Activa'";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, inicio.toString());
            ps.setString(2, fin.toString());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                total = rs.getDouble("total");
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al calcular total ventas: " + e.getMessage());
        }
        return total;
    }
    
    @Override
    public int contarVentasPorFormaPago(String formaPago) {
        int count = 0;
        String sql = "SELECT COUNT(*) as total FROM ventas WHERE forma_pago = ? AND estado = 'Activa'";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, formaPago);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                count = rs.getInt("total");
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al contar ventas: " + e.getMessage());
        }
        return count;
    }
    
    // ========== MÉTODOS DE REPORTES ==========
    
    @Override
    public ReporteVentasDTO generarReporteVentasDiarias(LocalDate fecha) {
        ReporteVentasDTO reporte = new ReporteVentasDTO();
        reporte.setFecha(fecha);
        
        String sql = "SELECT v.* FROM ventas v WHERE DATE(v.fecha_hora) = ? AND v.estado = 'Activa'";
        List<Venta> ventas = new ArrayList<>();
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, fecha.toString());
            ResultSet rs = ps.executeQuery();
            
            DatabaseCliente daoCliente = new DatabaseCliente();
            
            while (rs.next()) {
                Cliente cliente = daoCliente.buscar(rs.getString("codigo_cliente"));
                Venta venta = new Venta(
                    rs.getString("numero_factura"),
                    cliente,
                    rs.getString("forma_pago"),
                    rs.getString("usuario_registro")
                );
                cargarDetallesVenta(venta);
                ventas.add(venta);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error en reporte diario: " + e.getMessage());
        }
        
        double totalVentas = 0;
        double totalIVA = 0;
        Map<String, Double> ventasPorFormaPago = new HashMap<>();
        Map<String, Integer> productosVendidos = new HashMap<>();
        
        for (Venta v : ventas) {
            totalVentas += v.getTotal();
            totalIVA += v.getIva();
            ventasPorFormaPago.merge(v.getFormaPago(), v.getTotal(), Double::sum);
            
            for (DetalleVenta d : v.getDetalles()) {
                productosVendidos.merge(d.getProducto().getNombre(), d.getCantidad(), Integer::sum);
            }
        }
        
        reporte.setTotalVentas(totalVentas);
        reporte.setTotalIVA(totalIVA);
        reporte.setNumeroFacturas(ventas.size());
        reporte.setVentasPorFormaPago(ventasPorFormaPago);
        
        List<Object[]> topProductos = productosVendidos.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(10)
            .map(e -> new Object[]{e.getKey(), e.getValue()})
            .collect(Collectors.toList());
        reporte.setProductosMasVendidos(topProductos);
        
        return reporte;
    }
    
    @Override
    public ReporteVentasDTO generarReporteVentasMensuales(int anio, int mes) {
        ReporteVentasDTO reporte = new ReporteVentasDTO();
        

        if (anio < 2000 || anio > 2100 || mes < 1 || mes > 12) {
            System.err.println(" Año o mes inválido: " + anio + "/" + mes);
            return reporte;
        }
        
        LocalDate inicio = LocalDate.of(anio, mes, 1);
        LocalDate fin = inicio.withDayOfMonth(inicio.lengthOfMonth());
        reporte.setFecha(inicio);
        
        String sql = "SELECT v.* FROM ventas v WHERE DATE(v.fecha_hora) BETWEEN ? AND ? AND v.estado = 'Activa'";
        List<Venta> ventas = new ArrayList<>();
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, inicio.toString());
            ps.setString(2, fin.toString());
            ResultSet rs = ps.executeQuery();
            
            DatabaseCliente daoCliente = new DatabaseCliente();
            
            while (rs.next()) {
                Cliente cliente = daoCliente.buscar(rs.getString("codigo_cliente"));
                Venta venta = new Venta(
                    rs.getString("numero_factura"),
                    cliente,
                    rs.getString("forma_pago"),
                    rs.getString("usuario_registro")
                );
                cargarDetallesVenta(venta);
                ventas.add(venta);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error en reporte mensual: " + e.getMessage());
        }
        
        double totalVentas = 0;
        double totalIVA = 0;
        Map<String, Double> ventasPorFormaPago = new HashMap<>();
        
        for (Venta v : ventas) {
            totalVentas += v.getTotal();
            totalIVA += v.getIva();
            ventasPorFormaPago.merge(v.getFormaPago(), v.getTotal(), Double::sum);
        }
        
        reporte.setTotalVentas(totalVentas);
        reporte.setTotalIVA(totalIVA);
        reporte.setNumeroFacturas(ventas.size());
        reporte.setVentasPorFormaPago(ventasPorFormaPago);
        
        return reporte;
    }
    
    @Override
    public ReporteVentasDTO generarReporteVentasAnuales(int anio) {
        ReporteVentasDTO reporte = new ReporteVentasDTO();
        
        if (anio < 2000 || anio > 2100) {
            System.err.println(" Año inválido: " + anio);
            return reporte;
        }
        
        LocalDate inicio = LocalDate.of(anio, 1, 1);
        LocalDate fin = LocalDate.of(anio, 12, 31);
        reporte.setFecha(inicio);
        
        String sql = "SELECT v.* FROM ventas v WHERE DATE(v.fecha_hora) BETWEEN ? AND ? AND v.estado = 'Activa'";
        List<Venta> ventas = new ArrayList<>();
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, inicio.toString());
            ps.setString(2, fin.toString());
            ResultSet rs = ps.executeQuery();
            
            DatabaseCliente daoCliente = new DatabaseCliente();
            
            while (rs.next()) {
                Cliente cliente = daoCliente.buscar(rs.getString("codigo_cliente"));
                Venta venta = new Venta(
                    rs.getString("numero_factura"),
                    cliente,
                    rs.getString("forma_pago"),
                    rs.getString("usuario_registro")
                );
                cargarDetallesVenta(venta);
                ventas.add(venta);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error en reporte anual: " + e.getMessage());
        }
        
        double totalVentas = 0;
        double totalIVA = 0;
        Map<String, Double> ventasPorFormaPago = new HashMap<>();
        Map<String, Integer> productosVendidos = new HashMap<>();
        
        for (Venta v : ventas) {
            totalVentas += v.getTotal();
            totalIVA += v.getIva();
            ventasPorFormaPago.merge(v.getFormaPago(), v.getTotal(), Double::sum);
            
            for (DetalleVenta d : v.getDetalles()) {
                productosVendidos.merge(d.getProducto().getNombre(), d.getCantidad(), Integer::sum);
            }
        }
        
        reporte.setTotalVentas(totalVentas);
        reporte.setTotalIVA(totalIVA);
        reporte.setNumeroFacturas(ventas.size());
        reporte.setVentasPorFormaPago(ventasPorFormaPago);
        
        List<Object[]> topProductos = productosVendidos.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(10)
            .map(e -> new Object[]{e.getKey(), e.getValue()})
            .collect(Collectors.toList());
        reporte.setProductosMasVendidos(topProductos);
        
        return reporte;
    }
    
    @Override
    public List<Object[]> obtenerProductosMasVendidos(int top, LocalDate inicio, LocalDate fin) {
        List<Object[]> productos = new ArrayList<>();
        
        String sql = "SELECT p.codigo, p.nombre, SUM(dv.cantidad) as total_vendido " +
                     "FROM detalle_ventas dv " +
                     "JOIN productos p ON dv.codigo_producto = p.codigo " +
                     "JOIN ventas v ON dv.numero_factura = v.numero_factura " +
                     "WHERE v.estado = 'Activa' " +
                     "AND DATE(v.fecha_hora) BETWEEN ? AND ? " +
                     "GROUP BY p.codigo, p.nombre " +
                     "ORDER BY total_vendido DESC " +
                     "LIMIT ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, inicio.toString());
            ps.setString(2, fin.toString());
            ps.setInt(3, top);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Object[] fila = {
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getInt("total_vendido")
                };
                productos.add(fila);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al obtener productos más vendidos: " + e.getMessage());
        }
        
        return productos;
    }
    
    @Override
    public List<Object[]> obtenerClientesTopCompradores(int top, LocalDate inicio, LocalDate fin) {
        List<Object[]> clientes = new ArrayList<>();
        
        String sql = "SELECT c.codigo, c.nombre, c.tipo_cliente, SUM(v.total) as total_comprado " +
                     "FROM ventas v " +
                     "JOIN clientes c ON v.codigo_cliente = c.codigo " +
                     "WHERE v.estado = 'Activa' " +
                     "AND DATE(v.fecha_hora) BETWEEN ? AND ? " +
                     "GROUP BY c.codigo, c.nombre, c.tipo_cliente " +
                     "ORDER BY total_comprado DESC " +
                     "LIMIT ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, inicio.toString());
            ps.setString(2, fin.toString());
            ps.setInt(3, top);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Object[] fila = {
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getString("tipo_cliente"),
                    rs.getDouble("total_comprado")
                };
                clientes.add(fila);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al obtener clientes top: " + e.getMessage());
        }
        
        return clientes;
    }
    
    @Override
    public Map<String, Double> obtenerVentasPorFormaPago(LocalDate inicio, LocalDate fin) {
        Map<String, Double> resultado = new HashMap<>();
        
        String sql = "SELECT forma_pago, SUM(total) as total " +
                     "FROM ventas " +
                     "WHERE estado = 'Activa' " +
                     "AND DATE(fecha_hora) BETWEEN ? AND ? " +
                     "GROUP BY forma_pago";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, inicio.toString());
            ps.setString(2, fin.toString());
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                resultado.put(rs.getString("forma_pago"), rs.getDouble("total"));
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al obtener ventas por forma de pago: " + e.getMessage());
        }
        
        return resultado;
    }
    
    @Override
    public List<Object[]> obtenerInventarioValorizado() {
        List<Object[]> inventario = new ArrayList<>();
        String sql = "SELECT codigo, nombre, stock_actual, precio_compra, (stock_actual * precio_compra) as valor_total FROM productos WHERE activo = 1";
        
        try (Connection conn = conexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Object[] fila = {
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getInt("stock_actual"),
                    rs.getDouble("precio_compra"),
                    rs.getDouble("valor_total")
                };
                inventario.add(fila);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al listar inventario valorizado: " + e.getMessage());
        }
        return inventario;
    }
    
    @Override
    public double calcularUtilidadBruta(LocalDate inicio, LocalDate fin) {
        List<Venta> ventas = listarVentasPorPeriodo(inicio, fin);
        double totalVentas = 0;
        double costoVentas = 0;
        
        for (Venta v : ventas) {
            totalVentas += v.getTotal();
            for (DetalleVenta d : v.getDetalles()) {
                costoVentas += d.getCantidad() * d.getProducto().getPrecioCompra();
            }
        }
        
        return totalVentas - costoVentas;
        
        
    }
    @Override
    public double obtenerValorInventarioTotal() {
        double total = 0;
        String sql = "SELECT SUM(stock_actual * precio_compra) as total FROM productos WHERE activo = 1";
        
        try (Connection conn = conexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                total = rs.getDouble("total");
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al calcular valor inventario: " + e.getMessage());
        }
        return total;
    }
}