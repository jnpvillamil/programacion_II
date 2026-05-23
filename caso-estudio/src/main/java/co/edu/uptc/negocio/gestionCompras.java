package co.edu.uptc.negocio;

import java.util.List;
import co.edu.uptc.interfaces.IGestionCompra;
import co.edu.uptc.interfaces.IGestionMovimientoContable;
import co.edu.uptc.interfaces.IGestionProducto;
import co.edu.uptc.negocio.dto.compraDto;
import co.edu.uptc.negocio.dto.itemCompraDto;
import co.edu.uptc.negocio.dto.movimientoContableDto;
import co.edu.uptc.negocio.dto.productoDto;
import co.edu.uptc.persistencia.LocalCompra;
import co.edu.uptc.persistencia.LocalMovimientoContable;
import co.edu.uptc.persistencia.LocalProducto;

public class gestionCompras {

    private IGestionCompra             iCompra;
    private IGestionMovimientoContable iMovimiento;
    private IGestionProducto           iProducto;

    public gestionCompras() {
        this.iCompra     = new LocalCompra();
        this.iMovimiento = new LocalMovimientoContable();
        this.iProducto   = new LocalProducto();
    }

    public void registrar(compraDto compra) throws Exception {
        if (compra == null)
            throw new Exception("No se tiene informacion de la compra");
        if (compra.getProductos() == null || compra.getProductos().isEmpty())
            throw new Exception("La compra debe tener al menos un producto");
        if (compra.getCodigoProveedor() <= 0)
            throw new Exception("Debe seleccionar un proveedor valido");

        calcularTotales(compra);
        iCompra.guardar(compra);
        actualizarInventario(compra);
        registrarEgresoContable(compra);
    }

    public void anular(int numeroFactura) throws Exception {
        compraDto compra = iCompra.buscar(numeroFactura);
        if (compra == null)
            throw new Exception("No se encontro la compra con factura N° " + numeroFactura);
        iCompra.anular(numeroFactura);
    }

    public compraDto buscar(int numeroFactura) throws Exception {
        compraDto compra = iCompra.buscar(numeroFactura);
        if (compra == null)
            throw new Exception("No se encontro la compra con factura N° " + numeroFactura);
        return compra;
    }

    public List<compraDto> listar() {
        return iCompra.listar();
    }

    public List<movimientoContableDto> listarMovimientos() {
        return iMovimiento.listar();
    }

    // Métodos privados 

    private void calcularTotales(compraDto compra) {
        double subtotal = 0;
        for (itemCompraDto item : compra.getProductos()) {
            double subItem = item.getCantidad() * item.getCostoUnitario();
            item.setSubtotal(subItem);
            subtotal += subItem;
        }
        compra.setSubtotal(subtotal);
        compra.setTotal(subtotal + compra.getImpuestos());
    }

    private void actualizarInventario(compraDto compra) {
        for (itemCompraDto item : compra.getProductos()) {
            productoDto producto = iProducto.buscar(item.getCodigoProducto());
            if (producto != null) {
                producto.setStockActual(producto.getStockActual() + item.getCantidad());
                producto.setPrecioCompra(item.getCostoUnitario());
                iProducto.actualizar(producto);
            }
        }
    }

    private void registrarEgresoContable(compraDto compra) {
        movimientoContableDto movimiento = new movimientoContableDto();
        movimiento.setFecha(compra.getFecha());
        movimiento.setTipoMovimiento("EGRESO");
        movimiento.setCuentaContable("Cuentas por pagar - Proveedores");
        movimiento.setValor(compra.getTotal());
        movimiento.setDescripcion("Compra factura N° " + compra.getNumeroFacturaProveedor()
                + " - Proveedor: " + compra.getRazonSocialProveedor());
        iMovimiento.guardar(movimiento);
    }
}