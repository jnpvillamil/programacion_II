package co.uptc.edu.tienda.gui;

import javax.swing.*;
import java.awt.*;

public abstract class DialogoCentralProducto extends JDialog {

    protected boolean isCrear;

    protected JTextField txNombre, txCategoria, txPrecioCompra, txPrecioVenta, txStockActual, txStockMinimo;
    protected JButton btnGuardar, btnCerrar;

    public DialogoCentralProducto(Evento evento, String titulo, boolean isCrear) {

        this.isCrear = isCrear;

        setTitle(titulo);
        setSize(300, 300);
        setLayout(new GridLayout(7,2));

        add(new JLabel("Nombre"));
        txNombre = new JTextField();
        add(txNombre);

        add(new JLabel("Categoría"));
        txCategoria = new JTextField();
        add(txCategoria);

        add(new JLabel("Precio Compra"));
        txPrecioCompra = new JTextField();
        add(txPrecioCompra);

        add(new JLabel("Precio Venta"));
        txPrecioVenta = new JTextField();
        add(txPrecioVenta);

        add(new JLabel("Stock"));
        txStockActual = new JTextField();
        add(txStockActual);

        add(new JLabel("Stock Mínimo"));
        txStockMinimo = new JTextField();
        add(txStockMinimo);

        btnGuardar = new JButton("Guardar");
        btnCerrar = new JButton("Cancelar");

        btnGuardar.addActionListener(evento);
        btnCerrar.addActionListener(evento);

        btnCerrar.setActionCommand(Evento.CANCELAR_PRD);

        add(btnCerrar);
        add(btnGuardar);

        asignarComandoBotones();
    }

    public abstract void asignarComandoBotones();
}