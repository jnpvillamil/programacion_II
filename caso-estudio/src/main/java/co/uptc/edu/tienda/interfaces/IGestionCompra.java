package co.uptc.edu.tienda.interfaces;

import java.util.List;
import co.uptc.edu.tienda.modelo.Compra;

public interface IGestionCompra {

    void agregarCompra(Compra compra);

    void actualizarCompra(Compra compra);

    boolean eliminarCompra(int id);

    Compra buscarCompra(int id);

    List<Compra> listarCompras();

	List<Compra> cargar();

	void guardar(List<Compra> lista);
}