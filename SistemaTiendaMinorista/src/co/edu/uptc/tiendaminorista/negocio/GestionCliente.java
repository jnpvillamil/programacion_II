package co.edu.uptc.tiendaminorista.negocio;

import java.util.List;
import co.edu.uptc.tiendaminorista.interfaces.IGestionCliente;
import co.edu.uptc.tiendaminorista.modelo.Cliente;
import co.edu.uptc.tiendaminorista.modelo.CompasCliente;

public class GestionCliente {
    private IGestionCliente clientes;

    public GestionCliente(IGestionCliente clientes) {
        this.clientes = clientes;
    }

    public void agregarCliente(Cliente cliente) { 
        if (cliente.getNumeroIdentificacion() == null || cliente.getNumeroIdentificacion().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de documento es obligatorio.");
        }
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente es obligatorio.");
        }
        
        List<Cliente> actuales = listarClientes();
        for (Cliente c : actuales) {
            if (c.getNumeroIdentificacion().trim().equalsIgnoreCase(cliente.getNumeroIdentificacion().trim())) {
                throw new IllegalArgumentException("Ya existe un cliente registrado con el documento: " + cliente.getNumeroIdentificacion());
            }
        }
        clientes.guardar(cliente); 
    }

    public void actualizarCliente(Cliente cliente) { 
        if (cliente.getCodigo() == null || cliente.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException("No se puede actualizar un cliente sin un código de referencia válido.");
        }
        
        List<Cliente> actuales = listarClientes();
        for (Cliente c : actuales) {
            if (!c.getCodigo().equals(cliente.getCodigo()) && 
                c.getNumeroIdentificacion().trim().equalsIgnoreCase(cliente.getNumeroIdentificacion().trim())) {
                throw new IllegalArgumentException("Error: El documento " + cliente.getNumeroIdentificacion() + " ya pertenece a otro cliente.");
            }
        }

        clientes.actualizar(cliente); 
    }

    public void desactivarCliente(String codigo) { 
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("Código de cliente inválido.");
        }
        clientes.desactivar(codigo); 
    }

    public void activarCliente(String codigo) { 
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("Código de cliente inválido.");
        }
        clientes.activar(codigo); 
    }

    public List<Cliente> listarClientes() { 
        return clientes.listar(); 
    }
    public List<Cliente> consultarClientes(String textoBusqueda) {
        List<Cliente> todos = listarClientes();
        if (textoBusqueda == null || textoBusqueda.trim().isEmpty()) {
            return todos;
        }
        
        List<Cliente> filtrados = new java.util.ArrayList<>();
        String query = textoBusqueda.trim().toLowerCase();
        
        for (Cliente c : todos) {
            if ((c.getNombre() != null && c.getNombre().toLowerCase().contains(query)) ||
                (c.getNumeroIdentificacion() != null && c.getNumeroIdentificacion().contains(query))) {
                filtrados.add(c);
            }
        }
        return filtrados;
    }

	public List<CompasCliente> listarTodasLasCompras() {
		// TODO Auto-generated method stub
		return null;
	}
}