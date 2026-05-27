package co.uptc.edu.co.interfaces;

import java.util.List;

import co.uptc.edu.co.modelo.DetalleVenta;
import co.uptc.edu.co.modelo.Venta;

public interface IGestionVenta {

    void registrarVenta(Venta venta) throws Exception;
    
    Venta buscarVentaPorNumero(String numeroFactura) throws Exception;
    
    void validarVenta(Venta venta) throws Exception;
    
    void validarDetalleVenta(DetalleVenta detalle) throws Exception;
    
    List<Venta> obtenerVentas();
    
    void anularVenta(String numeroFactura) throws Exception;


}
