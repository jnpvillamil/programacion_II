package co.edu.uptc.negocio;

import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.persistencia.PersistenciaCompra;
import co.edu.uptc.modelo.Producto;

public class GestionCompras {
    private PersistenciaCompra repo;
    private GestionInventario inventario;

    public GestionCompras(GestionInventario inventario) {
        this.repo = new PersistenciaCompra();
        this.inventario = inventario;
    }

    public boolean procesarCompra(Compra c) {
        for (DetalleVenta dv : c.getProductosComprados()) {
            Producto p = inventario.buscarProducto(dv.getProducto().getCodigoProducto());
            if (p != null) {
                p.setStockActual(p.getStockActual() + dv.getCantidad());
                inventario.actualizarProducto(p);
            }
        }
        repo.guardarCompra(c);
        return true;
    }
}