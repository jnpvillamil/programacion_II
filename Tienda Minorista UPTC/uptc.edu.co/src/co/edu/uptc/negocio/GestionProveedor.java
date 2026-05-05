package co.edu.uptc.negocio;

import java.util.ArrayList;

public class GestionProveedor {
    private ArrayList<Proveedor> proveedores;

    public GestionProveedor() {
        this.proveedores = new ArrayList<>();
    }

    public void registrarProveedor(Proveedor p) {
        proveedores.add(p);
    }

    public Proveedor buscarProveedor(String nit) {
        for (Proveedor p : proveedores) {
            if (p.getIdentificacion().equals(nit)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Proveedor> getProveedores() {
        return proveedores;
    }

    public void eliminarProveedor(String nit) {
        proveedores.removeIf(p -> p.getIdentificacion().equals(nit));
    }
}