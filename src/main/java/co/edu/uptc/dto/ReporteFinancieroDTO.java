package co.edu.uptc.dto;

import java.util.List;

public record ReporteFinancieroDTO(
        String fechaGeneracion,
        ResumenPeriodoVentaDTO resumenPeriodoVenta,
        double utilidadBrutaPeriodo,
        List<RankingProductoDTO> rankingProducto,
        List<RankingClienteDTO> rankingCliente,
        BalanceResumenDTO balanceResumen) {

    public record ResumenPeriodoVentaDTO(double totalDiario, double totalMensual, double totalAnual) {
    }

    public record RankingProductoDTO(String nombreProducto, int cantidadVendida) {
    }

    public record RankingClienteDTO(String nombreCliente, double montoTotal) {
    }

    public record BalanceResumenDTO(double totalIngreso, double totalEgreso, double saldoNeto) {
    }
}
