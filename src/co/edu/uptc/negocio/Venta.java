package co.edu.uptc.negocio;

import java.util.Date;
import java.util.List;

public class Venta extends Transaccion {
    private Cliente cliente; private String formaPago;
    public Venta(String factura, Date fecha, Cliente c, String pago) { super(factura, fecha); this.cliente = c; this.formaPago = pago; }
    
    @Override
    public void generarMovimientoContable() {
        System.out.println("Generando asiento contable por Venta: " + numeroFactura);
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