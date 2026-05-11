package co.edu.uptc.negocio;

import co.edu.uptc.enums.TipoMovimiento;
import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.DetalleCompra;
import co.edu.uptc.modelo.Producto;

public class GestionCompra {
    private Repositorio<Compra> persistenciaCompra;
    private GestionProducto gestionProducto;
    private GestionContable gestionContable;

    public GestionCompra(Repositorio<Compra> persistenciaCompra, GestionProducto gestionProducto, GestionContable gestionContable) {
        this.persistenciaCompra = persistenciaCompra;
        this.gestionProducto = gestionProducto;
        this.gestionContable = gestionContable;
    }

    public void registrarCompra(Compra compra) {
        for (DetalleCompra detalle : compra.getListaDetalles()) {
            incrementarStock(detalle.getProducto(), detalle.getCantidad());
        }

        this.persistenciaCompra.guardar(compra);

        double iva = compra.getTotal() * 0.19;
        double subtotal = compra.getTotal() - iva;

        this.gestionContable.registrarPartidaDoble(subtotal, TipoMovimiento.EGRESO, "Inventario", "Compra Proveedor Fac: " + compra.getNumeroFacturaProveedor());
        this.gestionContable.registrarPartidaDoble(iva, TipoMovimiento.EGRESO, "IVA Descontable", "IVA Compra Fac: " + compra.getNumeroFacturaProveedor());
        this.gestionContable.registrarPartidaDoble(compra.getTotal(), TipoMovimiento.EGRESO, "Caja/Bancos", "Pago Proveedor Fac: " + compra.getNumeroFacturaProveedor());
    }

    public void incrementarStock(Producto producto, int cantidadComprada) {
        int nuevoStock = producto.getStockActual() + cantidadComprada;
        producto.setStockActual(nuevoStock);
        this.gestionProducto.actualizarProducto(producto);
    }
}