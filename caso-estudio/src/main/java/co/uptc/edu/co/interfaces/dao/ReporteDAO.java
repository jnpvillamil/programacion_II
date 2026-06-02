package co.uptc.edu.co.interfaces.dao;

import java.time.LocalDate;
import java.util.List;

import co.uptc.edu.co.modelo.dto.ResumenClienteDTO;
import co.uptc.edu.co.modelo.dto.ResumenContableDTO;
import co.uptc.edu.co.modelo.dto.ResumenFormaPagoDTO;
import co.uptc.edu.co.modelo.dto.ResumenVentasDTO;
import co.uptc.edu.co.modelo.dto.ResumenInventarioValorizadoDTO;
import co.uptc.edu.co.modelo.dto.ResumenProductoDTO;
import co.uptc.edu.co.modelo.dto.ResumenUtilidadBrutaDTO;

public interface ReporteDAO {

	String guardarReporteProductosMasVendidos(List<ResumenProductoDTO> resumenes, LocalDate fechaInicio,
			LocalDate fechaFin) throws Exception;

	String guardarReporteProducto(ResumenProductoDTO resumen, LocalDate fechaInicio, LocalDate fechaFin)
			throws Exception;

	String guardarReporteVentasDiarias(ResumenVentasDTO resumen, LocalDate fecha) throws Exception;

	String guardarReporteVentasMensuales(ResumenVentasDTO resumen) throws Exception;

	String guardarReporteVentasAnuales(ResumenVentasDTO resumen) throws Exception;

	String guardarReporteUtilidadBruta(ResumenUtilidadBrutaDTO resumen) throws Exception;

	String guardarReporteVentasFormaPago(List<ResumenFormaPagoDTO> resumenes, LocalDate fechaInicio,
			LocalDate fechaFin) throws Exception;

	
	String guardarReporteClientesMayorCompra(List<ResumenClienteDTO> resumenes, LocalDate fechaInicio,
			LocalDate fechaFin) throws Exception;

	String guardarReporteInventarioValorizado(List<ResumenInventarioValorizadoDTO> resumenes) throws Exception;

	String guardarReporteResumenContable(ResumenContableDTO resumen, LocalDate fechaInicio, LocalDate fechaFin)
			throws Exception;

	
}
