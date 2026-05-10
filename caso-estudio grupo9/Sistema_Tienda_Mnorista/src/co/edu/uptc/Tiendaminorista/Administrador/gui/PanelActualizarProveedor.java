package co.edu.uptc.Tiendaminorista.Administrador.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import co.edu.uptc.Tiendaminorista.Gui.Evento;
import co.edu.uptc.Tiendaminorista.modelo.Proveedor;

public class PanelActualizarProveedor extends JPanel {

    private JComboBox<String> comboProveedores;
    private java.util.List<Proveedor> proveedores;
    private JTextField razonpro;
    private JTextField nitpro;
    private JTextField direccionpro;
    private JTextField telefonopro;
    private JTextField correopro1;

    public PanelActualizarProveedor(Evento e) {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titulo = new JLabel("Actualizar proveedor");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(titulo);

        contenedor.add(Box.createVerticalStrut(20));

        JPanel informacion = new JPanel();
        informacion.setLayout(new BoxLayout(informacion, BoxLayout.Y_AXIS));
        informacion.setAlignmentX(Component.CENTER_ALIGNMENT);

        comboProveedores = new JComboBox<>();
        comboProveedores.setMaximumSize(new Dimension(300, 40));
        comboProveedores.setAlignmentX(Component.CENTER_ALIGNMENT);
        comboProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                cargarProveedorSeleccionado();
            }
        });

        informacion.add(new JLabel("Seleccionar proveedor:"));
        ((JLabel) informacion.getComponent(informacion.getComponentCount() - 1)).setAlignmentX(Component.CENTER_ALIGNMENT);
        informacion.add(Box.createVerticalStrut(5));
        informacion.add(comboProveedores);
        informacion.add(Box.createVerticalStrut(10));

        JLabel Razonpro = new JLabel("Razon social");
        Razonpro.setAlignmentX(Component.CENTER_ALIGNMENT);

        razonpro = new JTextField();
        razonpro.setMaximumSize(new Dimension(300, 40));
        razonpro.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel Nitpro = new JLabel("NIT");
        Nitpro.setAlignmentX(Component.CENTER_ALIGNMENT);

        nitpro = new JTextField();
        nitpro.setMaximumSize(new Dimension(300, 40));
        nitpro.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel Direccionpro = new JLabel("Direccion de la residencia");
        Direccionpro.setAlignmentX(Component.CENTER_ALIGNMENT);

        direccionpro = new JTextField();
        direccionpro.setMaximumSize(new Dimension(300, 40));
        direccionpro.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel Telefonopro = new JLabel("Numero de telefono");
        Telefonopro.setAlignmentX(Component.CENTER_ALIGNMENT);

        telefonopro = new JTextField();
        telefonopro.setMaximumSize(new Dimension(300, 25));
        telefonopro.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel Correopro = new JLabel("Correo Electronico");
        Correopro.setAlignmentX(Component.CENTER_ALIGNMENT);

        correopro1 = new JTextField();
        correopro1.setMaximumSize(new Dimension(300, 25));
        correopro1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel butones = new JPanel();
        butones.setBorder(new EmptyBorder(20, 0, 0, 0));
        butones.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btncalcelarpro = new JButton(Evento.CANCELAR);
        btncalcelarpro.addActionListener(e);
        btncalcelarpro.setActionCommand(Evento.CANCELAR);

        JButton btnActualizarpro = new JButton(Evento.ACTUALIZARPRO1);
        btnActualizarpro.addActionListener(e);
        btnActualizarpro.setActionCommand(Evento.ACTUALIZARPRO1);

        JButton btndesactivarpro = new JButton(Evento.DESACTIVARPRO);
        btndesactivarpro.addActionListener(e);
        btndesactivarpro.setActionCommand(Evento.DESACTIVARPRO);

        JButton btnActivarpro = new JButton(Evento.ACTIVARPRO);
        btnActivarpro.addActionListener(e);
        btnActivarpro.setActionCommand(Evento.ACTIVARPRO);

        informacion.add(Razonpro);
        informacion.add(Box.createVerticalStrut(5));
        informacion.add(razonpro);
        informacion.add(Box.createVerticalStrut(10));

        informacion.add(Nitpro);
        informacion.add(Box.createVerticalStrut(5));
        informacion.add(nitpro);
        informacion.add(Box.createVerticalStrut(10));

        informacion.add(Direccionpro);
        informacion.add(Box.createVerticalStrut(5));
        informacion.add(direccionpro);
        informacion.add(Box.createVerticalStrut(10));

        informacion.add(Telefonopro);
        informacion.add(Box.createVerticalStrut(5));
        informacion.add(telefonopro);
        informacion.add(Box.createVerticalStrut(10));

        informacion.add(Correopro);
        informacion.add(Box.createVerticalStrut(5));
        informacion.add(correopro1);
        informacion.add(Box.createVerticalStrut(10));

        butones.add(btncalcelarpro);
        butones.add(btnActualizarpro);
        butones.add(btndesactivarpro);
        butones.add(btnActivarpro);

        contenedor.add(informacion);
        contenedor.add(butones);

        add(contenedor);
    }

    public void setProveedores(java.util.List<Proveedor> proveedores) {
        this.proveedores = new ArrayList<>(proveedores);
        comboProveedores.removeAllItems();
        for (Proveedor proveedor : proveedores) {
            comboProveedores.addItem(proveedor.getCodigo() + " - " + proveedor.getNombre());
        }
        cargarProveedorSeleccionado();
    }

    public void cargarProveedorSeleccionado() {
        String codigo = getProveedorCodigoSeleccionado();
        if (codigo == null || codigo.isEmpty() || proveedores == null) {
            razonpro.setText("");
            nitpro.setText("");
            direccionpro.setText("");
            telefonopro.setText("");
            correopro1.setText("");
            return;
        }
        for (Proveedor proveedor : proveedores) {
            if (codigo.equals(proveedor.getCodigo())) {
                razonpro.setText(proveedor.getNombre());
                nitpro.setText(proveedor.getNit());
                direccionpro.setText(proveedor.getDireccion());
                telefonopro.setText(proveedor.getTelefono());
                correopro1.setText(proveedor.getCorreo());
                return;
            }
        }
    }

    public String getProveedorCodigoSeleccionado() {
        Object seleccionado = comboProveedores.getSelectedItem();
        if (seleccionado == null) {
            return "";
        }
        String texto = seleccionado.toString();
        int separador = texto.indexOf(" - ");
        return separador >= 0 ? texto.substring(0, separador) : texto;
    }

    public String getRazon() {
        return razonpro.getText();
    }

    public String getNit() {
        return nitpro.getText();
    }

    public String getDireccion() {
        return direccionpro.getText();
    }

    public String getTelefono() {
        return telefonopro.getText();
    }

    public String getCorreo() {
        return correopro1.getText();
    }

    public void refreshProveedorSeleccionado() {
        cargarProveedorSeleccionado();
    }
}