package co.edu.uptc.interfaces;

import co.edu.uptc.modelo.Compra;
import java.time.LocalDate;
import java.util.List;

public interface GestionCompra {
    
    // CRUD
    void crearCompra(Compra compra);
    Compra buscarCompra(String numeroFacturaProveedor);
    List<Compra> listarCompras();
    void anularCompra(String numeroFacturaProveedor);
    
   
    List<Compra> listarComprasPorFecha(LocalDate fecha);
    List<Compra> listarComprasPorProveedor(String codigoProveedor);
    List<Compra> listarComprasPorPeriodo(LocalDate inicio, LocalDate fin);
    
 
    double calcularTotalComprasPeriodo(LocalDate inicio, LocalDate fin);
    List<Object[]> obtenerProductosMasComprados();  // Para reportes
}