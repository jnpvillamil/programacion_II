package co.edu.uptc.gui;

import co.edu.uptc.negocio.GestionProveedor;
import co.edu.uptc.negocio.Proveedor;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PanelProveedor extends JPanel {
    private JTextField tNit, tNom, tEmail;
    private JButton btnG;
    private GestionProveedor gProv;
    private Color azulCabecera = new Color(13, 38, 64);

    public PanelProveedor(GestionProveedor gProv, Evento ev) {
        this.gProv = gProv;
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(230, 230, 230));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(azulCabecera);
        JLabel lblTitulo = new JLabel(" GESTIÓN DE PROVEEDORES");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setBorder(new EmptyBorder(10, 10, 10, 10));
        pnlHeader.add(lblTitulo, BorderLayout.WEST);
        add(pnlHeader, BorderLayout.NORTH);

        JPanel pnlContenedorBlanco = new JPanel(new GridBagLayout());
        pnlContenedorBlanco.setBackground(Color.WHITE);
        pnlContenedorBlanco.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 25, 15, 25);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        pnlContenedorBlanco.add(new JLabel("NIT / Identificación:"), gbc);
        tNit = new JTextField(25);
        tNit.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 1;
        pnlContenedorBlanco.add(tNit, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        pnlContenedorBlanco.add(new JLabel("Nombre o Razón Social:"), gbc);
        tNom = new JTextField(25);
        tNom.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 1;
        pnlContenedorBlanco.add(tNom, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        pnlContenedorBlanco.add(new JLabel("Correo Electrónico:"), gbc);
        tEmail = new JTextField(25);
        tEmail.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 1;
        pnlContenedorBlanco.add(tEmail, gbc);

        gbc.weighty = 1.0;
        gbc.gridy = 3;
        pnlContenedorBlanco.add(new JLabel(" "), gbc);

        add(pnlContenedorBlanco, BorderLayout.CENTER);

        btnG = new JButton("GUARDAR PROVEEDOR");
        btnG.setBackground(azulCabecera);
        btnG.setForeground(Color.WHITE);
        btnG.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnG.setFocusPainted(false);
        btnG.setOpaque(true);
        btnG.setBorderPainted(false);
        btnG.setPreferredSize(new Dimension(0, 50));
        btnG.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnG.addActionListener(ev);
        
        add(btnG, BorderLayout.SOUTH);
    }

    public void guardarProveedor() {
        if(tNit.getText().isEmpty() || tNom.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete los campos obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Proveedor p = new Proveedor(tNit.getText(), tNom.getText(), "", "", tEmail.getText());
        gProv.registrarProveedor(p);
        
        JOptionPane.showMessageDialog(this, "Proveedor registrado exitosamente en el sistema.");
        limpiarCampos();
    }

    private void limpiarCampos() {
        tNit.setText("");
        tNom.setText("");
        tEmail.setText("");
        tNit.requestFocus();
    }

    public JButton getBtnGuardar() {
        return btnG;
    }
}