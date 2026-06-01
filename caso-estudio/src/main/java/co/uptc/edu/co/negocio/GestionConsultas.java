package co.uptc.edu.co.negocio;

import java.time.LocalDate;
import java.util.List;

import co.uptc.edu.co.interfaces.IGestionConsultas;
import co.uptc.edu.co.interfaces.dao.VentaDAO;
import co.uptc.edu.co.modelo.Venta;

public class GestionConsultas implements IGestionConsultas {

	private final VentaDAO ventaDAO;

	public GestionConsultas(VentaDAO ventaDAO) {
		if (ventaDAO == null) {
			throw new IllegalArgumentException("La ventaDAO no puede ser nula.");
		}

		this.ventaDAO = ventaDAO;
	}

	@Override
	public List<Venta> obtenerVentasPorFecha(LocalDate fecha) throws Exception {
		if (fecha == null) {
			throw new Exception("La fecha es obligatoria.");
		}

		return ventaDAO.listarVentasPorFecha(fecha);
	}
}
