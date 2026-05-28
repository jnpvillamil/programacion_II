package co.edu.uptc.negocio;

import co.edu.uptc.dto.ResumenDiarioJSONDTO;
import co.edu.uptc.enums.FormaPago;
import co.edu.uptc.enums.TipoMovimiento;
import co.edu.uptc.interfaces.IContabilizable;
import co.edu.uptc.interfaces.IRepositorioContable;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.MovimientoContable;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.persistencia.PersistenciaContable;
import co.edu.uptc.utilidades.ManejadorFechas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GestionContable implements IContabilizable {

    private final IRepositorioContable repoContable;

    public GestionContable(IRepositorioContable repoContable) {
        this.repoContable = repoContable;
    }

    public GestionContable() {
        this(new PersistenciaContable());
    }

    @Override
    public void registrarMovimiento(MovimientoContable movimiento) {
        if (movimiento != null) {
            repoContable.guardar(movimiento);
        }
    }

    @Override
    public double obtenerSaldo(String cuentaContable) {
        if (cuentaContable == null || cuentaContable.isBlank()) {
            return 0.0;
        }
        Map<String, Double> resumen = generarResumenContablePorPeriodo(
                java.time.LocalDateTime.MIN,
                java.time.LocalDateTime.now());
        return resumen.getOrDefault(cuentaContable.trim(), 0.0);
    }

    public void registrarContabilidadCompra(Compra compra) {
        MovimientoContable debitoInv = new MovimientoContable(
                compra.getFacturaProveedor(),
                compra.getFecha(),
                TipoMovimiento.INGRESO,
                "Inventario",
                compra.getTotalCompra() - compra.getIva(),
                "Débito por ingreso de mercancía");
        repoContable.guardar(debitoInv);

        if (compra.getIva() > 0) {
            MovimientoContable debitoIva = new MovimientoContable(
                    compra.getFacturaProveedor(),
                    compra.getFecha(),
                    TipoMovimiento.INGRESO,
                    "IVA descontable",
                    compra.getIva(),
                    "Débito por IVA descontable en compra");
            repoContable.guardar(debitoIva);
        }

        MovimientoContable creditoProv = new MovimientoContable(
                compra.getFacturaProveedor(),
                compra.getFecha(),
                TipoMovimiento.EGRESO,
                "Proveedores",
                compra.getTotalCompra(),
                "Crédito por cuenta por pagar a proveedor");
        repoContable.guardar(creditoProv);
    }

    public void registrarContabilidadVenta(Venta venta) {
        String cuentaCobro = resolverCuentaCobro(venta.getFormaPago());

        MovimientoContable debitoCobro = new MovimientoContable(
                venta.getNumeroFactura(),
                venta.getFechaHora(),
                TipoMovimiento.INGRESO,
                cuentaCobro,
                venta.getTotalVenta(),
                "Débito por cobro de venta");
        repoContable.guardar(debitoCobro);

        MovimientoContable creditoIngresos = new MovimientoContable(
                venta.getNumeroFactura(),
                venta.getFechaHora(),
                TipoMovimiento.EGRESO,
                "Ingresos por ventas",
                venta.getSubtotal(),
                "Crédito por ingreso operacional");
        repoContable.guardar(creditoIngresos);

        if (venta.getIvaAplicado() > 0) {
            MovimientoContable creditoIva = new MovimientoContable(
                    venta.getNumeroFactura(),
                    venta.getFechaHora(),
                    TipoMovimiento.EGRESO,
                    "IVA generado",
                    venta.getIvaAplicado(),
                    "Crédito por IVA generado en venta");
            repoContable.guardar(creditoIva);
        }
    }

    public void registrarContabilidadAnulacionVenta(Venta venta) {
        String cuentaCobro = resolverCuentaCobro(venta.getFormaPago());
        String referencia = venta.getNumeroFactura();

        MovimientoContable egresoCobro = new MovimientoContable(
                referencia,
                ManejadorFechas.obtenerFechaActual(),
                TipoMovimiento.EGRESO,
                cuentaCobro,
                venta.getTotalVenta(),
                "Reversión de cobro por anulación de venta");
        repoContable.guardar(egresoCobro);

        MovimientoContable ingresoVentas = new MovimientoContable(
                referencia,
                ManejadorFechas.obtenerFechaActual(),
                TipoMovimiento.INGRESO,
                "Ingresos por ventas",
                venta.getSubtotal(),
                "Reversión de ingreso operacional por anulación");
        repoContable.guardar(ingresoVentas);

        if (venta.getIvaAplicado() > 0) {
            MovimientoContable ingresoIva = new MovimientoContable(
                    referencia,
                    ManejadorFechas.obtenerFechaActual(),
                    TipoMovimiento.INGRESO,
                    "IVA generado",
                    venta.getIvaAplicado(),
                    "Reversión de IVA por anulación de venta");
            repoContable.guardar(ingresoIva);
        }
    }

    public List<MovimientoContable> consultarMovimientoContable(String codigoTransaccion) {
        if (codigoTransaccion == null || codigoTransaccion.trim().isEmpty()) {
            System.err.println("El código de transacción para buscar no puede estar vacío.");
            return new ArrayList<>();
        }

        return repoContable.consultarPorTransaccion(codigoTransaccion);
    }

    public Map<String, Double> generarResumenContablePorPeriodo(java.time.LocalDateTime inicio, java.time.LocalDateTime fin) {
        if (inicio == null || fin == null) {
            System.err.println("Las fechas para el resumen contable no pueden estar vacías.");
            return new java.util.HashMap<>();
        }

        if (inicio.isAfter(fin)) {
            System.err.println("La fecha de inicio no puede ser posterior a la fecha de fin.");
            return new java.util.HashMap<>();
        }

        return repoContable.resumenContablePorPeriodo(inicio, fin);
    }

    public ResumenDiarioJSONDTO.ResumenContable obtenerResumenContableDiario(java.time.LocalDate fecha) {
        if (fecha == null) {
            return new ResumenDiarioJSONDTO.ResumenContable();
        }
        java.time.LocalDateTime inicio = fecha.atStartOfDay();
        java.time.LocalDateTime fin = fecha.atTime(23, 59, 59);
        return repoContable.resumenFinancieroPorPeriodo(inicio, fin);
    }

    private String resolverCuentaCobro(FormaPago formaPago) {
        if (formaPago == null) {
            return "Caja";
        }

        return switch (formaPago) {
            case EFECTIVO -> "Caja";
            case TRANSFERENCIA, TARJETA -> "Bancos";
            case CREDITO -> "Cuentas por cobrar";
        };
    }
}
