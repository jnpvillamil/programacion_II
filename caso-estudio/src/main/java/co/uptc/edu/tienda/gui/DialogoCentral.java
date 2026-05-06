package co.uptc.edu.tienda.gui;

import javax.swing.*;
import java.awt.*;

public abstract class DialogoCentral extends JDialog {

    protected boolean isCrear;
    protected String tituloDialogo;

    // 🔹 PROVEEDOR (NO TOCAR)
    protected JTextField txRazonSocial, txNit, txDireccion, txTelefono, txCorreo;

    // 🔹 PRODUCTO (NUEVO)
    protected JTextField txNombre, txCategoria, txPrecioCompra, txPrecioVenta, txStockActual, txStockMinimo;

    protected JButton btnGuardar;
    protected JButton btnCerrar;

    public DialogoCentral(Evento evento, String tituloDialogo, boolean isCrear) {
        this.isCrear = isCrear;
        setSize(300, 350);
        setTitle(tituloDialogo);
        setLayout(new BorderLayout());

        JPanel panelCentral = new JPanel();

        // 🔥 DECIDE QUÉ MOSTRAR
        if (tituloDialogo.contains("Proveedor")) {

            // ===== PROVEEDOR =====
            txRazonSocial = new JTextField();
            txNit = new JTextField();
            txDireccion = new JTextField();
            txTelefono = new JTextField();
            txCorreo = new JTextField();

            panelCentral.setLayout(new GridLayout(5, 2));

            panelCentral.add(new JLabel("Razón Social"));
            panelCentral.add(txRazonSocial);
            panelCentral.add(new JLabel("NIT"));
            panelCentral.add(txNit);
            panelCentral.add(new JLabel("Dirección"));
            panelCentral.add(txDireccion);
            panelCentral.add(new JLabel("Teléfono"));
            panelCentral.add(txTelefono);
            panelCentral.add(new JLabel("Correo"));
            panelCentral.add(txCorreo);

        } else {

            // ===== PRODUCTO =====
            txNombre = new JTextField();
            txCategoria = new JTextField();
            txPrecioCompra = new JTextField();
            txPrecioVenta = new JTextField();
            txStockActual = new JTextField();
            txStockMinimo = new JTextField();

            panelCentral.setLayout(new GridLayout(6, 2));

            panelCentral.add(new JLabel("Nombre"));
            panelCentral.add(txNombre);
            panelCentral.add(new JLabel("Categoría"));
            panelCentral.add(txCategoria);
            panelCentral.add(new JLabel("Precio Compra"));
            panelCentral.add(txPrecioCompra);
            panelCentral.add(new JLabel("Precio Venta"));
            panelCentral.add(txPrecioVenta);
            panelCentral.add(new JLabel("Stock Actual"));
            panelCentral.add(txStockActual);
            panelCentral.add(new JLabel("Stock Mínimo"));
            panelCentral.add(txStockMinimo);
        }

        // 🔹 BOTONES (IGUAL PARA AMBOS)
        if (isCrear) {
            btnGuardar = new JButton(Evento.GUARDAR);
        } else {
            btnGuardar = new JButton(Evento.EDITAR);
        }

        btnCerrar = new JButton(Evento.CANCELAR);

        btnGuardar.addActionListener(evento);
        btnCerrar.addActionListener(evento);
        btnCerrar.setActionCommand(Evento.CANCELAR_PR);

        JPanel pBotones = new JPanel();
        pBotones.add(btnCerrar);
        pBotones.add(btnGuardar);

        add(panelCentral, BorderLayout.CENTER);
        add(pBotones, BorderLayout.SOUTH);

        asignarComandoBotones();
    }

    public abstract void asignarComandoBotones();
}
