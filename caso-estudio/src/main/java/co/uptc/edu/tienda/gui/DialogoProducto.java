package co.uptc.edu.tienda.gui;

import javax.swing.*;
import java.awt.*;
import co.uptc.edu.tienda.modelo.Producto;

public class DialogoProducto extends DialogoCentral {

    private JTextField txtNombre, txtCategoria, txtPrecioCompra, txtPrecioVenta, txtStock, txtStockMinimo, txtStockMaximo;
    private int codigoActual;

    public DialogoProducto(Evento evento, String titulo, boolean isCrear) {
        super(evento, titulo, isCrear);
        setSize(450, 500); // Un poco más ancho para que los campos luzcan bien
    }

    @Override
    public void iniciarComponentes() {
        // Añadimos margen para que no se vea "feo" ni pegado a los bordes
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Usamos GridLayout de 7 filas y 2 columnas con espacio entre ellas
        panelCentral.setLayout(new GridLayout(7, 2, 10, 15));

        txtNombre = new JTextField();
        txtCategoria = new JTextField();
        txtPrecioCompra = new JTextField();
        txtPrecioVenta = new JTextField();
        txtStock = new JTextField();
        txtStockMinimo = new JTextField();
        txtStockMaximo = new JTextField();

        // Agregamos Labels y Fields
        panelCentral.add(new JLabel("Nombre:", SwingConstants.RIGHT));
        panelCentral.add(txtNombre);
        
        panelCentral.add(new JLabel("Categoría:", SwingConstants.RIGHT));
        panelCentral.add(txtCategoria);
        
        panelCentral.add(new JLabel("Precio Compra:", SwingConstants.RIGHT));
        panelCentral.add(txtPrecioCompra);
        
        panelCentral.add(new JLabel("Precio Venta:", SwingConstants.RIGHT));
        panelCentral.add(txtPrecioVenta);
        
        panelCentral.add(new JLabel("Stock Actual:", SwingConstants.RIGHT));
        panelCentral.add(txtStock);
        
        panelCentral.add(new JLabel("Stock Mínimo:", SwingConstants.RIGHT));
        panelCentral.add(txtStockMinimo);
        
        panelCentral.add(new JLabel("Stock Máximo:", SwingConstants.RIGHT));
        panelCentral.add(txtStockMaximo);
    }

    @Override
    public void asignarComandos() {
        // Los botones btnGuardar y btnCerrar vienen heredados de DialogoCentral
        btnGuardar.setActionCommand(isCrear ? Evento.GUARDAR_PRD : Evento.EDITAR_PRD);
        btnCerrar.setActionCommand(Evento.CANCELAR_PRD);
    }

    public Producto capturarDatos() {
        // Si es crear, podrías usar el random o dejar que la lógica lo maneje
        int codigo = isCrear ? (int)(Math.random() * 1000) : codigoActual;

        try {
            // Validamos que no haya campos vacíos antes de parsear
            if(txtNombre.getText().trim().isEmpty() || txtPrecioCompra.getText().trim().isEmpty()){
                throw new Exception("Campos obligatorios vacíos.");
            }

            return new Producto(
                codigo,
                txtNombre.getText().trim(),
                txtCategoria.getText().trim(),
                Double.parseDouble(txtPrecioCompra.getText().trim()),
                Double.parseDouble(txtPrecioVenta.getText().trim()),
                Integer.parseInt(txtStock.getText().trim()),
                Integer.parseInt(txtStockMinimo.getText().trim()),
                Integer.parseInt(txtStockMaximo.getText().trim())
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
    }
}