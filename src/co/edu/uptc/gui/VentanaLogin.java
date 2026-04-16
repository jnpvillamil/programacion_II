package co.edu.uptc.gui;
import co.edu.uptc.negocio.Persona;
import co.edu.uptc.negocio.SistemaGestion;
import javax.swing.*;
import java.awt.*;

public class VentanaLogin extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private SistemaGestion sistemaGestion;

    public VentanaLogin(SistemaGestion sistemaGestion) {
        this.sistemaGestion = sistemaGestion;
        setTitle("Login - Tienda Minorista");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(244, 246, 249)); // Fondo claro

        JLabel lblTitulo = new JLabel("Bienvenido", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(44, 62, 80)); // Pizarra oscuro (Alto contraste)
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridLayout(2, 2, 10, 15));
        panelForm.setOpaque(false);
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        JLabel lblUsu = new JLabel("Usuario:");
        lblUsu.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblUsu.setForeground(Color.BLACK); // Contraste fuerte
        panelForm.add(lblUsu);
        
        txtUsuario = new JTextField();
        panelForm.add(txtUsuario);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblPass.setForeground(Color.BLACK);
        panelForm.add(lblPass);
        
        txtContrasena = new JPasswordField();
        panelForm.add(txtContrasena);
        
        add(panelForm, BorderLayout.CENTER);

        JButton btnIniciar = new JButton("Iniciar Sesión");
        btnIniciar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnIniciar.setBackground(new Color(52, 152, 219)); // Azul
        btnIniciar.setForeground(Color.WHITE); // Letra blanca
        
        // 🔴 LA SOLUCIÓN MÁGICA PARA MAC/WINDOWS EN SWING 🔴
        btnIniciar.setOpaque(true);
        btnIniciar.setBorderPainted(false);
        
        JPanel panelSur = new JPanel();
        panelSur.setOpaque(false);
        panelSur.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelSur.add(btnIniciar);
        add(panelSur, BorderLayout.SOUTH);

        btnIniciar.addActionListener(e -> validarCredenciales());
    }

    public void validarCredenciales() {
        String usuario = txtUsuario.getText();
        String pass = new String(txtContrasena.getPassword());
        Persona logueado = sistemaGestion.iniciarSesion(usuario, pass);

        if (logueado != null) {
            String rol = logueado.getClass().getSimpleName();
            new VentanaPrincipal(sistemaGestion, rol).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}