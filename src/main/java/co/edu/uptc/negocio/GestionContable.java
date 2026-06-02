package co.edu.uptc.negocio;

import co.edu.uptc.dto.ReporteFinancieroDTO;
import co.edu.uptc.dto.ReporteUtilidadDTO;
import co.edu.uptc.dto.MovimientoResumenDTO;
import co.edu.uptc.enums.TipoMovimiento;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.DetalleCompra;
import co.edu.uptc.modelo.MovimientoContable;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.interfaces.RepositorioSistema;
import co.edu.uptc.utilidades.AdaptadorJson;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GestionContable {

    public static final String RUTA_REPORTE_FINANCIERO = "reportes_financieros.json";

    private final RepositorioSistema persistenciaSistema;

    public GestionContable(RepositorioSistema persistenciaSistema) {
        this.persistenciaSistema = persistenciaSistema;
    }

    public void registrarPartidaDoble(double valor, TipoMovimiento tipo, String cuentaContable, String descripcion) {
        String codigoTransaccion = UUID.randomUUID().toString();
        MovimientoContable movimiento = new MovimientoContable(
                codigoTransaccion,
                LocalDateTime.now(),
                tipo,
                cuentaContable,
                valor,
                descripcion);
        persistenciaSistema.guardarMovimientoContable(movimiento);
    }

    public void registrarAsientoVenta(Venta venta) {
        for (MovimientoContable movimiento : construirAsientoVenta(venta)) {
            persistenciaSistema.guardarMovimientoContable(movimiento);
        }
    }

    public List<MovimientoContable> construirAsientoVenta(Venta venta) {
        String codigoTransaccion = UUID.randomUUID().toString();
        String referencia = "Factura " + venta.getNumeroFactura();
        List<MovimientoContable> asiento = new ArrayList<>();
        asiento.add(crearMovimiento(
                codigoTransaccion, TipoMovimiento.INGRESO, "Caja/Bancos", venta.getTotal(),
                "Débito cobro " + referencia));
        asiento.add(crearMovimiento(
                codigoTransaccion, TipoMovimiento.INGRESO, "Ingresos por Ventas", venta.getSubtotal(),
                "Crédito ingreso por venta " + referencia));
        asiento.add(crearMovimiento(
                codigoTransaccion, TipoMovimiento.INGRESO, "IVA Generado", venta.getIva(),
                "Crédito IVA generado " + referencia));
        return asiento;
    }

    public void registrarAsientoAnulacionVenta(Venta venta) {
        for (MovimientoContable movimiento : construirAsientoAnulacionVenta(venta)) {
            persistenciaSistema.guardarMovimientoContable(movimiento);
        }
    }

    public List<MovimientoContable> construirAsientoAnulacionVenta(Venta venta) {
        String codigoTransaccion = UUID.randomUUID().toString();
        String referencia = "Anulación factura " + venta.getNumeroFactura();
        List<MovimientoContable> asiento = new ArrayList<>();
        asiento.add(crearMovimiento(
                codigoTransaccion, TipoMovimiento.EGRESO, "Caja/Bancos", venta.getTotal(),
                "Reverso cobro " + referencia));
        asiento.add(crearMovimiento(
                codigoTransaccion, TipoMovimiento.EGRESO, "Ingresos por Ventas", venta.getSubtotal(),
                "Reverso venta " + referencia));
        asiento.add(crearMovimiento(
                codigoTransaccion, TipoMovimiento.EGRESO, "IVA Generado", venta.getIva(),
                "Reverso IVA " + referencia));
        return asiento;
    }

    public void registrarAsientoCompra(Compra compra, double subtotal, double iva) {
        for (MovimientoContable movimiento : construirAsientoCompra(compra, subtotal, iva)) {
            persistenciaSistema.guardarMovimientoContable(movimiento);
        }
    }

    public List<MovimientoContable> construirAsientoCompra(Compra compra, double subtotal, double iva) {
        String codigoTransaccion = UUID.randomUUID().toString();
        String referencia = "Factura proveedor " + compra.getNumeroFacturaProveedor();
        List<MovimientoContable> asiento = new ArrayList<>();
        asiento.add(crearMovimiento(
                codigoTransaccion, TipoMovimiento.EGRESO, "Inventario", subtotal,
                "Débito inventario " + referencia));
        asiento.add(crearMovimiento(
                codigoTransaccion, TipoMovimiento.EGRESO, "IVA Descontable", iva,
                "Débito IVA descontable " + referencia));
        asiento.add(crearMovimiento(
                codigoTransaccion, TipoMovimiento.INGRESO, "Proveedores", compra.getTotal(),
                "Crédito proveedor " + referencia));
        return asiento;
    }

    public List<MovimientoContable> listarMovimiento() {
        return persistenciaSistema.listarMovimientoContable();
    }

    public List<MovimientoResumenDTO> listarResumenMovimiento() {
        return persistenciaSistema.listarResumenMovimiento();
    }

    public List<ReporteUtilidadDTO> listarReporteUtilidad() {
        return persistenciaSistema.listarReporteUtilidad();
    }

    public ReporteFinancieroDTO generarReporteFinanciero() {
        ReporteFinancieroDTO reporte = persistenciaSistema.construirReporteFinanciero();
        AdaptadorJson.escribirJsonEnArchivo(reporte, RUTA_REPORTE_FINANCIERO);
        return reporte;
    }

    public ReporteFinancieroDTO leerReporteFinanciero() {
        return AdaptadorJson.leerJsonDesdeArchivo(RUTA_REPORTE_FINANCIERO, ReporteFinancieroDTO.class);
    }

    private MovimientoContable crearMovimiento(String codigoTransaccion, TipoMovimiento tipo,
                                               String cuentaContable, double valor, String descripcion) {
        return new MovimientoContable(
                codigoTransaccion + "-" + UUID.randomUUID().toString().substring(0, 8),
                LocalDateTime.now(),
                tipo,
                cuentaContable,
                valor,
                descripcion);
    }
}
