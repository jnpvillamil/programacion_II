package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Venta;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaVenta implements Repositorio<Venta> {
    private List<Venta> ventas;

    public PersistenciaVenta() {
        this.ventas = new ArrayList<>();
    }

    @Override
    public void guardar(Venta venta) {
        this.ventas.add(venta);
    }

    @Override
    public void eliminar(String id) {
        this.ventas.removeIf(v -> v.getNumeroFactura().equals(id));
    }

    @Override
    public List<Venta> listar() {
        return new ArrayList<>(this.ventas);
    }

    @Override
    public Venta buscarPorId(String id) {
        for (Venta v : this.ventas) {
            if (v.getNumeroFactura().equals(id)) {
                return v;
            }
        }
        return null;
    }
}