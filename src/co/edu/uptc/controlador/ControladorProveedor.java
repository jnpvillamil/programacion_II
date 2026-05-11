package co.edu.uptc.controlador;

import co.edu.uptc.dto.ProveedorResumenDTO;
import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.negocio.GestionProveedor;
import java.util.ArrayList;
import java.util.List;

public class ControladorProveedor {
    private GestionProveedor gestionProveedor;

    public ControladorProveedor(GestionProveedor gestionProveedor) {
        this.gestionProveedor = gestionProveedor;
    }

    public String registrarProveedor(Proveedor proveedor) {
        try {
            gestionProveedor.registrarProveedor(proveedor);
            return "Proveedor registrado con éxito.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String modificarProveedor(Proveedor proveedor) {
        try {
            if (gestionProveedor.actualizarProveedor(proveedor)) {
                return "Proveedor actualizado correctamente.";
            }
            return "Error: Proveedor no encontrado.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String inactivarProveedor(String nit) {
        if (gestionProveedor.inactivarProveedor(nit)) {
            return "Proveedor inactivado correctamente.";
        }
        return "Error: Proveedor no encontrado.";
    }

    public Proveedor buscarProveedor(String nit) {
        return gestionProveedor.buscarProveedor(nit);
    }

    public List<ProveedorResumenDTO> obtenerListadoResumen() {
        List<ProveedorResumenDTO> resumen = new ArrayList<>();
        for (Proveedor p : gestionProveedor.listarTodos()) {
            String estado = p.isActivo() ? "Activo" : "Inactivo";
            resumen.add(new ProveedorResumenDTO(p.getNit(), p.getRazonSocial(), p.getCorreoElectronico(), p.getTelefono(), estado));
        }
        return resumen;
    }
}
