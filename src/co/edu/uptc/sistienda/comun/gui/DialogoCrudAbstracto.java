package co.edu.uptc.sistienda.comun.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import co.edu.uptc.sistienda.gui.Evento;


 //Diálogo abstracto base para Crear / Editar cualquier entidad
 //Herencia: cada módulo extiende este diálogo y solo implementa
 //los métodos abstractos (construir campos, capturar datos, cargar datos)
 
public abstract class DialogoCrudAbstracto extends JDialog {

	protected boolean esCreacion;  // true = Nuevo  /  false = Editar
	protected JButton botonGuardar;
	protected JButton botonCancelar;

	public DialogoCrudAbstracto(Evento evento, String titulo, boolean esCreacion) {
		this.esCreacion = esCreacion;
		setTitle(titulo);
		setSize(380, 380);
		setLocationRelativeTo(null);
		setModal(true);
		setLayout(new BorderLayout());

		// Panel de campos (lo construye la subclase)
		JPanel panelCampos = new JPanel(new GridLayout(0, 2, 6, 6));
		panelCampos.setBorder(BorderFactory.createEmptyBorder(12, 14, 8, 14));
		construirCamposFormulario(panelCampos);
		add(panelCampos, BorderLayout.CENTER);

		// Botones inferiores
		botonGuardar  = new JButton(esCreacion ? "Guardar" : "Actualizar");
		botonCancelar = new JButton("Cancelar");

		// Los ActionCommand los asigna la subclase según el módulo
		asignarComandosBotones();

		botonGuardar.addActionListener(evento);
		botonCancelar.addActionListener(evento);

		JPanel panelBotones = new JPanel();
		panelBotones.setBorder(BorderFactory.createEmptyBorder(4, 0, 8, 0));
		panelBotones.add(botonCancelar);
		panelBotones.add(botonGuardar);
		add(panelBotones, BorderLayout.SOUTH);
	}

	// Métodos abstractos polimorfismo por módulo

	public abstract void construirCamposFormulario(JPanel panelCampos);

	
	 //Asigna ActionCommand a botonGuardar y botonCancelar.
	 //Ej: botonGuardar.setActionCommand(Evento.GUARDAR_PRODUCTO)
	public abstract void asignarComandosBotones();

	 //Carga los datos de un objeto existente en los campos del formulario (se usa solo en modo edición)
	public abstract void cargarDatosEnFormulario(Object objeto);
}
