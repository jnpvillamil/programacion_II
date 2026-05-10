package co.edu.uptc.Tiendaminorista.interfaces;

import java.util.List;
import co.edu.uptc.Tiendaminorista.modelo.Producto;

public interface IGestionProducto {

    public void guardar(Producto producto); 
    
    public void actualizar(Producto producto); 
    
    public List<Producto> listar();  
    
    public void desactivar(String codigo);
 
    public void activar(String codigo);

    public void registrarMovimientoInventario(String codigo, int cantidad);
}