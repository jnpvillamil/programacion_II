package co.uptc.edu.co.gui.dialog;

import javax.swing.JDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import co.uptc.edu.co.gui.Evento;
import co.uptc.edu.co.modelo.enums.TipoMovimientoInventarioEnum;

public class DialogMovimientoInventario extends JDialog {

    private JTextField campoCodigoProducto;
    private JComboBox<TipoMovimientoInventarioEnum> comboTipoMovimiento;
    private JTextField campoCantidad;
    private JTextField campoFecha;
    private JTextArea campoDescripcionMovimiento;

    private JButton botonOk;
    private JButton botonCancel;

    public DialogMovimientoInventario(Frame propietario) {
        this(propietario, null);
    }

    public DialogMovimientoInventario(Frame propietario, Evento evento) {
        super(propietario, "Movimiento de Inventario", true);
        inicializarComponentes();
        configurarDialogo();
        agregarComponentes();
        inicializarEventos(evento);
    }

    private void inicializarComponentes() {
        campoCodigoProducto = new JTextField(25);

        comboTipoMovimiento = new JComboBox<>(TipoMovimientoInventarioEnum.values());

        campoCantidad = new JTextField(25);
        campoFecha = new JTextField(25);
        campoDescripcionMovimiento = new JTextArea(4, 25);
        campoDescripcionMovimiento.setLineWrap(true);
        campoDescripcionMovimiento.setWrapStyleWord(true);

        botonOk = new JButton("OK");
        botonCancel = new JButton("Cancelar");
    }

    private void configurarDialogo() {
        setSize(360, 400);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void agregarComponentes() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 0);
        panelPrincipal.add(new JLabel("Código de producto:"), gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelPrincipal.add(campoCodigoProducto, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(8, 0, 5, 0);
        panelPrincipal.add(new JLabel("Tipo de movimiento:"), gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 5, 0);
        panelPrincipal.add(comboTipoMovimiento, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(8, 0, 5, 0);
        panelPrincipal.add(new JLabel("Cantidad:"), gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 5, 0);
        panelPrincipal.add(campoCantidad, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(8, 0, 5, 0);
        panelPrincipal.add(new JLabel("Fecha:"), gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 5, 0);
        panelPrincipal.add(campoFecha, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(8, 0, 5, 0);
        panelPrincipal.add(new JLabel("Descripción del movimiento:"), gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 0);
        panelPrincipal.add(new JScrollPane(campoDescripcionMovimiento), gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBotones.add(botonOk);
        panelBotones.add(botonCancel);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 0, 0, 0);
        panelPrincipal.add(panelBotones, gbc);

        add(panelPrincipal);
    }

    private void inicializarEventos(Evento evento) {
        botonCancel.addActionListener(e -> dispose());

        if (evento != null) {
            botonOk.setActionCommand(Evento.CMD_CONFIRMAR_MOVIMIENTO_INVENTARIO);
            botonOk.addActionListener(evento);
        } else {
            botonOk.addActionListener(e -> dispose());
        }
    }

    public void cargarProducto(String codigoProducto) {
        campoCodigoProducto.setText(codigoProducto);
        campoCodigoProducto.setEditable(false);
    }

    public String obtenerCodigoProducto() throws Exception {
        String codigo = campoCodigoProducto.getText().trim();

        if (codigo.isEmpty()) {
            throw new Exception("El código del producto es obligatorio.");
        }

        return codigo;
    }

    public String obtenerTipoMovimiento() {
        TipoMovimientoInventarioEnum tipoMovimiento =
                (TipoMovimientoInventarioEnum) comboTipoMovimiento.getSelectedItem();
        return tipoMovimiento.name();
    }

    public int obtenerCantidad() throws Exception {
        String textoCantidad = campoCantidad.getText().trim();

        if (textoCantidad.isEmpty()) {
            throw new Exception("La cantidad es obligatoria.");
        }

        try {
            int cantidad = Integer.parseInt(textoCantidad);

            if (cantidad <= 0) {
                throw new Exception("La cantidad debe ser mayor que cero.");
            }

            return cantidad;
        } catch (NumberFormatException e) {
            throw new Exception("La cantidad debe ser un número entero.");
        }
    }

    public String obtenerFecha() throws Exception {
        String fecha = campoFecha.getText().trim();

        if (fecha.isEmpty()) {
            throw new Exception("La fecha es obligatoria.");
        }

        return fecha;
    }

    public String obtenerDescripcion() throws Exception {
        String descripcion = campoDescripcionMovimiento.getText().trim();

        if (descripcion.isEmpty()) {
            throw new Exception("La descripción del movimiento es obligatoria.");
        }

        return descripcion;
    }
}