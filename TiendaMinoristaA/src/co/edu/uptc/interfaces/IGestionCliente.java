package co.edu.uptc.interfaces;

import java.util.List;

import co.edu.uptc.negocio.dto.clienteDto;

public interface IGestionCliente {
    public void guardar(clienteDto cliente);
    public void actualizar(clienteDto cliente);
    public void eliminar(int codigoCliente);
    public clienteDto buscar(int codigoCliente);
    public List<clienteDto> listar();
}
