package co.edu.uptc.interfaces;

import java.util.List;

import co.edu.uptc.negocio.dto.proveedorDto;

public interface IGestionProveedor {
    public void guardar(proveedorDto proveedor);
    public void actualizar(proveedorDto proveedor);
    public void eliminar(int codigoProveedor);
    public proveedorDto buscar(int codigoProveedor);
    public List<proveedorDto> listar();
}
