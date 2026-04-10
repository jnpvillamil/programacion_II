package co.uptc.edu.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {
	private PanelLogin pLogin;
	private Evento evento;
	private PanelCentral pCentral;

	public VentanaPrincipal() {
		setSize(400,300);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		
		evento = new Evento(this);
		pLogin = new PanelLogin(evento);
		pCentral = new PanelCentral();
		add(pLogin,BorderLayout.CENTER);
	}
	
	public void loguear() {
		pLogin.setVisible(false);
		add(pCentral, BorderLayout.CENTER);
		pCentral.setVisible(true);
	}
	



}
