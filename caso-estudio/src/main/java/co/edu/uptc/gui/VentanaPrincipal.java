package co.edu.uptc.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

// Importamos tus paquetes donde están las otras piezas
import co.edu.uptc.controlador.ClienteControlador;
import co.edu.uptc.controlador.CompraControlador;
import co.edu.uptc.controlador.ProductoControlador;
import co.edu.uptc.controlador.ProveedorControlador;
import co.edu.uptc.controlador.VentaControlador;
import co.edu.uptc.negocio.GestionCliente;
import co.edu.uptc.negocio.GestionCompra;
import co.edu.uptc.negocio.GestionProducto;
import co.edu.uptc.negocio.GestionProveedor;
import co.edu.uptc.negocio.GestionVenta;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	public VentanaPrincipal() {
		construirVentana();
	}

	private void construirVentana() {
		setTitle("Tienda Minorista");
		setSize(420, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		JPanel panelMenu = new JPanel(new GridLayout(2, 3, 10, 10));
		panelMenu.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

		// Leemos los textos de los botones desde nuestro nuevo diccionario "Eventos"
		JButton bClientes = new JButton(Eventos.CLIENTES);
		JButton bProductos = new JButton(Eventos.PRODUCTOS);
		JButton bProveedores = new JButton(Eventos.PROVEEDORES);
		JButton bVentas = new JButton(Eventos.VENTAS);
		JButton bCompras = new JButton(Eventos.COMPRAS);

		panelMenu.add(bClientes);
		panelMenu.add(bProductos);
		panelMenu.add(bProveedores);
		panelMenu.add(bVentas);
		panelMenu.add(bCompras);

		JPanel panelSalir = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton bSalir = new JButton(Eventos.SALIR);
		panelSalir.add(bSalir);

		add(panelMenu, BorderLayout.CENTER);
		add(panelSalir, BorderLayout.SOUTH);

		// --- EL ENSAMBLAJE: Conectamos cada botón con su módulo respectivo ---
		bClientes.addActionListener(e -> abrirModuloClientes());
		bProductos.addActionListener(e -> abrirModuloProductos());
		bProveedores.addActionListener(e -> abrirModuloProveedores());
		bVentas.addActionListener(e -> abrirModuloVentas());
		bCompras.addActionListener(e -> abrirModuloCompras());

		bSalir.addActionListener(e -> {
			if (JOptionPane.showConfirmDialog(this, "¿Quieres salir del sistema?", "Salir",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		});
	}

	// --- MÉTODOS PARA CREAR Y ABRIR CADA MÓDULO (EL PATRÓN MVC EN ACCIÓN) ---

	private void abrirModuloClientes() {
		PanelCliente vista = new PanelCliente();
		GestionCliente negocio = new GestionCliente();
		new ClienteControlador(vista, negocio); // El controlador los conecta a ambos
		mostrarVentana("Gestión de Clientes", vista);
	}

	private void abrirModuloProductos() {
		PanelProducto vista = new PanelProducto();
		GestionProducto negocio = new GestionProducto();
		new ProductoControlador(vista, negocio);
		mostrarVentana("Gestión de Productos", vista);
	}

	private void abrirModuloProveedores() {
		PanelProveedor vista = new PanelProveedor();
		GestionProveedor negocio = new GestionProveedor();
		new ProveedorControlador(vista, negocio);
		mostrarVentana("Gestión de Proveedores", vista);
	}

	private void abrirModuloVentas() {
		PanelVentas vista = new PanelVentas();
		GestionVenta negocio = new GestionVenta();
		new VentaControlador(vista, negocio);
		mostrarVentana("Gestión de Ventas", vista);
	}

	private void abrirModuloCompras() {
		PanelCompra vista = new PanelCompra();
		GestionCompra negocio = new GestionCompra();
		new CompraControlador(vista, negocio);
		mostrarVentana("Gestión de Compras", vista);
	}

	// Este método toma cualquier panel, lo mete en un marco (JFrame) y lo muestra
	private void mostrarVentana(String titulo, JPanel panel) {
		JFrame ventana = new JFrame(titulo);
		ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana, no todo el programa
		ventana.add(panel);
		ventana.pack();
		ventana.setLocationRelativeTo(this); // La centra respecto al menú principal
		ventana.setVisible(true);
	}

	public static void main(String[] args) {
		// Un punto de arranque limpio y profesional
		SwingUtilities.invokeLater(() -> {
			VentanaPrincipal vp = new VentanaPrincipal();
			vp.setVisible(true);
		});
	}
}