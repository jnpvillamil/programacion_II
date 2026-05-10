package co.uptc.edu.negocio;

import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.modelo.Proveedor;

public class GestionProveedores {

    private List<Proveedor> listaProveedores;

    public GestionProveedores() {
        listaProveedores = new ArrayList<>();
    }

    // REGISTRAR
    public boolean registrarProveedor(Proveedor proveedor) {

        if (buscarProveedor(proveedor.getCodigo()) != null) {
            return false;
        }

        listaProveedores.add(proveedor);
        return true;
    }

    // BUSCAR
    public Proveedor buscarProveedor(String codigo) {

        for (Proveedor p : listaProveedores) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    // MODIFICAR
    public boolean modificarProveedor(Proveedor nuevo) {

        Proveedor p = buscarProveedor(nuevo.getCodigo());

        if (p != null) {
            p.setRazonSocial(nuevo.getRazonSocial());
            p.setNit(nuevo.getNit());
            p.setDireccion(nuevo.getDireccion());
            p.setTelefono(nuevo.getTelefono());
            p.setCorreo(nuevo.getCorreo());
            return true;
        }

        return false;
    }

    // INACTIVAR
    public boolean inactivarProveedor(String codigo) {

        Proveedor p = buscarProveedor(codigo);

        if (p != null) {
            p.setActivo(false);
            return true;
        }

        return false;
    }
    
    //ACTIVAR PROVEEDOR
    public boolean activarProveedor(String codigo){

        Proveedor p = buscarProveedor(codigo);

        if(p != null){
            p.setActivo(true);
            return true;
        }

        return false;
    }

    // LISTAR
    public List<Proveedor> obtenerProveedores() {
        return listaProveedores;
    }
}