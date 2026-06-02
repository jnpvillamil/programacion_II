package co.edu.uptc.negocio;

/**
 * Excepción de negocio para credenciales o estado de sesión inválido.
 */
public class ExcepcionAutenticacion extends RuntimeException {

    public ExcepcionAutenticacion(String mensaje) {
        super(mensaje);
    }
}
