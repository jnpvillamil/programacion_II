package co.edu.uptc.gui.evento;

import co.edu.uptc.dto.ClienteResumenDTO;
import co.edu.uptc.dto.ProductoResumenDTO;
import co.edu.uptc.dto.ProveedorResumenDTO;
import co.edu.uptc.dto.UsuarioResumenDTO;
import co.edu.uptc.enums.RolUsuario;
import co.edu.uptc.interfaces.ManejadorEventoAdministracion;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.modelo.Usuario;
import co.edu.uptc.negocio.GestionCliente;
import co.edu.uptc.negocio.GestionProducto;
import co.edu.uptc.negocio.GestionProveedor;
import co.edu.uptc.negocio.GestionUsuario;
import co.edu.uptc.persistencia.ExcepcionAccesoDatos;
import co.edu.uptc.utilidades.UtilidadMensajeAccesoDatos;

import java.util.ArrayList;
import java.util.List;

public class EventoAdministracion implements ManejadorEventoAdministracion {

    private final GestionCliente gestionCliente;
    private final GestionProveedor gestionProveedor;
    private final GestionProducto gestionProducto;
    private final GestionUsuario gestionUsuario;

    public EventoAdministracion(GestionCliente gestionCliente,
                                GestionProveedor gestionProveedor,
                                GestionProducto gestionProducto,
                                GestionUsuario gestionUsuario) {
        this.gestionCliente = gestionCliente;
        this.gestionProveedor = gestionProveedor;
        this.gestionProducto = gestionProducto;
        this.gestionUsuario = gestionUsuario;
    }

    @Override
    public String registrarCliente(Cliente cliente) {
        try {
            gestionCliente.registrarCliente(cliente);
            return "Cliente registrado con éxito.";
        } catch (IllegalArgumentException | IllegalStateException excepcion) {
            return "Error: " + excepcion.getMessage();
        } catch (ExcepcionAccesoDatos excepcion) {
            return "Error: " + UtilidadMensajeAccesoDatos.mensajeCliente(excepcion);
        }
    }

    @Override
    public String modificarCliente(Cliente cliente) {
        try {
            gestionCliente.actualizarCliente(cliente);
            return "Cliente actualizado correctamente.";
        } catch (IllegalArgumentException | IllegalStateException excepcion) {
            return "Error: " + excepcion.getMessage();
        } catch (ExcepcionAccesoDatos excepcion) {
            return "Error: " + UtilidadMensajeAccesoDatos.mensajeCliente(excepcion);
        }
    }

    @Override
    public String inactivarCliente(String identificacion) {
        try {
            gestionCliente.inactivarCliente(identificacion);
            return "Cliente inactivado correctamente.";
        } catch (IllegalArgumentException | IllegalStateException excepcion) {
            return "Error: " + excepcion.getMessage();
        } catch (ExcepcionAccesoDatos excepcion) {
            return "Error: " + UtilidadMensajeAccesoDatos.mensajeCliente(excepcion);
        }
    }

    @Override
    public String activarCliente(String identificacion) {
        try {
            gestionCliente.activarCliente(identificacion);
            return "Cliente activado correctamente.";
        } catch (IllegalArgumentException | IllegalStateException excepcion) {
            return "Error: " + excepcion.getMessage();
        } catch (ExcepcionAccesoDatos excepcion) {
            return "Error: " + UtilidadMensajeAccesoDatos.mensajeCliente(excepcion);
        }
    }

    @Override
    public Cliente buscarPorIdentificacion(String identificacion) {
        return gestionCliente.buscarPorIdentificacion(identificacion);
    }

    @Override
    public Cliente buscarCliente(String identificacion) {
        return buscarPorIdentificacion(identificacion);
    }

    @Override
    public Cliente buscarPorCodigoCliente(String codigoCliente) {
        return gestionCliente.buscarPorCodigo(codigoCliente);
    }

    @Override
    public List<ClienteResumenDTO> obtenerListadoResumenCliente() {
        return gestionCliente.listarResumen();
    }

    @Override
    public String registrarProveedor(Proveedor proveedor) {
        try {
            gestionProveedor.registrarProveedor(proveedor);
            return "Proveedor registrado con éxito.";
        } catch (IllegalArgumentException | IllegalStateException excepcion) {
            return "Error: " + excepcion.getMessage();
        } catch (ExcepcionAccesoDatos excepcion) {
            return "Error: " + UtilidadMensajeAccesoDatos.mensajeProveedor(excepcion);
        }
    }

    @Override
    public String modificarProveedor(Proveedor proveedor) {
        try {
            gestionProveedor.actualizarProveedor(proveedor);
            return "Proveedor actualizado correctamente.";
        } catch (IllegalArgumentException | IllegalStateException excepcion) {
            return "Error: " + excepcion.getMessage();
        } catch (ExcepcionAccesoDatos excepcion) {
            return "Error: " + UtilidadMensajeAccesoDatos.mensajeProveedor(excepcion);
        }
    }

    @Override
    public String inactivarProveedor(String nit) {
        try {
            gestionProveedor.inactivarProveedor(nit);
            return "Proveedor inactivado correctamente.";
        } catch (IllegalArgumentException | IllegalStateException excepcion) {
            return "Error: " + excepcion.getMessage();
        } catch (ExcepcionAccesoDatos excepcion) {
            return "Error: " + UtilidadMensajeAccesoDatos.mensajeProveedor(excepcion);
        }
    }

    @Override
    public String activarProveedor(String nit) {
        try {
            gestionProveedor.activarProveedor(nit);
            return "Proveedor activado correctamente.";
        } catch (IllegalArgumentException | IllegalStateException excepcion) {
            return "Error: " + excepcion.getMessage();
        } catch (ExcepcionAccesoDatos excepcion) {
            return "Error: " + UtilidadMensajeAccesoDatos.mensajeProveedor(excepcion);
        }
    }

    @Override
    public Proveedor buscarPorNit(String nit) {
        return gestionProveedor.buscarPorNit(nit);
    }

    @Override
    public Proveedor buscarProveedor(String nit) {
        return buscarPorNit(nit);
    }

    @Override
    public Proveedor buscarPorCodigoProveedor(String codigoProveedor) {
        return gestionProveedor.buscarPorCodigo(codigoProveedor);
    }

    @Override
    public List<ProveedorResumenDTO> obtenerListadoResumenProveedor() {
        return gestionProveedor.listarResumen();
    }

    @Override
    public String registrarProducto(Producto producto) {
        try {
            gestionProducto.registrarProducto(producto);
            return "Producto registrado con éxito.";
        } catch (Exception excepcion) {
            return "Error: " + excepcion.getMessage();
        }
    }

    @Override
    public String modificarProducto(Producto producto) {
        try {
            if (gestionProducto.actualizarProducto(producto)) {
                return "Producto actualizado correctamente.";
            }
            return "Error: Producto no encontrado.";
        } catch (Exception excepcion) {
            return "Error: " + excepcion.getMessage();
        }
    }

    @Override
    public String inactivarProducto(String codigoInterno) {
        if (gestionProducto.inactivarProducto(codigoInterno)) {
            return "Producto inactivado correctamente.";
        }
        return "Error: Producto no encontrado o ya inactivo.";
    }

    @Override
    public String activarProducto(String codigoInterno) {
        if (gestionProducto.activarProducto(codigoInterno)) {
            return "Producto activado correctamente.";
        }
        return "Error: Producto no encontrado o ya activo.";
    }

    @Override
    public Producto buscarProducto(String codigoInterno) {
        return gestionProducto.buscarProducto(codigoInterno);
    }

    @Override
    public List<ProductoResumenDTO> obtenerListadoResumenProducto() {
        List<ProductoResumenDTO> resumen = new ArrayList<>();
        for (Producto producto : gestionProducto.listarTodos()) {
            String alerta = producto.getStockActual() <= producto.getStockMinimo()
                    ? "¡BAJO STOCK!"
                    : "Normal";
            resumen.add(new ProductoResumenDTO(
                    producto.getCodigoInterno(),
                    producto.getNombreProducto(),
                    producto.getCategoria().name(),
                    producto.getPrecioVenta(),
                    producto.getStockActual(),
                    alerta,
                    producto.isActivo() ? "Activo" : "Inactivo"));
        }
        return resumen;
    }

    @Override
    public String registrarUsuario(String login, String clave, RolUsuario rol) {
        try {
            gestionUsuario.registrarUsuario(login, clave, rol);
            return "Usuario registrado correctamente.";
        } catch (IllegalArgumentException | IllegalStateException excepcion) {
            return "Error: " + excepcion.getMessage();
        } catch (ExcepcionAccesoDatos excepcion) {
            return "Error: " + UtilidadMensajeAccesoDatos.mensajeGeneral(excepcion);
        }
    }

    @Override
    public Usuario buscarUsuario(String login) {
        try {
            return gestionUsuario.buscarUsuario(login);
        } catch (ExcepcionAccesoDatos excepcion) {
            return null;
        }
    }

    @Override
    public String eliminarUsuario(String login) {
        try {
            gestionUsuario.eliminarUsuario(login);
            return "Usuario eliminado correctamente.";
        } catch (IllegalArgumentException | IllegalStateException excepcion) {
            return "Error: " + excepcion.getMessage();
        } catch (ExcepcionAccesoDatos excepcion) {
            return "Error: " + UtilidadMensajeAccesoDatos.mensajeGeneral(excepcion);
        }
    }

    @Override
    public String activarUsuario(String login) {
        try {
            gestionUsuario.activarUsuario(login);
            return "Usuario activado correctamente.";
        } catch (IllegalArgumentException | IllegalStateException excepcion) {
            return "Error: " + excepcion.getMessage();
        } catch (ExcepcionAccesoDatos excepcion) {
            return "Error: " + UtilidadMensajeAccesoDatos.mensajeGeneral(excepcion);
        }
    }

    @Override
    public String inactivarUsuario(String login) {
        try {
            gestionUsuario.inactivarUsuario(login);
            return "Usuario inactivado correctamente.";
        } catch (IllegalArgumentException | IllegalStateException excepcion) {
            return "Error: " + excepcion.getMessage();
        } catch (ExcepcionAccesoDatos excepcion) {
            return "Error: " + UtilidadMensajeAccesoDatos.mensajeGeneral(excepcion);
        }
    }

    @Override
    public List<UsuarioResumenDTO> listarResumenUsuario() {
        try {
            return gestionUsuario.listarResumenUsuario();
        } catch (ExcepcionAccesoDatos excepcion) {
            return List.of();
        }
    }
}
