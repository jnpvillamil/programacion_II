package co.edu.uptc.tiendaminorista.gui.administrador;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List; 

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.tiendaminorista.gui.Evento;
import co.edu.uptc.tiendaminorista.modelo.Empleado;

public class PanelRegistrosEmpleados extends JPanel {
    
    private JTable tabla;
    private DefaultTableModel modelo;
    private JComboBox<String> combot;
    
    private JTextField txtCorreo;
    private JTextField txtPassword;

    public PanelRegistrosEmpleados(Evento e) {
   
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titulo = new JLabel("Registrar personal de la tienda", SwingConstants.CENTER);
        this.add(titulo, BorderLayout.NORTH);
        
        JPanel panelCentro = new JPanel(new GridLayout(1, 2, 20, 0));
        
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        
        JLabel registrar = new JLabel("Registrar");
        registrar.setFont(registrar.getFont().deriveFont(16.0f)); 
        registrar.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblCorreo = new JLabel("Correo");
        lblCorreo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        txtCorreo = new JTextField();
        txtCorreo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtCorreo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        txtPassword = new JTextField(); 
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblRol = new JLabel("Seleccionar rol");
        lblRol.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        String[] options = {"Administrador", "Empleado"};
        combot = new JComboBox<>(options);
        combot.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        combot.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelFormulario.add(registrar);
        panelFormulario.add(Box.createVerticalStrut(15));
        panelFormulario.add(lblCorreo);
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(txtCorreo);
        panelFormulario.add(Box.createVerticalStrut(15));
        panelFormulario.add(lblPassword);
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(txtPassword);
        panelFormulario.add(Box.createVerticalStrut(15));
        panelFormulario.add(lblRol);
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(combot);

        String[] columnas = {"Correo", "Contraseña"}; 

        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        tabla = new JTable(modelo);

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int filaSeleccionada = tabla.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    String correo = tabla.getValueAt(filaSeleccionada, 0).toString();
                    String password = tabla.getValueAt(filaSeleccionada, 1).toString();
                    
                    txtCorreo.setText(correo);
                    txtPassword.setText(password);
                    txtCorreo.setEditable(false);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        
        panelCentro.add(panelFormulario);
        panelCentro.add(scrollPane);
        
        this.add(panelCentro, BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        
        JButton btnAgregar = new JButton(Evento.REGISTRAREM);
        btnAgregar.addActionListener(e);
        btnAgregar.setActionCommand(Evento.REGISTRAREM);
        
        JButton btnActualizar = new JButton(Evento.ACTUALIZAREM);
        btnActualizar.addActionListener(e);
        btnActualizar.setActionCommand(Evento.ACTUALIZAREM);
        
        JButton btnEliminar = new JButton(Evento.ELIMINAREM);
        btnEliminar.addActionListener(e);
        btnEliminar.setActionCommand(Evento.ELIMINAREM);
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        
        this.add(panelBotones, BorderLayout.SOUTH);
    } 

    public String getCorreo() {
        return txtCorreo.getText().trim();
    }

    public String getContraseña() {
        return txtPassword.getText().trim();
    }

    public String getRol() {
        return (String) combot.getSelectedItem();
    }

    public void limpiarCampos() {
        txtCorreo.setText("");
        txtPassword.setText("");
        combot.setSelectedIndex(0);
        txtCorreo.setEditable(true); 
    }

    public void cargarEmpleados(List<Empleado> listaEmpleados) {
        modelo.setRowCount(0); 
        if (listaEmpleados != null) {
            for (Empleado emp : listaEmpleados) {
                Object[] fila = { emp.getCorreo(), emp.getPassword() }; 
                modelo.addRow(fila);
            }
        }
    }
}