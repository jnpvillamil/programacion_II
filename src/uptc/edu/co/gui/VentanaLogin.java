package uptc.edu.co.gui;

import uptc.edu.co.controller.UsuarioController;
import uptc.edu.co.controller.UsuarioController.Resultado;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;


//Ingreso administrador 0000

public class VentanaLogin extends JFrame {

    
    private JTextField    txtUsuario;
    private JPasswordField txtPassword;
    private JButton       btnIngresar;
    private JButton       btnRegistrar;
    private JLabel        lblMensaje;

    
    private final UsuarioController controller = new UsuarioController();

    private int intentos = 3; 

   
    private final Color COLOR_FONDO     = new Color(245, 247, 250);
    private final Color COLOR_TARJETA   = Color.WHITE;
    private final Color COLOR_PRIMARIO  = new Color(24, 95, 200);
    private final Color COLOR_PRIMARIO2 = new Color(16, 70, 160);
    private final Color COLOR_EXITO     = new Color(34, 139, 34);
    private final Color COLOR_ERROR     = new Color(190, 30, 30);
    private final Color COLOR_BORDE     = new Color(200, 210, 225);
    private final Color COLOR_HOLDER    = new Color(170, 180, 195);

    
    public VentanaLogin() {
        setTitle("Inicio de Sesión — Sistema de Ventas UPTC");
        setSize(400, 470);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        construirUI();
    }

    // aqui va ka interfaz
    
    private void construirUI() {
        JPanel panelPrincipal = new JPanel(null);
        panelPrincipal.setBackground(COLOR_FONDO);

        // Tarjeta central
        JPanel tarjeta = new JPanel(null);
        tarjeta.setBackground(COLOR_TARJETA);
        tarjeta.setBounds(30, 20, 340, 400);
        tarjeta.setBorder(new LineBorder(COLOR_BORDE, 1, true));
        panelPrincipal.add(tarjeta);

        // la parte superior del sistemas ventas 
        
        JPanel franja = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, COLOR_PRIMARIO, getWidth(), 0, COLOR_PRIMARIO2));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        franja.setBounds(0, 0, 340, 70);
        tarjeta.add(franja);

        JLabel lblIcono = new JLabel("🔐");
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        lblIcono.setBounds(145, 18, 50, 35);
        franja.add(lblIcono);

        // Aqui va l titulo y subtitulo
        JLabel lblTitulo = crearLabel("Sistema de Ventas UPTC",
                new Font("Segoe UI", Font.BOLD, 16), new Color(30, 50, 90));
        lblTitulo.setBounds(0, 80, 340, 26);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        tarjeta.add(lblTitulo);

        JLabel lblSub = crearLabel("Ingresa tus credenciales para continuar",
                new Font("Segoe UI", Font.PLAIN, 11), new Color(130, 140, 160));
        lblSub.setBounds(0, 106, 340, 18);
        lblSub.setHorizontalAlignment(SwingConstants.CENTER);
        tarjeta.add(lblSub);

        // aqui va el  usuario la vista
        crearLabel("Usuario", new Font("Segoe UI", Font.BOLD, 11),
                   new Color(60, 80, 120)).setBounds(30, 136, 80, 18);
        tarjeta.add(crearLabel("Usuario", new Font("Segoe UI", Font.BOLD, 11),
                               new Color(60, 80, 120)));
        ((JLabel) tarjeta.getComponent(tarjeta.getComponentCount() - 1)).setBounds(30, 136, 80, 18);

        txtUsuario = new JTextField("Escribe tu usuario...");
        txtUsuario.setForeground(COLOR_HOLDER);
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtUsuario.setBounds(30, 156, 280, 32);
        txtUsuario.setBorder(campoBorde(false));
        txtUsuario.addFocusListener(placeholderFocus(txtUsuario, "Escribe tu usuario..."));
        tarjeta.add(txtUsuario);

        // Contraseña vista 
        JLabel lblPass = crearLabel("Contraseña", new Font("Segoe UI", Font.BOLD, 11),
                                    new Color(60, 80, 120));
        lblPass.setBounds(30, 200, 100, 18);
        tarjeta.add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setEchoChar('*');
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPassword.setBounds(30, 220, 244, 32);
        txtPassword.setBorder(campoBorde(false));
        txtPassword.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) { txtPassword.setBorder(campoBorde(true)); }
            public void focusLost(FocusEvent e)   { txtPassword.setBorder(campoBorde(false)); }
        });
        tarjeta.add(txtPassword);

        // Boton de ojo para ver y ocultar la contrseña
        JButton btnOjo = new JButton("👁");
        btnOjo.setBounds(277, 220, 33, 32);
        btnOjo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        btnOjo.setContentAreaFilled(false);
        btnOjo.setBorderPainted(false);
        btnOjo.setFocusPainted(false);
        btnOjo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnOjo.addActionListener(e -> {
            if (txtPassword.echoCharIsSet()) { txtPassword.setEchoChar((char) 0); btnOjo.setText("🙈"); }
            else                             { txtPassword.setEchoChar('*');        btnOjo.setText("👁"); }
        });
        tarjeta.add(btnOjo);

        // Botones Ingresar y Registrar color azul y color verde
        //AZUL
        btnIngresar = crearBoton("Ingresar", COLOR_PRIMARIO, COLOR_PRIMARIO2);
        btnIngresar.setBounds(30, 270, 130, 36);
        tarjeta.add(btnIngresar);
        //VERDE
        btnRegistrar = crearBoton("Registrar", new Color(60, 160, 80), new Color(40, 120, 60));
        btnRegistrar.setBounds(180, 270, 130, 36);
        tarjeta.add(btnRegistrar);

        // Etiqueta de mensajes
        lblMensaje = new JLabel("");
        lblMensaje.setBounds(0, 316, 340, 18);
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Segoe UI", Font.BOLD, 11));
        tarjeta.add(lblMensaje);

        
        JSeparator sep = new JSeparator();
        sep.setBounds(30, 346, 280, 2);
        sep.setForeground(COLOR_BORDE);
        tarjeta.add(sep);

        JLabel lblInfo = crearLabel("💾  Datos guardados en archivos .txt",
                new Font("Segoe UI", Font.PLAIN, 10), new Color(150, 160, 180));
        lblInfo.setBounds(0, 354, 340, 18);
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        tarjeta.add(lblInfo);

        // RELOJ
        JPanel barraEstado = new JPanel(null);
        barraEstado.setBackground(new Color(236, 240, 245));
        barraEstado.setBounds(0, 436, 400, 28);
        panelPrincipal.add(barraEstado);

        JLabel lblConectado = crearLabel("● Conectado",
                new Font("Segoe UI", Font.PLAIN, 11), new Color(34, 160, 60));
        lblConectado.setBounds(10, 7, 100, 14);
        barraEstado.add(lblConectado);

        JLabel lblReloj = crearLabel("00:00:00",
                new Font("Segoe UI", Font.PLAIN, 11), new Color(110, 120, 140));
        lblReloj.setBounds(305, 7, 80, 14);
        barraEstado.add(lblReloj);

        
        new Timer(1000, e -> lblReloj.setText(
                new SimpleDateFormat("HH:mm:ss").format(new Date()))).start();

        
        btnIngresar.addActionListener(e -> validarLogin());
        txtPassword.addActionListener(e -> validarLogin());
        btnRegistrar.addActionListener(e -> abrirRegistro());
        getRootPane().setDefaultButton(btnIngresar);

        add(panelPrincipal);
    }

    
    private void validarLogin() {
        String usuario  = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        
        if (usuario.equals("Escribe tu usuario...")) usuario = "";

        
        Resultado resultado = controller.login(usuario, password);

        if (resultado.exito) {
            mostrarMensaje(resultado.mensaje, COLOR_EXITO);
            btnIngresar.setEnabled(false);
            btnRegistrar.setEnabled(false);
            
            Timer t = new Timer(800, e -> {
                new VentanaMenu().setVisible(true);
                dispose(); 
            });
            t.setRepeats(false);
            t.start();
        } else {
            intentos--;
            String msg = resultado.mensaje;
            if (intentos > 0) {
                msg += " (intentos restantes: " + intentos + ")";
            } else {
                msg = "Cuenta bloqueada. Demasiados intentos fallidos.";
                bloquearCampos();
            }
            mostrarMensaje(msg, COLOR_ERROR);
            txtPassword.setText("");
            txtPassword.requestFocus();
        }
    }

    
    private void abrirRegistro() {
        JDialog dialogo = new JDialog(this, "Registrar usuario", true);
        dialogo.setSize(320, 240);
        dialogo.setLocationRelativeTo(this);
        dialogo.setResizable(false);

        JPanel p = new JPanel(null);
        p.setBackground(Color.WHITE);

        JLabel lTitulo = crearLabel("Crear nuevo usuario",
                new Font("Segoe UI", Font.BOLD, 14), new Color(30, 50, 90));
        lTitulo.setBounds(0, 15, 320, 22);
        lTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        p.add(lTitulo);

        JLabel lU = crearLabel("Usuario:", new Font("Segoe UI", Font.BOLD, 11), new Color(60, 80, 120));
        lU.setBounds(30, 48, 80, 18);
        p.add(lU);

        JTextField tfU = new JTextField();
        tfU.setBounds(30, 66, 260, 30);
        tfU.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tfU.setBorder(campoBorde(false));
        p.add(tfU);

        JLabel lP = crearLabel("Contraseña:", new Font("Segoe UI", Font.BOLD, 11), new Color(60, 80, 120));
        lP.setBounds(30, 106, 100, 18);
        p.add(lP);

        JPasswordField tfP = new JPasswordField();
        tfP.setBounds(30, 124, 260, 30);
        tfP.setEchoChar('*');
        tfP.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tfP.setBorder(campoBorde(false));
        p.add(tfP);

        JLabel lMsg = new JLabel("");
        lMsg.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lMsg.setBounds(0, 162, 320, 16);
        lMsg.setHorizontalAlignment(SwingConstants.CENTER);
        p.add(lMsg);

        JButton btnGuardar = crearBoton("Guardar", new Color(60, 160, 80), new Color(40, 120, 60));
        btnGuardar.setBounds(80, 182, 160, 34);
        btnGuardar.addActionListener(e -> {
            String u  = tfU.getText().trim();
            String pw = new String(tfP.getPassword()).trim();

            
            Resultado r = controller.registrar(u, pw);

            lMsg.setForeground(r.exito ? COLOR_EXITO : COLOR_ERROR);
            lMsg.setText(r.mensaje);

            if (r.exito) {
                btnGuardar.setEnabled(false);
                new Timer(1400, ev -> dialogo.dispose()) {{ setRepeats(false); }}.start();
            }
        });
        p.add(btnGuardar);

        dialogo.add(p);
        dialogo.setVisible(true);
    }

    
    private void mostrarMensaje(String texto, Color color) {
        lblMensaje.setForeground(color);
        lblMensaje.setText(texto);
    }

    private void bloquearCampos() {
        btnIngresar.setEnabled(false);
        btnRegistrar.setEnabled(false);
        txtUsuario.setEnabled(false);
        txtPassword.setEnabled(false);
    }

    private JLabel crearLabel(String texto, Font fuente, Color color) {
        JLabel l = new JLabel(texto);
        l.setFont(fuente);
        l.setForeground(color);
        return l;
    }

    private Border campoBorde(boolean activo) {
        Color c = activo ? COLOR_PRIMARIO : COLOR_BORDE;
        int w   = activo ? 2 : 1;
        return new CompoundBorder(new LineBorder(c, w, true), new EmptyBorder(0, 8, 0, 8));
    }

    private FocusListener placeholderFocus(JTextField campo, String placeholder) {
        return new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(new Color(30, 40, 60));
                }
                campo.setBorder(campoBorde(true));
            }
            public void focusLost(FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(COLOR_HOLDER);
                }
                campo.setBorder(campoBorde(false));
            }
        };
    }

    private JButton crearBoton(String texto, Color c1, Color c2) {
        JButton btn = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
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

    
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new VentanaLogin().setVisible(true));
    }
}
