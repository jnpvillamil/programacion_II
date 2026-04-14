package co.edu.uptc.gui;

import co.edu.uptc.negocio.*;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private JPanel panelContenido;
    private CardLayout cardLayout;
    private Color azulSidebar = new Color(13, 38, 64);

    public VentanaPrincipal() {
        GestionProducto gestionProd = new GestionProducto();
        GestionCliente gestionCli = new GestionCliente();
        GestionProveedor gestionProv = new GestionProveedor();
        GestionVenta gestionVen = new GestionVenta();
        GestionCompra gestionCom = new GestionCompra();
        
        Evento manejador = new Evento();

        setTitle("Sistema Comercial - Tienda Minorista");
        setSize(1150, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(azulSidebar);
        sidebar.setPreferredSize(new Dimension(230, 0));

        sidebar.add(Box.createVerticalStrut(40));

        JPanel pnlTitulo = new JPanel();
        pnlTitulo.setLayout(new BoxLayout(pnlTitulo, BoxLayout.Y_AXIS));
        pnlTitulo.setBackground(azulSidebar);
        pnlTitulo.setMaximumSize(new Dimension(230, 80));

        JLabel lblLinea1 = new JLabel("SISTEMA");
        lblLinea1.setForeground(Color.WHITE);
        lblLinea1.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblLinea1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblLinea2 = new JLabel("COMERCIAL");
        lblLinea2.setForeground(Color.WHITE);
        lblLinea2.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblLinea2.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlTitulo.add(lblLinea1);
        pnlTitulo.add(lblLinea2);
        sidebar.add(pnlTitulo);

        sidebar.add(Box.createVerticalGlue());

        JButton btnInv = crearBotonMenu("Inventario");
        JButton btnCli = crearBotonMenu("Clientes");
        JButton btnVen = crearBotonMenu("Ventas");
        JButton btnCom = crearBotonMenu("Compras");
        JButton btnProv = crearBotonMenu("Proveedores");

        JButton[] botones = {btnInv, btnCli, btnVen, btnCom, btnProv};
        for (JButton btn : botones) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            sidebar.add(btn);
            sidebar.add(Box.createVerticalStrut(15));
        }

        sidebar.add(Box.createVerticalGlue());

        JButton btnSalir = crearBotonMenu("CERRAR SESIÓN");
        btnSalir.setForeground(new Color(255, 100, 100));
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(btnSalir);
        sidebar.add(Box.createVerticalStrut(30));

        add(sidebar, BorderLayout.WEST);

        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);
        
        PanelProducto pProd = new PanelProducto(gestionProd, manejador);
        PanelCliente pCli = new PanelCliente(gestionCli, manejador);
        PanelVenta pVen = new PanelVenta(gestionProd, gestionCli, gestionVen, manejador);
        PanelCompra pCom = new PanelCompra(gestionProd, gestionCom, manejador);
        PanelProveedor pProv = new PanelProveedor(gestionProv, manejador);

        manejador.setPaneles(pProd, pVen, pCli, pCom, pProv);

        panelContenido.add(pProd, "INV");
        panelContenido.add(pVen, "VEN");
        panelContenido.add(pCli, "CLI");
        panelContenido.add(pCom, "COM");
        panelContenido.add(pProv, "PROV");

        add(panelContenido, BorderLayout.CENTER);

        btnInv.addActionListener(e -> cardLayout.show(panelContenido, "INV"));
        btnVen.addActionListener(e -> cardLayout.show(panelContenido, "VEN"));
        btnCli.addActionListener(e -> cardLayout.show(panelContenido, "CLI"));
        btnCom.addActionListener(e -> cardLayout.show(panelContenido, "COM"));
        btnProv.addActionListener(e -> cardLayout.show(panelContenido, "PROV"));
        
        btnSalir.addActionListener(e -> { 
            new VentanaLogin().setVisible(true); 
            this.dispose(); 
        });
    }

    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(180, 45));
        btn.setForeground(Color.WHITE);
        btn.setBackground(azulSidebar);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            new VentanaLogin().setVisible(true);
        });
    }
}