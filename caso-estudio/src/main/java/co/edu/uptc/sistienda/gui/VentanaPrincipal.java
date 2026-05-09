package co.edu.uptc.sistienda.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import co.edu.uptc.sistienda.clientes.gui.DialogoCliente;
import co.edu.uptc.sistienda.clientes.gui.PanelClientes;
import co.edu.uptc.sistienda.contabilidad.gui.PanelContabilidad;
import co.edu.uptc.sistienda.modelo.Cliente;
import co.edu.uptc.sistienda.modelo.DetalleVenta;
import co.edu.uptc.sistienda.modelo.Producto;
import co.edu.uptc.sistienda.modelo.Proveedor;
import co.edu.uptc.sistienda.modelo.Venta;
import co.edu.uptc.sistienda.negocio.config.SistiendaConfig;
import co.edu.uptc.sistienda.negocio.dto.CredencialDto;
import co.edu.uptc.sistienda.productos.gui.DialogoProducto;
import co.edu.uptc.sistienda.productos.gui.PanelProductos;
import co.edu.uptc.sistienda.proveedores.gui.DialogoProveedor;
import co.edu.uptc.sistienda.proveedores.gui.PanelProveedores;
import co.edu.uptc.sistienda.ventas.gui.DialogoSelectorProducto;

public class VentanaPrincipal extends JFrame {

	private static final String TITULO_VENTANA = "Sistienda – Iniciar sesión";
	private static final String TITULO_SISTEMA = "Sistema de Gestión Contable y Comercial";

	// Identificadores de tarjetas para CardLayout
	private static final String TARJETA_LOGIN = "LOGIN";
	private static final String TARJETA_PRINCIPAL = "PRINCIPAL";
	private static final String TARJETA_CAJERO = "CAJERO";
	private static final String TARJETA_BIENVENIDA = "BIENVENIDA";

	// Capa de negocio
	private SistiendaConfig configuracion;
	private Evento evento;
	private String rolActivo;

	// Paneles de navegación
	private PanelLogin panelLogin;
	private PanelDashboard panelDashboard;
	private PanelProductos panelProductos;
	private PanelClientes panelClientes;
	private PanelProveedores panelProveedores;
	private PanelCajero panelCajero;
	private PanelContabilidad panelContabilidad;
	
	// Botones del menú (para control de permisos)
	private JButton btnProductos;
	private JButton btnClientes;
	private JButton btnProveedores;
	private JButton btnContabilidad;

	// Diálogos activos
	private DialogoProducto dialogoProducto;
	private DialogoCliente dialogoCliente;
	private DialogoProveedor dialogoProveedor;

	// Layout principal
	private CardLayout layoutPrincipal;
	private JPanel panelRaiz;
	private JPanel panelContenidoCentral;
	private CardLayout layoutContenido;

	public VentanaPrincipal() {
		configuracion = new SistiendaConfig();
		evento = new Evento(this);

		setTitle(TITULO_VENTANA);
		setSize(900, 580);
		setMinimumSize(new Dimension(800, 500));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		layoutPrincipal = new CardLayout();
		panelRaiz = new JPanel(layoutPrincipal);

		// Tarjeta LOGIN
		panelLogin = new PanelLogin(evento);
		JPanel envolventeCentrado = new JPanel(new BorderLayout());
		JPanel columna = new JPanel();
		columna.setLayout(new BoxLayout(columna, BoxLayout.Y_AXIS));
		columna.setBorder(BorderFactory.createEmptyBorder(80, 200, 80, 200));
		columna.add(panelLogin);
		envolventeCentrado.add(columna, BorderLayout.CENTER);
		panelRaiz.add(envolventeCentrado, TARJETA_LOGIN);

		// Tarjeta PRINCIPAL
		// Solo la verá el Administrador
		JPanel panelPrincipal = construirPanelPrincipal();
		panelRaiz.add(panelPrincipal, TARJETA_PRINCIPAL);

		// Tarjeta CAJERO
		panelCajero = new PanelCajero(evento);
		JPanel envCajero = new JPanel(new BorderLayout());
		envCajero.add(construirBarraSuperior(), BorderLayout.NORTH);
		envCajero.add(panelCajero, BorderLayout.CENTER);
		panelRaiz.add(envCajero, TARJETA_CAJERO);

		// Tarjeta BIENVENIDA
		// La verán todos los roles que no sean Administrador
		JPanel panelBienvenida = construirPanelBienvenida();
		panelRaiz.add(panelBienvenida, TARJETA_BIENVENIDA);

		add(panelRaiz);
		layoutPrincipal.show(panelRaiz, TARJETA_LOGIN);
	}

	// Construcción de paneles

	private JPanel construirPanelBienvenida() {
		JPanel raiz = new JPanel(new BorderLayout());

		// Barra superior con botón Salir
		JPanel barraSuperior = construirBarraSuperior();
		raiz.add(barraSuperior, BorderLayout.NORTH);

		// Mensaje centrado en pantalla
		JPanel centro = new JPanel(new java.awt.GridBagLayout());
		JLabel mensaje = new JLabel("Bienvenido a Sistienda");
		mensaje.setFont(mensaje.getFont().deriveFont(java.awt.Font.BOLD, 24f));
		centro.add(mensaje);
		raiz.add(centro, BorderLayout.CENTER);

		return raiz;
	}

	private JPanel construirPanelPrincipal() {
		JPanel raiz = new JPanel(new BorderLayout());

		// Barra superior
		JPanel barraSuperior = construirBarraSuperior();
		raiz.add(barraSuperior, BorderLayout.NORTH);

		// Menú lateral
		JPanel menuLateral = construirMenuLateral();
		raiz.add(menuLateral, BorderLayout.WEST);

		// Área de contenido central con CardLayout
		layoutContenido = new CardLayout();
		panelContenidoCentral = new JPanel(layoutContenido);

		panelDashboard = new PanelDashboard();
		panelProductos = new PanelProductos(evento);
		panelClientes = new PanelClientes(evento);
		panelProveedores = new PanelProveedores(evento);
		panelContabilidad = new PanelContabilidad();
		
		panelContenidoCentral.add(panelDashboard, Evento.MENU_DASHBOARD);
		panelContenidoCentral.add(panelProductos, Evento.MENU_PRODUCTOS);
		panelContenidoCentral.add(panelClientes, Evento.MENU_CLIENTES);
		panelContenidoCentral.add(panelProveedores, Evento.MENU_PROVEEDORES);
		panelContenidoCentral.add(panelContabilidad, "CONTABILIDAD");

		raiz.add(panelContenidoCentral, BorderLayout.CENTER);
		return raiz;
	}

	private JPanel construirBarraSuperior() {
		JPanel barraSuperior = new JPanel(new BorderLayout());
		barraSuperior.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
		barraSuperior.add(new JLabel(TITULO_SISTEMA), BorderLayout.WEST);
		JButton botonCerrarSesion = new JButton("Salir");
		botonCerrarSesion.setActionCommand(Evento.SALIR);
		botonCerrarSesion.addActionListener(evento);
		barraSuperior.add(botonCerrarSesion, BorderLayout.EAST);
		return barraSuperior;
	}

	private JPanel construirMenuLateral() {
		JPanel menu = new JPanel();
		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
		menu.setPreferredSize(new Dimension(160, 0));
		menu.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, java.awt.Color.GRAY));

		menu.add(crearSeparadorMenu("PRINCIPAL"));
		menu.add(crearBotonMenu("Dashboard", Evento.MENU_DASHBOARD));
		menu.add(crearSeparadorMenu("CATÁLOGOS"));
		btnProductos = crearBotonMenu("Productos", Evento.MENU_PRODUCTOS);
	    btnClientes = crearBotonMenu("Clientes", Evento.MENU_CLIENTES);
	    btnProveedores = crearBotonMenu("Proveedores", Evento.MENU_PROVEEDORES);
		btnContabilidad = crearBotonMenu("Contabilidad", Evento.MENU_CONTABILIDAD);
		
		menu.add(btnProductos);
	    menu.add(btnClientes);
	    menu.add(btnProveedores);
	    menu.add(btnContabilidad);
		
		return menu;
	}

	private JLabel crearSeparadorMenu(String texto) {
		JLabel etiqueta = new JLabel(texto);
		etiqueta.setBorder(BorderFactory.createEmptyBorder(10, 8, 2, 8));
		etiqueta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		return etiqueta;
	}

	private JButton crearBotonMenu(String texto, String accion) {
		JButton boton = new JButton(texto);
		boton.setActionCommand(accion);
		boton.addActionListener(evento);
		boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
		boton.setHorizontalAlignment(JButton.LEFT);
		boton.setBorderPainted(false);
		return boton;
	}

	// Login

	public void loguear() {
		try {
			CredencialDto credencial = panelLogin.obtenerCredencialesIngresadas();
			if (configuracion.getGestionDeSeguridad().validarLogueo(credencial)) {
				rolActivo = credencial.getRol();
				setTitle("Sistienda – " + rolActivo);
				mostrarPantallaInicial();
			} else {
				JOptionPane.showMessageDialog(this, "Usuario, contraseña o rol incorrecto.", "Acceso denegado",
						JOptionPane.WARNING_MESSAGE);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void mostrarPantallaInicial() {
		if ("Administrador".equals(rolActivo) || "Contador".equals(rolActivo)) {
			// Administrador: muestra la tarjeta con menú lateral y el dashboard
			aplicarPermisos();
			mostrarDashboard();
			layoutPrincipal.show(panelRaiz, TARJETA_PRINCIPAL);
		} else if ("Cajero".equals(rolActivo)) {
			// Cajero: muestra la tarjeta del cajeero con el panel de ventas
			mostrarPanelRegistrarVenta();
			layoutPrincipal.show(panelRaiz, TARJETA_CAJERO);
		} else {
			// Cualquier otro rol: muestra solo la pantalla de bienvenida, sin menú
			layoutPrincipal.show(panelRaiz, TARJETA_BIENVENIDA);
		}
	}

	// Navegación

	public void mostrarDashboard() {
		panelDashboard.actualizarResumen(configuracion.getGestionProducto().obtenerProductosConStockBajoMinimo());
		layoutContenido.show(panelContenidoCentral, Evento.MENU_DASHBOARD);
	}

	public void mostrarPanelProductos() {
		refrescarTablaProductos();
		layoutContenido.show(panelContenidoCentral, Evento.MENU_PRODUCTOS);
	}

	public void mostrarPanelClientes() {
		refrescarTablaClientes();
		layoutContenido.show(panelContenidoCentral, Evento.MENU_CLIENTES);
	}

	public void mostrarPanelProveedores() {
		refrescarTablaProveedores();
		layoutContenido.show(panelContenidoCentral, Evento.MENU_PROVEEDORES);
	}
	public void mostrarPanelContabilidad() {

	    List<Venta> ventas =
	        configuracion.getGestionVenta().obtenerListaVentas();

	    System.out.println("Ventas encontradas: " + ventas.size());

	    panelContabilidad.cargarTotalVentas(ventas);

	    layoutContenido.show(panelContenidoCentral, "CONTABILIDAD");
	}

	// Navegación Cajero

	public void mostrarPanelRegistrarVenta() {
		String numeroFacturaNueva = configuracion.getGestionVenta().generarNumeroFactura();
		String fechaHoraActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		List<Cliente> clientesDisponibles = configuracion.getGestionCliente().obtenerListaClientes();
		panelCajero.mostrarRegistrarVenta(numeroFacturaNueva, fechaHoraActual, clientesDisponibles);
	}

	public void mostrarPanelVentasRegistradas() {
		panelCajero.mostrarVentasRegistradas(configuracion.getGestionVenta().obtenerListaVentas());
	}


	// Acciones Cajero

	public void abrirSelectorProducto() {
		List<Producto> productosDisponibles = configuracion.getGestionProducto().obtenerListaProductos();
		DialogoSelectorProducto dialogoSelectorProducto = new DialogoSelectorProducto(productosDisponibles);
		dialogoSelectorProducto.setVisible(true);
		DetalleVenta itemSeleccionado = dialogoSelectorProducto.getItemResultado();
		if (itemSeleccionado != null) {
			panelCajero.agregarProductoALaVenta(itemSeleccionado);
		}
	}

	public void registrarVenta() {
	    try {
	        String codigoClienteVenta = panelCajero.getCodigoClienteSeleccionado();
	        if (codigoClienteVenta == null) {
	            JOptionPane.showMessageDialog(this, "Seleccione un cliente", "Aviso", JOptionPane.WARNING_MESSAGE);
	            return;
	        }
	        if (panelCajero.getProductosAgregados().isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Agregue al menos un producto", "Aviso",
	                    JOptionPane.WARNING_MESSAGE);
	            return;
	        }
	        Cliente clienteDeVenta = configuracion.getGestionCliente().consultarClientePorCodigo(codigoClienteVenta);
	        Venta nuevaVenta = new Venta(panelCajero.getNumeroFactura(), clienteDeVenta,
	                panelCajero.getFormaPagoSeleccionada());
	        for (DetalleVenta itemDeVenta : panelCajero.getProductosAgregados()) {
	            nuevaVenta.agregarItem(itemDeVenta);
	        }
	        configuracion.getGestionVenta().resgistrarVenta(nuevaVenta);
	        JOptionPane.showMessageDialog(this, "Venta Registrada correctamente");
	        mostrarPanelRegistrarVenta();
	    } catch (Exception ex) {
	        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	public void cancelarVenta() {
		mostrarPanelRegistrarVenta();
	}

	public void anularVenta() {
		String numeroFacturaAnular = panelCajero.obtenerNumeroFacturaSeleccionada();
		if (numeroFacturaAnular == null) {
			JOptionPane.showMessageDialog(this, "Seleccione una venta.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		int confirmacionAnulacion = JOptionPane.showConfirmDialog(this,
				"¿Desea anular la venta " + numeroFacturaAnular + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
		if (confirmacionAnulacion == JOptionPane.YES_OPTION) {
			try {
				configuracion.getGestionVenta().anularVenta(numeroFacturaAnular);
				mostrarPanelVentasRegistradas();
				JOptionPane.showMessageDialog(this, "Venta anulada correctamente.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void consultarVentasPorFecha() {
		java.time.LocalDate fechaConsulta = panelCajero.obtenerFechaBusqueda();
		if (fechaConsulta == null)
			return;
		List<Venta> ventasEnFecha = configuracion.getGestionVenta().consultarVentasPorFecha(fechaConsulta);
		panelCajero.poblarTablaVentas(ventasEnFecha);
		if (ventasEnFecha.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No hay ventas para esa fecha.", "Sin resultados",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// CRUD Productos

	private void refrescarTablaProductos() {
		List<Producto> lista = configuracion.getGestionProducto().obtenerListaProductos();
		panelProductos.poblarTabla(lista);
		List<Producto> bajoMinimo = configuracion.getGestionProducto().obtenerProductosConStockBajoMinimo();
		if (!bajoMinimo.isEmpty()) {
			panelProductos
					.mostrarMensajeAlerta(bajoMinimo.size() + " producto(s) con stock por debajo del mínimo definido.");
		} else {
			panelProductos.ocultarMensajeAlerta();
		}
	}

	public void abrirDialogoNuevoProducto() {
		dialogoProducto = new DialogoProducto(evento, true);
		dialogoProducto.setVisible(true);
	}

	public void abrirDialogoEditarProducto() {
		String codigo = panelProductos.obtenerCodigoFilaSeleccionada();
		if (codigo == null) {
			JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla.", "Aviso",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		Producto producto = configuracion.getGestionProducto().consultarProductoPorCodigo(codigo);
		if (producto == null)
			return;
		dialogoProducto = new DialogoProducto(evento, false);
		dialogoProducto.cargarDatosEnFormulario(producto);
		dialogoProducto.setVisible(true);
	}

	public void guardarNuevoProducto() {
		try {
			Producto nuevo = dialogoProducto.capturarDatosFormulario();
			configuracion.getGestionProducto().registrarNuevoProducto(nuevo);
			cerrarDialogoProducto();
			refrescarTablaProductos();
			JOptionPane.showMessageDialog(this, "Producto registrado correctamente.");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void guardarEdicionProducto() {
		try {
			Producto editado = dialogoProducto.capturarDatosFormulario();
			configuracion.getGestionProducto().modificarProducto(editado);
			cerrarDialogoProducto();
			refrescarTablaProductos();
			JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void inactivarProductoSeleccionado() {
		String codigo = panelProductos.obtenerCodigoFilaSeleccionada();
		if (codigo == null) {
			JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla.", "Aviso",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea inactivar el producto con código: " + codigo + "?",
				"Confirmar inactivación", JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {
			try {
				configuracion.getGestionProducto().inactivarProducto(codigo);
				refrescarTablaProductos();
				JOptionPane.showMessageDialog(this, "Producto inactivado correctamente.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void activarProductoSeleccionado() {
		String codigo = panelProductos.obtenerCodigoFilaSeleccionada();
		if (codigo == null) {
			JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla.", "Aviso",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea activar el producto con código: " + codigo + "?",
				"Confirmar activación", JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {
			try {
				configuracion.getGestionProducto().activarProducto(codigo);
				refrescarTablaProductos();
				JOptionPane.showMessageDialog(this, "Producto activado correctamente.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void buscarProducto() {
		String texto = panelProductos.obtenerTextoBusqueda().toLowerCase();
		List<Producto> todos = configuracion.getGestionProducto().obtenerListaProductos();
		List<Producto> filtrado = todos.stream().filter(p -> p.getCodigoInterno().toLowerCase().contains(texto)
				|| p.getNombreProducto().toLowerCase().contains(texto)).collect(Collectors.toList());
		panelProductos.poblarTabla(filtrado);
	}

	public void limpiarBusquedaProductos() {
		panelProductos.limpiarCampoBusqueda();
		refrescarTablaProductos();
	}

	public void cerrarDialogoProducto() {
		if (dialogoProducto != null) {
			dialogoProducto.setVisible(false);
			dialogoProducto = null;
		}
	}

	// CRUD Clientes

	private void refrescarTablaClientes() {
		panelClientes.poblarTabla(configuracion.getGestionCliente().obtenerListaClientes());
	}

	public void abrirDialogoNuevoCliente() {
		dialogoCliente = new DialogoCliente(evento, true);
		dialogoCliente.setVisible(true);
	}

	public void abrirDialogoEditarCliente() {
		String codigo = panelClientes.obtenerCodigoFilaSeleccionada();
		if (codigo == null) {
			JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla.", "Aviso",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		Cliente cliente = configuracion.getGestionCliente().consultarClientePorCodigo(codigo);
		if (cliente == null)
			return;
		dialogoCliente = new DialogoCliente(evento, false);
		dialogoCliente.cargarDatosEnFormulario(cliente);
		dialogoCliente.setVisible(true);
	}

	public void guardarNuevoCliente() {
		try {
			Cliente nuevo = dialogoCliente.capturarDatosFormulario();
			configuracion.getGestionCliente().registrarNuevoCliente(nuevo);
			cerrarDialogoCliente();
			refrescarTablaClientes();
			JOptionPane.showMessageDialog(this, "Cliente registrado correctamente.");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void guardarEdicionCliente() {
		try {
			Cliente editado = dialogoCliente.capturarDatosFormulario();
			configuracion.getGestionCliente().modificarCliente(editado);
			cerrarDialogoCliente();
			refrescarTablaClientes();
			JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void inactivarClienteSeleccionado() {
		String codigo = panelClientes.obtenerCodigoFilaSeleccionada();
		if (codigo == null) {
			JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla.", "Aviso",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea inactivar el cliente con código: " + codigo + "?",
				"Confirmar inactivación", JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {
			try {
				configuracion.getGestionCliente().inactivarCliente(codigo);
				refrescarTablaClientes();
				JOptionPane.showMessageDialog(this, "Cliente inactivado correctamente.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void activarClienteSeleccionado() {
		String codigo = panelClientes.obtenerCodigoFilaSeleccionada();
		if (codigo == null) {
			JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla.", "Aviso",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea activar el cliente con código: " + codigo + "?",
				"Confirmar activación", JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {
			try {
				configuracion.getGestionCliente().activarCliente(codigo);
				refrescarTablaClientes();
				JOptionPane.showMessageDialog(this, "Cliente activado correctamente.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void buscarCliente() {
		String texto = panelClientes.obtenerTextoBusqueda().toLowerCase();
		List<Cliente> todos = configuracion.getGestionCliente().obtenerListaClientes();
		List<Cliente> filtrado = todos.stream()
				.filter(c -> c.getCodigoCliente().toLowerCase().contains(texto)
						|| c.getNombreCompletoORazonSocial().toLowerCase().contains(texto)
						|| c.getNumeroIdentificacion().toLowerCase().contains(texto))
				.collect(Collectors.toList());
		panelClientes.poblarTabla(filtrado);
	}

	public void limpiarBusquedaClientes() {
		panelClientes.limpiarCampoBusqueda();
		refrescarTablaClientes();
	}

	public void cerrarDialogoCliente() {
		if (dialogoCliente != null) {
			dialogoCliente.setVisible(false);
			dialogoCliente = null;
		}
	}

	// CRUD Proveedores

	private void refrescarTablaProveedores() {
		panelProveedores.poblarTabla(configuracion.getGestionProveedor().obtenerListaProveedores());
	}

	public void abrirDialogoNuevoProveedor() {
		dialogoProveedor = new DialogoProveedor(evento, true);
		dialogoProveedor.setVisible(true);
	}

	public void abrirDialogoEditarProveedor() {
		String codigo = panelProveedores.obtenerCodigoFilaSeleccionada();
		if (codigo == null) {
			JOptionPane.showMessageDialog(this, "Seleccione un proveedor de la tabla.", "Aviso",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		Proveedor proveedor = configuracion.getGestionProveedor().consultarProveedorPorCodigo(codigo);
		if (proveedor == null)
			return;
		dialogoProveedor = new DialogoProveedor(evento, false);
		dialogoProveedor.cargarDatosEnFormulario(proveedor);
		dialogoProveedor.setVisible(true);
	}

	public void guardarNuevoProveedor() {
		try {
			Proveedor nuevo = dialogoProveedor.capturarDatosFormulario();
			configuracion.getGestionProveedor().registrarNuevoProveedor(nuevo);
			cerrarDialogoProveedor();
			refrescarTablaProveedores();
			JOptionPane.showMessageDialog(this, "Proveedor registrado correctamente.");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void guardarEdicionProveedor() {
		try {
			Proveedor editado = dialogoProveedor.capturarDatosFormulario();
			configuracion.getGestionProveedor().modificarProveedor(editado);
			cerrarDialogoProveedor();
			refrescarTablaProveedores();
			JOptionPane.showMessageDialog(this, "Proveedor actualizado correctamente.");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void inactivarProveedorSeleccionado() {
		String codigo = panelProveedores.obtenerCodigoFilaSeleccionada();
		if (codigo == null) {
			JOptionPane.showMessageDialog(this, "Seleccione un proveedor de la tabla.", "Aviso",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea inactivar el proveedor con código: " + codigo + "?",
				"Confirmar inactivación", JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {
			try {
				configuracion.getGestionProveedor().inactivarProveedor(codigo);
				refrescarTablaProveedores();
				JOptionPane.showMessageDialog(this, "Proveedor inactivado correctamente.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void activarProveedorSeleccionado() {
		String codigo = panelProveedores.obtenerCodigoFilaSeleccionada();
		if (codigo == null) {
			JOptionPane.showMessageDialog(this, "Seleccione un proveedor de la tabla.", "Aviso",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea activar el proveedor con código: " + codigo + "?",
				"Confirmar activación", JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {
			try {
				configuracion.getGestionProveedor().activarProveedor(codigo);
				refrescarTablaProveedores();
				JOptionPane.showMessageDialog(this, "Proveedor activo correctamente.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void buscarProveedor() {
		String texto = panelProveedores.obtenerTextoBusqueda().toLowerCase();
		List<Proveedor> todos = configuracion.getGestionProveedor().obtenerListaProveedores();
		List<Proveedor> filtrado = todos.stream()
				.filter(p -> p.getCodigoProveedor().toLowerCase().contains(texto)
						|| p.getRazonSocial().toLowerCase().contains(texto) || p.getNit().toLowerCase().contains(texto))
				.collect(Collectors.toList());
		panelProveedores.poblarTabla(filtrado);
	}

	public void limpiarBusquedaProveedores() {
		panelProveedores.limpiarCampoBusqueda();
		refrescarTablaProveedores();
	}

	public void cerrarDialogoProveedor() {
		if (dialogoProveedor != null) {
			dialogoProveedor.setVisible(false);
			dialogoProveedor = null;
		}
	}
	private void aplicarPermisos() {
	    if ("Contador".equals(rolActivo)) {
	        btnProductos.setVisible(false);
	        btnClientes.setVisible(false);
	        btnProveedores.setVisible(false);
	        btnContabilidad.setVisible(true);
	    }
	}

	// Cerrar sesión

	public void cerrarSesion() {
		rolActivo = null;
		setTitle(TITULO_VENTANA);
		layoutPrincipal.show(panelRaiz, TARJETA_LOGIN);
	}

}
