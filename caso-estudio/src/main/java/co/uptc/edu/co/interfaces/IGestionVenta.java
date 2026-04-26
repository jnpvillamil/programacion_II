package co.uptc.edu.co.interfaces;

import java.util.List;
import co.uptc.edu.co.modelo.Venta;

public interface IGestionVenta {

    void registrarVenta(Venta venta) throws Exception;

    List<Venta> obtenerVentas();

    Venta buscarVentaPorNumero(String numeroFactura) throws Exception;
}
