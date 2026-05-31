package co.uptc.edu.tienda.persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.tienda.conexion.Conexion;
import co.uptc.edu.tienda.interfaces.IGestionCompra;
import co.uptc.edu.tienda.modelo.Compra;
import co.uptc.edu.tienda.modelo.DetalleCompra;
import co.uptc.edu.tienda.modelo.Producto;
import co.uptc.edu.tienda.modelo.Proveedor;

public class SqlCompra implements IGestionCompra {

    private static final String TABLA_COMPRAS = "compras";
    private static final String TABLA_DETALLES = "detalles_compra";

    // =====================================
    // GUARDAR COMPRA + DETALLES
    // =====================================
    @Override
    public void guardar(Compra compra) {
        Conexion conex = new Conexion();
        try {
            // 1. Insertar cabecera de compra
            String sqlCompra = "INSERT INTO " + TABLA_COMPRAS
                    + " (numero_factura, codigo_proveedor, fecha_compra, total) "
                    + "VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conex.getConnection().prepareStatement(sqlCompra,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, compra.getNumeroFactura());
            ps.setInt(2, compra.getProveedor().getCodigoProveedor());
            ps.setString(3, compra.getFechaCompra());
            ps.setDouble(4, compra.getTotal());
            ps.executeUpdate();

            // Obtener el id_compra generado por AUTO_INCREMENT
            ResultSet keys = ps.getGeneratedKeys();
            int idCompra = 0;
            if (keys.next()) {
                idCompra = keys.getInt(1);
                compra.setIdCompra(idCompra);
            }
            ps.close();

            // 2. Insertar cada detalle
            if (compra.getDetalles() != null) {
                String sqlDetalle = "INSERT INTO " + TABLA_DETALLES
                        + " (id_compra, codigo_producto, cantidad, precio_compra, subtotal) "
                        + "VALUES (?, ?, ?, ?, ?)";
                PreparedStatement psD = conex.getConnection().prepareStatement(sqlDetalle);
                for (DetalleCompra d : compra.getDetalles()) {
                    psD.setInt(1, idCompra);
                    psD.setInt(2, d.getProducto().getCodigoProducto());
                    psD.setInt(3, d.getCantidad());
                    psD.setDouble(4, d.getPrecioCompra());
                    psD.setDouble(5, d.getSubtotal());
                    psD.executeUpdate();
                }
                psD.close();
            }
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al guardar compra: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // =====================================
    // CARGAR COMPRAS CON SUS DETALLES
    // =====================================
    @Override
    public List<Compra> cargar() {
        Conexion conex = new Conexion();
        List<Compra> lista = new ArrayList<>();
        try {
            String sql = "SELECT c.*, p.razon_social, p.nit "
                    + "FROM " + TABLA_COMPRAS + " c "
                    + "JOIN proveedores p ON c.codigo_proveedor = p.codigo_proveedor";
            Statement st = conex.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Compra compra = mapearCompra(rs);
                compra.setDetalles(cargarDetalles(compra.getIdCompra()));
                lista.add(compra);
            }
            st.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al cargar compras: " + e.getMessage());
        }
        return lista;
    }

    // =====================================
    // CARGAR DETALLES DE UNA COMPRA
    // =====================================
    private List<DetalleCompra> cargarDetalles(int idCompra) {
        Conexion conex = new Conexion();
        List<DetalleCompra> detalles = new ArrayList<>();
        try {
            String sql = "SELECT dc.*, p.nombre_producto "
                    + "FROM " + TABLA_DETALLES + " dc "
                    + "JOIN productos p ON dc.codigo_producto = p.codigo_producto "
                    + "WHERE dc.id_compra=?";
            PreparedStatement ps = conex.getConnection().prepareStatement(sql);
            ps.setInt(1, idCompra);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Producto producto = new Producto(rs.getInt("codigo_producto"));
                producto.setNombreProducto(rs.getString("nombre_producto"));
                producto.setPrecioCompra(rs.getDouble("precio_compra"));

                DetalleCompra d = new DetalleCompra(producto, rs.getInt("cantidad"));
                detalles.add(d);
            }
            ps.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al cargar detalles compra: " + e.getMessage());
        }
        return detalles;
    }

    // =====================================
    // MAPEAR COMPRA
    // =====================================
    private Compra mapearCompra(ResultSet rs) throws SQLException {
        Compra c = new Compra();
        c.setIdCompra(rs.getInt("id_compra"));
        c.setNumeroFactura(rs.getString("numero_factura"));
        c.setFechaCompra(rs.getString("fecha_compra"));
        c.setTotal(rs.getDouble("total"));

        Proveedor proveedor = new Proveedor(rs.getInt("codigo_proveedor"));
        proveedor.setRazonSocial(rs.getString("razon_social"));
        proveedor.setNit(rs.getString("nit"));
        c.setProveedor(proveedor);

        return c;
    }
}