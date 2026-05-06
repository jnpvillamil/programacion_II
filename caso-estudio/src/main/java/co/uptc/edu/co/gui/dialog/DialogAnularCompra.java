package co.uptc.edu.co.gui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

public class DialogAnularCompra extends JDialog {

    private JTextField campoNumeroFactura;
    private JTextField campoFecha;
    private JTextField campoProveedor;
    private JTextField campoTotal;
    private JTextArea areaMotivo;

    private JButton botonConfirmar;
    private JButton botonCancelar;

    private boolean compraAnulada;

    public DialogAnularCompra(Frame propietario) {
        super(propietario, "Anular Compra", true);
        inicializarComponentes();
        configurarDialogo();
        agregarComponentes();
        inicializarEventos();
    }

    private void inicializarComponentes() {
        campoNumeroFactura = new JTextField(15);
        campoFecha = new JTextField(15);
        campoProveedor = new JTextField(25);
        campoTotal = new JTextField(15);

        campoNumeroFactura.setEditable(false);
        campoFecha.setEditable(false);
        campoProveedor.setEditable(false);
        campoTotal.setEditable(false);

        areaMotivo = new JTextArea(5, 30);
        areaMotivo.setLineWrap(true);
        areaMotivo.setWrapStyleWord(true);

        botonConfirmar = new JButton("Confirmar anulación");
        botonCancelar = new JButton("Cancelar");

        compraAnulada = false;
    }

    private void configurarDialogo() {
        setSize(550, 350);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void agregarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panelPrincipal.add(crearPanelDatos(), BorderLayout.NORTH);
        panelPrincipal.add(crearPanelMotivo(), BorderLayout.CENTER);
        panelPrincipal.add(crearPanelBotones(), BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private JPanel crearPanelDatos() {
        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBorder(BorderFactory.createTitledBorder("Compra seleccionada"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDatos.add(new JLabel("N° Factura:"), gbc);

        gbc.gridx = 1;
        panelDatos.add(campoNumeroFactura, gbc);

        gbc.gridx = 2;
        panelDatos.add(new JLabel("Fecha:"), gbc);

        gbc.gridx = 3;
        panelDatos.add(campoFecha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelDatos.add(new JLabel("Proveedor:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        panelDatos.add(campoProveedor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panelDatos.add(new JLabel("Total:"), gbc);

        gbc.gridx = 1;
        panelDatos.add(campoTotal, gbc);

        return panelDatos;
    }

    private JPanel crearPanelMotivo() {
        JPanel panelMotivo = new JPanel(new BorderLayout(5, 5));
        panelMotivo.setBorder(BorderFactory.createTitledBorder("Motivo de anulación"));

        JScrollPane scrollMotivo = new JScrollPane(areaMotivo);
        panelMotivo.add(scrollMotivo, BorderLayout.CENTER);

        return panelMotivo;
    }

    private JPanel crearPanelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.add(botonConfirmar);
        panelBotones.add(botonCancelar);
        return panelBotones;
    }

    private void inicializarEventos() {
        botonConfirmar.addActionListener(e -> confirmarAnulacion());
        botonCancelar.addActionListener(e -> dispose());
    }

    private void confirmarAnulacion() {
        String motivo = areaMotivo.getText().trim();

        if (motivo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar un motivo de anulación.",
                    "Campo obligatorio",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de anular esta compra?",
                "Confirmar anulación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            compraAnulada = true;
            dispose();
        }
    }

    public void cargarCompra(String numeroFactura, String fecha, String proveedor, String total) {
        campoNumeroFactura.setText(numeroFactura);
        campoFecha.setText(fecha);
        campoProveedor.setText(proveedor);
        campoTotal.setText(total);
    }

    public String getMotivoAnulacion() {
        return areaMotivo.getText().trim();
    }

    public boolean isCompraAnulada() {
        return compraAnulada;
    }
}