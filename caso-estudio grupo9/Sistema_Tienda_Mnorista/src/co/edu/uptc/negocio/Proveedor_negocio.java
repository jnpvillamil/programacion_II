package co.edu.uptc.negocio;

import co.edu.uptc.Datos.Proveedordt;
import java.util.ArrayList;
import java.util.List;

public class Proveedor_negocio {

    private static Proveedor_negocio instance;
    private List<Proveedordt> listaProveedores;

    private Proveedor_negocio() {
        listaProveedores = new ArrayList<>();
    }

    public static Proveedor_negocio getInstance() {
        if (instance == null) {
            instance = new Proveedor_negocio();
        }
        return instance;
    }

    public void agregarProveedor(Proveedordt proveedor) {
        listaProveedores.add(proveedor);
    }

    public List<Proveedordt> getListaProveedores() {
        return listaProveedores;
    }

    public void actualizarProveedor(Proveedordt proveedor) {
        for (int i = 0; i < listaProveedores.size(); i++) {
            if (listaProveedores.get(i).getNit().equals(proveedor.getNit())) {
                listaProveedores.set(i, proveedor);
                return;
            }
        }
    }

    public void desactivarProveedor(String nit) {
        for (Proveedordt p : listaProveedores) {
            if (p.getNit().equals(nit)) {
                p.setActivo(false);
                return;
            }
        }
    }

    public void activarProveedor(String nit) {
        for (Proveedordt p : listaProveedores) {
            if (p.getNit().equals(nit)) {
                p.setActivo(true);
                return;
            }
        }
    }
}