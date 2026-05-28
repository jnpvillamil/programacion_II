package co.edu.uptc.tiendaminorista.negocio;

import java.util.List;
import co.edu.uptc.tiendaminorista.interfaces.IGestionCliente;
import co.edu.uptc.tiendaminorista.modelo.Cliente;

public class GestionCliente {
    private IGestionCliente clientes;

    public GestionCliente(IGestionCliente clientes) {
        this.clientes = clientes;
    }

    public void agregarCliente(Cliente cliente) { clientes.guardar(cliente); }
    public void actualizarCliente(Cliente cliente) { clientes.actualizar(cliente); }
    public void desactivarCliente(String codigo) { clientes.desactivar(codigo); }
    public void activarCliente(String codigo) { clientes.activar(codigo); }
    public List<Cliente> listarClientes() { return clientes.listar(); }
}
