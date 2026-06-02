package co.edu.uptc.interfaces;

import co.edu.uptc.dto.CompraDTO;
import co.edu.uptc.dto.VentaDTO;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.MovimientoContable;
import co.edu.uptc.modelo.Venta;

import java.util.List;
import java.util.function.Function;

public interface RepositorioComercial {

    void guardarVenta(Venta venta, Function<Venta, List<MovimientoContable>> constructorAsiento);

    void anularVenta(String numeroFactura, Function<Venta, List<MovimientoContable>> constructorAsiento);

    Venta buscarVentaPorNumeroFactura(String numeroFactura);

    void guardarCompra(Compra compra, Function<Compra, List<MovimientoContable>> constructorAsiento);

    List<VentaDTO> listarVenta();

    List<CompraDTO> listarCompra();
}
