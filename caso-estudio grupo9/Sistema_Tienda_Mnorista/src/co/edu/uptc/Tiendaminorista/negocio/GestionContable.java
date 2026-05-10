package co.edu.uptc.Tiendaminorista.negocio;

import java.time.LocalDate;
import java.util.List;

import co.edu.uptc.Tiendaminorista.interfaces.IGestionContable;
import co.edu.uptc.Tiendaminorista.modelo.MovimientoContable;

public class GestionContable {

    private IGestionContable movimientos;

    public GestionContable(IGestionContable movimientos) {
        super();
        this.movimientos = movimientos;
    }

    public void registrarIngreso(String producto, double valor, String documentoRelacionado) {
        MovimientoContable m = new MovimientoContable();
        m.setFecha(LocalDate.now());
        m.setTipo("Ingreso");
        m.setCuentaContable("Ingreso por venta");
        m.setDebito(0);
        m.setCredito(valor);
        m.setDescripcion("Venta de " + producto);
        m.setDocumentoRelacionado(documentoRelacionado);
        movimientos.guardar(m);
    }

    public void registrarEgreso(String producto, double valor, String documentoRelacionado) {
        MovimientoContable m = new MovimientoContable();
        m.setFecha(LocalDate.now());
        m.setTipo("Egreso");
        m.setCuentaContable("Compra de mercancía");
        m.setDebito(valor);
        m.setCredito(0);
        m.setDescripcion("Compra de " + producto);
        m.setDocumentoRelacionado(documentoRelacionado);
        movimientos.guardar(m);
    }

    public List<MovimientoContable> listarMovimientos() {
        return movimientos.listar();
    }

    public List<MovimientoContable> filtrarPorTipo(String tipo) {
        return movimientos.filtrarPorTipo(tipo);
    }

    public List<MovimientoContable> filtrarPorRangoFechas(LocalDate desde, LocalDate hasta) {
        return movimientos.filtrarPorRangoFechas(desde, hasta);
    }

    public List<MovimientoContable> buscarPorCuenta(String cuentaContable) {
        return movimientos.buscarPorCuenta(cuentaContable);
    }

    public double getTotalIngresos() {
        return movimientos.getTotalIngresos();
    }

    public double getTotalEgresos() {
        return movimientos.getTotalEgresos();
    }

    public double getSaldoActual() {
        return movimientos.getSaldoActual();
    }
}