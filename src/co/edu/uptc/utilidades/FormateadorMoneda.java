package co.edu.uptc.utilidades;

import java.text.NumberFormat;
import java.util.Locale;

public class FormateadorMoneda {
    
    public static String formatear(double valor) {
        NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        return formato.format(valor);
    }
}