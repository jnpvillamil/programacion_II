package co.uptc.edu.tienda.negocio;

import java.util.List;

import co.uptc.edu.tienda.interfaces.IGestionProducto;
import co.uptc.edu.tienda.modelo.Producto;

public class GestionProducto {

    private final IGestionProducto gestionP;

    public GestionProducto(IGestionProducto gestionP) {
        super();
        this.gestionP = gestionP;
    }

    public void guardar(Producto nuevo) {

        if (nuevo.getNombreProducto().isEmpty()) {
            throw new IllegalArgumentException("Nombre obligatorio");
        }

        if (nuevo.getPrecioCompra() <= 0 || nuevo.getPrecioVenta() <= 0) {
            throw new IllegalArgumentException("Precios deben ser mayores a 0");
        }

        if (nuevo.getStockActual() < 0) {
            throw new IllegalArgumentException("Stock no puede ser negativo");
        }

        List<Producto> actuales = gestionP.listar();

        int maxId = 99;

        if (actuales.size() > 0) {

            for (int i = 0; i < actuales.size(); i++) {

                if (actuales.get(i).getCodigoProducto() > maxId) {

                    maxId = actuales.get(i).getCodigoProducto();
                }
            }
        }

        int idReal = maxId + 1;

        nuevo.setCodigoProducto(idReal);

        actuales.add(nuevo);

        gestionP.guardar(actuales);
    }

    public void actualizar(Producto producto) {
        gestionP.actualizar(producto);
    }

    public void eliminar(int codigoProducto) {
        gestionP.eliminar(codigoProducto);
    }

    public Producto buscar(int codigoProducto) {
        return gestionP.buscar(codigoProducto);
    }

    public List<Producto> listar() {
        return gestionP.listar();
    }

    public void inactivar(int codigoProducto) {
    	
    	gestionP.eliminar(codigoProducto);

    }

    public void activar(int codigoProducto) {

        gestionP.cambiarEstado(codigoProducto);
    }
}