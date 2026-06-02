package co.edu.uptc.gui;

import co.edu.uptc.dto.UsuarioDTO;
import co.edu.uptc.gui.evento.EventoAdministracion;
import co.edu.uptc.gui.evento.EventoComercial;
import co.edu.uptc.interfaces.ManejadorEventoAdministracion;
import co.edu.uptc.interfaces.ManejadorEventoComercial;
import co.edu.uptc.interfaces.ManejadorEventoSistema;
import co.edu.uptc.negocio.GestionCliente;
import co.edu.uptc.negocio.GestionCompra;
import co.edu.uptc.negocio.GestionContable;
import co.edu.uptc.negocio.GestionProducto;
import co.edu.uptc.negocio.GestionProveedor;
import co.edu.uptc.interfaces.ProveedorUsuarioSesion;
import co.edu.uptc.negocio.GestionUsuario;
import co.edu.uptc.negocio.GestionVenta;
import co.edu.uptc.negocio.ServicioAuditoria;
import co.edu.uptc.persistencia.PersistenciaAdministracion;
import co.edu.uptc.persistencia.PersistenciaComercial;
import co.edu.uptc.utilidades.ConstructorComponentes;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private JPanel panelContenedor;
    private CardLayout cardLayout;

    public VentanaPrincipal(UsuarioDTO usuarioAutenticado,
                            ManejadorEventoSistema manejadorEventoSistema,
                            GestionUsuario gestionUsuario,
                            GestionContable gestionContable,
                            ServicioAuditoria servicioAuditoria,
                            ProveedorUsuarioSesion proveedorUsuarioSesion) {
        setTitle("Sistema de Gestión - Tienda Minorista");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        inicializarComponentes(
                usuarioAutenticado,
                manejadorEventoSistema,
                gestionUsuario,
                gestionContable,
                servicioAuditoria,
                proveedorUsuarioSesion);
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes(UsuarioDTO usuarioAutenticado,
                                        ManejadorEventoSistema manejadorEventoSistema,
                                        GestionUsuario gestionUsuario,
                                        GestionContable gestionContable,
                                        ServicioAuditoria servicioAuditoria,
                                        ProveedorUsuarioSesion proveedorUsuarioSesion) {
        JPanel menuLateral = new JPanel(new GridLayout(10, 1, 0, 2));
        menuLateral.setBackground(ConstructorComponentes.COLOR_MENU_OSCURO);
        menuLateral.setPreferredSize(new Dimension(220, 0));

        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        ConstructorComponentes.aplicarFondoPanel(panelContenedor);
        manejadorEventoSistema.configurarNavegacion(panelContenedor, cardLayout);

        String[] opciones = {"Inicio", "Productos", "Clientes", "Proveedores", "Ventas", "Compras", "Contabilidad"};
        String[] nombresCard = {"Home", "Prod", "Cli", "Prov", "Vent", "Comp", "Cont"};

        PersistenciaAdministracion persistenciaAdministracion = new PersistenciaAdministracion();
        GestionCliente gestionCliente = new GestionCliente(persistenciaAdministracion);
        GestionProveedor gestionProveedor = new GestionProveedor(persistenciaAdministracion);
        GestionProducto gestionProducto = new GestionProducto(persistenciaAdministracion);

        ManejadorEventoAdministracion eventoAdministracion = new EventoAdministracion(
                gestionCliente, gestionProveedor, gestionProducto, gestionUsuario);

        PersistenciaComercial persistenciaComercial = new PersistenciaComercial();
        GestionVenta gestionVenta = new GestionVenta(
                persistenciaComercial,
                gestionProducto,
                gestionContable,
                gestionCliente,
                servicioAuditoria,
                proveedorUsuarioSesion);
        GestionCompra gestionCompra = new GestionCompra(
                persistenciaComercial,
                gestionProducto,
                gestionContable,
                gestionProveedor,
                servicioAuditoria,
                proveedorUsuarioSesion);

        ManejadorEventoComercial eventoComercial = new EventoComercial(gestionVenta, gestionCompra);

        PanelVenta panelVenta = new PanelVenta(eventoComercial);
        PanelCompra panelCompra = new PanelCompra(eventoComercial);

        for (int i = 0; i < opciones.length; i++) {
            JButton btn = ConstructorComponentes.crearBotonMenu(opciones[i]);
            final String card = nombresCard[i];
            btn.addActionListener(evento -> {
                manejadorEventoSistema.mostrarPanel(card);
                if ("Vent".equals(card)) {
                    panelVenta.inicializarPanel();
                } else if ("Comp".equals(card)) {
                    panelCompra.inicializarPanel();
                }
            });
            menuLateral.add(btn);
        }

        panelContenedor.add(new PanelHome(
                usuarioAutenticado,
                eventoAdministracion,
                () -> {
                    manejadorEventoSistema.cerrarSesion();
                    dispose();
                    SwingUtilities.invokeLater(() -> new VentanaLogin().setVisible(true));
                }), "Home");
        panelContenedor.add(new PanelCliente(eventoAdministracion), "Cli");
        panelContenedor.add(new PanelProveedor(eventoAdministracion), "Prov");
        panelContenedor.add(new PanelProducto(eventoAdministracion), "Prod");
        panelContenedor.add(panelVenta, "Vent");
        panelContenedor.add(panelCompra, "Comp");
        panelContenedor.add(new PanelContabilidad(manejadorEventoSistema), "Cont");

        add(menuLateral, BorderLayout.WEST);
        add(panelContenedor, BorderLayout.CENTER);
        cardLayout.show(panelContenedor, "Home");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            VentanaLogin login = new VentanaLogin();
            login.setVisible(true);
        });
    }
}
