package co.edu.uptc.interfaces;

import co.edu.uptc.modelo.Cliente;
import java.util.List;

public interface GestionCliente {
    
    void crear(Cliente cliente);
    void actualizar(Cliente cliente);
    void eliminar(String codigo); 
    Cliente buscar(String codigo);
    List<Cliente> listar();
    List<Cliente> listarActivos();
    boolean existe(String codigo);
}