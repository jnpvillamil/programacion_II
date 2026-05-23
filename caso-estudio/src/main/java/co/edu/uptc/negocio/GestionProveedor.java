package co.edu.uptc.negocio;

import co.edu.uptc.interfaces.IPersistenciaProveedor;
import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.persistencia.PersistenciaProveedor;
import java.util.List;


public class GestionProveedor {
    private IPersistenciaProveedor repo;

  
    public GestionProveedor(IPersistenciaProveedor repo) { 
        this.repo = repo; 
    }
 
    public GestionProveedor() { 
        this(new PersistenciaProveedor());
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