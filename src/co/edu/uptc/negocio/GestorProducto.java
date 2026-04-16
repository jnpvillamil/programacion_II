
package co.edu.uptc.negocio;

import co.edu.uptc.persistencia.PersistenciaProducto;
import java.util.List;

public class GestorProducto {
    private PersistenciaProducto persistenciaProducto;

    // El gestor recibe la persistencia por inyección de dependencias
    public GestorProducto(PersistenciaProducto persistenciaProducto) {
        this.persistenciaProducto = persistenciaProducto;
    }

    public void registrarProducto(Producto p) throws Exception {
        // Agregar validaciones extra de negocio antes de guardar
        persistenciaProducto.crear(p);
    }

 
    public List<Producto> obtenerProductos() {
        return persistenciaProducto.leerTodos();
    }
}