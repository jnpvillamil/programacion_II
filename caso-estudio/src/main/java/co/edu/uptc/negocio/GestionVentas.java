package co.edu.uptc.negocio;

import co.edu.uptc.dto.VentaDTO;
import co.edu.uptc.enums.FormaPago;
import co.edu.uptc.interfaces.Calculable;
import co.edu.uptc.interfaces.IContabilizable;
import co.edu.uptc.interfaces.IRepositorioVenta;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.modelo.MovimientoContable;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.persistencia.PersistenciaContable;
import co.edu.uptc.persistencia.PersistenciaVentas;
import co.edu.uptc.utilidades.LogSistema;
import co.edu.uptc.utilidades.ManejadorFechas;
import co.edu.uptc.utilidades.MapeadorDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GestionVentas implements IContabilizable {

    private final IRepositorioVenta repositorioVenta;
    private final GestionInventario gestionInventario;
    private final GestionContable gestionContable;

    public GestionVentas(IRepositorioVenta repositorioVenta, GestionInventario gestionInventario,
                         GestionContable gestionContable) {
        this.repositorioVenta = repositorioVenta;
        this.gestionInventario = gestionInventario;
        this.gestionContable = gestionContable;
    }

    public GestionVentas(IRepositorioVenta repositorioVenta, GestionInventario gestionInventario) {
        this(repositorioVenta, gestionInventario, new GestionContable(new PersistenciaContable()));
    }

    public GestionVentas(GestionInventario gestionInventario) {
        this(new PersistenciaVentas(), gestionInventario);
    }

    @Override
    public void registrarMovimiento(MovimientoContable movimiento) {
        gestionContable.registrarMovimiento(movimiento);
    }

    @Override
    public double obtenerSaldo(String cuentaContable) {
        return gestionContable.obtenerSaldo(cuentaContable);
    }

    public Venta procesarVentaDesdeDto(VentaDTO dto, Cliente cliente) {
        Venta venta = MapeadorDTO.aVenta(dto, cliente);
        if (procesarVenta(venta)) {
            return venta;
        }
        return null;
    }

    public boolean procesarVenta(Venta venta) {
        if (venta.getProductosVendidos() == null || venta.getProductosVendidos().isEmpty()) {
            return false;
        }
        if (venta.getCliente() == null) {
            return false;
        }

        for (DetalleVenta detalle : venta.getProductosVendidos()) {
            double subtotalItem = detalle.getCantidad() * detalle.getPrecioUnitario();
            detalle.setSubtotal(subtotalItem);
        }

        venta.setSubtotal(venta.calcularSubtotal());
        aplicarTotalesCalculables(venta);
        venta.setFechaHora(ManejadorFechas.obtenerFechaActual());

        for (DetalleVenta detalle : venta.getProductosVendidos()) {
            boolean descontado = gestionInventario.descontarStock(
                    detalle.getProducto().getCodigoProducto(),
                    detalle.getCantidad());
            if (!descontado) {
                return false;
            }
        }

        repositorioVenta.guardarVenta(venta);
        gestionContable.registrarContabilidadVenta(venta);
        LogSistema.ventaRegistrada(venta.getNumeroFactura(), venta.getTotalVenta());
        return true;
    }

    public String generarFactura(Venta venta) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== FACTURA =====\n");
        sb.append("Factura: ").append(venta.getNumeroFactura()).append("\n");
        sb.append("Cliente: ").append(venta.getCliente().getNombre()).append("\n");
        sb.append("Total: ").append(venta.getTotalVenta()).append("\n");
        sb.append("Pago: ").append(venta.getFormaPago() != null ? venta.getFormaPago().name() : "").append("\n");
        return sb.toString();
    }

    public String reimprimirComprobante(String numeroFactura) {
        Venta venta = repositorioVenta.buscarVentaPorFactura(numeroFactura);
        if (venta == null) {
            return "Venta no encontrada";
        }

        return "===== COMPROBANTE =====\n"
                + "Factura: " + venta.getNumeroFactura() + "\n"
                + "Subtotal: " + venta.getSubtotal() + "\n"
                + "IVA: " + venta.getIvaAplicado() + "\n"
                + "Total: " + venta.getTotalVenta() + "\n"
                + "Forma Pago: " + (venta.getFormaPago() != null ? venta.getFormaPago().name() : "");
    }

    public List<Venta> consultarHistorialCliente(String identificacion) {
        return repositorioVenta.consultarHistorialCliente(identificacion);
    }

    public List<Venta> consultarVentasPorFecha(String fecha) {
        return repositorioVenta.consultarVentasPorFecha(fecha);
    }

    public double calcularTotalVentasPorFecha(LocalDate fecha) {
        if (fecha == null) {
            return 0.0;
        }
        String fechaConsulta = fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return consultarVentasPorFecha(fechaConsulta).stream()
                .mapToDouble(Venta::getTotalVenta)
                .sum();
    }

    public Map<FormaPago, Double> agruparVentasPorFormaPago(LocalDate fecha) {
        Map<FormaPago, Double> agrupado = new LinkedHashMap<>();
        if (fecha == null) {
            return agrupado;
        }
        String fechaConsulta = fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        for (Venta venta : consultarVentasPorFecha(fechaConsulta)) {
            FormaPago formaPago = venta.getFormaPago() != null ? venta.getFormaPago() : FormaPago.EFECTIVO;
            agrupado.merge(formaPago, venta.getTotalVenta(), Double::sum);
        }
        return agrupado;
    }

    public boolean anularVenta(String numeroFactura) {
        Venta venta = repositorioVenta.buscarVentaPorFactura(numeroFactura);
        if (venta == null || venta.getProductosVendidos() == null || venta.getProductosVendidos().isEmpty()) {
            return false;
        }

        boolean anulada = repositorioVenta.anularVenta(numeroFactura);
        if (!anulada) {
            return false;
        }

        for (DetalleVenta detalle : venta.getProductosVendidos()) {
            gestionInventario.registrarMovimientoInventario(
                    detalle.getProducto().getCodigoProducto(),
                    detalle.getCantidad(),
                    "ENTRADA");
        }

        gestionContable.registrarContabilidadAnulacionVenta(venta);
        LogSistema.ventaAnulada(numeroFactura);
        return true;
    }

    public String generarNumeroFactura() {
        return "FAC-" + System.currentTimeMillis();
    }

    private void aplicarTotalesCalculables(Venta venta) {
        Calculable calculable = venta;
        venta.setIvaAplicado(calculable.calcularIVA());
        venta.setTotalVenta(calculable.calcularTotal());
    }
}
