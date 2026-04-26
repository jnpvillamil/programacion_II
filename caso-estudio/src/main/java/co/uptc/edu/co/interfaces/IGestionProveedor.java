package co.uptc.edu.co.interfaces;

import java.util.List;
import co.uptc.edu.co.modelo.Proveedor;

public interface IGestionProveedor {

    Proveedor buscarProveedorPorCodigo(String codigo);

    List<Proveedor> obtenerProveedores();

    void registrarProveedor(Proveedor proveedor) throws Exception;

    void actualizarProveedor(Proveedor proveedor) throws Exception;

    void cambiarEstadoProveedor(String codigo) throws Exception;
}
