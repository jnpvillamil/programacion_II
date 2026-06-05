package co.edu.uptc.gui.interfaces;

import java.util.List;
import co.edu.uptc.gui.modelo.Producto;

public interface IGestionDeProducto {

	public void guardarProducto(Producto producto);

	public void actualizarProducto(Producto producto);

	public void inactivarProducto(String codigoInterno);
	
	public void activarProducto(String codigoInterno);

	public Producto buscarProductoPorCodigo(String codigoInterno);

	public List<Producto> obtenerListaProductos();

	public List<Producto> obtenerProductosConStockBajoMinimo();
	
    public void ejecutarOperacionProducto(Producto producto);
    public List<Producto> listarProductos();

	List<Producto> consultarAlertasStockBajo();

	List<Producto> consultarDisponibles();
}