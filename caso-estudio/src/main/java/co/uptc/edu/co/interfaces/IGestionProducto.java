package co.uptc.edu.co.interfaces;

import java.util.List;

import co.uptc.edu.co.modelo.Producto;

public interface IGestionProducto {

	Producto buscarProductoPorCodigo(String codigo);

	List<Producto> obtenerProductos();

	void registrarProducto(Producto producto) throws Exception;

	void actualizarProducto(Producto productoActualizado) throws Exception;

	void cambiarEstadoProducto(String codigo) throws Exception;

	String generarCodigoProducto();

	void recargar() throws Exception;

}
