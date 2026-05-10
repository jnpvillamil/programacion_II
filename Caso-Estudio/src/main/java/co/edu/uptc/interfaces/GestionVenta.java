package co.edu.uptc.interfaces;

import co.edu.uptc.modelo.Venta;
import co.edu.uptc.negocio.ReporteVentasDTO;
import co.edu.uptc.negocio.ReporteDiarioJSONDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface GestionVenta {
    
    // CRUD
    void crearVenta(Venta venta);
    Venta buscarVenta(String numeroFactura);
    List<Venta> listarVentas();
    void anularVenta(String numeroFactura);
    
 
    List<Venta> listarVentasPorFecha(LocalDate fecha);
    List<Venta> listarVentasPorCliente(String codigoCliente);
    List<Venta> listarVentasPorFormaPago(String formaPago);
    List<Venta> listarVentasPorPeriodo(LocalDate inicio, LocalDate fin);
    
    
    double calcularTotalVentasPeriodo(LocalDate inicio, LocalDate fin);
    int contarVentasPorFormaPago(String formaPago);

 // ventas diarias
    ReporteVentasDTO generarReporteVentasDiarias(LocalDate fecha);
 
    //  JSON diario
    ReporteDiarioJSONDTO generarDatosJSONDiario(LocalDate fecha);
    
    //  ventas mensuales
    ReporteVentasDTO generarReporteVentasMensuales(int anio, int mes);
    
    //  ventas anuales
    ReporteVentasDTO generarReporteVentasAnuales(int anio);
    
    // Productos más vendidos 
    List<Object[]> obtenerProductosMasVendidos(int top, LocalDate inicio, LocalDate fin);
    
    // Clientes con mayor volumen de compra 
    List<Object[]> obtenerClientesTopCompradores(int top, LocalDate inicio, LocalDate fin);
    
    // Ventas por forma de pago
    Map<String, Double> obtenerVentasPorFormaPago(LocalDate inicio, LocalDate fin);
    
   
    double obtenerValorInventarioTotal();
    List<Object[]> obtenerInventarioValorizado();
    
    // Utilidad bruta en un período
    double calcularUtilidadBruta(LocalDate inicio, LocalDate fin);
}

