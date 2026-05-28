package co.edu.uptc.tiendaminorista.interfaces;

import java.util.List;
import co.edu.uptc.tiendaminorista.modelo.Producto;

public interface IGestionProducto {
    void guardar(Producto producto);
    void actualizar(Producto producto);
    List<Producto> listar();
    void desactivar(String codigo);
    void activar(String codigo);
    void registrarMovimientoInventario(String codigo, int cantidad);
}
