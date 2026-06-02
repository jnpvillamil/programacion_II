package co.edu.uptc.dto;

import java.time.LocalDateTime;

public record MovimientoResumenDTO(
        LocalDateTime fecha,
        String tipoMovimiento,
        String cuenta,
        double valor,
        String descripcion) {
}
