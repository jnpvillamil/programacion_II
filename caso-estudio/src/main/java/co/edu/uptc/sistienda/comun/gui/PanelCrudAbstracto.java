package co.edu.uptc.sistienda.comun.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.sistienda.gui.Evento;

//Panel abstracto base para los módulos CRUD
//Cada subclase solo implementa los métodos abstractos.

public abstract class PanelCrudAbstracto extends JPanel {

	// Componentes compartidos son accesibles por las subclases (protected)
	protected JButton    botonNuevo;
	protected JButton    botonEditar;
	protected JButton    botonInactivar;
	protected JButton    botonActivar;
	protected JButton    botonBuscar;
	protected JButton    botonLimpiar;
	protected JTextField campoBusqueda;
	protected JTable     tabla;
	protected DefaultTableModel modeloTabla;
	protected JLabel     etiquetaAlerta;

	public PanelCrudAbstracto(Evento evento) {
		setLayout(new BorderLayout());

		JPanel panelNorte = new JPanel(new BorderLayout());

		// Título del módulo 
		JLabel etiquetaTitulo = new JLabel(obtenerTituloPanel());
		etiquetaTitulo.setBorder(BorderFactory.createEmptyBorder(6, 8, 4, 8));
		panelNorte.add(etiquetaTitulo, BorderLayout.NORTH);

		// Alerta stock (visible solo en productos, pero existe en todos)
		etiquetaAlerta = new JLabel(" ");
		etiquetaAlerta.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
		panelNorte.add(etiquetaAlerta, BorderLayout.CENTER);

		JPanel panelBotones = new JPanel();
		panelBotones.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));

		botonNuevo     = new JButton("Nuevo");
		botonEditar    = new JButton("Editar");
		botonInactivar = new JButton("Inactivar");
		botonActivar = new JButton("Activar");
		botonBuscar    = new JButton("Buscar");
		botonLimpiar   = new JButton("Limpiar");
		campoBusqueda  = new JTextField();
		campoBusqueda.setPreferredSize(new Dimension(160, 24));

		// Asignar ActionCommand polimórfico, cada subclase define cuáles
		asignarComandosBotones();

		botonNuevo.addActionListener(evento);
		botonEditar.addActionListener(evento);
		botonInactivar.addActionListener(evento);
		botonActivar.addActionListener(evento);
		botonBuscar.addActionListener(evento);
		botonLimpiar.addActionListener(evento);

		panelBotones.add(botonNuevo);
		panelBotones.add(botonEditar);
		panelBotones.add(botonInactivar);
		panelBotones.add(botonActivar);
		panelBotones.add(campoBusqueda);
		panelBotones.add(botonBuscar);
		panelBotones.add(botonLimpiar);

		panelNorte.add(panelBotones, BorderLayout.SOUTH);
		add(panelNorte, BorderLayout.NORTH);

		// Tabla central
		modeloTabla = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // tabla de solo lectura
			}
		};
		tabla = new JTable(modeloTabla);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.getTableHeader().setReorderingAllowed(false);

		// Las columnas las define la subclase
		agregarColumnasTabla();

		JScrollPane scrollTabla = new JScrollPane(tabla);
		add(scrollTabla, BorderLayout.CENTER);
	}

	// Métodos abstractos que cada subclase debe implementar

	public abstract String obtenerTituloPanel();

	public abstract void asignarComandosBotones();

	//Define las columnas de la tabla de ese módulo
	public abstract void agregarColumnasTabla();

	
	 //Rellena la tabla con la lista de objetos correspondiente.
	 //El parámetro usa List<?> para mantener el mismo algoritmo.
	public abstract void poblarTabla(List<?> listaRegistros);

	// Métodos comunes heredados por todas las subclases

	public String obtenerTextoBusqueda() {
		return campoBusqueda.getText().trim();
	}

	public void limpiarCampoBusqueda() {
		campoBusqueda.setText("");
	}
	
	public void mostrarMensajeAlerta(String mensaje) {
		etiquetaAlerta.setText(mensaje);
	}

	public void ocultarMensajeAlerta() {
		etiquetaAlerta.setText(" ");
	}

	public String obtenerCodigoFilaSeleccionada() {
		int filaSeleccionada = tabla.getSelectedRow();
		if (filaSeleccionada < 0) {
			return null;
		}
		Object valor = modeloTabla.getValueAt(filaSeleccionada, 0);
		return valor != null ? valor.toString() : null;
	}

	public void limpiarTabla() {
		modeloTabla.setRowCount(0);
	}
}
