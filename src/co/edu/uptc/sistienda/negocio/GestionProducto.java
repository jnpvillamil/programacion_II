package co.edu.uptc.sistienda.negocio;

import java.util.List;

import co.edu.uptc.sistienda.interfaces.IGestionProducto;
import co.edu.uptc.sistienda.modelo.Producto;

public class GestionProducto {

	private IGestionProducto repositorioProducto;

	public GestionProducto(IGestionProducto repositorioProducto) {
		this.repositorioProducto = repositorioProducto;
	}

	public void registrarNuevoProducto(Producto producto) throws Exception {
		if (producto.getCodigoInterno() == null || producto.getCodigoInterno().trim().isEmpty()) {
			throw new Exception("El código interno del producto no puede estar vacío.");
		}
		if (producto.getNombreProducto() == null || producto.getNombreProducto().trim().isEmpty()) {
			throw new Exception("El nombre del producto no puede estar vacío.");
		}
		if (producto.getPrecioCompra() <= 0) {
			throw new Exception("El precio de compra debe ser mayor a cero.");
		}
		if (producto.getPrecioVenta() <= 0) {
			throw new Exception("El precio de venta debe ser mayor a cero.");
		}
		if (repositorioProducto.buscarProductoPorCodigo(producto.getCodigoInterno()) != null) {
			throw new Exception("Ya existe un producto con el código: " + producto.getCodigoInterno());
		}
		repositorioProducto.guardarProducto(producto);
	}

	public void modificarProducto(Producto producto) throws Exception {
		if (repositorioProducto.buscarProductoPorCodigo(producto.getCodigoInterno()) == null) {
			throw new Exception("No se encontró el producto con código: " + producto.getCodigoInterno());
		}
		repositorioProducto.actualizarProducto(producto);
	}

	public void inactivarProducto(String codigoInterno) throws Exception {
		if (repositorioProducto.buscarProductoPorCodigo(codigoInterno) == null) {
			throw new Exception("No se encontró el producto con código: " + codigoInterno);
		}
		repositorioProducto.inactivarProducto(codigoInterno);
	}
	
	public void activarProducto(String codigoInterno) throws Exception {
		if (repositorioProducto.buscarProductoPorCodigo(codigoInterno) == null) {
			throw new Exception("No se encontró el producto con código: " + codigoInterno);
		}
		repositorioProducto.activarProducto(codigoInterno);
	}

	public Producto consultarProductoPorCodigo(String codigoInterno) {
		return repositorioProducto.buscarProductoPorCodigo(codigoInterno);
	}

	public List<Producto> obtenerListaProductos() {
		return repositorioProducto.obtenerListaProductos();
	}

	public List<Producto> obtenerProductosConStockBajoMinimo() {
		return repositorioProducto.obtenerProductosConStockBajoMinimo();
	}
}
