package uptc.edu.co.gui;

import uptc.edu.co.controller.UsuarioController;
import uptc.edu.co.controller.UsuarioController.Resultado;
import uptc.edu.co.model.Usuario;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

//Todo lo del usuario referente 

public class VentanaUsuarios extends JFrame {

    private JTable           tabla;
    private DefaultTableModel modeloTabla;
    private JTextField       txtNuevoPass;
    private JLabel           lblMensaje;

    private final UsuarioController controller = new UsuarioController();

    private final Color AZUL  = new Color(24, 95, 200);
    private final Color ROJO  = new Color(180, 40, 40);
    private final Color VERDE = new Color(40, 140, 60);
    private final Color BORDE = new Color(200, 210, 225);
    private final Color EXITO = new Color(34, 139, 34);
    private final Color ERROR = new Color(190, 30, 30);

    public VentanaUsuarios() {
        setTitle("Gestión de Usuarios");
        setSize(520, 440);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        construirUI();
        cargarTabla();
    }

    private void construirUI() {
        setLayout(new BorderLayout(0, 0));

        
        JPanel header = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0,0, new Color(40,130,60), getWidth(), 0, new Color(20,90,40)));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setPreferredSize(new Dimension(0, 50));
        JLabel lbl = new JLabel("👤  Gestión de Usuarios");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(16, 14, 300, 22);
        header.add(lbl);
        add(header, BorderLayout.NORTH);

        
        JPanel centro = new JPanel(new BorderLayout(0, 8));
        centro.setBackground(new Color(245, 247, 250));
        centro.setBorder(new EmptyBorder(12, 12, 8, 12));
        add(centro, BorderLayout.CENTER);

        
        String[] columnas = {"Usuario", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabla.setRowHeight(26);
        tabla.setSelectionBackground(new Color(210, 225, 250));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabla.getTableHeader().setBackground(new Color(230, 235, 245));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(new LineBorder(BORDE, 1, true));
        centro.add(scroll, BorderLayout.CENTER);

        
        JPanel panelPass = new JPanel(null);
        panelPass.setBackground(Color.WHITE);
        panelPass.setBorder(new CompoundBorder(new LineBorder(BORDE, 1, true), new EmptyBorder(8, 10, 8, 10)));
        panelPass.setPreferredSize(new Dimension(0, 70));

        JLabel lPass = new JLabel("Nueva contraseña:");
        lPass.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lPass.setForeground(new Color(60, 80, 120));
        lPass.setBounds(0, 12, 130, 18);
        panelPass.add(lPass);

        txtNuevoPass = new JTextField();
        txtNuevoPass.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtNuevoPass.setBounds(130, 8, 200, 28);
        txtNuevoPass.setBorder(new CompoundBorder(new LineBorder(BORDE, 1, true), new EmptyBorder(0,6,0,6)));
        panelPass.add(txtNuevoPass);

        JButton btnCambiar = boton("Cambiar", AZUL);
        btnCambiar.setBounds(340, 8, 100, 28);
        btnCambiar.addActionListener(e -> cambiarPassword());
        panelPass.add(btnCambiar);

        centro.add(panelPass, BorderLayout.NORTH);

        lblMensaje = new JLabel(" ");
        lblMensaje.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        centro.add(lblMensaje, BorderLayout.SOUTH);

        
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        botones.setBackground(new Color(236, 240, 245));
        botones.setBorder(new MatteBorder(1, 0, 0, 0, BORDE));

        JButton btnEliminar = boton("🗑 Eliminar usuario", ROJO);
        btnEliminar.setPreferredSize(new Dimension(180, 32));
        btnEliminar.addActionListener(e -> eliminarUsuario());

        JButton btnRefresh = boton("🔄 Actualizar lista", VERDE);
        btnRefresh.setPreferredSize(new Dimension(160, 32));
        btnRefresh.addActionListener(e -> cargarTabla());

        botones.add(btnEliminar);
        botones.add(btnRefresh);
        add(botones, BorderLayout.SOUTH);
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        try {
            List<Usuario> usuarios = controller.listarUsuarios();
            for (Usuario u : usuarios) {
                String estado = u.getNombre().equals("administrador") ? "Admin (protegido)" : "Activo";
                modeloTabla.addRow(new Object[]{ u.getNombre(), estado });
            }
        } catch (IOException e) {
            lblMensaje.setForeground(ERROR);
            lblMensaje.setText("Error al cargar usuarios: " + e.getMessage());
        }
    }

    private void eliminarUsuario() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            lblMensaje.setForeground(ERROR);
            lblMensaje.setText("✖  Selecciona un usuario de la tabla");
            return;
        }
        String nombre = modeloTabla.getValueAt(fila, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Eliminar al usuario '" + nombre + "'?",
            "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Resultado r = controller.eliminar(nombre);
            lblMensaje.setForeground(r.exito ? EXITO : ERROR);
            lblMensaje.setText((r.exito ? "✔  " : "✖  ") + r.mensaje);
            if (r.exito) cargarTabla();
        }
    }

    private void cambiarPassword() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            lblMensaje.setForeground(ERROR);
            lblMensaje.setText("✖  Selecciona un usuario de la tabla");
            return;
        }
        String nombre   = modeloTabla.getValueAt(fila, 0).toString();
        String nuevaPass = txtNuevoPass.getText().trim();
        Resultado r = controller.actualizarPassword(nombre, nuevaPass);
        lblMensaje.setForeground(r.exito ? EXITO : ERROR);
        lblMensaje.setText((r.exito ? "✔  " : "✖  ") + r.mensaje);
        if (r.exito) txtNuevoPass.setText("");
    }

    private JButton boton(String texto, Color color) {
        JButton btn = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? color.darker() : color);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
