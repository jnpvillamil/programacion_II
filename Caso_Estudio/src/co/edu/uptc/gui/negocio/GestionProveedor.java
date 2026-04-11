package co.edu.uptc.gui.negocio;

import java.util.List;

import co.edu.uptc.gui.modelo.Proveedor;
import co.edu.uptc.persistencia.LocalProveedor;

public class GestionProveedor {

    private LocalProveedor localProveedor;

    public GestionProveedor() {
        localProveedor = new LocalProveedor();
    }

    public boolean registrarProveedor(Proveedor proveedor) {
        return localProveedor.guardarProveedor(proveedor);
    }

    public boolean modificarProveedor(Proveedor proveedor) {
        return localProveedor.actualizarProveedor(proveedor);
    }

    public boolean eliminarProveedor(String codigoProveedor) {
        return localProveedor.eliminarProveedor(codigoProveedor);
    }

    public Proveedor buscarProveedor(String codigoProveedor) {
        return localProveedor.buscarProveedor(codigoProveedor);
    }

    public List<Proveedor> listarProveedores() {
        return localProveedor.getProveedores();
    }
}