package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Proveedor;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaProveedor implements Repositorio<Proveedor> {
    private List<Proveedor> proveedores;

    public PersistenciaProveedor() {
        this.proveedores = new ArrayList<>();
    }

    @Override
    public void guardar(Proveedor proveedor) {
        this.proveedores.add(proveedor);
    }

    @Override
    public void eliminar(String id) {
        this.proveedores.removeIf(p -> p.getCodigoProveedor().equals(id));
    }

    @Override
    public List<Proveedor> listar() {
        return new ArrayList<>(this.proveedores);
    }

    @Override
    public Proveedor buscarPorId(String id) {
        for (Proveedor p : this.proveedores) {
            if (p.getCodigoProveedor().equals(id)) {
                return p;
            }
        }
        return null;
    }
}