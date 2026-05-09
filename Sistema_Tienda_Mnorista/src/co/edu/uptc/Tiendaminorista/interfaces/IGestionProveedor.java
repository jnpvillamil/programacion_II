package co.edu.uptc.Tiendaminorista.interfaces;

import java.util.List;

import co.edu.uptc.Tiendaminorista.modelo.Proveedor;

public interface IGestionProveedor {

    public void guardar(Proveedor proveedor);

    public void actualizar(Proveedor proveedor);

    public List<Proveedor> listar();

    public void desactivar(String codigo);

    public void activar(String codigo);
}
