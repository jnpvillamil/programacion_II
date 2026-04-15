package negocio;

import modelo.Proveedor;
import java.util.ArrayList;

public class NegocioProveedor {

    private ArrayList<Proveedor> lista;

    public NegocioProveedor() {
        lista = new ArrayList<>();
    }

    
    public void agregarProveedor(Proveedor p) {
        lista.add(p);
    }

   
    public Proveedor buscarProveedor(String codigo) {
        for (Proveedor p : lista) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    
    public boolean modificarProveedor(Proveedor nuevo) {
        Proveedor p = buscarProveedor(nuevo.getCodigo());

        if (p != null) {
            p.setNombre(nuevo.getNombre());
            p.setNit(nuevo.getNit());
            p.setDireccion(nuevo.getDireccion());
            p.setTelefono(nuevo.getTelefono());
            p.setEmail(nuevo.getEmail());
            return true;
        }
        return false;
    }

    
    public boolean eliminarProveedor(String codigo) {
        Proveedor p = buscarProveedor(codigo);

        if (p != null) {
            p.setActivo(false);
            return true;
        }
        return false;
    }

    public ArrayList<Proveedor> getLista() {
        return lista;
    }
}
