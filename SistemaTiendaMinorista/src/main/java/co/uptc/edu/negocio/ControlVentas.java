package co.uptc.edu.negocio;

import co.uptc.edu.modelo.Producto;
import co.uptc.edu.modelo.Venta;

import java.util.ArrayList;
import java.util.List;

public class ControlVentas {

    private List<Venta> listaVentas;

    private GestionProductos gestionProductos;

    public ControlVentas() {

        listaVentas = new ArrayList<>();

        gestionProductos =
                new GestionProductos();
    }

    // ================= REGISTRAR VENTA =================
    public boolean registrarVenta(Venta venta) {

        Producto producto =
                gestionProductos.buscarProducto(
                        venta.getCodigoProducto()
                );

        // VALIDAR EXISTENCIA
        if (producto == null) {
            return false;
        }

        int stockActual =
                producto.getStockActual();

        // VALIDAR STOCK
        if (venta.getCantidadVendida() > stockActual) {
            return false;
        }

        // DESCONTAR STOCK
        producto.setStockActual(
                stockActual - venta.getCantidadVendida()
        );

        // GUARDAR VENTA
        listaVentas.add(venta);

        return true;
    }

    // ================= LISTAR VENTAS =================
    public List<Venta> obtenerVentas() {

        return listaVentas;
    }
}