package co.edu.uptc.gui;

import co.edu.uptc.negocio.SistemaGestion;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private JPanel panelMenu;
    private JPanel panelContenido;
    private CardLayout layoutPaneles;
    private Eventos eventos;
    private SistemaGestion sistemaGestion;
    private String rolUsuario;

    public VentanaPrincipal(SistemaGestion sistemaGestion, String rolUsuario) {
        this.sistemaGestion = sistemaGestion;
        this.rolUsuario = rolUsuario;
        this.eventos = new Eventos(this);
        
        setTitle("Sistema de Gestión - Perfil: " + rolUsuario);
        setSize(1050, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        iniciarComponentes();
    }

    public void iniciarComponentes() {
        // --- MENÚ LATERAL ---
        panelMenu = new JPanel(new GridLayout(9, 1, 0, 5));
        panelMenu.setBackground(new Color(44, 62, 80)); 
        panelMenu.setPreferredSize(new Dimension(220, 0));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        agregarBotonMenu("Productos", Eventos.CMD_VER_PRODUCTOS);
        agregarBotonMenu("Clientes", Eventos.CMD_VER_CLIENTES);
        agregarBotonMenu("Proveedores", Eventos.CMD_VER_PROVEEDORES);
        agregarBotonMenu("Ventas", Eventos.CMD_VER_VENTAS);
        agregarBotonMenu("Compras", Eventos.CMD_VER_COMPRAS);
        agregarBotonMenu("Contabilidad", Eventos.CMD_VER_CONTABILIDAD);
        
        add(panelMenu, BorderLayout.WEST);

        // --- CONTENIDO CENTRAL (CardLayout) ---
        layoutPaneles = new CardLayout();
        panelContenido = new JPanel(layoutPaneles);
        panelContenido.setBackground(new Color(244, 246, 249));

        // Aquí integramos los paneles con el SistemaGestion
        panelContenido.add(new PanelProductos(sistemaGestion), "PANEL_PRODUCTOS");
        panelContenido.add(new PanelCompras(sistemaGestion), "PANEL_COMPRAS");
        panelContenido.add(new PanelVentas(sistemaGestion), "PANEL_VENTAS");

        add(panelContenido, BorderLayout.CENTER);
    }

    private void agregarBotonMenu(String texto, String comando) {
        JButton btn = new JButton(texto);
        btn.setActionCommand(comando);
        btn.addActionListener(eventos);
        btn.setBackground(new Color(52, 73, 94));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelMenu.add(btn);
    }

    public void mostrarPanel(String nombrePanel) {
        layoutPaneles.show(panelContenido, nombrePanel);
    }

    // MÉTODO MAIN INTEGRADO (Punto de arranque del sistema)
    public static void main(String[] args) {
        // Configurar el aspecto visual de las ventanas (Look & Feel)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Ejecutar en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            // 1. Inicializar toda la capa de negocio y persistencia
            SistemaGestion sistema = new SistemaGestion();
            
            // 2. Abrir primero el Login (o cambiar a new VentanaPrincipal(...) para omitir login en pruebas)
            VentanaLogin login = new VentanaLogin(sistema);
            login.setVisible(true);
        });
    }
}