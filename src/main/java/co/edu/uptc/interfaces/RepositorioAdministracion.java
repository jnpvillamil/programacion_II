package co.edu.uptc.interfaces;

import co.edu.uptc.dto.ClienteResumenDTO;
import co.edu.uptc.dto.ProveedorResumenDTO;
import co.edu.uptc.dto.UsuarioResumenDTO;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.modelo.Usuario;

import java.util.List;

/**
 * Contrato unificado de persistencia para maestros del sistema.
 */
public interface RepositorioAdministracion {

    void guardarCliente(Cliente cliente);

    void actualizarCliente(Cliente cliente);

    boolean existeIdentificacionCliente(String identificacion);

    Cliente buscarClientePorCodigo(String codigoCliente);

    Cliente buscarClientePorIdentificacion(String identificacion);

    void activarClientePorIdentificacion(String identificacion);

    void inactivarClientePorIdentificacion(String identificacion);

    List<ClienteResumenDTO> listarResumenCliente();

    void guardarProveedor(Proveedor proveedor);

    void actualizarProveedor(Proveedor proveedor);

    boolean existeNitProveedor(String nit);

    Proveedor buscarProveedorPorCodigo(String codigoProveedor);

    Proveedor buscarProveedorPorNit(String nit);

    void activarProveedorPorNit(String nit);

    void inactivarProveedorPorNit(String nit);

    List<ProveedorResumenDTO> listarResumenProveedor();

    void guardarProducto(Producto producto);

    void eliminarProducto(String codigoInterno);

    Producto buscarProductoPorCodigo(String codigoInterno);

    List<Producto> listarProducto();

    void activarProductoPorCodigo(String codigoInterno);

    void inactivarProductoPorCodigo(String codigoInterno);

    void guardarUsuario(Usuario usuario);

    void eliminarUsuario(String usuarioLogin);

    Usuario buscarUsuarioPorLogin(String usuarioLogin);

    boolean existeUsuarioLogin(String usuarioLogin);

    void activarUsuarioPorLogin(String usuarioLogin);

    void inactivarUsuarioPorLogin(String usuarioLogin);

    List<UsuarioResumenDTO> listarResumenUsuario();

    List<Usuario> listarUsuario();
}
