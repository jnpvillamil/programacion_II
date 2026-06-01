package co.uptc.edu.co.interfaces;

import java.time.LocalDate;
import java.util.List;

import co.uptc.edu.co.modelo.dto.ResumenFormaPagoDTO;
import co.uptc.edu.co.modelo.dto.ResumenProductoDTO;
import co.uptc.edu.co.modelo.dto.ResumenUtilidadBrutaDTO;
import co.uptc.edu.co.modelo.dto.ResumenVentasDTO;

public interface IGestionReporte {

	String generarReporteProductosMasVendidos(LocalDate fechaInicio, LocalDate fechaFin) throws Exception;

	List<ResumenProductoDTO> obtenerResumenProductosMasVendidos(LocalDate fechaInicio, LocalDate fechaFin)
			throws Exception;

	String generarReporteProducto(String codigoProducto, LocalDate fechaInicio, LocalDate fechaFin) throws Exception;

	List<ResumenFormaPagoDTO> obtenerVentasPorFormaPago(LocalDate fechaInicio, LocalDate fechaFin) throws Exception;

	ResumenVentasDTO obtenerTotalVentasDiarias(LocalDate fecha) throws Exception;

	ResumenVentasDTO obtenerTotalVentasMensuales(int mes, int anio) throws Exception;

	ResumenVentasDTO obtenerTotalVentasAnuales(int anio) throws Exception;

	ResumenUtilidadBrutaDTO obtenerUtilidadBruta(LocalDate fechaInicio, LocalDate fechaFin) throws Exception;
}
