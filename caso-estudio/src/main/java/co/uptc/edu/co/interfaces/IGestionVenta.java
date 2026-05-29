package co.uptc.edu.co.interfaces;

import java.util.List;

import co.uptc.edu.co.modelo.Venta;

public interface IGestionVenta {

    void registrarVenta(Venta venta) throws Exception;
    
    Venta buscarVentaPorNumero(String numeroFactura) throws Exception;
    
    List<Venta> obtenerVentas();
    
    void anularVenta(String numeroFactura, String motivo) throws Exception;

    String generarNumeroFactura();

    void recargar() throws Exception;

}
