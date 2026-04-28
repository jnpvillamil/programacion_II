package co.edu.uptc.persistencia;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.gui.modelo.Compra;

public class LocalCompra {

    private static List<Compra> compras = new ArrayList<>();

    public LocalCompra() {
    }

    public boolean guardarCompra(Compra compra) {
        if (compra == null) {
            return false;
        }

        if (buscarCompra(compra.getNumeroFacturaProveedor()) != null) {
            return false;
        }

        return compras.add(compra);
    }

    public Compra buscarCompra(String factura) {
        if (factura == null) return null;

        for (Compra c : compras) {
            if (c.getNumeroFacturaProveedor() != null &&
                c.getNumeroFacturaProveedor().equalsIgnoreCase(factura)) {
                return c;
            }
        }
        return null;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public boolean actualizarCompra(Compra compraActualizada) {

        if (compraActualizada == null) return false;

        for (int i = 0; i < compras.size(); i++) {

            if (compras.get(i).getNumeroFacturaProveedor()
                    .equalsIgnoreCase(compraActualizada.getNumeroFacturaProveedor())) {

                compras.set(i, compraActualizada);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarCompra(String factura) {

        if (factura == null) return false;

        return compras.removeIf(c ->
                c.getNumeroFacturaProveedor() != null &&
                c.getNumeroFacturaProveedor().equalsIgnoreCase(factura));
    }
}