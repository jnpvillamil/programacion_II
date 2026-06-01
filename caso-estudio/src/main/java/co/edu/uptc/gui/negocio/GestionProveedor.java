package co.edu.uptc.gui.negocio;

import java.util.List;
import co.edu.uptc.dao.ProveedorDao;
import co.edu.uptc.gui.modelo.Proveedor;

public class GestionProveedor {

    private ProveedorDao proveedorDao;

    public GestionProveedor() {
        this.proveedorDao = new ProveedorDao();
    }

    public void registrarProveedorLocal(Proveedor proveedor) {
        if (proveedor == null || proveedor.getNit() == null || proveedor.getNit().trim().isEmpty()) {
            throw new IllegalArgumentException("Error de Negocio: El NIT es obligatorio.");
        }
        if (proveedor.getRazonSocial() == null || proveedor.getRazonSocial().trim().isEmpty()) {
            throw new IllegalArgumentException("Error de Negocio: La Razón Social es obligatoria.");
        }
        try {
            proveedorDao.registrarProveedor(proveedor);
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar proveedor: " + e.getMessage(), e);
        }
    }

    public void actualizarProveedorLocal(Proveedor proveedor) {
        if (proveedor == null || proveedor.getNit() == null) {
            throw new IllegalArgumentException("Datos insuficientes para actualizar.");
        }
        try {
            proveedorDao.actualizarProveedor(proveedor);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar proveedor: " + e.getMessage(), e);
        }
    }

    public void inactivarProveedorLocal(String nit) {
        if (nit == null || nit.trim().isEmpty()) {
            throw new IllegalArgumentException("El NIT no puede estar vacío para inactivar.");
        }
        try {
            proveedorDao.inactivarProveedor(nit);
        } catch (Exception e) {
            throw new RuntimeException("Error al inactivar proveedor: " + e.getMessage(), e);
        }
    }

    public void eliminarProveedorLocal(String nit) {
        if (nit == null || nit.trim().isEmpty()) {
            throw new IllegalArgumentException("El NIT no puede estar vacío para eliminar.");
        }
        try {
            proveedorDao.eliminarProveedor(nit);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar proveedor: " + e.getMessage(), e);
        }
    }

    public List<Proveedor> listarProveedores() {
        try {
            return proveedorDao.listarProveedores();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar proveedores: " + e.getMessage(), e);
        }
    }
}