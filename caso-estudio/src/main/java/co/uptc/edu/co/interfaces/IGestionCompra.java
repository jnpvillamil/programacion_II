package co.uptc.edu.co.interfaces;

import java.util.List;
import co.uptc.edu.co.modelo.Compra;

public interface IGestionCompra {

    void registrarCompra(Compra compra) throws Exception;

    List<Compra> obtenerCompras();

    Compra buscarCompraPorNumero(String numeroFactura) throws Exception;
    
}
