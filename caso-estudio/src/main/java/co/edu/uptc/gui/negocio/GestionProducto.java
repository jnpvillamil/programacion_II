package co.edu.uptc.gui.negocio;

import co.edu.uptc.gui.interfaces.*;
import co.edu.uptc.gui.modelo.Producto;
import java.util.List;

public class GestionProducto implements 
    RF01_RegistrarArticulo, 
    RF02_AsignarCodigoBarras, 
    RF03_ModificarPrecioVenta, 
    RF04_ModificarPrecioCompra, 
    RF05_ControlarStockMinimo, 
    RF06_ConsultarDisponibles, 
    RF07_EliminarDelCatalogo {
  
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
}