package co.uptc.edu.tienda.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import co.uptc.edu.tienda.modelo.ClienteVolumen;
import co.uptc.edu.tienda.modelo.ProductoMasVendido;
import co.uptc.edu.tienda.modelo.ResumenFinanciero;
import co.uptc.edu.tienda.modelo.VentaPorFormaPago;

public class PanelReportes extends JPanel {

    private JTextField txtDesde;
    private JTextField txtHasta;
    private JButton btnGenerar;
    private JTextArea areaResultado;

    public PanelReportes(Evento evento) {

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 240));

        // =====================================
        // TITULO
        // =====================================
        JLabel titulo = new JLabel("REPORTES FINANCIEROS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        // =====================================
        // PANEL FILTRO FECHAS
        // =====================================
        JPanel filtro = new JPanel();
        filtro.setBorder(BorderFactory.createTitledBorder("Periodo del reporte"));
        filtro.setBackground(new Color(240, 240, 240));

        txtDesde = new JTextField(10);
        txtHasta = new JTextField(10);
        txtDesde.setToolTipText("yyyy-MM-dd");
        txtHasta.setToolTipText("yyyy-MM-dd");

        // Atajos rápidos
        JButton btnHoy = new JButton("Hoy");
        JButton btnEsteMes = new JButton("Este mes");
        JButton btnEsteAnio = new JButton("Este año");

        btnHoy.addActionListener(e -> {
            String hoy = LocalDate.now().toString();
            txtDesde.setText(hoy);
            txtHasta.setText(hoy);
        });

        btnEsteMes.addActionListener(e -> {
            LocalDate hoy = LocalDate.now();
            txtDesde.setText(hoy.withDayOfMonth(1).toString());
            txtHasta.setText(hoy.withDayOfMonth(hoy.lengthOfMonth()).toString());
        });

        btnEsteAnio.addActionListener(e -> {
            int anio = LocalDate.now().getYear();
            txtDesde.setText(anio + "-01-01");
            txtHasta.setText(anio + "-12-31");
        });

        btnGenerar = new JButton("Generar Reporte");
        btnGenerar.setBackground(new Color(39, 174, 96));
        btnGenerar.setForeground(Color.WHITE);
        btnGenerar.setFont(new Font("Arial", Font.BOLD, 13));
        btnGenerar.setFocusPainted(false);
        btnGenerar.setActionCommand(Evento.GENERAR_REPORTE);
        btnGenerar.addActionListener(evento);

        filtro.add(new JLabel("Desde (yyyy-MM-dd):"));
        filtro.add(txtDesde);
        filtro.add(new JLabel("Hasta (yyyy-MM-dd):"));
        filtro.add(txtHasta);
        filtro.add(btnHoy);
        filtro.add(btnEsteMes);
        filtro.add(btnEsteAnio);
        filtro.add(btnGenerar);

        // =====================================
        // PANEL NORTE
        // =====================================
        JPanel norte = new JPanel(new BorderLayout());
        norte.setBackground(new Color(240, 240, 240));
        norte.add(titulo, BorderLayout.NORTH);
        norte.add(filtro, BorderLayout.SOUTH);
        add(norte, BorderLayout.NORTH);

        // =====================================
        // AREA DE RESULTADO
        // =====================================
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaResultado.setBackground(new Color(250, 250, 250));
        areaResultado.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(areaResultado);
        scroll.setBorder(BorderFactory.createTitledBorder("Resumen del periodo"));
        add(scroll, BorderLayout.CENTER);

        // =====================================
        // PANEL INFERIOR
        // =====================================
        JPanel inferior = new JPanel();
        inferior.setBackground(new Color(240, 240, 240));
        JLabel lblInfo = new JLabel("El reporte también se guarda automáticamente en JSON.");
        lblInfo.setForeground(new Color(127, 140, 141));
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 12));
        inferior.add(lblInfo);
        add(inferior, BorderLayout.SOUTH);
    }

    // =====================================
    // GETTERS PARA VentanaPrincipal
    // =====================================
    public String getDesde() {
        return txtDesde.getText().trim();
    }

    public String getHasta() {
        return txtHasta.getText().trim();
    }

    // =====================================
    // MOSTRAR RESUMEN EN PANTALLA
    // =====================================
    public void mostrarResumen(ResumenFinanciero resumen) {
        StringBuilder sb = new StringBuilder();

        sb.append("═══════════════════════════════════════════════════\n");
        sb.append("   REPORTE FINANCIERO\n");
        sb.append("   Periodo: ").append(resumen.getFechaInicio())
          .append(" → ").append(resumen.getFechaFin()).append("\n");
        sb.append("═══════════════════════════════════════════════════\n\n");

        sb.append("RESUMEN GENERAL\n");
        sb.append("───────────────────────────────────────────────────\n");
        sb.append(String.format("  Total ventas:       $%,.2f%n", resumen.getTotalVentas()));
        sb.append(String.format("  Total compras:      $%,.2f%n", resumen.getTotalCompras()));
        sb.append(String.format("  Utilidad bruta:     $%,.2f%n", resumen.getUtilidadBruta()));
        sb.append(String.format("  Inventario valor.:  $%,.2f%n", resumen.getInventarioValorizado()));
        sb.append("\n");

        sb.append("RESUMEN CONTABLE\n");
        sb.append("───────────────────────────────────────────────────\n");
        if (resumen.getResumenContable() != null) {
            sb.append(String.format("  Ingresos:           $%,.2f%n", resumen.getResumenContable().getIngresos()));
            sb.append(String.format("  Egresos:            $%,.2f%n", resumen.getResumenContable().getEgresos()));
            sb.append(String.format("  Utilidad:           $%,.2f%n", resumen.getResumenContable().getUtilidad()));
            sb.append(String.format("  IVA generado:       $%,.2f%n", resumen.getResumenContable().getIvaGenerado()));
            sb.append(String.format("  IVA descontable:    $%,.2f%n", resumen.getResumenContable().getIvaDescontable()));
        }
        sb.append("\n");

        sb.append("VENTAS POR FORMA DE PAGO\n");
        sb.append("───────────────────────────────────────────────────\n");
        if (resumen.getVentasPorFormaPago() != null) {
            for (VentaPorFormaPago v : resumen.getVentasPorFormaPago()) {
                sb.append(String.format("  %-20s $%,.2f%n", v.getTipo(), v.getValor()));
            }
        }
        sb.append("\n");

        sb.append("PRODUCTOS MÁS VENDIDOS\n");
        sb.append("───────────────────────────────────────────────────\n");
        if (resumen.getProductosMasVendidos() != null) {
            int pos = 1;
            for (ProductoMasVendido p : resumen.getProductosMasVendidos()) {
                sb.append(String.format("  %d. %-20s %d unidades%n",
                        pos++, p.getNombre(), p.getCantidadVendida()));
            }
        }
        sb.append("\n");

        sb.append("CLIENTES CON MAYOR VOLUMEN\n");
        sb.append("───────────────────────────────────────────────────\n");
        if (resumen.getClientesMayorVolumen() != null) {
            int pos = 1;
            for (ClienteVolumen c : resumen.getClientesMayorVolumen()) {
                sb.append(String.format("  %d. %-20s $%,.2f%n",
                        pos++, c.getNombre(), c.getTotalComprado()));
            }
        }
        sb.append("\n═══════════════════════════════════════════════════\n");

        areaResultado.setText(sb.toString());
        areaResultado.setCaretPosition(0);
    }
}