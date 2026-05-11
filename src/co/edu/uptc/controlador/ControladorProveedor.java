package co.edu.uptc.controlador;

import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.negocio.GestionProveedor;
import java.util.List;

public class ControladorProveedor {
    private GestionProveedor gestionProveedor;

    public ControladorProveedor(GestionProveedor gestionProveedor) {
        this.gestionProveedor = gestionProveedor;
    }

    public boolean registrarProveedor(Proveedor proveedor) {
        return this.gestionProveedor.registrarProveedor(proveedor);
    }

    public void actualizarProveedor(Proveedor proveedor) {
        this.gestionProveedor.actualizarProveedor(proveedor);
    }

    public Proveedor buscarProveedor(String codigoProveedor) {
        return this.gestionProveedor.buscarProveedor(codigoProveedor);
    }

    public List<Proveedor> listarProveedores() {
        return this.gestionProveedor.listarProveedores();
    }
}