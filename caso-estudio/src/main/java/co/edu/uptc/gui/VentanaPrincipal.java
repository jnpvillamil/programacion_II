package co.edu.uptc.gui;

import co.edu.uptc.controlador.*;
import co.edu.uptc.interfaces.IRepositorioCompra;
import co.edu.uptc.interfaces.IRepositorioContable;
import co.edu.uptc.interfaces.IRepositorioVenta;
import co.edu.uptc.interfaces.IPersistenciaProveedor;
import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.negocio.*;
import co.edu.uptc.persistencia.*;
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
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            boolean USAR_TXT = false;

            Repositorio<Producto> repoProducto;
            Repositorio<Cliente> repoCliente;
            IRepositorioVenta repoVenta;
            IRepositorioCompra repoCompra;
            IPersistenciaProveedor repoProveedor;
            IRepositorioContable repoContable;

            if (USAR_TXT) {
                repoProducto = new PersistenciaProductoTXT();
                repoCliente = new PersistenciaClienteTXT();
                repoVenta = new PersistenciaVentasTXT();
                repoCompra = new PersistenciaCompraTXT();
                repoProveedor = new PersistenciaProveedorTXT();
                repoContable = new PersistenciaContableTXT();
            } else {
                repoProducto = new PersistenciaProducto();
                repoCliente = new PersistenciaCliente();
                repoVenta = new PersistenciaVentas();
                repoCompra = new PersistenciaCompra();
                repoProveedor = new PersistenciaProveedor();
                repoContable = new PersistenciaContable();
            }

            GestionInventario gestionInventario = new GestionInventario(repoProducto);
            GestionClientes gestionClientes = new GestionClientes(repoCliente);
            GestionContable gestionContable = new GestionContable(repoContable);
            GestionVentas gestionVentas = new GestionVentas(repoVenta, gestionInventario, gestionContable);
            GestionProveedor gestionProveedor = new GestionProveedor(repoProveedor);
            GestionCompras gestionCompra = new GestionCompras(repoCompra, gestionInventario, gestionContable);
            GestionUsuarios gestionUsuarios = new GestionUsuarios();

            PersistenciaReportes persistenciaReportes = new PersistenciaReportes();
            PersistenciaConsultas persistenciaConsultas = new PersistenciaConsultas();
            GestionFinanciera gestionFinanciera = new GestionFinanciera();
            GestionReportes gestionReportes = new GestionReportes(
                    persistenciaReportes, gestionFinanciera, gestionContable,
                    gestionVentas, gestionCompra);
            GestionConsultas gestionConsultas = new GestionConsultas(
                    persistenciaConsultas, persistenciaReportes, gestionContable, gestionFinanciera);
            ServicioAutorizacion servicioAutorizacion = new ServicioAutorizacion();

            VentanaLogin ventanaLogin = new VentanaLogin();
            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();

            PanelProducto panelProducto = new PanelProducto();
            PanelClientes panelClientes = new PanelClientes();
            PanelVentas panelVentas = new PanelVentas();
            PanelCompra panelCompra = new PanelCompra();
            PanelProveedor panelProveedor = new PanelProveedor();
            PanelReportes panelReportes = new PanelReportes();
            PanelConsultas panelConsultas = new PanelConsultas();

            ventanaPrincipal.agregarPanel(panelProducto, "INVENTARIO");
            ventanaPrincipal.agregarPanel(panelClientes, "CLIENTES");
            ventanaPrincipal.agregarPanel(panelVentas, "VENTAS");
            ventanaPrincipal.agregarPanel(panelCompra, "COMPRAS");
            ventanaPrincipal.agregarPanel(panelProveedor, "PROVEEDORES");
            ventanaPrincipal.agregarPanel(panelReportes, "REPORTES");
            ventanaPrincipal.agregarPanel(panelConsultas, "CONSULTAS");

            ControladorPrincipal controladorPrincipal = new ControladorPrincipal(
                    ventanaPrincipal, gestionUsuarios, servicioAutorizacion);
            new ControladorLogin(ventanaLogin, ventanaPrincipal, gestionUsuarios, controladorPrincipal);
            new ControladorProducto(panelProducto, gestionInventario);
            new ControladorCliente(panelClientes, gestionClientes);
            new ControladorVentas(panelVentas, gestionVentas, gestionInventario, gestionClientes);
            new ControladorProveedor(panelProveedor, gestionProveedor);
            new ControladorCompra(panelCompra, gestionCompra, gestionProveedor, gestionInventario);
            new ControladorReportes(panelReportes, gestionReportes);
            new ControladorConsultas(panelConsultas, gestionConsultas);
            new ControladorCerrarSesion(ventanaPrincipal, ventanaLogin, gestionUsuarios);

            ventanaPrincipal.mostrarPanel("INVENTARIO");
            ventanaLogin.setVisible(true);
        });
    }
}
