package co.edu.uptc.gui;

import java.awt.Dimension;
import java.awt.Component;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Panel_Actualizarpro extends JPanel {
  
	public Panel_Actualizarpro (Evento e) {
	
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		this.setBorder(BorderFactory.createEmptyBorder(20,20,20,40));
		
		JLabel titulo = new JLabel("Proveedores");
		titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(titulo);
		
		
		add(Box.createVerticalStrut(20));
		
		JPanel informacion = new JPanel ();
		informacion.setLayout(new BoxLayout(informacion,BoxLayout.Y_AXIS));
		
		JLabel Razonpro = new JLabel ("Razon proveedores");
		Razonpro.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JTextField razonpro = new JTextField();
		razonpro.setMaximumSize(new Dimension(300,40));
		razonpro.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel Direccionpro =new JLabel("Direccion de la residencia");
		Direccionpro.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JTextField direccionpro = new JTextField();
		direccionpro.setMaximumSize(new Dimension(300,40));
		direccionpro.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel Telefonopro =new JLabel("numero de telefono");
		Telefonopro.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JTextField telefonopro = new JTextField();
		telefonopro.setMaximumSize(new Dimension(300,25));	
		telefonopro.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel Correopro =new JLabel("Correo Electronico");
		Correopro.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JTextField Correopro1 = new JTextField();
		Correopro1.setMaximumSize(new Dimension(300,25));	
		Correopro1.setAlignmentX(Component.LEFT_ALIGNMENT);
	
		
		JPanel butones = new JPanel();
		
		butones.setBorder(new EmptyBorder(20, 0, 0, 0)); 
		
		JButton btncalcelarpro = new JButton(Evento.CANCELAR);
		btncalcelarpro.addActionListener(e);
		btncalcelarpro.setActionCommand(Evento.CANCELAR);
		JButton btnRegistrarpro = new JButton(Evento.ACTUALIZARPRO1);
		btnRegistrarpro.addActionListener(e);
		btnRegistrarpro.setActionCommand(Evento.ACTUALIZARPRO1);
		JButton btndesactivarpro = new JButton(Evento.DESACTIVARCLI);
		btndesactivarpro.addActionListener(e);
		btndesactivarpro.setActionCommand(Evento.DESACTIVARCLI);
		
		informacion.add(Razonpro);
		informacion.add(Box.createVerticalStrut(5));
		informacion.add(razonpro);
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
		informacion.add(Correopro1);
		informacion.add(Box.createVerticalStrut(10));
		
		butones.add(btncalcelarpro);
		butones.add(btnRegistrarpro);
		butones.add(btndesactivarpro);
		
		add(informacion);
		add(butones);
	}
}