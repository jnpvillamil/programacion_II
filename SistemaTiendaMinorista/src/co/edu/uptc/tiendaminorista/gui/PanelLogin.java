package co.edu.uptc.tiendaminorista.gui;

import java.awt.*;
import javax.swing.*;

import co.edu.uptc.tiendaminorista.dto.CredencialDto;

public class PanelLogin extends JPanel {

    private JTextField txUsuario;
    private JPasswordField contrasena;

    public PanelLogin(Evento e) {

        setLayout(new GridBagLayout());

        JPanel plogin = new JPanel();
        plogin.setLayout(new BoxLayout(plogin, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Inicio de sesión");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        plogin.add(titulo);

        plogin.add(Box.createVerticalStrut(15));
        
        //Etiqueta Usuario
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        plogin.add(lblUsuario);

        txUsuario = new JTextField();
        txUsuario.setMaximumSize(new Dimension(200, 30));
        txUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        plogin.add(txUsuario);

        plogin.add(Box.createVerticalStrut(10));
        
        //Etiqueta Contraseña
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setAlignmentX(Component.CENTER_ALIGNMENT);
        plogin.add(lblContrasena);

        contrasena = new JPasswordField();
        contrasena.setMaximumSize(new Dimension(200, 30));
        contrasena.setAlignmentX(Component.CENTER_ALIGNMENT);
        plogin.add(contrasena);

        plogin.add(Box.createVerticalStrut(15));

        JPanel botones = new JPanel();
        botones.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton btncalcelar = new JButton(Evento.SALIR);
        JButton btnentrar = new JButton(Evento.ENTRAR);
        btncalcelar.addActionListener(e);
        btncalcelar.setActionCommand(Evento.SALIR);
        btnentrar.setActionCommand(Evento.ENTRAR);
        btnentrar.addActionListener(e);
        botones.add(btncalcelar);
        botones.add(btnentrar);

        plogin.add(botones);
        
        plogin.add(Box.createVerticalStrut(20));
        
        //Credenciales de prueba
        JLabel lblAyuda = new JLabel("-Prueba con usr: admin / pw: 1234");
        lblAyuda.setFont(new Font("Arial", Font.ITALIC, 12));
        lblAyuda.setForeground(new Color(25, 70, 120));
        lblAyuda.setAlignmentX(Component.CENTER_ALIGNMENT);
        plogin.add(lblAyuda);

        add(plogin);
    }
    
    public CredencialDto getCredencialusuario() {
        String usuario = txUsuario.getText();
        String password = new String(contrasena.getPassword());
        
        if (usuario == null || usuario.isBlank()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un usuario");
            return null;
        }
        
        if (password == null || password.isBlank()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar una contraseña");
            return null;
        }
        
        CredencialDto credencial = new CredencialDto();
        credencial.setUsuario(usuario);
        credencial.setPassword(password.getBytes());
        
        return credencial;
    }
}