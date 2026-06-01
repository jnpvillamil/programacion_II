package co.edu.uptc.tiendaminorista.gui.administrador;

import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.tiendaminorista.gui.Evento;
import co.edu.uptc.tiendaminorista.modelo.Cliente;

public class PanelCliente extends JPanel {

    private JTextField Motolcli;
    private JTable tabla;
    private DefaultTableModel modelo;
    private Evento evento;

    public PanelCliente(Evento e) {
        this.evento = e;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Gestión Cliente");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titulo);

        add(Box.createVerticalStrut(20));

        JPanel panelBotones = new JPanel();
        panelBotones.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnRegistrar = new JButton("Registrar cliente");
        btnRegistrar.addActionListener(evento);
        btnRegistrar.setActionCommand(Evento.REGISTRARCLIENTE); 
        panelBotones.add(btnRegistrar);

        JButton btnModificar = new JButton("Modificar cliente");
        btnModificar.addActionListener(evento);
        btnModificar.setActionCommand(Evento.MODIFICARCLIENTE); 
        panelBotones.add(btnModificar);

        JButton btnHistorial = new JButton("Historial de compra cliente");
        btnHistorial.addActionListener(evento);
        btnHistorial.setActionCommand(Evento.HISTORIALCLIENTE);
        panelBotones.add(btnHistorial);

        JButton btnComprasCliente = new JButton("Compras cliente");
        btnComprasCliente.addActionListener(evento);
        btnComprasCliente.setActionCommand(Evento.COMPRASCLI); 
        panelBotones.add(btnComprasCliente);

        add(panelBotones);

        add(Box.createVerticalStrut(10));

        JPanel motor = new JPanel();
        motor.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel motorcli = new JLabel("Motor de búsqueda cliente ");
        motor.add(motorcli);

        Motolcli = new JTextField(20);
        motor.add(Motolcli);
        add(motor);

        add(Box.createVerticalStrut(20));

        String[] columnas = {"Nombre", "Tipo de documento", "Número de documento", "Tipo de cliente", "Estado"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setMaximumSize(new Dimension(850, 350));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(scrollPane);

        Motolcli.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) { ejecutarFiltro(); }

            @Override
            public void removeUpdate(DocumentEvent de) { ejecutarFiltro(); }

            @Override
            public void changedUpdate(DocumentEvent de) { ejecutarFiltro(); }

            private void ejecutarFiltro() {
                SwingUtilities.invokeLater(() -> {
                    String texto = Motolcli.getText();
                    if (evento != null && evento.getVentana() != null) {
                        evento.getVentana().filtrarClientes(texto);
                    }
                });
            }
        });
    }

    public void cargarClientes(List<Cliente> clientes) {
        modelo.setRowCount(0); 
        if (clientes != null) {
            for (Cliente cliente : clientes) {
                modelo.addRow(new Object[]{
                    cliente.getNombre(),
                    cliente.getTipodoc() != null ? cliente.getTipodoc().name() : "",
                    cliente.getNumeroIdentificacion(),
                    cliente.getTipoCliente(),
                    cliente.isActivo() ? "ACTIVO" : "INACTIVO"
                });
            }
        }
        tabla.revalidate();
        tabla.repaint();
    }

    public String getTextoBusqueda() {
        return Motolcli.getText();
    }

    public void limpiarBuscador() {
        Motolcli.setText("");
    }
}