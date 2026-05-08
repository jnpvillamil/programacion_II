package co.uptc.edu.tienda.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import co.uptc.edu.tienda.modelo.Producto;
import co.uptc.edu.tienda.negocio.GestionProducto;
import co.uptc.edu.tienda.persistencia.LocalProducto;

import java.awt.*;
import java.util.List;

public class PanelPadreProducto extends JPanel {

    private JButton btnRegistrar, btnInactivar, btnActivar, btnActualizar;
    private JButton btnBuscar, btnLimpiar, btnVer;

    private JTable tabla;
    private DefaultTableModel modelo;

    private GestionProducto gestionProducto;

    public PanelPadreProducto(Evento evento) {

        gestionProducto = new GestionProducto(new LocalProducto());

        setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // REGISTRAR
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setActionCommand(Evento.CREAR_PRD);
        btnRegistrar.addActionListener(evento);

        // MODIFICAR
        btnActualizar = new JButton("Modificar");
        btnActualizar.setActionCommand(Evento.ACTUALIZAR_PRD);
        btnActualizar.addActionListener(evento);

        // INACTIVAR
        btnInactivar = new JButton("Inactivar");
        btnInactivar.setActionCommand(Evento.INACTIVAR_PRD);
        btnInactivar.addActionListener(evento);

        // ACTIVAR
        btnActivar = new JButton("Activar");
        btnActivar.setActionCommand(Evento.ACTIVAR_PRD);
        btnActivar.addActionListener(evento);

        // BUSCAR
        btnBuscar = new JButton("Buscar");
        btnBuscar.setActionCommand(Evento.BUSCAR_PRD);
        btnBuscar.addActionListener(evento);

        // LIMPIAR
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setActionCommand(Evento.LIMPIAR_PRD);
        btnLimpiar.addActionListener(evento);

        // VER
        btnVer = new JButton("Ver");
        btnVer.setActionCommand(Evento.VER_PRD);
        btnVer.addActionListener(evento);

        // AGREGAR BOTONES
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnInactivar);
        panelBotones.add(btnActivar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnVer);

        add(panelBotones, BorderLayout.NORTH);

        // TABLA
        modelo = new DefaultTableModel();

        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Categoría");
        modelo.addColumn("Precio Compra");
        modelo.addColumn("Precio Venta");
        modelo.addColumn("Stock");
        modelo.addColumn("Stock Mínimo");
        modelo.addColumn("Stock Máximo");
        modelo.addColumn("Activo");

        tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);

        add(scroll, BorderLayout.CENTER);

        poblarTabla(gestionProducto.listar());
    }

    public GestionProducto getGestionProducto() {
        return gestionProducto;
    }

    public int getItemSeleccionado() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(this, "Seleccione un producto");

            return -1;
        }

        return Integer.parseInt(
            modelo.getValueAt(fila, 0).toString()
        );
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
                p.getStockActual(),
                p.getStockMinimo(),
                p.getStockMaximo(),
                p.isActivo() ? "Sí" : "No"
            });
        }
    }
}