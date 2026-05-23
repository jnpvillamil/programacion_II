package co.uptc.edu.co.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import co.uptc.edu.co.gui.Evento;

public class DialogAnularVenta extends JDialog {

	private JTextField campoNumeroFactura;
	private JTextField campoCliente;
	private JTextField campoFecha;
	private JTextField campoTotal;
	private JTextField campoEstado;

	private JTextArea areaMotivoAnulacion;

	private JButton botonConfirmarAnulacion;
	private JButton botonCancelar;

	public DialogAnularVenta(Frame propietario) {
		this(propietario, null);
	}

	public DialogAnularVenta(Frame propietario, Evento evento) {
		super(propietario, "Anular Venta", true);
		inicializarComponentes();
		configurarDialogo();
		agregarComponentes();
		inicializarEventos(evento);
	}

	private void inicializarComponentes() {
		campoNumeroFactura = new JTextField(20);
		campoCliente = new JTextField(20);
		campoFecha = new JTextField(20);
		campoTotal = new JTextField(20);
		campoEstado = new JTextField(20);

		areaMotivoAnulacion = new JTextArea(4, 20);
		areaMotivoAnulacion.setLineWrap(true);
		areaMotivoAnulacion.setWrapStyleWord(true);

		botonConfirmarAnulacion = new JButton("Confirmar Anulación");
		botonCancelar = new JButton("Cancelar");

		botonConfirmarAnulacion.setBackground(new Color(198, 40, 40));
		botonConfirmarAnulacion.setForeground(Color.WHITE);

		botonCancelar.setBackground(new Color(220, 220, 220));

		campoNumeroFactura.setEditable(false);
		campoCliente.setEditable(false);
		campoFecha.setEditable(false);
		campoTotal.setEditable(false);
		campoEstado.setEditable(false);
	}

	private void configurarDialogo() {
		setSize(500, 420);
		setLocationRelativeTo(getOwner());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
	}

	private void agregarComponentes() {
		JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		JPanel panelDatos = new JPanel(new GridBagLayout());
		panelDatos.setBorder(BorderFactory.createTitledBorder("Datos de la Venta"));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(6, 6, 6, 6);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		panelDatos.add(new JLabel("N° Factura:"), gbc);

		gbc.gridx = 1;
		panelDatos.add(campoNumeroFactura, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panelDatos.add(new JLabel("Cliente:"), gbc);

		gbc.gridx = 1;
		panelDatos.add(campoCliente, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panelDatos.add(new JLabel("Fecha:"), gbc);

		gbc.gridx = 1;
		panelDatos.add(campoFecha, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		panelDatos.add(new JLabel("Total:"), gbc);

		gbc.gridx = 1;
		panelDatos.add(campoTotal, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		panelDatos.add(new JLabel("Estado:"), gbc);

		gbc.gridx = 1;
		panelDatos.add(campoEstado, gbc);

		JPanel panelMotivo = new JPanel(new BorderLayout(5, 5));
		panelMotivo.setBorder(BorderFactory.createTitledBorder("Motivo de anulación"));
		panelMotivo.add(new JScrollPane(areaMotivoAnulacion), BorderLayout.CENTER);

		JLabel etiquetaConfirmacion = new JLabel("¿Está seguro de anular esta venta?");
		JPanel panelPregunta = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelPregunta.add(etiquetaConfirmacion);

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		panelBotones.add(botonConfirmarAnulacion);
		panelBotones.add(botonCancelar);

		JPanel panelInferior = new JPanel(new BorderLayout());
		panelInferior.add(panelPregunta, BorderLayout.NORTH);
		panelInferior.add(panelBotones, BorderLayout.SOUTH);

		panelPrincipal.add(panelDatos, BorderLayout.NORTH);
		panelPrincipal.add(panelMotivo, BorderLayout.CENTER);
		panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

		add(panelPrincipal);
	}

	private void inicializarEventos(Evento evento) {
		botonCancelar.addActionListener(e -> dispose());

		if (evento != null) {
			botonConfirmarAnulacion.setActionCommand(Evento.CMD_CONFIRMAR_ANULAR_VENTA);
			botonConfirmarAnulacion.addActionListener(evento);
		}
	}

	public void cargarVenta(String numeroFactura, String cliente, String fecha, String total, String estado) {
		campoNumeroFactura.setText(numeroFactura);
		campoCliente.setText(cliente);
		campoFecha.setText(fecha);
		campoTotal.setText(total);
		campoEstado.setText(estado);
	}

	public String obtenerMotivoAnulacion() {
		return areaMotivoAnulacion.getText().trim();
	}

	public String obtenerNumeroFactura() {
		return campoNumeroFactura.getText().trim();
	}
}