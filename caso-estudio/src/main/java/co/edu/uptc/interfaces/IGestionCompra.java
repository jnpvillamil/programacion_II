package co.edu.uptc.interfaces;

import java.util.List;
import co.edu.uptc.negocio.dto.compraDto;

public interface IGestionCompra {
    public void guardar(compraDto compra);
    public void anular(int numeroFactura);
    public compraDto buscar(int numeroFactura);
    public List<compraDto> listar();
}