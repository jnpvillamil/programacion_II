package co.edu.uptc.caso.estudio.dao;

import co.edu.uptc.negocio.dto.clienteDto;
import java.util.List;

public interface ClienteDAO {
    void guardarClientes(List<clienteDto> clientes, String rutaArchivo);
    List<clienteDto> leerClientes(String rutaArchivo);
}