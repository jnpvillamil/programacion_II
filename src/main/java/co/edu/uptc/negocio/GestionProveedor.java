package co.edu.uptc.negocio;

import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.utilidades.ValidadorEntradas;
import java.util.List;

public class GestionProveedor {
    private Repositorio<Proveedor> persistenciaProveedor;

    public GestionProveedor(Repositorio<Proveedor> persistenciaProveedor) {
        this.persistenciaProveedor = persistenciaProveedor;
    }

    public boolean registrarProveedor(Proveedor proveedor) throws Exception {
        if (this.persistenciaProveedor.buscarPorId(proveedor.getNit()) != null) {
            throw new Exception("El NIT ya se encuentra registrado en el sistema.");
        }
        if (!ValidadorEntradas.esCorreoValido(proveedor.getCorreoElectronico())) {
            throw new Exception("El formato del correo electrónico no es válido.");
        }
        this.persistenciaProveedor.guardar(proveedor);
        return true;
    }

    public boolean actualizarProveedor(Proveedor proveedorActualizado) throws Exception {
        if (!ValidadorEntradas.esCorreoValido(proveedorActualizado.getCorreoElectronico())) {
            throw new Exception("El formato del correo electrónico no es válido.");
        }
        
        Proveedor existente = this.persistenciaProveedor.buscarPorId(proveedorActualizado.getNit());
        if (existente != null) {
            this.persistenciaProveedor.eliminar(existente.getNit());
            this.persistenciaProveedor.guardar(proveedorActualizado);
            return true;
        }
        return false;
    }

    public boolean inactivarProveedor(String nit) {
        Proveedor proveedor = this.persistenciaProveedor.buscarPorId(nit);
        if (proveedor != null) {
            proveedor.setActivo(false);
            try {
                actualizarProveedor(proveedor);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return false;
    }

    public Proveedor buscarProveedor(String nit) {
        return this.persistenciaProveedor.buscarPorId(nit);
    }

    public List<Proveedor> listarTodos() {
        return this.persistenciaProveedor.listar();
    }
}
