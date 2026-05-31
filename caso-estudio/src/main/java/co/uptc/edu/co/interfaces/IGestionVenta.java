package co.uptc.edu.co.interfaces;

import java.util.List;

import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.modelo.Cliente;
import co.uptc.edu.co.modelo.DetalleVenta;
import co.uptc.edu.co.modelo.Producto;

public interface IGestionVenta {

    void registrarVenta(Venta venta) throws Exception;
    
    Venta buscarVentaPorNumero(String numeroFactura) throws Exception;

    List<Venta> obtenerVentas();

    List<Venta> obtenerVentasPorCliente(Cliente cliente) throws Exception;
    
    void anularVenta(String numeroFactura, String motivo) throws Exception;

    String generarNumeroFactura();

    void recargar() throws Exception;

    double calcularSubtotalDetalleVenta(Producto producto, int cantidad) throws Exception;

    double calcularIvaDetalleVenta(Producto producto, int cantidad) throws Exception;

    double calcularSubtotalVenta(List<DetalleVenta> detalles) throws Exception;

    double calcularImpuestosVenta(List<DetalleVenta> detalles) throws Exception;

    double calcularTotalVenta(List<DetalleVenta> detalles) throws Exception;

}
