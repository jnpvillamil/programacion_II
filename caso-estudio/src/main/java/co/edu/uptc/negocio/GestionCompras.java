package co.edu.uptc.negocio;

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

        compra.setIva(compra.calcularIVA());
        compra.setTotalCompra(compra.calcularTotal());
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

        repositorioCompra.guardarCompra(compra);
        gestionContable.registrarContabilidadCompra(compra);
        return true;
    }
}
