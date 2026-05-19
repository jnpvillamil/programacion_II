package co.edu.uptc.controlador;

import co.edu.uptc.gui.PanelInventario;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.negocio.GestionInventario;
import co.edu.uptc.utilidades.FormateadorMoneda;
import co.edu.uptc.utilidades.ValidadorEntradas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ControladorInventario {

    private PanelInventario vistaInventario;
    private GestionInventario gestionInventario;

    public ControladorInventario(PanelInventario vistaInventario, GestionInventario gestionInventario) {
        this.vistaInventario = vistaInventario;
        this.gestionInventario = gestionInventario;
        inicializarEventos();
        actualizarTabla();
    }

    private void inicializarEventos() {
        vistaInventario.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });

        vistaInventario.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });
    }

    private void guardarProducto() {
        String codigo = vistaInventario.getTxtCodigo().getText();
        String nombre = vistaInventario.getTxtNombre().getText();
        String categoria = vistaInventario.getTxtCategoria().getText();
        String precioCompraStr = vistaInventario.getTxtPrecioCompra().getText();
        String precioVentaStr = vistaInventario.getTxtPrecioVenta().getText();
        String stockActualStr = vistaInventario.getTxtStockActual().getText();
        String stockMinimoStr = vistaInventario.getTxtStockMinimo().getText();
        String stockMaximoStr = vistaInventario.getTxtStockMaximo().getText();

        if (ValidadorEntradas.esVacio(codigo) || ValidadorEntradas.esVacio(nombre) || ValidadorEntradas.esVacio(categoria)) {
            JOptionPane.showMessageDialog(vistaInventario, "Existen campos de texto vacíos.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!ValidadorEntradas.esNumero(precioCompraStr) || !ValidadorEntradas.esNumero(precioVentaStr) ||
            !ValidadorEntradas.esNumero(stockActualStr) || !ValidadorEntradas.esNumero(stockMinimoStr) || !ValidadorEntradas.esNumero(stockMaximoStr)) {
            JOptionPane.showMessageDialog(vistaInventario, "Los campos de precio y stock deben ser numéricos.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Producto producto = new Producto(
                    codigo, nombre, categoria,
                    Double.parseDouble(precioCompraStr),
                    Double.parseDouble(precioVentaStr),
                    Integer.parseInt(stockActualStr),
                    Integer.parseInt(stockMinimoStr),
                    Integer.parseInt(stockMaximoStr),
                    true
            );

            if (gestionInventario.buscarProducto(codigo) == null) {
                boolean registrado = gestionInventario.registrarProducto(producto);
                if (registrado) {
                    JOptionPane.showMessageDialog(vistaInventario, "Producto registrado con éxito.");
                } else {
                    JOptionPane.showMessageDialog(vistaInventario, "No se pudo registrar el producto. Revise el stock mínimo y máximo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                gestionInventario.actualizarProducto(producto);
                JOptionPane.showMessageDialog(vistaInventario, "Producto actualizado con éxito.");
            }

            limpiarFormulario();
            actualizarTabla();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaInventario, "Ocurrió un error al procesar el producto: " + ex.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProducto() {
        int filaSeleccionada = vistaInventario.getTablaProductos().getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vistaInventario, "Seleccione un producto de la tabla para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigo = vistaInventario.getModeloTabla().getValueAt(filaSeleccionada, 0).toString();
        int confirmacion = JOptionPane.showConfirmDialog(vistaInventario, "¿Está seguro de inactivar el producto seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                Producto p = gestionInventario.buscarProducto(codigo);
                if (p != null) {
                    p.setActivo(false);
                    gestionInventario.actualizarProducto(p);
                    actualizarTabla();
                    JOptionPane.showMessageDialog(vistaInventario, "Producto inactivado con éxito.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vistaInventario, "Ocurrió un error: " + ex.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarTabla() {
        DefaultTableModel modelo = vistaInventario.getModeloTabla();
        modelo.setRowCount(0);
        List<Producto> productos = gestionInventario.obtenerTodosLosProductos();
        
        for (Producto p : productos) {
            if (p.isActivo()) {
                Object[] fila = {
                        p.getCodigoProducto(),
                        p.getNombreProducto(),
                        p.getCategoria(),
                        FormateadorMoneda.formatearPeso(p.getPrecioCompra()),
                        FormateadorMoneda.formatearPeso(p.getPrecioVenta()),
                        p.getStockActual(),
                        p.getStockMinimo(),
                        p.getStockMaximo()
                };
                modelo.addRow(fila);
            }
        }
    }

    private void limpiarFormulario() {
        vistaInventario.getTxtCodigo().setText("");
        vistaInventario.getTxtNombre().setText("");
        vistaInventario.getTxtCategoria().setText("");
        vistaInventario.getTxtPrecioCompra().setText("");
        vistaInventario.getTxtPrecioVenta().setText("");
        vistaInventario.getTxtStockActual().setText("");
        vistaInventario.getTxtStockMinimo().setText("");
        vistaInventario.getTxtStockMaximo().setText("");
    }
}