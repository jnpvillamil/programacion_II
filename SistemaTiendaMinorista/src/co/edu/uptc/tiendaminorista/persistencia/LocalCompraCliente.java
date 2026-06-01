package co.edu.uptc.tiendaminorista.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.edu.uptc.tiendaminorista.interfaces.IGestionCompraCli;
import co.edu.uptc.tiendaminorista.modelo.Cliente;
import co.edu.uptc.tiendaminorista.modelo.CompasCliente;
import co.edu.uptc.tiendaminorista.modelo.Producto;

public class LocalCompraCliente implements IGestionCompraCli {

    @Override
    public void guardarCompra(CompasCliente compra) {
        if (compra == null) return;

        String sql = "INSERT INTO ventas_cliente (codigo_cliente, nombre_cliente, codigo_producto, nombre_producto, cantidad, total, fecha) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            Cliente c = compra.getCliente();
            Producto p = compra.getProducto();

            pstmt.setString(1, c != null ? c.getCodigo() : "");
            pstmt.setString(2, c != null ? c.getNombre() : "");
            pstmt.setString(3, p != null ? p.getCodigo() : "");
            pstmt.setString(4, p != null ? p.getNombre() : "");
            pstmt.setInt(5, compra.getCantidad());
            pstmt.setDouble(6, compra.getTotalCompra());

            Date fecha = compra.getFecha();
            if (fecha != null) {
                pstmt.setString(7, fecha.toString());
            } else {
                pstmt.setString(7, new Date().toString());
            }

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CompasCliente> obtenerTodasLasCompras() {
        List<CompasCliente> lista = new ArrayList<>();
        String sql = "SELECT codigo_cliente, nombre_cliente, codigo_producto, nombre_producto, cantidad, total, fecha FROM ventas_cliente";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CompasCliente compra = new CompasCliente();

                Cliente cliente = new Cliente();
                cliente.setCodigo(rs.getString("codigo_cliente"));
                cliente.setNombre(rs.getString("nombre_cliente"));
                compra.setCliente(cliente);

                Producto producto = new Producto();
                producto.setCodigo(rs.getString("codigo_producto"));
                producto.setNombre(rs.getString("nombre_producto"));
                compra.setProducto(producto);

                compra.setCantidad(rs.getInt("cantidad"));
                compra.setTotalCompra(rs.getDouble("total"));

                lista.add(compra);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
