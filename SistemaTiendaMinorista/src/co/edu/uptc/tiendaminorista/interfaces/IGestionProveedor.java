package co.edu.uptc.tiendaminorista.interfaces;

import java.util.List;
import co.edu.uptc.tiendaminorista.modelo.Proveedor;

public interface IGestionProveedor {
    void guardar(Proveedor proveedor);
    void actualizar(Proveedor proveedor);
    List<Proveedor> listar();
    void desactivar(String codigo);
    void activar(String codigo);
}
