package co.edu.uptc.gui;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Panel_Actualizarcli extends JPanel {

    private JComboBox<String> comboClientes;

    
    private JTextField nombre;
    private JTextField direccion;
    private JTextField telefono;

    public Panel_Actualizarcli(Evento e) {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Actualizar cliente");
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(titulo);

        add(Box.createVerticalStrut(20));

        JPanel informacion = new JPanel();
        informacion.setLayout(new BoxLayout(informacion, BoxLayout.Y_AXIS));

        comboClientes = new JComboBox<>();
        comboClientes.setMaximumSize(new Dimension(300, 40));
        comboClientes.setAlignmentX(Component.LEFT_ALIGNMENT);

        informacion.add(new JLabel("Seleccionar cliente:"));
        informacion.add(Box.createVerticalStrut(5));
        informacion.add(comboClientes);
        informacion.add(Box.createVerticalStrut(10));

        JLabel Nombrecli = new JLabel("Nombre del cliente");
        Nombrecli.setAlignmentX(Component.LEFT_ALIGNMENT);

        nombre = new JTextField(); 
        nombre.setMaximumSize(new Dimension(200, 40));
        nombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel Direccion = new JLabel("Direccion de la residencia");
        Direccion.setAlignmentX(Component.LEFT_ALIGNMENT);

        direccion = new JTextField();
        direccion.setMaximumSize(new Dimension(200, 40));
        direccion.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel Telefono = new JLabel("numero de telefono");
        Telefono.setAlignmentX(Component.LEFT_ALIGNMENT);

        telefono = new JTextField(); 
        telefono.setMaximumSize(new Dimension(200, 25));
        telefono.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel butones = new JPanel();
        butones.setAlignmentX(CENTER_ALIGNMENT);
        butones.setBorder(new EmptyBorder(20, 0, 0, 0));

        JButton btncalcelar1 = new JButton(Evento.CANCELAR);
        btncalcelar1.addActionListener(e);
        btncalcelar1.setActionCommand(Evento.CANCELAR);

        JButton btnActualizarcli = new JButton(Evento.ACTUALIZARCLI);
        btnActualizarcli.addActionListener(e);
        btnActualizarcli.setActionCommand(Evento.ACTUALIZARCLI);

        JButton btndesactivarcli = new JButton(Evento.DESACTIVARCLI);
        btndesactivarcli.addActionListener(e);
        btndesactivarcli.setActionCommand(Evento.DESACTIVARCLI);

        informacion.add(Nombrecli);
        informacion.add(Box.createVerticalStrut(5));
        informacion.add(nombre);
        informacion.add(Box.createVerticalStrut(10));

        informacion.add(Direccion);
        informacion.add(Box.createVerticalStrut(5));
        informacion.add(direccion);
        informacion.add(Box.createVerticalStrut(10));

        informacion.add(Telefono);
        informacion.add(Box.createVerticalStrut(5));
        informacion.add(telefono);
        informacion.add(Box.createVerticalStrut(10));

        butones.add(btncalcelar1);
        butones.add(btnActualizarcli);
        butones.add(btndesactivarcli);

        add(informacion);
        add(butones);
    }


    public void cargarClientes(java.util.List<co.edu.uptc.Datos.Clientedt> lista) {

        comboClientes.removeAllItems();

        for (co.edu.uptc.Datos.Clientedt c : lista) {
            comboClientes.addItem(c.getNumerodocli());
        }
    }

  
    public String getClienteSeleccionado() {
        Object seleccionado = comboClientes.getSelectedItem();
        return (seleccionado != null) ? seleccionado.toString() : "";
    }


    public String getNombre() {
        return nombre.getText();
    }

    public String getDireccion() {
        return direccion.getText();
    }

    public String getTelefono() {
        return telefono.getText();
    }
}