package co.edu.uptc.gui;
import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelProveedores extends JPanel {

    public PanelProveedores(Evento e) {
    	
    	this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel titulo = new JLabel("Gestión Proveedores");
        setAlignmentX(CENTER_ALIGNMENT);
        add(titulo);
        
        
        JPanel BOTONES = new JPanel();
        add(BOTONES);
        BOTONES.setAlignmentX(CENTER_ALIGNMENT);
        
        JButton btnRegistarpro = new JButton(Evento.REGISTRARPROVEDORES);
        btnRegistarpro.addActionListener(e);
        btnRegistarpro.setActionCommand(Evento.REGISTRARPROVEDORES);
        
        JButton btnActualizarpro = new JButton(Evento.ACTUALIZARPRO);
        btnActualizarpro.addActionListener(e);
        btnActualizarpro.setActionCommand(Evento.ACTUALIZARPRO);
        
        JButton btnCompraspro = new JButton(Evento.COMPRASPRO);
        btnCompraspro.addActionListener(e);
        btnCompraspro.setActionCommand(Evento.COMPRASPRO);
        
        BOTONES.add(btnRegistarpro);
        BOTONES.add(btnActualizarpro);
        BOTONES.add(btnCompraspro);
        
        JPanel motor = new JPanel();
       
        add(motor);
        JLabel motorcli = new JLabel("Motor de busqueda cliente");
        motor.setAlignmentX(RIGHT_ALIGNMENT);;
   
        motor.add(motorcli);
        JTextField Motolcli = new JTextField(20);
        motor.add(Motolcli);
       
        add(Box.createVerticalStrut(20));
        
        String[] columnas1 = {"Razon social", "NIT", "Direccion", "Correo"};
        DefaultTableModel modelo1 = new DefaultTableModel(columnas1, 0);
        JTable tabla = new JTable(modelo1);
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setMaximumSize(new Dimension(700, 250));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        add(scrollPane);
        
     
       
    }
}