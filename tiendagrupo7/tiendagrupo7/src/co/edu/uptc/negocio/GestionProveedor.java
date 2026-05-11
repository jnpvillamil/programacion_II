package co.edu.uptc.negocio;

import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.persistencia.PersistenciaProveedor;
import java.util.List;

public class GestionProveedor {
    private PersistenciaProveedor repo;

    public GestionProveedor() { 
        this.repo = new PersistenciaProveedor(); 
    }

    public boolean registrar(Proveedor p) {
        if (repo.buscarPorId(p.getCodigoProveedor()) != null) return false;
        repo.guardar(p);
        return true;
    }

    public void actualizar(Proveedor p) {
        repo.actualizar(p);
    }

    public List<Proveedor> listar() { 
        return repo.listar(); 
    }

    public Proveedor buscar(String criterio) { 
        return repo.buscarPorId(criterio); 
    }
}