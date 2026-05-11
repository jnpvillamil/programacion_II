package co.edu.uptc.controlador;

import co.edu.uptc.dto.ClienteResumenDTO;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.negocio.GestionCliente;
import java.util.ArrayList;
import java.util.List;

public class ControladorCliente {
    private GestionCliente gestionCliente;

    public ControladorCliente(GestionCliente gestionCliente) {
        this.gestionCliente = gestionCliente;
    }

    public String registrarCliente(Cliente cliente) {
        try {
            gestionCliente.registrarCliente(cliente);
            return "Cliente registrado con éxito.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String modificarCliente(Cliente cliente) {
        if (gestionCliente.actualizarCliente(cliente)) {
            return "Cliente actualizado correctamente.";
        }
        return "Error: Cliente no encontrado.";
    }

    public String inactivarCliente(String identificacion) {
        if (gestionCliente.inactivarCliente(identificacion)) {
            return "Cliente inactivado correctamente.";
        }
        return "Error: Cliente no encontrado.";
    }

    public Cliente buscarCliente(String identificacion) {
        return gestionCliente.buscarCliente(identificacion);
    }

    public List<ClienteResumenDTO> obtenerListadoResumen() {
        List<ClienteResumenDTO> resumen = new ArrayList<>();
        for (Cliente c : gestionCliente.listarTodos()) {
            String nombreCompleto = c.getNombre() + " " + c.getApellido();
            String estado = c.isActivo() ? "Activo" : "Inactivo";
            resumen.add(new ClienteResumenDTO(c.getCodigoCliente(), nombreCompleto, c.getTelefono(), estado));
        }
        return resumen;
    }
}