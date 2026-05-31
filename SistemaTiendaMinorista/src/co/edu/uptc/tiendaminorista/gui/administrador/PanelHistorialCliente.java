package co.edu.uptc.tiendaminorista.gui.administrador;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.tiendaminorista.gui.Evento;
import co.edu.uptc.tiendaminorista.modelo.Cliente;
import co.edu.uptc.tiendaminorista.modelo.CompasCliente;

public class PanelHistorialCliente extends JPanel {

    private JComboBox<String> comboClientes;
    private JTable tablaHistorial;
    private DefaultTableModel modeloTabla;
    private Evento evento;
    private List<Cliente> listaClientesAux;

    public PanelHistorialCliente(Evento e) {
        this.evento = e;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

 
        JLabel titulo = new JLabel("Historial de Compras por Cliente");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 16f));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titulo);
        add(Box.createVerticalStrut(20));

      
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelFiltro.add(new JLabel("Seleccione el Cliente:"));
        
        comboClientes = new JComboBox<>();
        comboClientes.setPreferredSize(new Dimension(300, 25));
        panelFiltro.add(comboClientes);

        JButton btnBuscar = new JButton("Ver Historial");
        btnBuscar.addActionListener(evento);
      
        btnBuscar.setActionCommand(Evento.BUSCAR_HISTORIAL_CLI); 
        panelFiltro.add(btnBuscar);

        panelFiltro.setMaximumSize(new Dimension(700, 40));
        panelFiltro.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(panelFiltro);
        add(Box.createVerticalStrut(20));

      
        String[] columnas = {"Producto", "Cantidad", "Total Pagado", "Fecha"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        tablaHistorial = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        scrollPane.setMaximumSize(new Dimension(850, 300));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(scrollPane);
        add(Box.createVerticalStrut(20));

        // --- BOTÓN VOLVER ---
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(evento);
        btnVolver.setActionCommand(Evento.VOLVER);
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(btnVolver);
    }

   
    public void cargarClientesEnCombo(List<Cliente> clientes) {
        this.listaClientesAux = clientes;
        comboClientes.removeAllItems();
        if (clientes != null) {
            for (Cliente c : clientes) {
             
                comboClientes.addItem(c.getNombre() + " - " + c.getNumeroIdentificacion());
            }
        }
    }

   
    public Cliente getClienteSeleccionado() {
        int index = comboClientes.getSelectedIndex();
        if (index >= 0 && listaClientesAux != null && index < listaClientesAux.size()) {
            return listaClientesAux.get(index);
        }
        return null;
    }

   
    public void actualizarTabla(List<CompasCliente> comprasFiltradas) {
        modeloTabla.setRowCount(0);
        if (comprasFiltradas != null) {
            for (CompasCliente c : comprasFiltradas) {
                String nombreProd = (c.getProducto() != null) ? c.getProducto().getNombre() : "Producto Eliminado";
                modeloTabla.addRow(new Object[]{
                    nombreProd,
                    c.getCantidad(),
                    c.getTotalCompra(),
                    c.getFecha() != null ? c.getFecha().toString() : "N/A"
                });
            }
        }
        tablaHistorial.revalidate();
        tablaHistorial.repaint();
    }
}