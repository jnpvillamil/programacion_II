package co.uptc.edu.co.persistencia.archivo;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import co.uptc.edu.co.interfaces.dao.ReporteDAO;
import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.modelo.dto.DetalleUtilidadBrutaDTO;
import co.uptc.edu.co.modelo.dto.ResumenClienteDTO;
import co.uptc.edu.co.modelo.dto.ResumenContableDTO;
import co.uptc.edu.co.modelo.dto.ResumenFinancieroDiarioDTO;
import co.uptc.edu.co.modelo.dto.ResumenFormaPagoDTO;
import co.uptc.edu.co.modelo.dto.ResumenInventarioValorizadoDTO;
import co.uptc.edu.co.modelo.dto.ResumenProductoDTO;
import co.uptc.edu.co.modelo.dto.ResumenUtilidadBrutaDTO;
import co.uptc.edu.co.modelo.dto.ResumenVentasDTO;

public class ReporteJSONDAO implements ReporteDAO {

	private static final String CARPETA_REPORTES = "reportes";
	private static final DateTimeFormatter FORMATO_ARCHIVO = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a",
			Locale.US);

	@Override
	public String guardarReporteProductosMasVendidos(List<ResumenProductoDTO> resumenes, LocalDate fechaInicio,
			LocalDate fechaFin) throws Exception {
		File archivo = crearArchivo("productos_mas_vendidos_" + LocalDateTime.now().format(FORMATO_ARCHIVO) + ".json");

		JsonObject raiz = new JsonObject();
		raiz.addProperty("tipo_reporte", "productos_mas_vendidos");
		raiz.addProperty("generado_en", formatearFechaHora(LocalDateTime.now()));
		raiz.addProperty("fecha_inicio", formatearFecha(fechaInicio));
		raiz.addProperty("fecha_fin", formatearFecha(fechaFin));
		raiz.addProperty("total_productos", resumenes != null ? resumenes.size() : 0);

		JsonArray productos = new JsonArray();
		if (resumenes != null) {
			for (ResumenProductoDTO resumen : resumenes) {
				JsonObject item = new JsonObject();
				item.addProperty("codigo_producto", resumen.getCodigoProducto());
				item.addProperty("nombre_producto", resumen.getNombreProducto());
				item.addProperty("cantidad_vendida", resumen.getCantidadVendida());
				item.addProperty("total_vendido", resumen.getTotalVendido());
				productos.add(item);
			}
		}
		raiz.add("productos", productos);

		
		escribirJson(archivo, raiz);
		return archivo.getPath();
	}

	@Override
	public String guardarReporteProducto(ResumenProductoDTO resumen, LocalDate fechaInicio, LocalDate fechaFin)
			throws Exception {
		if (resumen == null) {
			throw new Exception("No hay datos del producto para guardar el reporte.");
		}

		String nombreArchivo = "producto_" + resumen.getCodigoProducto() + "_"
				+ LocalDateTime.now().format(FORMATO_ARCHIVO) + ".json";
		File archivo = crearArchivo(nombreArchivo);

		JsonObject raiz = new JsonObject();
		raiz.addProperty("tipo_reporte", "producto_mas_vendido");
		raiz.addProperty("generado_en", formatearFechaHora(LocalDateTime.now()));
		raiz.addProperty("fecha_inicio", formatearFecha(fechaInicio));
		raiz.addProperty("fecha_fin", formatearFecha(fechaFin));
		raiz.addProperty("codigo_producto", resumen.getCodigoProducto());
		raiz.addProperty("nombre_producto", resumen.getNombreProducto());
		raiz.addProperty("cantidad_vendida", resumen.getCantidadVendida());
		raiz.addProperty("total_vendido", resumen.getTotalVendido());

		escribirJson(archivo, raiz);
		return archivo.getPath();
	}

	@Override
	public String guardarReporteVentasDiarias(ResumenVentasDTO resumen, LocalDate fecha) throws Exception {
		if (resumen == null) {
			throw new Exception("No hay datos de ventas diarias para guardar el reporte.");
		}

		File archivo = crearArchivo("ventas_diarias_" + LocalDateTime.now().format(FORMATO_ARCHIVO) + ".json");
		JsonObject raiz = construirReporteVentas("ventas_diarias", resumen);
		raiz.addProperty("fecha", formatearFecha(fecha));

		escribirJson(archivo, raiz);
		return archivo.getPath();
	}

	@Override
	public String guardarReporteVentasMensuales(ResumenVentasDTO resumen) throws Exception {
		if (resumen == null) {
			throw new Exception("No hay datos de ventas mensuales para guardar el reporte.");
		}

		File archivo = crearArchivo("ventas_mensuales_" + LocalDateTime.now().format(FORMATO_ARCHIVO) + ".json");
		JsonObject raiz = construirReporteVentas("ventas_mensuales", resumen);

		escribirJson(archivo, raiz);
		return archivo.getPath();
	}

	@Override
	public String guardarReporteVentasAnuales(ResumenVentasDTO resumen) throws Exception {
		if (resumen == null) {
			throw new Exception("No hay datos de ventas anuales para guardar el reporte.");
		}

		File archivo = crearArchivo("ventas_anuales_" + LocalDateTime.now().format(FORMATO_ARCHIVO) + ".json");
		JsonObject raiz = construirReporteVentas("ventas_anuales", resumen);

		escribirJson(archivo, raiz);
		return archivo.getPath();
	}

	private JsonObject construirReporteVentas(String tipoReporte, ResumenVentasDTO resumen) {
		JsonObject raiz = new JsonObject();
		raiz.addProperty("tipo_reporte", tipoReporte);
		raiz.addProperty("generado_en", formatearFechaHora(LocalDateTime.now()));
		raiz.addProperty("periodo", formatearPeriodo(resumen.getPeriodo()));
		raiz.addProperty("cantidad_ventas", resumen.getCantidadVentas());
		raiz.addProperty("subtotal_ventas", resumen.getSubtotalVentas());
		raiz.addProperty("impuestos", resumen.getImpuestos());
		raiz.addProperty("total_ventas", resumen.getTotalVentas());

		JsonArray ventas = new JsonArray();
		for (Venta venta : resumen.getVentas()) {
			JsonObject item = new JsonObject();
			item.addProperty("factura", venta.getNumeroFactura());
			item.addProperty("fecha",
					venta.getFechaHora() != null ? formatearFecha(venta.getFechaHora().toLocalDate()) : "");
			item.addProperty("cliente", venta.getCliente() != null && !venta.getCliente().isBlank() ? venta.getCliente()
					: "ANONIMO");
			item.addProperty("codigo_cliente", venta.getCodigoCliente());
			item.addProperty("forma_pago", venta.getFormaPago() != null ? venta.getFormaPago().toString() : "");
			item.addProperty("subtotal", venta.getSubTotal());
			item.addProperty("impuestos", venta.getImpuestos());
			item.addProperty("total", venta.getTotal());
			item.addProperty("estado", venta.getEstado() != null ? venta.getEstado().toString() : "");
			ventas.add(item);
		}
		raiz.add("ventas", ventas);

		return raiz;
	}

	@Override
	public String guardarReporteUtilidadBruta(ResumenUtilidadBrutaDTO resumen) throws Exception {
		if (resumen == null) {
			throw new Exception("No hay datos de utilidad bruta para guardar el reporte.");
		}

		File archivo = crearArchivo("utilidad_bruta_" + LocalDateTime.now().format(FORMATO_ARCHIVO) + ".json");

		JsonObject raiz = new JsonObject();
		raiz.addProperty("tipo_reporte", "utilidad_bruta");
		raiz.addProperty("generado_en", formatearFechaHora(LocalDateTime.now()));
		raiz.addProperty("periodo", resumen.getPeriodo());
		raiz.addProperty("total_ventas", resumen.getTotalVentas());
		raiz.addProperty("costo_ventas", resumen.getCostoVentas());
		raiz.addProperty("utilidad_bruta", resumen.getUtilidadBruta());
		raiz.addProperty("cantidad_ventas", resumen.getCantidadVentas());
		raiz.addProperty("cantidad_vendida", resumen.getCantidadVendida());

		JsonArray productos = new JsonArray();
		for (DetalleUtilidadBrutaDTO detalle : resumen.getDetalles()) {
			JsonObject item = new JsonObject();
			item.addProperty("codigo", detalle.getCodigoProducto());
			item.addProperty("producto", detalle.getNombreProducto());
			item.addProperty("cantidad_vendida", detalle.getCantidadVendida());
			item.addProperty("ventas", detalle.getVentas());
			item.addProperty("costo_venta", detalle.getCostoVenta());
			item.addProperty("utilidad", detalle.getUtilidad());
			productos.add(item);
		}
		raiz.add("productos", productos);

		escribirJson(archivo, raiz);
		return archivo.getPath();
	}

	@Override
	public String guardarReporteVentasFormaPago(List<ResumenFormaPagoDTO> resumenes, LocalDate fechaInicio,
			LocalDate fechaFin) throws Exception {
		File archivo = crearArchivo("ventas_por_forma_pago_" + LocalDateTime.now().format(FORMATO_ARCHIVO) + ".json");

		JsonObject raiz = crearRaizConPeriodo("ventas_por_forma_pago", fechaInicio, fechaFin);
		JsonArray formasPago = new JsonArray();
		double total = 0;
		int cantidadVentas = 0;

		if (resumenes != null) {
			for (ResumenFormaPagoDTO resumen : resumenes) {
				JsonObject item = new JsonObject();
				item.addProperty("tipo", resumen.getFormaPago() != null ? resumen.getFormaPago().toString() : "");
				item.addProperty("cantidad_ventas", resumen.getCantidadVentas());
				item.addProperty("valor", resumen.getValorTotal());
				formasPago.add(item);
				total += resumen.getValorTotal();
				cantidadVentas += resumen.getCantidadVentas();
			}
		}

		raiz.addProperty("cantidad_ventas", cantidadVentas);
		raiz.addProperty("total_ventas", total);
		raiz.add("formas_pago", formasPago);

		escribirJson(archivo, raiz);
		return archivo.getPath();
	}

	@Override
	public String guardarReporteClientesMayorCompra(List<ResumenClienteDTO> resumenes, LocalDate fechaInicio,
			LocalDate fechaFin) throws Exception {
		File archivo = crearArchivo("clientes_mayor_compra_" + LocalDateTime.now().format(FORMATO_ARCHIVO) + ".json");

		JsonObject raiz = crearRaizConPeriodo("clientes_mayor_compra", fechaInicio, fechaFin);
		JsonArray clientes = new JsonArray();
		double totalComprado = 0;

		if (resumenes != null) {
			for (ResumenClienteDTO resumen : resumenes) {
				JsonObject item = new JsonObject();
				item.addProperty("codigo_cliente", resumen.getCodigoCliente());
				item.addProperty("cliente", resumen.getNombreCliente());
				item.addProperty("cantidad_compras", resumen.getCantidadCompras());
				item.addProperty("total_comprado", resumen.getTotalComprado());
				clientes.add(item);
				totalComprado += resumen.getTotalComprado();
			}
		}

		raiz.addProperty("cantidad_clientes", resumenes != null ? resumenes.size() : 0);
		raiz.addProperty("total_comprado", totalComprado);
		raiz.add("clientes", clientes);

		escribirJson(archivo, raiz);
		return archivo.getPath();
	}

	@Override
	public String guardarReporteInventarioValorizado(List<ResumenInventarioValorizadoDTO> resumenes) throws Exception {
		File archivo = crearArchivo("inventario_valorizado_" + LocalDateTime.now().format(FORMATO_ARCHIVO) + ".json");

		JsonObject raiz = new JsonObject();
		raiz.addProperty("tipo_reporte", "inventario_valorizado");
		raiz.addProperty("generado_en", formatearFechaHora(LocalDateTime.now()));

		JsonArray productos = new JsonArray();
		double valorTotalInventario = 0;

		if (resumenes != null) {
			for (ResumenInventarioValorizadoDTO resumen : resumenes) {
				JsonObject item = new JsonObject();
				item.addProperty("codigo", resumen.getCodigoProducto());
				item.addProperty("producto", resumen.getNombreProducto());
				item.addProperty("categoria", resumen.getCategoria() != null ? resumen.getCategoria().toString() : "");
				item.addProperty("stock_actual", resumen.getStockActual());
				item.addProperty("precio_compra", resumen.getPrecioCompra());
				item.addProperty("valor_inventario", resumen.getValorInventario());
				productos.add(item);
				valorTotalInventario += resumen.getValorInventario();
			}
		}

		raiz.addProperty("cantidad_productos", resumenes != null ? resumenes.size() : 0);
		raiz.addProperty("valor_total_inventario", valorTotalInventario);
		raiz.add("productos", productos);

		escribirJson(archivo, raiz);
		return archivo.getPath();
	}

	@Override
	public String guardarReporteResumenContable(ResumenContableDTO resumen, LocalDate fechaInicio, LocalDate fechaFin)
			throws Exception {
		if (resumen == null) {
			throw new Exception("No hay datos contables para guardar el reporte.");
		}

		File archivo = crearArchivo("resumen_contable_" + LocalDateTime.now().format(FORMATO_ARCHIVO) + ".json");

		JsonObject raiz = crearRaizConPeriodo("resumen_contable", fechaInicio, fechaFin);
		raiz.addProperty("ingresos", resumen.getIngresos());
		raiz.addProperty("egresos", resumen.getEgresos());
		raiz.addProperty("utilidad", resumen.getUtilidad());

		escribirJson(archivo, raiz);
		return archivo.getPath();
	}

	@Override
	public String guardarReporteResumenFinancieroDiario(ResumenFinancieroDiarioDTO resumen) throws Exception {
		if (resumen == null) {
			throw new Exception("No hay datos del resumen financiero diario para guardar el reporte.");
		}

		File archivo = crearArchivo(
				"resumen_financiero_diario_" + LocalDateTime.now().format(FORMATO_ARCHIVO) + ".json");

		JsonObject raiz = new JsonObject();
		raiz.addProperty("fecha", resumen.getFecha() != null ? resumen.getFecha().toString() : null);
		raiz.addProperty("total_ventas", resumen.getTotalVentas());
		raiz.addProperty("total_compras", resumen.getTotalCompras());
		raiz.addProperty("utilidad_bruta", resumen.getUtilidadBruta());

		JsonArray ventasPorFormaPago = new JsonArray();
		if (resumen.getVentasPorFormaPago() != null) {
			for (ResumenFormaPagoDTO formaPago : resumen.getVentasPorFormaPago()) {
				JsonObject item = new JsonObject();
				item.addProperty("tipo", formaPago.getFormaPago() != null ? formaPago.getFormaPago().toString() : "");
				item.addProperty("valor", formaPago.getValorTotal());
				ventasPorFormaPago.add(item);
			}
		}
		raiz.add("ventas_por_forma_pago", ventasPorFormaPago);

		JsonArray productosMasVendidos = new JsonArray();
		if (resumen.getProductosMasVendidos() != null) {
			for (ResumenProductoDTO producto : resumen.getProductosMasVendidos()) {
				JsonObject item = new JsonObject();
				item.addProperty("codigo", producto.getCodigoProducto());
				item.addProperty("nombre", producto.getNombreProducto());
				item.addProperty("cantidad_vendida", producto.getCantidadVendida());
				productosMasVendidos.add(item);
			}
		}
		raiz.add("productos_mas_vendidos", productosMasVendidos);

		JsonObject resumenContable = new JsonObject();
		resumenContable.addProperty("ingresos",
				resumen.getResumenContable() != null ? resumen.getResumenContable().getIngresos() : 0);
		resumenContable.addProperty("egresos",
				resumen.getResumenContable() != null ? resumen.getResumenContable().getEgresos() : 0);
		resumenContable.addProperty("iva_generado", resumen.getIvaGenerado());
		resumenContable.addProperty("iva_descontable", resumen.getIvaDescontable());
		raiz.add("resumen_contable", resumenContable);

		escribirJson(archivo, raiz);
		return archivo.getPath();
	}

	private JsonObject crearRaizConPeriodo(String tipoReporte, LocalDate fechaInicio, LocalDate fechaFin) {
		JsonObject raiz = new JsonObject();
		raiz.addProperty("tipo_reporte", tipoReporte);
		raiz.addProperty("generado_en", formatearFechaHora(LocalDateTime.now()));
		raiz.addProperty("fecha_inicio", formatearFecha(fechaInicio));
		raiz.addProperty("fecha_fin", formatearFecha(fechaFin));
		return raiz;
	}

	private File crearArchivo(String nombreArchivo) throws Exception {
		File carpeta = new File(CARPETA_REPORTES);
		if (!carpeta.exists() && !carpeta.mkdirs()) {
			throw new Exception("No se pudo crear la carpeta de reportes.");
		}

		return new File(carpeta, nombreArchivo);
	}

	private void escribirJson(File archivo, JsonObject contenido) throws Exception {
		Gson gson = crearGson();
		try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
			writer.print(gson.toJson(contenido));
		}
	}

	private String formatearFecha(LocalDate fecha) {
		return fecha != null ? fecha.format(FORMATO_FECHA) : null;
	}

	private String formatearFechaHora(LocalDateTime fechaHora) {
		return fechaHora != null ? fechaHora.format(FORMATO_FECHA_HORA) : null;
	}

	private String formatearPeriodo(String periodo) {
		if (periodo == null || periodo.isBlank()) {
			return periodo;
		}

		try {
			return formatearFecha(LocalDate.parse(periodo));
		} catch (Exception e) {
			return periodo;
		}
	}

	private Gson crearGson() {
		return new GsonBuilder()
				.registerTypeAdapter(LocalDate.class,
						(com.google.gson.JsonSerializer<LocalDate>) (fecha, tipo, contexto) ->
								new JsonPrimitive(formatearFecha(fecha)))
				.registerTypeAdapter(LocalDateTime.class,
						(com.google.gson.JsonSerializer<LocalDateTime>) (fechaHora, tipo, contexto) ->
								new JsonPrimitive(formatearFechaHora(fechaHora)))
				.setPrettyPrinting()
				.create();
	}

	
}
