package co.uptc.edu.tienda.interfaces;

import java.util.List;
import co.uptc.edu.tienda.modelo.Compra;

public interface IGestionCompra {
    void guardar(Compra compra);        // ← individual, no lista
    List<Compra> cargar();
}