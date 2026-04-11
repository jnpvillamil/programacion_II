package co.edu.uptc.persistencia;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.gui.modelo.Compra;

public class LocalCompra {

    private List<Compra> compras;

    public LocalCompra() {
        compras = new ArrayList<>();
    }

    public boolean guardarCompra(Compra compra) {
        if (buscarCompra(compra.getNumeroFacturaProveedor()) != null) {
            return false;
        }
        compras.add(compra);
        return true;
    }

    public Compra buscarCompra(String numeroFacturaProveedor) {
        for (Compra compra : compras) {
            if (compra.getNumeroFacturaProveedor().equalsIgnoreCase(numeroFacturaProveedor)) {
                return compra;
            }
        }
        return null;
    }

    public List<Compra> getCompras() {
        return compras;
    }
}