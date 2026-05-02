package co.edu.uptc.persistencia;

import java.util.ArrayList;
import java.util.List;
import co.edu.uptc.interfaces.IGestionProveedor;
import co.edu.uptc.negocio.dto.proveedorDto;

public class LocalProveedor implements IGestionProveedor {

    private List<proveedorDto> listaProveedores;

    public LocalProveedor() {
        this.listaProveedores = new ArrayList<>();
    }

    @Override
    public void guardar(proveedorDto proveedor) {
        listaProveedores.add(proveedor);
    }

    @Override
    public void actualizar(proveedorDto proveedor) {
        for (int i = 0; i < listaProveedores.size(); i++) {
            if (listaProveedores.get(i).getCodigoProveedor() == proveedor.getCodigoProveedor()) {
                listaProveedores.set(i, proveedor);
                return;
            }
        }
    }

    @Override
    public void eliminar(int codigoProveedor) {
        listaProveedores.removeIf(p -> p.getCodigoProveedor() == codigoProveedor);
    }

    @Override
    public proveedorDto buscar(int codigoProveedor) {
        for (proveedorDto p : listaProveedores) {
            if (p.getCodigoProveedor() == codigoProveedor) return p;
        }
        return null;
    }

    @Override
    public List<proveedorDto> listar() {
        return listaProveedores;
    }
}
