package co.edu.uptc.gui.negocio;

import co.edu.uptc.gui.interfaces.IGestionDeProducto;
import co.edu.uptc.gui.modelo.Producto;
import co.edu.uptc.persistencia.LocalProducto;

import java.util.List;

public class GestionProducto 
 {
	private IGestionDeProducto IProducto;
	
	private LocalProducto localProducto; 
	
    public GestionProducto(IGestionDeProducto IProducto) {
        this.IProducto =  IProducto;
    }
    public void ejecutarOperacionProducto(Producto producto) {
    }

    public List<Producto> listarProductos() {
        return null;
    }

    
    public List<Producto> consultarAlertasStockBajo() {
        return null; 
    }

    
    public List<Producto> consultarDisponibles() {
        return null; 
    }

	public boolean registrarProducto(co.edu.uptc.dto.Producto producto) {
		// TODO Auto-generated method stub
		return false;
	}
}
 	