package co.uptc.edu.co.interfaces;

import java.time.LocalDate;
import java.util.List;

import co.uptc.edu.co.modelo.ResumenProductoDTO;

public interface IGestionReporte {

	String generarReporteProductosMasVendidos(LocalDate fechaInicio, LocalDate fechaFin) throws Exception;

	List<ResumenProductoDTO> obtenerResumenProductosMasVendidos(LocalDate fechaInicio, LocalDate fechaFin)
			throws Exception;

	String generarReporteProducto(String codigoProducto, LocalDate fechaInicio, LocalDate fechaFin) throws Exception;
}
