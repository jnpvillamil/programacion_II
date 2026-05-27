package co.edu.uptc.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import co.edu.uptc.ventanas.MenuPrincipal;
import co.edu.uptc.ventanas.MenuVendedor;

@SuppressWarnings("serial")
public class LoginGUI extends JFrame implements ActionListener {

    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
    private JButton btnSalir;

    public LoginGUI() {
        setTitle("Sistema de Gestión");
        setSize(450, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        setLayout(new BorderLayout(10, 10));

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);

        URL rutaLogo = getClass().getResource("/recursos/LogoTM.png");

        if (rutaLogo != null) {
            ImageIcon iconoOriginal = new ImageIcon(rutaLogo);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.out.println("No se encontró el logo en la ruta: /recursos/LogoTM.png");
            lblLogo.setText("LOGO");
        }

        JLabel lblTitulo = new JLabel("Sistema de Gestión", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel lblSubtitulo = new JLabel("Inicio de sesión", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel panelTitulos = new JPanel(new GridLayout(2, 1));
        panelTitulos.add(lblTitulo);
        panelTitulos.add(lblSubtitulo);

        panelSuperior.add(lblLogo, BorderLayout.CENTER);
        panelSuperior.add(panelTitulos, BorderLayout.SOUTH);

        JPanel panelCentral = new JPanel(new GridLayout(2, 2, 10, 15));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        panelCentral.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panelCentral.add(txtUsuario);

        panelCentral.add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField();
        panelCentral.add(txtContrasena);

        JPanel panelInferior = new JPanel();
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setActionCommand("INGRESAR");
        btnIngresar.addActionListener(this);

        btnSalir = new JButton("Salir");
        btnSalir.setActionCommand("SALIR");
        btnSalir.addActionListener(this); 

        panelInferior.add(btnIngresar);
        panelInferior.add(btnSalir);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        
        if (comando.equals("INGRESAR")) {
       
            String usuario = txtUsuario.getText();
            String contrasena = new String(txtContrasena.getPassword());

            if (usuario.equals("admin") && contrasena.equals("1234")) {
                this.dispose(); 
                MenuPrincipal menuAdmin = new MenuPrincipal();
                menuAdmin.setVisible(true);

            } else if (usuario.equals("vendedor") && contrasena.equals("1234")) {
                this.dispose(); 
                MenuVendedor menuEmpleado = new MenuVendedor();
                menuEmpleado.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas de prueba.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (comando.equals("SALIR")) {
            System.exit(0); 
        }
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JPasswordField getTxtContrasena() {
        return txtContrasena;
    }

    public JButton getBtnIngresar() {
        return btnIngresar;
    }

    public JButton getBtnSalir() {
        return btnSalir;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}