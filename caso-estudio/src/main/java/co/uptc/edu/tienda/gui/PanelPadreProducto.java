package co.uptc.edu.tienda.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import co.uptc.edu.tienda.negocio.Producto;
import co.uptc.edu.tienda.negocio.GestionProducto;

import java.awt.*;
import java.util.List;

public class PanelPadreProducto extends JPanel {

    private JButton btnRegistrar, btnEliminar, btnActualizar;
    private JButton btnBuscar, btnLimpiar, btnVer;

    private JTable tabla;
    private DefaultTableModel modelo;

    private GestionProducto gestionProducto;

    public PanelPadreProducto(Evento evento) {

        gestionProducto = new GestionProducto();

        setLayout(new BorderLayout()); // 🔥 CAMBIO IMPORTANTE

        // 🔥 PANEL DE BOTONES ARRIBA
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setActionCommand(Evento.CREAR_PRD);
        btnRegistrar.addActionListener(evento);

        btnActualizar = new JButton("Modificar");
        btnActualizar.setActionCommand(Evento.ACTUALIZAR_PRD);
        btnActualizar.addActionListener(evento);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setActionCommand(Evento.ELIMINAR_PRD);
        btnEliminar.addActionListener(evento);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setActionCommand(Evento.BUSCAR_PRD);
        btnBuscar.addActionListener(evento);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setActionCommand(Evento.LIMPIAR_PRD);
        btnLimpiar.addActionListener(evento);

        btnVer = new JButton("Ver");
        btnVer.setActionCommand(Evento.VER_PRD);
        btnVer.addActionListener(evento);

        // 🔥 AGREGAR BOTONES AL PANEL
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnVer);

        add(panelBotones, BorderLayout.NORTH);

        // 🔹 TABLA
        modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Categoría");
        modelo.addColumn("Precio Compra");
        modelo.addColumn("Precio Venta");
        modelo.addColumn("Stock");

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        add(scroll, BorderLayout.CENTER);

        // 🔥 CARGA INICIAL
        poblarTabla(gestionProducto.listar());
    }

    // 🔥 MÉTODOS

    public GestionProducto getGestionProducto() {
        return gestionProducto;
    }

    public int getItemSeleccionado() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto");
            return -1;
        }

        return Integer.parseInt(modelo.getValueAt(fila, 0).toString());
    }

    public void poblarTabla(List<Producto> lista) {
        modelo.setRowCount(0);

        for (Producto p : lista) {
            modelo.addRow(new Object[]{
                p.getCodigoProducto(),
                p.getNombreProducto(),
                p.getCategoria(),
                p.getPrecioCompra(),
                p.getPrecioVenta(),
                p.getStockActual()
            });
        }
    }
}