package co.edu.uptc.negocio;
import java.util.Date;
import java.util.List;

public class Compra extends Transaccion {
    private Proveedor proveedor;
    public Compra(String factura, Date fecha, Proveedor p) { super(factura, fecha); this.proveedor = p; }
    
    @Override
    public void generarMovimientoContable() {
        // En una app real, aquí se envía el evento al GestorContable
        System.out.println("Generando asiento contable por Compra: " + numeroFactura);
    }

	@Override
	public void registrarMovimientoContable(List<MovimientoContable> libroDiario) {
		// TODO Auto-generated method stub
		
	}

	public Object getNumeroFactura() {
		// TODO Auto-generated method stub
		return null;
	}
}