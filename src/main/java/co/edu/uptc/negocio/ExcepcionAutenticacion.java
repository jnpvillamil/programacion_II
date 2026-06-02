package co.edu.uptc.negocio;

public class ExcepcionAutenticacion extends RuntimeException {

    public ExcepcionAutenticacion(String mensaje) {
        super(mensaje);
    }
}
