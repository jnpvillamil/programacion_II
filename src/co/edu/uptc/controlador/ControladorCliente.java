package co.edu.uptc.controlador;

import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.negocio.GestionCliente;
import java.util.List;

public class ControladorCliente {
    private GestionCliente gestionCliente;

    public ControladorCliente(GestionCliente gestionCliente) {
        this.gestionCliente = gestionCliente;
    }

    public boolean registrarCliente(Cliente cliente) {
        return this.gestionCliente.registrarCliente(cliente);
    }

    public void actualizarCliente(Cliente cliente) {
        this.gestionCliente.actualizarCliente(cliente);
    }

    public Cliente buscarCliente(String codigoCliente) {
        return this.gestionCliente.buscarCliente(codigoCliente);
    }

    public List<Cliente> listarClientes() {
        return this.gestionCliente.listarClientes();
    }
}