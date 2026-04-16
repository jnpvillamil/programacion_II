package co.edu.uptc.gui;

import co.edu.uptc.negocio.Cliente;
import co.edu.uptc.negocio.SistemaGestion;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelClientes extends JPanel {
    private SistemaGestion sis;
    private DefaultTableModel modelo;

    public PanelClientes(SistemaGestion sis) {
        this.sis = sis;
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(244, 246, 249));

        JLabel lblTitulo = new JLabel("Gestión de Clientes");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.setOpaque(false);

        JPanel form = new JPanel(new GridLayout(4, 4, 10, 10));
        form.setOpaque(false);
        
        JTextField txtCodigo = new JTextField(); JTextField txtNombre = new JTextField();
        JTextField txtTipoId = new JTextField(); JTextField txtNumId = new JTextField();
        JTextField txtDir = new JTextField(); JTextField txtTel = new JTextField();
        JComboBox<String> cbTipoCliente = new JComboBox<>(new String[]{"Minorista", "Mayorista"});

        form.add(new JLabel("Código:")); form.add(txtCodigo);
        form.add(new JLabel("Nombre Completo:")); form.add(txtNombre);
        form.add(new JLabel("Tipo Identificación:")); form.add(txtTipoId);
        form.add(new JLabel("Número Identificación:")); form.add(txtNumId);
        form.add(new JLabel("Dirección:")); form.add(txtDir);
        form.add(new JLabel("Teléfono:")); form.add(txtTel);
        form.add(new JLabel("Tipo Cliente:")); form.add(cbTipoCliente);

        panelCentro.add(form, BorderLayout.CENTER);

        JButton btnGuardar = new JButton("Guardar Cliente");
        btnGuardar.setBackground(new Color(46, 204, 113));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("SansSerif", Font.BOLD, 12));
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setOpaque(false);
        panelBotones.add(btnGuardar);
        panelCentro.add(panelBotones, BorderLayout.SOUTH);

        add(panelCentro, BorderLayout.CENTER);

        modelo = new DefaultTableModel(new String[]{"Código", "Nombre", "Identificación", "Teléfono", "Tipo"}, 0);
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.getTableHeader().setBackground(new Color(44, 62, 80));
        tabla.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(0, 250));
        add(scroll, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> {
            try {
                Cliente c = new Cliente(txtCodigo.getText(), txtNombre.getText(), txtTipoId.getText(), 
                                        txtNumId.getText(), cbTipoCliente.getSelectedItem().toString());
                // sis.getGestorCliente().registrarCliente(c); // Llamada al negocio
                JOptionPane.showMessageDialog(this, "Cliente guardado.");
                // cargarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}