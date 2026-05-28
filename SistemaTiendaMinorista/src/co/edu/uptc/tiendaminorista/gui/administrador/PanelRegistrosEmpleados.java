package co.edu.uptc.tiendaminorista.gui.administrador;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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

public class PanelRegistrosEmpleados extends JPanel {
    
    private JTable tabla;
    private DefaultTableModel modelo;
    private JComboBox<String> combot;

    public PanelRegistrosEmpleados(Evento e) {
   
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
   
        JLabel titulo = new JLabel("Registrar personal de la tienda", SwingConstants.CENTER);
        this.add(titulo, BorderLayout.NORTH);
        
 
        JPanel panelCentro = new JPanel(new GridLayout(1, 2, 20, 0));
        
   
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        
        JLabel registrar = new JLabel("Registrar");
        // Truco para cambiar el tamaño de fuente si lo deseas más grande como en el dibujo:
        registrar.setFont(registrar.getFont().deriveFont(16.0f)); 
        registrar.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblCorreo = new JLabel("Correo");
        lblCorreo.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField txtCorreo = new JTextField();
        txtCorreo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtCorreo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField txtPassword = new JTextField(); // Puedes cambiar a JPasswordField si prefieres ocultar el texto
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblRol = new JLabel("Seleccionar rol");
        lblRol.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        String[] options = {"Administrador", "Empleado"};
        combot = new JComboBox<>(options);
        combot.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        combot.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Agregamos los componentes al formulario con espaciados
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
        
        // --- SECCIÓN DERECHA: Tabla de Visualización ---
        // Ajustado a "Correo" y "Contraseña" según se aprecia en tu dibujo
        String[] columnas = {"Correo", "Contraseña"}; 
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);
        
        // Agregar formulario y tabla al panel dividido del centro
        panelCentro.add(panelFormulario);
        panelCentro.add(scrollPane);
        
        this.add(panelCentro, BorderLayout.CENTER);
        
        // --- 3. PANEL INFERIOR (Botones de Acción) ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        
        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        
        this.add(panelBotones, BorderLayout.SOUTH);
    }
}