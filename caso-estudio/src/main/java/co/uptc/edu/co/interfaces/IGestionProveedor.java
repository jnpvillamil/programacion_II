package co.uptc.edu.co.interfaces;

import java.util.List;
import co.uptc.edu.co.modelo.Proveedor;

public interface IGestionProveedor {

    void guardar(Proveedor proveedor) throws Exception;

    void actualizar(Proveedor proveedor) throws Exception;

    Proveedor buscar(String codigo) throws Exception;

    List<Proveedor> listar() throws Exception;

    void cambiarEstado(String codigo) throws Exception;
}