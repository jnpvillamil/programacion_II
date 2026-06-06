package co.uptc.edu.co.negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import co.uptc.edu.co.interfaces.IGestionCompra;
import co.uptc.edu.co.interfaces.IGestionProducto;
import co.uptc.edu.co.interfaces.IGestionReporte;
import co.uptc.edu.co.interfaces.IGestionVenta;
import co.uptc.edu.co.modelo.Compra;
import co.uptc.edu.co.modelo.DetalleVenta;
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.modelo.dto.DetalleUtilidadBrutaDTO;
import co.uptc.edu.co.modelo.dto.ResumenClienteDTO;
import co.uptc.edu.co.modelo.dto.ResumenContableDTO;
import co.uptc.edu.co.modelo.dto.ResumenFinancieroDiarioDTO;
import co.uptc.edu.co.modelo.dto.ResumenFormaPagoDTO;
import co.uptc.edu.co.modelo.dto.ResumenInventarioValorizadoDTO;
import co.uptc.edu.co.modelo.dto.ResumenProductoDTO;
import co.uptc.edu.co.modelo.dto.ResumenUtilidadBrutaDTO;
import co.uptc.edu.co.modelo.dto.ResumenVentasDTO;
import co.uptc.edu.co.modelo.enums.EstadoCompraEnum;
import co.uptc.edu.co.modelo.enums.EstadoVentaEnum;
import co.uptc.edu.co.modelo.enums.FormaPago;

public class GestionReporte {

    private final IGestionVenta gestionVenta;
    private final IGestionReporte gestionReporte;
    private final IGestionProducto gestionProducto;
    private final IGestionCompra gestionCompra;

    public GestionReporte(IGestionVenta gestionVenta, IGestionReporte gestionReporte,
            IGestionProducto gestionProducto, IGestionCompra gestionCompra) {

        if (gestionVenta == null) {
            throw new IllegalArgumentException("La gestionVenta no puede ser nula.");
        }
        if (gestionReporte == null) {
            throw new IllegalArgumentException("La gestionReporte no puede ser nula.");
        }
        if (gestionProducto == null) {
            throw new IllegalArgumentException("La gestionProducto no puede ser nula.");
        }
        if (gestionCompra == null) {
            throw new IllegalArgumentException("La gestionCompra no puede ser nula.");
        }

        this.gestionVenta = gestionVenta;
        this.gestionReporte = gestionReporte;
        this.gestionProducto = gestionProducto;
        this.gestionCompra = gestionCompra;
    }

    public String generarReporteProductosMasVendidos(LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        List<ResumenProductoDTO> resumen = obtenerResumenProductosMasVendidos(fechaInicio, fechaFin);
        String nombre = "productos_mas_vendidos_" + fechaInicio + "_" + fechaFin;
        gestionReporte.guardar(nombre, resumen);
        return nombre;
    }

    public List<ResumenProductoDTO> obtenerResumenProductosMasVendidos(LocalDate fechaInicio, LocalDate fechaFin)
            throws Exception {
        List<Venta> ventasActuales = gestionVenta.listar();
        return construirResumenProductos(ventasActuales, fechaInicio, fechaFin);
    }

    public String generarReporteProducto(String codigoProducto, LocalDate fechaInicio, LocalDate fechaFin)
            throws Exception {

        if (codigoProducto == null || codigoProducto.isBlank()) {
            throw new Exception("Debe seleccionar un codigo de producto valido para generar el reporte.");
        }

        List<Venta> ventasActuales = gestionVenta.listar();
        List<ResumenProductoDTO> resumenes = construirResumenProductos(ventasActuales, fechaInicio, fechaFin);

        ResumenProductoDTO encontrado = null;

        for (ResumenProductoDTO resumen : resumenes) {
            if (resumen.getCodigoProducto() != null && resumen.getCodigoProducto().equalsIgnoreCase(codigoProducto)) {
                encontrado = resumen;
                break;
            }
        }

        if (encontrado == null) {
            throw new Exception("No se encontro datos de ventas para el producto seleccionado dentro del rango.");
        }

        String nombre = "producto_" + codigoProducto + "_" + fechaInicio + "_" + fechaFin;
        gestionReporte.guardar(nombre, encontrado);
        return nombre;
    }

    public String generarReporteVentasDiarias(LocalDate fecha) throws Exception {
        ResumenVentasDTO resumen = obtenerTotalVentasDiarias(fecha);
        String nombre = "ventas_diarias_" + fecha;
        gestionReporte.guardar(nombre, resumen);
        return nombre;
    }

    public String generarReporteVentasMensuales(int mes, int anio) throws Exception {
        ResumenVentasDTO resumen = obtenerTotalVentasMensuales(mes, anio);
        String nombre = "ventas_mensuales_" + mes + "_" + anio;
        gestionReporte.guardar(nombre, resumen);
        return nombre;
    }

    public String generarReporteVentasAnuales(int anio) throws Exception {
        ResumenVentasDTO resumen = obtenerTotalVentasAnuales(anio);
        String nombre = "ventas_anuales_" + anio;
        gestionReporte.guardar(nombre, resumen);
        return nombre;
    }

    public String generarReporteUtilidadBruta(LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        ResumenUtilidadBrutaDTO resumen = obtenerUtilidadBruta(fechaInicio, fechaFin);
        String nombre = "utilidad_bruta_" + fechaInicio + "_" + fechaFin;
        gestionReporte.guardar(nombre, resumen);
        return nombre;
    }

    public String generarReporteVentasFormaPago(LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        List<ResumenFormaPagoDTO> resumen = obtenerVentasPorFormaPago(fechaInicio, fechaFin);
        String nombre = "ventas_forma_pago_" + fechaInicio + "_" + fechaFin;
        gestionReporte.guardar(nombre, resumen);
        return nombre;
    }

    public String generarReporteClientesMayorCompra(LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        List<ResumenClienteDTO> resumen = obtenerClientesMayorVolumenCompra(fechaInicio, fechaFin);
        String nombre = "clientes_mayor_compra_" + fechaInicio + "_" + fechaFin;
        gestionReporte.guardar(nombre, resumen);
        return nombre;
    }

    public String generarReporteInventarioValorizado() throws Exception {
        List<ResumenInventarioValorizadoDTO> resumen = obtenerInventarioValorizado();
        String nombre = "inventario_valorizado";
        gestionReporte.guardar(nombre, resumen);
        return nombre;
    }

    public String generarReporteResumenContable(LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        ResumenContableDTO resumen = obtenerResumenContable(fechaInicio, fechaFin);
        String nombre = "resumen_contable_" + fechaInicio + "_" + fechaFin;
        gestionReporte.guardar(nombre, resumen);
        return nombre;
    }

    public String generarReporteResumenFinancieroDiario(LocalDate fecha) throws Exception {
        ResumenFinancieroDiarioDTO resumen = obtenerResumenFinancieroDiario(fecha);
        String nombre = "resumen_financiero_diario_" + fecha;
        gestionReporte.guardar(nombre, resumen);
        return nombre;
    }

    public List<ResumenFormaPagoDTO> obtenerVentasPorFormaPago(LocalDate fechaInicio, LocalDate fechaFin)
            throws Exception {
        List<Venta> ventasActuales = gestionVenta.listar();
        Map<FormaPago, Integer> cantidadMap = new LinkedHashMap<>();
        Map<FormaPago, Double> totalMap = new LinkedHashMap<>();

        for (Venta venta : ventasActuales) {
            if (!debeIncluirVentaEnReporte(venta, fechaInicio, fechaFin) || venta.getFormaPago() == null) {
                continue;
            }

            FormaPago fp = venta.getFormaPago();
            cantidadMap.put(fp, cantidadMap.getOrDefault(fp, 0) + 1);
            totalMap.put(fp, totalMap.getOrDefault(fp, 0.0) + venta.getTotal());
        }

        List<ResumenFormaPagoDTO> resumenes = new ArrayList<>();

        for (FormaPago fp : cantidadMap.keySet()) {
            resumenes.add(new ResumenFormaPagoDTO(fp, cantidadMap.get(fp), totalMap.getOrDefault(fp, 0.0)));
        }

        resumenes.sort(Comparator.comparingDouble(ResumenFormaPagoDTO::getValorTotal).reversed());
        return resumenes;
    }

    public List<ResumenClienteDTO> obtenerClientesMayorVolumenCompra(LocalDate fechaInicio, LocalDate fechaFin)
            throws Exception {
        List<Venta> ventasActuales = gestionVenta.listar();
        Map<String, ResumenClienteDTO> resumenPorCliente = new LinkedHashMap<>();

        for (Venta venta : ventasActuales) {
            if (!debeIncluirVentaEnReporte(venta, fechaInicio, fechaFin)) {
                continue;
            }

            String codigoCliente = venta.getCodigoCliente().trim();
            String nombreCliente = venta.getCliente().trim();

            ResumenClienteDTO resumen = resumenPorCliente.computeIfAbsent(codigoCliente,
                    clave -> new ResumenClienteDTO(codigoCliente, nombreCliente));

            resumen.acumularCompra(venta.getTotal());
        }

        List<ResumenClienteDTO> resumenes = new ArrayList<>(resumenPorCliente.values());
        resumenes.sort(Comparator.comparingDouble(ResumenClienteDTO::getTotalComprado).reversed()
                .thenComparing(ResumenClienteDTO::getNombreCliente, String.CASE_INSENSITIVE_ORDER));

        return resumenes;
    }

    public List<ResumenInventarioValorizadoDTO> obtenerInventarioValorizado() throws Exception {
        List<Producto> productos = gestionProducto.listar();
        List<ResumenInventarioValorizadoDTO> resumenes = new ArrayList<>();

        for (Producto producto : productos) {
            double valorInventario = producto.getStockActual() * producto.getPrecioCompra();

            resumenes.add(new ResumenInventarioValorizadoDTO(producto.getCodigoProducto(), producto.getNombreProducto(),
                    producto.getCategoria(), producto.getStockActual(), producto.getPrecioCompra(), valorInventario));
        }

        resumenes.sort(Comparator.comparingDouble(ResumenInventarioValorizadoDTO::getValorInventario).reversed()
                .thenComparing(ResumenInventarioValorizadoDTO::getNombreProducto, String.CASE_INSENSITIVE_ORDER));

        return resumenes;
    }

    public ResumenContableDTO obtenerResumenContable(LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        validarRangoFechas(fechaInicio, fechaFin);

        double ingresos = calcularIngresosPeriodo(fechaInicio, fechaFin);
        double egresos = calcularEgresosPeriodo(fechaInicio, fechaFin);
        double utilidad = ingresos - egresos;

        return new ResumenContableDTO(ingresos, egresos, utilidad);
    }

    public ResumenFinancieroDiarioDTO obtenerResumenFinancieroDiario(LocalDate fecha) throws Exception {
        if (fecha == null) {
            throw new Exception("La fecha es obligatoria.");
        }

        ResumenVentasDTO ventas = obtenerTotalVentasDiarias(fecha);
        ResumenUtilidadBrutaDTO utilidad = obtenerUtilidadBruta(fecha, fecha);
        ResumenContableDTO contable = obtenerResumenContable(fecha, fecha);
        List<ResumenFormaPagoDTO> ventasPorFormaPago = obtenerVentasPorFormaPago(fecha, fecha);
        List<ResumenProductoDTO> productosMasVendidos = obtenerResumenProductosMasVendidos(fecha, fecha);

        double totalCompras = 0;
        double ivaDescontable = 0;

        for (Compra compra : gestionCompra.listar()) {
            if (!debeIncluirCompraEnReporte(compra, fecha, fecha)) {
                continue;
            }

            totalCompras += compra.getTotalCompra();
            ivaDescontable += compra.getImpuestos();
        }

        double ivaGenerado = 0;

        for (Venta venta : gestionVenta.listar()) {
            if (!debeIncluirVentaEnReporte(venta, fecha, fecha)) {
                continue;
            }

            ivaGenerado += venta.getImpuestos();
        }

        return new ResumenFinancieroDiarioDTO(fecha, ventas.getTotalVentas(), totalCompras, utilidad.getUtilidadBruta(),
                ventasPorFormaPago, productosMasVendidos, contable, ivaGenerado, ivaDescontable);
    }

    public ResumenVentasDTO obtenerTotalVentasDiarias(LocalDate fecha) throws Exception {
        if (fecha == null) {
            throw new Exception("La fecha es obligatoria.");
        }

        return construirResumenVentas(fecha.toString(), fecha, fecha);
    }

    public ResumenVentasDTO obtenerTotalVentasMensuales(int mes, int anio) throws Exception {
        if (mes < 1 || mes > 12) {
            throw new Exception("El mes debe estar entre 1 y 12.");
        }

        if (anio <= 0) {
            throw new Exception("El año debe ser valido.");
        }

        LocalDate fechaInicio = LocalDate.of(anio, mes, 1);
        LocalDate fechaFin = fechaInicio.withDayOfMonth(fechaInicio.lengthOfMonth());
        String periodo = String.format("%02d/%d", mes, anio);

        return construirResumenVentas(periodo, fechaInicio, fechaFin);
    }

    public ResumenVentasDTO obtenerTotalVentasAnuales(int anio) throws Exception {
        if (anio <= 0) {
            throw new Exception("El año debe ser valido.");
        }

        LocalDate fechaInicio = LocalDate.of(anio, 1, 1);
        LocalDate fechaFin = LocalDate.of(anio, 12, 31);

        return construirResumenVentas(String.valueOf(anio), fechaInicio, fechaFin);
    }

    public ResumenUtilidadBrutaDTO obtenerUtilidadBruta(LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        validarRangoFechas(fechaInicio, fechaFin);

        List<Venta> ventasActuales = gestionVenta.listar();
        Map<String, Integer> cantidadMap = new LinkedHashMap<>();
        Map<String, Double> ventasMap = new LinkedHashMap<>();
        Map<String, Double> costoMap = new LinkedHashMap<>();
        Map<String, String> nombreMap = new LinkedHashMap<>();

        double totalVentas = 0;
        double costoVentas = 0;
        int cantidadVentas = 0;
        int cantidadVendida = 0;

        for (Venta venta : ventasActuales) {
            if (!debeIncluirVentaEnReporte(venta, fechaInicio, fechaFin)) {
                continue;
            }

            totalVentas += venta.getSubTotal();
            costoVentas += calcularCostoVenta(venta);
            cantidadVentas++;
            cantidadVendida += calcularCantidadVendida(venta);

            if (venta.getDetalles() == null) {
                continue;
            }

            for (DetalleVenta detalle : venta.getDetalles()) {
                if (detalle == null || detalle.getProducto() == null) {
                    continue;
                }

                String codigoProducto = detalle.getProducto().getCodigoProducto();
                if (codigoProducto == null || codigoProducto.isBlank()) {
                    codigoProducto = "SIN_CODIGO";
                }

                String nombreProducto = detalle.getProducto().getNombreProducto();
                if (nombreProducto == null || nombreProducto.isBlank()) {
                    nombreProducto = "Producto sin nombre";
                }

                nombreMap.putIfAbsent(codigoProducto, nombreProducto);
                cantidadMap.put(codigoProducto, cantidadMap.getOrDefault(codigoProducto, 0) + detalle.getCantidad());
                ventasMap.put(codigoProducto, ventasMap.getOrDefault(codigoProducto, 0.0) + detalle.getSubtotal());
                costoMap.put(codigoProducto, costoMap.getOrDefault(codigoProducto, 0.0)
                        + detalle.getCantidad() * detalle.getProducto().getPrecioCompra());
            }
        }

        double utilidadBruta = totalVentas - costoVentas;

        return new ResumenUtilidadBrutaDTO(construirPeriodo(fechaInicio, fechaFin),
                construirDetallesUtilidad(cantidadMap, ventasMap, costoMap, nombreMap), totalVentas, costoVentas,
                utilidadBruta, cantidadVentas, cantidadVendida);
    }

    private ResumenVentasDTO construirResumenVentas(String periodo, LocalDate fechaInicio, LocalDate fechaFin)
            throws Exception {
        List<Venta> ventasActuales = gestionVenta.listar();
        List<Venta> ventasDelDia = new ArrayList<>();
        double subtotalVentas = 0;
        double totalVentas = 0;
        double impuestos = 0;
        int cantidadVentas = 0;

        for (Venta venta : ventasActuales) {
            if (!debeIncluirVentaEnReporte(venta, fechaInicio, fechaFin)) {
                continue;
            }

            ventasDelDia.add(venta);
            subtotalVentas += venta.getSubTotal();
            totalVentas += venta.getTotal();
            impuestos += venta.getImpuestos();
            cantidadVentas++;
        }

        return new ResumenVentasDTO(periodo, ventasDelDia, subtotalVentas, totalVentas, cantidadVentas, impuestos);
    }

    private List<ResumenProductoDTO> construirResumenProductos(List<Venta> ventasFuente, LocalDate fechaInicio,
            LocalDate fechaFin) {
        Map<String, Integer> cantidadMap = new LinkedHashMap<>();
        Map<String, Double> totalMap = new LinkedHashMap<>();
        Map<String, String> nombreMap = new LinkedHashMap<>();

        for (Venta venta : ventasFuente) {
            if (!debeIncluirVentaEnReporte(venta, fechaInicio, fechaFin) || venta.getDetalles() == null) {
                continue;
            }

            for (DetalleVenta detalle : venta.getDetalles()) {
                if (detalle == null || detalle.getProducto() == null) {
                    continue;
                }

                String codigoProducto = detalle.getProducto().getCodigoProducto();
                if (codigoProducto == null || codigoProducto.isBlank()) {
                    codigoProducto = "SIN_CODIGO";
                }

                String nombreProducto = detalle.getProducto().getNombreProducto();
                if (nombreProducto == null || nombreProducto.isBlank()) {
                    nombreProducto = "Producto sin nombre";
                }

                nombreMap.putIfAbsent(codigoProducto, nombreProducto);
                cantidadMap.put(codigoProducto, cantidadMap.getOrDefault(codigoProducto, 0) + detalle.getCantidad());
                totalMap.put(codigoProducto, totalMap.getOrDefault(codigoProducto, 0.0) + detalle.getSubtotal());
            }
        }

        List<ResumenProductoDTO> resumenes = new ArrayList<>();

        for (String codigo : cantidadMap.keySet()) {
            resumenes.add(new ResumenProductoDTO(codigo, nombreMap.getOrDefault(codigo, ""),
                    cantidadMap.getOrDefault(codigo, 0), totalMap.getOrDefault(codigo, 0.0)));
        }

        resumenes.sort(Comparator.comparingInt(ResumenProductoDTO::getCantidadVendida).reversed()
                .thenComparing(ResumenProductoDTO::getNombreProducto, String.CASE_INSENSITIVE_ORDER));

        return resumenes;
    }

    private boolean debeIncluirVentaEnReporte(Venta venta, LocalDate fechaInicio, LocalDate fechaFin) {
        if (venta == null || venta.getEstado() == EstadoVentaEnum.ANULADA || venta.getFechaHora() == null) {
            return false;
        }

        LocalDate fechaVenta = venta.getFechaHora().toLocalDate();

        if (fechaInicio != null && fechaVenta.isBefore(fechaInicio)) {
            return false;
        }

        if (fechaFin != null && fechaVenta.isAfter(fechaFin)) {
            return false;
        }

        return true;
    }

    private boolean debeIncluirCompraEnReporte(Compra compra, LocalDate fechaInicio, LocalDate fechaFin) {
        if (compra == null || compra.getEstado() == EstadoCompraEnum.ANULADA || compra.getFecha() == null) {
            return false;
        }

        LocalDate fechaCompra = compra.getFecha();

        if (fechaInicio != null && fechaCompra.isBefore(fechaInicio)) {
            return false;
        }

        if (fechaFin != null && fechaCompra.isAfter(fechaFin)) {
            return false;
        }

        return true;
    }

    private double calcularIngresosPeriodo(LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        double ingresos = 0;

        for (Venta venta : gestionVenta.listar()) {
            if (debeIncluirVentaEnReporte(venta, fechaInicio, fechaFin)) {
                ingresos += venta.getTotal();
            }
        }

        return ingresos;
    }

    private double calcularEgresosPeriodo(LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        double egresos = 0;

        for (Compra compra : gestionCompra.listar()) {
            if (debeIncluirCompraEnReporte(compra, fechaInicio, fechaFin)) {
                egresos += compra.getTotalCompra();
            }
        }

        return egresos;
    }

    private double calcularCostoVenta(Venta venta) {
        double costoVenta = 0;

        if (venta.getDetalles() == null) {
            return costoVenta;
        }

        for (DetalleVenta detalle : venta.getDetalles()) {
            if (detalle == null || detalle.getProducto() == null) {
                continue;
            }

            costoVenta += detalle.getCantidad() * detalle.getProducto().getPrecioCompra();
        }

        return costoVenta;
    }

    private int calcularCantidadVendida(Venta venta) {
        int cantidadVendida = 0;

        if (venta.getDetalles() == null) {
            return cantidadVendida;
        }

        for (DetalleVenta detalle : venta.getDetalles()) {
            if (detalle != null) {
                cantidadVendida += detalle.getCantidad();
            }
        }

        return cantidadVendida;
    }

    private List<DetalleUtilidadBrutaDTO> construirDetallesUtilidad(Map<String, Integer> cantidadMap,
            Map<String, Double> ventasMap, Map<String, Double> costoMap, Map<String, String> nombreMap) {
        List<DetalleUtilidadBrutaDTO> detalles = new ArrayList<>();

        for (String codigo : cantidadMap.keySet()) {
            int cantidad = cantidadMap.getOrDefault(codigo, 0);
            double ventas = ventasMap.getOrDefault(codigo, 0.0);
            double costo = costoMap.getOrDefault(codigo, 0.0);
            String nombre = nombreMap.getOrDefault(codigo, "");

            detalles.add(new DetalleUtilidadBrutaDTO(codigo, nombre, cantidad, ventas, costo, ventas - costo));
        }

        detalles.sort(Comparator.comparingDouble(DetalleUtilidadBrutaDTO::getUtilidad).reversed()
                .thenComparing(DetalleUtilidadBrutaDTO::getNombreProducto, String.CASE_INSENSITIVE_ORDER));

        return detalles;
    }

    private void validarRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        if (fechaInicio != null && fechaFin != null && fechaInicio.isAfter(fechaFin)) {
            throw new Exception("La fecha de inicio no puede ser posterior a la fecha final.");
        }
    }

    private String construirPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null && fechaFin == null) {
            return "Todos";
        }

        if (fechaInicio != null && fechaFin != null) {
            return fechaInicio + " a " + fechaFin;
        }

        if (fechaInicio != null) {
            return "Desde " + fechaInicio;
        }

        return "Hasta " + fechaFin;
    }
}