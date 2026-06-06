package co.edu.uptc.gui;

import co.edu.uptc.dto.ProductoResumenDTO;
import co.edu.uptc.interfaces.ManejadorEventoBodeguero;
import co.edu.uptc.utilidades.ConstructorComponentes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelBodeguero extends JPanel {

    private final ManejadorEventoBodeguero manejadorEvento;

    private DefaultTableModel modeloTablaCritico;
    private JTable tablaInventarioCritico;

    public PanelBodeguero(ManejadorEventoBodeguero manejadorEvento) {
        this.manejadorEvento = manejadorEvento;

        setLayout(new BorderLayout(20, 20));
        ConstructorComponentes.aplicarFondoPanel(this);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = ConstructorComponentes.crearTituloModulo("Alertas de Inventario Crítico");
        add(titulo, BorderLayout.NORTH);
        add(construirPanelInventarioCritico(), BorderLayout.SOUTH);

        actualizarTablaInventarioCritico();
    }

    private JPanel construirPanelInventarioCritico() {
        JPanel panelInventario = new JPanel(new BorderLayout(10, 10));
        ConstructorComponentes.aplicarFondoPanel(panelInventario);
        panelInventario.setBorder(BorderFactory.createTitledBorder("Productos con Stock Crítico"));

        String[] columna = {
                "Código",
                "Nombre",
                "Categoría",
                "Precio Venta",
                "Stock Actual",
                "Alerta",
                "Estado"
        };

        modeloTablaCritico = new DefaultTableModel(columna, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaInventarioCritico = new JTable(modeloTablaCritico);
        ConstructorComponentes.darEstiloTabla(tablaInventarioCritico);
        tablaInventarioCritico.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollTabla = new JScrollPane(tablaInventarioCritico);
        scrollTabla.setPreferredSize(new Dimension(0, 280));
        panelInventario.add(scrollTabla, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        ConstructorComponentes.aplicarFondoPanel(panelBoton);

        JButton botonRefrescar = ConstructorComponentes.crearBotonInformativo("Refrescar");
        botonRefrescar.addActionListener(evento -> actualizarTablaInventarioCritico());
        panelBoton.add(botonRefrescar);

        panelInventario.add(panelBoton, BorderLayout.SOUTH);
        return panelInventario;
    }

    private void actualizarTablaInventarioCritico() {
        modeloTablaCritico.setRowCount(0);

        List<ProductoResumenDTO> inventarioCritico = manejadorEvento.obtenerInventarioCritico();

        if (inventarioCritico.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay producto con inventario crítico en este momento.",
                    "Inventario crítico",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (ProductoResumenDTO producto : inventarioCritico) {
            modeloTablaCritico.addRow(new Object[]{
                    producto.codigo(),
                    producto.nombre(),
                    producto.categoria(),
                    "$" + producto.precioVenta(),
                    producto.stockActual(),
                    producto.alertaMinima(),
                    producto.estado()
            });
        }
    }
}