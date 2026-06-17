package co.edu.uptc.sistienda.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import co.edu.uptc.sistienda.negocio.dto.CredencialDto;

public class PanelLogin extends JPanel {

	private static final String[] ROLES = { "Administrador", "Cajero", "Encargado de compras", "Contador" };

	private JTextField     campoUsuario;
	private JPasswordField campoContrasenia;
	private JComboBox<String> comboRol;

	public PanelLogin(Evento evento) {
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill      = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx   = 1.0;
		gbc.insets    = new Insets(4, 0, 2, 0);

		// Titulo
		JLabel etiquetaTitulo = new JLabel("Sistienda", JLabel.CENTER);
		add(etiquetaTitulo, gbc);

		JLabel etiquetaSubtitulo = new JLabel("Sistema de Gestión Contable y Comercial", JLabel.CENTER);
		gbc.insets = new Insets(0, 0, 16, 0);
		add(etiquetaSubtitulo, gbc);
		gbc.insets = new Insets(4, 0, 2, 0);

		// Usuario
		add(new JLabel("Usuario:"), gbc);
		campoUsuario = new JTextField("admin");
		campoUsuario.setPreferredSize(new Dimension(260, 26));
		gbc.insets = new Insets(0, 0, 8, 0);
		add(campoUsuario, gbc);
		gbc.insets = new Insets(4, 0, 2, 0);

		// Contraseña
		add(new JLabel("Contraseña:"), gbc);
		campoContrasenia = new JPasswordField();
		campoContrasenia.setPreferredSize(new Dimension(260, 26));
		gbc.insets = new Insets(0, 0, 8, 0);
		add(campoContrasenia, gbc);
		gbc.insets = new Insets(4, 0, 2, 0);

		// Rol
		add(new JLabel("Rol / Perfil:"), gbc);
		comboRol = new JComboBox<>(ROLES);
		comboRol.setPreferredSize(new Dimension(260, 26));
		gbc.insets = new Insets(0, 0, 16, 0);
		add(comboRol, gbc);

		// Botón
		JButton botonIngresar = new JButton(Evento.INGRESAR);
		botonIngresar.setActionCommand(Evento.INGRESAR);
		botonIngresar.addActionListener(evento);
		gbc.insets = new Insets(4, 60, 0, 60);
		add(botonIngresar, gbc);
	}

	public CredencialDto obtenerCredencialesIngresadas() {
		CredencialDto credencial = new CredencialDto();
		credencial.setUsuario(campoUsuario.getText().trim());
		credencial.setPassword(new String(campoContrasenia.getPassword()).getBytes());
		credencial.setRol((String) comboRol.getSelectedItem());
		return credencial;
	}
}
