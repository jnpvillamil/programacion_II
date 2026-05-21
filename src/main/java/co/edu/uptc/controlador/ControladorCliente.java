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
        } catch (IllegalStateException e) {
            return "Error: " + e.getMessage();
        } catch (ExcepcionAccesoDatos e) {
            return "Error: " + mensajeParaUsuario(e);
        }
    }

    public String modificarCliente(Cliente cliente) {
        try {
            gestionCliente.actualizarCliente(cliente);
            return "Cliente actualizado correctamente.";
        } catch (IllegalStateException e) {
            return "Error: " + e.getMessage();
        } catch (ExcepcionAccesoDatos e) {
            return "Error: " + mensajeParaUsuario(e);
        }
    }

    public String inactivarCliente(String identificacion) {
        try {
            gestionCliente.inactivarCliente(identificacion);
            return "Cliente inactivado correctamente.";
        } catch (IllegalStateException e) {
            return "Error: " + e.getMessage();
        } catch (ExcepcionAccesoDatos e) {
            return "Error: " + mensajeParaUsuario(e);
        }
    }

    public Cliente buscarCliente(String identificacion) {
        return gestionCliente.buscarCliente(identificacion);
    }

    public List<ClienteResumenDTO> obtenerListadoResumen() {
        return gestionCliente.listarResumen();
    }

    /**
     * Traduce fallos JDBC (red, credenciales, servicio caído) a mensajes comprensibles para la vista.
     */
    public static String mensajeParaUsuario(ExcepcionAccesoDatos e) {
        Throwable causa = e.getCause();
        if (causa instanceof SQLException sql) {
            String mensaje = sql.getMessage() != null ? sql.getMessage().toLowerCase() : "";
            if (mensaje.contains("communications link failure")
                    || mensaje.contains("connection refused")
                    || mensaje.contains("timed out")
                    || "08S01".equals(sql.getSQLState())) {
                return "No se pudo conectar con la base de datos en la nube. Verifique su red o el estado del servicio Aiven.";
            }
            if (mensaje.contains("access denied")) {
                return "Acceso denegado a la base de datos. Revise usuario y contraseña en Aiven.";
            }
            return "Error de base de datos: " + sql.getMessage();
        }
        return "Error de base de datos: " + e.getMessage();
    }
}
