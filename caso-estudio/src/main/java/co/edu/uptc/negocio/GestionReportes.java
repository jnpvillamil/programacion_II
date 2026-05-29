package co.edu.uptc.negocio;

import co.edu.uptc.dto.ReporteConsolidadoDiarioDTO;
import co.edu.uptc.dto.ReporteFinancieroDTO;
import co.edu.uptc.dto.ReportesDTO;
import co.edu.uptc.dto.ResumenDiarioJSONDTO;
import co.edu.uptc.enums.FormaPago;
import co.edu.uptc.interfaces.GeneradorReporte;
import co.edu.uptc.persistencia.PersistenciaReportes;
import co.edu.uptc.utilidades.ExportadorDatos;
import com.google.gson.GsonBuilder;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GestionReportes implements GeneradorReporte {

    private static final int TOP_PRODUCTOS_VENDIDOS = 5;

    private final PersistenciaReportes persistenciaReportes;
    private final GestionFinanciera gestionFinanciera;
    private final GestionContable gestionContable;
    private final GestionVentas gestionVentas;
    private final GestionCompras gestionCompras;

    public GestionReportes(PersistenciaReportes persistenciaReportes,
                           GestionFinanciera gestionFinanciera,
                           GestionContable gestionContable,
                           GestionVentas gestionVentas,
                           GestionCompras gestionCompras) {
        this.persistenciaReportes = persistenciaReportes;
        this.gestionFinanciera = gestionFinanciera;
        this.gestionContable = gestionContable;
        this.gestionVentas = gestionVentas;
        this.gestionCompras = gestionCompras;
    }

    public GestionReportes(PersistenciaReportes persistenciaReportes,
                           GestionFinanciera gestionFinanciera,
                           GestionContable gestionContable) {
        this(persistenciaReportes, gestionFinanciera, gestionContable,
                new GestionVentas(new GestionInventario()),
                new GestionCompras(new GestionInventario()));
    }

    public GestionReportes(PersistenciaReportes persistenciaReportes) {
        this(persistenciaReportes, new GestionFinanciera(), new GestionContable());
    }

    @Override
    public byte[] exportarPdf(List<Object> datos) {
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(datos);
        return json.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void imprimirReporte() {
        System.out.println("Reporte disponible para exportación vía exportarPdf().");
    }

    public boolean generarResumenDiarioJSON(Date fecha, String rutaDestino) {
        if (fecha == null || rutaDestino == null || rutaDestino.isBlank()) {
            return false;
        }

        LocalDate localDate = Instant.ofEpochMilli(fecha.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        ResumenDiarioJSONDTO resumen = construirResumenDiario(localDate);
        return ExportadorDatos.escribirResumenDiarioJSON(resumen, rutaDestino);
    }

    public ResumenDiarioJSONDTO construirResumenDiario(LocalDate fecha) {
        if (fecha == null) {
            return null;
        }

        double totalVentas = gestionVentas.calcularTotalVentasPorFecha(fecha);
        double totalCompras = gestionCompras.calcularTotalComprasPorFecha(fecha);
        ReporteFinancieroDTO financiero = gestionFinanciera.generarReporteFinanciero(fecha, fecha);

        ResumenDiarioJSONDTO resumen = new ResumenDiarioJSONDTO();
        resumen.setFecha(fecha.format(DateTimeFormatter.ISO_LOCAL_DATE));
        resumen.setTotalVentas(totalVentas);
        resumen.setTotalCompras(totalCompras);
        resumen.setUtilidadBruta(financiero.getUtilidadBruta());
        resumen.setVentasPorFormaPago(mapearVentasPorFormaPago(gestionVentas.agruparVentasPorFormaPago(fecha)));
        resumen.setProductosMasVendidos(mapearProductosMasVendidos(
                persistenciaReportes.productosMasVendidosPorFecha(fecha, TOP_PRODUCTOS_VENDIDOS)));
        resumen.setResumenContable(gestionContable.obtenerResumenContableDiario(fecha));
        return resumen;
    }

    public ReporteConsolidadoDiarioDTO generarConsolidadoDiario(LocalDate fecha) {
        ResumenDiarioJSONDTO json = construirResumenDiario(fecha);
        if (json == null) {
            return null;
        }

        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = fecha.atTime(23, 59, 59);

        ReporteConsolidadoDiarioDTO consolidado = new ReporteConsolidadoDiarioDTO();
        consolidado.setFecha(json.getFecha());
        consolidado.setTotalVentas(json.getTotalVentas());
        consolidado.setTotalCompras(json.getTotalCompras());
        consolidado.setUtilidadBruta(json.getUtilidadBruta());
        consolidado.setVentasPorFormaPago(persistenciaReportes.ventasPorFormaPagoPorFecha(fecha));
        consolidado.setProductosMasVendidos(
                persistenciaReportes.productosMasVendidosPorFecha(fecha, TOP_PRODUCTOS_VENDIDOS));
        consolidado.setResumenContable(gestionContable.generarResumenContablePorPeriodo(inicio, fin));
        return consolidado;
    }

    private List<ResumenDiarioJSONDTO.VentaPorFormaPago> mapearVentasPorFormaPago(
            Map<FormaPago, Double> ventasAgrupadas) {
        List<ResumenDiarioJSONDTO.VentaPorFormaPago> items = new ArrayList<>();
        for (Map.Entry<FormaPago, Double> entry : ventasAgrupadas.entrySet()) {
            items.add(new ResumenDiarioJSONDTO.VentaPorFormaPago(
                    entry.getKey().name(),
                    entry.getValue()));
        }
        return items;
    }

    private List<ResumenDiarioJSONDTO.ProductoMasVendido> mapearProductosMasVendidos(
            List<ReporteConsolidadoDiarioDTO.ProductoMasVendidoItem> productos) {
        List<ResumenDiarioJSONDTO.ProductoMasVendido> items = new ArrayList<>();
        for (ReporteConsolidadoDiarioDTO.ProductoMasVendidoItem producto : productos) {
            items.add(new ResumenDiarioJSONDTO.ProductoMasVendido(
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getCantidad()));
        }
        return items;
    }

    public List<ReportesDTO.MejorClienteItem> obtenerReporteMejorCliente() {
        return persistenciaReportes.reporteMejorCliente();
    }

    public List<ReportesDTO.ProductoVendidoItem> obtenerReporteProductoMasVendido() {
        return persistenciaReportes.reporteProductoMasVendido();
    }

    public List<ReportesDTO.VentaMetodoPagoItem> obtenerReporteVentasPorMetodoPago() {
        return persistenciaReportes.reporteMetodoPago();
    }

    public List<ReportesDTO.InventarioItem> obtenerReporteEstadoInventario() {
        return persistenciaReportes.reporteInventario();
    }
}
