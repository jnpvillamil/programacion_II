package co.edu.uptc.tiendaminorista.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.tiendaminorista.enums.CategoriaProducto;
import co.edu.uptc.tiendaminorista.interfaces.IGestionProducto;
import co.edu.uptc.tiendaminorista.modelo.Producto;

// Cambie de SQLite a MySQL para que quede igual que LocalCliente
// Quite el metodo crearTablaProductos porque el script SQL ya crea todo
public class LocalProducto implements IGestionProducto {

    public LocalProducto() {
    }

    // INSERT - guardar nuevo producto en la base de datos
    @Override
    public void guardar(Producto producto) {
        String sql = "INSERT INTO productos (codigo, nombre, categoria, precio_compra, precio_venta, stock_actual, stock_minimo, activo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            producto.setActivo(true);
            stmt.setString(1, producto.getCodigo());
            stmt.setString(2, producto.getNombre());
            stmt.setString(3, producto.getCategoria() != null ? producto.getCategoria().name() : CategoriaProducto.OTROS.name());
            stmt.setDouble(4, producto.getPrecioCompra());
            stmt.setDouble(5, producto.getPrecioVenta());
            stmt.setInt(6, producto.getStockActual());
            stmt.setInt(7, producto.getStockMinimo());
            stmt.setBoolean(8, producto.isActivo());

            int filas = stmt.executeUpdate();
            if (filas == 0) {
                System.err.println("No se pudo insertar el producto: " + producto.getCodigo());
            }

        } catch (SQLException e) {
            System.err.println("Error guardando producto: " + e.getMessage());
        }
    }

    // SELECT - listar todos los productos de la base de datos
    @Override
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT codigo, nombre, categoria, precio_compra, precio_venta, stock_actual, stock_minimo, activo FROM productos";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearProducto(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error listando productos: " + e.getMessage());
        }

        return lista;
    }

    // UPDATE - modificar datos del producto
    @Override
    public void actualizar(Producto producto) {
        String sql = "UPDATE productos SET nombre = ?, categoria = ?, precio_compra = ?, precio_venta = ?, stock_actual = ?, stock_minimo = ? WHERE codigo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria() != null ? producto.getCategoria().name() : CategoriaProducto.OTROS.name());
            stmt.setDouble(3, producto.getPrecioCompra());
            stmt.setDouble(4, producto.getPrecioVenta());
            stmt.setInt(5, producto.getStockActual());
            stmt.setInt(6, producto.getStockMinimo());
            stmt.setString(7, producto.getCodigo());

            int filas = stmt.executeUpdate();
            if (filas == 0) {
                System.err.println("Producto no encontrado para actualizar: " + producto.getCodigo());
            }

        } catch (SQLException e) {
            System.err.println("Error actualizando producto: " + e.getMessage());
        }
    }

    // UPDATE - cambiar estado activo a false (inactivar en vez de borrar)
    @Override
    public void desactivar(String codigo) {
        cambiarEstado(codigo, false);
    }

    // UPDATE - volver a activar un producto inactivado
    @Override
    public void activar(String codigo) {
        cambiarEstado(codigo, true);
    }

    private void cambiarEstado(String codigo, boolean activo) {
        String sql = "UPDATE productos SET activo = ? WHERE codigo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, activo);
            stmt.setString(2, codigo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error cambiando estado del producto: " + e.getMessage());
        }
    }

    // UPDATE - sumar o restar cantidad al stock (cantidad positiva = entrada, negativa = salida)
    @Override
    public void registrarMovimientoInventario(String codigo, int cantidad) {
        String sql = "UPDATE productos SET stock_actual = stock_actual + ? WHERE codigo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cantidad);
            stmt.setString(2, codigo);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error actualizando inventario: " + e.getMessage());
        }
    }

    // SELECT con filtro - buscar producto por codigo exacto
    public Producto buscarPorCodigo(String codigo) {
        String sql = "SELECT codigo, nombre, categoria, precio_compra, precio_venta, stock_actual, stock_minimo, activo FROM productos WHERE codigo = ?";
        Producto p = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    p = mapearProducto(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error buscando producto: " + e.getMessage());
        }

        return p;
    }

    // SELECT - productos con stock por debajo del minimo (consulta requerida por el caso)
    public List<Producto> listarBajoStockMinimo() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT codigo, nombre, categoria, precio_compra, precio_venta, stock_actual, stock_minimo, activo "
                + "FROM productos WHERE stock_actual < stock_minimo AND activo = 1";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearProducto(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error consultando productos bajo stock: " + e.getMessage());
        }

        return lista;
    }

    // MÉTODOS AUXILIARES
    private Producto mapearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setCodigo(rs.getString("codigo"));
        p.setNombre(rs.getString("nombre"));
        try {
            p.setCategoria(CategoriaProducto.valueOf(rs.getString("categoria")));
        } catch (Exception ex) {
            p.setCategoria(CategoriaProducto.OTROS);
        }
        p.setPrecioCompra(rs.getDouble("precio_compra"));
        p.setPrecioVenta(rs.getDouble("precio_venta"));
        p.setStockActual(rs.getInt("stock_actual"));
        p.setStockMinimo(rs.getInt("stock_minimo"));
        p.setActivo(rs.getBoolean("activo"));
        return p;
    }
}
