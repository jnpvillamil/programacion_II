package co.uptc.edu.negocio;

import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.modelo.Compra;

public class GestionCompras {

    private List<Compra> listaCompras;

    public GestionCompras() {
        listaCompras = new ArrayList<>();
    }

    public boolean registrarCompra(Compra compra) {

        if (buscarCompra(compra.getNumeroFactura()) != null) {
            return false;
        }

        listaCompras.add(compra);
        return true;
    }

    public Compra buscarCompra(String numeroFactura) {

        for (Compra c : listaCompras) {
            if (c.getNumeroFactura().equals(numeroFactura)) {
                return c;
            }
        }
        return null;
    }

    public List<Compra> obtenerCompras() {
        return listaCompras;
    }
}