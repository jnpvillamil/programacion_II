package co.edu.uptc.gui;

import co.edu.uptc.utilidades.ConstructorComponentes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelCompra extends JPanel {
    public PanelCompra() {
        setLayout(new BorderLayout(20, 20));
        setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = ConstructorComponentes.crearEtiquetaNegrita("MÓDULO DE COMPRAS");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // Cabecera de Selección
        JPanel panelCabecera = new JPanel(new GridLayout(2, 4, 10, 10));
        panelCabecera.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        
        panelCabecera.add(ConstructorComponentes.crearEtiquetaNegrita("Factura Proveedor:"));
        panelCabecera.add(ConstructorComponentes.crearCampoTexto());
        
        panelCabecera.add(ConstructorComponentes.crearEtiquetaNegrita("Producto (Código):"));
        panelCabecera.add(ConstructorComponentes.crearCampoTexto());
        
        JButton btnAgregar = ConstructorComponentes.crearBotonAccion("Añadir Ingreso", ConstructorComponentes.COLOR_AZUL_ACCION);
        panelCabecera.add(new JLabel(""));
        panelCabecera.add(new JLabel(""));
        panelCabecera.add(new JLabel(""));
        panelCabecera.add(btnAgregar);

        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        panelCentro.add(panelCabecera, BorderLayout.NORTH);

        // Tabla
        String[] cols = {"Cantidad", "Descripción", "Costo Unitario", "Subtotal"};
        JTable tabla = new JTable(new DefaultTableModel(cols, 0));
        ConstructorComponentes.darEstiloTabla(tabla);
        panelCentro.add(new JScrollPane(tabla), BorderLayout.CENTER);

        add(panelCentro, BorderLayout.CENTER);

        // Panel Inferior (Totales)
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        panelSur.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        
        panelSur.add(ConstructorComponentes.crearEtiquetaNegrita("Costo Total: $ 0.00"));
        JButton btnFinalizar = ConstructorComponentes.crearBotonAccion("Registrar Compra", ConstructorComponentes.COLOR_VERDE_GUARDAR);
        panelSur.add(btnFinalizar);

        add(panelSur, BorderLayout.SOUTH);
    }
}