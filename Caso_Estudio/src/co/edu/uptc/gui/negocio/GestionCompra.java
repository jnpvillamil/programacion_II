package co.edu.uptc.gui.negocio;

import java.util.List;

import co.edu.uptc.gui.modelo.Compra;
import co.edu.uptc.persistencia.LocalCompra;

public class GestionCompra {

    private LocalCompra localCompra;

    public GestionCompra() {
        localCompra = new LocalCompra();
    }

    public boolean registrarCompra(Compra compra) {
        compra.calcularTotalCompra();
        return localCompra.guardarCompra(compra);
    }

    public Compra buscarCompra(String numeroFacturaProveedor) {
        return localCompra.buscarCompra(numeroFacturaProveedor);
    }

    public List<Compra> listarCompras() {
        return localCompra.getCompras();
    }
}