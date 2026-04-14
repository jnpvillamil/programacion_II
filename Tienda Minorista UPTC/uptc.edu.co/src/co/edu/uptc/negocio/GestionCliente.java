package co.edu.uptc.negocio;
import java.util.ArrayList;

public class GestionCliente {
    private ArrayList<Cliente> clientes;

    public GestionCliente() {
        this.clientes = new ArrayList<>();
    }

    public void registrarCliente(Cliente c) { clientes.add(c); }
    public void eliminarCliente(String id) { clientes.removeIf(cl -> cl.getIdentificacion().equals(id)); }
    public Cliente buscarCliente(String id) {
        for (Cliente c : clientes) if (c.getIdentificacion().equals(id)) return c;
        return null;
    }
    public ArrayList<Cliente> getClientes() { return clientes; }
}