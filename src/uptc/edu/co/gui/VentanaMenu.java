package uptc.edu.co.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * Aqui va el codigo depues del login 
 * y se ingresa datos del ususario
 */
public class VentanaMenu extends JFrame {

    private final Color COLOR_FONDO    = new Color(245, 247, 250);
    private final Color COLOR_PRIMARIO = new Color(24, 95, 200);
    private final Color COLOR_PRIM2    = new Color(16, 70, 160);

    public VentanaMenu() {
        setTitle("Sistema de Ventas — Menú Principal");
        setSize(420, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        construirUI();
    }

    private void construirUI() {
        JPanel panel = new JPanel(null);
        panel.setBackground(COLOR_FONDO);

        // despliege 3 menus
        JPanel header = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, COLOR_PRIMARIO, getWidth(), 0, COLOR_PRIM2));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setBounds(0, 0, 420, 80);
        panel.add(header);

        JLabel lblIcono = new JLabel("🏪");
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        lblIcono.setBounds(180, 14, 60, 45);
        header.add(lblIcono);

        JLabel lblTitulo = new JLabel("Sistema de Ventas UPTC");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 90, 420, 28);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblTitulo);

        JLabel lblSub = new JLabel("Selecciona un módulo para continuar");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSub.setForeground(new Color(130, 140, 160));
        lblSub.setBounds(0, 118, 420, 18);
        lblSub.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblSub);

        
        int yBase = 155;
        int alto  = 50;
        int gap   = 14;

        
        JButton btnProductos = crearBotonMenu(
            "📦  Gestión de Productos",
            "Crear, ver, editar y eliminar productos",
            new Color(24, 95, 200), new Color(16, 70, 160)
        );
        btnProductos.setBounds(40, yBase, 340, alto);
        btnProductos.addActionListener(e -> {
            new VentanaProductos().setVisible(true);
        });
        panel.add(btnProductos);

        
        JButton btnUsuarios = crearBotonMenu(
            "👤  Gestión de Usuarios",
            "Ver y eliminar usuarios del sistema",
            new Color(60, 130, 60), new Color(40, 100, 40)
        );
        btnUsuarios.setBounds(40, yBase + alto + gap, 340, alto);
        btnUsuarios.addActionListener(e -> {
            new VentanaUsuarios().setVisible(true);
        });
        panel.add(btnUsuarios);

        
        JButton btnSalir = crearBotonMenu(
            "🚪  Cerrar sesión",
            "Volver a la pantalla de inicio de sesión",
            new Color(160, 50, 50), new Color(120, 30, 30)
        );
        btnSalir.setBounds(40, yBase + (alto + gap) * 2, 340, alto);
        btnSalir.addActionListener(e -> {
            dispose();
            new VentanaLogin().setVisible(true);
        });
        panel.add(btnSalir);

        add(panel);
    }

    
    private JButton crearBotonMenu(String titulo, String subtitulo, Color c1, Color c2) {
        JButton btn = new JButton() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color fondo = getModel().isRollover() ? c2 : c1;
                g2.setColor(fondo);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                
                g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
                g2.setColor(Color.WHITE);
                g2.drawString(titulo, 16, 20);

                
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                g2.setColor(new Color(220, 230, 245));
                g2.drawString(subtitulo, 16, 36);
            }
        };
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
