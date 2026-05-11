package co.edu.uptc.gui;

import co.edu.uptc.utilidades.ConstructorComponentes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelCliente extends JPanel {
    public PanelCliente() {
        setLayout(new BorderLayout(20, 20));
        setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = ConstructorComponentes.crearEtiquetaNegrita("GESTIÓN DE CLIENTES");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // --- FORMULARIO ORGANIZADO ---
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 0.5;

        // Fila 1
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Identificación:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        panelForm.add(ConstructorComponentes.crearCampoTexto(), gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Nombre Completo:"), gbc);
        gbc.gridx = 3; gbc.gridy = 0;
        panelForm.add(ConstructorComponentes.crearCampoTexto(), gbc);

        // Fila 2
        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Teléfono:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        panelForm.add(ConstructorComponentes.crearCampoTexto(), gbc);

        gbc.gridx = 3; gbc.gridy = 1;
        JButton btnGuardar = ConstructorComponentes.crearBotonAccion("Guardar Cliente", ConstructorComponentes.COLOR_VERDE_GUARDAR);
        panelForm.add(btnGuardar, gbc);

        // Contenedor para evitar que el formulario se estire verticalmente
        JPanel formContainer = new JPanel(new BorderLayout());
        formContainer.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        formContainer.add(panelForm, BorderLayout.NORTH);
        add(formContainer, BorderLayout.CENTER);

        // --- TABLA ---
        String[] cols = {"Identificación", "Nombre", "Teléfono", "Dirección", "Estado"};
        JTable tabla = new JTable(new DefaultTableModel(cols, 0));
        ConstructorComponentes.darEstiloTabla(tabla);
        
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(0, 350));
        add(scroll, BorderLayout.SOUTH);
    }
}