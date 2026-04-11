package co.uptc.edu.gui;
import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {

        // Configuración básica de la ventana
        setTitle("Sistema de Gestión Comercial y Contable");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Agregar componentes
        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearPanelBotones(), BorderLayout.CENTER);
    }

   
    // ENCABEZADO
   
    private JPanel crearEncabezado() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 10, 20, 10));

        JLabel titulo = new JLabel("SISTEMA DE GESTIÓN COMERCIAL Y CONTABLE");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(33, 47, 61));

        JLabel subtitulo = new JLabel("Seleccione un módulo para continuar");
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitulo.setForeground(Color.GRAY);

        panel.add(titulo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(subtitulo);

        return panel;
    }

   
    // PANEL DE BOTONES
    
    private JPanel crearPanelBotones() {

        JPanel panel = new JPanel(new GridLayout(2, 4, 30, 30));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        JButton btnClientes = crearBoton("Gestión Clientes");
        JButton btnProductos = crearBoton("Gestión Productos");
        JButton btnProveedores = crearBoton("Gestión Proveedores");
        JButton btnVentas = crearBoton("Gestión Ventas");
        JButton btnCompras = crearBoton("Gestión Compras");
        JButton btnContable = crearBoton("Gestión Contable");
        JButton btnConsultas = crearBoton("Módulo Consultas");
        JButton btnReportes = crearBoton("Panel de Reportes");

        // Acciones
        btnClientes.addActionListener(e -> new GestionClientes().setVisible(true));
        btnProductos.addActionListener(e -> new GestionProductos().setVisible(true));
        btnProveedores.addActionListener(e -> new GestionProveedores().setVisible(true));
        btnVentas.addActionListener(e -> new GestionVentas().setVisible(true));
        btnCompras.addActionListener(e -> new GestionCompras().setVisible(true));
        btnContable.addActionListener(e -> new GestionContable().setVisible(true));
        btnConsultas.addActionListener(e -> new ModuloConsultas().setVisible(true));
        btnReportes.addActionListener(e -> new PanelReportes().setVisible(true));

        panel.add(btnClientes);
        panel.add(btnProductos);
        panel.add(btnProveedores);
        panel.add(btnVentas);
        panel.add(btnCompras);
        panel.add(btnContable);
        panel.add(btnConsultas);
        panel.add(btnReportes);

        return panel;
    }


    //BOTONES ESTILIZADOS

    private JButton crearBoton(String texto) {

        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(41, 128, 185));
        boton.setForeground(Color.WHITE);
        boton.setPreferredSize(new Dimension(200, 80));

        return boton;
    }

   
    // MAIN
    
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal().setVisible(true);
        });
    }
}