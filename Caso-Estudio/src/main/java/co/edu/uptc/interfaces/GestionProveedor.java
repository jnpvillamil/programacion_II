package co.edu.uptc.interfaces;

import co.edu.uptc.modelo.Proveedor;
import java.util.List;

public interface GestionProveedor {
    
    // CRUD 
    void crear(Proveedor proveedor);
    void actualizar(Proveedor proveedor);
    void eliminar(String codigo); 
    Proveedor buscar(String codigo);
    Proveedor buscarPorNit(String nit);  
    
  
    List<Proveedor> listar();
    List<Proveedor> listarActivos();
    
 
    boolean existe(String codigo);
    boolean existeNit(String nit);
}