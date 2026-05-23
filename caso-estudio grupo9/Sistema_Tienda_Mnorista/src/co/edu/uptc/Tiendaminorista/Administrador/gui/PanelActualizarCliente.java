package co.edu.uptc.Tiendaminorista.Administrador.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import co.edu.uptc.Tiendaminorista.Enum.TipoDcoumenEnum;
import co.edu.uptc.Tiendaminorista.Gui.Evento;
import co.edu.uptc.Tiendaminorista.modelo.Cliente;

public class PanelActualizarCliente extends JPanel {

    private JComboBox<String> comboClientes;
    private java.util.List<Cliente> clientes;
    private JTextField nombre;
    private JComboBox<String> tipoDocumento;
    private JTextField numeroDocumento;
    private JTextField direccion;
    private JTextField telefono;
    private JComboBox<String> tipoCliente;

    public PanelActualizarCliente(Evento e) {

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
        comboClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                cargarClienteSeleccionado();
            }
        });

        informacion.add(new JLabel("Seleccionar cliente:"));
        informacion.add(Box.createVerticalStrut(5));
        informacion.add(comboClientes);
        informacion.add(Box.createVerticalStrut(10));

        JLabel Nombrecli = new JLabel("Nombre del cliente");
        Nombrecli.setAlignmentX(Component.LEFT_ALIGNMENT);

        nombre = new JTextField(); 
        nombre.setMaximumSize(new Dimension(300, 40));
        nombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel TipoDoc = new JLabel("Tipo de documento");
        TipoDoc.setAlignmentX(Component.LEFT_ALIGNMENT);

        tipoDocumento = new JComboBox<>(new String[]{"CC", "NIT", "CE", "PA"});
        tipoDocumento.setMaximumSize(new Dimension(150, 40));
        tipoDocumento.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel NumeroDoc = new JLabel("Número de documento");
        NumeroDoc.setAlignmentX(Component.LEFT_ALIGNMENT);

        numeroDocumento = new JTextField();
        numeroDocumento.setMaximumSize(new Dimension(300, 40));
        numeroDocumento.setAlignmentX(Component.LEFT_ALIGNMENT);

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
        
        JButton btnActivar = new JButton(Evento.ACTIVARCLI);
        btnActivar.addActionListener(e);
        btnActivar.setActionCommand(Evento.ACTIVARCLI);

        

        informacion.add(Nombrecli);
        informacion.add(Box.createVerticalStrut(5));
        informacion.add(nombre);
        informacion.add(Box.createVerticalStrut(10));

        informacion.add(TipoDoc);
        informacion.add(Box.createVerticalStrut(5));
        informacion.add(tipoDocumento);
        informacion.add(Box.createVerticalStrut(10));

        informacion.add(NumeroDoc);
        informacion.add(Box.createVerticalStrut(5));
        informacion.add(numeroDocumento);
        informacion.add(Box.createVerticalStrut(10));

        informacion.add(Direccion);
        informacion.add(Box.createVerticalStrut(5));
        informacion.add(direccion);
        informacion.add(Box.createVerticalStrut(10));

        informacion.add(Telefono);
        informacion.add(Box.createVerticalStrut(5));
        informacion.add(telefono);
        informacion.add(Box.createVerticalStrut(10));

        JLabel TipoCliente = new JLabel("Tipo de cliente");
        TipoCliente.setAlignmentX(Component.LEFT_ALIGNMENT);

        tipoCliente = new JComboBox<>(new String[]{"Minorista", "Mayorista"});
        tipoCliente.setMaximumSize(new Dimension(150, 40));
        tipoCliente.setAlignmentX(Component.LEFT_ALIGNMENT);

        informacion.add(TipoCliente);
        informacion.add(Box.createVerticalStrut(5));
        informacion.add(tipoCliente);
        informacion.add(Box.createVerticalStrut(10));

        butones.add(btncalcelar1);
        butones.add(btnActualizarcli);
        butones.add(btndesactivarcli);
        butones.add(btnActivar);
        add(informacion);
        add(butones);
    }

    public void setClientes(java.util.List<Cliente> clientes) {
        this.clientes = new ArrayList<>(clientes);
        comboClientes.removeAllItems();
        for (Cliente cliente : clientes) {
            comboClientes.addItem(cliente.getCodigo() + " - " + cliente.getNombre());
        }
        cargarClienteSeleccionado();
    }

    public void cargarClienteSeleccionado() {
        String codigo = getClienteCodigoSeleccionado();
        if (codigo == null || codigo.isEmpty() || clientes == null) {
            nombre.setText("");
            tipoDocumento.setSelectedItem("CC");
            numeroDocumento.setText("");
            direccion.setText("");
            telefono.setText("");
            tipoCliente.setSelectedItem("Minorista");
            return;
        }
        for (Cliente cliente : clientes) {
            if (codigo.equals(cliente.getCodigo())) {
                nombre.setText(cliente.getNombre());
                tipoDocumento.setSelectedItem(cliente.getTipodoc() != null ? cliente.getTipodoc().name() : "CC");
                numeroDocumento.setText(cliente.getNumeroIdentificacion());
                direccion.setText(cliente.getDireccion());
                telefono.setText(cliente.getTelefono());
                tipoCliente.setSelectedItem(cliente.getTipoCliente() != null ? cliente.getTipoCliente() : "Minorista");
                return;
            }
        }
    }

    public String getClienteCodigoSeleccionado() {
        Object seleccionado = comboClientes.getSelectedItem();
        if (seleccionado == null) {
            return "";
        }
        String texto = seleccionado.toString();
        int separador = texto.indexOf(" - ");
        return separador >= 0 ? texto.substring(0, separador) : texto;
    }

    public String getNombre() {
        return nombre.getText();
    }

    public String getTipoDoc() {
        return tipoDocumento.getSelectedItem().toString();
    }

    public String getNumeroDoc() {
        return numeroDocumento.getText();
    }

    public String getDireccion() {
        return direccion.getText();
    }

    public String getTelefono() {
        return telefono.getText();
    }

    public String getTipoCliente() {
        return tipoCliente.getSelectedItem().toString();
    }

    public void refreshClienteSeleccionado() {
        cargarClienteSeleccionado();
    }
}
