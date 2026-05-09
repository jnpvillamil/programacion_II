package uptc.edu.co.util;

import java.util.regex.Pattern;

//Aqui se valida si hay un erro en el ususario si se repiten los usuarios o falta algo 
public class Validador {

    
    private static final Pattern PATRON_USUARIO =
        Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9_]{3,20}$");

    //Contraseñas con tipo de carateres 
    private static final Pattern PATRON_PASSWORD =
        Pattern.compile("^\\S{4,}$");

    //
    private static final Pattern PATRON_CODIGO =
        Pattern.compile("^[A-Z]{1,4}[0-9]{1,4}$");

    
    private static final Pattern PATRON_PRECIO =
        Pattern.compile("^[0-9]+(\\.[0-9]{1,2})?$");

    
    private static final Pattern PATRON_STOCK =
        Pattern.compile("^[0-9]+$");

    
    public static boolean esUsuarioValido(String usuario) {
        if (usuario == null || usuario.isBlank()) return false;
        return PATRON_USUARIO.matcher(usuario.trim()).matches();
    }

    
    public static boolean esPasswordValida(String password) {
        if (password == null || password.isEmpty()) return false;
        return PATRON_PASSWORD.matcher(password).matches();
    }

    
    public static boolean esCodigoValido(String codigo) {
        if (codigo == null || codigo.isBlank()) return false;
        return PATRON_CODIGO.matcher(codigo.trim().toUpperCase()).matches();
    }

    
    public static boolean esPrecioValido(String precio) {
        if (precio == null || precio.isBlank()) return false;
        return PATRON_PRECIO.matcher(precio.trim()).matches();
    }

    
    public static boolean esStockValido(String stock) {
        if (stock == null || stock.isBlank()) return false;
        return PATRON_STOCK.matcher(stock.trim()).matches();
    }

    
    public static boolean esCampoVacio(String texto) {
        return texto == null || texto.isBlank();
    }

    
    public static String mensajeErrorUsuario() {
        return "El usuario debe tener entre 3 y 20 caracteres (letras, números o _)";
    }

    public static String mensajeErrorPassword() {
        return "La contraseña debe tener mínimo 4 caracteres sin espacios";
    }

    public static String mensajeErrorCodigo() {
        return "El código debe ser letras mayúsculas + números (ej: P001, PROD10)";
    }

    public static String mensajeErrorPrecio() {
        return "El precio debe ser un número positivo (ej: 1500 o 3500.50)";
    }

    public static String mensajeErrorStock() {
        return "El stock debe ser un número entero no negativo";
    }
}
