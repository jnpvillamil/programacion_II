package co.edu.uptc.controlador;

import co.edu.uptc.gui.PanelProducto;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.negocio.GestionInventario;
import co.edu.uptc.utilidades.ValidadorEntradas;
import javax.swing.*;

public class ControladorProducto {

    private PanelProducto vista;
    private GestionInventario negocio;

    public ControladorProducto(PanelProducto vista, GestionInventario negocio) {
        this.vista = vista;
        this.negocio = negocio;
        this.inicializarEventos();
    }

    private void inicializarEventos() {
        vista.getBtnGuardar().addActionListener(e -> agregar());
    }

    private void agregar() {
        if (ValidadorEntradas.esVacio(vista.getTxtCodigo().getText()) || 
            ValidadorEntradas.esVacio(vista.getTxtNombre().getText())) {
            JOptionPane.showMessageDialog(vista, "Código y Nombre son campos obligatorios.");
            return;
        }

        try {
            Producto nuevo = new Producto(
                vista.getTxtCodigo().getText(),
                vista.getTxtNombre().getText(),
                vista.getTxtCategoria().getText(),
                Double.parseDouble(vista.getTxtPrecioCompra().getText()),
                Double.parseDouble(vista.getTxtPrecioVenta().getText()),
                Integer.parseInt(vista.getTxtStockActual().getText()),
                Integer.parseInt(vista.getTxtStockMinimo().getText()),
                Integer.parseInt(vista.getTxtStockMaximo().getText()),
                true
            );

            boolean exito = negocio.registrarProducto(nuevo);
            
            if (exito) {
                JOptionPane.showMessageDialog(vista, "Producto agregado correctamente al sistema.");
                this.limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vista, "Error: El producto ya existe o los valores de stock son inválidos.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Error: Verifique que los precios y stock sean valores numéricos.");
        }
    }

    private void limpiarCampos() {
        vista.getTxtCodigo().setText("");
        vista.getTxtNombre().setText("");
        vista.getTxtCategoria().setText("");
        vista.getTxtPrecioCompra().setText("");
        vista.getTxtPrecioVenta().setText("");
        vista.getTxtStockActual().setText("");
        vista.getTxtStockMinimo().setText("");
        vista.getTxtStockMaximo().setText("");
    }
}