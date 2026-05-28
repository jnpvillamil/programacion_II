package co.edu.uptc.interfaces;

import co.edu.uptc.modelo.Venta;

import java.util.List;

public interface IRepositorioVenta {

    void guardarVenta(Venta venta);

    List<Venta> consultarHistorialCliente(String identificacion);

    List<Venta> consultarVentasPorFecha(String fecha);

    Venta buscarVentaPorFactura(String numeroFactura);

    boolean anularVenta(String numeroFactura);
}
