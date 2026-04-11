package co.edu.uptc.persistencia;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.gui.modelo.Proveedor;

public class LocalProveedor {

    private List<Proveedor> proveedores;

    public LocalProveedor() {
        proveedores = new ArrayList<>();
    }

    public boolean guardarProveedor(Proveedor proveedor) {
        if (buscarProveedor(proveedor.getCodigo()) != null) {
            return false;
        }
        proveedores.add(proveedor);
        return true;
    }

    public Proveedor buscarProveedor(String codigoProveedor) {
        for (Proveedor proveedor : proveedores) {
            if (proveedor.getCodigo().equalsIgnoreCase(codigoProveedor)) {
                return proveedor;
            }
        }
        return null;
    }

    public boolean actualizarProveedor(Proveedor proveedorActualizado) {
        for (int i = 0; i < proveedores.size(); i++) {
            if (proveedores.get(i).getCodigo().equalsIgnoreCase(proveedorActualizado.getCodigo())) {
                proveedores.set(i, proveedorActualizado);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarProveedor(String codigoProveedor) {
        Proveedor proveedor = buscarProveedor(codigoProveedor);
        if (proveedor != null) {
            proveedores.remove(proveedor);
            return true;
        }
        return false;
    }

    public List<Proveedor> getProveedores() {
        return proveedores;
    }
}