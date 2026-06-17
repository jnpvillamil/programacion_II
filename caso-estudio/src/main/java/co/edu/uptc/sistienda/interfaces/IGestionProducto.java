package co.edu.uptc.sistienda.interfaces;

import java.util.List;
import co.edu.uptc.sistienda.modelo.Producto;

public interface IGestionProducto {

	public void guardarProducto(Producto producto);

	public void actualizarProducto(Producto producto);

	public void inactivarProducto(String codigoInterno);
	
	public void activarProducto(String codigoInterno);

	public Producto buscarProductoPorCodigo(String codigoInterno);

	public List<Producto> obtenerListaProductos();

	public List<Producto> obtenerProductosConStockBajoMinimo();
}
