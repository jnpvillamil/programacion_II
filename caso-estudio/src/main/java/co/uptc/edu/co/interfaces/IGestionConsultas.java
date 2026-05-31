package co.uptc.edu.co.interfaces;

import java.time.LocalDate;
import java.util.List;

import co.uptc.edu.co.modelo.Venta;

public interface IGestionConsultas {

	List<Venta> obtenerVentasPorFecha(LocalDate fecha) throws Exception;
}
