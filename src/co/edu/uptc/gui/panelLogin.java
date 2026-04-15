package co.edu.uptc.gui;

import java.awt.*;
import javax.swing.*;
import co.edu.uptc.negocio.credencialDto;

public class panelLogin extends JPanel {

    public JLabel lUsuario;
    public JLabel lContrasena;
    private JTextField tUsuario;
    private JTextField tContrasena;
    public JButton bIniciarSecion;
    public JButton bCancelar;
    private Eventos evento;

    public panelLogin(Eventos evento) {
        this.evento = evento;
        construirPanel();
    }

    public panelLogin() {
        this.evento = new Eventos();
        construirPanel();
    }

    private void construirPanel() {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("INICIAR SESION");
        titulo.setHorizontalAlignment(JLabel.CENTER);

        JPanel Campos = new JPanel(new GridLayout(4, 1, 10, 10));
        Campos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lUsuario    = new JLabel("Usuario:");
        lContrasena = new JLabel("Contrasena:");
        tUsuario    = new JTextField(15);
        tContrasena = new JTextField(15);

        bIniciarSecion = new JButton(Eventos.lINICIAR_SESION);
        bCancelar      = new JButton(Eventos.lCANCELAR);

        Campos.add(lUsuario);
        Campos.add(tUsuario);
        Campos.add(lContrasena);
        Campos.add(tContrasena);

        JPanel Botones = new JPanel(new GridLayout(1, 2, 20, 20));
        Botones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        Botones.add(bIniciarSecion);
        Botones.add(bCancelar);

        add(titulo,  BorderLayout.NORTH);
        add(Campos,  BorderLayout.CENTER);
        add(Botones, BorderLayout.SOUTH);

        bIniciarSecion.addActionListener(evento);
        bCancelar.addActionListener(evento);
    }

    public credencialDto getCredencialesUsuario() {
        credencialDto nuevo = new credencialDto();
        String usuario = tUsuario.getText();
        String contrasena = tContrasena.getText();

        if (usuario != null && !usuario.isBlank()) {
            nuevo.setUsuario(tUsuario.getText());
            if (contrasena != null && !contrasena.isBlank()) {
                nuevo.setContraseña(tContrasena.getText().getBytes());
                return nuevo;
            } else {
                JOptionPane.showMessageDialog(this, "Contraseña debe ser diligenciada");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Usuario debe ser diligenciado");
        }
        return null;
    }
}