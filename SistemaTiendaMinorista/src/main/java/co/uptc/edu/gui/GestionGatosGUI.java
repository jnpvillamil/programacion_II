package co.uptc.edu.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import co.uptc.edu.modelo.Gato;
import co.uptc.edu.persistencia.GatoDAO;
import co.uptc.edu.interfaces.IGatoDAO;
import co.uptc.edu.negocio.GestionGatos;

public class GestionGatosGUI extends JFrame {

    private JTextField txtNombre;
    private JTextField txtEdad;

    private JTable tabla;
    private DefaultTableModel modelo;

    private GestionGatos gestion;
    private IGatoDAO gatoDAO;

    public GestionGatosGUI(GestionGatos gestion) {

        this.gestion = gestion;
        this.gatoDAO = new GatoDAO();

        setTitle("Gestión de Gatos");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearTabla(), BorderLayout.CENTER);

        cargarGatosTabla();
    }

    private JPanel crearPanelSuperior() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Datos del Gato"));

        JPanel formulario = new JPanel(new GridLayout(2,2,10,10));

        txtNombre = new JTextField();
        txtEdad = new JTextField();

        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);

        formulario.add(new JLabel("Edad:"));
        formulario.add(txtEdad);

        panel.add(formulario, BorderLayout.CENTER);

        JPanel botones = new JPanel();

        JButton btnNuevo = new JButton("Nuevo");
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnRefrescar = new JButton("Refrescar");

        botones.add(btnNuevo);
        botones.add(btnRegistrar);
        botones.add(btnRefrescar);

        btnRegistrar.addActionListener(e -> {

            try {

                String nombre = txtNombre.getText();
                int edad = Integer.parseInt(txtEdad.getText());

                if (gestion.registrarGato(nombre, edad)) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Gato registrado correctamente"
                    );

                    cargarGatosTabla();
                    limpiar();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "No se pudo registrar"
                    );
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Datos inválidos"
                );
            }
        });

        btnNuevo.addActionListener(e -> limpiar());

        btnRefrescar.addActionListener(e -> cargarGatosTabla());

        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }

    private JScrollPane crearTabla() {

        String[] columnas = {
                "ID",
                "Nombre",
                "Edad"
        };

        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);

        tabla.getSelectionModel().addListSelectionListener(e -> {

            int fila = tabla.getSelectedRow();

            if (fila >= 0) {

                txtNombre.setText(
                        modelo.getValueAt(fila, 1).toString()
                );

                txtEdad.setText(
                        modelo.getValueAt(fila, 2).toString()
                );
            }
        });

        return new JScrollPane(tabla);
    }

    private void cargarGatosTabla() {

        modelo.setRowCount(0);

        for (Gato g : gatoDAO.obtenerGatos()) {

            modelo.addRow(new Object[]{

                    g.getId(),
                    g.getNombre(),
                    g.getEdad()
            });
        }
    }

    private void limpiar() {

        txtNombre.setText("");
        txtEdad.setText("");
    }

    public static void main(String[] args) {

        IGatoDAO dao = new GatoDAO();

        GestionGatos gestion =
                new GestionGatos(dao);

        new GestionGatosGUI(
                gestion
        ).setVisible(true);
    }
}