package co.edu.uptc.Tiendaminorista.Administrador.gui;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.Tiendaminorista.Gui.Evento;
import co.edu.uptc.Tiendaminorista.modelo.Cliente;

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
        motor.setAlignmentX(RIGHT_ALIGNMENT);
   
        motor.add(motorcli);
        JTextField Motolcli = new JTextField(20);
        motor.add(Motolcli);
       
        add(Box.createVerticalStrut(20));
        
        String[] columnas = {"Nombre", "Tipo de documento", "Número de documento", "Tipo de cliente", "Estado"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setMaximumSize(new Dimension(700, 350));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        add(scrollPane);
    }
     
    public void cargarClientes(java.util.List<Cliente> clientes) {
        modelo.setRowCount(0);
        for (Cliente cliente : clientes) {
            modelo.addRow(new Object[]{
                cliente.getNombre(),
                cliente.getTipodoc() != null ? cliente.getTipodoc().name() : "",
                cliente.getNumeroIdentificacion(),
                cliente.getTipoCliente(),
                cliente.isActivo() ? "ACTIVO" : "INACTIVO"
            });
        }
    }
}
