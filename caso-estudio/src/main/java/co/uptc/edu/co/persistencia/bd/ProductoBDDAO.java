package co.uptc.edu.co.persistencia.bd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.conexion.ConexionBD;
import co.uptc.edu.co.interfaces.IGestionProducto;
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.enums.CategoriaProductoEnum;
import co.uptc.edu.co.modelo.enums.EstadoEnum;
import co.uptc.edu.co.util.LogUtil;

public class ProductoBDDAO implements IGestionProducto {

	private static final String TABLA_PRODUCTOS = "productos";

	private static final String SQL_INSERTAR = "INSERT INTO " + TABLA_PRODUCTOS
			+ " (codigoProducto, nombreProducto, categoria, precioCompra, precioVenta, stockActual, stockMinimo, stockMaximo, aplicaIva, estado) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_BUSCAR = "SELECT codigoProducto, nombreProducto, categoria, precioCompra, precioVenta, "
			+ "stockActual, stockMinimo, stockMaximo, aplicaIva, estado FROM " + TABLA_PRODUCTOS
			+ " WHERE codigoProducto = ?";

	private static final String SQL_LISTAR = "SELECT codigoProducto, nombreProducto, categoria, precioCompra, precioVenta, "
			+ "stockActual, stockMinimo, stockMaximo, aplicaIva, estado FROM " + TABLA_PRODUCTOS;

	private static final String SQL_ACTUALIZAR = "UPDATE " + TABLA_PRODUCTOS + " SET "
			+ "nombreProducto = ?, categoria = ?, precioCompra = ?, precioVenta = ?, "
			+ "stockActual = ?, stockMinimo = ?, stockMaximo = ?, aplicaIva = ?, estado = ? "
			+ "WHERE codigoProducto = ?";

	private static final String SQL_DESCONTAR_STOCK_VENTA = "UPDATE " + TABLA_PRODUCTOS
			+ " SET stockActual = stockActual - ? "
			+ "WHERE codigoProducto = ? AND estado = 'ACTIVO' AND stockActual >= ?";

	@Override
	public void guardar(Producto producto) throws Exception {
		LogUtil.info("Entrando a guardar producto. codigo=" + producto.getCodigoProducto());

		try (Connection connection = ConexionBD.getConexion();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERTAR)) {

			prepararInsert(preparedStatement, producto);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			LogUtil.error("Error en guardar producto: " + e.getMessage(), e);
			throw new Exception("Error al guardar el producto: " + e.getMessage(), e);
		}
	}

	@Override
	public void actualizar(Producto producto) throws Exception {
		LogUtil.info("Entrando a actualizar producto. codigo=" + producto.getCodigoProducto());

		try (Connection connection = ConexionBD.getConexion();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_ACTUALIZAR)) {

			ejecutarActualizacion(preparedStatement, producto);

		} catch (SQLException e) {
			LogUtil.error("Error en actualizar producto: " + e.getMessage(), e);
			throw new Exception("Error al actualizar el producto: " + e.getMessage(), e);
		}
	}

	@Override
	public Producto buscar(String codigo) throws Exception {
		LogUtil.info("Entrando a buscar producto. codigo=" + codigo);

		try (Connection connection = ConexionBD.getConexion();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_BUSCAR)) {

			return ejecutarBusqueda(preparedStatement, codigo);

		} catch (SQLException e) {
			LogUtil.error("Error en buscar producto: " + e.getMessage(), e);
			throw new Exception("Error al buscar el producto: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Producto> listar() throws Exception {
		LogUtil.info("Entrando a listar productos");

		List<Producto> lista = new ArrayList<>();

		try (Connection connection = ConexionBD.getConexion();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_LISTAR);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				lista.add(construirProducto(resultSet));
			}

		} catch (SQLException e) {
			LogUtil.error("Error en listar productos: " + e.getMessage(), e);
			throw new Exception("Error al listar los productos: " + e.getMessage(), e);
		}

		return lista;
	}

	@Override
	public void cambiarEstado(String codigo) throws Exception {
		Producto producto = buscar(codigo);

		if (producto == null) {
			throw new Exception("No se encontró el producto.");
		}

		if (producto.estaActivo()) {
			producto.setEstado(EstadoEnum.INACTIVO);
		} else {
			producto.setEstado(EstadoEnum.ACTIVO);
		}

		actualizar(producto);
	}
	@Override
	public void actualizar(Connection conexion, Producto producto) throws Exception {
		try (PreparedStatement preparedStatement = conexion.prepareStatement(SQL_ACTUALIZAR)) {
			ejecutarActualizacion(preparedStatement, producto);
		} catch (SQLException e) {
			throw new Exception("Error al actualizar el producto: " + e.getMessage(), e);
		}
	}

	public boolean descontarStockPorVenta(Connection conexion, String codigoProducto, int cantidad) throws Exception {
		LogUtil.info("Entrando a descontarStockPorVenta. codigo=" + codigoProducto + " cantidad=" + cantidad);

		try (PreparedStatement preparedStatement = conexion.prepareStatement(SQL_DESCONTAR_STOCK_VENTA)) {
			preparedStatement.setInt(1, cantidad);
			preparedStatement.setString(2, codigoProducto);
			preparedStatement.setInt(3, cantidad);
			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			LogUtil.error("Error en descontarStockPorVenta: " + e.getMessage(), e);
			throw new Exception("Error al descontar stock por venta: " + e.getMessage(), e);
		}
	}
	@Override
	public Producto buscar(Connection conexion, String codigo) throws Exception {
		try (PreparedStatement preparedStatement = conexion.prepareStatement(SQL_BUSCAR)) {
			return ejecutarBusqueda(preparedStatement, codigo);
		} catch (SQLException e) {
			throw new Exception("Error al buscar el producto: " + e.getMessage(), e);
		}
	}

	private void ejecutarActualizacion(PreparedStatement preparedStatement, Producto producto) throws Exception {
		prepararActualizar(preparedStatement, producto);

		int filasActualizadas = preparedStatement.executeUpdate();

		if (filasActualizadas == 0) {
			throw new Exception("No se encontró el producto a actualizar.");
		}
	}

	private Producto ejecutarBusqueda(PreparedStatement preparedStatement, String codigo) throws SQLException {
		preparedStatement.setString(1, codigo);

		try (ResultSet resultSet = preparedStatement.executeQuery()) {
			if (resultSet.next()) {
				return construirProducto(resultSet);
			}
		}

		return null;
	}

	private Producto construirProducto(ResultSet resultSet) throws SQLException {
		Producto producto = new Producto();

		producto.setCodigoProducto(resultSet.getString("codigoProducto"));
		producto.setNombreProducto(resultSet.getString("nombreProducto"));
		producto.setCategoria(CategoriaProductoEnum.valueOf(resultSet.getString("categoria")));
		producto.setPrecioCompra(resultSet.getDouble("precioCompra"));
		producto.setPrecioVenta(resultSet.getDouble("precioVenta"));
		producto.setStockActual(resultSet.getInt("stockActual"));
		producto.setStockMinimo(resultSet.getInt("stockMinimo"));
		producto.setStockMaximo(resultSet.getInt("stockMaximo"));
		producto.setAplicaIva(resultSet.getBoolean("aplicaIva"));
		producto.setEstado(EstadoEnum.valueOf(resultSet.getString("estado")));

		return producto;
	}

	private void prepararInsert(PreparedStatement preparedStatement, Producto producto) throws SQLException {
		preparedStatement.setString(1, producto.getCodigoProducto());
		preparedStatement.setString(2, producto.getNombreProducto());
		preparedStatement.setString(3, producto.getCategoria().name());
		preparedStatement.setBigDecimal(4, BigDecimal.valueOf(producto.getPrecioCompra()));
		preparedStatement.setBigDecimal(5, BigDecimal.valueOf(producto.getPrecioVenta()));
		preparedStatement.setInt(6, producto.getStockActual());
		preparedStatement.setInt(7, producto.getStockMinimo());
		preparedStatement.setInt(8, producto.getStockMaximo());
		preparedStatement.setBoolean(9, producto.isAplicaIva());
		preparedStatement.setString(10, producto.getEstado().name());
	}

	private void prepararActualizar(PreparedStatement preparedStatement, Producto producto) throws SQLException {
		preparedStatement.setString(1, producto.getNombreProducto());
		preparedStatement.setString(2, producto.getCategoria().name());
		preparedStatement.setBigDecimal(3, BigDecimal.valueOf(producto.getPrecioCompra()));
		preparedStatement.setBigDecimal(4, BigDecimal.valueOf(producto.getPrecioVenta()));
		preparedStatement.setInt(5, producto.getStockActual());
		preparedStatement.setInt(6, producto.getStockMinimo());
		preparedStatement.setInt(7, producto.getStockMaximo());
		preparedStatement.setBoolean(8, producto.isAplicaIva());
		preparedStatement.setString(9, producto.getEstado().name());
		preparedStatement.setString(10, producto.getCodigoProducto());
	}

	@Override
	public void guardar(Connection conexion, Producto producto) throws Exception {
		try (PreparedStatement preparedStatement = conexion.prepareStatement(SQL_INSERTAR)) {
			prepararInsert(preparedStatement, producto);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Error al guardar el producto: " + e.getMessage(), e);
		}
	}
}