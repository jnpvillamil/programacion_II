package co.edu.uptc.gui;

import java.awt.*;
import javax.swing.*;
import co.edu.uptc.negocio.productoDto;

public class panelProductos extends JPanel {

    public JButton bRegistrar;
    public JButton bModificar;
    public JButton bInactivar;
    public JButton bBuscar;
    public JButton bVolver;
    private JLabel lCodigo;
    private JLabel lNombre;
    private JLabel lCategoria;
    private JLabel lPrecioCompra;
    private JLabel lPrecioVenta;
    private JLabel lStockActual;
    private JLabel lStockMinimo;
    public JTextField tCodigo;
    public JTextField tNombre;
    public JTextField tCategoria;
    public JTextField tPrecioCompra;
    public JTextField tPrecioVenta;
    public JTextField tStockActual;
    public JTextField tStockMinimo;
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
        setLayout(new BorderLayout());
        JPanel Campos = new JPanel(new GridLayout(7, 2, 10, 10));
        Campos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        bRegistrar = new JButton(Eventos.pREGISTRAR);
        bModificar = new JButton(Eventos.pMODIFICAR);
        bInactivar = new JButton(Eventos.pINACTIVAR);
        bBuscar    = new JButton(Eventos.pBUSCAR);
        bVolver    = new JButton(Eventos.VOLVER);

        lCodigo       = new JLabel("CODIGO:");
        lNombre       = new JLabel("NOMBRE:");
        lCategoria    = new JLabel("CATEGORIA:");
        lPrecioCompra = new JLabel("PRECIO COMPRA:");
        lPrecioVenta  = new JLabel("PRECIO VENTA:");
        lStockActual  = new JLabel("STOCK ACTUAL:");
        lStockMinimo  = new JLabel("STOCK MINIMO:");

        tCodigo       = new JTextField(10);
        tNombre       = new JTextField(10);
        tCategoria    = new JTextField(10);
        tPrecioCompra = new JTextField(10);
        tPrecioVenta  = new JTextField(10);
        tStockActual  = new JTextField(10);
        tStockMinimo  = new JTextField(10);

        Campos.add(lCodigo);       Campos.add(tCodigo);
        Campos.add(lNombre);       Campos.add(tNombre);
        Campos.add(lCategoria);    Campos.add(tCategoria);
        Campos.add(lPrecioCompra); Campos.add(tPrecioCompra);
        Campos.add(lPrecioVenta);  Campos.add(tPrecioVenta);
        Campos.add(lStockActual);  Campos.add(tStockActual);
        Campos.add(lStockMinimo);  Campos.add(tStockMinimo);

        JPanel botones = new JPanel(new GridLayout(4, 1, 10, 10));
        botones.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 20));
        botones.add(bRegistrar);
        botones.add(bModificar);
        botones.add(bInactivar);
        botones.add(bBuscar);

        JPanel panelVolver = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelVolver.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        panelVolver.add(bVolver);

        JPanel este = new JPanel(new BorderLayout());
        este.add(botones,     BorderLayout.NORTH);
        este.add(panelVolver, BorderLayout.SOUTH);

        add(Campos, BorderLayout.CENTER);
        add(este,   BorderLayout.EAST);

        bRegistrar.addActionListener(evento);
        bModificar.addActionListener(evento);
        bInactivar.addActionListener(evento);
        bBuscar.addActionListener(evento);
        bVolver.addActionListener(evento);
    }

    public productoDto getDatosProducto() {
        String codigo = tCodigo.getText();
        String nombre = tNombre.getText();
        if (codigo == null || codigo.isBlank()) {
            JOptionPane.showMessageDialog(this, "El codigo es requerido");
            return null;
        }
        if (nombre == null || nombre.isBlank()) {
            JOptionPane.showMessageDialog(this, "El nombre es requerido");
            return null;
        }
        productoDto producto = new productoDto();
        producto.setCodigo(codigo);
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

    public String getCodigoProducto() {
        String codigo = tCodigo.getText();
        if (codigo == null || codigo.isBlank()) {
            JOptionPane.showMessageDialog(this, "El codigo es requerido");
            return null;
        }
        return codigo;
    }
}