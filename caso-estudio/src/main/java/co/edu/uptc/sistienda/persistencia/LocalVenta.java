package co.edu.uptc.sistienda.persistencia;
import java.util.ArrayList;
import java.util.List;
import co.edu.uptc.sistienda.interfaces.IGestionVenta;
import co.edu.uptc.sistienda.modelo.Venta;
public class LocalVenta implements IGestionVenta {
	private List<Venta> ventas = new ArrayList<>();
	
	@Override
	public void guardarVenta(Venta venta) {
		ventas.add(venta);
	}
	@Override
	public void anularVenta(String numeroFactura) {
		for(Venta v : ventas) {
			if(v.getNumeroFactura().equals(numeroFactura)) {
				v.setAnulada(true);
				return;
			}
		}
	}
	@Override
	public Venta buscarVentaPorNumeroFactura(String numeroFactura) {
		for(Venta v : ventas) {
			if(v.getNumeroFactura().equals(numeroFactura)) {
				return v;
			}
		}
		return null;
	}
	@Override
	public List<Venta> obtenerListaVentas() {
		return new ArrayList<>(ventas);
	}
	
}
