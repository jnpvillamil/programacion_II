package co.edu.uptc.persistencia;

import co.edu.uptc.negocio.Compra;
import java.util.ArrayList;

public class PersistenciaCompra {
    private ArrayList<Compra> listaCompras;

    public PersistenciaCompra() {
        this.listaCompras = new ArrayList<>();
    }

    public void guardar(Compra compra) {
        this.listaCompras.add(compra);
    }

    public ArrayList<Compra> leerTodas() {
        return this.listaCompras;
    }

    public Compra buscarPorFactura(String numeroFactura) {
        for (Compra compra : listaCompras) {
            if (compra.getNumeroFactura().equals(numeroFactura)) {
                return compra;
            }
        }
        return null; // Retorna null si no la encuentra
    }
}