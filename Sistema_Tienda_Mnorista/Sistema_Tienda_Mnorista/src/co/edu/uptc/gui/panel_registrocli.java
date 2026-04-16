package co.edu.uptc.gui;

import java.awt.Dimension;
import java.awt.Component;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import co.edu.uptc.Datos.*;

public class panel_registrocli extends JPanel {

    private JTextField nombre;
    private JTextField Numerodoc;
    private JTextField direccion;
    private JTextField telefono;
    private JComboBox<String> combo;
    private JComboBox<String> combot;

    public panel_registrocli(Evento e) {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 40));

        JLabel titulo = new JLabel("Registro cliente");
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(titulo);

        add(Box.createVerticalStrut(20));

        JPanel informacion = new JPanel();
        informacion.setLayout(new BoxLayout(informacion, BoxLayout.Y_AXIS));

        JLabel Nombrecli = new JLabel("Nombre del cliente");
        Nombrecli.setAlignmentX(Component.LEFT_ALIGNMENT);

        nombre = new JTextField();
        nombre.setMaximumSize(new Dimension(300, 40));
        nombre.setAlignmentX(Component.LEFT_ALIGNMENT);

    
        String[] opciones = {"CC", "NIT", "CE ","PA"};
        combo = new JComboBox<>(opciones);
        combo.setMaximumSize(new Dimension(200, 40));
        combo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel numerodoc = new JLabel("numero de documento");
        numerodoc.setAlignmentX(Component.LEFT_ALIGNMENT);

        Numerodoc = new JTextField();
        Numerodoc.setMaximumSize(new Dimension(300, 40));
        Numerodoc.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel Direccion = new JLabel("Direccion de la residencia");
        Direccion.setAlignmentX(Component.LEFT_ALIGNMENT);

        direccion = new JTextField();
        direccion.setMaximumSize(new Dimension(300, 40));
        direccion.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel Telefono = new JLabel("numero de telefono");
        Telefono.setAlignmentX(Component.LEFT_ALIGNMENT);

        telefono = new JTextField();
        telefono.setMaximumSize(new Dimension(300, 25));
        telefono.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] opciont = {"Minorista", "Mayorista"};
        combot = new JComboBox<>(opciont);
        combot.setMaximumSize(new Dimension(200, 40));
        combot.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel butones = new JPanel();
        butones.setBorder(new EmptyBorder(20, 0, 0, 0));

        JButton btncalcelar1 = new JButton(Evento.CANCELAR);
        btncalcelar1.addActionListener(e);
        btncalcelar1.setActionCommand(Evento.CANCELAR);

        JButton btnRegistrar = new JButton(Evento.REGISTRAR);
        btnRegistrar.addActionListener(e);
        btnRegistrar.setActionCommand(Evento.REGISTRAR);

        informacion.add(Nombrecli);
        informacion.add(Box.createVerticalStrut(5));
        informacion.add(nombre);
        informacion.add(Box.createVerticalStrut(10));

        informacion.add(new JLabel("Tipo de documento:"));
        informacion.add(Box.createVerticalStrut(5));
        informacion.add(combo);
        informacion.add(Box.createVerticalStrut(10));

        informacion.add(numerodoc);
        informacion.add(Box.createVerticalStrut(5));
        informacion.add(Numerodoc);
        informacion.add(Box.createVerticalStrut(10));

        informacion.add(Direccion);
        informacion.add(Box.createVerticalStrut(5));
        informacion.add(direccion);
        informacion.add(Box.createVerticalStrut(10));

        informacion.add(Telefono);
        informacion.add(Box.createVerticalStrut(5));
        informacion.add(telefono);
        informacion.add(Box.createVerticalStrut(10));

        informacion.add(new JLabel("Tipo de cliente:"));
        informacion.add(Box.createVerticalStrut(5));
        informacion.add(combot);

        butones.add(btncalcelar1);
        butones.add(btnRegistrar);

        add(informacion);
        add(butones);
    }

    public String getNombre() {
        return nombre.getText();
    }

    public String getNumeroDoc() {
        return Numerodoc.getText();
    }

    public String getDireccion() {
        return direccion.getText();
    }

    public String getTelefono() {
        return telefono.getText();
    }

    public String getTipoDoc() {
        return combo.getSelectedItem().toString();
    }

    public String getTipoCliente() {
        return combot.getSelectedItem().toString();
    }
}