package co.uptc.edu.co.negocio;

import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.interfaces.IGestionProveedor;
import co.uptc.edu.co.modelo.Proveedor;
import co.uptc.edu.co.modelo.enums.EstadoEnum;

public class GestionProveedor implements IGestionProveedor {

    private List<Proveedor> proveedores;

    public GestionProveedor() {
        proveedores = new ArrayList<>();
    }

    @Override
    public Proveedor buscarProveedorPorCodigo(String codigo) {
        for (Proveedor proveedor : proveedores) {
            if (proveedor.getCodigoProveedor().equalsIgnoreCase(codigo)) {
                return proveedor;
            }
        }
        return null;
    }

    @Override
    public List<Proveedor> obtenerProveedores() {
        return new ArrayList<>(proveedores);
    }

    @Override
    public void registrarProveedor(Proveedor proveedor) throws Exception {
        validarProveedor(proveedor);

        if (buscarProveedorPorCodigo(proveedor.getCodigoProveedor()) != null) {
            throw new Exception("Ya existe un proveedor con ese código.");
        }

        proveedor.setEstado(EstadoEnum.ACTIVO);
        proveedores.add(proveedor);
    }

    @Override
    public void actualizarProveedor(Proveedor proveedorActualizado) throws Exception {
        validarProveedor(proveedorActualizado);

        Proveedor proveedorExistente = buscarProveedorPorCodigo(proveedorActualizado.getCodigoProveedor());

        if (proveedorExistente == null) {
            throw new Exception("No se encontró el proveedor a actualizar.");
        }

        proveedorExistente.setRazonSocial(proveedorActualizado.getRazonSocial());
        proveedorExistente.setNit(proveedorActualizado.getNit());
        proveedorExistente.setDireccion(proveedorActualizado.getDireccion());
        proveedorExistente.setTelefono(proveedorActualizado.getTelefono());
        proveedorExistente.setCorreoElectronico(proveedorActualizado.getCorreoElectronico());
    }

    @Override
    public void cambiarEstadoProveedor(String codigo) throws Exception {
        Proveedor proveedor = buscarProveedorPorCodigo(codigo);

        if (proveedor == null) {
            throw new Exception("No se encontró el proveedor.");
        }

        if (proveedor.getEstado() == EstadoEnum.ACTIVO) {
            proveedor.setEstado(EstadoEnum.INACTIVO);
        } else {
            proveedor.setEstado(EstadoEnum.ACTIVO);
        }
    }

    private void validarProveedor(Proveedor proveedor) throws Exception {
        if (proveedor == null) {
            throw new Exception("El proveedor no puede ser nulo.");
        }

        if (proveedor.getCodigoProveedor() == null || proveedor.getCodigoProveedor().trim().isEmpty()) {
            throw new Exception("El código del proveedor es obligatorio.");
        }

        if (proveedor.getRazonSocial() == null || proveedor.getRazonSocial().trim().isEmpty()) {
            throw new Exception("La razón social es obligatoria.");
        }

        if (proveedor.getNit() == null || proveedor.getNit().trim().isEmpty()) {
            throw new Exception("El NIT es obligatorio.");
        }

        if (proveedor.getDireccion() == null || proveedor.getDireccion().trim().isEmpty()) {
            throw new Exception("La dirección es obligatoria.");
        }

        if (proveedor.getTelefono() == null || proveedor.getTelefono().trim().isEmpty()) {
            throw new Exception("El teléfono es obligatorio.");
        }

        if (proveedor.getCorreoElectronico() == null || proveedor.getCorreoElectronico().trim().isEmpty()) {
            throw new Exception("El correo electrónico es obligatorio.");
        }
    }
}