package co.edu.uptc.interfaces;

import co.edu.uptc.modelo.Venta;

public interface RepositorioVenta extends Repositorio<Venta> {
    void anularVenta(String numeroFactura);
}
