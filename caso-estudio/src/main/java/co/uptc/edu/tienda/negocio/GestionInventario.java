package co.uptc.edu.tienda.negocio;

import java.time.LocalDateTime;
import java.util.List;

import co.uptc.edu.tienda.enums.TipoMovimiento;
import co.uptc.edu.tienda.interfaces.IGestionInventario;
import co.uptc.edu.tienda.modelo.DetalleVenta;
import co.uptc.edu.tienda.modelo.MovimientoInventario;
import co.uptc.edu.tienda.modelo.Producto;
import co.uptc.edu.tienda.modelo.Venta;

public class GestionInventario {

    private List<MovimientoInventario> listaMovimientos;
    private IGestionInventario iInventario;
    private int consecutivo;

    public GestionInventario(IGestionInventario iInventario) {
        this.iInventario = iInventario;
        this.listaMovimientos = iInventario.leerMovimientos();
        int max = 0;
        for (MovimientoInventario m : listaMovimientos) {
            if (m.getIdMovimiento() > max) max = m.getIdMovimiento();
        }
        this.consecutivo = max + 1;
    }

    // =====================================
    // REGISTRAR SALIDA POR VENTA
    // =====================================
    public void registrarSalidaPorVenta(Venta venta,
                                        List<Producto> listaProductos) throws Exception {
        if (venta == null || venta.getDetalles().isEmpty()) {
            throw new Exception("La venta no tiene productos");
        }

        for (DetalleVenta detalle : venta.getDetalles()) {

            Producto productoReal = buscarProducto(
                    detalle.getProducto().getCodigoProducto(), listaProductos);

            if (productoReal == null) {
                throw new Exception("Producto no encontrado: "
                        + detalle.getProducto().getCodigoProducto());
            }

            productoReal.setStockActual(
                    productoReal.getStockActual() - detalle.getCantidad());

            MovimientoInventario mov = new MovimientoInventario();
            mov.setIdMovimiento(consecutivo++);
            mov.setProducto(productoReal);
            mov.setTipoMovimiento(TipoMovimiento.SALIDA);
            mov.setCantidad(detalle.getCantidad());
            mov.setFechaHora(LocalDateTime.now().toString());
            mov.setMotivo("Venta: " + venta.getNumeroFactura());

            listaMovimientos.add(mov);
            iInventario.guardar(mov); // ← guarda uno por uno
        }
    }

    // =====================================
    // REGISTRAR ENTRADA POR ANULACION
    // =====================================
    public void registrarEntradaPorAnulacion(Venta venta,
                                             List<Producto> listaProductos, String motivoUsuario) throws Exception {
        if (venta == null || venta.getDetalles().isEmpty()) {
            throw new Exception("La venta no tiene productos");
        }

        for (DetalleVenta detalle : venta.getDetalles()) {

            Producto productoReal = buscarProducto(
                    detalle.getProducto().getCodigoProducto(), listaProductos);

            if (productoReal == null) {
                throw new Exception("Producto no encontrado: "
                        + detalle.getProducto().getCodigoProducto());
            }

            productoReal.setStockActual(
                    productoReal.getStockActual() + detalle.getCantidad());

            MovimientoInventario mov = new MovimientoInventario();
            mov.setIdMovimiento(consecutivo++);
            mov.setProducto(productoReal);
            mov.setTipoMovimiento(TipoMovimiento.ENTRADA);
            mov.setCantidad(detalle.getCantidad());
            mov.setFechaHora(LocalDateTime.now().toString());
            mov.setMotivo("Anulación venta: " + venta.getNumeroFactura() + " - " + motivoUsuario);
            listaMovimientos.add(mov);
            iInventario.guardar(mov); // ← guarda uno por uno
        }
    }

    // =====================================
    // REGISTRAR ENTRADA POR COMPRA
    // =====================================
    public void registrarEntradaPorCompra(String numeroFacturaCompra,
                                          Producto producto,
                                          int cantidad,
                                          List<Producto> listaProductos) throws Exception {

        Producto productoReal = buscarProducto(
                producto.getCodigoProducto(), listaProductos);

        if (productoReal == null) {
            throw new Exception("Producto no encontrado: "
                    + producto.getCodigoProducto());
        }

        productoReal.setStockActual(
                productoReal.getStockActual() + cantidad);

        MovimientoInventario mov = new MovimientoInventario();
        mov.setIdMovimiento(consecutivo++);
        mov.setProducto(productoReal);
        mov.setTipoMovimiento(TipoMovimiento.ENTRADA);
        mov.setCantidad(cantidad);
        mov.setFechaHora(LocalDateTime.now().toString());
        mov.setMotivo("Compra: " + numeroFacturaCompra);

        listaMovimientos.add(mov);
        iInventario.guardar(mov); // ← guarda uno por uno
    }

    // =====================================
    // LISTAR
    // =====================================
    public List<MovimientoInventario> listarMovimientos() {
        return listaMovimientos;
    }

    // =====================================
    // BUSCAR PRODUCTO EN LISTA
    // =====================================
    private Producto buscarProducto(int codigo, List<Producto> lista) {
        for (Producto p : lista) {
            if (p.getCodigoProducto() == codigo) return p;
        }
        return null;
    }
}