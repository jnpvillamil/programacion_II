package co.uptc.edu.tienda.gui;

import javax.swing.*;
import java.awt.*;
import co.uptc.edu.tienda.modelo.Producto;

public class DialogoProducto extends DialogoCentral {

    private JTextField txtNombre, txtCategoria, txtPrecioCompra, txtPrecioVenta, txtStock, txtStockMinimo, txtStockMaximo;
    private JComboBox<String> comboIva; // ← atributo de instancia
    private int codigoActual;

    public DialogoProducto(Evento evento, String titulo, boolean isCrear) {
        super(evento, titulo, isCrear);
        setSize(450, 550); // Un poco más alto para acomodar la fila de IVA
    }

    @Override
    public void iniciarComponentes() {
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // 8 filas: 7 campos originales + 1 para IVA
        panelCentral.setLayout(new GridLayout(8, 2, 10, 15));

        txtNombre        = new JTextField();
        txtCategoria     = new JTextField();
        txtPrecioCompra  = new JTextField();
        txtPrecioVenta   = new JTextField();
        txtStock         = new JTextField();
        txtStockMinimo   = new JTextField();
        txtStockMaximo   = new JTextField();

        comboIva = new JComboBox<>(
            new String[]{"0% - No aplica", "5% - Reducido", "19% - General"}
        );

        panelCentral.add(new JLabel("Nombre:",        SwingConstants.RIGHT)); panelCentral.add(txtNombre);
        panelCentral.add(new JLabel("Categoría:",     SwingConstants.RIGHT)); panelCentral.add(txtCategoria);
        panelCentral.add(new JLabel("Precio Compra:", SwingConstants.RIGHT)); panelCentral.add(txtPrecioCompra);
        panelCentral.add(new JLabel("Precio Venta:",  SwingConstants.RIGHT)); panelCentral.add(txtPrecioVenta);
        panelCentral.add(new JLabel("Stock Actual:",  SwingConstants.RIGHT)); panelCentral.add(txtStock);
        panelCentral.add(new JLabel("Stock Mínimo:",  SwingConstants.RIGHT)); panelCentral.add(txtStockMinimo);
        panelCentral.add(new JLabel("Stock Máximo:",  SwingConstants.RIGHT)); panelCentral.add(txtStockMaximo);
        panelCentral.add(new JLabel("IVA:",           SwingConstants.RIGHT)); panelCentral.add(comboIva);
    }

    @Override
    public void asignarComandos() {
        btnGuardar.setActionCommand(isCrear ? Evento.GUARDAR_PRD : Evento.EDITAR_PRD);
        btnCerrar.setActionCommand(Evento.CANCELAR_PRD);
    }

    public Producto capturarDatos() {
        int codigo = isCrear ? (int)(Math.random() * 1000) : codigoActual;

        try {
            if (txtNombre.getText().trim().isEmpty() || txtPrecioCompra.getText().trim().isEmpty()) {
                throw new Exception("Campos obligatorios vacíos.");
            }

            // Resolver porcentaje de IVA desde el combo
            double porcentajeIva;
            String ivaSeleccionado = (String) comboIva.getSelectedItem();
            if      (ivaSeleccionado.startsWith("19")) porcentajeIva = 0.19;
            else if (ivaSeleccionado.startsWith("5"))  porcentajeIva = 0.05;
            else                                       porcentajeIva = 0.0;

            return new Producto(
                codigo,
                txtNombre.getText().trim(),
                txtCategoria.getText().trim(),
                Double.parseDouble(txtPrecioCompra.getText().trim()),
                Double.parseDouble(txtPrecioVenta.getText().trim()),
                Integer.parseInt(txtStock.getText().trim()),
                Integer.parseInt(txtStockMinimo.getText().trim()),
                Integer.parseInt(txtStockMaximo.getText().trim()),
                porcentajeIva
            );

        } catch (Exception e) {
            throw new IllegalArgumentException("Datos inválidos: Revise que los precios y stock sean números.");
        }
    }

    public void cargarDatos(Producto p) {
        this.codigoActual = p.getCodigoProducto();
        txtNombre.setText(p.getNombreProducto());
        txtCategoria.setText(p.getCategoria());
        txtPrecioCompra.setText(String.valueOf(p.getPrecioCompra()));
        txtPrecioVenta.setText(String.valueOf(p.getPrecioVenta()));
        txtStock.setText(String.valueOf(p.getStockActual()));
        txtStockMinimo.setText(String.valueOf(p.getStockMinimo()));
        txtStockMaximo.setText(String.valueOf(p.getStockMaximo()));

        // Preseleccionar la opción de IVA correcta al editar
        double iva = p.getPorcentajeIva();
        if      (iva == 0.19) comboIva.setSelectedIndex(2);
        else if (iva == 0.05) comboIva.setSelectedIndex(1);
        else                  comboIva.setSelectedIndex(0);
    }
}