package co.edu.uptc.tiendaminorista.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
                String fechaTexto = rs.getString("fecha");
                if (fechaTexto != null && !fechaTexto.isEmpty()) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                        compra.setFecha(sdf.parse(fechaTexto));
                    } catch (ParseException ex) {
                        try {
                            compra.setFecha(new Date());
                        } catch (Exception ignored) {
                        }
                    }
                }

                lista.add(compra);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public List<CompasCliente> listarComprasPorCliente(String clienteIdentificador) {
        List<CompasCliente> lista = new ArrayList<>();
        if (clienteIdentificador == null || clienteIdentificador.trim().isEmpty()) {
            return obtenerTodasLasCompras();
        }

        String sql = "SELECT vc.codigo_cliente, vc.nombre_cliente, vc.codigo_producto, vc.nombre_producto, vc.cantidad, vc.total, vc.fecha "
                   + "FROM ventas_cliente vc "
                   + "LEFT JOIN clientes c ON vc.codigo_cliente = c.codigo "
                   + "WHERE (c.numeroIdentificacion LIKE ?) OR (vc.nombre_cliente LIKE ?) OR (vc.codigo_cliente = ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String buscado = clienteIdentificador.trim();
            String like = "%" + buscado + "%";
            pstmt.setString(1, like);
            pstmt.setString(2, like);
            pstmt.setString(3, buscado);

            try (ResultSet rs = pstmt.executeQuery()) {
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
                    String fechaTexto = rs.getString("fecha");
                    if (fechaTexto != null && !fechaTexto.isEmpty()) {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                            compra.setFecha(sdf.parse(fechaTexto));
                        } catch (ParseException ex) {
                            try {
                                compra.setFecha(new Date());
                            } catch (Exception ignored) {
                            }
                        }
                    }

                    lista.add(compra);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (lista.isEmpty()) {
            List<CompasCliente> todas = obtenerTodasLasCompras();
            String buscadoLower = clienteIdentificador.trim().toLowerCase();
            for (CompasCliente compra : todas) {
                if (compra.getCliente() == null) continue;
                String codigo = compra.getCliente().getCodigo() != null ? compra.getCliente().getCodigo().toLowerCase() : "";
                String nombre = compra.getCliente().getNombre() != null ? compra.getCliente().getNombre().toLowerCase() : "";
                if (codigo.contains(buscadoLower) || nombre.contains(buscadoLower)) {
                    lista.add(compra);
                }
            }
        }

        return lista;
    }

    @Override
    public List<CompasCliente> listarComprasPorCliente(String codigoCliente, String nombreCliente) {
        List<CompasCliente> todas = obtenerTodasLasCompras();
        if ((codigoCliente == null || codigoCliente.trim().isEmpty()) && (nombreCliente == null || nombreCliente.trim().isEmpty())) {
            return todas;
        }

        String buscadoCodigo = codigoCliente != null ? codigoCliente.trim().toLowerCase() : "";
        String buscadoNombre = nombreCliente != null ? nombreCliente.trim().toLowerCase() : "";
        List<CompasCliente> lista = new ArrayList<>();
        for (CompasCliente compra : todas) {
            if (compra.getCliente() == null) continue;
            String codigo = compra.getCliente().getCodigo() != null ? compra.getCliente().getCodigo().toLowerCase() : "";
            String nombre = compra.getCliente().getNombre() != null ? compra.getCliente().getNombre().toLowerCase() : "";
            if ((!buscadoCodigo.isEmpty() && codigo.equals(buscadoCodigo)) || (!buscadoNombre.isEmpty() && nombre.equals(buscadoNombre))) {
                lista.add(compra);
            }
        }
        return lista;
    }
}
