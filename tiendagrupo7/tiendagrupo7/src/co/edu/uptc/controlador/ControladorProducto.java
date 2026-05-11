package co.edu.uptc.controlador;

import co.edu.uptc.gui.PanelProducto;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.negocio.GestionInventario;
import co.edu.uptc.utilidades.ValidadorEntradas;
import javax.swing.*;
import java.util.List;

public class ControladorProducto {

    private PanelProducto vista;
    private GestionInventario negocio;

    public ControladorProducto(PanelProducto vista, GestionInventario negocio) {
        this.vista = vista;
        this.negocio = negocio;
        this.inicializarEventos();
        this.actualizarTabla();
    }

    private void inicializarEventos() {
        vista.getBtnGuardar().addActionListener(e -> guardar());
        vista.getBtnBuscar().addActionListener(e -> buscar());
        vista.getBtnEditar().addActionListener(e -> editar());
        vista.getBtnInactivar().addActionListener(e -> inactivar());
        

        vista.getTablaProductos().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && vista.getTablaProductos().getSelectedRow() != -1) {
                cargarDesdeTabla();
            }
        });
    }

    private void guardar() {
        if (!validar()) return;
        Producto p = crearDesdeForm();
        if (negocio.registrarProducto(p)) {
            actualizarTabla();
            limpiar();
            JOptionPane.showMessageDialog(vista, "Producto Guardado.");
        } else {
            JOptionPane.showMessageDialog(vista, "Error: El código ya existe.");
        }
    }

    private void buscar() {
        String cod = vista.getTxtCodigo().getText();
        Producto p = negocio.buscarProducto(cod);
        if (p != null) {
            cargarEnForm(p);
        } else {
            JOptionPane.showMessageDialog(vista, "No se encontró el producto.");
        }
    }

    private void editar() {
        if (!validar()) return;
        Producto p = crearDesdeForm();
        if (negocio.actualizarProducto(p)) {
            actualizarTabla();
            JOptionPane.showMessageDialog(vista, "Producto Actualizado.");
        }
    }//prueba commit

    private void inactivar() {
        String cod = vista.getTxtCodigo().getText();
        Producto p = negocio.buscarProducto(cod);
        if (p != null) {
            p.setActivo(false);
            negocio.actualizarProducto(p);
            actualizarTabla();
            limpiar();
            JOptionPane.showMessageDialog(vista, "Producto Inactivado.");
        }
    }

    private void actualizarTabla() {
        vista.getModeloTabla().setRowCount(0);
        List<Producto> lista = negocio.obtenerTodosLosProductos();
        for (Producto p : lista) {
            if (p.isActivo()) {
                vista.getModeloTabla().addRow(new Object[]{
                    p.getCodigoProducto(), p.getNombreProducto(), p.getCategoria(),
                    p.getPrecioVenta(), p.getStockActual(), p.getStockMinimo()
                });
            }
        }
    }

    private void cargarDesdeTabla() {
        int fila = vista.getTablaProductos().getSelectedRow();
        String cod = vista.getModeloTabla().getValueAt(fila, 0).toString();
        cargarEnForm(negocio.buscarProducto(cod));
    }

    private boolean validar() {
        return !ValidadorEntradas.esVacio(vista.getTxtCodigo().getText()) && 
               ValidadorEntradas.esNumero(vista.getTxtPrecioVenta().getText());
    }

    private void cargarEnForm(Producto p) {
        vista.getTxtCodigo().setText(p.getCodigoProducto());
        vista.getTxtNombre().setText(p.getNombreProducto());
        vista.getTxtCategoria().setText(p.getCategoria());
        vista.getTxtPrecioCompra().setText(String.valueOf(p.getPrecioCompra()));
        vista.getTxtPrecioVenta().setText(String.valueOf(p.getPrecioVenta()));
        vista.getTxtStockActual().setText(String.valueOf(p.getStockActual()));
        vista.getTxtStockMinimo().setText(String.valueOf(p.getStockMinimo()));
        vista.getTxtStockMaximo().setText(String.valueOf(p.getStockMaximo()));
    }

    private Producto crearDesdeForm() {
        return new Producto(
            vista.getTxtCodigo().getText(), vista.getTxtNombre().getText(),
            vista.getTxtCategoria().getText(), Double.parseDouble(vista.getTxtPrecioCompra().getText()),
            Double.parseDouble(vista.getTxtPrecioVenta().getText()), Integer.parseInt(vista.getTxtStockActual().getText()),
            Integer.parseInt(vista.getTxtStockMinimo().getText()), Integer.parseInt(vista.getTxtStockMaximo().getText()), true
        );
    }

    private void limpiar() {
        vista.getTxtCodigo().setText(""); vista.getTxtNombre().setText("");
        vista.getTxtCategoria().setText(""); vista.getTxtPrecioVenta().setText("");
    }
}