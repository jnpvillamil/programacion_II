package co.edu.uptc.interfaces;

import co.edu.uptc.dto.ClienteResumenDTO;
import co.edu.uptc.dto.ProductoResumenDTO;
import co.edu.uptc.dto.ProveedorResumenDTO;
import co.edu.uptc.dto.UsuarioResumenDTO;
import co.edu.uptc.enums.RolUsuario;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.modelo.Usuario;

import java.util.List;

public interface ManejadorEventoAdministracion {

    String registrarCliente(Cliente cliente);

    String modificarCliente(Cliente cliente);

    String inactivarCliente(String identificacion);

    String activarCliente(String identificacion);

    Cliente buscarPorIdentificacion(String identificacion);

    Cliente buscarCliente(String identificacion);

    Cliente buscarPorCodigoCliente(String codigoCliente);

    List<ClienteResumenDTO> obtenerListadoResumenCliente();

    String registrarProveedor(Proveedor proveedor);

    String modificarProveedor(Proveedor proveedor);

    String inactivarProveedor(String nit);

    String activarProveedor(String nit);

    Proveedor buscarPorNit(String nit);

    Proveedor buscarProveedor(String nit);

    Proveedor buscarPorCodigoProveedor(String codigoProveedor);

    List<ProveedorResumenDTO> obtenerListadoResumenProveedor();

    String registrarProducto(Producto producto);

    String modificarProducto(Producto producto);

    String inactivarProducto(String codigoInterno);

    String activarProducto(String codigoInterno);

    Producto buscarProducto(String codigoInterno);

    List<ProductoResumenDTO> obtenerListadoResumenProducto();

    String registrarUsuario(String login, String clave, RolUsuario rol);

    Usuario buscarUsuario(String login);

    String eliminarUsuario(String login);

    String activarUsuario(String login);

    String inactivarUsuario(String login);

    List<UsuarioResumenDTO> listarResumenUsuario();
}
