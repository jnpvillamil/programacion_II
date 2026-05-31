package co.uptc.edu.co.interfaces;

import java.time.LocalDate;
import java.util.List;

import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.modelo.ResumenProductoDTO;

public interface IGestionVenta {

    void registrarVenta(Venta venta) throws Exception;
    
    Venta buscarVentaPorNumero(String numeroFactura) throws Exception;

    List<Venta> obtenerVentasPorFecha(LocalDate fecha) throws Exception;
    
    List<Venta> obtenerVentas();
    
    void anularVenta(String numeroFactura, String motivo) throws Exception;

    String generarReporteProductosMasVendidos(LocalDate fechaInicio, LocalDate fechaFin) throws Exception;

    List<ResumenProductoDTO> obtenerResumenProductosMasVendidos(LocalDate fechaInicio, LocalDate fechaFin) throws Exception;

    String generarReporteProducto(String codigoProducto, LocalDate fechaInicio, LocalDate fechaFin) throws Exception;

    String generarNumeroFactura();

    void recargar() throws Exception;

}
