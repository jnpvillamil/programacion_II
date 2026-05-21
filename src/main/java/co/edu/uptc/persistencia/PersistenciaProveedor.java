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
        // Se permite eliminar por NIT o Código
        this.proveedores.removeIf(p -> p.getNit().equals(id) || p.getCodigoProveedor().equals(id));
    }

    @Override
    public List<Proveedor> listar() {
        return new ArrayList<>(this.proveedores);
    }

    @Override
    public Proveedor buscarPorId(String nit) {
        for (Proveedor p : this.proveedores) {
            if (p.getNit().equals(nit)) {
                return p;
            }
        }
        return null;
    }
}