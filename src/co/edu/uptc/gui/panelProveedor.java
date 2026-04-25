package co.edu.uptc.gui;

import java.awt.*;
import javax.swing.*;
import co.edu.uptc.negocio.proveedorDto;

public class panelProveedor extends JPanel {

    public JButton bRegistrar;
    public JButton bModificar;
    public JButton bInactivar;
    public JButton bBuscar;
    public JButton bVolver;
    private JLabel lCodigoProveedor;
    private JLabel lRazonSocialProveedor;
    private JLabel lNitProveedor;
    private JLabel lDireccionProveedor;
    private JLabel lTelefonoProveedor;
    private JLabel lCorreoProveedor;
    public JTextField tCodigoProveedor;
    public JTextField tRazonSocialProveedor;
    public JTextField tNitProveedor;
    public JTextField tDireccionProveedor;
    public JTextField tTelefonoProveedor;
    public JTextField tCorreoProveedor;
    private Eventos evento;

    public panelProveedor(Eventos evento) {
        this.evento = evento;
        construirPanel();
    }

    public panelProveedor() {
        this.evento = new Eventos();
        construirPanel();
    }

    private void construirPanel() {
        setLayout(new BorderLayout());
        JPanel Campos = new JPanel(new GridLayout(6, 2, 10, 10));
        Campos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        bRegistrar = new JButton(Eventos.oREGISTRAR);
        bModificar = new JButton(Eventos.oMODIFICAR);
        bInactivar = new JButton(Eventos.oINACTIVAR);
        bBuscar    = new JButton(Eventos.oBUSCAR);
        bVolver    = new JButton(Eventos.VOLVER);

        lCodigoProveedor      = new JLabel("CODIGO:");
        lRazonSocialProveedor = new JLabel("RAZON SOCIAL:");
        lNitProveedor         = new JLabel("NIT:");
        lDireccionProveedor   = new JLabel("DIRECCION:");
        lTelefonoProveedor    = new JLabel("TELEFONO:");
        lCorreoProveedor      = new JLabel("CORREO ELECTRONICO:");

        tCodigoProveedor      = new JTextField(10);
        tRazonSocialProveedor = new JTextField(10);
        tNitProveedor         = new JTextField(10);
        tDireccionProveedor   = new JTextField(10);
        tTelefonoProveedor    = new JTextField(10);
        tCorreoProveedor      = new JTextField(10);

        Campos.add(lCodigoProveedor);      Campos.add(tCodigoProveedor);
        Campos.add(lRazonSocialProveedor); Campos.add(tRazonSocialProveedor);
        Campos.add(lNitProveedor);         Campos.add(tNitProveedor);
        Campos.add(lDireccionProveedor);   Campos.add(tDireccionProveedor);
        Campos.add(lTelefonoProveedor);    Campos.add(tTelefonoProveedor);
        Campos.add(lCorreoProveedor);      Campos.add(tCorreoProveedor);

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

    public proveedorDto getDatosProveedor() {
        String codigo      = tCodigoProveedor.getText();
        String razonSocial = tRazonSocialProveedor.getText();
        if (codigo == null || codigo.isBlank()) {
            JOptionPane.showMessageDialog(this, "El codigo es requerido");
            return null;
        }
        if (razonSocial == null || razonSocial.isBlank()) {
            JOptionPane.showMessageDialog(this, "La razon social es requerida");
            return null;
        }
        proveedorDto proveedor = new proveedorDto();
        proveedor.setCodigo(codigo);
        proveedor.setRazonSocial(razonSocial);
        proveedor.setNit(tNitProveedor.getText());
        proveedor.setDireccion(tDireccionProveedor.getText());
        proveedor.setTelefono(tTelefonoProveedor.getText());
        proveedor.setCorreo(tCorreoProveedor.getText());
        return proveedor;
    }

    public String getCodigoProveedor() {
        String codigo = tCodigoProveedor.getText();
        if (codigo == null || codigo.isBlank()) {
            JOptionPane.showMessageDialog(this, "El codigo es requerido");
            return null;
        }
        return codigo;
    }
}