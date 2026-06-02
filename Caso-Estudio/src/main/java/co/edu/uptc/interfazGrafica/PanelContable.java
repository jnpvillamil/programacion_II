package co.edu.uptc.interfazGrafica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import co.edu.uptc.config.TiendaConfig;
import co.edu.uptc.modelo.AsientoContable;

public class PanelContable extends JPanel {
    
    private JTable tabla;
    private DefaultTableModel modelo;
    private Evento evento;
    private JTextField txtBuscar;
    
    public PanelContable(Evento evento) {
        this.evento = evento;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // ========== PANEL SUPERIOR  ==========
        JPanel panelNorte = new JPanel(new BorderLayout());
        JLabel lblTitulo = new JLabel("Gestión Contable");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelNorte.add(lblTitulo, BorderLayout.WEST);
        
        // ========== PANEL DE BOTONES DE REPORTES ==========
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBorder(BorderFactory.createTitledBorder("Reportes Contables"));
        
        JButton btnEstadoResultados = new JButton(" Estado de Resultados");
        btnEstadoResultados.addActionListener(e -> {
            if (evento != null) {
                evento.getVentana().generarReporteContable();
            }
        });
        
        JButton btnBalanceGeneral = new JButton(" Balance General");
        btnBalanceGeneral.addActionListener(e -> {
            if (evento != null) {
                evento.getVentana().generarBalanceGeneral();
            }
        });
        
        JButton btnVerDetalle = new JButton(" Ver Detalle");
        btnVerDetalle.addActionListener(e -> verDetalleAsiento());
        
        panelBotones.add(btnEstadoResultados);
        panelBotones.add(btnBalanceGeneral);
        panelBotones.add(btnVerDetalle);
        
        // ========== PANEL DE BÚSQUEDA ==========
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        txtBuscar = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> poblarTabla());
        
        panelBusqueda.add(new JLabel("Buscar por código:"));
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        
        JPanel panelNorteInterno = new JPanel(new BorderLayout());
        panelNorteInterno.add(panelBotones, BorderLayout.NORTH);
        panelNorteInterno.add(panelBusqueda, BorderLayout.SOUTH);
        
        panelNorte.add(panelNorteInterno, BorderLayout.CENTER);
        add(panelNorte, BorderLayout.NORTH);
        
        // ========== TABLA DE ASIENTOS ==========
        configurarTabla();
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowHeight(25);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Asientos Contables"));
        add(scrollPane, BorderLayout.CENTER);
        
        // ========== BOTÓN DE REFRESCAR ==========
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnRefrescar = new JButton(" Refrescar");
        btnRefrescar.addActionListener(e -> poblarTabla());
        panelSur.add(btnRefrescar);
        add(panelSur, BorderLayout.SOUTH);
        
        // Cargar datos iniciales
        poblarTabla();
    }
    
    public void configurarTabla() {
        modelo = new DefaultTableModel();
        modelo.addColumn("Código Asiento");
        modelo.addColumn("Fecha");
        modelo.addColumn("Descripción");
        modelo.addColumn("Referencia");
        modelo.addColumn("# Movimientos");
    }
    
    public void poblarTabla() {
        modelo.setRowCount(0);
        String filtro = txtBuscar.getText().trim();
        
        for (AsientoContable a : TiendaConfig.getInstancia()
                .getNegocioContable().listarAsientos()) {
            
            if (filtro.isEmpty() || a.getCodigoAsiento().contains(filtro)) {
                Object[] fila = {
                    a.getCodigoAsiento(),
                    a.getFecha().toString().split("T")[0],
                    a.getDescripcion(),
                    a.getReferencia(),
                    a.getMovimientos().size()
                };
                modelo.addRow(fila);
            }
        }
    }
    
    private void verDetalleAsiento() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione un asiento contable para ver su detalle");
            return;
        }
        
        String codigo = tabla.getValueAt(fila, 0).toString();
        AsientoContable asiento = null;
        
        for (AsientoContable a : TiendaConfig.getInstancia()
                .getNegocioContable().listarAsientos()) {
            if (a.getCodigoAsiento().equals(codigo)) {
                asiento = a;
                break;
            }
        }
        
        if (asiento != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("=== DETALLE DEL ASIENTO CONTABLE ===\n\n");
            sb.append("Código: ").append(asiento.getCodigoAsiento()).append("\n");
            sb.append("Fecha: ").append(asiento.getFecha()).append("\n");
            sb.append("Descripción: ").append(asiento.getDescripcion()).append("\n");
            sb.append("Referencia: ").append(asiento.getReferencia()).append("\n\n");
            sb.append("MOVIMIENTOS:\n");
            sb.append("Tipo    | Cuenta     |  Valor  |   Descripción\n");
            sb.append("----------------------------------------\n");
            
            for (var m : asiento.getMovimientos()) {
                sb.append(String.format("%s | %s | $%,.2f | %s\n", 
                    m.getTipoMovimiento().getDescripcion(),
                    m.getCuenta().getNombre(),
                    m.getValor(),
                    m.getDescripcion()));
            }
            
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            JScrollPane scroll = new JScrollPane(textArea);
            scroll.setPreferredSize(new Dimension(600, 400));
            JOptionPane.showMessageDialog(this, scroll, 
                "Detalle del Asiento", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}