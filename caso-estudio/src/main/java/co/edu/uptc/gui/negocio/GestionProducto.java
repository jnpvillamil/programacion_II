package co.edu.uptc.gui.negocio;

import java.util.List;

import co.edu.uptc.dto.Producto;
import co.edu.uptc.gui.interfaces.ICrudProducto;
import co.edu.uptc.persistencia.LocalProducto;

public class GestionProducto implements ICrudProducto  {

    private LocalProducto localProducto;

    public GestionProducto() {
        localProducto = new LocalProducto();
    }

    public boolean registrarProducto(Producto producto) {
        return localProducto.guardarProducto(producto);
    }

    public Producto buscarProducto(String codigoProducto) {
        return localProducto.buscarProducto(codigoProducto);
    }

    public List<Producto> listarProductos() {
        return localProducto.getProductos();
    }

	@Override
	public void registrarProducto(co.edu.uptc.gui.modelo.Producto producto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public co.edu.uptc.gui.modelo.Producto obtenerProducto(String codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizarProducto(co.edu.uptc.gui.modelo.Producto producto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarProducto(String codigo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<co.edu.uptc.gui.modelo.Producto> listarProductosDisponibles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<co.edu.uptc.gui.modelo.Producto> consultarProductosStockBajo() {
		// TODO Auto-generated method stub
		return null;
	}
}