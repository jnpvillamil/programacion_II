package co.edu.uptc.negocio;

import co.edu.uptc.dto.ResultadoOperacion;
import co.edu.uptc.interfaces.IPersistenciaProveedor;
import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.persistencia.PersistenciaProveedor;
import co.edu.uptc.utilidades.ValidadorEntradas;

import java.util.List;

public class GestionProveedor {

    private IPersistenciaProveedor repo;

    public GestionProveedor(IPersistenciaProveedor repo) {
        this.repo = repo;
    }

    public GestionProveedor() {
        this(new PersistenciaProveedor());
    }

    public ResultadoOperacion registrar(Proveedor proveedor) {
        ResultadoOperacion validacion = validarProveedor(proveedor);
        if (!validacion.isExito()) {
            return validacion;
        }
        if (repo.buscarPorId(proveedor.getCodigoProveedor()) != null) {
            return ResultadoOperacion.error("Error: El código de proveedor ya existe.");
        }
        repo.guardar(proveedor);
        return ResultadoOperacion.exito("Proveedor registrado con éxito.");
    }

    public ResultadoOperacion actualizar(Proveedor proveedor) {
        ResultadoOperacion validacion = validarProveedor(proveedor);
        if (!validacion.isExito()) {
            return validacion;
        }
        if (repo.buscarPorId(proveedor.getCodigoProveedor()) == null) {
            return ResultadoOperacion.error("Proveedor no encontrado.");
        }
        repo.actualizar(proveedor);
        return ResultadoOperacion.exito("Proveedor actualizado correctamente.");
    }

    public ResultadoOperacion inactivar(String codigo) {
        if (ValidadorEntradas.esVacio(codigo)) {
            return ResultadoOperacion.error("Ingrese el código del proveedor.");
        }
        Proveedor proveedor = buscar(codigo.trim());
        if (proveedor == null) {
            return ResultadoOperacion.error("Proveedor no encontrado.");
        }
        proveedor.setActivo(false);
        repo.actualizar(proveedor);
        return ResultadoOperacion.exito("Proveedor inactivado.");
    }

    public List<Proveedor> listar() {
        return repo.listar();
    }

    public Proveedor buscar(String criterio) {
        if (ValidadorEntradas.esVacio(criterio)) {
            return null;
        }
        return repo.buscarPorId(criterio.trim());
    }

    public Proveedor buscarPorIdentificacion(String criterio) {
        if (ValidadorEntradas.esVacio(criterio)) {
            return null;
        }
        String valor = criterio.trim();
        Proveedor porCodigo = repo.buscarPorId(valor);
        if (porCodigo != null) {
            return porCodigo;
        }

        for (Proveedor proveedor : repo.listar()) {
            if (valor.equals(proveedor.getIdentificacion())) {
                return proveedor;
            }
        }
        return null;
    }

    public ResultadoOperacion buscarProveedorActivo(String criterio) {
        if (ValidadorEntradas.esVacio(criterio)) {
            return ResultadoOperacion.error("Ingrese el NIT o código del proveedor.");
        }
        Proveedor proveedor = buscarPorIdentificacion(criterio);
        if (proveedor == null || !proveedor.isActivo()) {
            return ResultadoOperacion.error("Proveedor no encontrado o inactivo.");
        }
        return ResultadoOperacion.exito("Proveedor encontrado.", proveedor);
    }

    public ResultadoOperacion buscarValidado(String codigo) {
        if (ValidadorEntradas.esVacio(codigo)) {
            return ResultadoOperacion.error("Ingrese el código del proveedor.");
        }
        Proveedor proveedor = buscar(codigo);
        if (proveedor == null) {
            return ResultadoOperacion.error("Proveedor no encontrado.");
        }
        return ResultadoOperacion.exito("Proveedor encontrado.", proveedor);
    }

    private ResultadoOperacion validarProveedor(Proveedor proveedor) {
        if (proveedor == null) {
            return ResultadoOperacion.error("Proveedor inválido.");
        }
        if (ValidadorEntradas.esVacio(proveedor.getCodigoProveedor())) {
            return ResultadoOperacion.error("Código y Razón Social son obligatorios.");
        }
        if (ValidadorEntradas.esVacio(proveedor.getNombre())) {
            return ResultadoOperacion.error("Código y Razón Social son obligatorios.");
        }
        return ResultadoOperacion.exito("Validación correcta.");
    }
}
