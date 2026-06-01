package co.edu.uptc.dto;

public class ResultadoOperacion {

    private final boolean exito;
    private final String mensaje;
    private final Object dato;

    private ResultadoOperacion(boolean exito, String mensaje, Object dato) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.dato = dato;
    }

    public static ResultadoOperacion exito(String mensaje) {
        return new ResultadoOperacion(true, mensaje, null);
    }

    public static ResultadoOperacion exito(String mensaje, Object dato) {
        return new ResultadoOperacion(true, mensaje, dato);
    }

    public static ResultadoOperacion error(String mensaje) {
        return new ResultadoOperacion(false, mensaje, null);
    }

    public boolean isExito() {
        return exito;
    }

    public String getMensaje() {
        return mensaje;
    }

    @SuppressWarnings("unchecked")
    public <T> T getDato() {
        return (T) dato;
    }
}
