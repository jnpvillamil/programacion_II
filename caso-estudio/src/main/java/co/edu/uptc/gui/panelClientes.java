package co.edu.uptc.gui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.negocio.dto.clienteDto;

public class panelClientes extends JPanel {

    public JButton bRegistrar;
    public JButton bModificar;
    public JButton bInactivar;
    public JButton bBuscar;
    public JButton bVolver;
    public JButton bLimpiar;

    private JLabel lNombre;
    private JLabel lTelefono;
    private JLabel lDireccion;

    public JTextField tNombre;
    public JTextField tTelefono;
    public JTextField tDireccion;

    protected DefaultTableModel modeloTabla;
    protected JTable tablaClientes;

    private int codigoSeleccionado = -1;
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
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel Campos = new JPanel(new GridLayout(3, 2, 10, 8));
        Campos.setBorder(BorderFactory.createTitledBorder("Datos del cliente"));

        bRegistrar = new JButton(Eventos.cREGISTRAR);
        bModificar = new JButton(Eventos.cMODIFICAR);
        bInactivar = new JButton(Eventos.cINACTIVAR);
        bBuscar    = new JButton(Eventos.cBUSCAR);
        bVolver    = new JButton(Eventos.VOLVER);
        bLimpiar   = new JButton(Eventos.cLIMPIAR);

        lNombre    = new JLabel("NOMBRE:");
        lTelefono  = new JLabel("TELEFONO:");
        lDireccion = new JLabel("DIRECCION:");

        tNombre    = new JTextField(10);
        tTelefono  = new JTextField(10);
        tDireccion = new JTextField(10);

        Campos.add(lNombre);    Campos.add(tNombre);
        Campos.add(lTelefono);  Campos.add(tTelefono);
        Campos.add(lDireccion); Campos.add(tDireccion);

        modeloTabla = new DefaultTableModel(
            new String[]{"Codigo", "Nombre", "Telefono", "Direccion"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaClientes.setRowHeight(20);
        tablaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cargarSeleccionEnCampos();
        });

        JScrollPane scroll = new JScrollPane(tablaClientes);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de clientes"));
        scroll.setPreferredSize(new Dimension(500, 150));

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
        int fila = tablaClientes.getSelectedRow();
        if (fila < 0) return;
        codigoSeleccionado = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
        tNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
        tTelefono.setText(modeloTabla.getValueAt(fila, 2).toString());
        tDireccion.setText(modeloTabla.getValueAt(fila, 3).toString());
    }

    public void poblarTabla(List<clienteDto> lista) {
        modeloTabla.setRowCount(0);
        for (clienteDto c : lista) {
            modeloTabla.addRow(new Object[]{
                c.getCodigoCliente(), c.getNombre(), c.getTelefono(), c.getDireccion()
            });
        }
    }

    public void limpiarCampos() {
        codigoSeleccionado = -1;
        tNombre.setText("");
        tTelefono.setText("");
        tDireccion.setText("");
        tablaClientes.clearSelection();
    }

    public clienteDto getDatosCliente() {
        String nombre = tNombre.getText();
        if (nombre == null || nombre.isBlank()) {
            JOptionPane.showMessageDialog(this, "El nombre es requerido");
            return null;
        }
        clienteDto cliente = new clienteDto();
        cliente.setNombre(nombre);
        try {
            cliente.setTelefono(Long.parseLong(tTelefono.getText()));
        } catch (NumberFormatException e) {
            cliente.setTelefono(0L);
        }
        cliente.setDireccion(tDireccion.getText());
        return cliente;
    }

    public clienteDto getDatosClienteModificar() {
        if (codigoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla para modificar");
            return null;
        }
        String nombre = tNombre.getText();
        if (nombre == null || nombre.isBlank()) {
            JOptionPane.showMessageDialog(this, "El nombre es requerido");
            return null;
        }
        clienteDto cliente = new clienteDto(codigoSeleccionado);
        cliente.setNombre(nombre);
        try {
            cliente.setTelefono(Long.parseLong(tTelefono.getText()));
        } catch (NumberFormatException e) {
            cliente.setTelefono(0L);
        }
        cliente.setDireccion(tDireccion.getText());
        return cliente;
    }

    public int getCodigoCliente() {
        if (codigoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla");
            return -1;
        }
        return codigoSeleccionado;
    }
}
