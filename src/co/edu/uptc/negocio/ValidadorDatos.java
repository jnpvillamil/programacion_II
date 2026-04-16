package co.edu.uptc.negocio;

public class ValidadorDatos {
    public static void validarCadenaNoVacia(String cadena, String campo) throws Exception {
        if(cadena == null || cadena.trim().isEmpty()) throw new Exception("El campo " + campo + " no puede estar vacío.");
    }
    public static int validarEnteroPositivo(String valor, String campo) throws Exception {
        try {
            int num = Integer.parseInt(valor);
            if(num <= 0) throw new Exception("El campo " + campo + " debe ser mayor a cero.");
            return num;
        } catch (NumberFormatException e) { throw new Exception("El campo " + campo + " debe ser un número entero válido."); }
    }
    public static double validarDecimalPositivo(String valor, String campo) throws Exception {
        try {
            double num = Double.parseDouble(valor);
            if(num < 0) throw new Exception("El campo " + campo + " no puede ser negativo.");
            return num;
        } catch (NumberFormatException e) { throw new Exception("El campo " + campo + " debe ser un número decimal válido."); }
    }
}