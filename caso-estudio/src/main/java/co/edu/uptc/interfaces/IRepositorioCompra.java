package co.edu.uptc.interfaces;

import java.util.List;
import co.edu.uptc.modelo.Compra;


public interface IRepositorioCompra extends Repositorio<Compra> {

    void guardarCompra(Compra compra);

    List<Compra> consultarPorProveedor(String codigoProveedor);
}