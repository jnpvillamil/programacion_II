package co.uptc.edu.co.gui.dialog;

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
import javax.swing.JTextField;

import co.uptc.edu.co.gui.Evento;
import co.uptc.edu.co.modelo.Empleado;

public class DialogEmpleado extends JDialog {

	private JTextField campoSalario;
	private JButton botonGuardar;
	private JButton botonCancelar;

	public DialogEmpleado(Frame propietario, Evento evento) {
		super(propietario, "Registrar Salario Empleado", true);
		inicializarComponentes();
		configurarDialogo();
		agregarComponentes();
		inicializarEventos(evento);
	}

	private void inicializarComponentes() {
		campoSalario = new JTextField(25);

		botonGuardar = new JButton("Guardar");
		botonCancelar = new JButton("Cancelar");

		botonGuardar.setBackground(new Color(46, 125, 50));
		botonGuardar.setForeground(Color.WHITE);

		botonCancelar.setBackground(new Color(220, 220, 220));
	}

	private void configurarDialogo() {
		setSize(360, 210);
		setLocationRelativeTo(getOwner());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
	}

	private void agregarComponentes() {
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new GridBagLayout());
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(8, 0, 4, 0);

		panelPrincipal.add(new JLabel("Salario:"), gbc);

		gbc.gridy++;
		panelPrincipal.add(campoSalario, gbc);

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		panelBotones.add(botonGuardar);
		panelBotones.add(botonCancelar);

		gbc.gridy++;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(20, 0, 0, 0);
		panelPrincipal.add(panelBotones, gbc);

		add(panelPrincipal);
	}

	private void inicializarEventos(Evento evento) {
		botonCancelar.addActionListener(e -> dispose());

		botonGuardar.setActionCommand(Evento.CMD_CONFIRMAR_EMPLEADO);
		botonGuardar.addActionListener(evento);
	}

	public Empleado obtenerEmpleado() throws Exception {
		String textoSalario = campoSalario.getText().trim();

		if (textoSalario.isEmpty()) {
			throw new Exception("El salario del empleado es obligatorio.");
		}

		double salarioEmpleado;
		try {
			salarioEmpleado = Double.parseDouble(textoSalario);
		} catch (NumberFormatException e) {
			throw new Exception("El salario del empleado debe ser numerico.");
		}

		Empleado empleado = new Empleado();
		empleado.setSalarioEmpleado(salarioEmpleado);
		return empleado;
	}
}
