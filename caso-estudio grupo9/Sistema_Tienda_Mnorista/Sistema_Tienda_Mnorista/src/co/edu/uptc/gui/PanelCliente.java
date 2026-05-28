package co.edu.uptc.gui;
import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelCliente extends JPanel {
	private JTable tabla;
	private DefaultTableModel modelo;
    public PanelCliente(Evento e) {
    	
    	this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel titulo = new JLabel("Gestión Cliente");
        setAlignmentX(CENTER_ALIGNMENT);
        add(titulo);
        
        
        JPanel BOTONES = new JPanel();
        add(BOTONES);
        BOTONES.setAlignmentX(CENTER_ALIGNMENT);
        
        JButton btnRegistarcli = new JButton(Evento.REGISTRARCLIENTE);
        btnRegistarcli.addActionListener(e);
        btnRegistarcli.setActionCommand(Evento.REGISTRARCLIENTE);
        
        JButton btnModificarcli = new JButton(Evento.MODIFICARCLIENTE);
        btnModificarcli.addActionListener(e);
        btnModificarcli.setActionCommand(Evento.MODIFICARCLIENTE);
        
        JButton btnHistorialcli = new JButton(Evento.HISTORIALCLIENTE);
        btnHistorialcli.addActionListener(e);
        btnHistorialcli.setActionCommand(Evento.HISTORIALCLIENTE);
        
        BOTONES.add(btnRegistarcli);
        BOTONES.add(btnModificarcli);
        BOTONES.add(btnHistorialcli);
        
        JPanel motor = new JPanel();
       
        add(motor);
        JLabel motorcli = new JLabel("Motor de busqueda cliente");
        motor.setAlignmentX(RIGHT_ALIGNMENT);;
   
        motor.add(motorcli);
        JTextField Motolcli = new JTextField(20);
        motor.add(Motolcli);
       
        add(Box.createVerticalStrut(20));
        
        String[] columnas = {"Nombre", "Apellido", "Número de documento", "Tipo de cliente"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setMaximumSize(new Dimension(700, 250));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        add(scrollPane);
    }
     
        public void cargarClientes(java.util.List<co.edu.uptc.Datos.Clientedt> lista) {

            modelo.setRowCount(0);

            for (co.edu.uptc.Datos.Clientedt c : lista) {
                modelo.addRow(new Object[]{
                    c.getNombre(),
                    c.getTipodocli(),
                    c.getNumerodocli(),
                    c.getTipocli()
                });
            }}}
     