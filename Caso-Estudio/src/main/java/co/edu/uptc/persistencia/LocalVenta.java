package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.GestionVenta;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.negocio.ReporteVentasDTO;
import co.edu.uptc.negocio.ReporteDiarioJSONDTO;
import co.edu.uptc.config.TiendaConfig;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class LocalVenta implements GestionVenta {
    
    private Map<String, Venta> ventas;
    
    public LocalVenta() {
        ventas = new HashMap<>();
       
    }
    
    @Override
    public void crearVenta(Venta venta) {
        ventas.put(venta.getNumeroFactura(), venta);
        
    }
    
    @Override
    public Venta buscarVenta(String numeroFactura) {
        return ventas.get(numeroFactura);
    }
    
    @Override
    public List<Venta> listarVentas() {
        return new ArrayList<>(ventas.values());
    }
    
    @Override
    public void anularVenta(String numeroFactura) {
        Venta venta = ventas.get(numeroFactura);
        if(venta != null && "Activa".equals(venta.getEstado())) {
            venta.anular();
        }
    }
    
    @Override
    public List<Venta> listarVentasPorFecha(LocalDate fecha) {
        return ventas.values().stream()
            .filter(v -> v.getFechaHora().toLocalDate().equals(fecha))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Venta> listarVentasPorCliente(String codigoCliente) {
        return ventas.values().stream()
            .filter(v -> v.getCliente() != null && v.getCliente().getCodigo().equals(codigoCliente))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Venta> listarVentasPorFormaPago(String formaPago) {
        return ventas.values().stream()
            .filter(v -> v.getFormaPago().equals(formaPago))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Venta> listarVentasPorPeriodo(LocalDate inicio, LocalDate fin) {
        return ventas.values().stream()
            .filter(v -> {
                LocalDate fechaVenta = v.getFechaHora().toLocalDate();
                return (fechaVenta.isEqual(inicio) || fechaVenta.isAfter(inicio)) &&
                       (fechaVenta.isEqual(fin) || fechaVenta.isBefore(fin));
            })
            .collect(Collectors.toList());
    }
    
    @Override
    public double calcularTotalVentasPeriodo(LocalDate inicio, LocalDate fin) {
        return listarVentasPorPeriodo(inicio, fin).stream()
            .mapToDouble(Venta::getTotal)
            .sum();
    }
    
    @Override
    public int contarVentasPorFormaPago(String formaPago) {
        return (int) ventas.values().stream()
            .filter(v -> v.getFormaPago().equals(formaPago))
            .count();
    }
    @Override
    public ReporteVentasDTO generarReporteVentasDiarias(LocalDate fecha) {
        List<Venta> ventasDelDia = listarVentasPorFecha(fecha);
        ReporteVentasDTO reporte = new ReporteVentasDTO();
        reporte.setFecha(fecha);
        
        double totalVentas = 0;
        double totalIVA = 0;
        Map<String, Double> ventasPorFormaPago = new HashMap<>();
        Map<String, Integer> productosVendidos = new HashMap<>();
        Map<String, Double> comprasPorCliente = new HashMap<>();
        
        for(Venta v : ventasDelDia) {
            if("Activa".equals(v.getEstado())) {
                totalVentas += v.getTotal();
                totalIVA += v.getIva();
                
                
                ventasPorFormaPago.merge(v.getFormaPago(), v.getTotal(), Double::sum);
                
                
                comprasPorCliente.merge(v.getCliente().getNombre(), v.getTotal(), Double::sum);
                
                
                for(DetalleVenta d : v.getDetalles()) {
                    productosVendidos.merge(d.getProducto().getNombre(), 
                        d.getCantidad(), Integer::sum);
                }
            }
        }
        
        reporte.setTotalVentas(totalVentas);
        reporte.setTotalIVA(totalIVA);
        reporte.setNumeroFacturas(ventasDelDia.size());
        reporte.setVentasPorFormaPago(ventasPorFormaPago);
        
        
        List<Object[]> topProductos = productosVendidos.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(10)
            .map(e -> new Object[]{e.getKey(), e.getValue()})
            .collect(Collectors.toList());
        reporte.setProductosMasVendidos(topProductos);
        
        
        List<Object[]> topClientes = comprasPorCliente.entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .limit(10)
            .map(e -> new Object[]{e.getKey(), e.getValue()})
            .collect(Collectors.toList());
        reporte.setClientesTop(topClientes);
        
        return reporte;
    }
    
    @Override
    public ReporteVentasDTO generarReporteVentasMensuales(int anio, int mes) {
        YearMonth yearMonth = YearMonth.of(anio, mes);
        LocalDate inicio = yearMonth.atDay(1);
        LocalDate fin = yearMonth.atEndOfMonth();
        
        List<Venta> ventasPeriodo = listarVentasPorPeriodo(inicio, fin);
        ReporteVentasDTO reporte = new ReporteVentasDTO();
        reporte.setFecha(inicio);
        
        double totalVentas = 0;
        double totalIVA = 0;
        Map<String, Double> ventasPorFormaPago = new HashMap<>();
        Map<String, Integer> productosVendidos = new HashMap<>();
        Map<String, Double> comprasPorCliente = new HashMap<>();
        
        for(Venta v : ventasPeriodo) {
            if("Activa".equals(v.getEstado())) {
                totalVentas += v.getTotal();
                totalIVA += v.getIva();
                ventasPorFormaPago.merge(v.getFormaPago(), v.getTotal(), Double::sum);
                comprasPorCliente.merge(v.getCliente().getNombre(), v.getTotal(), Double::sum);
                
                for(DetalleVenta d : v.getDetalles()) {
                    productosVendidos.merge(d.getProducto().getNombre(), 
                        d.getCantidad(), Integer::sum);
                }
            }
        }
        
        reporte.setTotalVentas(totalVentas);
        reporte.setTotalIVA(totalIVA);
        reporte.setNumeroFacturas(ventasPeriodo.size());
        reporte.setVentasPorFormaPago(ventasPorFormaPago);
        
        List<Object[]> topProductos = productosVendidos.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(10)
            .map(e -> new Object[]{e.getKey(), e.getValue()})
            .collect(Collectors.toList());
        reporte.setProductosMasVendidos(topProductos);
        
        List<Object[]> topClientes = comprasPorCliente.entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .limit(10)
            .map(e -> new Object[]{e.getKey(), e.getValue()})
            .collect(Collectors.toList());
        reporte.setClientesTop(topClientes);
        
        return reporte;
    }
    
    @Override
    public ReporteVentasDTO generarReporteVentasAnuales(int anio) {
        LocalDate inicio = LocalDate.of(anio, 1, 1);
        LocalDate fin = LocalDate.of(anio, 12, 31);
        
        List<Venta> ventasPeriodo = listarVentasPorPeriodo(inicio, fin);
        ReporteVentasDTO reporte = new ReporteVentasDTO();
        reporte.setFecha(inicio);
        
        double totalVentas = 0;
        double totalIVA = 0;
        Map<String, Double> ventasPorFormaPago = new HashMap<>();
        Map<String, Integer> productosVendidos = new HashMap<>();
        
        for(Venta v : ventasPeriodo) {
            if("Activa".equals(v.getEstado())) {
                totalVentas += v.getTotal();
                totalIVA += v.getIva();
                ventasPorFormaPago.merge(v.getFormaPago(), v.getTotal(), Double::sum);
                
                for(DetalleVenta d : v.getDetalles()) {
                    productosVendidos.merge(d.getProducto().getNombre(), 
                        d.getCantidad(), Integer::sum);
                }
            }
        }
        
        reporte.setTotalVentas(totalVentas);
        reporte.setTotalIVA(totalIVA);
        reporte.setNumeroFacturas(ventasPeriodo.size());
        reporte.setVentasPorFormaPago(ventasPorFormaPago);
        
        List<Object[]> topProductos = productosVendidos.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(10)
            .map(e -> new Object[]{e.getKey(), e.getValue()})
            .collect(Collectors.toList());
        reporte.setProductosMasVendidos(topProductos);
        
        return reporte;
    }
    
    @Override
    public List<Object[]> obtenerProductosMasVendidos(int top, LocalDate inicio, LocalDate fin) {
        Map<String, Integer> productosVendidos = new HashMap<>();
        
        for(Venta v : listarVentasPorPeriodo(inicio, fin)) {
            if("Activa".equals(v.getEstado())) {
                for(DetalleVenta d : v.getDetalles()) {
                    productosVendidos.merge(d.getProducto().getCodigo(), 
                        d.getCantidad(), Integer::sum);
                }
            }
        }
        
        return productosVendidos.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(top)
            .map(entry -> {
                Producto p = TiendaConfig.getInstancia().getGestionProducto().buscar(entry.getKey());
                return new Object[]{p.getCodigo(), p.getNombre(), entry.getValue(), 
                    p.getPrecioVenta(), entry.getValue() * p.getPrecioVenta()};
            })
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Object[]> obtenerClientesTopCompradores(int top, LocalDate inicio, LocalDate fin) {
        Map<String, Double> comprasPorCliente = new HashMap<>();
        
        for(Venta v : listarVentasPorPeriodo(inicio, fin)) {
            if("Activa".equals(v.getEstado())) {
                comprasPorCliente.merge(v.getCliente().getCodigo(), v.getTotal(), Double::sum);
            }
        }
        
        return comprasPorCliente.entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .limit(top)
            .map(entry -> {
                var cliente = TiendaConfig.getInstancia().getGestionCliente().buscar(entry.getKey());
                return new Object[]{cliente.getCodigo(), cliente.getNombre(), 
                    cliente.getTipoCliente(), entry.getValue()};
            })
            .collect(Collectors.toList());
    }
    
    @Override
    public Map<String, Double> obtenerVentasPorFormaPago(LocalDate inicio, LocalDate fin) {
        Map<String, Double> ventasPorFormaPago = new HashMap<>();
        
        for(Venta v : listarVentasPorPeriodo(inicio, fin)) {
            if("Activa".equals(v.getEstado())) {
                ventasPorFormaPago.merge(v.getFormaPago(), v.getTotal(), Double::sum);
            }
        }
        
        return ventasPorFormaPago;
    }
    
    @Override
    public double obtenerValorInventarioTotal() {
        double valorTotal = 0;
        for(Producto p : TiendaConfig.getInstancia().getGestionProducto().listarActivos()) {
            valorTotal += p.getStockActual() * p.getPrecioCompra();
        }
        return valorTotal;
    }
    
    @Override
    public List<Object[]> obtenerInventarioValorizado() {
        List<Object[]> inventario = new ArrayList<>();
        for(Producto p : TiendaConfig.getInstancia().getGestionProducto().listarActivos()) {
            double valorTotal = p.getStockActual() * p.getPrecioCompra();
            Object[] fila = {
                p.getCodigo(),
                p.getNombre(),
                p.getStockActual(),
                p.getPrecioCompra(),
                valorTotal
            };
            inventario.add(fila);
        }
        return inventario;
    }
    
    @Override
    public double calcularUtilidadBruta(LocalDate inicio, LocalDate fin) {
        double totalVentas = 0;
        double costoVentas = 0;
        
        for(Venta v : listarVentasPorPeriodo(inicio, fin)) {
            if("Activa".equals(v.getEstado())) {
                totalVentas += v.getTotal();
                
                for(DetalleVenta d : v.getDetalles()) {
                    costoVentas += d.getCantidad() * d.getProducto().getPrecioCompra();
                }
            }
        }
        
        return totalVentas - costoVentas;
    }

 
@Override
public ReporteDiarioJSONDTO generarDatosJSONDiario(LocalDate fecha) {
    
    List<Venta> ventasDelDia = listarVentasPorFecha(fecha);
    List<Compra> comprasDelDia = TiendaConfig.getInstancia()
        .getGestionCompra().listarComprasPorFecha(fecha);
    
  
    double totalVentas = 0;
    double totalCompras = 0;
    Map<String, Double> ventasPorFormaPago = new HashMap<>();
    Map<String, Integer> productosVendidos = new HashMap<>();
    
   
    for(Venta v : ventasDelDia) {
        if("Activa".equals(v.getEstado())) {
            totalVentas += v.getTotal();
            ventasPorFormaPago.merge(v.getFormaPago(), v.getTotal(), Double::sum);
            
            for(DetalleVenta d : v.getDetalles()) {
                productosVendidos.merge(d.getProducto().getNombre(), 
                    d.getCantidad(), Integer::sum);
            }
        }
    }
    
  
    for(Compra c : comprasDelDia) {
        if("Activa".equals(c.getEstado())) {
            totalCompras += c.getTotal();
        }
    }
    
    
    double costoVentas = 0;
    for(Venta v : ventasDelDia) {
        if("Activa".equals(v.getEstado())) {
            for(DetalleVenta d : v.getDetalles()) {
                costoVentas += d.getCantidad() * d.getProducto().getPrecioCompra();
            }
        }
    }
    double utilidadBruta = totalVentas - costoVentas;
    
    
    List<Map<String, Object>> ventasPorPagoList = new ArrayList<>();
    for(Map.Entry<String, Double> entry : ventasPorFormaPago.entrySet()) {
        Map<String, Object> item = new HashMap<>();
        item.put("tipo", entry.getKey());
        item.put("valor", entry.getValue());
        ventasPorPagoList.add(item);
    }
    
   
    List<Map<String, Object>> productosTopList = new ArrayList<>();
    productosVendidos.entrySet().stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
        .limit(5)
        .forEach(entry -> {
            Map<String, Object> item = new HashMap<>();
            item.put("codigo", "P" + entry.getKey().substring(0, Math.min(3, entry.getKey().length())).toUpperCase());
            item.put("nombre", entry.getKey());
            item.put("cantidad_vendida", entry.getValue());
            productosTopList.add(item);
        });
    
    
    var resumenContable = TiendaConfig.getInstancia()
        .getGestionContable().obtenerResumenContable(fecha, fecha);
    
    
    return new ReporteDiarioJSONDTO(
        fecha, totalVentas, totalCompras, utilidadBruta,
        ventasPorPagoList, productosTopList, resumenContable
    );
}
}
