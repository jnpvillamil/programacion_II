package co.edu.uptc.gui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.negocio.dto.productoDto;

public class panelProductos extends JPanel {

    public JButton bRegistrar;
    public JButton bModificar;
    public JButton bInactivar;
    public JButton bBuscar;
    public JButton bVolver;
    public JButton bLimpiar;

    private JLabel lNombre;
    private JLabel lCategoria;
    private JLabel lPrecioCompra;
    private JLabel lPrecioVenta;
    private JLabel lStockActual;
    private JLabel lStockMinimo;

    public JTextField tNombre;
    public JTextField tCategoria;
    public JTextField tPrecioCompra;
    public JTextField tPrecioVenta;
    public JTextField tStockActual;
    public JTextField tStockMinimo;

    protected DefaultTableModel modeloTabla;
    protected JTable tablaProductos;

    private int codigoSeleccionado = -1;
    private Eventos evento;

    public panelProductos(Eventos evento) {
        this.evento = evento;
        construirPanel();
    }

    public panelProductos() {
        this.evento = new Eventos();
        construirPanel();
    }

    private void construirPanel() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel Campos = new JPanel(new GridLayout(6, 2, 10, 8));
        Campos.setBorder(BorderFactory.createTitledBorder("Datos del producto"));

        bRegistrar = new JButton(Eventos.pREGISTRAR);
        bModificar = new JButton(Eventos.pMODIFICAR);
        bInactivar = new JButton(Eventos.pINACTIVAR);
        bBuscar    = new JButton(Eventos.pBUSCAR);
        bVolver    = new JButton(Eventos.VOLVER);
        bLimpiar   = new JButton(Eventos.pLIMPIAR);

        lNombre       = new JLabel("NOMBRE:");
        lCategoria    = new JLabel("CATEGORIA:");
        lPrecioCompra = new JLabel("PRECIO COMPRA:");
        lPrecioVenta  = new JLabel("PRECIO VENTA:");
        lStockActual  = new JLabel("STOCK ACTUAL:");
        lStockMinimo  = new JLabel("STOCK MINIMO:");

        tNombre       = new JTextField(10);
        tCategoria    = new JTextField(10);
        tPrecioCompra = new JTextField(10);
        tPrecioVenta  = new JTextField(10);
        tStockActual  = new JTextField(10);
        tStockMinimo  = new JTextField(10);

        Campos.add(lNombre);       Campos.add(tNombre);
        Campos.add(lCategoria);    Campos.add(tCategoria);
        Campos.add(lPrecioCompra); Campos.add(tPrecioCompra);
        Campos.add(lPrecioVenta);  Campos.add(tPrecioVenta);
        Campos.add(lStockActual);  Campos.add(tStockActual);
        Campos.add(lStockMinimo);  Campos.add(tStockMinimo);

        modeloTabla = new DefaultTableModel(
            new String[]{"Codigo", "Nombre", "Categoria", "P.Compra", "P.Venta", "Stock", "Stock Min."}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaProductos.setRowHeight(20);
        tablaProductos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cargarSeleccionEnCampos();
        });

        JScrollPane scroll = new JScrollPane(tablaProductos);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de productos"));
        scroll.setPreferredSize(new Dimension(600, 150));

        JPanel botones = new JPanel(new GridLayout(1, 5, 8, 8));
        botones.setBorder(BorderFactory.createEmptyBorder(8, 0, 4, 0));
        botones.add(bRegistrar);
        botones.add(bModificar);
        botones.add(bInactivar);
        botones.add(bBuscar);
        botones.add(bLimpiar);

        JPanel panelVolver = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelVolver.add(bVolver);

        JPanel sur = new JPanel(new BorderLayout());
        sur.add(botones,     BorderLayout.NORTH);
        sur.add(panelVolver, BorderLayout.SOUTH);

        add(Campos, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(sur,    BorderLayout.SOUTH);

        bRegistrar.addActionListener(evento);
        bModificar.addActionListener(evento);
        bInactivar.addActionListener(evento);
        bBuscar.addActionListener(evento);
        bVolver.addActionListener(evento);
        bLimpiar.addActionListener(evento);
    }

    private void cargarSeleccionEnCampos() {
        int fila = tablaProductos.getSelectedRow();
        if (fila < 0) return;
        codigoSeleccionado = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
        tNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
        tCategoria.setText(modeloTabla.getValueAt(fila, 2).toString());
        tPrecioCompra.setText(modeloTabla.getValueAt(fila, 3).toString());
        tPrecioVenta.setText(modeloTabla.getValueAt(fila, 4).toString());
        tStockActual.setText(modeloTabla.getValueAt(fila, 5).toString());
        tStockMinimo.setText(modeloTabla.getValueAt(fila, 6).toString());
    }

    public void poblarTabla(List<productoDto> lista) {
        modeloTabla.setRowCount(0);
        for (productoDto p : lista) {
            modeloTabla.addRow(new Object[]{
                p.getCodigoProducto(), p.getNombre(), p.getCategoria(),
                p.getPrecioCompra(), p.getPrecioVenta(),
                p.getStockActual(), p.getStockMinimo()
            });
        }
    }

    public void limpiarCampos() {
        codigoSeleccionado = -1;
        tNombre.setText("");
        tCategoria.setText("");
        tPrecioCompra.setText("");
        tPrecioVenta.setText("");
        tStockActual.setText("");
        tStockMinimo.setText("");
        tablaProductos.clearSelection();
    }

    public productoDto getDatosProducto() {
        String nombre = tNombre.getText();
        if (nombre == null || nombre.isBlank()) {
            JOptionPane.showMessageDialog(this, "El nombre es requerido");
            return null;
        }
        productoDto producto = new productoDto();
        producto.setNombre(nombre);
        producto.setCategoria(tCategoria.getText());
        try {
            producto.setPrecioCompra(Double.parseDouble(tPrecioCompra.getText()));
            producto.setPrecioVenta(Double.parseDouble(tPrecioVenta.getText()));
            producto.setStockActual(Integer.parseInt(tStockActual.getText()));
            producto.setStockMinimo(Integer.parseInt(tStockMinimo.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Los precios y stocks deben ser numericos");
            return null;
        }
        return producto;
    }

    public productoDto getDatosProductoModificar() {
        if (codigoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla para modificar");
            return null;
        }
        String nombre = tNombre.getText();
        if (nombre == null || nombre.isBlank()) {
            JOptionPane.showMessageDialog(this, "El nombre es requerido");
            return null;
        }
        productoDto producto = new productoDto(codigoSeleccionado);
        producto.setNombre(nombre);
        producto.setCategoria(tCategoria.getText());
        try {
            producto.setPrecioCompra(Double.parseDouble(tPrecioCompra.getText()));
            producto.setPrecioVenta(Double.parseDouble(tPrecioVenta.getText()));
            producto.setStockActual(Integer.parseInt(tStockActual.getText()));
            producto.setStockMinimo(Integer.parseInt(tStockMinimo.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Los precios y stocks deben ser numericos");
            return null;
        }
        return producto;
    }

    public int getCodigoProducto() {
        if (codigoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla");
            return -1;
        }
        return codigoSeleccionado;
    }
}
