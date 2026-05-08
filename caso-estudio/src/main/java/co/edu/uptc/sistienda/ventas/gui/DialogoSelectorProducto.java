package co.edu.uptc.sistienda.ventas.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.sistienda.modelo.DetalleVenta;
import co.edu.uptc.sistienda.modelo.Producto;

public class DialogoSelectorProducto extends JDialog {

    private JTable            tablaProductos;
    private DefaultTableModel modeloTablaProductos;
    private JTextField        campoPorcentajeDescuento;
    private DetalleVenta      detalleVentaSeleccionado;

    public DialogoSelectorProducto(List<Producto> productosDisponibles) {
        setTitle("Seleccionar producto");
        setModal(true);
        setLayout(new BorderLayout(6, 6));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        String[] encabezadosColumnas = {"Código", "Nombre", "Categoría", "Impuesto", "Precio Venta", "Stock"};
        modeloTablaProductos = new DefaultTableModel(encabezadosColumnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false; // El usuario no puede editar celdas directamente en la tabla
            }
        };

        // Llenamos la tabla solo con los productos que están activos
        for (Producto producto : productosDisponibles) {
            if (producto.isActivo()) {
                String descripcionCategoria = producto.getCategoria()    != null ? producto.getCategoria().getDescripcion()    : "";
                String descripcionImpuesto  = producto.getTipoImpuesto() != null ? producto.getTipoImpuesto().getDescripcion() : "";
                String precioFormateado     = "$" + String.format("%,.0f", producto.getPrecioVenta());

                modeloTablaProductos.addRow(new Object[]{
                    producto.getCodigoInterno(),
                    producto.getNombreProducto(),
                    descripcionCategoria,
                    descripcionImpuesto,
                    precioFormateado,
                    producto.getStockActual()
                });
            }
        }

        tablaProductos = new JTable(modeloTablaProductos);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Solo se puede elegir un producto a la vez
        tablaProductos.setRowHeight(22);
        tablaProductos.getTableHeader().setReorderingAllowed(false);

        // Si hay muchos productos, se muestran máximo 12 filas y el resto se ve con scroll
        int cantidadFilas   = modeloTablaProductos.getRowCount();
        int filasVisibles   = Math.min(cantidadFilas, 12);
        int alturaTabla     = filasVisibles * 22 + 30; 
        tablaProductos.setPreferredScrollableViewportSize(new Dimension(560, alturaTabla));

        add(new JScrollPane(tablaProductos), BorderLayout.CENTER);

        JPanel panelParteSur = new JPanel(new BorderLayout(6, 4));

        JPanel panelDescuento = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        panelDescuento.add(new JLabel("Descuento (%):"));
        campoPorcentajeDescuento = new JTextField("0", 5);
        panelDescuento.add(campoPorcentajeDescuento);
        panelParteSur.add(panelDescuento, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));

        JButton botonAgregar = new JButton("Agregar");
        botonAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                agregarProductoSeleccionado(productosDisponibles);
            }
        });

        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                dispose(); // Cierra el diálogo sin agregar nada
            }
        });

        panelBotones.add(botonAgregar);
        panelBotones.add(botonCancelar);
        panelParteSur.add(panelBotones, BorderLayout.SOUTH);

        add(panelParteSur, BorderLayout.SOUTH);

        // pack() se encarga de medir todo el contenido y ajusta el tamaño del diálogo automáticamente
        pack();
        setMinimumSize(new Dimension(500, 300));
        setLocationRelativeTo(null); 
    }

    private void agregarProductoSeleccionado(List<Producto> productosDisponibles) {
        int filaSeleccionada = tablaProductos.getSelectedRow();

        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String entradaCantidad = JOptionPane.showInputDialog(this, "Cantidad a vender:", "1");

        if (entradaCantidad == null) {
            return;
        }

        int cantidadAVender;
        try {
            cantidadAVender = Integer.parseInt(entradaCantidad.trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un número entero válido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cantidadAVender <= 0) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double porcentajeDescuento = 0;
        try {
            porcentajeDescuento = Double.parseDouble(campoPorcentajeDescuento.getText().trim());
        } catch (NumberFormatException descuentoInvalido) {}

        int contadorProductosActivos = 0;
        Producto productoElegido = null;
        for (Producto producto : productosDisponibles) {
            if (producto.isActivo()) {
                if (contadorProductosActivos == filaSeleccionada) {
                    productoElegido = producto;
                    break;
                }
                contadorProductosActivos++;
            }
        }

        if (productoElegido == null) {
            return;
        }

        // Crea el detalle de venta 
        DetalleVenta nuevoDetalle = new DetalleVenta(productoElegido, cantidadAVender, productoElegido.getPrecioVenta());
        nuevoDetalle.setDescuentoDto(porcentajeDescuento);
        detalleVentaSeleccionado = nuevoDetalle;

        dispose();
    }

    public DetalleVenta getItemResultado() {
        return detalleVentaSeleccionado;
    }
}
