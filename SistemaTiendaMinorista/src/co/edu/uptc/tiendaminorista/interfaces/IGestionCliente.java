package co.edu.uptc.tiendaminorista.interfaces;

import java.util.List;
import co.edu.uptc.tiendaminorista.modelo.Cliente;

public interface IGestionCliente {
    void guardar(Cliente cliente);
    void actualizar(Cliente cliente);
    List<Cliente> listar();
    void desactivar(String codigo);
    void activar(String codigo);
}
