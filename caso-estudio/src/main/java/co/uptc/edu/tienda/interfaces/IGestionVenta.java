package co.uptc.edu.tienda.interfaces;

import java.util.List;

import co.uptc.edu.tienda.modelo.Venta;

public interface IGestionVenta {

    void guardar(List<Venta> ventas);

    public List<Venta> leerVentas();
    
    public Venta buscarPorFactura(String numeroFactura); // ← nuevo
    public void actualizar(Venta venta);                 // ← nuevo
}