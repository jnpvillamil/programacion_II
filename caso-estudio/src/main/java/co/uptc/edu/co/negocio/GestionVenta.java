package co.uptc.edu.co.negocio;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import co.uptc.edu.co.conexion.TransaccionBD;
import co.uptc.edu.co.interfaces.IGestionContabilidad;
import co.uptc.edu.co.interfaces.IGestionVenta;
import co.uptc.edu.co.interfaces.IGestionInventario;
import co.uptc.edu.co.interfaces.VentaDAO;
import co.uptc.edu.co.modelo.DetalleVenta;
import co.uptc.edu.co.modelo.ResumenProductoDTO;
import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.modelo.enums.EstadoVentaEnum;

public class GestionVenta implements IGestionVenta {

	private static final String PREFIJO_FACTURA = "factura_";
	private static final int DIGITOS_FACTURA = 6;
	private static final String CARPETA_REPORTES = "reportes";
	private static final DateTimeFormatter FORMATO_ARCHIVO = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

	private List<Venta> ventas;
	private final VentaDAO ventaDAO;
	private final IGestionInventario gestionInventario;
	private final IGestionContabilidad gestionContabilidad;

	public GestionVenta(VentaDAO ventaDAO, IGestionInventario gestionInventario,
			IGestionContabilidad gestionContabilidad) {

		if (ventaDAO == null) {

			throw new IllegalArgumentException("La ventaDAO no puede ser nulo");
		}
		if (gestionInventario == null) {
			throw new IllegalArgumentException("La gestionInventario no puede ser nula");
		}
		if (gestionContabilidad == null) {
			throw new IllegalArgumentException("La gestionContabilidad no puede ser nula");
		}
		this.ventaDAO = ventaDAO;
		this.gestionInventario = gestionInventario;
		this.gestionContabilidad = gestionContabilidad;

		try {
			ventas = ventaDAO.listarVentas();
		} catch (Exception e) {
			ventas = new ArrayList<>();
			System.out.println("Error al cargar ventas: " + e.getMessage());
		}

	}

	private void recargarVentas() throws Exception {
		ventas = ventaDAO.listarVentas();
	}

	@Override
	public void recargar() throws Exception {
		recargarVentas();
	}

	@Override
	public void registrarVenta(Venta venta) throws Exception {
		validarVenta(venta);
		validarFacturaNoRegistrada(venta.getNumeroFactura());

		if (venta.getFechaHora() == null) {
			venta.setFechaHora(LocalDateTime.now());
		}

		if (venta.getEstado() == null) {
			venta.setEstado(EstadoVentaEnum.ACTIVA);
		}

		calcularTotales(venta);

		TransaccionBD.ejecutar(conexion -> {
			ventaDAO.guardarVenta(conexion, venta);
			gestionInventario.registrarSalidaPorVenta(conexion, venta);
			gestionContabilidad.registrarIngresoPorVenta(conexion, venta);
		});

		ventas.add(0, venta);
	}

	@Override
	public List<Venta> obtenerVentas() {
		return new ArrayList<>(ventas);
	}

	@Override
	public List<Venta> obtenerVentasPorFecha(LocalDate fecha) throws Exception {
		if (fecha == null) {
			throw new Exception("La fecha es obligatoria.");
		}

		return ventaDAO.listarVentasPorFecha(fecha);
	}

	@Override
	public Venta buscarVentaPorNumero(String numeroFactura) throws Exception {
		if (numeroFactura == null) {
			return null;
		}

		return ventaDAO.buscarVentaPorNumero(numeroFactura);
	}

	private void validarFacturaNoRegistrada(String numeroFactura) throws Exception {
		for (Venta ventaRegistrada : ventas) {
			if (ventaRegistrada.getNumeroFactura() != null
					&& ventaRegistrada.getNumeroFactura().equalsIgnoreCase(numeroFactura)) {
				throw new Exception("Ya existe una venta con ese numero de factura.");
			}
		}

		if (ventaDAO.buscarVentaPorNumero(numeroFactura) != null) {
			throw new Exception("Ya existe una venta con ese numero de factura.");
		}
	}

	private void validarVenta(Venta venta) throws Exception {

		if (venta == null) {
			throw new Exception("La venta no puede ser nula");
		}

		if (venta.getNumeroFactura() == null || venta.getNumeroFactura().trim().isEmpty()) {

			throw new Exception("El numero de factura es obligatorio");
		}

		if (venta.getCodigoCliente() == null || venta.getCodigoCliente().trim().isEmpty()) {
			throw new Exception("El codigo del cliente es obligatorio");
		}

		if (venta.getCliente() == null || venta.getCliente().trim().isEmpty()) {
			throw new Exception("El cliente es obligatorio");
		}

		if (venta.getFormaPago() == null) {
		    throw new Exception("La forma de pago es obligatoria");
		}
		
		if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
			throw new Exception("La venta debe tener almenos un producto");
		}

		for (DetalleVenta detalle : venta.getDetalles()) {
			validarDetalleVenta(detalle);
		}
	}

	private void validarDetalleVenta(DetalleVenta detalle) throws Exception {

		if (detalle == null) {
			throw new Exception("El detalle de venta no puede ser nulo");
		}

		if (detalle.getProducto() == null) {
			throw new Exception("Cada detalle debe tener un producto");
		}

		if (detalle.getCantidad() <= 0) {
			throw new Exception("La cantidad vendida debe ser mayor a cero.");
		}
		if (detalle.getPrecioUnitario() <= 0) {
			throw new Exception("El precio unitario debe ser mayor a cero.");
		}

		if (detalle.getSubtotal() < 0) {
			throw new Exception("El subtotal del detalle no puede ser negativo");
		}
	}

	private void calcularTotales(Venta venta) {
		double subtotal = 0;
		double impuestos = 0;

		for (DetalleVenta detalle : venta.getDetalles()) {
			double subtotalDetalle = detalle.getCantidad() * detalle.getPrecioUnitario();
			detalle.setSubtotal(subtotalDetalle);
			subtotal += subtotalDetalle;

			if (detalle.getProducto() != null && detalle.getProducto().isAplicaIva()) {
				impuestos += subtotalDetalle * 0.19;
			}
		}

		double total = subtotal + impuestos;

		venta.setSubTotal(subtotal);
		venta.setImpuestos(impuestos);
		venta.setTotal(total);

	}

	@Override
	public void anularVenta(String numeroFactura, String motivo) throws Exception {
		Venta venta = buscarVentaPorNumero(numeroFactura);
		if (venta == null) {
			throw new Exception("No se encontro la venta a anular.");
		}

		if (venta.getEstado() == EstadoVentaEnum.ANULADA) {
			throw new Exception("La venta ya esta anulada.");
		}

		if (venta.getEstado() == EstadoVentaEnum.DEVUELTA
				|| venta.getEstado() == EstadoVentaEnum.PARCIALMENTE_DEVUELTA) {
			throw new Exception("No se puede anular una venta que ya tiene devolucion registrada.");
		}

		if (motivo == null || motivo.trim().isEmpty()) {
			throw new Exception("Debe ingresar un motivo de anulacion");
		}

		venta.setEstado(EstadoVentaEnum.ANULADA);
		venta.setMotivoAnulacion(motivo.trim());
		venta.setFechaAnulacion(LocalDateTime.now());
		TransaccionBD.ejecutar(conexion -> {
			ventaDAO.actualizarVenta(conexion, venta);
			gestionInventario.registrarEntradaPorAnulacion(conexion, venta, motivo.trim());
			gestionContabilidad.registrarReversoPorAnulacionVenta(conexion, venta, motivo.trim());
		});
		actualizarVentaEnMemoria(venta);
	}

	@Override
	public String generarReporteProductosMasVendidos(LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
		List<Venta> ventasActuales = ventaDAO.listarVentas();
		List<ResumenProducto> resumenProductos = construirResumenProductos(ventasActuales, fechaInicio, fechaFin);
		return guardarReporteProductosMasVendidos(resumenProductos, fechaInicio, fechaFin);
	}

	@Override
	public List<ResumenProductoDTO> obtenerResumenProductosMasVendidos(LocalDate fechaInicio, LocalDate fechaFin)
			throws Exception {
		List<Venta> ventasActuales = ventaDAO.listarVentas();
		List<ResumenProducto> resumenes = construirResumenProductos(ventasActuales, fechaInicio, fechaFin);
		List<ResumenProductoDTO> dtos = new ArrayList<>();
		for (ResumenProducto r : resumenes) {
			dtos.add(new ResumenProductoDTO(r.getCodigoProducto(), r.getNombreProducto(), r.getCantidadVendida()));
		}
		return dtos;
	}

	@Override
	public String generarReporteProducto(String codigoProducto, LocalDate fechaInicio, LocalDate fechaFin)
			throws Exception {
		if (codigoProducto == null || codigoProducto.isBlank()) {
			throw new Exception("Debe seleccionar un codigo de producto valido para generar el reporte.");
		}

		List<Venta> ventasActuales = ventaDAO.listarVentas();
		List<ResumenProducto> resumenes = construirResumenProductos(ventasActuales, fechaInicio, fechaFin);
		ResumenProducto encontrado = null;
		for (ResumenProducto r : resumenes) {
			if (r.getCodigoProducto() != null && r.getCodigoProducto().equalsIgnoreCase(codigoProducto)) {
				encontrado = r;
				break;
			}
		}

		if (encontrado == null) {
			throw new Exception("No se encontro datos de ventas para el producto seleccionado dentro del rango.");
		}

		File carpeta = new File(CARPETA_REPORTES);
		if (!carpeta.exists() && !carpeta.mkdirs()) {
			throw new Exception("No se pudo crear la carpeta de reportes.");
		}

		String nombreArchivo = "producto_" + encontrado.getCodigoProducto() + "_"
				+ LocalDateTime.now().format(FORMATO_ARCHIVO) + ".json";
		File archivo = new File(carpeta, nombreArchivo);

		JsonObject raiz = new JsonObject();
		raiz.addProperty("tipo_reporte", "producto_mas_vendido");
		raiz.addProperty("generado_en", LocalDateTime.now().toString());
		raiz.addProperty("fecha_inicio", fechaInicio != null ? fechaInicio.toString() : null);
		raiz.addProperty("fecha_fin", fechaFin != null ? fechaFin.toString() : null);
		raiz.addProperty("codigo_producto", encontrado.getCodigoProducto());
		raiz.addProperty("nombre_producto", encontrado.getNombreProducto());
		raiz.addProperty("cantidad_vendida", encontrado.getCantidadVendida());

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
			writer.print(gson.toJson(raiz));
		}

		return archivo.getPath();
	}

	private List<ResumenProducto> construirResumenProductos(List<Venta> ventasFuente, LocalDate fechaInicio,
			LocalDate fechaFin) {
		Map<String, ResumenProducto> resumenPorProducto = new LinkedHashMap<>();

		for (Venta venta : ventasFuente) {
			if (!debeIncluirVentaEnReporte(venta, fechaInicio, fechaFin) || venta.getDetalles() == null) {
				continue;
			}

			for (DetalleVenta detalle : venta.getDetalles()) {
				if (detalle == null || detalle.getProducto() == null) {
					continue;
				}

				String codigoProducto = detalle.getProducto().getCodigoProducto();
				if (codigoProducto == null || codigoProducto.isBlank()) {
					codigoProducto = "SIN_CODIGO";
				}

				String nombreProducto = detalle.getProducto().getNombreProducto();
				if (nombreProducto == null || nombreProducto.isBlank()) {
					nombreProducto = "Producto sin nombre";
				}

				final String codigoProductoFinal = codigoProducto;
				final String nombreProductoFinal = nombreProducto;
				ResumenProducto resumen = resumenPorProducto.computeIfAbsent(codigoProducto,
						clave -> new ResumenProducto(codigoProductoFinal, nombreProductoFinal));
				resumen.sumarCantidad(detalle.getCantidad());
			}
		}

		List<ResumenProducto> resumenes = new ArrayList<>(resumenPorProducto.values());
		resumenes.sort(Comparator.comparingInt(ResumenProducto::getCantidadVendida).reversed()
				.thenComparing(ResumenProducto::getNombreProducto, String.CASE_INSENSITIVE_ORDER));
		return resumenes;
	}

	private boolean debeIncluirVentaEnReporte(Venta venta, LocalDate fechaInicio, LocalDate fechaFin) {
		if (venta == null || venta.getEstado() == EstadoVentaEnum.ANULADA || venta.getFechaHora() == null) {
			return false;
		}

		LocalDate fechaVenta = venta.getFechaHora().toLocalDate();
		if (fechaInicio != null && fechaVenta.isBefore(fechaInicio)) {
			return false;
		}
		if (fechaFin != null && fechaVenta.isAfter(fechaFin)) {
			return false;
		}

		return true;
	}

	private String guardarReporteProductosMasVendidos(List<ResumenProducto> resumenes, LocalDate fechaInicio,
			LocalDate fechaFin) throws Exception {
		File carpeta = new File(CARPETA_REPORTES);
		if (!carpeta.exists() && !carpeta.mkdirs()) {
			throw new Exception("No se pudo crear la carpeta de reportes.");
		}

		String nombreArchivo = "productos_mas_vendidos_" + LocalDateTime.now().format(FORMATO_ARCHIVO) + ".json";
		File archivo = new File(carpeta, nombreArchivo);

		JsonObject raiz = new JsonObject();
		raiz.addProperty("tipo_reporte", "productos_mas_vendidos");
		raiz.addProperty("generado_en", LocalDateTime.now().toString());
		raiz.addProperty("fecha_inicio", fechaInicio != null ? fechaInicio.toString() : null);
		raiz.addProperty("fecha_fin", fechaFin != null ? fechaFin.toString() : null);
		raiz.addProperty("total_productos", resumenes.size());

		JsonArray productos = new JsonArray();
		for (ResumenProducto resumen : resumenes) {
			JsonObject item = new JsonObject();
			item.addProperty("codigo_producto", resumen.getCodigoProducto());
			item.addProperty("nombre_producto", resumen.getNombreProducto());
			item.addProperty("cantidad_vendida", resumen.getCantidadVendida());
			productos.add(item);
		}
		raiz.add("productos", productos);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
			writer.print(gson.toJson(raiz));
		}

		return archivo.getPath();
	}

	private static final class ResumenProducto {
		private final String codigoProducto;
		private final String nombreProducto;
		private int cantidadVendida;

		private ResumenProducto(String codigoProducto, String nombreProducto) {
			this.codigoProducto = codigoProducto;
			this.nombreProducto = nombreProducto;
		}

		private void sumarCantidad(int cantidad) {
			this.cantidadVendida += cantidad;
		}

		private String getCodigoProducto() {
			return codigoProducto;
		}

		private String getNombreProducto() {
			return nombreProducto;
		}

		private int getCantidadVendida() {
			return cantidadVendida;
		}
	}

	private void actualizarVentaEnMemoria(Venta ventaActualizada) {
		for (int i = 0; i < ventas.size(); i++) {
			Venta venta = ventas.get(i);

			if (venta.getNumeroFactura() != null
					&& venta.getNumeroFactura().equalsIgnoreCase(ventaActualizada.getNumeroFactura())) {
				ventas.set(i, ventaActualizada);
				return;
			}
		}
	}

	@Override
	public String generarNumeroFactura() {
		return PREFIJO_FACTURA + String.format("%0" + DIGITOS_FACTURA + "d", obtenerSiguienteConsecutivoFactura());
	}

	private int obtenerSiguienteConsecutivoFactura() {
		int mayorConsecutivo = 0;

		for (Venta venta : ventas) {
			String numeroFactura = venta.getNumeroFactura();

			if (numeroFactura != null && numeroFactura.startsWith(PREFIJO_FACTURA)) {
				String consecutivo = numeroFactura.substring(PREFIJO_FACTURA.length());

				if (consecutivo.matches("\\d+")) {
					mayorConsecutivo = Math.max(mayorConsecutivo, Integer.parseInt(consecutivo));
				}
			}
		}

		return mayorConsecutivo + 1;
	}

}
