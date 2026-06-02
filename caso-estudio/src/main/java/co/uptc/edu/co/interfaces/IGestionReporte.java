package co.uptc.edu.co.interfaces;

import java.time.LocalDate;
import java.util.List;

import co.uptc.edu.co.modelo.dto.ResumenClienteDTO;
import co.uptc.edu.co.modelo.dto.ResumenContableDTO;
import co.uptc.edu.co.modelo.dto.ResumenFinancieroDiarioDTO;
import co.uptc.edu.co.modelo.dto.ResumenFormaPagoDTO;
import co.uptc.edu.co.modelo.dto.ResumenInventarioValorizadoDTO;
import co.uptc.edu.co.modelo.dto.ResumenProductoDTO;
import co.uptc.edu.co.modelo.dto.ResumenUtilidadBrutaDTO;
import co.uptc.edu.co.modelo.dto.ResumenVentasDTO;

public interface IGestionReporte {

	String generarReporteProductosMasVendidos(LocalDate fechaInicio, LocalDate fechaFin) throws Exception;

	List<ResumenProductoDTO> obtenerResumenProductosMasVendidos(LocalDate fechaInicio, LocalDate fechaFin)
			throws Exception;

	String generarReporteProducto(String codigoProducto, LocalDate fechaInicio, LocalDate fechaFin) throws Exception;

	String generarReporteVentasDiarias(LocalDate fecha) throws Exception;

	String generarReporteVentasMensuales(int mes, int anio) throws Exception;

	String generarReporteVentasAnuales(int anio) throws Exception;

	String generarReporteUtilidadBruta(LocalDate fechaInicio, LocalDate fechaFin) throws Exception;

	String generarReporteVentasFormaPago(LocalDate fechaInicio, LocalDate fechaFin) throws Exception;

	String generarReporteClientesMayorCompra(LocalDate fechaInicio, LocalDate fechaFin) throws Exception;

	String generarReporteInventarioValorizado() throws Exception;

	String generarReporteResumenContable(LocalDate fechaInicio, LocalDate fechaFin) throws Exception;

	String generarReporteResumenFinancieroDiario(LocalDate fecha) throws Exception;

	List<ResumenFormaPagoDTO> obtenerVentasPorFormaPago(LocalDate fechaInicio, LocalDate fechaFin) throws Exception;

	List<ResumenClienteDTO> obtenerClientesMayorVolumenCompra(LocalDate fechaInicio, LocalDate fechaFin)
			throws Exception;

	
	List<ResumenInventarioValorizadoDTO> obtenerInventarioValorizado() throws Exception;

	ResumenContableDTO obtenerResumenContable(LocalDate fechaInicio, LocalDate fechaFin) throws Exception;

	ResumenFinancieroDiarioDTO obtenerResumenFinancieroDiario(LocalDate fecha) throws Exception;

	ResumenVentasDTO obtenerTotalVentasDiarias(LocalDate fecha) throws Exception;

	ResumenVentasDTO obtenerTotalVentasMensuales(int mes, int anio) throws Exception;

	ResumenVentasDTO obtenerTotalVentasAnuales(int anio) throws Exception;

	ResumenUtilidadBrutaDTO obtenerUtilidadBruta(LocalDate fechaInicio, LocalDate fechaFin) throws Exception;
}
