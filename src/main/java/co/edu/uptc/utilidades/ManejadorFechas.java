package co.edu.uptc.utilidades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ManejadorFechas {
    
    private static final String FORMATO = "dd/MM/yyyy HH:mm:ss";

    public static String formatearFecha(LocalDateTime fecha) {
        if (fecha == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATO);
        return fecha.format(formatter);
    }

    public static LocalDateTime analizarFecha(String fechaTexto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATO);
        return LocalDateTime.parse(fechaTexto, formatter);
    }
}