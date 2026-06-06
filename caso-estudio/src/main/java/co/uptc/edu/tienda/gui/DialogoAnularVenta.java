package co.uptc.edu.tienda.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogoAnularVenta extends JDialog {

    private JTextField txtFactura;
    private JTextField txtMotivo;
    private JButton btnAnular;
    private JButton btnCancelar;

    public DialogoAnularVenta(Evento evento) {

        setTitle("Anular Venta");
        setSize(400, 220);
        setModal(true);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // =====================================
        // CAMPOS
        // =====================================
        JPanel campos = new JPanel(new GridLayout(2, 2, 10, 10));
        campos.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        txtFactura = new JTextField();
        txtMotivo = new JTextField();

        campos.add(new JLabel("Número de factura:"));
        campos.add(txtFactura);
        campos.add(new JLabel("Motivo de anulación:"));
        campos.add(txtMotivo);

        add(campos, BorderLayout.CENTER);

        // =====================================
        // BOTONES
        // =====================================
        JPanel botones = new JPanel();

        btnAnular = new JButton("Confirmar Anulación");
        btnAnular.setActionCommand(Evento.ANULAR_VTA);
        btnAnular.addActionListener(evento);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setActionCommand(Evento.CANCELAR_ANULAR_VTA);
        btnCancelar.addActionListener(evento);

        botones.add(btnAnular);
        botones.add(btnCancelar);

        add(botones, BorderLayout.SOUTH);
    }

    // =====================================
    // CAPTURAR DATOS
    // =====================================
    public String getFactura() {
        return txtFactura.getText().trim();
    }

    public String getMotivo() {
        return txtMotivo.getText().trim();
    }
    
    public void setFactura(String factura) {
        txtFactura.setText(factura);
    }
}
