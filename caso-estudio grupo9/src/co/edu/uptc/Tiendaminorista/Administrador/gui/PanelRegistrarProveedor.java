package co.edu.uptc.Tiendaminorista.Administrador.gui;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import co.edu.uptc.Tiendaminorista.Gui.Evento;

public class PanelRegistrarProveedor extends JPanel {
  
    private JTextField razonpro;
    private JTextField Numeropro;
    private JTextField direccionpro;
    private JTextField telefonopro;
    private JTextField correopro1;
  
	public PanelRegistrarProveedor (Evento e) {
	
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JPanel contenedor = new JPanel();
		contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
		contenedor.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel titulo = new JLabel("Proveedores");
		titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		contenedor.add(titulo);
		
		contenedor.add(Box.createVerticalStrut(20));
		
		JPanel informacion = new JPanel();
		informacion.setLayout(new BoxLayout(informacion, BoxLayout.Y_AXIS));
		informacion.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel Razonpro = new JLabel("Razon proveedores");
		Razonpro.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		razonpro = new JTextField();
		razonpro.setMaximumSize(new Dimension(300, 40));
		razonpro.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel numeropro = new JLabel("Numero del NIT");
		numeropro.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Numeropro = new JTextField();
		Numeropro.setMaximumSize(new Dimension(300, 40));
		Numeropro.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel Direccionpro = new JLabel("Direccion de la residencia");
		Direccionpro.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		direccionpro = new JTextField();
		direccionpro.setMaximumSize(new Dimension(300, 40));
		direccionpro.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel Telefonopro = new JLabel("Numero de telefono");
		Telefonopro.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		telefonopro = new JTextField();
		telefonopro.setMaximumSize(new Dimension(300, 25));	
		telefonopro.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel Correopro = new JLabel("Correo Electronico");
		Correopro.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		correopro1 = new JTextField();
		correopro1.setMaximumSize(new Dimension(300, 25));	
		correopro1.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel butones = new JPanel();
		butones.setBorder(new EmptyBorder(20, 0, 0, 0));
		butones.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton btncalcelarpro = new JButton(Evento.CANCELAR);
		btncalcelarpro.addActionListener(e);
		btncalcelarpro.setActionCommand(Evento.CANCELAR);
		
		JButton btnRegistrarpro = new JButton(Evento.REGISTRARPROV);
		btnRegistrarpro.addActionListener(e);
		btnRegistrarpro.setActionCommand(Evento.REGISTRARPROV);
		
		informacion.add(Razonpro);
		informacion.add(Box.createVerticalStrut(5));
		informacion.add(razonpro);
		informacion.add(Box.createVerticalStrut(10));
		
		informacion.add(numeropro);
		informacion.add(Box.createVerticalStrut(5));
		informacion.add(Numeropro);
		informacion.add(Box.createVerticalStrut(10));
		
		informacion.add(Direccionpro);
		informacion.add(Box.createVerticalStrut(5));
		informacion.add(direccionpro);
		informacion.add(Box.createVerticalStrut(10));
		
		informacion.add(Telefonopro);
		informacion.add(Box.createVerticalStrut(5));
		informacion.add(telefonopro);
		informacion.add(Box.createVerticalStrut(10));
		
		informacion.add(Correopro);
		informacion.add(Box.createVerticalStrut(5));
		informacion.add(correopro1);
		informacion.add(Box.createVerticalStrut(10));
		
		butones.add(btncalcelarpro);
		butones.add(btnRegistrarpro);
		
		contenedor.add(informacion);
		contenedor.add(butones);
		
		add(contenedor);
	}

	public String getRazon() {
		return razonpro.getText();
	}

	public String getNit() {
		return Numeropro.getText();
	}

	public String getDireccion() {
		return direccionpro.getText();
	}

	public String getTelefono() {
		return telefonopro.getText();
	}

	public String getCorreo() {
		return correopro1.getText();
	}
}