package co.edu.uptc.utilidades;

import co.edu.uptc.persistencia.ExcepcionAccesoDatos;

import java.sql.SQLException;

public final class UtilidadMensajeAccesoDatos {

    private UtilidadMensajeAccesoDatos() {
    }

    public static String mensajeCliente(ExcepcionAccesoDatos excepcion) {
        return traducirExcepcion(excepcion,
                "Ya existe un registro con la misma identificación o código de cliente.",
                null);
    }

    public static String mensajeProveedor(ExcepcionAccesoDatos excepcion) {
        return traducirExcepcion(excepcion,
                "Ya existe un registro con el mismo NIT o código de proveedor.",
                "La tabla proveedor no existe. Consulte bd/01_estructura_maestra.sql (solo respaldo documental).");
    }

    public static String mensajeGeneral(ExcepcionAccesoDatos excepcion) {
        return traducirExcepcion(excepcion,
                "Ya existe un registro duplicado en la base de datos.",
                null);
    }

    private static String traducirExcepcion(ExcepcionAccesoDatos excepcion,
                                            String mensajeDuplicado,
                                            String mensajeTablaFaltante) {
        Throwable causa = excepcion.getCause();
        if (causa instanceof SQLException sql) {
            String mensaje = sql.getMessage() != null ? sql.getMessage().toLowerCase() : "";
            if (mensaje.contains("duplicate") || "23000".equals(sql.getSQLState())) {
                return mensajeDuplicado;
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
            if (mensajeTablaFaltante != null
                    && mensaje.contains("doesn't exist")
                    && mensaje.contains("proveedor")) {
                return mensajeTablaFaltante;
            }
            return "Error de base de datos: " + sql.getMessage();
        }
        return "Error de base de datos: " + excepcion.getMessage();
    }
}
