package co.edu.uptc.sistienda.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Evento implements ActionListener {

	// Login
	public static final String INGRESAR = "INGRESAR AL SISTEMA";

	// Menú lateral
	public static final String MENU_DASHBOARD = "MENU_DASHBOARD";
	public static final String MENU_PRODUCTOS = "MENU_PRODUCTOS";
	public static final String MENU_CLIENTES = "MENU_CLIENTES";
	public static final String MENU_PROVEEDORES = "MENU_PROVEEDORES";
	public static final String MENU_CONTABILIDAD = "MENU_CONTABILIDAD";
	public static final String MENU_COMPRAS = "MENU_COMPRAS";
	public static final String SALIR = "SALIR";

	// Productos
	public static final String NUEVO_PRODUCTO = "NUEVO_PRODUCTO";
	public static final String EDITAR_PRODUCTO = "EDITAR_PRODUCTO";
	public static final String INACTIVAR_PRODUCTO = "INACTIVAR_PRODUCTO";
	public static final String ACTIVAR_PRODUCTO = "ACTIVAR_PRODUCTO";
	public static final String BUSCAR_PRODUCTO = "BUSCAR_PRODUCTO";
	public static final String LIMPIAR_PRODUCTO = "LIMPIAR_PRODUCTO";
	public static final String GUARDAR_PRODUCTO = "GUARDAR_PRODUCTO";
	public static final String ACTUALIZAR_PRODUCTO = "ACTUALIZAR_PRODUCTO";
	public static final String CANCELAR_PRODUCTO = "CANCELAR_PRODUCTO";

	// Clientes
	public static final String NUEVO_CLIENTE = "NUEVO_CLIENTE";
	public static final String EDITAR_CLIENTE = "EDITAR_CLIENTE";
	public static final String INACTIVAR_CLIENTE = "INACTIVAR_CLIENTE";
	public static final String ACTIVAR_CLIENTE = "ACTIVAR_CLIENTE";
	public static final String BUSCAR_CLIENTE = "BUSCAR_CLIENTE";
	public static final String LIMPIAR_CLIENTE = "LIMPIAR_CLIENTE";
	public static final String GUARDAR_CLIENTE = "GUARDAR_CLIENTE";
	public static final String ACTUALIZAR_CLIENTE = "ACTUALIZAR_CLIENTE";
	public static final String CANCELAR_CLIENTE = "CANCELAR_CLIENTE";

	// Proveedores
	public static final String NUEVO_PROVEEDOR = "NUEVO_PROVEEDOR";
	public static final String EDITAR_PROVEEDOR = "EDITAR_PROVEEDOR";
	public static final String INACTIVAR_PROVEEDOR = "INACTIVAR_PROVEEDOR";
	public static final String ACTIVAR_PROVEEDOR = "ACTIVAR_PROVEEDOR";
	public static final String BUSCAR_PROVEEDOR = "BUSCAR_PROVEEDOR";
	public static final String LIMPIAR_PROVEEDOR = "LIMPIAR_PROVEEDOR";
	public static final String GUARDAR_PROVEEDOR = "GUARDAR_PROVEEDOR";
	public static final String ACTUALIZAR_PROVEEDOR = "ACTUALIZAR_PROVEEDOR";
	public static final String CANCELAR_PROVEEDOR = "CANCELAR_PROVEEDOR";

	//Ventas (Cajero)
	public static final String MENU_REGISTRAR_VENTA = "MENU_REGISTRAR_VENTA";
	public static final String MENU_VENTAS_REGISTRADAS ="MENU_VENTAS_REGISTRADAS";
	public static final String MENU_STOCK_BAJO_MINIMO = "MENU_STOCK_BAJO_MINIMO";
	public static final String ABRIR_SELECTOR_PRODUCTO ="ABRIR_SELECTOR_PRODUCTO";
	public static final String REGISTRAR_VENTA = "REGISTRAR_VENTA";
	public static final String CANCELAR_VENTA = "CANCELAR_VENTA";
	public static final String ANULAR_VENTA = "ANULAR_VENTA";
	public static final String CONSULTAR_VENTAS_POR_FECHA = "CONSULTAR_VENTAS_POR_FECHA";
	public static final String MOSTRAR_TODAS_VENTAS = "MOSTRAR_TODAS_VENTAS";
	public static final String REGISTRAR_DEVOLUCION = "REGISTRAR_DEVOLUCION";
	public static final String VER_FACTURA = "VER_FACTURA";

	// Compras
	public static final String REGISTRAR_COMPRA = "REGISTRAR_COMPRA";
	
	// Contabilidad
	public static final String MENU_MOV_CONTABLES = "MENU_MOV_CONTABLES";
	public static final String MENU_REPORTES = "MENU_REPORTES";
	public static final String MENU_CONSULTAS = "MENU_CONSULTAS";
	
	private VentanaPrincipal ventana;

	public Evento(VentanaPrincipal ventana) {
		this.ventana = ventana;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String accion = e.getActionCommand();
		switch (accion) {
		case INGRESAR:
			ventana.loguear();
			break;
		case MENU_DASHBOARD:
			ventana.mostrarDashboard();
			break;
		case MENU_PRODUCTOS:
			ventana.mostrarPanelProductos();
			break;
		case MENU_CLIENTES:
			ventana.mostrarPanelClientes();
			break;
		case MENU_PROVEEDORES:
			ventana.mostrarPanelProveedores();
			break;
		case MENU_CONTABILIDAD:
		    ventana.mostrarPanelContabilidad();
		    break;
		case MENU_COMPRAS:
			ventana.mostrarPanelCompras();
			break;
		case SALIR:
			ventana.cerrarSesion();
			//System.exit(0);
			break;
		// Productos
		case NUEVO_PRODUCTO:
			ventana.abrirDialogoNuevoProducto();
			break;
		case EDITAR_PRODUCTO:
			ventana.abrirDialogoEditarProducto();
			break;
		case INACTIVAR_PRODUCTO:
			ventana.inactivarProductoSeleccionado();
			break;
		case ACTIVAR_PRODUCTO:
			ventana.activarProductoSeleccionado();
			break;
		case BUSCAR_PRODUCTO:
			ventana.buscarProducto();
			break;
		case LIMPIAR_PRODUCTO:
			ventana.limpiarBusquedaProductos();
			break;
		case GUARDAR_PRODUCTO:
			ventana.guardarNuevoProducto();
			break;
		case ACTUALIZAR_PRODUCTO:
			ventana.guardarEdicionProducto();
			break;
		case CANCELAR_PRODUCTO:
			ventana.cerrarDialogoProducto();
			break;
		// Clientes
		case NUEVO_CLIENTE:
			ventana.abrirDialogoNuevoCliente();
			break;
		case EDITAR_CLIENTE:
			ventana.abrirDialogoEditarCliente();
			break;
		case INACTIVAR_CLIENTE:
			ventana.inactivarClienteSeleccionado();
			break;
		case ACTIVAR_CLIENTE:
			ventana.activarClienteSeleccionado();
			break;
		case BUSCAR_CLIENTE:
			ventana.buscarCliente();
			break;
		case LIMPIAR_CLIENTE:
			ventana.limpiarBusquedaClientes();
			break;
		case GUARDAR_CLIENTE:
			ventana.guardarNuevoCliente();
			break;
		case ACTUALIZAR_CLIENTE:
			ventana.guardarEdicionCliente();
			break;
		case CANCELAR_CLIENTE:
			ventana.cerrarDialogoCliente();
			break;
		// Proveedores
		case NUEVO_PROVEEDOR:
			ventana.abrirDialogoNuevoProveedor();
			break;
		case EDITAR_PROVEEDOR:
			ventana.abrirDialogoEditarProveedor();
			break;
		case INACTIVAR_PROVEEDOR:
			ventana.inactivarProveedorSeleccionado();
			break;
		case ACTIVAR_PROVEEDOR:
			ventana.activarProveedorSeleccionado();
			break;
		case BUSCAR_PROVEEDOR:
			ventana.buscarProveedor();
			break;
		case LIMPIAR_PROVEEDOR:
			ventana.limpiarBusquedaProveedores();
			break;
		case GUARDAR_PROVEEDOR:
			ventana.guardarNuevoProveedor();
			break;
		case ACTUALIZAR_PROVEEDOR:
			ventana.guardarEdicionProveedor();
			break;
		case CANCELAR_PROVEEDOR:
			ventana.cerrarDialogoProveedor();
			break;
		//Ventas (Cajero)
		case MENU_REGISTRAR_VENTA:
			ventana.mostrarPanelRegistrarVenta();
			break;
		case MENU_VENTAS_REGISTRADAS:
			ventana.mostrarPanelVentasRegistradas();
			break;
		case MENU_STOCK_BAJO_MINIMO:
			ventana.mostrarPanelStockBajoMinimo();
			break; 
		case ABRIR_SELECTOR_PRODUCTO:
			ventana.abrirSelectorProducto();
			break; 
		case REGISTRAR_VENTA: 
			ventana.registrarVenta();
			break; 
		case CANCELAR_VENTA: 
			ventana.cancelarVenta();
			break; 
		case ANULAR_VENTA: 
			ventana.anularVenta();
			break; 
		case CONSULTAR_VENTAS_POR_FECHA:
			ventana.consultarVentasPorFecha();		
			break; 
		case MOSTRAR_TODAS_VENTAS:
			ventana.mostrarPanelVentasRegistradas();
			break;
		// Contabilidad
		case MENU_MOV_CONTABLES:
		    ventana.mostrarMovimientosContables();
		    break;

		case MENU_REPORTES:
		    ventana.mostrarReportesContables();
		    break;

		case MENU_CONSULTAS:
		    ventana.mostrarConsultasContables();
		    break;
		case REGISTRAR_DEVOLUCION:
			ventana.registrarDevolucion();
			break;
		case VER_FACTURA:
			ventana.verFactura();
			break;
		case REGISTRAR_COMPRA:
			ventana.registrarCompra();
			break;
		
		}
	}
}