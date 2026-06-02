package co.edu.uptc.negocio;

import co.edu.uptc.dto.CarritoItemDTO;
import co.edu.uptc.dto.ResultadoOperacion;
import co.edu.uptc.interfaces.Calculable;
import co.edu.uptc.interfaces.IContabilizable;
import co.edu.uptc.interfaces.IRepositorioCompra;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.modelo.MovimientoContable;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.persistencia.PersistenciaCompra;
import co.edu.uptc.persistencia.PersistenciaContable;
import co.edu.uptc.utilidades.ManejadorFechas;
import co.edu.uptc.utilidades.ValidadorEntradas;

import java.time.LocalDate;
import java.util.List;

public class GestionCompras implements IContabilizable {

    private final IRepositorioCompra repositorioCompra;
    private final GestionInventario inventario;
    private final GestionContable gestionContable;

    public GestionCompras(IRepositorioCompra repositorioCompra, GestionInventario inventario,
                          GestionContable gestionContable) {
        this.repositorioCompra = repositorioCompra;
        this.inventario = inventario;
        this.gestionContable = gestionContable;
    }

    public GestionCompras(IRepositorioCompra repositorioCompra, GestionInventario inventario) {
        this(repositorioCompra, inventario, new GestionContable(new PersistenciaContable()));
    }

    public GestionCompras(GestionInventario inventario) {
        this(new PersistenciaCompra(), inventario);
    }

    @Override
    public void registrarMovimiento(MovimientoContable movimiento) {
        gestionContable.registrarMovimiento(movimiento);
    }

    @Override
    public double obtenerSaldo(String cuentaContable) {
        return gestionContable.obtenerSaldo(cuentaContable);
    }

    public double calcularTotalComprasPorFecha(LocalDate fecha) {
        if (fecha == null) {
            return 0.0;
        }
        return repositorioCompra.listar().stream()
                .filter(compra -> compra.getFecha() != null
                        && compra.getFecha().toLocalDate().equals(fecha))
                .mapToDouble(Compra::getTotalCompra)
                .sum();
    }

    public boolean procesarCompra(Compra compra) {
        if (compra == null || compra.getProveedor() == null) {
            return false;
        }
        if (ValidadorEntradas.esVacio(compra.getFacturaProveedor())) {
            return false;
        }
        if (compra.getProductosComprados() == null || compra.getProductosComprados().isEmpty()) {
            return false;
        }

        for (DetalleVenta detalle : compra.getProductosComprados()) {
            Producto producto = inventario.buscarProducto(detalle.getProducto().getCodigoProducto());
            if (producto == null || !producto.isActivo()) {
                return false;
            }

            double subtotalItem = detalle.getCantidad() * detalle.getPrecioUnitario();
            detalle.setSubtotal(subtotalItem);
        }

        aplicarTotalesCalculables(compra);
        if (compra.getFecha() == null) {
            compra.setFecha(ManejadorFechas.obtenerFechaActual());
        }

        for (DetalleVenta detalle : compra.getProductosComprados()) {
            boolean ingresado = inventario.registrarMovimientoInventario(
                    detalle.getProducto().getCodigoProducto(),
                    detalle.getCantidad(),
                    "ENTRADA");
            if (!ingresado) {
                return false;
            }
        }

        if (!repositorioCompra.guardarCompra(compra)) {
            revertirStockCompra(compra);
            return false;
        }
        gestionContable.registrarContabilidadCompra(compra);
        return true;
    }

    private void revertirStockCompra(Compra compra) {
        for (DetalleVenta detalle : compra.getProductosComprados()) {
            inventario.registrarMovimientoInventario(
                    detalle.getProducto().getCodigoProducto(),
                    detalle.getCantidad(),
                    "SALIDA");
        }
    }

    private void aplicarTotalesCalculables(Compra compra) {
        Calculable calculable = compra;
        compra.setIva(calculable.calcularIVA());
        compra.setTotalCompra(calculable.calcularTotal());
    }

    public ResultadoOperacion validarAgregarLineaCompra(String codigo, String cantidadStr, String costoStr) {
        if (ValidadorEntradas.esVacio(codigo) || !ValidadorEntradas.esNumero(cantidadStr)
                || !ValidadorEntradas.esNumero(costoStr)) {
            return ResultadoOperacion.error("Complete código, cantidad y costo unitario con valores válidos.");
        }
        int cantidad = (int) Double.parseDouble(cantidadStr.trim());
        double costoUnitario = Double.parseDouble(costoStr.trim());
        if (cantidad <= 0 || costoUnitario <= 0) {
            return ResultadoOperacion.error("Cantidad y costo deben ser mayores a cero.");
        }
        Producto producto = inventario.buscarProducto(codigo.trim());
        if (producto == null || !producto.isActivo()) {
            return ResultadoOperacion.error("Producto no encontrado o inactivo.");
        }
        double subtotal = costoUnitario * cantidad;
        CarritoItemDTO item = new CarritoItemDTO(producto, cantidad, costoUnitario, subtotal);
        return ResultadoOperacion.exito("Línea validada.", item);
    }

    public ResultadoOperacion procesarCompraConResultado(Compra compra) {
        if (compra == null || compra.getProveedor() == null) {
            return ResultadoOperacion.error("Debe seleccionar un proveedor antes de registrar la compra.");
        }
        if (ValidadorEntradas.esVacio(compra.getFacturaProveedor())) {
            return ResultadoOperacion.error("Ingrese el número de factura del proveedor.");
        }
        if (compra.getProductosComprados() == null || compra.getProductosComprados().isEmpty()) {
            return ResultadoOperacion.error("Agregue al menos un producto a la compra.");
        }
        if (procesarCompra(compra)) {
            return ResultadoOperacion.exito("Compra registrada exitosamente.", compra);
        }
        return ResultadoOperacion.error(
                "No se pudo registrar la compra. Revise conexion a BD, proveedor y tablas compras/detalles_compras.");
    }
}
