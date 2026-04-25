package co.edu.uptc.negocio;

import java.util.ArrayList;

public class GestionCompra {
    private ArrayList<Compra> historialCompras;

    public GestionCompra() {
        this.historialCompras = new ArrayList<>();
    }

    public void registrarCompra(Compra c) {
        historialCompras.add(c);
    }

    public ArrayList<Compra> getHistorialCompras() {
        return historialCompras;
    }

    public double calcularInversionTotal() {
        double total = 0;
        for (Compra c : historialCompras) {
            total += c.getTotal();
        }
        return total;
    }
}