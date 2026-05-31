package co.uptc.edu.tienda.persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.tienda.conexion.Conexion;
import co.uptc.edu.tienda.enums.EstadoVentaEnum;
import co.uptc.edu.tienda.enums.FormaPagoEnum;
import co.uptc.edu.tienda.interfaces.IGestionVenta;
import co.uptc.edu.tienda.modelo.Cliente;
import co.uptc.edu.tienda.modelo.DetalleVenta;
import co.uptc.edu.tienda.modelo.Producto;
import co.uptc.edu.tienda.modelo.Venta;

public class SqlVenta implements IGestionVenta {

    private static final String TABLA_VENTAS = "ventas";
    private static final String TABLA_DETALLES = "detalles_venta"; // ← con s

    @Override
    public void guardar(Venta venta) {
        Conexion conex = new Conexion();
        try {
            String sqlVenta = "INSERT INTO " + TABLA_VENTAS
                    + " (numero_factura, fecha_hora, id_cliente, "
                    + "forma_pago, impuestos, total, estado, motivo_anulacion) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conex.getConnection().prepareStatement(sqlVenta);
            ps.setString(1, venta.getNumeroFactura());
            ps.setString(2, venta.getFechaHora());
            ps.setInt(3, venta.getCliente().getIdCliente());
            ps.setString(4, venta.getFormaPago().name());
            ps.setDouble(5, venta.getImpuestos());
            ps.setDouble(6, venta.getTotal());
            ps.setString(7, venta.getEstado().name());
            ps.setString(8, venta.getMotivoAnulacion());
            ps.executeUpdate();
            ps.close();

            if (venta.getDetalles() != null) {
                String sqlDetalle = "INSERT INTO " + TABLA_DETALLES
                        + " (numero_factura, codigo_producto, "
                        + "cantidad, precio_unitario, impuestos, subtotal) "
                        + "VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement psD = conex.getConnection().prepareStatement(sqlDetalle);
                for (DetalleVenta d : venta.getDetalles()) {
                    psD.setString(1, venta.getNumeroFactura());
                    psD.setInt(2, d.getProducto().getCodigoProducto());
                    psD.setInt(3, d.getCantidad());
                    psD.setDouble(4, d.getPrecioUnitario());
                    psD.setDouble(5, d.getImpuestos());
                    psD.setDouble(6, d.getSubtotal());
                    psD.executeUpdate();
                }
                psD.close();
            }
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al guardar venta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Venta venta) {
        Conexion conex = new Conexion();
        try {
            String sql = "UPDATE " + TABLA_VENTAS
                    + " SET estado=?, motivo_anulacion=? WHERE numero_factura=?";
            PreparedStatement ps = conex.getConnection().prepareStatement(sql);
            ps.setString(1, venta.getEstado().name());
            ps.setString(2, venta.getMotivoAnulacion());
            ps.setString(3, venta.getNumeroFactura());
            ps.executeUpdate();
            ps.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al actualizar venta: " + e.getMessage());
        }
    }

    @Override
    public List<Venta> leerVentas() {
        Conexion conex = new Conexion();
        List<Venta> lista = new ArrayList<>();
        try {
            String sql = "SELECT v.*, c.nombre_completo, c.numero_documento "
                    + "FROM " + TABLA_VENTAS + " v "
                    + "JOIN clientes c ON v.id_cliente = c.id_cliente";
            Statement st = conex.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Venta v = mapearVenta(rs);
                v.setDetalles(leerDetalles(v.getNumeroFactura()));
                lista.add(v);
            }
            st.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al leer ventas: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Venta buscarPorFactura(String numeroFactura) {
        Conexion conex = new Conexion();
        try {
            String sql = "SELECT v.*, c.nombre_completo, c.numero_documento "
                    + "FROM " + TABLA_VENTAS + " v "
                    + "JOIN clientes c ON v.id_cliente = c.id_cliente "
                    + "WHERE v.numero_factura=?";
            PreparedStatement ps = conex.getConnection().prepareStatement(sql);
            ps.setString(1, numeroFactura);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Venta v = mapearVenta(rs);
                v.setDetalles(leerDetalles(numeroFactura));
                ps.close();
                conex.desconectar();
                return v;
            }
            ps.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al buscar venta: " + e.getMessage());
        }
        return null;
    }

    private List<DetalleVenta> leerDetalles(String numeroFactura) {
        Conexion conex = new Conexion();
        List<DetalleVenta> detalles = new ArrayList<>();
        try {
            String sql = "SELECT dv.*, p.nombre_producto "
                    + "FROM " + TABLA_DETALLES + " dv "
                    + "JOIN productos p ON dv.codigo_producto = p.codigo_producto "
                    + "WHERE dv.numero_factura=?";
            PreparedStatement ps = conex.getConnection().prepareStatement(sql);
            ps.setString(1, numeroFactura);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Producto producto = new Producto(rs.getInt("codigo_producto"));
                producto.setNombreProducto(rs.getString("nombre_producto"));
                producto.setPrecioVenta(rs.getDouble("precio_unitario"));
                producto.setPorcentajeIva(0.0);
                DetalleVenta d = new DetalleVenta(producto, rs.getInt("cantidad"));
                detalles.add(d);
            }
            ps.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al leer detalles: " + e.getMessage());
        }
        return detalles;
    }

    private Venta mapearVenta(ResultSet rs) throws SQLException {
        Venta v = new Venta();
        v.setNumeroFactura(rs.getString("numero_factura"));
        v.setFechaHora(rs.getString("fecha_hora"));
        v.setFormaPago(FormaPagoEnum.valueOf(rs.getString("forma_pago")));
        v.setImpuestos(rs.getDouble("impuestos"));
        v.setTotal(rs.getDouble("total"));
        v.setEstado(EstadoVentaEnum.valueOf(rs.getString("estado")));
        v.setMotivoAnulacion(rs.getString("motivo_anulacion"));
        Cliente cliente = new Cliente(rs.getInt("id_cliente"));
        cliente.setNombreCompleto(rs.getString("nombre_completo"));
        cliente.setNumeroDocumento(rs.getLong("numero_documento"));
        v.setCliente(cliente);
        return v;
    }
}