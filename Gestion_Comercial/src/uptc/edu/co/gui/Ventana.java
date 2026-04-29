package uptc.edu.co.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ventana extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private JButton btnRegistrar;
    private JLabel lblMensaje;

    private int intentos = 3;

    private final Color COLOR_FONDO      = new Color(245, 247, 250);
    private final Color COLOR_TARJETA    = Color.WHITE;
    private final Color COLOR_PRIMARIO   = new Color(24, 95, 200);
    private final Color COLOR_PRIMARIO2  = new Color(16, 70, 160);
    private final Color COLOR_EXITO      = new Color(34, 139, 34);
    private final Color COLOR_ERROR      = new Color(190, 30, 30);
    private final Color COLOR_BORDE      = new Color(200, 210, 225);
    private final Color COLOR_ESTADO_BG  = new Color(236, 240, 245);
    private final Color COLOR_PLACEHOLDER= new Color(170, 180, 195);

    public Ventana() {
        setTitle("Inicio de Sesión — Gestión Comercial");
        setSize(400, 460);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(null);
        panelPrincipal.setBackground(COLOR_FONDO);

        JPanel tarjeta = new JPanel(null);
        tarjeta.setBackground(COLOR_TARJETA);
        tarjeta.setBounds(30, 20, 340, 390);
        tarjeta.setBorder(new LineBorder(COLOR_BORDE, 1, true));
        panelPrincipal.add(tarjeta);

        JPanel franja = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, COLOR_PRIMARIO, getWidth(), 0, COLOR_PRIMARIO2);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        franja.setBounds(0, 0, 340, 70);
        tarjeta.add(franja);

        JLabel lblIcono = new JLabel("🔐");
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        lblIcono.setBounds(145, 18, 50, 35);
        franja.add(lblIcono);

        JLabel lblTitulo = new JLabel("Sistema de Ventas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblTitulo.setForeground(new Color(30, 50, 90));
        lblTitulo.setBounds(0, 80, 340, 28);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        tarjeta.add(lblTitulo);

        JLabel lblSub = new JLabel("Ingresa tus credenciales para continuar");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSub.setForeground(new Color(130, 140, 160));
        lblSub.setBounds(0, 108, 340, 18);
        lblSub.setHorizontalAlignment(SwingConstants.CENTER);
        tarjeta.add(lblSub);

        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblUsuario.setForeground(new Color(60, 80, 120));
        lblUsuario.setBounds(30, 138, 80, 18);
        tarjeta.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(30, 158, 280, 32);
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtUsuario.setBorder(new CompoundBorder(new LineBorder(COLOR_BORDE, 1, true), new EmptyBorder(0, 8, 0, 8)));
        txtUsuario.setText("Escribe tu usuario...");
        txtUsuario.setForeground(COLOR_PLACEHOLDER);
        txtUsuario.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (txtUsuario.getText().equals("Escribe tu usuario...")) {
                    txtUsuario.setText("");
                    txtUsuario.setForeground(new Color(30, 40, 60));
                }
                txtUsuario.setBorder(new CompoundBorder(new LineBorder(COLOR_PRIMARIO, 2, true), new EmptyBorder(0, 8, 0, 8)));
            }
            public void focusLost(FocusEvent e) {
                if (txtUsuario.getText().isEmpty()) {
                    txtUsuario.setText("Escribe tu usuario...");
                    txtUsuario.setForeground(COLOR_PLACEHOLDER);
                }
                txtUsuario.setBorder(new CompoundBorder(new LineBorder(COLOR_BORDE, 1, true), new EmptyBorder(0, 8, 0, 8)));
            }
        });
        tarjeta.add(txtUsuario);

        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblPassword.setForeground(new Color(60, 80, 120));
        lblPassword.setBounds(30, 202, 100, 18);
        tarjeta.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(30, 222, 244, 32);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPassword.setEchoChar('*');
        txtPassword.setBorder(new CompoundBorder(new LineBorder(COLOR_BORDE, 1, true), new EmptyBorder(0, 8, 0, 8)));
        txtPassword.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                txtPassword.setBorder(new CompoundBorder(new LineBorder(COLOR_PRIMARIO, 2, true), new EmptyBorder(0, 8, 0, 8)));
            }
            public void focusLost(FocusEvent e) {
                txtPassword.setBorder(new CompoundBorder(new LineBorder(COLOR_BORDE, 1, true), new EmptyBorder(0, 8, 0, 8)));
            }
        });
        tarjeta.add(txtPassword);

        JButton btnOjo = new JButton("👁");
        btnOjo.setBounds(277, 222, 33, 32);
        btnOjo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        btnOjo.setContentAreaFilled(false);
        btnOjo.setBorderPainted(false);
        btnOjo.setFocusPainted(false);
        btnOjo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnOjo.addActionListener(e -> {
            if (txtPassword.echoCharIsSet()) {
                txtPassword.setEchoChar((char) 0);
                btnOjo.setText("🙈");
            } else {
                txtPassword.setEchoChar('*');
                btnOjo.setText("👁");
            }
        });
        tarjeta.add(btnOjo);

        btnIngresar = crearBoton("Ingresar", COLOR_PRIMARIO, COLOR_PRIMARIO2);
        btnIngresar.setBounds(30, 275, 130, 36);
        tarjeta.add(btnIngresar);

        btnRegistrar = crearBoton("Registrar", new Color(60, 160, 80), new Color(40, 120, 60));
        btnRegistrar.setBounds(180, 275, 130, 36);
        tarjeta.add(btnRegistrar);

        lblMensaje = new JLabel("");
        lblMensaje.setBounds(0, 322, 340, 18);
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Segoe UI", Font.BOLD, 11));
        tarjeta.add(lblMensaje);

        JSeparator sep = new JSeparator();
        sep.setBounds(30, 352, 280, 2);
        sep.setForeground(COLOR_BORDE);
        tarjeta.add(sep);

        JLabel lblInfo = new JLabel("💾  Los usuarios se guardan en usuarios.txt");
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblInfo.setForeground(new Color(150, 160, 180));
        lblInfo.setBounds(0, 360, 340, 18);
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        tarjeta.add(lblInfo);

        JPanel panelEstado = new JPanel(null);
        panelEstado.setBackground(COLOR_ESTADO_BG);
        panelEstado.setBounds(0, 428, 400, 28);
        panelPrincipal.add(panelEstado);

        JLabel lblConectado = new JLabel("● Conectado");
        lblConectado.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblConectado.setForeground(new Color(34, 160, 60));
        lblConectado.setBounds(10, 7, 100, 14);
        panelEstado.add(lblConectado);

        JLabel lblReloj = new JLabel("00:00:00");
        lblReloj.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblReloj.setForeground(new Color(110, 120, 140));
        lblReloj.setBounds(305, 7, 80, 14);
        panelEstado.add(lblReloj);

        add(panelPrincipal);

        new Timer(1000, e -> lblReloj.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()))).start();

        btnIngresar.addActionListener(e -> validarLogin());
        txtPassword.addActionListener(e -> validarLogin());
        btnRegistrar.addActionListener(e -> abrirRegistro());
        getRootPane().setDefaultButton(btnIngresar);
    }

    private JButton crearBoton(String texto, Color c1, Color c2) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color base = isEnabled() ? (getModel().isRollover() ? c2 : c1) : new Color(180, 190, 210);
                g2.setColor(base);
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

    private void validarLogin() {
        String usuario = txtUsuario.getText().trim();
        String pass    = new String(txtPassword.getPassword()).trim();

        if (usuario.equals("Escribe tu usuario...") || usuario.isEmpty() || pass.isEmpty()) {
            lblMensaje.setForeground(new Color(180, 110, 0));
            lblMensaje.setText("⚠  Completa todos los campos");
            return;
        }

        // Consulta el archivo usuarios.txt
        if (GestorUsuarios.verificarLogin(usuario, pass)) {
            lblMensaje.setForeground(COLOR_EXITO);
            lblMensaje.setText("✔  Bienvenido, " + usuario + "!");
            btnIngresar.setEnabled(false);
            btnRegistrar.setEnabled(false);
            // Aquí puedes abrir la siguiente ventana:
            // new MenuPrincipal().setVisible(true);
            // dispose();
        } else {
            intentos--;
            lblMensaje.setForeground(COLOR_ERROR);
            if (intentos > 0) {
                lblMensaje.setText("✖  Credenciales incorrectas. Intentos restantes: " + intentos);
            } else {
                lblMensaje.setText("✖  Cuenta bloqueada. Demasiados intentos.");
                btnIngresar.setEnabled(false);
                btnRegistrar.setEnabled(false);
                txtUsuario.setEnabled(false);
                txtPassword.setEnabled(false);
            }
            txtPassword.setText("");
            txtPassword.requestFocus();
        }
    }

    private void abrirRegistro() {
        JDialog dialogo = new JDialog(this, "Registrar nuevo usuario", true);
        dialogo.setSize(320, 260);
        dialogo.setLocationRelativeTo(this);
        dialogo.setResizable(false);

        JPanel p = new JPanel(null);
        p.setBackground(Color.WHITE);

        JLabel lTitulo = new JLabel("Crear nuevo usuario");
        lTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lTitulo.setForeground(new Color(30, 50, 90));
        lTitulo.setBounds(0, 15, 320, 22);
        lTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        p.add(lTitulo);

        JLabel lU = new JLabel("Usuario:");
        lU.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lU.setForeground(new Color(60, 80, 120));
        lU.setBounds(30, 50, 80, 18);
        p.add(lU);

        JTextField tfU = new JTextField();
        tfU.setBounds(30, 68, 260, 30);
        tfU.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tfU.setBorder(new CompoundBorder(new LineBorder(COLOR_BORDE, 1, true), new EmptyBorder(0, 8, 0, 8)));
        p.add(tfU);

        JLabel lP = new JLabel("Contraseña:");
        lP.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lP.setForeground(new Color(60, 80, 120));
        lP.setBounds(30, 108, 100, 18);
        p.add(lP);

        JPasswordField tfP = new JPasswordField();
        tfP.setBounds(30, 126, 260, 30);
        tfP.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tfP.setEchoChar('*');
        tfP.setBorder(new CompoundBorder(new LineBorder(COLOR_BORDE, 1, true), new EmptyBorder(0, 8, 0, 8)));
        p.add(tfP);

        JLabel lMsg = new JLabel("");
        lMsg.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lMsg.setBounds(0, 165, 320, 16);
        lMsg.setHorizontalAlignment(SwingConstants.CENTER);
        p.add(lMsg);

        JButton btnGuardar = crearBoton("Guardar usuario", new Color(60, 160, 80), new Color(40, 120, 60));
        btnGuardar.setBounds(70, 190, 180, 34);
        btnGuardar.addActionListener(e -> {
            String u  = tfU.getText().trim();
            String pw = new String(tfP.getPassword()).trim();

            if (u.isEmpty() || pw.isEmpty()) {
                lMsg.setForeground(new Color(180, 110, 0));
                lMsg.setText("⚠  Completa ambos campos");
                return;
            }
            if (pw.length() < 3) {
                lMsg.setForeground(COLOR_ERROR);
                lMsg.setText("✖  Contraseña mínimo 3 caracteres");
                return;
            }
            // Guarda en usuarios.txt
            if (GestorUsuarios.registrarUsuario(u, pw)) {
                lMsg.setForeground(COLOR_EXITO);
                lMsg.setText("✔  Usuario registrado correctamente");
                btnGuardar.setEnabled(false);
                new Timer(1500, ev -> dialogo.dispose()) {{ setRepeats(false); }}.start();
            } else {
                lMsg.setForeground(COLOR_ERROR);
                lMsg.setText("✖  Ese usuario ya existe");
            }
        });
        p.add(btnGuardar);

        dialogo.add(p);
        dialogo.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new Ventana().setVisible(true));
    }
}
