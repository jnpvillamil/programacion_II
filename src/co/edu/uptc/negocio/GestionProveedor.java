package co.edu.uptc.negocio;

import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Proveedor;
import java.util.List;

public class GestionProveedor {
    private Repositorio<Proveedor> persistenciaProveedor;

    public GestionProveedor(Repositorio<Proveedor> persistenciaProveedor) {
        this.persistenciaProveedor = persistenciaProveedor;
    }

    public boolean registrarProveedor(Proveedor proveedor) {
        if (this.persistenciaProveedor.buscarPorId(proveedor.getCodigoProveedor()) != null) {
            return false;
        }
        this.persistenciaProveedor.guardar(proveedor);
        return true;
    }

    public void actualizarProveedor(Proveedor proveedor) {
        Proveedor existente = this.persistenciaProveedor.buscarPorId(proveedor.getCodigoProveedor());
        if (existente != null) {
            this.persistenciaProveedor.eliminar(existente.getCodigoProveedor());
            this.persistenciaProveedor.guardar(proveedor);
        }
    }

    public Proveedor buscarProveedor(String codigoProveedor) {
        return this.persistenciaProveedor.buscarPorId(codigoProveedor);
    }

    public List<Proveedor> listarProveedores() {
        return this.persistenciaProveedor.listar();
    }
}