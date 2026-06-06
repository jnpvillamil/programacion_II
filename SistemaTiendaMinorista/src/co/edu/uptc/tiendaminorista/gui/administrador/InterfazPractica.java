package co.edu.uptc.tiendaminorista.gui.administrador;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import co.edu.uptc.tiendaminorista.gui.Evento;
import co.edu.uptc.tiendaminorista.modelo.PersonaPractica;

public class InterfazPractica extends JPanel {

    private JTextField txtTexto1;
    private JTextField txtTexto2;
    private JTable tablaPractica;
    private DefaultTableModel modeloTabla;

    public InterfazPractica(Evento e) {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JPanel panelTitulo = new JPanel();
        JLabel titulo = new JLabel("Panel de Prácticas");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelTitulo.add(titulo);
        add(panelTitulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

        JPanel panelFormulario = new JPanel(new GridLayout(2, 2, 10, 10));
        panelFormulario.setMaximumSize(new Dimension(500, 60));

        JLabel label = new JLabel("INGRESA CUALQUIER VALOR 1:");
        txtTexto1 = new JTextField(15);
        JLabel label2 = new JLabel("INGRESA CUALQUIER VALOR 2:");
        txtTexto2 = new JTextField(15);

        panelFormulario.add(label);
        panelFormulario.add(txtTexto1);
        panelFormulario.add(label2);
        panelFormulario.add(txtTexto2);
        
        JPanel panelBotones = new JPanel();

        JButton btnEnviar = new JButton("Enviar");
        btnEnviar.addActionListener(e);
        btnEnviar.setActionCommand(Evento.ENVIAR);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e);
        btnActualizar.setActionCommand(Evento.ACTUALIZARDTO);
        
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e);
        btnEliminar.setActionCommand(Evento.ELIMINARDTO); 

        panelBotones.add(btnEnviar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        panelCentro.add(panelFormulario);
        panelCentro.add(Box.createVerticalStrut(15));
        panelCentro.add(panelBotones);
        panelCentro.add(Box.createVerticalStrut(15));

        add(panelCentro, BorderLayout.CENTER);
        
        String[] columnas = {"Texto 1", "Texto 2"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaPractica = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaPractica);
        scrollTabla.setPreferredSize(new Dimension(500, 250));
        
        add(scrollTabla, BorderLayout.SOUTH);
    }
    
    public void actualizarTabla(List<PersonaPractica> lista) {
        modeloTabla.setRowCount(0); 
        if (lista != null) {
            for (PersonaPractica p : lista) {
                Object[] fila = {p.getTexto1(), p.getTexto2()};
                modeloTabla.addRow(fila);
            }
        }
    }
    
    public String getTexto1Seleccionado() {
        int filaSeleccionada = tablaPractica.getSelectedRow();
        if (filaSeleccionada != -1) {
            return modeloTabla.getValueAt(filaSeleccionada, 0).toString();
        }
        return null;
    }

    public JTextComponent getTxtTexto1() {
        return txtTexto1;
    }

    public JTextComponent getTxtTexto2() {
        return txtTexto2;
    }
}