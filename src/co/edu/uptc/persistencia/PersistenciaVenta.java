package co.edu.uptc.persistencia;

import co.edu.uptc.negocio.Venta;
import java.util.ArrayList;

public class PersistenciaVenta {
    private ArrayList<Venta> listaVentas;

    public PersistenciaVenta() {
        this.listaVentas = new ArrayList<>();
    }

    public void guardar(Venta venta) {
        this.listaVentas.add(venta);
    }

    public ArrayList<Venta> leerTodas() {
        return this.listaVentas;
    }

    public Venta buscarPorFactura(String numeroFactura) {
        for (Venta venta : listaVentas) {
            // Asumiendo que Venta hereda getNumeroFactura() de Transaccion
            if (venta.getNumeroFactura().equals(numeroFactura)) {
                return venta;
            }
        }
        return null; // Retorna null si no la encuentra
    }
}