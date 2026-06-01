package co.edu.uptc.tiendaminorista.gui.administrador;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import co.edu.uptc.tiendaminorista.modelo.CompasCliente;
import co.edu.uptc.tiendaminorista.modelo.Producto;
import co.edu.uptc.tiendaminorista.negocio.GestionCompasCliente;
import co.edu.uptc.tiendaminorista.negocio.GestionProducto;
import co.edu.uptc.tiendaminorista.negocio.GestionContable;
import co.edu.uptc.tiendaminorista.persistencia.LocalContable;

public class PanelReportes extends JPanel {
    
    private JTextArea areaResultado;
    private JComboBox<String> comboAnio, comboMes;
    
    private GestionCompasCliente gestionCompras;
    private GestionProducto gestionProducto;
    private GestionContable gestionContable;
    
    public PanelReportes() {
        gestionCompras = new GestionCompasCliente();
        gestionProducto = new GestionProducto(new co.edu.uptc.tiendaminorista.persistencia.LocalProducto());
        gestionContable = new GestionContable(new LocalContable());
        
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JPanel panelSelector = crearPanelSelector();
        add(panelSelector, BorderLayout.NORTH);
        
        JPanel panelBotones = crearPanelBotones();
        add(panelBotones, BorderLayout.CENTER);
        
        JPanel panelResultado = crearPanelResultado();
        add(panelResultado, BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelSelector() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Seleccionar Periodo"));
        
        comboAnio = new JComboBox<>();
        int anioActual = LocalDate.now().getYear();
        for (int i = anioActual - 2; i <= anioActual; i++) {
            comboAnio.addItem(String.valueOf(i));
        }
        comboAnio.setSelectedItem(String.valueOf(anioActual));
        comboAnio.setPreferredSize(new Dimension(100, 30));
        
        comboMes = new JComboBox<>();
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
                          "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        for (String mes : meses) {
            comboMes.addItem(mes);
        }
        comboMes.setSelectedIndex(LocalDate.now().getMonthValue() - 1);
        comboMes.setPreferredSize(new Dimension(130, 30));
        
        panel.add(new JLabel("Año:"));
        panel.add(comboAnio);
        panel.add(Box.createHorizontalStrut(30));
        panel.add(new JLabel("Mes:"));
        panel.add(comboMes);
        
        return panel;
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Reportes"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        String[][] botones = {
            {"Total de ventas diarias, mensuales y anuales", "ventas_totales"},
            {"Utilidad bruta (ventas – costo de ventas)", "utilidad"},
            {"Productos más vendidos", "productos"},
            {"Clientes con mayor volumen de compra", "clientes"},
            {"Estado de inventario valorizado", "inventario"},
            {"Resumen contable por periodo", "contable"}
        };
        
        for (int i = 0; i < botones.length; i++) {
            JButton btn = new JButton(botones[i][0]);
            btn.setFont(new Font("Arial", Font.PLAIN, 12));
            btn.setPreferredSize(new Dimension(350, 45));
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            final String tipo = botones[i][1];
            btn.addActionListener(e -> generarReporte(tipo));
            
            gbc.gridx = i % 2;
            gbc.gridy = i / 2;
            panel.add(btn, gbc);
        }
        
        return panel;
    }
    
    private JPanel crearPanelResultado() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Resultado (JSON)"));
        
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaResultado.setBackground(new Color(250, 250, 250));
        areaResultado.setRows(20);
        
        JScrollPane scroll = new JScrollPane(areaResultado);
        scroll.setPreferredSize(new Dimension(0, 350));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scroll, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void generarReporte(String tipo) {
        int anio = Integer.parseInt((String) comboAnio.getSelectedItem());
        int mes = comboMes.getSelectedIndex() + 1;
        
        Map<String, Object> resultado = new LinkedHashMap<>();
        resultado.put("fecha_generacion", LocalDate.now().toString());
        resultado.put("periodo", anio + "-" + String.format("%02d", mes));
        
        switch (tipo) {
            case "ventas_totales":
                resultado.put("total_ventas_diarias", calcularVentasDiarias(anio, mes));
                resultado.put("total_ventas_mensuales", calcularTotalVentas(anio, mes));
                resultado.put("total_ventas_anuales", calcularTotalVentasAnuales(anio));
                break;
            case "utilidad":
                double[] utilidad = calcularUtilidad(anio, mes);
                resultado.put("total_ventas", utilidad[0]);
                resultado.put("costo_de_ventas", utilidad[1]);
                resultado.put("utilidad_bruta", utilidad[2]);
                resultado.put("margen_utilidad", utilidad[0] > 0 ? Math.round((utilidad[2]/utilidad[0])*10000)/100.0 + "%" : "0%");
                break;
            case "productos":
                resultado.put("productos_mas_vendidos", calcularProductosMasVendidos(anio, mes));
                break;
            case "clientes":
                resultado.put("clientes_mayor_volumen", calcularClientesMayorVolumen(anio, mes));
                break;
            case "inventario":
                resultado.put("estado_inventario_valorizado", calcularInventario());
                break;
            case "contable":
                resultado.put("resumen_contable", calcularResumenContable());
                break;
        }
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        areaResultado.setText(gson.toJson(resultado));
    }
    
    //MetodosDeCalculo
    private Map<Integer, Double> calcularVentasDiarias(int anio, int mes) {
        List<CompasCliente> compras = gestionCompras.listarTodasLasCompras();
        Map<Integer, Double> ventasPorDia = new TreeMap<>();
        for (CompasCliente c : compras) {
            LocalDate fecha = c.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (fecha.getYear() == anio && fecha.getMonthValue() == mes) {
                int dia = fecha.getDayOfMonth();
                ventasPorDia.put(dia, ventasPorDia.getOrDefault(dia, 0.0) + c.getTotalCompra());
            }
        }
        return ventasPorDia;
    }
    
    private double calcularTotalVentas(int anio, int mes) {
        List<CompasCliente> compras = gestionCompras.listarTodasLasCompras();
        double total = 0;
        for (CompasCliente c : compras) {
            LocalDate fecha = c.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (fecha.getYear() == anio && fecha.getMonthValue() == mes) {
                total += c.getTotalCompra();
            }
        }
        return total;
    }
    
    private double calcularTotalVentasAnuales(int anio) {
        List<CompasCliente> compras = gestionCompras.listarTodasLasCompras();
        double total = 0;
        for (CompasCliente c : compras) {
            LocalDate fecha = c.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (fecha.getYear() == anio) {
                total += c.getTotalCompra();
            }
        }
        return total;
    }
    
    private double[] calcularUtilidad(int anio, int mes) {
        List<CompasCliente> compras = gestionCompras.listarTodasLasCompras();
        double totalVentas = 0;
        double totalCosto = 0;
        for (CompasCliente c : compras) {
            LocalDate fecha = c.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (fecha.getYear() == anio && fecha.getMonthValue() == mes) {
                totalVentas += c.getTotalCompra();
                totalCosto += c.getCantidad() * c.getProducto().getPrecioCompra();
            }
        }
        return new double[]{totalVentas, totalCosto, totalVentas - totalCosto};
    }
    
    private Map<String, Object> calcularInventario() {
        List<Producto> productos = gestionProducto.listarProductos();
        double valorTotal = 0;
        List<Map<String, Object>> detalle = new ArrayList<>();
        for (Producto p : productos) {
            if (p.isActivo()) {
                double valor = p.getStockActual() * p.getPrecioVenta();
                valorTotal += valor;
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("codigo", p.getCodigo());
                item.put("nombre", p.getNombre());
                item.put("stock", p.getStockActual());
                item.put("precio_venta", p.getPrecioVenta());
                item.put("valor_total", valor);
                detalle.add(item);
            }
        }
        Map<String, Object> inventario = new LinkedHashMap<>();
        inventario.put("valor_total_inventario", valorTotal);
        inventario.put("total_productos_activos", productos.stream().filter(Producto::isActivo).count());
        inventario.put("detalle", detalle);
        return inventario;
    }
    
    private Map<String, Object> calcularResumenContable() {
        Map<String, Object> resumen = new LinkedHashMap<>();
        double ingresos = gestionContable.getTotalIngresos();
        double egresos = gestionContable.getTotalEgresos();
        resumen.put("ingresos", ingresos);
        resumen.put("egresos", egresos);
        resumen.put("utilidad_neta", ingresos - egresos);
        resumen.put("iva_generado", ingresos * 0.19);
        resumen.put("iva_descontable", egresos * 0.19);
        return resumen;
    }
    
    private List<Map<String, Object>> calcularProductosMasVendidos(int anio, int mes) {
        List<CompasCliente> compras = gestionCompras.listarTodasLasCompras();
        Map<String, Integer> cantidadPorProducto = new HashMap<>();
        Map<String, String> nombrePorCodigo = new HashMap<>();
        
        for (CompasCliente c : compras) {
            LocalDate fecha = c.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (fecha.getYear() == anio && fecha.getMonthValue() == mes) {
                String codigo = c.getProducto().getCodigo();
                cantidadPorProducto.put(codigo, cantidadPorProducto.getOrDefault(codigo, 0) + c.getCantidad());
                nombrePorCodigo.put(codigo, c.getProducto().getNombre());
            }
        }
        
        List<Map.Entry<String, Integer>> lista = new ArrayList<>(cantidadPorProducto.entrySet());
        lista.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        
        List<Map<String, Object>> top = new ArrayList<>();
        for (int i = 0; i < Math.min(5, lista.size()); i++) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("codigo", lista.get(i).getKey());
            item.put("nombre", nombrePorCodigo.get(lista.get(i).getKey()));
            item.put("cantidad_vendida", lista.get(i).getValue());
            top.add(item);
        }
        return top;
    }
    
    private List<Map<String, Object>> calcularClientesMayorVolumen(int anio, int mes) {
        List<CompasCliente> compras = gestionCompras.listarTodasLasCompras();
        Map<String, Double> totalPorCliente = new HashMap<>();
        Map<String, String> nombrePorCliente = new HashMap<>();
        
        for (CompasCliente c : compras) {
            LocalDate fecha = c.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (fecha.getYear() == anio && fecha.getMonthValue() == mes) {
                String cedula = c.getCliente().getNumeroIdentificacion();
                totalPorCliente.put(cedula, totalPorCliente.getOrDefault(cedula, 0.0) + c.getTotalCompra());
                nombrePorCliente.put(cedula, c.getCliente().getNombre());
            }
        }
        
        List<Map.Entry<String, Double>> lista = new ArrayList<>(totalPorCliente.entrySet());
        lista.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        
        List<Map<String, Object>> top = new ArrayList<>();
        for (int i = 0; i < Math.min(5, lista.size()); i++) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("identificacion", lista.get(i).getKey());
            item.put("nombre", nombrePorCliente.get(lista.get(i).getKey()));
            item.put("total_compras", lista.get(i).getValue());
            top.add(item);
        }
        return top;
    }
}