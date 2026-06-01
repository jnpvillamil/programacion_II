package co.uptc.edu.co.negocio.factura;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormateadorFactura {

	static final String SALTO_LINEA = System.lineSeparator();
	static final String LINEA = "================================================================================";
	static final String LINEA_CORTA = "--------------------------------------------------------------------------------";

	private static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final DecimalFormat FORMATO_MONEDA = crearFormatoMoneda();

	private static DecimalFormat crearFormatoMoneda() {
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setGroupingSeparator('.');
		simbolos.setDecimalSeparator(',');

		DecimalFormat formato = new DecimalFormat("$ #,##0", simbolos);
		formato.setGroupingUsed(true);
		return formato;
	}

	String construirEncabezado() {
		StringBuilder contenido = new StringBuilder();
		contenido.append(LINEA).append(SALTO_LINEA);
		contenido.append(centrarTexto("TIENDA MINORISTA UPTC")).append(SALTO_LINEA);
		contenido.append(centrarTexto("NIT: 803.153.436-7")).append(SALTO_LINEA);
		contenido.append(centrarTexto("Calle 5 # 8-9, Bogota - Colombia")).append(SALTO_LINEA);
		contenido.append(centrarTexto("Tel: 601-1234567")).append(SALTO_LINEA);
		contenido.append(centrarTexto("Sistema de Gestion Contable y Comercial")).append(SALTO_LINEA);
		contenido.append(LINEA).append(SALTO_LINEA);
		return contenido.toString();
	}

	String construirTitulo(String titulo) {
		return centrarTexto(titulo) + SALTO_LINEA + LINEA + SALTO_LINEA;
	}

	String formatearMoneda(double valor) {
		return FORMATO_MONEDA.format(valor);
	}

	String formatearFechaHora(LocalDateTime fechaHora) {
		if (fechaHora == null) {
			return "";
		}
		return fechaHora.format(FORMATO_FECHA_HORA);
	}

	String formatearFecha(LocalDate fecha) {
		if (fecha == null) {
			return "";
		}
		return fecha.format(FORMATO_FECHA);
	}

	String valorSeguro(Object valor) {
		return valor != null ? valor.toString() : "";
	}

	String limitarTexto(String texto, int longitudMaxima) {
		if (texto == null) {
			return "";
		}
		if (texto.length() <= longitudMaxima) {
			return texto;
		}
		return texto.substring(0, longitudMaxima - 3) + "...";
	}

	String centrarTexto(String texto) {
		if (texto == null || texto.length() >= LINEA.length()) {
			return valorSeguro(texto);
		}

		int espaciosIzquierda = (LINEA.length() - texto.length()) / 2;
		return " ".repeat(espaciosIzquierda) + texto;
	}
}
