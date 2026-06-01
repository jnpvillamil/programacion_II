package co.edu.uptc.gui;

import co.edu.uptc.negocio.AppConfig;
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
    private JButton btnReportes;
    private JButton btnConsultas;
    private JButton btnCerrarSesion;

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
        btnReportes = ConstructorComponentes.crearBotonMenu("  📊  Reportes");
        btnConsultas = ConstructorComponentes.crearBotonMenu("  🔍  Consultas");
        btnCerrarSesion = ConstructorComponentes.crearBotonMenu("  🚪  Cerrar Sesión");

        sidebar.add(btnInventario);
        sidebar.add(btnClientes);
        sidebar.add(btnVentas);
        sidebar.add(btnCompras);
        sidebar.add(btnProveedores);
        sidebar.add(btnReportes);
        sidebar.add(btnConsultas);
        sidebar.add(btnCerrarSesion);

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
    public JButton getBtnReportes() { return btnReportes; }
    public JButton getBtnConsultas() { return btnConsultas; }
    public JButton getBtnCerrarSesion() { return btnCerrarSesion; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppConfig::iniciarAplicacion);
    }
}
