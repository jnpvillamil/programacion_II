package co.edu.uptc.interfaces;

import co.edu.uptc.dto.CompraDTO;
import co.edu.uptc.dto.VentaDTO;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Venta;

import java.util.List;

public interface ManejadorEventoComercial {

    String realizarVenta(Venta venta);

    String anularVenta(String numeroFactura);

    void calcularTotales(Venta venta);

    String registrarCompra(Compra compra);

    Producto buscarProducto(String codigoInterno);

    Cliente buscarCliente(String identificacion);

    List<VentaDTO> obtenerListadoVenta();

    List<CompraDTO> obtenerListadoCompra();
}
