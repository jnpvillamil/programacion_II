package co.edu.uptc.negocio;

import java.util.ArrayList;

public class GestionVenta {
    private ArrayList<Venta> historialVentas;

    public GestionVenta() {
        this.historialVentas = new ArrayList<>();
    }

    public void registrarVenta(Venta v) {
        historialVentas.add(v);
    }

    public ArrayList<Venta> getHistorialVentas() {
        return historialVentas;
    }

    public double calcularTotalVentasDelDia() {
        double total = 0;
        for (Venta v : historialVentas) {
            total += v.getTotal();
        }
        return total;
    }
}