package co.edu.uptc.interfaces;

import java.util.List;

import co.edu.uptc.negocio.dto.ventaDto;

public interface IGestionVenta {
    public void guardar(ventaDto venta);
    public void anular(int numeroFactura);
    public ventaDto buscar(int numeroFactura);
    public List<ventaDto> listar();
}
