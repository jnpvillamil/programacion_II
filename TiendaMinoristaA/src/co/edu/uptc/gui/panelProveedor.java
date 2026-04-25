package co.edu.uptc.gui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.negocio.dto.proveedorDto;

public class panelProveedor extends JPanel {

    public JButton bRegistrar;
    public JButton bModificar;
    public JButton bInactivar;
    public JButton bBuscar;
    public JButton bVolver;
    public JButton bLimpiar;

    private JLabel lRazonSocialProveedor;
    private JLabel lNitProveedor;
    private JLabel lDireccionProveedor;
    private JLabel lTelefonoProveedor;
    private JLabel lCorreoProveedor;

    public JTextField tRazonSocialProveedor;
    public JTextField tNitProveedor;
    public JTextField tDireccionProveedor;
    public JTextField tTelefonoProveedor;
    public JTextField tCorreoProveedor;

    protected DefaultTableModel modeloTabla;
    protected JTable tablaProveedores;

    // Código del proveedor seleccionado para modificar/eliminar
    private int codigoSeleccionado = -1;

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
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel Campos = new JPanel(new GridLayout(5, 2, 10, 8));
        Campos.setBorder(BorderFactory.createTitledBorder("Datos del proveedor"));

        bRegistrar = new JButton(Eventos.oREGISTRAR);
        bModificar = new JButton(Eventos.oMODIFICAR);
        bInactivar = new JButton(Eventos.oINACTIVAR);
        bBuscar    = new JButton(Eventos.oBUSCAR);
        bVolver    = new JButton(Eventos.VOLVER);
        bLimpiar   = new JButton(Eventos.oLIMPIAR);

        lRazonSocialProveedor = new JLabel("RAZON SOCIAL:");
        lNitProveedor         = new JLabel("NIT:");
        lDireccionProveedor   = new JLabel("DIRECCION:");
        lTelefonoProveedor    = new JLabel("TELEFONO:");
        lCorreoProveedor      = new JLabel("CORREO ELECTRONICO:");

        tRazonSocialProveedor = new JTextField(10);
        tNitProveedor         = new JTextField(10);
        tDireccionProveedor   = new JTextField(10);
        tTelefonoProveedor    = new JTextField(10);
        tCorreoProveedor      = new JTextField(10);

        Campos.add(lRazonSocialProveedor); Campos.add(tRazonSocialProveedor);
        Campos.add(lNitProveedor);         Campos.add(tNitProveedor);
        Campos.add(lDireccionProveedor);   Campos.add(tDireccionProveedor);
        Campos.add(lTelefonoProveedor);    Campos.add(tTelefonoProveedor);
        Campos.add(lCorreoProveedor);      Campos.add(tCorreoProveedor);

        modeloTabla = new DefaultTableModel(
            new String[]{"Codigo", "Razon Social", "NIT", "Direccion", "Telefono", "Correo"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaProveedores = new JTable(modeloTabla);
        tablaProveedores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaProveedores.setRowHeight(20);
        tablaProveedores.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cargarSeleccionEnCampos();
        });

        JScrollPane scroll = new JScrollPane(tablaProveedores);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de proveedores"));
        scroll.setPreferredSize(new Dimension(600, 160));

        JPanel botones = new JPanel(new GridLayout(1, 5, 8, 8));
        botones.setBorder(BorderFactory.createEmptyBorder(8, 0, 4, 0));
        botones.add(bRegistrar);
        botones.add(bModificar);
        botones.add(bInactivar);
        botones.add(bBuscar);
        botones.add(bLimpiar);

        JPanel panelVolver = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelVolver.add(bVolver);

        JPanel sur = new JPanel(new BorderLayout());
        sur.add(botones,     BorderLayout.NORTH);
        sur.add(panelVolver, BorderLayout.SOUTH);

        add(Campos, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(sur,    BorderLayout.SOUTH);

        bRegistrar.addActionListener(evento);
        bModificar.addActionListener(evento);
        bInactivar.addActionListener(evento);
        bBuscar.addActionListener(evento);
        bVolver.addActionListener(evento);
        bLimpiar.addActionListener(evento);
    }

    private void cargarSeleccionEnCampos() {
        int fila = tablaProveedores.getSelectedRow();
        if (fila < 0) return;
        codigoSeleccionado = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
        tRazonSocialProveedor.setText(modeloTabla.getValueAt(fila, 1).toString());
        tNitProveedor.setText(modeloTabla.getValueAt(fila, 2).toString());
        tDireccionProveedor.setText(modeloTabla.getValueAt(fila, 3).toString());
        tTelefonoProveedor.setText(modeloTabla.getValueAt(fila, 4).toString());
        tCorreoProveedor.setText(modeloTabla.getValueAt(fila, 5).toString());
    }

    public void poblarTabla(List<proveedorDto> lista) {
        modeloTabla.setRowCount(0);
        for (proveedorDto p : lista) {
            modeloTabla.addRow(new Object[]{
                p.getCodigoProveedor(), p.getRazonSocial(), p.getNit(),
                p.getDireccion(), p.getTelefono(), p.getCorreo()
            });
        }
    }

    public void limpiarCampos() {
        codigoSeleccionado = -1;
        tRazonSocialProveedor.setText("");
        tNitProveedor.setText("");
        tDireccionProveedor.setText("");
        tTelefonoProveedor.setText("");
        tCorreoProveedor.setText("");
        tablaProveedores.clearSelection();
    }

    // Para registrar: crea nuevo proveedor (codigo autogenerado)
    public proveedorDto getDatosProveedor() {
        String razonSocial = tRazonSocialProveedor.getText();
        if (razonSocial == null || razonSocial.isBlank()) {
            JOptionPane.showMessageDialog(this, "La razon social es requerida");
            return null;
        }
        proveedorDto proveedor = new proveedorDto();
        proveedor.setRazonSocial(razonSocial);
        proveedor.setNit(tNitProveedor.getText());
        proveedor.setDireccion(tDireccionProveedor.getText());
        try {
            proveedor.setTelefono(Long.parseLong(tTelefonoProveedor.getText()));
        } catch (NumberFormatException e) {
            proveedor.setTelefono(0L);
        }
        proveedor.setCorreo(tCorreoProveedor.getText());
        return proveedor;
    }

    // Para modificar: usa el codigo seleccionado de la tabla
    public proveedorDto getDatosProveedorModificar() {
        if (codigoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor de la tabla para modificar");
            return null;
        }
        String razonSocial = tRazonSocialProveedor.getText();
        if (razonSocial == null || razonSocial.isBlank()) {
            JOptionPane.showMessageDialog(this, "La razon social es requerida");
            return null;
        }
        proveedorDto proveedor = new proveedorDto(codigoSeleccionado);
        proveedor.setRazonSocial(razonSocial);
        proveedor.setNit(tNitProveedor.getText());
        proveedor.setDireccion(tDireccionProveedor.getText());
        try {
            proveedor.setTelefono(Long.parseLong(tTelefonoProveedor.getText()));
        } catch (NumberFormatException e) {
            proveedor.setTelefono(0L);
        }
        proveedor.setCorreo(tCorreoProveedor.getText());
        return proveedor;
    }

    public int getCodigoProveedor() {
        if (codigoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor de la tabla");
            return -1;
        }
        return codigoSeleccionado;
    }
}
