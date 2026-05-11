package co.edu.uptc.negocio;

import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.persistencia.PersistenciaVentas;
import co.edu.uptc.utilidades.ManejadorFechas;

public class GestionVentas {

    private PersistenciaVentas persistenciaVenta;
    private GestionInventario gestionInventario;
    
    public GestionVentas(GestionInventario gestionInventario) {
        this.persistenciaVenta = new PersistenciaVentas();
        this.gestionInventario = gestionInventario;
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

    public String generarNumeroFactura() {
        // Generador simple basado en el timestamp de milisegundos
        return "FAC-" + System.currentTimeMillis();
    }
}