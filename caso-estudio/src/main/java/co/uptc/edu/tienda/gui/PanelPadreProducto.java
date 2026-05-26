package co.uptc.edu.tienda.gui;

import javax.swing.*;
import co.uptc.edu.tienda.modelo.Producto;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class PanelPadreProducto extends PanelCentral<Producto> {

	private JButton btnAlertaStock;
	private List<Producto> todosProductos = new ArrayList<>();

    public PanelPadreProducto(Evento evento) {
        // 1. Llama al constructor del padre (crea botones, tabla y layout)
        super(evento); 
        
        btnAlertaStock = new JButton("⚠ Stock Mínimo");
        btnAlertaStock.setBackground(new Color(230, 126, 34));
        btnAlertaStock.setForeground(Color.WHITE);
        btnAlertaStock.setFocusPainted(false);
        btnAlertaStock.setActionCommand(Evento.ALERTA_STOCK);
        btnAlertaStock.addActionListener(evento); // ← pasa el evento

        JPanel panelAlerta = new JPanel();
        panelAlerta.add(btnAlertaStock);
        add(panelAlerta, BorderLayout.SOUTH);
        
    }

    @Override
    public void agregarTituloPanel() {
        this.tituloPanel = "Productos";
    }

    @Override
    public void agregarIdentificadorComandoBoton() {
        // Configuramos los botones HEREDADOS del padre con comandos de Producto
        btnRegistrar.setActionCommand(Evento.CREAR_PRD);
        btnActualizar.setActionCommand(Evento.ACTUALIZAR_PRD);
        btnInactivar.setActionCommand(Evento.INACTIVAR_PRD);
        btnVer.setActionCommand(Evento.VER_PRD);
        btnBuscar.setActionCommand(Evento.BUSCAR_PRD);
        btnLimpiar.setActionCommand(Evento.LIMPIAR_PRD);
        btnActivar.setActionCommand(Evento.ACTIVAR_PRD);
        
        
        
        // Nota: Si necesitas el botón "Activar", podrías añadirlo aquí:
        // JButton btnActivar = new JButton("Activar");
        // btnActivar.addActionListener(listener);...
    }

    @Override
    public void agregarCabeceraTabla() {
        // Se ejecuta automáticamente durante el super(evento)
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Categoría");
        modelo.addColumn("P. Compra");
        modelo.addColumn("P. Venta");
        modelo.addColumn("IVA (%)");
        modelo.addColumn("Stock");
        modelo.addColumn("S. Mín");
        modelo.addColumn("S. Máx");
        modelo.addColumn("Estado");
        
        tblGenerica.setModel(modelo);
    }

    @Override
    public void poblarTabla(List<Producto> listaProducto) {
    	this.todosProductos = listaProducto;
        modelo.setRowCount(0);
        for (Producto p : listaProducto) {
            modelo.addRow(new Object[]{
                p.getCodigoProducto(),
                p.getNombreProducto(),
                p.getCategoria(),
                p.getPrecioCompra(),
                p.getPrecioVenta(),
                p.getPorcentajeIva(),
                p.getStockActual(),
                p.getStockMinimo(),
                p.getStockMaximo(),
                p.isActivo() ? "Activo" : "Inactivo"
            });
        }
    }



    public int getItemSeleccionado() {
        // Usamos 'tblProveedores' porque así se llama el atributo en tu PanelCentral
        int fila = tblGenerica.getSelectedRow(); 
        
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un producto de la tabla");
            return -1;
        }
        
        // Retornamos el código del producto (columna 0)
        return Integer.parseInt(modelo.getValueAt(fila, 0).toString());
    }
    
    public void filtrarStockMinimo() {
        modelo.setRowCount(0);
        for (Producto p : todosProductos) {
            if (p.getStockActual() < p.getStockMinimo()) {
                modelo.addRow(new Object[]{
                    p.getCodigoProducto(),
                    p.getNombreProducto(),
                    p.getCategoria(),
                    p.getPrecioCompra(),
                    p.getPrecioVenta(),
                    p.getPorcentajeIva(),
                    p.getStockActual(),
                    p.getStockMinimo(),
                    p.getStockMaximo(),
                    p.isActivo() ? "Activo" : "Inactivo"
                });
            }
        }
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                "No hay productos por debajo del stock mínimo.",
                "Sin alertas",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
}