package co.edu.uptc.gui.negocio;

import co.edu.uptc.gui.interfaces.RF13_RegistrarProveedor;
import co.edu.uptc.gui.interfaces.RF14_ValidarCampoNitObligatorio;
import co.edu.uptc.gui.interfaces.RF15_ValidarRazonSocialObligatoria;
import co.edu.uptc.gui.interfaces.RF16_AsignarResponsabilidadTributaria;
import co.edu.uptc.gui.interfaces.RF17_ActualizarDatosProveedor;
import co.edu.uptc.gui.interfaces.RF18_InactivarProveedor;
import co.edu.uptc.gui.modelo.Proveedor;
import co.edu.uptc.dao.ProveedorDao;
import java.util.List;

public class GestionProveedor implements 
    RF13_RegistrarProveedor, 
    RF14_ValidarCampoNitObligatorio, 
    RF15_ValidarRazonSocialObligatoria, 
    RF16_AsignarResponsabilidadTributaria, 
    RF17_ActualizarDatosProveedor, 
    RF18_InactivarProveedor {

    private ProveedorDao proveedorDao;

    public GestionProveedor() {
        this.proveedorDao = new ProveedorDao();
    }

    @Override
    public void ejecutarOperacionProveedor(Proveedor proveedor) {
        if (proveedor == null) {
            throw new IllegalArgumentException("El proveedor no puede ser nulo.");
        }

        if (proveedor.getNit() == null || proveedor.getNit().trim().isEmpty()) {
            throw new IllegalArgumentException("Error de Negocio: El NIT es un campo obligatorio.");
        }

        if (proveedor.getRazonSocial() == null || proveedor.getRazonSocial().trim().isEmpty()) {
            throw new IllegalArgumentException("Error de Negocio: La Razón Social es un campo obligatorio.");
        }

        proveedorDao.registrarProveedor(proveedor);
    }

    @Override
    public List<Proveedor> listarProveedores() {
        return proveedorDao.listarProveedores();
    }
}