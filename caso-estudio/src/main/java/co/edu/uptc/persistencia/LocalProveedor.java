package co.edu.uptc.persistencia;

import java.util.ArrayList;
import java.util.List;
import co.edu.uptc.caso.estudio.dao.ProveedorDAOImpl;
import co.edu.uptc.interfaces.IGestionProveedor;
import co.edu.uptc.negocio.dto.proveedorDto;

public class LocalProveedor implements IGestionProveedor {

    private List<proveedorDto> listaProveedores = new ArrayList<>();
    private ProveedorDAOImpl dao = new ProveedorDAOImpl();
    private String ruta = "proveedores.json";

    @Override
    public void guardar(proveedorDto proveedor) {
        listaProveedores.add(proveedor);
        dao.guardarProveedores(listaProveedores, ruta);
    }

    @Override
    public void actualizar(proveedorDto proveedor) {
        for (int i = 0; i < listaProveedores.size(); i++) {
            if (listaProveedores.get(i).getCodigoProveedor() == proveedor.getCodigoProveedor()) {
                listaProveedores.set(i, proveedor);
            }
        }
        dao.guardarProveedores(listaProveedores, ruta);
    }

    @Override
    public void eliminar(int codigo) {
        listaProveedores.removeIf(p -> p.getCodigoProveedor() == codigo);
        dao.guardarProveedores(listaProveedores, ruta);
    }

    @Override
    public proveedorDto buscar(int codigo) {
        for (proveedorDto p : listaProveedores)
            if (p.getCodigoProveedor() == codigo) return p;
        return null;
    }

    @Override
    public List<proveedorDto> listar() {
        return listaProveedores;
    }
}