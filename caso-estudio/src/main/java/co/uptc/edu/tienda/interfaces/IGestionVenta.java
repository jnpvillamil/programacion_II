package co.uptc.edu.tienda.interfaces;

import java.util.List;

import co.uptc.edu.tienda.modelo.Venta;

public interface IGestionVenta {

    void guardar(List<Venta> ventas);

    List<Venta> leerVentas();
}