package co.uptc.edu.co.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Evento implements ActionListener {
	public static final String CMD_CLIENTES = "Clientes";
	public static final String CMD_PRODUCTOS = "Productos";
	public static final String CMD_NUEVO_PRODUCTO = "NuevoProducto";
	public static final String CMD_CONFIRMAR_PRODUCTO = "ConfirmarProducto";
	public static final String CMD_CONFIRMAR_EDICION_PRODUCTO = "ConfirmarEdicionProducto";
	public static final String CMD_INACTIVAR_PRODUCTO = "InactivarProducto";
	public static final String CMD_ACTUALIZAR_PRECIO_PRODUCTO = "ActualizarPrecioProducto";
	public static final String CMD_EDITAR_PRODUCTO = "EditarProducto";
	public static final String CMD_MOVIMIENTO_INVENTARIO = "MovimientoInventarioProducto";
	public static final String CMD_PROVEEDORES = "Proveedores";
	public static final String CMD_VENTAS = "Ventas";
	public static final String CMD_COMPRAS = "Compras";
	public static final String CMD_CONTABILIDAD = "Contabilidad";
	public static final String CMD_REPORTES = "Reportes";
	public static final String CMD_CONSULTAS = "Consultas";
	public static final String CMD_CONFIRMAR_ACTUALIZAR_PRECIO_PRODUCTO = "ConfirmarActualizarPrecioProducto";
	public static final String CMD_CONFIRMAR_INACTIVACION_PRODUCTO = "ConfirmarInactivacionProducto";

	private VentanaPrincipal ventana;

	public Evento(VentanaPrincipal ventana) {
		this.ventana = ventana;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String comando = e.getActionCommand();

		switch (comando) {

		case CMD_CLIENTES:
			ventana.mostrarPanel(new PanelCliente());
			break;

		case CMD_PRODUCTOS:
			PanelProducto panelProducto = new PanelProducto();
			panelProducto.inicializarEventos(this);
			ventana.mostrarPanel(panelProducto);
			break;
		case CMD_NUEVO_PRODUCTO:
			DialogProducto dialogProducto = new DialogProducto(ventana, this);
			dialogProducto.setVisible(true);
			break;
		case CMD_CONFIRMAR_PRODUCTO:
			JOptionPane.showMessageDialog(ventana, "Registro de producto exitoso");
			Component componente = (Component) e.getSource();
			SwingUtilities.getWindowAncestor(componente).dispose();
			break;
		case CMD_EDITAR_PRODUCTO:
			DialogEditarProducto dialogEditarProducto = new DialogEditarProducto(ventana, this);
			dialogEditarProducto.setVisible(true);
			break;
		case CMD_CONFIRMAR_EDICION_PRODUCTO:
			JOptionPane.showMessageDialog(ventana, "Producto editado exitoso");
			cerrarVentanaDeEvento(e);
			break;
		case CMD_INACTIVAR_PRODUCTO:
			DialogInactivarProducto dialogInactivarProducto = new DialogInactivarProducto(ventana, this);
			dialogInactivarProducto.setVisible(true);
			break;
		case CMD_CONFIRMAR_INACTIVACION_PRODUCTO:
			JOptionPane.showMessageDialog(ventana, "Inactivacion de producto exitosas .");
			cerrarVentanaDeEvento(e);
			break;
		case CMD_ACTUALIZAR_PRECIO_PRODUCTO:
			DialogActualizarPrecio dialogActualizarPrecio = new DialogActualizarPrecio(ventana, this);
			dialogActualizarPrecio.setVisible(true);
			break;
		case CMD_CONFIRMAR_ACTUALIZAR_PRECIO_PRODUCTO:
			JOptionPane.showMessageDialog(ventana, "Actualizacion de precio existosas");
			cerrarVentanaDeEvento(e);
			break;

		case CMD_MOVIMIENTO_INVENTARIO:
			JOptionPane.showMessageDialog(ventana, "En procesos");
			break;
		case CMD_PROVEEDORES:
			ventana.mostrarPanel(new PanelProveedor());
			break;

		case CMD_VENTAS:
			ventana.mostrarPanel(new PanelVenta());
			break;

		case CMD_COMPRAS:
			ventana.mostrarPanel(new PanelCompra());
			break;

		case CMD_CONTABILIDAD:
			ventana.mostrarPanel(new PanelContabilidad());
			break;

		case CMD_REPORTES:
			ventana.mostrarPanel(new PanelReportes());
			break;

		case CMD_CONSULTAS:
			ventana.mostrarPanel(new PanelConsultas());
		}
	}

	private void cerrarVentanaDeEvento(ActionEvent e) {
		// TODO Auto-generated method stub
		Component componente = (Component) e.getSource();
		SwingUtilities.getWindowAncestor(componente).dispose();

	}
}