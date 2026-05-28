package co.edu.uptc.interfaces;

import java.time.LocalDateTime;

public interface IRepositorioFinanciero {
    
    double calcularTotalIngresosVentas(LocalDateTime inicio, LocalDateTime fin);
    
    double calcularTotalCostosCompras(LocalDateTime inicio, LocalDateTime fin);
    
}