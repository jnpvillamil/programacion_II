package co.edu.uptc.negocio;

import co.edu.uptc.enums.TipoMovimiento;
import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.MovimientoContable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class GestionContable {
    private Repositorio<MovimientoContable> persistenciaContable;

    public GestionContable(Repositorio<MovimientoContable> persistenciaContable) {
        this.persistenciaContable = persistenciaContable;
    }

    public void registrarPartidaDoble(double valor, TipoMovimiento tipo, String cuentaContable, String descripcion) {
        String codigoTransaccion = UUID.randomUUID().toString();
        MovimientoContable movimiento = new MovimientoContable(
            codigoTransaccion, 
            LocalDateTime.now(), 
            tipo, 
            cuentaContable, 
            valor, 
            descripcion
        );
        this.persistenciaContable.guardar(movimiento);
    }

    public List<MovimientoContable> listarMovimientos() {
        return this.persistenciaContable.listar();
    }
}