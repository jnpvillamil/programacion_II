package co.edu.uptc.gui;

import co.edu.uptc.dto.UsuarioDTO;
import co.edu.uptc.enums.RolUsuario;
import co.edu.uptc.gui.evento.EventoAdministracion;
import co.edu.uptc.gui.evento.EventoBodeguero;
import co.edu.uptc.gui.evento.EventoComercial;
import co.edu.uptc.interfaces.ManejadorEventoAdministracion;
import co.edu.uptc.interfaces.ManejadorEventoBodeguero;
import co.edu.uptc.interfaces.ManejadorEventoComercial;
import co.edu.uptc.interfaces.ManejadorEventoSistema;
import co.edu.uptc.negocio.AppConfig;
import co.edu.uptc.utilidades.ConstructorComponentes;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private final AppConfig appConfig;
    private JPanel panelContenedor;
    private CardLayout cardLayout;

    public VentanaPrincipal(UsuarioDTO usuarioAutenticado,
                            AppConfig appConfig,
                            ManejadorEventoSistema manejadorEventoSistema) {
        this.appConfig = appConfig;
        setTitle("Sistema de Gestión - Tienda Minorista");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        inicializarComponentes(usuarioAutenticado, manejadorEventoSistema);
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes(UsuarioDTO usuarioAutenticado,
                                        ManejadorEventoSistema manejadorEventoSistema) {
        if (RolUsuario.BODEGUERO.name().equals(usuarioAutenticado.rol())) {
            inicializarVistaExclusivaBodeguero(manejadorEventoSistema);
            return;
        }

        JPanel menuLateral = new JPanel(new GridLayout(10, 1, 0, 2));
        menuLateral.setBackground(ConstructorComponentes.COLOR_MENU_OSCURO);
        menuLateral.setPreferredSize(new Dimension(220, 0));

        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        ConstructorComponentes.aplicarFondoPanel(panelContenedor);
        manejadorEventoSistema.configurarNavegacion(panelContenedor, cardLayout);

        String[] opcion = {"Inicio", "Productos", "Clientes", "Proveedores", "Ventas", "Compras", "Contabilidad"};
        String[] nombreCard = {"Home", "Prod", "Cli", "Prov", "Vent", "Comp", "Cont"};

        ManejadorEventoAdministracion eventoAdministracion = new EventoAdministracion(appConfig);
        ManejadorEventoComercial eventoComercial = new EventoComercial(appConfig);
        ManejadorEventoBodeguero eventoBodeguero = new EventoBodeguero(appConfig);
        PanelBodeguero panelBodeguero = new PanelBodeguero(eventoBodeguero);

        PanelVenta panelVenta = new PanelVenta(eventoComercial);
        PanelCompra panelCompra = new PanelCompra(eventoComercial);

        for (int i = 0; i < opcion.length; i++) {
            JButton boton = ConstructorComponentes.crearBotonMenu(opcion[i]);
            final String card = nombreCard[i];
            boton.addActionListener(evento -> {
                manejadorEventoSistema.mostrarPanel(card);
                if ("Vent".equals(card)) {
                    panelVenta.inicializarPanel();
                } else if ("Comp".equals(card)) {
                    panelCompra.inicializarPanel();
                }
            });
            menuLateral.add(boton);
        }

        panelContenedor.add(new PanelHome(
                usuarioAutenticado,
                eventoAdministracion,
                () -> {
                    manejadorEventoSistema.cerrarSesion();
                    dispose();
                    SwingUtilities.invokeLater(() -> new VentanaLogin(appConfig).setVisible(true));
                }), "Home");
        panelContenedor.add(new PanelCliente(eventoAdministracion), "Cli");
        panelContenedor.add(new PanelProveedor(eventoAdministracion), "Prov");
        panelContenedor.add(new PanelProducto(eventoAdministracion), "Prod");
        panelContenedor.add(panelVenta, "Vent");
        panelContenedor.add(panelCompra, "Comp");
        panelContenedor.add(new PanelContabilidad(manejadorEventoSistema), "Cont");
        panelContenedor.add(panelBodeguero, "PANEL_BODEGUERO");

        add(menuLateral, BorderLayout.WEST);
        add(panelContenedor, BorderLayout.CENTER);
        cardLayout.show(panelContenedor, "Home");
    }

    private void inicializarVistaExclusivaBodeguero(ManejadorEventoSistema manejadorEventoSistema) {
        setTitle("Módulo de Bodega - Acceso Restringido");

        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        ConstructorComponentes.aplicarFondoPanel(panelContenedor);
        manejadorEventoSistema.configurarNavegacion(panelContenedor, cardLayout);

        ManejadorEventoBodeguero eventoBodeguero = new EventoBodeguero(appConfig);
        PanelBodeguero panelBodeguero = new PanelBodeguero(eventoBodeguero);

        panelContenedor.add(panelBodeguero, "PANEL_BODEGUERO");

        JPanel menuRestringido = new JPanel(new GridLayout(2, 1, 0, 2));
        menuRestringido.setBackground(ConstructorComponentes.COLOR_MENU_OSCURO);
        menuRestringido.setPreferredSize(new Dimension(220, 0));

        JButton botonBodega = ConstructorComponentes.crearBotonMenu("Bodega");
        botonBodega.addActionListener(evento -> manejadorEventoSistema.mostrarPanel("PANEL_BODEGUERO"));
        menuRestringido.add(botonBodega);

        JButton botonCerrarSesion = ConstructorComponentes.crearBotonMenu("Cerrar Sesión");
        botonCerrarSesion.addActionListener(evento -> {
            manejadorEventoSistema.cerrarSesion();
            dispose();
            SwingUtilities.invokeLater(() -> new VentanaLogin(appConfig).setVisible(true));
        });
        menuRestringido.add(botonCerrarSesion);

        add(menuRestringido, BorderLayout.WEST);
        add(panelContenedor, BorderLayout.CENTER);
        cardLayout.show(panelContenedor, "PANEL_BODEGUERO");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception excepcion) {
                excepcion.printStackTrace();
            }

            AppConfig appConfig = AppConfig.getInstancia();
            VentanaLogin login = new VentanaLogin(appConfig);
            login.setVisible(true);
        });
    }
}
