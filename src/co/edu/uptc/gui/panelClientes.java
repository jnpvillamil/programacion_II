package co.edu.uptc.gui;

import java.awt.*;
import javax.swing.*;
import co.edu.uptc.negocio.clienteDto;

public class panelClientes extends JPanel {

    public JButton bRegistrar;
    public JButton bModificar;
    public JButton bInactivar;
    public JButton bBuscar;
    public JButton bVolver;
    private JLabel lCodigo;
    private JLabel lNombre;
    private JLabel lTelefono;
    private JLabel lDireccion;
    public JTextField tCodigo;
    public JTextField tNombre;
    public JTextField tTelefono;
    public JTextField tDireccion;
    private Eventos evento;

    public panelClientes(Eventos evento) {
        this.evento = evento;
        construirPanel();
    }

    public panelClientes() {
        this.evento = new Eventos();
        construirPanel();
    }

    private void construirPanel() {
        setLayout(new BorderLayout());
        JPanel Campos = new JPanel(new GridLayout(4, 2, 10, 10));
        Campos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        bRegistrar = new JButton(Eventos.cREGISTRAR);
        bModificar = new JButton(Eventos.cMODIFICAR);
        bInactivar = new JButton(Eventos.cINACTIVAR);
        bBuscar    = new JButton(Eventos.cBUSCAR);
        bVolver    = new JButton(Eventos.VOLVER);

        lCodigo    = new JLabel("CODIGO:");
        lNombre    = new JLabel("NOMBRE:");
        lTelefono  = new JLabel("TELEFONO:");
        lDireccion = new JLabel("DIRECCION:");

        tCodigo    = new JTextField(10);
        tNombre    = new JTextField(10);
        tTelefono  = new JTextField(10);
        tDireccion = new JTextField(10);

        Campos.add(lCodigo);    Campos.add(tCodigo);
        Campos.add(lNombre);    Campos.add(tNombre);
        Campos.add(lTelefono);  Campos.add(tTelefono);
        Campos.add(lDireccion); Campos.add(tDireccion);

        JPanel botones = new JPanel(new GridLayout(1, 4, 10, 10));
        botones.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 20));
        botones.add(bRegistrar);
        botones.add(bModificar);
        botones.add(bInactivar);
        botones.add(bBuscar);

        JPanel panelVolver = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelVolver.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        panelVolver.add(bVolver);

        JPanel sur = new JPanel(new BorderLayout());
        sur.add(botones,     BorderLayout.NORTH);
        sur.add(panelVolver, BorderLayout.SOUTH);

        add(Campos, BorderLayout.CENTER);
        add(sur,    BorderLayout.SOUTH);

        bRegistrar.addActionListener(evento);
        bModificar.addActionListener(evento);
        bInactivar.addActionListener(evento);
        bBuscar.addActionListener(evento);
        bVolver.addActionListener(evento);
    }

    public clienteDto getDatosCliente() {
        String codigo = tCodigo.getText();
        String nombre = tNombre.getText();
        if (codigo == null || codigo.isBlank()) {
            JOptionPane.showMessageDialog(this, "El codigo es requerido");
            return null;
        }
        if (nombre == null || nombre.isBlank()) {
            JOptionPane.showMessageDialog(this, "El nombre es requerido");
            return null;
        }
        clienteDto cliente = new clienteDto();
        cliente.setCodigo(codigo);
        cliente.setNombre(nombre);
        cliente.setTelefono(tTelefono.getText());
        cliente.setDireccion(tDireccion.getText());
        return cliente;
    }

    public String getCodigoCliente() {
        String codigo = tCodigo.getText();
        if (codigo == null || codigo.isBlank()) {
            JOptionPane.showMessageDialog(this, "El codigo es requerido");
            return null;
        }
        return codigo;
    }
}