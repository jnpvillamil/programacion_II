package co.edu.uptc.utilidades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ManejadorFechas {

    public static LocalDateTime obtenerFechaActual() {
        return LocalDateTime.now();
    }

    public static String formatearFecha(LocalDateTime fecha) {
        if (fecha == null) {
            return "";
        }
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return fecha.format(formateador);
    }
}