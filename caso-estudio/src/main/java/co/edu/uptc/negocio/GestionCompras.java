package co.edu.uptc.negocio;

import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.persistencia.PersistenciaCompra;
import co.edu.uptc.modelo.Producto;

/**
 * Capa de negocio para la gestión de compras.
 * 
 * APLICACIÓN DE PRINCIPIOS SOLID:
 * - S (Single Responsibility): Solo gestiona lógica de negocio de compras
 * - D (Dependency Inversion): Recibe GestionInventario inyectada, no la instancia
 * - O (Open/Closed): Abierto a nuevas implementaciones de persistencia
 */
public class GestionCompras {
    private PersistenciaCompra repo;
    private GestionInventario inventario;

    /**
     * Constructor con inyección de dependencias.
     * 
     * @param repo Persistencia de compras
     * @param inventario Gestión de inventario inyectada
     */
    public GestionCompras(PersistenciaCompra repo, GestionInventario inventario) {
        this.repo = repo;
        this.inventario = inventario;
    }

    /**
     * Constructor que recibe solo GestionInventario (compatibilidad).
     * Instancia PersistenciaCompra por defecto.
     */
    public GestionCompras(GestionInventario inventario) {
        this(new PersistenciaCompra(), inventario);
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