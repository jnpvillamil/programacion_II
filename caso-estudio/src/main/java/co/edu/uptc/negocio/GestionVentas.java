package co.edu.uptc.negocio;

import co.edu.uptc.dto.CarritoItemDTO;
import co.edu.uptc.dto.ResultadoOperacion;
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
import co.edu.uptc.utilidades.ValidadorEntradas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import co.edu.uptc.modelo.Producto;

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

        if (!repositorioVenta.guardarVenta(venta)) {
            revertirStockVenta(venta);
            return false;
        }
        gestionContable.registrarContabilidadVenta(venta);
        LogSistema.ventaRegistrada(venta.getNumeroFactura(), venta.getTotalVenta());
        return true;
    }

    private void revertirStockVenta(Venta venta) {
        for (DetalleVenta detalle : venta.getProductosVendidos()) {
            gestionInventario.registrarMovimientoInventario(
                    detalle.getProducto().getCodigoProducto(),
                    detalle.getCantidad(),
                    "ENTRADA");
        }
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

    public ResultadoOperacion validarAgregarAlCarrito(String codigo, String cantidadStr,
                                                      List<CarritoItemDTO> carritoActual) {
        if (ValidadorEntradas.esVacio(codigo) || !ValidadorEntradas.esNumero(cantidadStr)) {
            return ResultadoOperacion.error("Código vacío o cantidad no numérica.");
        }
        int cantidad = (int) Double.parseDouble(cantidadStr.trim());
        if (cantidad <= 0) {
            return ResultadoOperacion.error("La cantidad debe ser mayor a 0.");
        }
        Producto producto = gestionInventario.buscarProducto(codigo.trim());
        if (producto == null || !producto.isActivo()) {
            return ResultadoOperacion.error("Producto no encontrado o inactivo.");
        }
        int cantidadEnCarrito = carritoActual.stream()
                .filter(item -> item.getProducto().getCodigoProducto().equals(codigo.trim()))
                .mapToInt(CarritoItemDTO::getCantidad)
                .sum();
        if ((cantidadEnCarrito + cantidad) > producto.getStockActual()) {
            return ResultadoOperacion.error("Stock insuficiente. Stock actual: " + producto.getStockActual());
        }
        double subtotal = producto.getPrecioVenta() * cantidad;
        CarritoItemDTO item = new CarritoItemDTO(producto, cantidad, producto.getPrecioVenta(), subtotal);
        return ResultadoOperacion.exito("Producto agregado al carrito.", item);
    }

    public ResultadoOperacion validarFinalizarVenta(Cliente cliente, List<CarritoItemDTO> carrito) {
        if (cliente == null) {
            return ResultadoOperacion.error("Debe seleccionar un cliente antes de finalizar.");
        }
        if (carrito == null || carrito.isEmpty()) {
            return ResultadoOperacion.error("El carrito está vacío.");
        }
        return ResultadoOperacion.exito("Validación correcta.");
    }

    public ResultadoOperacion procesarVentaConResultado(VentaDTO dto, Cliente cliente) {
        ResultadoOperacion validacion = validarFinalizarVenta(cliente, dto.getItems());
        if (!validacion.isExito()) {
            return validacion;
        }
        Venta venta = procesarVentaDesdeDto(dto, cliente);
        if (venta != null) {
            return ResultadoOperacion.exito(
                    "Venta registrada exitosamente.\n" + generarFactura(venta), venta);
        }
        return ResultadoOperacion.error(
                "No se pudo registrar la venta. Revise conexion a BD, tablas ventas/detalles_ventas y cliente registrado.");
    }

    public ResultadoOperacion validarIdentificacionConsulta(String identificacion) {
        if (ValidadorEntradas.esVacio(identificacion)) {
            return ResultadoOperacion.error("Ingrese la identificación del cliente.");
        }
        return ResultadoOperacion.exito("Validación correcta.");
    }

    public ResultadoOperacion validarFechaConsulta(String fecha) {
        if (ValidadorEntradas.esVacio(fecha)) {
            return ResultadoOperacion.error("Ingrese la fecha en formato dd/MM/yyyy.");
        }
        return ResultadoOperacion.exito("Validación correcta.");
    }

    public ResultadoOperacion validarNumeroFactura(String numeroFactura) {
        if (ValidadorEntradas.esVacio(numeroFactura)) {
            return ResultadoOperacion.error("Ingrese o seleccione un número de factura.");
        }
        return ResultadoOperacion.exito("Validación correcta.");
    }

    public ResultadoOperacion anularVentaConResultado(String numeroFactura) {
        ResultadoOperacion validacion = validarNumeroFactura(numeroFactura);
        if (!validacion.isExito()) {
            return validacion;
        }
        if (anularVenta(numeroFactura.trim())) {
            return ResultadoOperacion.exito("Venta anulada correctamente.");
        }
        return ResultadoOperacion.error("No se encontró la factura o ya estaba anulada.");
    }

    public ResultadoOperacion reimprimirComprobanteConValidacion(String numeroFactura) {
        ResultadoOperacion validacion = validarNumeroFactura(numeroFactura);
        if (!validacion.isExito()) {
            return validacion;
        }
        return ResultadoOperacion.exito(reimprimirComprobante(numeroFactura.trim()));
    }
}
