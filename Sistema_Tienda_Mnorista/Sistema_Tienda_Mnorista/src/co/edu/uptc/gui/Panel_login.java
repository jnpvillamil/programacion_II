package co.edu.uptc.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Panel_login extends JPanel {

    private JTextField txUsuario;
    private JTextField txnumerodoc;

    public Panel_login(Evento e) {

        setLayout(new GridBagLayout());

        JPanel plogin = new JPanel();
        plogin.setLayout(new BoxLayout(plogin, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Inicio de sesión");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        plogin.add(titulo);

        plogin.add(Box.createVerticalStrut(15));

        txUsuario = new JTextField();
        txUsuario.setMaximumSize(new Dimension(200, 30));
        plogin.add(txUsuario);

        plogin.add(Box.createVerticalStrut(10));

        txnumerodoc = new JTextField();
        txnumerodoc.setMaximumSize(new Dimension(200, 30));
        plogin.add(txnumerodoc);

        plogin.add(Box.createVerticalStrut(15));

        JPanel botones = new JPanel();
        JButton btncalcelar =new JButton(Evento.SALIR);
        JButton btnentrar = new JButton(Evento.ENTRAR);
        btncalcelar.addActionListener(e);
        btncalcelar.setActionCommand(Evento.SALIR);
        btnentrar.setActionCommand(Evento.ENTRAR);
        btnentrar.addActionListener(e);
        botones.add(btncalcelar);
        botones.add(btnentrar);

        plogin.add(botones);

        add(plogin);
    }
}