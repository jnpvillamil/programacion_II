package co.edu.uptc.dto;

import java.util.ArrayList;
import java.util.List;

public class ResumenDiarioJSONDTO {

    private String fecha;
    private double totalVentas;
    private double totalCompras;
    private double utilidadBruta;
    private List<VentaPorFormaPago> ventasPorFormaPago = new ArrayList<>();
    private List<ProductoMasVendido> productosMasVendidos = new ArrayList<>();
    private ResumenContable resumenContable = new ResumenContable();

    public static class VentaPorFormaPago {
        private String tipo;
        private double valor;

        public VentaPorFormaPago() {
        }

        public VentaPorFormaPago(String tipo, double valor) {
            this.tipo = tipo;
            this.valor = valor;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public double getValor() {
            return valor;
        }

        public void setValor(double valor) {
            this.valor = valor;
        }
    }

    public static class ProductoMasVendido {
        private String codigo;
        private String nombre;
        private int cantidadVendida;

        public ProductoMasVendido() {
        }

        public ProductoMasVendido(String codigo, String nombre, int cantidadVendida) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.cantidadVendida = cantidadVendida;
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

        public int getCantidadVendida() {
            return cantidadVendida;
        }

        public void setCantidadVendida(int cantidadVendida) {
            this.cantidadVendida = cantidadVendida;
        }
    }

    public static class ResumenContable {
        private double ingresos;
        private double egresos;
        private double ivaGenerado;
        private double ivaDescontable;

        public double getIngresos() {
            return ingresos;
        }

        public void setIngresos(double ingresos) {
            this.ingresos = ingresos;
        }

        public double getEgresos() {
            return egresos;
        }

        public void setEgresos(double egresos) {
            this.egresos = egresos;
        }

        public double getIvaGenerado() {
            return ivaGenerado;
        }

        public void setIvaGenerado(double ivaGenerado) {
            this.ivaGenerado = ivaGenerado;
        }

        public double getIvaDescontable() {
            return ivaDescontable;
        }

        public void setIvaDescontable(double ivaDescontable) {
            this.ivaDescontable = ivaDescontable;
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

    public List<VentaPorFormaPago> getVentasPorFormaPago() {
        return ventasPorFormaPago;
    }

    public void setVentasPorFormaPago(List<VentaPorFormaPago> ventasPorFormaPago) {
        this.ventasPorFormaPago = ventasPorFormaPago;
    }

    public List<ProductoMasVendido> getProductosMasVendidos() {
        return productosMasVendidos;
    }

    public void setProductosMasVendidos(List<ProductoMasVendido> productosMasVendidos) {
        this.productosMasVendidos = productosMasVendidos;
    }

    public ResumenContable getResumenContable() {
        return resumenContable;
    }

    public void setResumenContable(ResumenContable resumenContable) {
        this.resumenContable = resumenContable;
    }
}
