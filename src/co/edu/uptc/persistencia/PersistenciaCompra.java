package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Compra;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaCompra implements Repositorio<Compra> {
    private List<Compra> compras;

    public PersistenciaCompra() {
        this.compras = new ArrayList<>();
    }

    @Override
    public void guardar(Compra compra) {
        this.compras.add(compra);
    }

    @Override
    public void eliminar(String id) {
        this.compras.removeIf(c -> c.getNumeroFacturaProveedor().equals(id));
    }

    @Override
    public List<Compra> listar() {
        return new ArrayList<>(this.compras);
    }

    @Override
    public Compra buscarPorId(String id) {
        for (Compra c : this.compras) {
            if (c.getNumeroFacturaProveedor().equals(id)) {
                return c;
            }
        }
        return null;
    }
}