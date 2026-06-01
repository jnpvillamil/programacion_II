package co.edu.uptc.tiendaminorista.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.tiendaminorista.modelo.CompraPro;

public class LocalCompraPro {

    public LocalCompraPro() {
    }

    public List<CompraPro> leer() {
        List<CompraPro> lista = new ArrayList<>();
        String sql = "SELECT fecha, proveedor, producto, cantidad, precio_unitario, total FROM compras_proveedor";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                CompraPro c = new CompraPro();
                String fechaStr = rs.getString("fecha");
                if (fechaStr != null && !fechaStr.isEmpty()) {
                    try {
                        c.setFecha(LocalDate.parse(fechaStr));
                    } catch (Exception e) {
                        c.setFecha(LocalDate.now());
                    }
                }
                c.setProveedor(rs.getString("proveedor"));
                c.setProducto(rs.getString("producto"));
                c.setCantidad(rs.getInt("cantidad"));
                c.setPrecioUnitario(rs.getDouble("precio_unitario"));
                c.setTotal(rs.getDouble("total"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void guardar(CompraPro compraPro) {
        String sql = "INSERT INTO compras_proveedor (fecha, proveedor, producto, cantidad, precio_unitario, total) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            LocalDate fecha = compraPro.getFecha() != null ? compraPro.getFecha() : LocalDate.now();
            pstmt.setString(1, fecha.toString());
            pstmt.setString(2, compraPro.getProveedor());
            pstmt.setString(3, compraPro.getProducto());
            pstmt.setInt(4, compraPro.getCantidad());
            pstmt.setDouble(5, compraPro.getPrecioUnitario());
            pstmt.setDouble(6, compraPro.getTotal());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
