package co.edu.uptc.negocio;

import co.edu.uptc.dto.ReporteFinancieroDTO;
import co.edu.uptc.dto.ResultadoOperacion;
import co.edu.uptc.interfaces.Consultable;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.persistencia.PersistenciaConsultas;
import co.edu.uptc.persistencia.PersistenciaReportes;
import co.edu.uptc.utilidades.ValidadorEntradas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GestionConsultas implements Consultable {

    private final PersistenciaConsultas persistenciaConsultas;
    private final PersistenciaReportes persistenciaReportes;
    private final GestionContable gestionContable;
    private final GestionFinanciera gestionFinanciera;
    private final GestionProveedor gestionProveedor;

    public GestionConsultas(PersistenciaConsultas persistenciaConsultas,
                            PersistenciaReportes persistenciaReportes,
                            GestionContable gestionContable,
                            GestionFinanciera gestionFinanciera,
                            GestionProveedor gestionProveedor) {
        this.persistenciaConsultas = persistenciaConsultas;
        this.persistenciaReportes = persistenciaReportes;
        this.gestionContable = gestionContable;
        this.gestionFinanciera = gestionFinanciera;
        this.gestionProveedor = gestionProveedor;
    }

    public GestionConsultas(PersistenciaConsultas persistenciaConsultas,
                            PersistenciaReportes persistenciaReportes,
                            GestionContable gestionContable,
                            GestionFinanciera gestionFinanciera) {
        this(persistenciaConsultas, persistenciaReportes, gestionContable, gestionFinanciera,
                new GestionProveedor());
    }

    @Override
    public List<Object> consultarPorRangoFecha(LocalDateTime inicio, LocalDateTime fin) {
        List<Object> resultados = new ArrayList<>();
        if (inicio == null || fin == null || inicio.isAfter(fin)) {
            return resultados;
        }

        LocalDate fechaInicio = inicio.toLocalDate();
        LocalDate fechaFin = fin.toLocalDate();
        resultados.add(consultarResumenContable(fechaInicio, fechaFin));
        resultados.add(consultarUtilidadFinanciera(fechaInicio, fechaFin));
        return resultados;
    }

    @Override
    public List<Object> consultarPorCriterio(String criterio) {
        if (criterio == null || criterio.isBlank()) {
            return List.of();
        }

        return switch (criterio.trim().toUpperCase()) {
            case "STOCK_BAJO" -> new ArrayList<>(consultarStockBajoMinimo());
            default -> List.of();
        };
    }

    public List<Producto> consultarStockBajoMinimo() {
        return persistenciaConsultas.productosConStockBajoMinimo();
    }

    public ResultadoOperacion validarRangoFechas(LocalDate inicio, LocalDate fin) {
        if (inicio == null || fin == null) {
            return ResultadoOperacion.error("Seleccione fechas válidas.");
        }
        if (inicio.isAfter(fin)) {
            return ResultadoOperacion.error("La fecha inicial no puede ser mayor que la final.");
        }
        return ResultadoOperacion.exito("Rango válido.");
    }

    public ResultadoOperacion validarConsultaComprasProveedor(String codigoProveedor,
                                                              LocalDate inicio, LocalDate fin) {
        ResultadoOperacion rango = validarRangoFechas(inicio, fin);
        if (!rango.isExito()) {
            return rango;
        }
        if (ValidadorEntradas.esVacio(codigoProveedor)) {
            return ResultadoOperacion.error("Ingrese el código o NIT del proveedor.");
        }
        Proveedor proveedor = gestionProveedor.buscarPorIdentificacion(codigoProveedor.trim());
        if (proveedor == null || !proveedor.isActivo()) {
            return ResultadoOperacion.error("Proveedor no encontrado. Verifique el código o NIT ingresado.");
        }
        List<Compra> compras = consultarComprasPorProveedor(codigoProveedor.trim(), inicio, fin);
        if (compras.isEmpty()) {
            return ResultadoOperacion.error(
                    "El proveedor existe, pero no tiene compras en el rango de fechas seleccionado.");
        }
        return ResultadoOperacion.exito("Consulta válida.", compras);
    }

    public List<Compra> consultarComprasPorProveedor(String codigoProveedor,
                                                     LocalDate inicio, LocalDate fin) {
        if (codigoProveedor == null || codigoProveedor.isBlank() || inicio == null || fin == null) {
            return List.of();
        }
        if (inicio.isAfter(fin)) {
            return List.of();
        }
        return persistenciaConsultas.comprasPorProveedorEnRango(codigoProveedor.trim(), inicio, fin);
    }

    public Map<String, Double> consultarResumenContable(LocalDate inicio, LocalDate fin) {
        if (inicio == null || fin == null || inicio.isAfter(fin)) {
            return Map.of();
        }
        LocalDateTime desde = inicio.atStartOfDay();
        LocalDateTime hasta = fin.atTime(23, 59, 59);
        return gestionContable.generarResumenContablePorPeriodo(desde, hasta);
    }

    public double consultarTotalVentas(String periodo, LocalDate fechaReferencia) {
        if (fechaReferencia == null || periodo == null) {
            return 0.0;
        }
        return switch (periodo.toUpperCase()) {
            case "DIARIO" -> persistenciaReportes.totalVentasPorFecha(fechaReferencia);
            case "MENSUAL" -> persistenciaConsultas.totalVentasMensual(
                    fechaReferencia.getYear(), fechaReferencia.getMonthValue());
            case "ANUAL" -> persistenciaConsultas.totalVentasAnual(fechaReferencia.getYear());
            default -> 0.0;
        };
    }

    public ReporteFinancieroDTO consultarUtilidadFinanciera(LocalDate inicio, LocalDate fin) {
        return gestionFinanciera.generarReporteFinanciero(inicio, fin);
    }
}
