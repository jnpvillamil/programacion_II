package co.uptc.edu.tienda.negocio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.uptc.edu.tienda.enums.EstadoVentaEnum;
import co.uptc.edu.tienda.interfaces.IGestionCompra;
import co.uptc.edu.tienda.interfaces.IGestionReporte;
import co.uptc.edu.tienda.interfaces.IGestionVenta;
import co.uptc.edu.tienda.modelo.ClienteVolumen;
import co.uptc.edu.tienda.modelo.Compra;
import co.uptc.edu.tienda.modelo.DetalleVenta;
import co.uptc.edu.tienda.modelo.Producto;
import co.uptc.edu.tienda.modelo.ProductoMasVendido;
import co.uptc.edu.tienda.modelo.ResumenContable;
import co.uptc.edu.tienda.modelo.ResumenFinanciero;
import co.uptc.edu.tienda.modelo.Venta;
import co.uptc.edu.tienda.modelo.VentaPorFormaPago;

public class GestionReporte {

    private IGestionVenta iVenta;
    private IGestionCompra iCompra;
    private IGestionReporte iReporte;

    public GestionReporte(IGestionVenta iVenta,
                          IGestionCompra iCompra,
                          IGestionReporte iReporte) {
        this.iVenta = iVenta;
        this.iCompra = iCompra;
        this.iReporte = iReporte;
    }

    // =====================================
    // GENERAR REPORTE POR PERIODO
    // desde y hasta en formato yyyy-MM-dd
    // =====================================
    public ResumenFinanciero generarReporte(String desde, String hasta,
                                            List<Producto> productos) {
        List<Venta> ventas = iVenta.leerVentas();
        List<Compra> compras = iCompra.cargar();

        // Filtrar por periodo y solo ventas activas
        List<Venta> ventasFiltradas = filtrarVentas(ventas, desde, hasta);
        List<Compra> comprasFiltradas = filtrarCompras(compras, desde, hasta);

        ResumenFinanciero resumen = new ResumenFinanciero();
        resumen.setFechaInicio(desde);
        resumen.setFechaFin(hasta);

        // Totales
        double totalVentas = calcularTotalVentas(ventasFiltradas);
        double totalCompras = calcularTotalCompras(comprasFiltradas);
        resumen.setTotalVentas(totalVentas);
        resumen.setTotalCompras(totalCompras);
        resumen.setUtilidadBruta(totalVentas - totalCompras);

        // Ventas por forma de pago
        resumen.setVentasPorFormaPago(calcularVentasPorFormaPago(ventasFiltradas));

        // Productos más vendidos
        resumen.setProductosMasVendidos(calcularProductosMasVendidos(ventasFiltradas));

        // Clientes con mayor volumen
        resumen.setClientesMayorVolumen(calcularClientesMayorVolumen(ventasFiltradas));

        // Inventario valorizado
        resumen.setInventarioValorizado(calcularInventarioValorizado(productos));

        // Resumen contable
        double ivaGenerado = calcularIvaGenerado(ventasFiltradas);
        resumen.setResumenContable(new ResumenContable(
                totalVentas, totalCompras, ivaGenerado, 0.0));

        // Persistir el reporte en JSON
        iReporte.guardar(resumen);

        return resumen;
    }

    // =====================================
    // FILTROS
    // =====================================
    private List<Venta> filtrarVentas(List<Venta> ventas, String desde, String hasta) {
        List<Venta> resultado = new ArrayList<>();
        for (Venta v : ventas) {
            if (v.getEstado() != EstadoVentaEnum.ACTIVA) continue;
            String fecha = v.getFechaHora().substring(0, 10);
            if (!desde.isEmpty() && fecha.compareTo(desde) < 0) continue;
            if (!hasta.isEmpty() && fecha.compareTo(hasta) > 0) continue;
            resultado.add(v);
        }
        return resultado;
    }

    private List<Compra> filtrarCompras(List<Compra> compras, String desde, String hasta) {
        List<Compra> resultado = new ArrayList<>();
        for (Compra c : compras) {
            String fecha = c.getFechaCompra().substring(0, 10);
            if (!desde.isEmpty() && fecha.compareTo(desde) < 0) continue;
            if (!hasta.isEmpty() && fecha.compareTo(hasta) > 0) continue;
            resultado.add(c);
        }
        return resultado;
    }

    // =====================================
    // CÁLCULOS
    // =====================================
    private double calcularTotalVentas(List<Venta> ventas) {
        double total = 0;
        for (Venta v : ventas) total += v.getTotal();
        return total;
    }

    private double calcularTotalCompras(List<Compra> compras) {
        double total = 0;
        for (Compra c : compras) total += c.getTotal();
        return total;
    }

    private double calcularIvaGenerado(List<Venta> ventas) {
        double iva = 0;
        for (Venta v : ventas) iva += v.getImpuestos();
        return iva;
    }

    private List<VentaPorFormaPago> calcularVentasPorFormaPago(List<Venta> ventas) {
        Map<String, Double> mapa = new HashMap<>();
        for (Venta v : ventas) {
            String forma = v.getFormaPago().name();
            mapa.put(forma, mapa.getOrDefault(forma, 0.0) + v.getTotal());
        }
        List<VentaPorFormaPago> resultado = new ArrayList<>();
        for (Map.Entry<String, Double> entry : mapa.entrySet()) {
            resultado.add(new VentaPorFormaPago(entry.getKey(), entry.getValue()));
        }
        return resultado;
    }

    private List<ProductoMasVendido> calcularProductosMasVendidos(List<Venta> ventas) {
        Map<Integer, int[]> mapa = new HashMap<>(); // codigo → [cantidad, nombre_placeholder]
        Map<Integer, String> nombres = new HashMap<>();

        for (Venta v : ventas) {
            if (v.getDetalles() == null) continue;
            for (DetalleVenta d : v.getDetalles()) {
                int codigo = d.getProducto().getCodigoProducto();
                String nombre = d.getProducto().getNombreProducto();
                nombres.put(codigo, nombre);
                int[] acum = mapa.getOrDefault(codigo, new int[]{0});
                acum[0] += d.getCantidad();
                mapa.put(codigo, acum);
            }
        }

        List<ProductoMasVendido> resultado = new ArrayList<>();
        for (Map.Entry<Integer, int[]> entry : mapa.entrySet()) {
            resultado.add(new ProductoMasVendido(
                    entry.getKey(),
                    nombres.get(entry.getKey()),
                    entry.getValue()[0]));
        }

        // Ordenar de mayor a menor cantidad
        resultado.sort((a, b) -> b.getCantidadVendida() - a.getCantidadVendida());
        return resultado;
    }

    private List<ClienteVolumen> calcularClientesMayorVolumen(List<Venta> ventas) {
        Map<Integer, Double> montos = new HashMap<>();
        Map<Integer, String> nombres = new HashMap<>();

        for (Venta v : ventas) {
            if (v.getCliente() == null) continue;
            int id = v.getCliente().getIdCliente();
            String nombre = v.getCliente().getNombreCompleto();
            nombres.put(id, nombre);
            montos.put(id, montos.getOrDefault(id, 0.0) + v.getTotal());
        }

        List<ClienteVolumen> resultado = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : montos.entrySet()) {
            resultado.add(new ClienteVolumen(
                    entry.getKey(),
                    nombres.get(entry.getKey()),
                    entry.getValue()));
        }

        // Ordenar de mayor a menor volumen
        resultado.sort((a, b) -> Double.compare(b.getTotalComprado(), a.getTotalComprado()));
        return resultado;
    }

    private double calcularInventarioValorizado(List<Producto> productos) {
        double total = 0;
        for (Producto p : productos) {
            total += p.getStockActual() * p.getPrecioCompra();
        }
        return total;
    }
}