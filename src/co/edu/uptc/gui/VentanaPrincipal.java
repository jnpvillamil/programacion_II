package co.edu.uptc.gui;

import co.edu.uptc.controlador.ControladorPrincipal;
import co.edu.uptc.utilidades.ConstructorComponentes;
import javax.swing.*;
import java.awt.*;

/**
 * Punto de entrada (main) y gestiona los paneles mediante CardLayout.
 */
public class VentanaPrincipal extends JFrame {
    private JPanel panelContenedor;
    private CardLayout cardLayout;
    private ControladorPrincipal controladorPrincipal;

    public VentanaPrincipal() {
        // Configuración básica de la ventana principal 
        setTitle("Sistema de Gestión - Tienda Minorista");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Inicialización de la navegación y el control
        inicializarComponentes();
        
        // Ubicación en el centro de la pantalla
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() {
        // 1. Menú Lateral (Diseño oscuro con botones blancos) 
        JPanel menuLateral = new JPanel(new GridLayout(10, 1, 0, 2));
        menuLateral.setBackground(ConstructorComponentes.COLOR_MENU_OSCURO);
        menuLateral.setPreferredSize(new Dimension(220, 0));

        // 2. Contenedor Central con CardLayout 
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        controladorPrincipal = new ControladorPrincipal(panelContenedor, cardLayout);

        // Definición de módulos
        String[] opciones = {"Inicio", "Productos", "Clientes", "Proveedores", "Ventas", "Compras", "Contabilidad"};
        String[] nombresCard = {"Home", "Prod", "Cli", "Prov", "Vent", "Comp", "Cont"};

        // Creación dinámica de botones del menú
        for (int i = 0; i < opciones.length; i++) {
            JButton btn = ConstructorComponentes.crearBotonMenu(opciones[i]);
            final String card = nombresCard[i];
            btn.addActionListener(e -> controladorPrincipal.mostrarPanel(card));
            menuLateral.add(btn);
        }

        // 3. Inicialización e integración de Paneles 
       // Instanciar dependencias para el módulo Cliente
        co.edu.uptc.persistencia.PersistenciaCliente persistenciaCliente = new co.edu.uptc.persistencia.PersistenciaCliente();
        co.edu.uptc.negocio.GestionCliente gestionCliente = new co.edu.uptc.negocio.GestionCliente(persistenciaCliente);
        co.edu.uptc.controlador.ControladorCliente controladorCliente = new co.edu.uptc.controlador.ControladorCliente(gestionCliente);

        //Agregar los paneles al contenedor
        panelContenedor.add(new PanelHome(), "Home");
        panelContenedor.add(new PanelProducto(), "Prod"); 
        
       
        panelContenedor.add(new PanelCliente(controladorCliente), "Cli"); 
        
        panelContenedor.add(new PanelProveedor(), "Prov");
        panelContenedor.add(new PanelVenta(), "Vent");
        panelContenedor.add(new PanelCompra(), "Comp");
        panelContenedor.add(new PanelContabilidad(), "Cont");

        // Agregar a la ventana
        add(menuLateral, BorderLayout.WEST);
        add(panelContenedor, BorderLayout.CENTER);
    }

    /**
     * Punto de entrada principal del sistema.
     * Lanza la ventana de Login para autenticación inicial.
     */
    public static void main(String[] args) {
        // Ejecución en el hilo de despacho de eventos de Swing para estabilidad visual
        SwingUtilities.invokeLater(() -> {
            try {
                // Aplicar Look and Feel del sistema para mejor integración visual 
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // Iniciar con la Ventana de Login 
            VentanaLogin login = new VentanaLogin();
            login.setVisible(true);
        });
    }
}