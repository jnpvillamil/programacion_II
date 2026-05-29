package co.edu.uptc.sistienda.contabilidad.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import co.edu.uptc.sistienda.gui.Evento;
import co.edu.uptc.sistienda.modelo.Venta;

public class PanelContabilidad extends JPanel {

    private JLabel lblTotalIngresosVal;
    private JLabel lblTotalEgresosVal;
    private JLabel lblUtilidadBrutaVal;

    private JComboBox<String> cbCuentas;
    private JTextField txtFechaInicio;
    private JTextField txtFechaFin;
    private JButton btnConsultar;

    private CardLayout layoutContenido;
    private JPanel panelContenido;
    private JPanel panelListaTransacciones;
    private List<Venta> ventasGlobales;
    private JTextArea resultado;

    public PanelContabilidad() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel panelMenuIzquierdo = crearMenuContabilidadIzquierdo();
        layoutContenido = new CardLayout();
        panelContenido = new JPanel(layoutContenido);

        panelContenido.add(crearPanelMovimientos(), "MOVIMIENTOS");
        panelContenido.add(crearPanelReportes(), "REPORTES");
        panelContenido.add(crearPanelConsultas(), "CONSULTAS");

        add(panelContenido, BorderLayout.CENTER);

        add(panelMenuIzquierdo, BorderLayout.WEST);
        
    }

    private JPanel crearMenuContabilidadIzquierdo() {
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setPreferredSize(new Dimension(180, 0));
        menu.setBackground(new Color(245, 245, 245));
        menu.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));

        JLabel lblContabilidad = new JLabel("CONTABILIDAD");
        lblContabilidad.setFont(new Font("Arial", Font.BOLD, 11));
        lblContabilidad.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
        menu.add(lblContabilidad);

        JButton btnMovContables = new JButton("  Mov. Contables");
        btnMovContables.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        btnMovContables.setHorizontalAlignment(JButton.LEFT);
        btnMovContables.setBackground(new Color(230, 230, 230));
        btnMovContables.setBorderPainted(false);
        
        btnMovContables.setActionCommand(Evento.MENU_MOV_CONTABLES);
        btnMovContables.addActionListener(e -> {mostrarMovimientos();
        });
        menu.add(btnMovContables);

        JButton btnReportes = new JButton("  Reportes");
        btnReportes.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        btnReportes.setHorizontalAlignment(JButton.LEFT);
        btnReportes.setContentAreaFilled(false);
        btnReportes.setBorderPainted(false);
        
        btnReportes.setActionCommand(Evento.MENU_REPORTES);
        btnReportes.addActionListener(e -> {mostrarReportes();
        });
        
        menu.add(btnReportes);

        JLabel lblConsultasSec = new JLabel("CONSULTAS");
        lblConsultasSec.setFont(new Font("Arial", Font.BOLD, 11));
        lblConsultasSec.setBorder(BorderFactory.createEmptyBorder(20, 10, 5, 10));
        menu.add(lblConsultasSec);

        JButton btnConsultas = new JButton("  Consultas");
        btnConsultas.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        btnConsultas.setHorizontalAlignment(JButton.LEFT);
        btnConsultas.setContentAreaFilled(false);
        btnConsultas.setBorderPainted(false);
        
        btnConsultas.setActionCommand(Evento.MENU_CONSULTAS);
        btnConsultas.addActionListener(e -> {mostrarConsultas();
        });
        
        menu.add(btnConsultas);

        return menu;
    }

    private JPanel crearPanelMovimientos() {
        JPanel cuerpo = new JPanel(new BorderLayout());
        cuerpo.setBackground(Color.WHITE);
        cuerpo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblSubtitulo = new JLabel("Gestión Contable - Movimientos");
        lblSubtitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblSubtitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        cuerpo.add(lblSubtitulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBackground(Color.WHITE);

        JPanel panelTarjetas = new JPanel(new GridLayout(1, 3, 15, 0));
        panelTarjetas.setBackground(Color.WHITE);
        panelTarjetas.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        lblTotalIngresosVal = new JLabel("$0");
        lblTotalEgresosVal = new JLabel("$6.400.000"); 
        lblUtilidadBrutaVal = new JLabel("$0");

        panelTarjetas.add(crearTarjetaContable("TOTAL INGRESOS (MES)", lblTotalIngresosVal, "Feb 2026 Ventas"));
        panelTarjetas.add(crearTarjetaContable("TOTAL EGRESOS (MES)", lblTotalEgresosVal, "Feb 2026 Compras"));
        panelTarjetas.add(crearTarjetaContable("UTILIDAD BRUTA", lblUtilidadBrutaVal, "Ingresos - Egresos"));

        panelCentro.add(panelTarjetas);
        panelCentro.add(Box.createVerticalStrut(20));

        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelFiltros.setBackground(Color.WHITE);
        panelFiltros.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        panelFiltros.add(new JLabel("Cuenta:"));
        cbCuentas = new JComboBox<>(new String[]{"Todas las cuentas", "Caja / Bancos", "Clientes"});
        cbCuentas.setPreferredSize(new Dimension(160, 25));
        panelFiltros.add(cbCuentas);

        panelFiltros.add(new JLabel("  Periodo:"));
        txtFechaInicio = new JTextField("01/03/2026", 8);
        panelFiltros.add(txtFechaInicio);

        panelFiltros.add(new JLabel("al"));
        txtFechaFin = new JTextField("05/03/2026", 8);
        panelFiltros.add(txtFechaFin);

        btnConsultar = new JButton("Consultar");
        panelFiltros.add(btnConsultar);

        panelCentro.add(panelFiltros);
        panelCentro.add(Box.createVerticalStrut(20));

        panelListaTransacciones = new JPanel();
        panelListaTransacciones.setLayout(new BoxLayout(panelListaTransacciones, BoxLayout.Y_AXIS));
        panelListaTransacciones.setBackground(Color.WHITE);

        JScrollPane scrollTransacciones = new JScrollPane(panelListaTransacciones);
        scrollTransacciones.setBorder(BorderFactory.createEmptyBorder());
        scrollTransacciones.getVerticalScrollBar().setUnitIncrement(16);
        
        cuerpo.add(panelCentro, BorderLayout.NORTH);
        cuerpo.add(scrollTransacciones, BorderLayout.CENTER);

        JPanel panelPaginador = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
        panelPaginador.setBackground(Color.WHITE);
        panelPaginador.add(new JButton("<"));
        panelPaginador.add(new JLabel(" 14 / 16 "));
        panelPaginador.add(new JButton(">"));
        JComboBox<String> cbPaginas = new JComboBox<>(new String[]{"Páginas"});
        panelPaginador.add(cbPaginas);
        
        cuerpo.add(panelPaginador, BorderLayout.SOUTH);

        return cuerpo;
    }
    private JPanel crearPanelReportes() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("REPORTES CONTABLES");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JButton btnVentas = new JButton("Reporte de Ventas");
        JButton btnEgresos = new JButton("Reporte de Egresos");
        btnEgresos.addActionListener(e -> {

            if (ventasGlobales == null || ventasGlobales.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay datos");
                return;
            }

            double ingresos = 0;

            for (Venta v : ventasGlobales) {
                ingresos += v.getTotal();
            }

            double egresos = 6400000;
            double utilidad = ingresos - egresos;

            JOptionPane.showMessageDialog(this,
                    "Ingresos: $" + String.format("%,.0f", ingresos) + "\n" +
                    "Egresos: $" + String.format("%,.0f", egresos) + "\n" +
                    "Utilidad: $" + String.format("%,.0f", utilidad)
            );
        });
        

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBackground(Color.WHITE);

        btnVentas.setMaximumSize(new Dimension(200, 40));
        btnEgresos.setMaximumSize(new Dimension(200, 40));

        centro.add(btnVentas);
        centro.add(Box.createVerticalStrut(10));
        centro.add(btnEgresos);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(centro, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelConsultas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JTextField txtBuscar = new JTextField();
        JButton btnBuscar = new JButton("Buscar");

        JTextArea resultado = new JTextArea();
        resultado.setEditable(false);

        btnBuscar.addActionListener(e -> {
            resultado.setText("Aquí irán resultados de consulta...");
        });

        JPanel top = new JPanel(new BorderLayout());
        top.add(txtBuscar, BorderLayout.CENTER);
        top.add(btnBuscar, BorderLayout.EAST);

        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(resultado), BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearTarjetaContable(String titulo, JLabel lblValor, String subTexto) {
        JPanel tarjeta = new JPanel(new BorderLayout(0, 2));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        JLabel lblTit = new JLabel(titulo);
        lblTit.setFont(new Font("Arial", Font.PLAIN, 11));
        lblTit.setForeground(Color.DARK_GRAY);

        lblValor.setFont(new Font("Arial", Font.BOLD, 15));
        lblValor.setForeground(Color.BLACK);

        JLabel lblSub = new JLabel(subTexto);
        lblSub.setFont(new Font("Arial", Font.ITALIC, 11));
        lblSub.setForeground(Color.GRAY);

        tarjeta.add(lblTit, BorderLayout.NORTH);
        tarjeta.add(lblValor, BorderLayout.CENTER);
        tarjeta.add(lblSub, BorderLayout.SOUTH);

        return tarjeta;
    }

    public void cargarVentas(List<Venta> ventas) {
    	this.ventasGlobales = ventas;
        panelListaTransacciones.removeAll();
        double totalIngresos = 0;
        
        for (Venta venta : ventas) {
            totalIngresos += venta.getTotal();
            String nombreCliente = venta.getCliente() != null 
                    ? venta.getCliente().getNombreCompletoORazonSocial() 
                    : "María López";

            JPanel panelBloqueTx = new JPanel(new BorderLayout());
            panelBloqueTx.setBackground(Color.WHITE);
            panelBloqueTx.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                    BorderFactory.createEmptyBorder(0, 0, 5, 0)
            ));
            panelBloqueTx.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

            JPanel panelHeaderTx = new JPanel(new BorderLayout());
            panelHeaderTx.setBackground(new Color(250, 250, 250));
            panelHeaderTx.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));

            JLabel lblTxnCode = new JLabel("TXN-20260305-" + venta.getNumeroFactura());
            lblTxnCode.setFont(new Font("Arial", Font.BOLD, 12));

            JLabel lblTipo = new JLabel(" INGRESO ", JLabel.CENTER);
            lblTipo.setFont(new Font("Arial", Font.BOLD, 11));
            lblTipo.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            JLabel lblMeta = new JLabel("05/03/2026   FAC-" + venta.getNumeroFactura() + "   " + nombreCliente);
            lblMeta.setFont(new Font("Arial", Font.PLAIN, 12));

            JPanel panelHeaderIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
            panelHeaderIzquierdo.setOpaque(false);
            panelHeaderIzquierdo.add(lblTxnCode);
            panelHeaderIzquierdo.add(lblTipo);

            panelHeaderTx.add(panelHeaderIzquierdo, BorderLayout.WEST);
            panelHeaderTx.add(lblMeta, BorderLayout.EAST);
            panelBloqueTx.add(panelHeaderTx, BorderLayout.NORTH);

            JPanel panelCuerpoAsiento = new JPanel(new GridLayout(3, 1, 2, 2));
            panelCuerpoAsiento.setBackground(Color.WHITE);
            panelCuerpoAsiento.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));

            double total = venta.getTotal();
            double ivaGenerado = total * 0.19 / 1.19; 
            double ingresosVentas = total - ivaGenerado;

            panelCuerpoAsiento.add(crearFilaAsiento("DÉBITO", "Caja / Bancos", total));
            panelCuerpoAsiento.add(crearFilaAsiento("CRÉDITO", "Ingresos por Ventas", ingresosVentas));
            panelCuerpoAsiento.add(crearFilaAsiento("CRÉDITO", "IVA Generado", ivaGenerado));

            panelBloqueTx.add(panelCuerpoAsiento, BorderLayout.CENTER);

            panelListaTransacciones.add(panelBloqueTx);
            panelListaTransacciones.add(javax.swing.Box.createVerticalStrut(15));
        }

        lblTotalIngresosVal.setText(String.format("$%,.0f", totalIngresos));
        
        double totalEgresos = 6400000; 
        double utilidadBruta = totalIngresos - totalEgresos;
        
        lblUtilidadBrutaVal.setText(String.format("$%,.0f", utilidadBruta));

        panelListaTransacciones.revalidate();
        panelListaTransacciones.repaint();
    }

    private JPanel crearFilaAsiento(String tipo, String cuenta, double valor) {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(Color.WHITE);

        JLabel lblTipo = new JLabel(tipo);
        lblTipo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTipo.setPreferredSize(new Dimension(80, 20));

        JLabel lblCuenta = new JLabel(cuenta);
        lblCuenta.setFont(new Font("Arial", Font.PLAIN, 12));
        lblCuenta.setForeground(Color.DARK_GRAY);

        JLabel lblValor = new JLabel(String.format("$%,.0f", valor));
        lblValor.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel subIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        subIzquierdo.setOpaque(false);
        subIzquierdo.add(lblTipo);
        subIzquierdo.add(lblCuenta);

        fila.add(subIzquierdo, BorderLayout.WEST);
        fila.add(lblValor, BorderLayout.EAST);

        return fila;
    }
    public void mostrarMovimientos() {
        layoutContenido.show(panelContenido, "MOVIMIENTOS");
    }

    public void mostrarReportes() {
        layoutContenido.show(panelContenido, "REPORTES");
    }

    public void mostrarConsultas() {
        layoutContenido.show(panelContenido, "CONSULTAS");
    }
}