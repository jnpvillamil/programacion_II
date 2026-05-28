package co.uptc.edu.co.gui;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

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
import co.uptc.edu.co.gui.dialog.DialogFacturaVenta;
import co.uptc.edu.co.gui.dialog.DialogHistorialCliente;
import co.uptc.edu.co.gui.dialog.DialogMovimientoInventario;
import co.uptc.edu.co.gui.dialog.DialogProducto;
import co.uptc.edu.co.gui.dialog.DialogProveedor;
import co.uptc.edu.co.gui.dialog.DialogVenta;
import co.uptc.edu.co.interfaces.IGestionCliente;
import co.uptc.edu.co.interfaces.IGestionCompra;
import co.uptc.edu.co.interfaces.IGestionProducto;
import co.uptc.edu.co.interfaces.IGestionProveedor;
import co.uptc.edu.co.interfaces.IGestionVenta;
import co.uptc.edu.co.modelo.Compra;
import co.uptc.edu.co.modelo.Cliente;
import co.uptc.edu.co.modelo.DetalleCompra;
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.Proveedor;
import co.uptc.edu.co.modelo.Venta;

public class Evento implements ActionListener {

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

	// CONSTANTES DE COMANDOS - CONTABILIDAD
	public static final String CMD_VER_DETALLE_CONTABLE = "VerDetalleContable";

	// ATRIBUTOS
	private VentanaPrincipal ventana;
	private IGestionProducto gestionProducto;
	private IGestionCliente gestionCliente;
	private IGestionProveedor gestionProveedor;
	private IGestionVenta gestionVenta;
	private IGestionCompra gestionCompra;

	// CONSTRUCTOR
	public Evento(VentanaPrincipal ventana, TiendaConfig config) {
		this.ventana = ventana;
		this.gestionProducto = config.getGestionProducto();
		this.gestionCliente = config.getGestionCliente();
		this.gestionProveedor = config.getGestionProveedor();
		this.gestionVenta = config.getGestionVenta();
		this.gestionCompra = config.getGestionCompra();
	}

	// METODO PRINCIPAL DE EVENTOS
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
			return true;

		case REPORTES:
			ventana.irReportes();
			return true;

		case CONSULTAS:
			ventana.irConsultas();
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
			Producto producto = obtenerProductoSeleccionado();
			boolean estabaActivo = producto.estaActivo();

			String mensaje = estabaActivo ? "Esta seguro de inactivar este producto?"
					: "Esta seguro de activar este producto?";

			int confirmacion = JOptionPane.showConfirmDialog(ventana, mensaje, "Confirmar cambio de estado",
					JOptionPane.YES_NO_OPTION);

			if (confirmacion != JOptionPane.YES_OPTION) {
				return;
			}

			gestionProducto.cambiarEstadoProducto(producto.getCodigoProducto());

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

			gestionProducto.registrarMovimientoInventario(codigo, tipoMovimiento, cantidad);

			mostrarInformacion("Movimiento de inventario registrado exitosamente.");
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
		DialogCliente dialog = new DialogCliente(ventana, this);
		String codigoGenerado = gestionCliente.generarCodigoCliente();
		dialog.cargarCodigoGenerado(codigoGenerado);
		dialog.setVisible(true);
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

			DialogHistorialCliente dialog = new DialogHistorialCliente(ventana);
			dialog.cargarCliente(cliente.getCodigo(), cliente.getNombre());
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

		try {
			dialog.cargarNitGenerado(gestionProveedor.generarNIT());
		} catch (Exception ex) {
			mostrarError("No se pudo generar el NIT automático: " + ex.getMessage());
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
		DialogVenta dialog = new DialogVenta(ventana, this);
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
			dialog.dispose();

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void abrirDialogoAnularVenta() {
		try {
			PanelVenta panelVenta = ventana.getPanelVenta();

			if (!panelVenta.haySeleccion()) {
				throw new Exception("Debe seleccionar una venta.");
			}

			String numeroFactura = panelVenta.obtenerFacturaSeleccionada();
			Venta venta = gestionVenta.buscarVentaPorNumero(numeroFactura);

			if (venta == null) {
				throw new Exception("No se encontro la venta seleccionada.");
			}

			DialogAnularVenta dialog = new DialogAnularVenta(ventana, this);
			dialog.cargarVenta(venta.getNumeroFactura(), venta.getCliente(),
					venta.getFechaHora() != null ? venta.getFechaHora().toLocalDate().toString() : "",
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

			gestionVenta.anularVenta(numeroFactura);

			mostrarInformacion("Venta anulada exitosamente.");
			refrescarTablaVentas();
			dialog.dispose();

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void abrirDialogoDevolucionVenta() {
		DialogDevolucionVenta dialog = new DialogDevolucionVenta(ventana, this);
		dialog.setVisible(true);
	}

	private void guardarDevolucionVenta(ActionEvent e) {
		mostrarInformacion("Devolucion de venta pendiente de implementacion.");
	}

	private void abrirDialogoDetalleVenta() {
		DialogDetalleVenta dialog = new DialogDetalleVenta(ventana);
		dialog.setVisible(true);
	}

	private void abrirDialogoFacturaVenta() {
		DialogFacturaVenta dialog = new DialogFacturaVenta(ventana);
		dialog.setVisible(true);
	}

	private void refrescarTablaVentas() {
		PanelVenta panelVenta = ventana.getPanelVenta();
		panelVenta.cargarVentas(gestionVenta.obtenerVentas());
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

		default:
			return false;
		}
	}

	private void abrirDialogoNuevaCompra() {
		DialogCompra dialog = new DialogCompra(ventana, this);
		dialog.cargarProductos(gestionProducto.obtenerProductos());
		dialog.cargarProveedores(gestionProveedor.obtenerProveedores());
		dialog.getCampoNumeroFactura().setText(gestionCompra.generarNumeroFactura());
		dialog.getCampoFecha().setText(LocalDate.now().toString());
		dialog.setVisible(true);
	}

	private void registrarCompra(ActionEvent e) {
		try {
			DialogCompra dialog = obtenerDialogCompra(e);
			Compra compra = dialog.obtenerCompra();

			gestionCompra.registrarCompra(compra);
			mostrarInformacion("Compra registrada con número " + compra.getNumeroFacturaProveedor() + ".");
			dialog.dispose();
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

			dialog.cargarCompra(
				compra.getNumeroFacturaProveedor(),
				compra.getFecha() != null ? compra.getFecha().toString() : "",
				compra.getCodigoProveedor(),
				String.valueOf(compra.getSubtotal()),
				String.valueOf(compra.getImpuestos()),
				String.valueOf(compra.getTotalCompra()));

			dialog.limpiarTabla();
			if (compra.getDetalles() != null) {
				for (DetalleCompra detalle : compra.getDetalles()) {
					dialog.agregarDetalle(
						detalle.getProducto().getCodigoProducto(),
						detalle.getProducto().getNombreProducto(),
						String.valueOf(detalle.getCantidad()),
						String.valueOf(detalle.getCostoUnitario()),
						String.valueOf(detalle.getImpuestos()),
						String.valueOf(detalle.getSubtotal()),
						String.valueOf(detalle.getTotalCompra()));
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
			dialog.cargarCompra(
				compra.getNumeroFacturaProveedor(),
				compra.getFecha() != null ? compra.getFecha().toString() : "",
				compra.getCodigoProveedor(),
				String.valueOf(compra.getTotalCompra()));

			dialog.setVisible(true);

			if (dialog.isCompraAnulada()) {
				gestionCompra.anularCompra(compra.getNumeroFacturaProveedor(), dialog.getMotivoAnulacion());
				refrescarTablaCompras();
				refrescarTablaProductos();
				mostrarInformacion("Compra anulada exitosamente.");
			}
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
		DialogDetalleContable dialog = new DialogDetalleContable(ventana);
		dialog.setVisible(true);
	}

	// METODOS AUXILIARES GENERALES
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
}
