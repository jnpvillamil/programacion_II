package co.uptc.edu.co.gui;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import co.uptc.edu.co.config.TiendaConfig;
import co.uptc.edu.co.gui.dialog.DialogAnularCompra;
import co.uptc.edu.co.gui.dialog.DialogAnularVenta;
import co.uptc.edu.co.gui.dialog.DialogCliente;
import co.uptc.edu.co.gui.dialog.DialogCompra;
import co.uptc.edu.co.gui.dialog.DialogDetalleCompra;
import co.uptc.edu.co.gui.dialog.DialogDetalleContable;
import co.uptc.edu.co.gui.dialog.DialogDetalleVenta;
import co.uptc.edu.co.gui.dialog.DialogDevolucionVenta;
import co.uptc.edu.co.gui.dialog.DialogHistorialCliente;
import co.uptc.edu.co.gui.dialog.DialogMovimientoInventario;
import co.uptc.edu.co.gui.dialog.DialogProducto;
import co.uptc.edu.co.gui.dialog.DialogProveedor;
import co.uptc.edu.co.gui.dialog.DialogVenta;
import co.uptc.edu.co.interfaces.IGestionCliente;
import co.uptc.edu.co.interfaces.IGestionCompra;
import co.uptc.edu.co.interfaces.IGestionConsultas;
import co.uptc.edu.co.interfaces.IGestionContabilidad;
import co.uptc.edu.co.interfaces.IGestionDevolucionVenta;
import co.uptc.edu.co.interfaces.IGestionFactura;
import co.uptc.edu.co.interfaces.IGestionInventario;
import co.uptc.edu.co.interfaces.IGestionProducto;
import co.uptc.edu.co.interfaces.IGestionProveedor;
import co.uptc.edu.co.interfaces.IGestionReporte;
import co.uptc.edu.co.interfaces.IGestionVenta;
import co.uptc.edu.co.modelo.Cliente;
import co.uptc.edu.co.util.LogUtil;
import co.uptc.edu.co.modelo.Compra;
import co.uptc.edu.co.modelo.DetalleCompra;
import co.uptc.edu.co.modelo.MovimientoContable;
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.Proveedor;
import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.modelo.dto.ResumenClienteDTO;
import co.uptc.edu.co.modelo.dto.ResumenContableDTO;
import co.uptc.edu.co.modelo.dto.ResumenFinancieroDiarioDTO;
import co.uptc.edu.co.modelo.dto.ResumenFormaPagoDTO;
import co.uptc.edu.co.modelo.dto.ResumenInventarioValorizadoDTO;
import co.uptc.edu.co.modelo.dto.ResumenProductoDTO;

public class Evento implements ActionListener {
	// CONSTANTES GENERALES
	private static final DecimalFormat FORMATO_MONEDA = crearFormatoMoneda();
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final String TIPO_MOVIMIENTO_ENTRADA = "ENTRADA";
	private static final String TIPO_MOVIMIENTO_SALIDA = "SALIDA";

	// CONSTANTES DE NAVEGACION
	public static final String PRODUCTOS = "Productos";
	public static final String CLIENTES = "Clientes";
	public static final String PROVEEDORES = "Proveedores";
	public static final String VENTAS = "Ventas";
	public static final String COMPRAS = "Compras";
	public static final String CONTABILIDAD = "Contabilidad";
	public static final String REPORTES = "Reportes";
	public static final String CONSULTAS = "Consultas";

	// CONSTANTES DE COMANDOS - PRODUCTO
	public static final String CMD_NUEVO_PRODUCTO = "NuevoProducto";
	public static final String CMD_EDITAR_PRODUCTO = "EditarProducto";
	public static final String CMD_ESTADO_PRODUCTO = "CambiarEstadoProducto";
	public static final String CMD_MOVIMIENTO_INVENTARIO = "MovimientoInventarioProducto";
	public static final String CMD_CONFIRMAR_PRODUCTO = "ConfirmarProducto";
	public static final String CMD_CONFIRMAR_EDICION_PRODUCTO = "ConfirmarEdicionProducto";
	public static final String CMD_CONFIRMAR_MOVIMIENTO_INVENTARIO = "ConfirmarMovimientoInventario";

	// CONSTANTES DE COMANDOS - CLIENTE
	public static final String CMD_NUEVO_CLIENTE = "NuevoCliente";
	public static final String CMD_EDITAR_CLIENTE = "EditarCliente";
	public static final String CMD_ESTADO_CLIENTE = "EstadoCliente";
	public static final String CMD_HISTORIAL_CLIENTE = "HistorialCliente";
	public static final String CMD_VER_DETALLE_CLIENTE = "VerDetalleCliente";
	public static final String CMD_CONFIRMAR_CLIENTE = "ConfirmarCliente";
	public static final String CMD_CONFIRMAR_EDICION_CLIENTE = "ConfirmarEdicionCliente";

	// CONSTANTES DE COMANDOS - PROVEEDOR
	public static final String CMD_NUEVO_PROVEEDOR = "NuevoProveedor";
	public static final String CMD_EDITAR_PROVEEDOR = "EditarProveedor";
	public static final String CMD_ESTADO_PROVEEDOR = "EstadoProveedor";
	public static final String CMD_CONFIRMAR_PROVEEDOR = "ConfirmarProveedor";
	public static final String CMD_CONFIRMAR_EDICION_PROVEEDOR = "ConfirmarEdicionProveedor";

	// CONSTANTES DE COMANDOS - VENTA
	public static final String CMD_NUEVA_VENTA = "NuevaVenta";
	public static final String CMD_CONFIRMAR_VENTA = "ConfirmarVenta";
	public static final String CMD_ANULAR_VENTA = "AnularVenta";
	public static final String CMD_CONFIRMAR_ANULAR_VENTA = "ConfirmarAnularVenta";
	public static final String CMD_DEVOLUCION_VENTA = "DevolucionVenta";
	public static final String CMD_GUARDAR_DEVOLUCION_VENTA = "GuardarDevolucionVenta";
	public static final String CMD_VER_DETALLE_VENTA = "VerDetalleVenta";
	public static final String CMD_FACTURA_VENTA = "FacturaVenta";

	// CONSTANTES DE COMANDOS - COMPRA
	public static final String CMD_NUEVA_COMPRA = "NuevaCompra";
	public static final String CMD_CONFIRMAR_COMPRA = "ConfirmarCompra";
	public static final String CMD_VER_DETALLE_COMPRA = "VerDetalleCompra";
	public static final String CMD_AGREGAR_PRODUCTO_COMPRA = "AgregarProductoCompra";
	public static final String CMD_ANULAR_COMPRA = "AnularCompra";
	public static final String CMD_CONFIRMAR_REGISTRO_COMPRA = "ConfirmarRegistroCompra";
	public static final String CMD_FACTURA_COMPRA = "FacturaCompra";

	// CONSTANTES DE COMANDOS - CONTABILIDAD
	public static final String CMD_VER_DETALLE_CONTABLE = "VerDetalleContable";

	// CONSTANTES DE COMANDOS - REPORTES
	public static final String CMD_BUSCAR_REPORTE = "BuscarReporte";
	public static final String CMD_GENERAR_REPORTE_JSON = "GenerarReporteJson";

	// CONSTANTES DE COMANDOS - CONSULTAS
	public static final String CMD_CONSULTAR_SISTEMA = "ConsultarSistema";

	// ATRIBUTOS
	private VentanaPrincipal ventana;
	private IGestionProducto gestionProducto;
	private IGestionCliente gestionCliente;
	private IGestionProveedor gestionProveedor;
	private IGestionVenta gestionVenta;
	private IGestionCompra gestionCompra;
	private IGestionDevolucionVenta gestionDevolucionVenta;
	private IGestionFactura gestionFactura;
	private IGestionReporte gestionReporte;
	private IGestionConsultas gestionConsultas;
	private IGestionContabilidad gestionContabilidad;
	private IGestionInventario gestionInventario;

	// CONSTRUCTOR
	public Evento(VentanaPrincipal ventana, TiendaConfig config) {
		this.ventana = ventana;
		this.gestionProducto = config.getGestionProducto();
		this.gestionCliente = config.getGestionCliente();
		this.gestionProveedor = config.getGestionProveedor();
		this.gestionVenta = config.getGestionVenta();
		this.gestionCompra = config.getGestionCompra();
		this.gestionDevolucionVenta = config.getGestionDevolucionVenta();
		this.gestionFactura = config.getGestionFactura();
		this.gestionReporte = config.getGestionReporte();
		this.gestionConsultas = config.getGestionConsultas();
		this.gestionContabilidad = config.getGestionContabilidad();
		this.gestionInventario = config.getGestionInventario();
	}

	// GETTERS NECESARIOS
	public IGestionCompra getGestionCompra() {
		return gestionCompra;
	}

	// ACTION PERFORMED
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();

		if (manejarNavegacion(comando)) {
			return;
		}

		if (manejarEventosProducto(comando, e)) {
			return;
		}

		if (manejarEventosCliente(comando, e)) {
			return;
		}

		if (manejarEventosProveedor(comando, e)) {
			return;
		}

		if (manejarEventosVenta(comando, e)) {
			return;
		}

		if (manejarEventosCompra(comando, e)) {
			return;
		}

		if (manejarEventosContabilidad(comando)) {
			return;
		}

		if (manejarEventosReportes(comando)) {
			return;
		}

		if (manejarEventosConsultas(comando)) {
			return;
		}
	}

	// NAVEGACION
	private boolean manejarNavegacion(String comando) {
		switch (comando) {
		case PRODUCTOS:
			ventana.irProductos();
			refrescarTablaProductos();
			return true;

		case CLIENTES:
			ventana.irClientes();
			refrescarTablaClientes();
			return true;

		case PROVEEDORES:
			ventana.irProveedores();
			refrescarTablaProveedores();
			return true;

		case VENTAS:
			ventana.irVentas();
			refrescarTablaVentas();
			return true;

		case COMPRAS:
			ventana.irCompras();
			refrescarTablaCompras();
			return true;

		case CONTABILIDAD:
			ventana.irContabilidad();
			refrescarTablaContabilidad();
			return true;

		case REPORTES:
			ventana.irReportes();
			return true;

		case CONSULTAS:
			ventana.irConsultas();
			refrescarDatosConsultas();
			return true;

		default:
			return false;
		}
	}

	// EVENTOS DE PRODUCTO
	private boolean manejarEventosProducto(String comando, ActionEvent e) {
		switch (comando) {
		case CMD_NUEVO_PRODUCTO:
			abrirDialogoNuevoProducto();
			return true;

		case CMD_CONFIRMAR_PRODUCTO:
			registrarProducto(e);
			return true;

		case CMD_EDITAR_PRODUCTO:
			abrirFormularioEditarProducto();
			return true;

		case CMD_CONFIRMAR_EDICION_PRODUCTO:
			editarProducto(e);
			return true;

		case CMD_ESTADO_PRODUCTO:
			cambiarEstadoProductoSeleccionado();
			return true;

		case CMD_MOVIMIENTO_INVENTARIO:
			abrirDialogoMovimientoInventario();
			return true;

		case CMD_CONFIRMAR_MOVIMIENTO_INVENTARIO:
			registrarMovimientoInventario(e);
			return true;

		default:
			return false;
		}
	}

	private Producto obtenerProductoSeleccionado() throws Exception {
		PanelProducto panelProducto = ventana.getPanelProducto();

		if (!panelProducto.haySeleccion()) {
			throw new Exception("Debe seleccionar un producto.");
		}

		String codigo = panelProducto.obtenerCodigoSeleccionado();
		Producto producto = gestionProducto.buscarProductoPorCodigo(codigo);

		if (producto == null) {
			throw new Exception("No se encontro el producto seleccionado.");
		}

		return producto;
	}

	private void abrirDialogoNuevoProducto() {
		DialogProducto dialog = new DialogProducto(ventana, this);
		String codigoGenerado = gestionProducto.generarCodigoProducto();
		dialog.cargarCodigoGenerado(codigoGenerado);
		dialog.setVisible(true);
	}

	private void registrarProducto(ActionEvent e) {
		try {
			DialogProducto dialog = obtenerDialogProducto(e);
			Producto producto = dialog.obtenerProducto();

			gestionProducto.registrarProducto(producto);
			mostrarInformacion("Producto registrado exitosamente.");
			refrescarTablaProductos();
			dialog.dispose();

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void abrirFormularioEditarProducto() {
		try {
			Producto producto = obtenerProductoSeleccionado();

			DialogProducto dialog = new DialogProducto(ventana, this);
			dialog.configurarModoEdicion();
			dialog.cargarProducto(producto);
			dialog.setVisible(true);

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void editarProducto(ActionEvent e) {
		try {
			DialogProducto dialog = obtenerDialogProducto(e);
			Producto productoEditado = dialog.obtenerProducto();

			gestionProducto.actualizarProducto(productoEditado);
			mostrarInformacion("Producto editado exitosamente.");
			refrescarTablaProductos();
			dialog.dispose();

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void cambiarEstadoProductoSeleccionado() {
		try {
			PanelProducto panelProducto = ventana.getPanelProducto();

			if (!panelProducto.haySeleccion()) {
				throw new Exception("Debe seleccionar un producto.");
			}

			String codigo = panelProducto.obtenerCodigoSeleccionado();
			String estado = panelProducto.obtenerEstadoSeleccionado();
			boolean estabaActivo = "ACTIVO".equalsIgnoreCase(estado) || "Activo".equalsIgnoreCase(estado);

			String mensaje = estabaActivo ? "Esta seguro de inactivar este producto?"
					: "Esta seguro de activar este producto?";

			int confirmacion = JOptionPane.showConfirmDialog(ventana, mensaje, "Confirmar cambio de estado",
					JOptionPane.YES_NO_OPTION);

			if (confirmacion != JOptionPane.YES_OPTION) {
				return;
			}

			gestionProducto.cambiarEstadoProducto(codigo);

			mostrarInformacion(estabaActivo ? "Producto inactivado exitosamente." : "Producto activado exitosamente.");

			refrescarTablaProductos();

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void abrirDialogoMovimientoInventario() {
		try {
			Producto producto = obtenerProductoSeleccionado();

			DialogMovimientoInventario dialog = new DialogMovimientoInventario(ventana, this);
			dialog.cargarProducto(producto.getCodigoProducto());
			dialog.setVisible(true);

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void registrarMovimientoInventario(ActionEvent e) {
		try {
			DialogMovimientoInventario dialog = obtenerDialogMovimientoInventario(e);

			String codigo = dialog.obtenerCodigoProducto();
			String tipoMovimiento = dialog.obtenerTipoMovimiento();
			int cantidad = dialog.obtenerCantidad();

			if (TIPO_MOVIMIENTO_ENTRADA.equalsIgnoreCase(tipoMovimiento)) {
				gestionInventario.registrarEntrada(codigo, cantidad, "Movimiento manual de inventario");
			} else if (TIPO_MOVIMIENTO_SALIDA.equalsIgnoreCase(tipoMovimiento)) {
				gestionInventario.registrarSalida(codigo, cantidad, "Movimiento manual de inventario");
			} else {
				throw new Exception("Tipo de movimiento no valido.");
			}

			mostrarInformacion("Movimiento de inventario registrado exitosamente.");
			gestionProducto.recargar();
			refrescarTablaProductos();
			dialog.dispose();

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void refrescarTablaProductos() {
		PanelProducto panelProducto = ventana.getPanelProducto();
		panelProducto.cargarProductos(gestionProducto.obtenerProductos());
	}

	// EVENTOS DE CLIENTE
	private boolean manejarEventosCliente(String comando, ActionEvent e) {
		switch (comando) {
		case CMD_NUEVO_CLIENTE:
			abrirDialogoNuevoCliente();
			return true;

		case CMD_CONFIRMAR_CLIENTE:
			registrarCliente(e);
			return true;

		case CMD_EDITAR_CLIENTE:
			abrirFormularioEditarCliente();
			return true;

		case CMD_CONFIRMAR_EDICION_CLIENTE:
			editarCliente(e);
			return true;

		case CMD_ESTADO_CLIENTE:
			cambiarEstadoClienteSeleccionado();
			return true;

		case CMD_HISTORIAL_CLIENTE:
			abrirHistorialCliente();
			return true;

		case CMD_VER_DETALLE_CLIENTE:
			abrirDetalleVentaDesdeHistorial(e);
			return true;

		default:
			return false;
		}
	}

	private Cliente obtenerClienteSeleccionado() throws Exception {
		PanelCliente panelCliente = ventana.getPanelCliente();

		if (!panelCliente.haySeleccion()) {
			throw new Exception("Debe seleccionar un cliente.");
		}

		String codigo = panelCliente.obtenerCodigoSeleccionado();
		Cliente cliente = gestionCliente.buscarClientePorCodigo(codigo);

		if (cliente == null) {
			throw new Exception("No se encontro el cliente seleccionado.");
		}

		return cliente;
	}

	private void abrirDialogoNuevoCliente() {
		try {
			DialogCliente dialog = new DialogCliente(ventana, this);
			String codigoGenerado = gestionCliente.generarCodigoCliente();
			dialog.cargarCodigoGenerado(codigoGenerado);
			dialog.setVisible(true);
		} catch (Exception ex) {
			mostrarError("No se pudo abrir el formulario de cliente: " + ex.getMessage());
		}
	}

	private void registrarCliente(ActionEvent e) {
		try {
			DialogCliente dialog = obtenerDialogCliente(e);
			Cliente cliente = dialog.obtenerCliente();

			gestionCliente.registrarCliente(cliente);
			mostrarInformacion("Cliente registrado exitosamente.");
			refrescarTablaClientes();
			dialog.dispose();

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void abrirFormularioEditarCliente() {
		try {
			Cliente cliente = obtenerClienteSeleccionado();

			DialogCliente dialog = new DialogCliente(ventana, this);
			dialog.configurarModoEdicion();
			dialog.cargarCliente(cliente);
			dialog.setVisible(true);

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void editarCliente(ActionEvent e) {
		try {
			DialogCliente dialog = obtenerDialogCliente(e);
			Cliente clienteEditado = dialog.obtenerCliente();

			gestionCliente.actualizarCliente(clienteEditado);
			mostrarInformacion("Cliente editado exitosamente.");
			refrescarTablaClientes();
			dialog.dispose();

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void cambiarEstadoClienteSeleccionado() {
		try {
			Cliente cliente = obtenerClienteSeleccionado();
			boolean estabaActivo = cliente.estaActivo();

			String mensaje = estabaActivo ? "Esta seguro de inactivar este cliente?"
					: "Esta seguro de activar este cliente?";

			int confirmacion = JOptionPane.showConfirmDialog(ventana, mensaje, "Confirmar cambio de estado",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (confirmacion != JOptionPane.YES_OPTION) {
				return;
			}

			gestionCliente.cambiarEstadoCliente(cliente.getCodigo());

			mostrarInformacion(estabaActivo ? "Cliente inactivado exitosamente." : "Cliente activado exitosamente.");

			refrescarTablaClientes();

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void abrirHistorialCliente() {
		try {
			Cliente cliente = obtenerClienteSeleccionado();

			DialogHistorialCliente dialog = new DialogHistorialCliente(ventana, this);
			dialog.cargarCliente(cliente.getCodigo(), cliente.getNombre());
			dialog.cargarHistorial(gestionVenta.obtenerVentasPorCliente(cliente));
			dialog.setVisible(true);

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void abrirDetalleVentaDesdeHistorial(ActionEvent e) {
		try {
			DialogHistorialCliente dialogHistorial = obtenerDialogHistorialCliente(e);

			if (!dialogHistorial.haySeleccion()) {
				throw new Exception("Debe seleccionar una venta del historial.");
			}

			String numeroFactura = dialogHistorial.obtenerFacturaSeleccionada();
			Venta venta = gestionVenta.buscarVentaPorNumero(numeroFactura);

			if (venta == null) {
				throw new Exception("No se encontro la venta seleccionada.");
			}

			DialogDetalleVenta dialog = new DialogDetalleVenta(ventana);
			dialog.cargarVenta(venta, gestionDevolucionVenta.obtenerResumenDetalleVenta(venta));
			dialog.setVisible(true);
		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void refrescarTablaClientes() {
		PanelCliente panelCliente = ventana.getPanelCliente();
		panelCliente.cargarClientes(gestionCliente.obtenerClientes());
	}

	// EVENTOS DE PROVEEDOR
	private boolean manejarEventosProveedor(String comando, ActionEvent e) {
		switch (comando) {
		case CMD_NUEVO_PROVEEDOR:
			abrirDialogoNuevoProveedor();
			return true;

		case CMD_CONFIRMAR_PROVEEDOR:
			registrarProveedor(e);
			return true;

		case CMD_EDITAR_PROVEEDOR:
			abrirFormularioEditarProveedor();
			return true;

		case CMD_CONFIRMAR_EDICION_PROVEEDOR:
			editarProveedor(e);
			return true;

		case CMD_ESTADO_PROVEEDOR:
			cambiarEstadoProveedorSeleccionado();
			return true;

		default:
			return false;
		}
	}

	private Proveedor obtenerProveedorSeleccionado() throws Exception {
		PanelProveedor panelProveedor = ventana.getPanelProveedor();

		if (!panelProveedor.haySeleccion()) {
			throw new Exception("Debe seleccionar un proveedor.");
		}

		String codigo = panelProveedor.obtenerCodigoSeleccionado();
		Proveedor proveedor = gestionProveedor.buscarProveedorPorCodigo(codigo);

		if (proveedor == null) {
			throw new Exception("No se encontro el proveedor seleccionado.");
		}

		return proveedor;
	}

	private void abrirDialogoNuevoProveedor() {
		DialogProveedor dialog = new DialogProveedor(ventana, this);
		try {

			dialog.cargarCodigoGenerado(gestionProveedor.generarCodigoProveedor());
		} catch (Exception ex) {
			mostrarError("No se pudo generar el código del proveedor: " + ex.getMessage());
		}

		dialog.setVisible(true);
	}

	private void registrarProveedor(ActionEvent e) {
		try {
			DialogProveedor dialog = obtenerDialogProveedor(e);
			Proveedor proveedor = dialog.obtenerProveedor();

			gestionProveedor.registrarProveedor(proveedor);
			mostrarInformacion("Proveedor registrado exitosamente.");
			refrescarTablaProveedores();
			dialog.dispose();

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void abrirFormularioEditarProveedor() {
		try {
			Proveedor proveedor = obtenerProveedorSeleccionado();

			DialogProveedor dialog = new DialogProveedor(ventana, this);
			dialog.configurarModoEdicion();
			dialog.cargarProveedor(proveedor);
			dialog.setVisible(true);

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void editarProveedor(ActionEvent e) {
		try {
			DialogProveedor dialog = obtenerDialogProveedor(e);
			Proveedor proveedorEditado = dialog.obtenerProveedor();

			gestionProveedor.actualizarProveedor(proveedorEditado);
			mostrarInformacion("Proveedor editado exitosamente.");
			refrescarTablaProveedores();
			dialog.dispose();

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void cambiarEstadoProveedorSeleccionado() {
		try {
			Proveedor proveedor = obtenerProveedorSeleccionado();
			boolean estabaActivo = proveedor.estaActivo();

			int confirmacion = JOptionPane.showConfirmDialog(ventana,
					estabaActivo ? "Esta seguro de inactivar este proveedor?"
							: "Esta seguro de activar este proveedor?",
					"Confirmar cambio de estado", JOptionPane.YES_NO_OPTION);

			if (confirmacion != JOptionPane.YES_OPTION) {
				return;
			}

			gestionProveedor.cambiarEstadoProveedor(proveedor.getCodigoProveedor());

			mostrarInformacion(
					estabaActivo ? "Proveedor inactivado exitosamente." : "Proveedor activado exitosamente.");

			refrescarTablaProveedores();

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void refrescarTablaProveedores() {
		PanelProveedor panelProveedor = ventana.getPanelProveedor();
		panelProveedor.cargarProveedores(gestionProveedor.obtenerProveedores());
	}

	// EVENTOS DE VENTA
	private boolean manejarEventosVenta(String comando, ActionEvent e) {
		switch (comando) {
		case CMD_NUEVA_VENTA:
			abrirDialogoNuevaVenta();
			return true;

		case CMD_CONFIRMAR_VENTA:
			registrarVenta(e);
			return true;

		case CMD_ANULAR_VENTA:
			abrirDialogoAnularVenta();
			return true;

		case CMD_CONFIRMAR_ANULAR_VENTA:
			confirmarAnulacionVenta(e);
			return true;

		case CMD_DEVOLUCION_VENTA:
			abrirDialogoDevolucionVenta();
			return true;

		case CMD_GUARDAR_DEVOLUCION_VENTA:
			guardarDevolucionVenta(e);
			return true;

		case CMD_VER_DETALLE_VENTA:
			abrirDialogoDetalleVenta();
			return true;

		case CMD_FACTURA_VENTA:
			abrirDialogoFacturaVenta();
			return true;

		default:
			return false;
		}
	}

	private void abrirDialogoNuevaVenta() {
		DialogVenta dialog = new DialogVenta(ventana, this, gestionVenta);
		dialog.cargarNumeroFactura(gestionVenta.generarNumeroFactura());
		dialog.cargarClientes(gestionCliente.obtenerClientes());
		dialog.cargarProductos(gestionProducto.obtenerProductos());
		dialog.setVisible(true);
	}

	private void registrarVenta(ActionEvent e) {
		try {
			DialogVenta dialog = obtenerDialogVenta(e);
			Venta venta = dialog.obtenerVenta();

			gestionVenta.registrarVenta(venta);
			gestionProducto.recargar();

			mostrarInformacion("Venta registrada exitosamente.");
			refrescarTablaVentas();
			refrescarTablaProductos();
			dialog.dispose();

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void abrirDialogoAnularVenta() {
		try {
			Venta venta = obtenerVentaSeleccionada();

			DialogAnularVenta dialog = new DialogAnularVenta(ventana, this);
			dialog.cargarVenta(venta.getNumeroFactura(), venta.getCliente(),
					venta.getFechaHora() != null ? venta.getFechaHora().toLocalDate().format(FORMATO_FECHA) : "",
					String.valueOf(venta.getTotal()), venta.getEstado() != null ? venta.getEstado().name() : "");

			dialog.setVisible(true);

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void confirmarAnulacionVenta(ActionEvent e) {
		try {
			DialogAnularVenta dialog = obtenerDialogAnularVenta(e);

			String numeroFactura = dialog.obtenerNumeroFactura();
			String motivo = dialog.obtenerMotivoAnulacion();

			gestionVenta.anularVenta(numeroFactura, motivo);
			gestionProducto.recargar();

			mostrarInformacion("Venta anulada exitosamente.");
			refrescarTablaVentas();
			refrescarTablaProductos();
			dialog.dispose();

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void abrirDialogoDevolucionVenta() {
		try {
			Venta venta = obtenerVentaSeleccionada();

			DialogDevolucionVenta dialog = new DialogDevolucionVenta(ventana, this);
			dialog.setGestionDevolucionVenta(gestionDevolucionVenta);
			dialog.cargarVenta(venta);
			dialog.setVisible(true);

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void guardarDevolucionVenta(ActionEvent e) {
		try {
			DialogDevolucionVenta dialog = obtenerDialogDevolucionVenta(e);
			Venta venta = dialog.obtenerVenta();
			String codigoProducto = dialog.obtenerCodigoProductoSeleccionado();
			int cantidad = Integer.parseInt(dialog.obtenerCantidad());
			String motivo = dialog.obtenerMotivoDevolucion();

			gestionDevolucionVenta.devolverVenta(venta, codigoProducto, cantidad, motivo);
			gestionProducto.recargar();
			gestionVenta.recargar();

			mostrarInformacion("Devolucion registrada exitosamente.");
			refrescarTablaVentas();
			refrescarTablaProductos();
			dialog.dispose();

		} catch (NumberFormatException ex) {
			mostrarError("La cantidad a devolver debe ser un numero entero.");
		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void abrirDialogoDetalleVenta() {
		try {
			Venta venta = obtenerVentaSeleccionada();

			DialogDetalleVenta dialog = new DialogDetalleVenta(ventana);
			dialog.cargarVenta(venta, gestionDevolucionVenta.obtenerResumenDetalleVenta(venta));
			dialog.setVisible(true);

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void abrirDialogoFacturaVenta() {
		try {
			Venta venta = obtenerVentaSeleccionada();

			String rutaFactura = gestionFactura.generarFactura(venta);

			mostrarInformacion("Factura generada correctamente en: " + rutaFactura);

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void refrescarTablaVentas() {
		PanelVenta panelVenta = ventana.getPanelVenta();
		panelVenta.cargarVentas(gestionVenta.obtenerVentas());
	}

	private Venta obtenerVentaSeleccionada() throws Exception {
		PanelVenta panelVenta = ventana.getPanelVenta();

		if (!panelVenta.haySeleccion()) {
			throw new Exception("Debe seleccionar una venta.");
		}

		String numeroFactura = panelVenta.obtenerFacturaSeleccionada();
		Venta venta = gestionVenta.buscarVentaPorNumero(numeroFactura);

		if (venta == null) {
			throw new Exception("No se encontro la venta seleccionada.");
		}

		return venta;
	}

	// EVENTOS DE COMPRA
	private boolean manejarEventosCompra(String comando, ActionEvent e) {
		switch (comando) {
		case CMD_NUEVA_COMPRA:
			abrirDialogoNuevaCompra();
			return true;

		case CMD_CONFIRMAR_COMPRA:
			registrarCompra(e);
			return true;

		case CMD_CONFIRMAR_REGISTRO_COMPRA:
			registrarCompra(e);
			return true;

		case CMD_VER_DETALLE_COMPRA:
			abrirDialogoDetalleCompra();
			return true;

		case CMD_ANULAR_COMPRA:
			abrirDialogoAnularCompra();
			return true;

		case CMD_FACTURA_COMPRA:
			generarFacturaCompra();
			return true;

		default:
			return false;
		}
	}

	private void abrirDialogoNuevaCompra() {
		DialogCompra dialog = new DialogCompra(ventana, this);
		dialog.cargarProductos(gestionProducto.obtenerProductos());
		dialog.cargarProveedores(gestionProveedor.obtenerProveedores());
		dialog.getCampoNumeroFactura().setText(gestionCompra.generarNumeroFactura());
		dialog.getCampoFecha().setText(LocalDate.now().format(FORMATO_FECHA));
		dialog.setVisible(true);
	}

	private void registrarCompra(ActionEvent e) {
		try {
			DialogCompra dialog = obtenerDialogCompra(e);
			Compra compra = dialog.obtenerCompra();

			gestionCompra.registrarCompra(compra);
			mostrarInformacion("Compra registrada con número " + compra.getNumeroFacturaProveedor() + ".");
			dialog.dispose();
			gestionProducto.recargar();
			refrescarTablaCompras();
			refrescarTablaProductos();
		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void abrirDialogoDetalleCompra() {
		try {
			Compra compra = obtenerCompraSeleccionada();
			DialogDetalleCompra dialog = new DialogDetalleCompra(ventana);

			dialog.cargarCompra(compra.getNumeroFacturaProveedor(),
					compra.getFecha() != null ? compra.getFecha().format(FORMATO_FECHA) : "", compra.getCodigoProveedor(),
					compra.getFormaPago() != null ? compra.getFormaPago().toString() : "",
					FORMATO_MONEDA.format(compra.getSubtotal()), FORMATO_MONEDA.format(compra.getImpuestos()),
					FORMATO_MONEDA.format(compra.getTotalCompra()));

			dialog.limpiarTabla();
			if (compra.getDetalles() != null) {
				for (DetalleCompra detalle : compra.getDetalles()) {
					dialog.agregarDetalle(detalle.getProducto().getCodigoProducto(),
							detalle.getProducto().getNombreProducto(), String.valueOf(detalle.getCantidad()),
							FORMATO_MONEDA.format(detalle.getCostoUnitario()),
							FORMATO_MONEDA.format(detalle.getImpuestos()), FORMATO_MONEDA.format(detalle.getSubtotal()),
							FORMATO_MONEDA.format(detalle.getTotalCompra()));
				}
			}

			dialog.setVisible(true);
		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void abrirDialogoAnularCompra() {
		try {
			Compra compra = obtenerCompraSeleccionada();
			DialogAnularCompra dialog = new DialogAnularCompra(ventana);
			dialog.cargarCompra(compra.getNumeroFacturaProveedor(),
					compra.getFecha() != null ? compra.getFecha().format(FORMATO_FECHA) : "", compra.getCodigoProveedor(),
					String.valueOf(compra.getTotalCompra()));

			dialog.setVisible(true);

			if (dialog.isCompraAnulada()) {
				gestionCompra.anularCompra(compra.getNumeroFacturaProveedor(), dialog.getMotivoAnulacion());
				gestionProducto.recargar();
				refrescarTablaCompras();
				refrescarTablaProductos();
				mostrarInformacion("Compra anulada exitosamente.");
			}
		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void generarFacturaCompra() {
		try {
			Compra compra = obtenerCompraSeleccionada();
			String rutaFactura = gestionFactura.generarFactura(compra);
			mostrarInformacion("Factura de compra generada correctamente en: " + rutaFactura);
		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private Compra obtenerCompraSeleccionada() throws Exception {
		PanelCompra panelCompra = ventana.getPanelCompra();

		if (!panelCompra.haySeleccion()) {
			throw new Exception("Debe seleccionar una compra.");
		}

		String numeroFactura = panelCompra.obtenerFacturaSeleccionada();
		Compra compra = gestionCompra.buscarCompraPorNumero(numeroFactura);

		if (compra == null) {
			throw new Exception("No se encontró la compra seleccionada.");
		}

		return compra;
	}

	private void refrescarTablaCompras() {
		PanelCompra panelCompra = ventana.getPanelCompra();
		panelCompra.cargarCompras(gestionCompra.obtenerCompras());
		try {
			panelCompra.cargarProveedores(gestionProveedor.obtenerProveedores());
		} catch (Exception e) {
		}
	}

	// EVENTOS DE CONTABILIDAD
	private boolean manejarEventosContabilidad(String comando) {
		switch (comando) {
		case CMD_VER_DETALLE_CONTABLE:
			abrirDialogoDetalleContable();
			return true;

		default:
			return false;
		}
	}

	private void abrirDialogoDetalleContable() {
		try {
			PanelContabilidad panelContabilidad = ventana.getPanelContabilidad();

			if (!panelContabilidad.haySeleccion()) {
				throw new Exception("Debe seleccionar un movimiento contable.");
			}

			String codigo = panelContabilidad.obtenerCodigoSeleccionado();
			MovimientoContable movimiento = gestionContabilidad.buscarMovimientoPorCodigo(codigo);

			if (movimiento == null) {
				throw new Exception("No se encontro el movimiento contable seleccionado.");
			}

			DialogDetalleContable dialog = new DialogDetalleContable(ventana);
			dialog.cargarMovimiento(movimiento.getCodigoTransaccion(),
					movimiento.getFecha() != null ? movimiento.getFecha().format(FORMATO_FECHA) : "",
					movimiento.getTipoMovimientoContable() != null ? movimiento.getTipoMovimientoContable().toString()
							: "",
					movimiento.getCuentaContable(),
					movimiento.getValor() != null ? formatearMoneda(movimiento.getValor()) : "",
					movimiento.getDescripcion(), movimiento.getOrigen(), movimiento.getReferencia());
			dialog.setVisible(true);

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void refrescarTablaContabilidad() {
		PanelContabilidad panelContabilidad = ventana.getPanelContabilidad();
		panelContabilidad.cargarMovimientos(gestionContabilidad.obtenerMovimientos());
	}

	// EVENTOS DE REPORTES
	private boolean manejarEventosReportes(String comando) {
		switch (comando) {
		case CMD_BUSCAR_REPORTE:
			buscarReporte();
			return true;

		case CMD_GENERAR_REPORTE_JSON:
			generarReporteJsonActual();
			return true;

		default:
			return false;
		}
	}

	private void generarReporteJsonActual() {
		try {
			PanelReportes panelReportes = ventana.getPanelReportes();

			String rutaReporte = generarReporteJsonDesdeNegocio(panelReportes);
			mostrarInformacion("Reporte JSON generado en: " + rutaReporte);
		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private String generarReporteJsonDesdeNegocio(PanelReportes panelReportes) throws Exception {
		if (panelReportes.esReporteVentasDiarias()) {
			return gestionReporte.generarReporteVentasDiarias(panelReportes.obtenerFechaReporte());
		}

		if (panelReportes.esReporteVentasMensuales()) {
			return gestionReporte.generarReporteVentasMensuales(panelReportes.obtenerMesReporte(),
					panelReportes.obtenerAnioReporte());
		}

		if (panelReportes.esReporteVentasAnuales()) {
			return gestionReporte.generarReporteVentasAnuales(panelReportes.obtenerAnioReporte());
		}

		if (panelReportes.esReporteUtilidadBruta()) {
			return gestionReporte.generarReporteUtilidadBruta(panelReportes.obtenerFechaInicioReporte(),
					panelReportes.obtenerFechaFinReporte());
		}

		if (panelReportes.esReporteProductosMasVendidos()) {
			return gestionReporte.generarReporteProductosMasVendidos(panelReportes.obtenerFechaInicioReporte(),
					panelReportes.obtenerFechaFinReporte());
		}

		if (panelReportes.esReporteClientesMayorCompra()) {
			return gestionReporte.generarReporteClientesMayorCompra(panelReportes.obtenerFechaInicioReporte(),
					panelReportes.obtenerFechaFinReporte());
		}

		if (panelReportes.esReporteVentasFormaPago()) {
			return gestionReporte.generarReporteVentasFormaPago(panelReportes.obtenerFechaInicioReporte(),
					panelReportes.obtenerFechaFinReporte());
		}

		if (panelReportes.esReporteInventarioValorizado()) {
			return gestionReporte.generarReporteInventarioValorizado();
		}

		if (panelReportes.esReporteResumenContable()) {
			return gestionReporte.generarReporteResumenContable(panelReportes.obtenerFechaInicioReporte(),
					panelReportes.obtenerFechaFinReporte());
		}

		if (panelReportes.esReporteResumenFinancieroDiario()) {
			return gestionReporte.generarReporteResumenFinancieroDiario(panelReportes.obtenerFechaReporte());
		}

		throw new Exception("El reporte seleccionado aun no tiene generacion JSON implementada.");
	}

	private void buscarReporte() {
		try {
			PanelReportes panelReportes = ventana.getPanelReportes();

			if (panelReportes.esReporteVentasDiarias()) {
				LocalDate fecha = panelReportes.obtenerFechaReporte();
				panelReportes.mostrarResumenVentas(gestionReporte.obtenerTotalVentasDiarias(fecha));
				return;
			}

			if (panelReportes.esReporteVentasMensuales()) {
				int mes = panelReportes.obtenerMesReporte();
				int anio = panelReportes.obtenerAnioReporte();
				panelReportes.mostrarResumenVentas(gestionReporte.obtenerTotalVentasMensuales(mes, anio));
				return;
			}

			if (panelReportes.esReporteVentasAnuales()) {
				int anio = panelReportes.obtenerAnioReporte();
				panelReportes.mostrarResumenVentas(gestionReporte.obtenerTotalVentasAnuales(anio));
				return;
			}

			if (panelReportes.esReporteUtilidadBruta()) {
				LocalDate fechaInicio = panelReportes.obtenerFechaInicioReporte();
				LocalDate fechaFin = panelReportes.obtenerFechaFinReporte();
				panelReportes.mostrarUtilidadBruta(gestionReporte.obtenerUtilidadBruta(fechaInicio, fechaFin));
				return;
			}

			if (panelReportes.esReporteVentasFormaPago()) {
				LocalDate fechaInicio = panelReportes.obtenerFechaInicioReporte();
				LocalDate fechaFin = panelReportes.obtenerFechaFinReporte();
				List<ResumenFormaPagoDTO> resumenFormaPago = gestionReporte.obtenerVentasPorFormaPago(fechaInicio,
						fechaFin);
				panelReportes.mostrarVentasPorFormaPago(resumenFormaPago);
				return;
			}

			if (panelReportes.esReporteClientesMayorCompra()) {
				LocalDate fechaInicio = panelReportes.obtenerFechaInicioReporte();
				LocalDate fechaFin = panelReportes.obtenerFechaFinReporte();
				List<ResumenClienteDTO> resumenClientes = gestionReporte.obtenerClientesMayorVolumenCompra(fechaInicio,
						fechaFin);
				panelReportes.mostrarClientesMayorCompra(resumenClientes);
				return;
			}

			if (panelReportes.esReporteInventarioValorizado()) {
				List<ResumenInventarioValorizadoDTO> resumenInventario = gestionReporte.obtenerInventarioValorizado();
				panelReportes.mostrarInventarioValorizado(resumenInventario);
				return;
			}

			if (panelReportes.esReporteResumenContable()) {
				LocalDate fechaInicio = panelReportes.obtenerFechaInicioReporte();
				LocalDate fechaFin = panelReportes.obtenerFechaFinReporte();
				ResumenContableDTO resumenContable = gestionReporte.obtenerResumenContable(fechaInicio, fechaFin);
				panelReportes.mostrarResumenContable(resumenContable);
				return;
			}

			if (panelReportes.esReporteResumenFinancieroDiario()) {
				LocalDate fecha = panelReportes.obtenerFechaReporte();
				ResumenFinancieroDiarioDTO resumenFinanciero = gestionReporte.obtenerResumenFinancieroDiario(fecha);
				panelReportes.mostrarResumenFinancieroDiario(resumenFinanciero);
				return;
			}

			if (!panelReportes.esReporteProductosMasVendidos()) {
				throw new Exception("El reporte seleccionado aun no esta implementado: "
						+ panelReportes.obtenerTipoReporteSeleccionado());
			}

			LocalDate fechaInicio = panelReportes.obtenerFechaInicioReporte();
			LocalDate fechaFin = panelReportes.obtenerFechaFinReporte();

			
			if (panelReportes.haySeleccion()) {
				String codigoSeleccionado = panelReportes.obtenerTextoSeleccionado(0);
				if (codigoSeleccionado == null || codigoSeleccionado.isBlank()) {
					throw new Exception("Debe seleccionar un producto valido en la tabla.");
				}
				String rutaReporte = gestionReporte.generarReporteProducto(codigoSeleccionado, fechaInicio, fechaFin);
				mostrarInformacion("Reporte JSON del producto generado en: " + rutaReporte);
				return;
			}
			java.util.List<ResumenProductoDTO> resumenDTOs = gestionReporte
					.obtenerResumenProductosMasVendidos(fechaInicio, fechaFin);
			panelReportes.mostrarResumenProductos(resumenDTOs);
		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	// CONSULTAS DEL SISTEMA
	private boolean manejarEventosConsultas(String comando) {
		switch (comando) {
		case CMD_CONSULTAR_SISTEMA:
			ejecutarConsultaSistema();
			return true;

		default:
			return false;
		}
	}

	private void refrescarDatosConsultas() {
		try {
			LogUtil.info("Refrescando datos para PanelConsultas");
			PanelConsultas panelConsultas = ventana.getPanelConsultas();
			panelConsultas.cargarProveedores(gestionProveedor.obtenerProveedores());
			panelConsultas.cargarClientes(gestionCliente.obtenerClientes());
			panelConsultas.cargarCuentas();
		} catch (Exception ex) {
			LogUtil.error("Error al refrescar datos de consultas: " + ex.getMessage(), ex);
			mostrarError(ex.getMessage());
		}
	}

	private void ejecutarConsultaSistema() {
		try {
			LogUtil.info("Ejecutando consulta del sistema desde GUI");
			PanelConsultas panelConsultas = ventana.getPanelConsultas();

			if (panelConsultas.esConsultaVentasPorFecha()) {
				LocalDate fecha = panelConsultas.obtenerFechaConsulta();
				panelConsultas.cargarVentasPorFecha(gestionConsultas.obtenerVentasPorFecha(fecha));
				return;
			}

			if (panelConsultas.esConsultaComprasPorProveedor()) {
				String codigoProveedor = panelConsultas.obtenerProveedorSeleccionado();
				LocalDate fechaInicio = panelConsultas.obtenerFechaInicio();
				LocalDate fechaFin = panelConsultas.obtenerFechaFin();

				panelConsultas.cargarComprasPorProveedor(
					gestionConsultas.obtenerComprasPorProveedor(codigoProveedor, fechaInicio, fechaFin));
				return;
			}

			if (panelConsultas.esConsultaStockBajo()) {
				panelConsultas.cargarProductosStockBajo(gestionConsultas.obtenerProductosStockBajo());
				return;
			}

			if (panelConsultas.esConsultaHistorialCliente()) {
				String codigoCliente = panelConsultas.obtenerClienteSeleccionado();
				panelConsultas.cargarHistorialCliente(gestionConsultas.obtenerHistorialCliente(codigoCliente));
				return;
			}

			if (panelConsultas.esConsultaMovimientosContables()) {
				String cuenta = panelConsultas.obtenerCuentaSeleccionada();
				String tipoMovimiento = panelConsultas.obtenerTipoMovimientoSeleccionado();
				LocalDate fechaInicio = panelConsultas.obtenerFechaInicio();
				LocalDate fechaFin = panelConsultas.obtenerFechaFin();

				panelConsultas.cargarMovimientosContables(
					gestionConsultas.obtenerMovimientosContables(cuenta, tipoMovimiento, fechaInicio, fechaFin));
				return;
			}

		} catch (Exception ex) {
			LogUtil.error("Error al ejecutar consulta del sistema: " + ex.getMessage(), ex);
			mostrarError(ex.getMessage());
		}
	}

	// METODOS AUXILIARES GENERALES
	private static DecimalFormat crearFormatoMoneda() {
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setGroupingSeparator('.');
		simbolos.setDecimalSeparator(',');

		DecimalFormat formato = new DecimalFormat("$ #,##0", simbolos);
		formato.setGroupingUsed(true);
		return formato;
	}

	private String formatearMoneda(double valor) {
		return FORMATO_MONEDA.format(valor);
	}

	private void mostrarError(String mensaje) {
		JOptionPane.showMessageDialog(ventana, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}

	private void mostrarInformacion(String mensaje) {
		JOptionPane.showMessageDialog(ventana, mensaje, "Informacion", JOptionPane.INFORMATION_MESSAGE);
	}

	private Window obtenerVentanaPadre(ActionEvent e) {
		Component componente = (Component) e.getSource();
		return SwingUtilities.getWindowAncestor(componente);
	}

	// METODOS PARA OBTENER DIALOGOS
	private DialogProducto obtenerDialogProducto(ActionEvent e) throws Exception {
		Window ventanaPadre = obtenerVentanaPadre(e);

		if (!(ventanaPadre instanceof DialogProducto)) {
			throw new Exception("Error interno: no se pudo identificar el formulario de producto.");
		}

		return (DialogProducto) ventanaPadre;
	}

	private DialogCliente obtenerDialogCliente(ActionEvent e) throws Exception {
		Window ventanaPadre = obtenerVentanaPadre(e);

		if (!(ventanaPadre instanceof DialogCliente)) {
			throw new Exception("Error interno: no se pudo identificar el formulario de cliente.");
		}

		return (DialogCliente) ventanaPadre;
	}

	private DialogHistorialCliente obtenerDialogHistorialCliente(ActionEvent e) throws Exception {
		Window ventanaPadre = obtenerVentanaPadre(e);

		if (!(ventanaPadre instanceof DialogHistorialCliente)) {
			throw new Exception("Error interno: no se pudo identificar el historial del cliente.");
		}

		return (DialogHistorialCliente) ventanaPadre;
	}

	private DialogProveedor obtenerDialogProveedor(ActionEvent e) throws Exception {
		Window ventanaPadre = obtenerVentanaPadre(e);

		if (!(ventanaPadre instanceof DialogProveedor)) {
			throw new Exception("Error interno: no se pudo identificar el formulario de proveedor.");
		}

		return (DialogProveedor) ventanaPadre;
	}

	private DialogMovimientoInventario obtenerDialogMovimientoInventario(ActionEvent e) throws Exception {
		Window ventanaPadre = obtenerVentanaPadre(e);

		if (!(ventanaPadre instanceof DialogMovimientoInventario)) {
			throw new Exception("Error interno: no se pudo identificar el formulario de movimiento.");
		}

		return (DialogMovimientoInventario) ventanaPadre;
	}

	private DialogVenta obtenerDialogVenta(ActionEvent e) throws Exception {
		Window ventanaPadre = obtenerVentanaPadre(e);

		if (!(ventanaPadre instanceof DialogVenta)) {
			throw new Exception("Error interno: no se pudo identificar el formulario de venta.");
		}

		return (DialogVenta) ventanaPadre;
	}

	private DialogAnularVenta obtenerDialogAnularVenta(ActionEvent e) throws Exception {
		Window ventanaPadre = obtenerVentanaPadre(e);

		if (!(ventanaPadre instanceof DialogAnularVenta)) {
			throw new Exception("Error interno: no se pudo identificar el formulario de anulacion.");
		}

		return (DialogAnularVenta) ventanaPadre;
	}

	private DialogCompra obtenerDialogCompra(ActionEvent e) throws Exception {
		Window ventanaPadre = obtenerVentanaPadre(e);

		if (!(ventanaPadre instanceof DialogCompra)) {
			throw new Exception("Error interno: no se pudo identificar el formulario de compra.");
		}

		return (DialogCompra) ventanaPadre;
	}

	private DialogDevolucionVenta obtenerDialogDevolucionVenta(ActionEvent e) throws Exception {
		Window ventanaPadre = obtenerVentanaPadre(e);

		if (!(ventanaPadre instanceof DialogDevolucionVenta)) {
			throw new Exception("Error interno: no se pudo identificar el formulario de devolucion.");
		}

		return (DialogDevolucionVenta) ventanaPadre;
	}
}
