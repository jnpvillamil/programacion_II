package co.edu.uptc.dto;

public final class ReportesDTO {

    private ReportesDTO() {
    }

    public static class MejorClienteItem {
        private String identificacion;
        private String nombre;
        private double totalComprado;

        public MejorClienteItem() {
        }

        public MejorClienteItem(String identificacion, String nombre, double totalComprado) {
            this.identificacion = identificacion;
            this.nombre = nombre;
            this.totalComprado = totalComprado;
        }

        public String getIdentificacion() {
            return identificacion;
        }

        public String getNombre() {
            return nombre;
        }

        public double getTotalComprado() {
            return totalComprado;
        }
    }

    public static class ProductoVendidoItem {
        private String codigo;
        private String nombre;
        private int cantidad;

        public ProductoVendidoItem() {
        }

        public ProductoVendidoItem(String codigo, String nombre, int cantidad) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.cantidad = cantidad;
        }

        public String getCodigo() {
            return codigo;
        }

        public String getNombre() {
            return nombre;
        }

        public int getCantidad() {
            return cantidad;
        }
    }

    public static class VentaMetodoPagoItem {
        private String formaPago;
        private double totalVenta;

        public VentaMetodoPagoItem() {
        }

        public VentaMetodoPagoItem(String formaPago, double totalVenta) {
            this.formaPago = formaPago;
            this.totalVenta = totalVenta;
        }

        public String getFormaPago() {
            return formaPago;
        }

        public double getTotalVenta() {
            return totalVenta;
        }
    }

    public static class InventarioItem {
        private String codigo;
        private String nombre;
        private int stockActual;
        private double valorizacion;

        public InventarioItem() {
        }

        public InventarioItem(String codigo, String nombre, int stockActual, double valorizacion) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.stockActual = stockActual;
            this.valorizacion = valorizacion;
        }

        public String getCodigo() {
            return codigo;
        }

        public String getNombre() {
            return nombre;
        }

        public int getStockActual() {
            return stockActual;
        }

        public double getValorizacion() {
            return valorizacion;
        }
    }
}
