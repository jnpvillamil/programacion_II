package co.edu.uptc.gui.negocio;

import co.edu.uptc.dao.ProveedorDao;
import co.edu.uptc.gui.interfaces.ICrudProveedor;
import co.edu.uptc.gui.modelo.Proveedor;
import java.util.List;

public class GestionProveedor implements ICrudProveedor {
    private ProveedorDao proveedorDao;

    public GestionProveedor() {
        this.proveedorDao = new ProveedorDao();
    }

    @Override
    public void registrarProveedor(Proveedor proveedor) {
        // REGLAS DE NEGOCIO 
        // 1. Validar que el proveedor no sea nulo
        if (proveedor == null) {
            throw new IllegalArgumentException("El objeto proveedor no puede ser nulo.");
        }

        // 2. Validar Criterio de Aceptación: No se permite registrar sin NIT
        if (proveedor.getNit() == null || proveedor.getNit().trim().isEmpty()) {
            throw new IllegalArgumentException("Error de Negocio: No se permite registrar un proveedor sin NIT.");
        }

        // 3. Validar Criterio de Aceptación: No se permite registrar sin Razón Social
        if (proveedor.getRazonSocial() == null || proveedor.getRazonSocial().trim().isEmpty()) {
            throw new IllegalArgumentException("Error de Negocio: No se permite registrar un proveedor sin razón social.");
        }

        proveedorDao.registrarProveedor(proveedor);
    }

    @Override
    public Proveedor obtenerProveedor(String nit) {
        if (nit == null || nit.trim().isEmpty()) {
            throw new IllegalArgumentException("El NIT suministrado no es válido para la búsqueda.");
        }
        return proveedorDao.obtenerProveedor(nit);
    }

    @Override
    public void actualizarProveedor(Proveedor proveedor) {
        if (proveedor == null || proveedor.getNit() == null || proveedor.getNit().trim().isEmpty()) {
            throw new IllegalArgumentException("No se puede actualizar un proveedor sin un NIT válido.");
        }
        proveedorDao.actualizarProveedor(proveedor);
    }

    @Override
    public void eliminarProveedor(String nit) {
        if (nit == null || nit.trim().isEmpty()) {
            throw new IllegalArgumentException("Se requiere un NIT válido para eliminar al proveedor.");
        }
        proveedorDao.eliminarProveedor(nit);
    }

    @Override
    public List<Proveedor> listarProveedores() {
        return proveedorDao.listarProveedores();
    }
}