package co.edu.uptc.persistencia;

import co.edu.uptc.dto.ClienteResumenDTO;
import co.edu.uptc.dto.ProveedorResumenDTO;
import co.edu.uptc.dto.UsuarioResumenDTO;
import co.edu.uptc.interfaces.RepositorioAdministracion;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.modelo.Usuario;

import java.util.List;

public class PersistenciaAdministracion implements RepositorioAdministracion {

    private final PersistenciaCliente persistenciaCliente;
    private final PersistenciaProveedor persistenciaProveedor;
    private final PersistenciaProducto persistenciaProducto;
    private final PersistenciaUsuario persistenciaUsuario;

    public PersistenciaAdministracion(PersistenciaCliente persistenciaCliente,
                                        PersistenciaProveedor persistenciaProveedor,
                                        PersistenciaProducto persistenciaProducto,
                                        PersistenciaUsuario persistenciaUsuario) {
        this.persistenciaCliente = persistenciaCliente;
        this.persistenciaProveedor = persistenciaProveedor;
        this.persistenciaProducto = persistenciaProducto;
        this.persistenciaUsuario = persistenciaUsuario;
    }

    @Override
    public void guardarCliente(Cliente cliente) {
        persistenciaCliente.guardar(cliente);
    }

    @Override
    public void actualizarCliente(Cliente cliente) {
        persistenciaCliente.actualizar(cliente);
    }

    @Override
    public boolean existeIdentificacionCliente(String identificacion) {
        return persistenciaCliente.existeIdentificacion(identificacion);
    }

    @Override
    public Cliente buscarClientePorCodigo(String codigoCliente) {
        return persistenciaCliente.buscarPorId(codigoCliente);
    }

    @Override
    public Cliente buscarClientePorIdentificacion(String identificacion) {
        return persistenciaCliente.buscarPorIdentificacion(identificacion);
    }

    @Override
    public void activarClientePorIdentificacion(String identificacion) {
        persistenciaCliente.activarPorIdentificacion(identificacion);
    }

    @Override
    public void inactivarClientePorIdentificacion(String identificacion) {
        persistenciaCliente.inactivarPorIdentificacion(identificacion);
    }

    @Override
    public List<ClienteResumenDTO> listarResumenCliente() {
        return persistenciaCliente.listarResumen();
    }

    @Override
    public void guardarProveedor(Proveedor proveedor) {
        persistenciaProveedor.guardar(proveedor);
    }

    @Override
    public void actualizarProveedor(Proveedor proveedor) {
        persistenciaProveedor.actualizar(proveedor);
    }

    @Override
    public boolean existeNitProveedor(String nit) {
        return persistenciaProveedor.existeNit(nit);
    }

    @Override
    public Proveedor buscarProveedorPorCodigo(String codigoProveedor) {
        return persistenciaProveedor.buscarPorId(codigoProveedor);
    }

    @Override
    public Proveedor buscarProveedorPorNit(String nit) {
        return persistenciaProveedor.buscarPorNit(nit);
    }

    @Override
    public void activarProveedorPorNit(String nit) {
        persistenciaProveedor.activarPorNit(nit);
    }

    @Override
    public void inactivarProveedorPorNit(String nit) {
        persistenciaProveedor.inactivarPorNit(nit);
    }

    @Override
    public List<ProveedorResumenDTO> listarResumenProveedor() {
        return persistenciaProveedor.listarResumen();
    }

    @Override
    public void guardarProducto(Producto producto) {
        persistenciaProducto.guardar(producto);
    }

    @Override
    public void eliminarProducto(String codigoInterno) {
        persistenciaProducto.eliminar(codigoInterno);
    }

    @Override
    public Producto buscarProductoPorCodigo(String codigoInterno) {
        return persistenciaProducto.buscarPorId(codigoInterno);
    }

    @Override
    public List<Producto> listarProducto() {
        return persistenciaProducto.listar();
    }

    @Override
    public void activarProductoPorCodigo(String codigoInterno) {
        persistenciaProducto.activarPorCodigoInterno(codigoInterno);
    }

    @Override
    public void inactivarProductoPorCodigo(String codigoInterno) {
        persistenciaProducto.inactivarPorCodigoInterno(codigoInterno);
    }

    @Override
    public void guardarUsuario(Usuario usuario) {
        persistenciaUsuario.guardar(usuario);
    }

    @Override
    public void eliminarUsuario(String usuarioLogin) {
        persistenciaUsuario.eliminar(usuarioLogin);
    }

    @Override
    public Usuario buscarUsuarioPorLogin(String usuarioLogin) {
        return persistenciaUsuario.buscarPorUsuarioLogin(usuarioLogin);
    }

    @Override
    public boolean existeUsuarioLogin(String usuarioLogin) {
        return persistenciaUsuario.existeUsuarioLogin(usuarioLogin);
    }

    @Override
    public void activarUsuarioPorLogin(String usuarioLogin) {
        persistenciaUsuario.activarPorUsuarioLogin(usuarioLogin);
    }

    @Override
    public void inactivarUsuarioPorLogin(String usuarioLogin) {
        persistenciaUsuario.inactivarPorUsuarioLogin(usuarioLogin);
    }

    @Override
    public List<UsuarioResumenDTO> listarResumenUsuario() {
        return persistenciaUsuario.listarResumen();
    }

    @Override
    public List<Usuario> listarUsuario() {
        return persistenciaUsuario.listar();
    }
}
