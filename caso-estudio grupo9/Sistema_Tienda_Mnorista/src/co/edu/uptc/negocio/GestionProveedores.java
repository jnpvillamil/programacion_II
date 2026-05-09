package co.edu.uptc.negocio;

import java.util.ArrayList;

public class GestionProveedores {

    private ArrayList<Proveedor> lista = new ArrayList<>();

    public void agregarProveedor(Proveedor p) {
        lista.add(p);
    }

    public ArrayList<Proveedor> getProveedores() {
        return lista;
    }

    //UPDATE
    public boolean actualizarProveedor(String nit, Proveedor nuevo) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNit().equals(nit)) {
                lista.set(i, nuevo);
                return true;
            }
        }
        return false;
    }

    //DESACTIVAR 
    public boolean desactivarProveedor(String nit) {
        return lista.removeIf(p -> p.getNit().equals(nit));
    }

    //BUSCAR
    public Proveedor buscarProveedor(String nit) {
        for (Proveedor p : lista) {
            if (p.getNit().equals(nit)) {
                return p;
            }
        }
        return null;
    }
}