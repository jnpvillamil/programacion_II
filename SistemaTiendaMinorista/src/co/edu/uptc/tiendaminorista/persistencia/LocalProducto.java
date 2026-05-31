package co.edu.uptc.tiendaminorista.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.tiendaminorista.enums.CategoriaProducto;
import co.edu.uptc.tiendaminorista.interfaces.IGestionProducto;
import co.edu.uptc.tiendaminorista.modelo.Producto;

public class LocalProducto implements IGestionProducto {

    private static final String URL = "jdbc:sqlite:tienda_minorista.db";

    public LocalProducto() {
        crearTablaProductos();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    private void crearTablaProductos() {
        String sql = "CREATE TABLE IF NOT EXISTS productos ("
                + "codigo TEXT PRIMARY KEY, "
                + "nombre TEXT NOT NULL, "
                + "categoria TEXT, "
                + "precio_compra REAL, "
                + "precio_venta REAL, "
                + "stock_actual INTEGER, "
                + "stock_minimo INTEGER, "
                + "activo INTEGER DEFAULT 1"
                + ")";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error creando tabla productos: " + e.getMessage());
        }
    }

    // INSERT - guardar nuevo producto en la base de datos
    @Override
    public void guardar(Producto producto) {
        String sql = "INSERT INTO productos (codigo, nombre, categoria, precio_compra, precio_venta, stock_actual, stock_minimo, activo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            producto.setActivo(true);
            stmt.setString(1, producto.getCodigo());
            stmt.setString(2, producto.getNombre());
            stmt.setString(3, producto.getCategoria() != null ? producto.getCategoria().name() : CategoriaProducto.OTROS.name());
            stmt.setDouble(4, producto.getPrecioCompra());
            stmt.setDouble(5, producto.getPrecioVenta());
            stmt.setInt(6, producto.getStockActual());
            stmt.setInt(7, producto.getStockMinimo());
            stmt.setInt(8, producto.isActivo() ? 1 : 0);

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

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
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
                p.setActivo(rs.getInt("activo") == 1);
                lista.add(p);
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

        try (Connection conn = getConnection();
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

    // UPDATE - cambiar estado activo a 0 (inactivar en vez de borrar)
    @Override
    public void desactivar(String codigo) {
        String sql = "UPDATE productos SET activo = 0 WHERE codigo = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, codigo);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error desactivando producto: " + e.getMessage());
        }
    }

    // UPDATE - volver a activar un producto inactivado
    @Override
    public void activar(String codigo) {
        String sql = "UPDATE productos SET activo = 1 WHERE codigo = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, codigo);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error activando producto: " + e.getMessage());
        }
    }

    // UPDATE - sumar o restar cantidad al stock (cantidad positiva = entrada, negativa = salida)
    @Override
    public void registrarMovimientoInventario(String codigo, int cantidad) {
        String sql = "UPDATE productos SET stock_actual = stock_actual + ? WHERE codigo = ?";

        try (Connection conn = getConnection();
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

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    p = new Producto();
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
                    p.setActivo(rs.getInt("activo") == 1);
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

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
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
                p.setActivo(rs.getInt("activo") == 1);
                lista.add(p);
            }

        } catch (SQLException e) {
            System.err.println("Error consultando productos bajo stock: " + e.getMessage());
        }

        return lista;
    }
}
