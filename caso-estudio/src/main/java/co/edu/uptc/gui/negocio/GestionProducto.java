package co.edu.uptc.gui.negocio;

import co.edu.uptc.gui.interfaces.*;
import co.edu.uptc.gui.modelo.Producto;
import java.util.List;

public class GestionProducto implements 
    IRegistrarArticulo, 
    IAsignarCodigoBarras, 
    IModificarPrecioVenta, 
    IModificarPrecioC, 
    IControlarStockMin, 
    IConsultarDisponibles, 
    IEliminarDelCatalogo {
  
    @Override
    public void ejecutarOperacionProducto(Producto producto) {
    }

    @Override
    public List<Producto> listarProductos() {
        return null;
    }

    @Override
    public List<Producto> consultarAlertasStockBajo() {
        return null; 
    }

    @Override
    public List<Producto> consultarDisponibles() {
        return null; 
    }

	public boolean registrarProducto(co.edu.uptc.dto.Producto producto) {
		// TODO Auto-generated method stub
		return false;
	}
}