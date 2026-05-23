package co.edu.uptc.persistencia;

import java.util.ArrayList;
import java.util.List;
import co.edu.uptc.caso.estudio.dao.MovimientoContableDAOImpl;
import co.edu.uptc.interfaces.IGestionMovimientoContable;
import co.edu.uptc.negocio.dto.movimientoContableDto;

public class LocalMovimientoContable implements IGestionMovimientoContable {

    private List<movimientoContableDto> listaMovimientos = new ArrayList<>();
    private MovimientoContableDAOImpl dao= new MovimientoContableDAOImpl();
    private String ruta="movimientos_contables.json";

    @Override
    public void guardar(movimientoContableDto movimiento) {
        listaMovimientos.add(movimiento);
        dao.guardarMovimientos(listaMovimientos, ruta);
    }

    @Override
    public movimientoContableDto buscar(int codigoTransaccion) {
        for (movimientoContableDto m : listaMovimientos)
            if (m.getCodigoTransaccion() == codigoTransaccion) return m;
        return null;
    }

    @Override
    public List<movimientoContableDto> listar() {
        return listaMovimientos;
    }
}