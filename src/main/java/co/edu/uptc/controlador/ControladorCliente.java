package co.edu.uptc.controlador;

import co.edu.uptc.dto.ClienteResumenDTO;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.negocio.GestionCliente;
import co.edu.uptc.persistencia.ExcepcionAccesoDatos;

import java.sql.SQLException;
import java.util.List;

public class ControladorCliente {

    private final GestionCliente gestionCliente;

    public ControladorCliente(GestionCliente gestionCliente) {
        this.gestionCliente = gestionCliente;
    }

    public String registrarCliente(Cliente cliente) {
        try {
            gestionCliente.registrarCliente(cliente);
            return "Cliente registrado con éxito.";
        } catch (IllegalArgumentException | IllegalStateException excepcion) {
            return "Error: " + excepcion.getMessage();
        } catch (ExcepcionAccesoDatos excepcion) {
            return "Error: " + mensajeParaUsuario(excepcion);
        }
    }

    public String modificarCliente(Cliente cliente) {
        try {
            gestionCliente.actualizarCliente(cliente);
            return "Cliente actualizado correctamente.";
        } catch (IllegalArgumentException | IllegalStateException excepcion) {
            return "Error: " + excepcion.getMessage();
        } catch (ExcepcionAccesoDatos excepcion) {
            return "Error: " + mensajeParaUsuario(excepcion);
        }
    }

    public String inactivarCliente(String identificacion) {
        try {
            gestionCliente.inactivarCliente(identificacion);
            return "Cliente inactivado correctamente.";
        } catch (IllegalArgumentException | IllegalStateException excepcion) {
            return "Error: " + excepcion.getMessage();
        } catch (ExcepcionAccesoDatos excepcion) {
            return "Error: " + mensajeParaUsuario(excepcion);
        }
    }

    public String activarCliente(String identificacion) {
        try {
            gestionCliente.activarCliente(identificacion);
            return "Cliente activado correctamente.";
        } catch (IllegalArgumentException | IllegalStateException excepcion) {
            return "Error: " + excepcion.getMessage();
        } catch (ExcepcionAccesoDatos excepcion) {
            return "Error: " + mensajeParaUsuario(excepcion);
        }
    }

    public Cliente buscarPorIdentificacion(String identificacion) {
        try {
            return gestionCliente.buscarPorIdentificacion(identificacion);
        } catch (ExcepcionAccesoDatos excepcion) {
            throw excepcion;
        }
    }

    public Cliente buscarCliente(String identificacion) {
        return buscarPorIdentificacion(identificacion);
    }

    public Cliente buscarPorCodigo(String codigoCliente) {
        try {
            return gestionCliente.buscarPorCodigo(codigoCliente);
        } catch (ExcepcionAccesoDatos excepcion) {
            throw excepcion;
        }
    }

    public List<ClienteResumenDTO> obtenerListadoResumen() {
        try {
            return gestionCliente.listarResumen();
        } catch (ExcepcionAccesoDatos excepcion) {
            throw excepcion;
        }
    }

    public static String mensajeParaUsuario(ExcepcionAccesoDatos excepcion) {
        Throwable causa = excepcion.getCause();
        if (causa instanceof SQLException sql) {
            String mensaje = sql.getMessage() != null ? sql.getMessage().toLowerCase() : "";
            if (mensaje.contains("duplicate") || "23000".equals(sql.getSQLState())) {
                return "Ya existe un registro con la misma identificación o código de cliente.";
            }
            if (mensaje.contains("communications link failure")
                    || mensaje.contains("connection refused")
                    || mensaje.contains("timed out")
                    || "08S01".equals(sql.getSQLState())) {
                return "No se pudo conectar con la base de datos en la nube. Verifique su red o el estado del servicio Aiven.";
            }
            if (mensaje.contains("access denied")) {
                return "Acceso denegado a la base de datos. Revise usuario y contraseña en configuracion/bd.properties.";
            }
            return "Error de base de datos: " + sql.getMessage();
        }
        return "Error de base de datos: " + excepcion.getMessage();
    }
}
