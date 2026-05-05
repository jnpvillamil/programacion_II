package co.edu.uptc.gui;

import co.edu.uptc.negocio.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelCliente extends JPanel {
    private JTextField tID, tNom, tTel;
    private JButton btnG, btnE;
    private DefaultTableModel modelo;
    private GestionCliente gCli;

    public PanelCliente(GestionCliente gCli, Evento ev) {
        this.gCli = gCli;
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(230, 230, 230));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel pnlForm = new JPanel(new BorderLayout());
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(13, 38, 64));
        JLabel lblT = new JLabel(" GESTIÓN DE CLIENTES");
        lblT.setForeground(Color.WHITE);
        lblT.setBorder(new EmptyBorder(8, 10, 8, 10));
        pnlHeader.add(lblT, BorderLayout.WEST);
        pnlForm.add(pnlHeader, BorderLayout.NORTH);

        JPanel campos = new JPanel(new GridLayout(1, 6, 15, 0));
        campos.setBackground(Color.WHITE);
        campos.setBorder(new EmptyBorder(15, 15, 15, 15));
        campos.add(new JLabel("Codigo Cliente:")); tID = new JTextField(); campos.add(tID);
        campos.add(new JLabel("Nombre:")); tNom = new JTextField(); campos.add(tNom);
        campos.add(new JLabel("Telefono:")); tTel = new JTextField(); campos.add(tTel);
        pnlForm.add(campos, BorderLayout.CENTER);

        JPanel pnlBotones = new JPanel();
        pnlBotones.setBackground(Color.WHITE);
        btnG = new JButton("Registrar"); btnG.addActionListener(ev);
        btnE = new JButton("Borrar"); btnE.addActionListener(ev);
        pnlBotones.add(btnG); pnlBotones.add(btnE);
        pnlForm.add(pnlBotones, BorderLayout.SOUTH);

        add(pnlForm, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Teléfono"}, 0);
        add(new JScrollPane(new JTable(modelo)), BorderLayout.CENTER);
    }

    public JButton getBtnGuardar() { return btnG; }
    public JButton getBtnEliminar() { return btnE; }

    public void guardarCliente() {
        gCli.registrarCliente(new Cliente(tID.getText(), tNom.getText(), "", tTel.getText(), "Minorista"));
        actualizar();
    }

    public void eliminarCliente() {
        gCli.eliminarCliente(tID.getText());
        actualizar();
    }

    private void actualizar() {
        modelo.setRowCount(0);
        for (Cliente c : gCli.getClientes()) modelo.addRow(new Object[]{c.getIdentificacion(), c.getNombre(), c.getTelefono()});
    }
}