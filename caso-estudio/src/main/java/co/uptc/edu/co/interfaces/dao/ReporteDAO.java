package co.uptc.edu.co.interfaces.dao;

import java.time.LocalDate;
import java.util.List;

import co.uptc.edu.co.modelo.dto.ResumenProductoDTO;

public interface ReporteDAO {

	String guardarReporteProductosMasVendidos(List<ResumenProductoDTO> resumenes, LocalDate fechaInicio,
			LocalDate fechaFin) throws Exception;

	String guardarReporteProducto(ResumenProductoDTO resumen, LocalDate fechaInicio, LocalDate fechaFin)
			throws Exception;
}
