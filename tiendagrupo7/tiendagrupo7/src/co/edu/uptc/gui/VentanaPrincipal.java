package co.edu.uptc.gui;

import co.edu.uptc.controlador.*;
import co.edu.uptc.negocio.*;
import co.edu.uptc.utilidades.ConstructorComponentes;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private JPanel sidebar;
    private JButton btnInventario;
    private JButton btnClientes;
    private JButton btnVentas;
    private JButton btnCompras;
    private JButton btnProveedores;

    private JPanel panelCentral;
    private CardLayout cardLayout;

    public VentanaPrincipal() {
        setTitle("Sistema de Gestión de Tienda Minorista");
        setSize(1300, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(8, 1, 0, 10)); 
        sidebar.setBackground(ConstructorComponentes.AZUL_OSCURO); 
        sidebar.setPreferredSize(new Dimension(260, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(30, 15, 30, 15));

        btnInventario = ConstructorComponentes.crearBotonMenu("  📦  Inventario");
        btnClientes = ConstructorComponentes.crearBotonMenu("  👥  Clientes");
        btnVentas = ConstructorComponentes.crearBotonMenu("  🛒  Ventas");
        btnCompras = ConstructorComponentes.crearBotonMenu("  📥  Compras");
        btnProveedores = ConstructorComponentes.crearBotonMenu("  🚚  Proveedores");

        sidebar.add(btnInventario);
        sidebar.add(btnClientes);
        sidebar.add(btnVentas);
        sidebar.add(btnCompras);
        sidebar.add(btnProveedores);

        cardLayout = new CardLayout();
        panelCentral = new JPanel(cardLayout);
        panelCentral.setBackground(ConstructorComponentes.GRIS_FONDO); 

        this.add(sidebar, BorderLayout.WEST);
        this.add(panelCentral, BorderLayout.CENTER);
    }

    public void agregarPanel(JPanel panel, String nombreReferencia) {
        panelCentral.add(panel, nombreReferencia);
    }

    public void mostrarPanel(String nombreReferencia) {
        cardLayout.show(panelCentral, nombreReferencia);
    }

    public JButton getBtnInventario() { return btnInventario; }
    public JButton getBtnClientes() { return btnClientes; }
    public JButton getBtnVentas() { return btnVentas; }
    public JButton getBtnCompras() { return btnCompras; }
    public JButton getBtnProveedores() { return btnProveedores; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            GestionUsuarios gestionUsuarios = new GestionUsuarios();
            GestionInventario gestionInventario = new GestionInventario();
            GestionClientes gestionClientes = new GestionClientes();
            GestionVentas gestionVentas = new GestionVentas(gestionInventario);
            GestionProveedor gestionProveedor = new GestionProveedor();
            GestionCompras gestionCompra = new GestionCompras(gestionInventario);
            VentanaLogin ventanaLogin = new VentanaLogin();
            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();

            PanelProducto panelProducto = new PanelProducto();
            PanelClientes panelClientes = new PanelClientes();
            PanelVentas panelVentas = new PanelVentas();
            PanelCompra panelCompra = new PanelCompra();
            PanelProveedor panelProveedor = new PanelProveedor();

            ventanaPrincipal.agregarPanel(panelProducto, "INVENTARIO");
            ventanaPrincipal.agregarPanel(panelClientes, "CLIENTES");
            ventanaPrincipal.agregarPanel(panelVentas, "VENTAS");
            ventanaPrincipal.agregarPanel(panelCompra, "COMPRAS");
            ventanaPrincipal.agregarPanel(panelProveedor, "PROVEEDORES");

            new ControladorLogin(ventanaLogin, ventanaPrincipal, gestionUsuarios);
            new ControladorPrincipal(ventanaPrincipal);
            new ControladorProducto(panelProducto, gestionInventario);
            new ControladorCliente(panelClientes, gestionClientes);
            new ControladorVentas(panelVentas, gestionVentas, gestionInventario, gestionClientes);
            new ControladorProveedor(panelProveedor, gestionProveedor);
            new ControladorCompra(panelCompra, gestionCompra, gestionProveedor, gestionInventario);

            ventanaPrincipal.mostrarPanel("INVENTARIO"); 
            ventanaLogin.setVisible(true); 
        });
    }
}