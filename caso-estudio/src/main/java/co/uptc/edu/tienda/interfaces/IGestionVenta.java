package co.uptc.edu.tienda.interfaces;

import java.util.List;

import co.uptc.edu.tienda.modelo.Venta;

public interface IGestionVenta {

    public void guardar(Venta venta);
    public List<Venta> leerVentas();
    public void actualizar(Venta venta);                 // ← nuevo
}