package co.edu.uptc.dto;

public record ProductoResumenDTO(
        String codigo,
        String nombre,
        String categoria,
        double precioVenta,
        int stockActual,
        String alertaMinima,
        String estado) {
}
