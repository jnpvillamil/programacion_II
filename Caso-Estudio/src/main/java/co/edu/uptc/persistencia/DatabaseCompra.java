package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.GestionCompra;
import co.edu.uptc.modelo.*;
import co.edu.uptc.Util.ConexionBD;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DatabaseCompra implements GestionCompra {
    
    private ConexionBD conexionBD = new ConexionBD();
    
    @Override
    public void crearCompra(Compra compra) {
        Connection conn = null;
        try {
            conn = conexionBD.getConexion();
            conn.setAutoCommit(false);  
            
            
            String sqlCompra = "INSERT INTO compras (factura_proveedor, fecha_hora, codigo_proveedor, subtotal, iva, total, estado, usuario_registro) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement ps = conn.prepareStatement(sqlCompra)) {
                ps.setString(1, compra.getNumeroFacturaProveedor());
                ps.setTimestamp(2, Timestamp.valueOf(compra.getFechaHora()));
                ps.setString(3, compra.getProveedor().getCodigo());
                ps.setDouble(4, compra.getSubtotal());
                ps.setDouble(5, compra.getIva());
                ps.setDouble(6, compra.getTotal());
                ps.setString(7, compra.getEstado());
                ps.setString(8, "admin");
                ps.executeUpdate();
            }
            
      
            String sqlDetalle = "INSERT INTO detalle_compras (factura_proveedor, codigo_producto, cantidad, precio_compra, subtotal) VALUES (?, ?, ?, ?, ?)";
            String sqlUpdateStock = "UPDATE productos SET stock_actual = stock_actual + ?, precio_compra = ? WHERE codigo = ?";
            
            for (DetalleCompra detalle : compra.getDetalles()) {
                
                try (PreparedStatement ps = conn.prepareStatement(sqlDetalle)) {
                    ps.setString(1, compra.getNumeroFacturaProveedor());
                    ps.setString(2, detalle.getProducto().getCodigo());
                    ps.setInt(3, detalle.getCantidad());
                    ps.setDouble(4, detalle.getPrecioCompra());
                    ps.setDouble(5, detalle.getSubtotal());
                    ps.executeUpdate();
                }
                
               
                try (PreparedStatement ps = conn.prepareStatement(sqlUpdateStock)) {
                    ps.setInt(1, detalle.getCantidad());
                    ps.setDouble(2, detalle.getPrecioCompra());
                    ps.setString(3, detalle.getProducto().getCodigo());
                    ps.executeUpdate();
                }
            }
            
            conn.commit();
            System.out.println(" Compra registrada en BD: " + compra.getNumeroFacturaProveedor());
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
                System.err.println(" Error al registrar compra, transacción revertida: " + e.getMessage());
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
    public Compra buscarCompra(String facturaProveedor) {
        String sql = "SELECT * FROM compras WHERE factura_proveedor = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, facturaProveedor);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                
                DatabaseProveedor daoProveedor = new DatabaseProveedor();
                Proveedor proveedor = daoProveedor.buscar(rs.getString("codigo_proveedor"));
                
                Compra compra = new Compra(
                    rs.getString("factura_proveedor"),
                    proveedor,
                    rs.getString("usuario_registro")
                );
                
              
                cargarDetallesCompra(compra);
                
                return compra;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar compra: " + e.getMessage());
        }
        return null;
    }
    
    private void cargarDetallesCompra(Compra compra) {
        String sql = "SELECT * FROM detalle_compras WHERE factura_proveedor = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, compra.getNumeroFacturaProveedor());
            ResultSet rs = ps.executeQuery();
            
            DatabaseProducto daoProducto = new DatabaseProducto();
            
            while (rs.next()) {
                Producto producto = daoProducto.buscar(rs.getString("codigo_producto"));
                int cantidad = rs.getInt("cantidad");
                double precioCompra = rs.getDouble("precio_compra");
                
                compra.agregarDetalle(producto, cantidad, precioCompra);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al cargar detalles compra: " + e.getMessage());
        }
    }
    
    @Override
    public List<Compra> listarCompras() {
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT * FROM compras ORDER BY fecha_hora DESC";
        
        try (Connection conn = conexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            DatabaseProveedor daoProveedor = new DatabaseProveedor();
            
            while (rs.next()) {
                Proveedor proveedor = daoProveedor.buscar(rs.getString("codigo_proveedor"));
                
                Compra compra = new Compra(
                    rs.getString("factura_proveedor"),
                    proveedor,
                    rs.getString("usuario_registro")
                );
                
                cargarDetallesCompra(compra);
                compras.add(compra);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al listar compras: " + e.getMessage());
        }
        return compras;
    }
    
    @Override
    public void anularCompra(String facturaProveedor) {
        Connection conn = null;
        try {
            conn = conexionBD.getConexion();
            conn.setAutoCommit(false);
            
           
            String sqlSelect = "SELECT * FROM detalle_compras WHERE factura_proveedor = ?";
            List<DetalleCompra> detalles = new ArrayList<>();
            
            try (PreparedStatement ps = conn.prepareStatement(sqlSelect)) {
                ps.setString(1, facturaProveedor);
                ResultSet rs = ps.executeQuery();
                
                DatabaseProducto daoProducto = new DatabaseProducto();
                while (rs.next()) {
                    Producto producto = daoProducto.buscar(rs.getString("codigo_producto"));
                    int cantidad = rs.getInt("cantidad");
                    double precioCompra = rs.getDouble("precio_compra");
                    detalles.add(new DetalleCompra(producto, cantidad, precioCompra));
                }
            }
            
       
            String sqlUpdateStock = "UPDATE productos SET stock_actual = stock_actual - ? WHERE codigo = ?";
            for (DetalleCompra detalle : detalles) {
                try (PreparedStatement ps = conn.prepareStatement(sqlUpdateStock)) {
                    ps.setInt(1, detalle.getCantidad());
                    ps.setString(2, detalle.getProducto().getCodigo());
                    ps.executeUpdate();
                }
            }
            
       
            String sqlUpdate = "UPDATE compras SET estado = 'Anulada' WHERE factura_proveedor = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
                ps.setString(1, facturaProveedor);
                ps.executeUpdate();
            }
            
            conn.commit();
            System.out.println(" Compra anulada en BD: " + facturaProveedor);
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
                System.err.println(" Error al anular compra, transacción revertida: " + e.getMessage());
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
    public List<Compra> listarComprasPorFecha(LocalDate fecha) {
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT * FROM compras WHERE DATE(fecha_hora) = ? AND estado = 'Activa'";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, fecha.toString());
            ResultSet rs = ps.executeQuery();
            
            DatabaseProveedor daoProveedor = new DatabaseProveedor();
            
            while (rs.next()) {
                Proveedor proveedor = daoProveedor.buscar(rs.getString("codigo_proveedor"));
                Compra compra = new Compra(
                    rs.getString("factura_proveedor"),
                    proveedor,
                    rs.getString("usuario_registro")
                );
                cargarDetallesCompra(compra);
                compras.add(compra);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al listar compras por fecha: " + e.getMessage());
        }
        return compras;
    }
    
    @Override
    public List<Compra> listarComprasPorProveedor(String codigoProveedor) {
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT * FROM compras WHERE codigo_proveedor = ? AND estado = 'Activa'";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, codigoProveedor);
            ResultSet rs = ps.executeQuery();
            
            DatabaseProveedor daoProveedor = new DatabaseProveedor();
            Proveedor proveedor = daoProveedor.buscar(codigoProveedor);
            
            while (rs.next()) {
                Compra compra = new Compra(
                    rs.getString("factura_proveedor"),
                    proveedor,
                    rs.getString("usuario_registro")
                );
                cargarDetallesCompra(compra);
                compras.add(compra);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al listar compras por proveedor: " + e.getMessage());
        }
        return compras;
    }
    
    @Override
    public List<Compra> listarComprasPorPeriodo(LocalDate inicio, LocalDate fin) {
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT * FROM compras WHERE DATE(fecha_hora) BETWEEN ? AND ? AND estado = 'Activa'";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, inicio.toString());
            ps.setString(2, fin.toString());
            ResultSet rs = ps.executeQuery();
            
            DatabaseProveedor daoProveedor = new DatabaseProveedor();
            
            while (rs.next()) {
                Proveedor proveedor = daoProveedor.buscar(rs.getString("codigo_proveedor"));
                Compra compra = new Compra(
                    rs.getString("factura_proveedor"),
                    proveedor,
                    rs.getString("usuario_registro")
                );
                cargarDetallesCompra(compra);
                compras.add(compra);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al listar compras por período: " + e.getMessage());
        }
        return compras;
    }
    
    @Override
    public double calcularTotalComprasPeriodo(LocalDate inicio, LocalDate fin) {
        double total = 0;
        String sql = "SELECT SUM(total) as total FROM compras WHERE DATE(fecha_hora) BETWEEN ? AND ? AND estado = 'Activa'";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, inicio.toString());
            ps.setString(2, fin.toString());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                total = rs.getDouble("total");
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al calcular total compras: " + e.getMessage());
        }
        return total;
    }
    
    @Override
    public List<Object[]> obtenerProductosMasComprados() {
        List<Object[]> productos = new ArrayList<>();
        String sql = "SELECT p.codigo, p.nombre, SUM(dc.cantidad) as total_comprado " +
                     "FROM detalle_compras dc " +
                     "JOIN productos p ON dc.codigo_producto = p.codigo " +
                     "JOIN compras c ON dc.factura_proveedor = c.factura_proveedor " +
                     "WHERE c.estado = 'Activa' " +
                     "GROUP BY p.codigo, p.nombre " +
                     "ORDER BY total_comprado DESC " +
                     "LIMIT 10";
        
        try (Connection conn = conexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Object[] fila = {
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getInt("total_comprado")
                };
                productos.add(fila);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al obtener productos más comprados: " + e.getMessage());
        }
        return productos;
    }
}