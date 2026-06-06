package co.uptc.edu.co.interfaces;

import java.sql.Connection;
import java.util.List;

import co.uptc.edu.co.modelo.Compra;

public interface IGestionCompra {

    void guardar(Compra compra) throws Exception;

    void guardar(Connection conexion, Compra compra) throws Exception;

    void actualizar(Compra compra) throws Exception;

    void actualizar(Connection conexion, Compra compra) throws Exception;

    Compra buscar(String numeroFactura) throws Exception;

    Compra buscar(Connection conexion, String numeroFactura) throws Exception;

    List<Compra> listar() throws Exception;

	
}