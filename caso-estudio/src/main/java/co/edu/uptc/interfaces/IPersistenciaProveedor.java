package co.edu.uptc.interfaces;

import co.edu.uptc.modelo.Proveedor;
import java.util.List;

public interface IPersistenciaProveedor {

    boolean guardar(Proveedor objeto);

    List<Proveedor> listar();

    boolean actualizar(Proveedor objeto);

    boolean eliminar(String id);

    Proveedor buscarPorId(String id);
}
