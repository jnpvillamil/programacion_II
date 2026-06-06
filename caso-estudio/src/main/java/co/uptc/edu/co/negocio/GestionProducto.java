package co.uptc.edu.co.negocio;

import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.interfaces.IGestionProducto;
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.enums.EstadoEnum;

public class GestionProducto {

	private List<Producto> productos;
	private final IGestionProducto gestionProducto;

	public GestionProducto(IGestionProducto gestionProducto) {
		if (gestionProducto == null) {
			throw new IllegalArgumentException("La gestion de producto no puede ser nula.");
		}

		this.gestionProducto = gestionProducto;

		try {
			productos = gestionProducto.listar();
		} catch (Exception e) {
			productos = new ArrayList<>();
			throw new IllegalStateException("Error al cargar productos.", e);
		}
	}

	private void recargarProductos() throws Exception {
		productos = gestionProducto.listar();
	}

	public Producto buscarProductoPorCodigo(String codigo) {
		try {
			return gestionProducto.buscar(codigo);
		} catch (Exception e) {
			throw new IllegalStateException("Error al buscar el producto por codigo: " + codigo, e);
		}
	}

	public List<Producto> obtenerProductos() {
		return new ArrayList<>(productos);
	}

	public void registrarProducto(Producto producto) throws Exception {
		validarProducto(producto);

		if (buscarProductoPorCodigo(producto.getCodigoProducto()) != null) {
			throw new Exception("Ya existe un producto con ese codigo.");
		}

		producto.setEstado(EstadoEnum.ACTIVO);
		gestionProducto.guardar(producto);
		recargarProductos();
	}

	public void actualizarProducto(Producto productoActualizado) throws Exception {
		validarProducto(productoActualizado);
		gestionProducto.actualizar(productoActualizado);
		recargarProductos();
	}

	public void cambiarEstadoProducto(String codigo) throws Exception {
		Producto producto = buscarProductoPorCodigo(codigo);

		if (producto == null) {
			throw new Exception("No se encontro el producto.");
		}

		gestionProducto.cambiarEstado(codigo);
		recargarProductos();
	}

	private void validarProducto(Producto producto) throws Exception {
		if (producto == null) {
			throw new Exception("El producto no puede ser nulo.");
		}

		if (producto.getCodigoProducto() == null || producto.getCodigoProducto().trim().isEmpty()) {
			throw new Exception("El codigo del producto es obligatorio.");
		}

		if (producto.getNombreProducto() == null || producto.getNombreProducto().trim().isEmpty()) {
			throw new Exception("El nombre del producto es obligatorio.");
		}

		if (producto.getCategoria() == null) {
			throw new Exception("La categoria es obligatoria.");
		}

		if (!producto.getCodigoProducto().matches("[A-Z0-9]{3,10}")) {
			throw new Exception("El codigo debe ser alfanumerico, en mayusculas, y tener entre 3 y 10 caracteres.");
		}

		if (!producto.getNombreProducto().matches("[\\p{L}0-9 .]+")) {
			throw new Exception("El nombre solo debe contener letras, numeros, espacios.");
		}

		if (producto.getPrecioCompra() <= 0) {
			throw new Exception("El precio de compra debe ser mayor que 0.");
		}

		if (producto.getPrecioVenta() <= 0) {
			throw new Exception("El precio de venta debe ser mayor que 0.");
		}

		if (producto.getPrecioVenta() < producto.getPrecioCompra()) {
			throw new Exception("El precio de venta no puede ser menor al precio de compra.");
		}

		if (producto.getStockActual() < 0) {
			throw new Exception("El stock actual no puede ser negativo.");
		}

		if (producto.getStockMinimo() < 0) {
			throw new Exception("El stock minimo no puede ser negativo.");
		}

		if (producto.getStockMaximo() <= 0) {
			throw new Exception("El stock maximo debe ser mayor que 0.");
		}

		if (producto.getStockActual() > producto.getStockMaximo()) {
			throw new Exception("El stock actual no puede ser mayor que el stock maximo.");
		}

		if (producto.getStockMinimo() > producto.getStockMaximo()) {
			throw new Exception("El stock minimo no puede ser mayor que el stock maximo.");
		}
	}

	public String generarCodigoProducto() {
		int mayor = 0;

		for (Producto producto : productos) {
			String codigo = producto.getCodigoProducto();

			if (codigo != null && codigo.matches("P\\d{5}")) {
				int numero = Integer.parseInt(codigo.substring(1));

				if (numero > mayor) {
					mayor = numero;
				}
			}
		}

		return String.format("P%05d", mayor + 1);
	}

	public void recargar() throws Exception {
		recargarProductos();
	}
}