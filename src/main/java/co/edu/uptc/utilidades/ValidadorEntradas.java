package co.edu.uptc.utilidades;

public class ValidadorEntradas {
    
    public static boolean esNuloOVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    public static boolean esCorreoValido(String correo) {
        return correo != null && correo.contains("@") && correo.contains(".");
    }

    public static boolean esNumeroValido(String texto) {
        try {
            Double.parseDouble(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}