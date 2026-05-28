package co.edu.uptc.dto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReporteConsolidadoDiarioDTO {

    private String fecha;
    private double totalVentas;
    private double totalCompras;
    private double utilidadBruta;
    private List<VentaFormaPagoItem> ventasPorFormaPago = new ArrayList<>();
    private List<ProductoMasVendidoItem> productosMasVendidos = new ArrayList<>();
    private Map<String, Double> resumenContable = new LinkedHashMap<>();

    public static class VentaFormaPagoItem {
        private String formaPago;
        private double total;

        public VentaFormaPagoItem() {
        }

        public VentaFormaPagoItem(String formaPago, double total) {
            this.formaPago = formaPago;
            this.total = total;
        }

        public String getFormaPago() {
            return formaPago;
        }

        public void setFormaPago(String formaPago) {
            this.formaPago = formaPago;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }
    }

    public static class ProductoMasVendidoItem {
        private String codigo;
        private String nombre;
        private int cantidad;

        public ProductoMasVendidoItem() {
        }

        public ProductoMasVendidoItem(String codigo, String nombre, int cantidad) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.cantidad = cantidad;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(double totalVentas) {
        this.totalVentas = totalVentas;
    }

    public double getTotalCompras() {
        return totalCompras;
    }

    public void setTotalCompras(double totalCompras) {
        this.totalCompras = totalCompras;
    }

    public double getUtilidadBruta() {
        return utilidadBruta;
    }

    public void setUtilidadBruta(double utilidadBruta) {
        this.utilidadBruta = utilidadBruta;
    }

    public List<VentaFormaPagoItem> getVentasPorFormaPago() {
        return ventasPorFormaPago;
    }

    public void setVentasPorFormaPago(List<VentaFormaPagoItem> ventasPorFormaPago) {
        this.ventasPorFormaPago = ventasPorFormaPago;
    }

    public List<ProductoMasVendidoItem> getProductosMasVendidos() {
        return productosMasVendidos;
    }

    public void setProductosMasVendidos(List<ProductoMasVendidoItem> productosMasVendidos) {
        this.productosMasVendidos = productosMasVendidos;
    }

    public Map<String, Double> getResumenContable() {
        return resumenContable;
    }

    public void setResumenContable(Map<String, Double> resumenContable) {
        this.resumenContable = resumenContable;
    }
}
