package co.edu.uptc.tiendaminorista.interfaces;

import java.util.List;
import co.edu.uptc.tiendaminorista.modelo.CompasCliente;

public interface IGestionCompraCli {
    void guardarCompra(CompasCliente compra);
    List<CompasCliente> obtenerTodasLasCompras();
   
    List<CompasCliente> listarComprasPorCliente(String codigoCliente);
    List<CompasCliente> listarComprasPorCliente(String codigoCliente, String nombreCliente);
}