package co.edu.uptc.persistencia;

import co.edu.uptc.negocio.Proveedor;
import java.util.ArrayList;

public class PersistenciaProveedor {
    private ArrayList<Proveedor> listaProveedores;

    public PersistenciaProveedor() {
        this.listaProveedores = new ArrayList<>();
    }

    public void crear(Proveedor proveedor) {
        this.listaProveedores.add(proveedor);
    }

    public ArrayList<Proveedor> leerTodos() {
        return this.listaProveedores;
    }

    public void actualizar(String codigo, Proveedor proveedorActualizado) {
        for (int i = 0; i < listaProveedores.size(); i++) {
            if (listaProveedores.get(i).getCodigo().equals(codigo)) {
                listaProveedores.set(i, proveedorActualizado);
                break;
            }
        }
    }

    public void eliminar(String codigo) {
        listaProveedores.removeIf(proveedor -> proveedor.getCodigo().equals(codigo));
    }
}