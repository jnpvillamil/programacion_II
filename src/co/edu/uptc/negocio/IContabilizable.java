package co.edu.uptc.negocio;
public interface IContabilizable {
    void registrarMovimientoContable(java.util.List<MovimientoContable> libroDiario);
	void generarMovimientoContable();
}