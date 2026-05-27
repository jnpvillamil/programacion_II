package co.edu.uptc.negocio;

import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.persistencia.PersistenciaVentas;
import co.edu.uptc.utilidades.ManejadorFechas;


public class GestionVentas {

    private PersistenciaVentas persistenciaVenta;
    private GestionInventario gestionInventario;
    
    
    public GestionVentas(PersistenciaVentas persistenciaVenta, GestionInventario gestionInventario) {
        this.persistenciaVenta = persistenciaVenta;
        this.gestionInventario = gestionInventario;
    }

    
    public GestionVentas(GestionInventario gestionInventario) {
        this(new PersistenciaVentas(), gestionInventario);
    }

    public boolean procesarVenta(Venta venta) {
        if (venta.getProductosVendidos() == null || venta.getProductosVendidos().isEmpty()) {
            return false;
        }
        if (venta.getCliente() == null) {
            return false;
        }
       
        double subtotalGlobal = 0;
        for (DetalleVenta detalle : venta.getProductosVendidos()) {
            double subtotalItem = detalle.getCantidad() * detalle.getPrecioUnitario();
            detalle.setSubtotal(subtotalItem);
            subtotalGlobal += subtotalItem;
        }
        
        double iva = subtotalGlobal * 0.19; // IVA por defecto
        double total = subtotalGlobal + iva;

        venta.setSubtotal(subtotalGlobal);
        venta.setIvaAplicado(iva);
        venta.setTotalVenta(total);
        venta.setFechaHora(ManejadorFechas.obtenerFechaActual());

        for (DetalleVenta detalle : venta.getProductosVendidos()) {
            boolean descontado = gestionInventario.descontarStock(
                    detalle.getProducto().getCodigoProducto(), 
                    detalle.getCantidad()
            );
            
            if (!descontado) {
                return false; 
            }
        }

        // 4. Guardar Venta
        persistenciaVenta.guardarVenta(venta);
        return true;
    }

    public String generarFactura(Venta venta){

        StringBuilder sb = new StringBuilder();

        sb.append("===== FACTURA =====\n");
        sb.append("Factura: ").append(venta.getNumeroFactura()).append("\n");
        sb.append("Cliente: ").append(venta.getCliente().getNombre()).append("\n");
        sb.append("Total: ").append(venta.getTotalVenta()).append("\n");
        sb.append("Pago: ").append(venta.getFormaPago()).append("\n");

        return sb.toString();
    }
    
      
    public String reimprimirComprobante(
            String numeroFactura){

        Venta venta =
                persistenciaVenta
                .buscarVentaPorFactura(numeroFactura);

        if(venta == null){

            return "Venta no encontrada";
        }

        return "===== COMPROBANTE =====\n" +
               "Factura: " +
               venta.getNumeroFactura() + "\n" +

               "Subtotal: " +
               venta.getSubtotal() + "\n" +

               "IVA: " +
               venta.getIvaAplicado() + "\n" +

               "Total: " +
               venta.getTotalVenta() + "\n" +

               "Forma Pago: " +
               venta.getFormaPago();
    }    
   
    
    public String generarNumeroFactura() {
        // Generador simple basado en el timestamp de milisegundos
        return "FAC-" + System.currentTimeMillis();
    }
}