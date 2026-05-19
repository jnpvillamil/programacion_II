package co.edu.uptc.utilidades;

public class ValidadorEntradas {

    public static boolean esVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    public static boolean esNumero(String texto) {
        if (esVacio(texto)) {
            return false;
        }
        try {
            Double.parseDouble(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validarEmail(String email) {
        if (esVacio(email)) {
            return false;
        }
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(regex);
    }
}