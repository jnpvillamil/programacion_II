package co.edu.uptc.utilidades;

import java.text.NumberFormat;
import java.util.Locale;

public class FormateadorMoneda {

    public static String formatearPeso(double monto) {
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        return formatoMoneda.format(monto);
    }
}