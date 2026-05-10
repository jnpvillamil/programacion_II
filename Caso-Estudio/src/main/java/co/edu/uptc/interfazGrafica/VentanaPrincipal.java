package co.edu.uptc.interfazGrafica;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;       
import java.util.Map; 
import co.edu.uptc.Util.LogUtil;
import co.edu.uptc.Util.JSOnExportador;  
import co.edu.uptc.config.TiendaConfig;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.modelo.DetalleCompra;
import co.edu.uptc.modelo.AsientoContable;
import co.edu.uptc.enums.CuentaContable; 

public class VentanaPrincipal extends JFrame {
    
    private Evento evento;
    private CardLayout cardLayout;
    private JPanel panelContenedor;
    private JPanel panelToolbar;
    private JButton btnProductos, btnClientes, btnProveedores, btnVentas, btnCompras, btnContabilidad, btnReportes, btnSalir;
    
    // Paneles
    private PanelLogin panelLogin;
    private PanelProducto panelProducto;
    private PanelCliente panelCliente;
    private PanelProveedor panelProveedor;
    private PanelVenta panelVenta;
    private PanelCompra panelCompra;
    private PanelContable panelContable;
    
    public VentanaPrincipal() {
        evento = new Evento(this);
        setTitle("Sistema de Gestión Comercial y Contable");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        setLayout(new BorderLayout());
        
    
        crearToolbar();
        
       
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        
        inicializarPaneles();
        
        add(panelToolbar, BorderLayout.NORTH);
        add(panelContenedor, BorderLayout.CENTER);
        
        mostrarPanelLogin();
        panelToolbar.setVisible(false);
    }
    
    private void crearToolbar() {
        panelToolbar = new JPanel();
        panelToolbar.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelToolbar.setBackground(new Color(240, 240, 240));
        panelToolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        
        btnProductos = new JButton("Productos");
        btnClientes = new JButton("Clientes");
        btnProveedores = new JButton("Proveedores");
        btnVentas = new JButton("Ventas");
        btnCompras = new JButton("Compras");
        btnContabilidad = new JButton("Contabilidad");
        btnReportes = new JButton("Reportes");
        btnSalir = new JButton("Salir");
        
        
        btnProductos.setActionCommand(Evento.MOSTRAR_PRODUCTOS);
        btnClientes.setActionCommand(Evento.MOSTRAR_CLIENTES);
        btnProveedores.setActionCommand(Evento.MOSTRAR_PROVEEDORES);
        btnVentas.setActionCommand(Evento.MOSTRAR_VENTAS);
        btnCompras.setActionCommand(Evento.MOSTRAR_COMPRAS);
        btnContabilidad.setActionCommand(Evento.MOSTRAR_CONTABILIDAD);
        btnReportes.setActionCommand(Evento.MOSTRAR_REPORTES);
        btnSalir.setActionCommand(Evento.SALIR);
        
       
        btnProductos.addActionListener(evento);
        btnClientes.addActionListener(evento);
        btnProveedores.addActionListener(evento);
        btnVentas.addActionListener(evento);
        btnCompras.addActionListener(evento);
        btnContabilidad.addActionListener(evento);
        btnReportes.addActionListener(evento);
        btnSalir.addActionListener(evento);
        
        panelToolbar.add(btnProductos);
        panelToolbar.add(btnClientes);
        panelToolbar.add(btnProveedores);
        panelToolbar.add(btnVentas);
        panelToolbar.add(btnCompras);
        panelToolbar.add(btnContabilidad);
        panelToolbar.add(btnReportes);
        panelToolbar.add(Box.createHorizontalStrut(20));
        panelToolbar.add(btnSalir);
    }
    
    private void inicializarPaneles() {
        panelLogin = new PanelLogin(evento);
        panelProducto = new PanelProducto(evento);
        panelCliente = new PanelCliente(evento);
        panelProveedor = new PanelProveedor(evento);
        panelVenta = new PanelVenta(evento);
        panelCompra = new PanelCompra(evento);
        panelContable = new PanelContable(evento);
        
        panelContenedor.add(panelLogin, "login");
        panelContenedor.add(panelProducto, "producto");
        panelContenedor.add(panelCliente, "cliente");
        panelContenedor.add(panelProveedor, "proveedor");
        panelContenedor.add(panelVenta, "venta");
        panelContenedor.add(panelCompra, "compra");
        panelContenedor.add(panelContable, "contabilidad");
    }
    
    public void mostrarPanelLogin() {
        cardLayout.show(panelContenedor, "login");
    }
    
    public void login() {
        panelToolbar.setVisible(true);
        mostrarPanelProductos();
    }
    
    public void mostrarPanelProductos() {
        cardLayout.show(panelContenedor, "producto");
        panelProducto.poblarTabla();
    }
    
    public void mostrarPanelClientes() {
        cardLayout.show(panelContenedor, "cliente");
        panelCliente.poblarTabla();
    }
    
    public void mostrarPanelProveedores() {
        cardLayout.show(panelContenedor, "proveedor");
        panelProveedor.poblarTabla();
    }
    
    public void mostrarPanelVentas() {
        cardLayout.show(panelContenedor, "venta");
        panelVenta.poblarTabla();
    }
    
    public void mostrarPanelCompras() {
        cardLayout.show(panelContenedor, "compra");
        panelCompra.poblarTabla();
    }
    
    public void mostrarPanelContabilidad() {
        cardLayout.show(panelContenedor, "contabilidad");
        panelContable.poblarTabla();
    }
    
    public void mostrarPanelReportes() {
        JOptionPane.showMessageDialog(this, "Módulo de Reportes - Seleccione una opción del menú");
    }
    
    public void salir() {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que desea salir?", "Salir", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    public void mostrarPanelCentral() {
        mostrarPanelProductos();
    }
    
    // ========== MÉTODOS DE PRODUCTO ==========
    public void nuevoProducto() {
        DialogoProducto d = new DialogoProducto(this, true, null);
        d.setVisible(true);
        if(d.isOk()) {
            TiendaConfig.getInstancia().getGestionProducto().crear(d.getProducto());
            panelProducto.poblarTabla();
            
    
           LogUtil.registrarOperacion("admin", "CREAR_PRODUCTO", 
                "Código: " + d.getProducto().getCodigo() + ", Nombre: " + d.getProducto().getNombre());
            
            JOptionPane.showMessageDialog(this, "Producto creado exitosamente");
        }
    }
    
    public void actualizarProducto() {
        String codigo = panelProducto.getCodigoSeleccionado();
        if(codigo == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla");
            return;
        }
        Producto p = TiendaConfig.getInstancia().getGestionProducto().buscar(codigo);
        if(p != null) {
            DialogoProducto d = new DialogoProducto(this, false, p);
            d.setVisible(true);
            if(d.isOk()) {
                TiendaConfig.getInstancia().getGestionProducto().actualizar(d.getProducto());
                panelProducto.poblarTabla();
                JOptionPane.showMessageDialog(this, "Producto actualizado");
            }
        }
    }
    
    public void eliminarProducto() {
        String codigo = panelProducto.getCodigoSeleccionado();
        if(codigo == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Inactivar producto?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION) {
            TiendaConfig.getInstancia().getGestionProducto().eliminar(codigo);
            panelProducto.poblarTabla();
            JOptionPane.showMessageDialog(this, "Producto inactivado");
        }
    }
    
    public void buscarProducto() {
        panelProducto.realizarBusqueda();
    }
    
    public void limpiarProductos() {
        panelProducto.limpiarBusqueda();
        panelProducto.poblarTabla();
    }
    
    // ========== MÉTODOS DE CLIENTE ==========
    public void nuevoCliente() {
        DialogoCliente d = new DialogoCliente(this, true, null);
        d.setVisible(true);
        if(d.isOk()) {
            TiendaConfig.getInstancia().getGestionCliente().crear(d.getCliente());
            panelCliente.poblarTabla();
            JOptionPane.showMessageDialog(this, "Cliente creado exitosamente");
        }
    }
    
    public void actualizarCliente() {
        String codigo = panelCliente.getCodigoSeleccionado();
        if(codigo == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla");
            return;
        }
        Cliente c = TiendaConfig.getInstancia().getGestionCliente().buscar(codigo);
        if(c != null) {
            DialogoCliente d = new DialogoCliente(this, false, c);
            d.setVisible(true);
            if(d.isOk()) {
                TiendaConfig.getInstancia().getGestionCliente().actualizar(d.getCliente());
                panelCliente.poblarTabla();
                JOptionPane.showMessageDialog(this, "Cliente actualizado");
            }
        }
    }
    
    public void eliminarCliente() {
        String codigo = panelCliente.getCodigoSeleccionado();
        if(codigo == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Inactivar cliente?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION) {
            TiendaConfig.getInstancia().getGestionCliente().eliminar(codigo);
            panelCliente.poblarTabla();
            JOptionPane.showMessageDialog(this, "Cliente inactivado");
        }
    }
    
    public void buscarCliente() {
        panelCliente.realizarBusqueda();
    }
    
    public void limpiarClientes() {
        panelCliente.limpiarBusqueda();
        panelCliente.poblarTabla();
    }
    
    // ========== MÉTODOS DE PROVEEDOR ==========
    public void nuevoProveedor() {
        DialogoProveedor d = new DialogoProveedor(this, true, null);
        d.setVisible(true);
        if(d.isOk()) {
            TiendaConfig.getInstancia().getGestionProveedor().crear(d.getProveedor());
            panelProveedor.poblarTabla();
            JOptionPane.showMessageDialog(this, "Proveedor creado exitosamente");
        }
    }
    
    public void actualizarProveedor() {
        String codigo = panelProveedor.getCodigoSeleccionado();
        if(codigo == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor de la tabla");
            return;
        }
        Proveedor p = TiendaConfig.getInstancia().getGestionProveedor().buscar(codigo);
        if(p != null) {
            DialogoProveedor d = new DialogoProveedor(this, false, p);
            d.setVisible(true);
            if(d.isOk()) {
                TiendaConfig.getInstancia().getGestionProveedor().actualizar(d.getProveedor());
                panelProveedor.poblarTabla();
                JOptionPane.showMessageDialog(this, "Proveedor actualizado");
            }
        }
    }
    
    public void eliminarProveedor() {
        String codigo = panelProveedor.getCodigoSeleccionado();
        if(codigo == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor de la tabla");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Inactivar proveedor?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION) {
            TiendaConfig.getInstancia().getGestionProveedor().eliminar(codigo);
            panelProveedor.poblarTabla();
            JOptionPane.showMessageDialog(this, "Proveedor inactivado");
        }
    }
    
    public void buscarProveedor() {
        panelProveedor.realizarBusqueda();
    }
    
    public void limpiarProveedores() {
        panelProveedor.limpiarBusqueda();
        panelProveedor.poblarTabla();
    }
    
 // ========== MÉTODOS DE VENTA ==========
    public void nuevaVenta() {
        DialogoVenta d = new DialogoVenta(this);
        d.setVisible(true);
        if(d.isOk()) {
            Venta venta = d.getVenta();
            TiendaConfig.getInstancia().getGestionVenta().crearVenta(venta);
            TiendaConfig.getInstancia().getGestionContable().registrarAsientoVenta(venta);
            panelVenta.poblarTabla();
            panelProducto.poblarTabla();
            
            // Agregar log
            LogUtil.registrarOperacion("admin", "REGISTRAR_VENTA", 
                "Factura: " + venta.getNumeroFactura() + ", Total: $" + venta.getTotal());
            
            JOptionPane.showMessageDialog(this, "Venta registrada exitosamente");
        }
    }
    
    public void anularVenta() {
        String factura = panelVenta.getCodigoSeleccionado();
        if(factura == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una venta");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "¿Anular esta venta? Se devolverá el stock", 
            "Confirmar", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION) {
            TiendaConfig.getInstancia().getGestionVenta().anularVenta(factura);
            panelVenta.poblarTabla();
            JOptionPane.showMessageDialog(this, "Venta anulada");
        }
    }

    public void consultarVenta() {
        String factura = panelVenta.getCodigoSeleccionado();
        if(factura == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una venta");
            return;
        }
        
        Venta v = TiendaConfig.getInstancia().getGestionVenta().buscarVenta(factura);
        if(v != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("=== DETALLE DE VENTA ===\n");
            sb.append("Factura: ").append(v.getNumeroFactura()).append("\n");
            sb.append("Fecha: ").append(v.getFechaHora()).append("\n");
            sb.append("Cliente: ").append(v.getCliente().getNombre()).append("\n");
            sb.append("Forma Pago: ").append(v.getFormaPago()).append("\n");
            sb.append("\n--- Productos ---\n");
            for(DetalleVenta d : v.getDetalles()) {
                sb.append(d.getProducto().getNombre())
                  .append(" x").append(d.getCantidad())
                  .append(" = $").append(d.getSubtotal()).append("\n");
            }
            sb.append("\nSubtotal: $").append(v.getSubtotal());
            sb.append("\nIVA: $").append(v.getIva());
            sb.append("\nTOTAL: $").append(v.getTotal());
            
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            JScrollPane scroll = new JScrollPane(textArea);
            scroll.setPreferredSize(new Dimension(400, 300));
            JOptionPane.showMessageDialog(this, scroll, "Detalle de Venta", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public void limpiarVentas() {
        panelVenta.limpiarBusqueda();
        panelVenta.poblarTabla();
    }
    
   
 // ========== MÉTODOS DE COMPRA ==========
    
    public void nuevaCompra() {
        DialogoCompra d = new DialogoCompra(this);
        d.setVisible(true);
        if(d.isOk()) {
            Compra compra = d.getCompra();
            TiendaConfig.getInstancia().getGestionCompra().crearCompra(compra);
            
            
            TiendaConfig.getInstancia().getGestionContable().registrarAsientoCompra(compra);
            
            panelCompra.poblarTabla();
            panelProducto.poblarTabla();
            JOptionPane.showMessageDialog(this, "Compra registrada exitosamente");
        }
    }

public void anularCompra() {
    String factura = panelCompra.getCodigoSeleccionado();
    if(factura == null) {
        JOptionPane.showMessageDialog(this, "Seleccione una compra");
        return;
    }
    
    int confirm = JOptionPane.showConfirmDialog(this, 
        "¿Anular esta compra? Se devolverá el stock y se ajustarán los precios", 
        "Confirmar", JOptionPane.YES_NO_OPTION);
    
    if(confirm == JOptionPane.YES_OPTION) {
        TiendaConfig.getInstancia().getGestionCompra().anularCompra(factura);
        panelCompra.poblarTabla();
        panelProducto.poblarTabla();  
        JOptionPane.showMessageDialog(this, "Compra anulada");
    }
}

public void consultarCompra() {
    String factura = panelCompra.getCodigoSeleccionado();
    if(factura == null) {
        JOptionPane.showMessageDialog(this, "Seleccione una compra");
        return;
    }
    
    Compra c = TiendaConfig.getInstancia().getGestionCompra().buscarCompra(factura);
    if(c != null) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== DETALLE DE COMPRA ===\n");
        sb.append("Factura Proveedor: ").append(c.getNumeroFacturaProveedor()).append("\n");
        sb.append("Fecha: ").append(c.getFechaHora()).append("\n");
        sb.append("Proveedor: ").append(c.getProveedor().getRazonSocial()).append("\n");
        sb.append("\n--- Productos ---\n");
        for(DetalleCompra d : c.getDetalles()) {
            sb.append(d.getProducto().getNombre())
              .append(" x").append(d.getCantidad())
              .append(" = $").append(d.getSubtotal()).append("\n");
        }
        sb.append("\nSubtotal: $").append(c.getSubtotal());
        sb.append("\nIVA: $").append(c.getIva());
        sb.append("\nTOTAL: $").append(c.getTotal());
        
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(this, scroll, "Detalle de Compra", JOptionPane.INFORMATION_MESSAGE);
    }
}

public void limpiarCompras() {
    panelCompra.limpiarBusqueda();
    panelCompra.poblarTabla();
}

// ========== REPORTES ==========
public void generarReporteVentas() {
    JOptionPane.showMessageDialog(this, "Reporte de Ventas en desarrollo");
}

public void generarReporteProductosMasComprados() {
    var productosMasComprados = TiendaConfig.getInstancia()
        .getGestionCompra().obtenerProductosMasComprados();
    
    if(productosMasComprados.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No hay compras registradas");
        return;
    }
    
    StringBuilder sb = new StringBuilder();
    sb.append("=== TOP 10 PRODUCTOS MÁS COMPRADOS ===\n\n");
    sb.append("Código | Producto | Cantidad\n");
    sb.append("-----------------------------------\n");
    
    for(Object[] item : productosMasComprados) {
        sb.append(item[0]).append(" | ")
          .append(item[1]).append(" | ")
          .append(item[2]).append("\n");
    }
    
    JTextArea textArea = new JTextArea(sb.toString());
    textArea.setEditable(false);
    JScrollPane scroll = new JScrollPane(textArea);
    scroll.setPreferredSize(new Dimension(500, 400));
    JOptionPane.showMessageDialog(this, scroll, "Productos Más Comprados", 
        JOptionPane.INFORMATION_MESSAGE);
}
public void generarReporteInventario() {
    var bajoStock = TiendaConfig.getInstancia().getGestionProducto().listarPorStockMinimo();
    if(bajoStock.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No hay productos con stock bajo");
        return;
    }
    StringBuilder msg = new StringBuilder("PRODUCTOS CON STOCK BAJO:\n\n");
    for(Producto p : bajoStock) {
        msg.append(p.getCodigo()).append(" - ").append(p.getNombre())
           .append(" | Stock: ").append(p.getStockActual())
           .append(" | Mínimo: ").append(p.getStockMinimo()).append("\n");
    }
    JOptionPane.showMessageDialog(this, msg.toString());
}

public void generarReporteContable() {

    JPanel panel = new JPanel(new GridLayout(2, 2));
    JTextField txtFechaInicio = new JTextField(LocalDate.now().withDayOfMonth(1).toString());
    JTextField txtFechaFin = new JTextField(LocalDate.now().toString());
    
    panel.add(new JLabel("Fecha Inicio (YYYY-MM-DD):"));
    panel.add(txtFechaInicio);
    panel.add(new JLabel("Fecha Fin (YYYY-MM-DD):"));
    panel.add(txtFechaFin);
    
    int result = JOptionPane.showConfirmDialog(this, panel, "Seleccionar Período", 
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
    if(result == JOptionPane.OK_OPTION) {
        try {
            LocalDate inicio = LocalDate.parse(txtFechaInicio.getText());
            LocalDate fin = LocalDate.parse(txtFechaFin.getText());
            
            var resultados = TiendaConfig.getInstancia()
                .getGestionContable().generarEstadoResultados(inicio, fin);
            
            StringBuilder sb = new StringBuilder();
            sb.append("=== ESTADO DE RESULTADOS ===\n");
            sb.append("Período: ").append(inicio).append(" al ").append(fin).append("\n\n");
            sb.append(String.format("Ingresos Totales: $%,.2f\n", resultados.get("Ingresos Totales")));
            sb.append(String.format("Costo de Ventas: $%,.2f\n", resultados.get("Costo de Ventas")));
            sb.append("-----------------------------------\n");
            sb.append(String.format("UTILIDAD BRUTA: $%,.2f\n", resultados.get("Utilidad Bruta")));
            sb.append(String.format("Gastos: $%,.2f\n", resultados.get("Gastos")));
            sb.append("-----------------------------------\n");
            sb.append(String.format("UTILIDAD NETA: $%,.2f\n", resultados.get("Utilidad Neta")));
            
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            JScrollPane scroll = new JScrollPane(textArea);
            scroll.setPreferredSize(new Dimension(500, 400));
            JOptionPane.showMessageDialog(this, scroll, "Estado de Resultados", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use YYYY-MM-DD");
        }
    }
}
   
public void generarBalanceGeneral() {
    LocalDate fechaCorte = LocalDate.now();
    
    var balance = TiendaConfig.getInstancia()
        .getGestionContable().generarBalanceGeneral(fechaCorte);
    
    StringBuilder sb = new StringBuilder();
    sb.append("=== BALANCE GENERAL ===\n");
    sb.append("Fecha de corte: ").append(fechaCorte).append("\n\n");
    
    sb.append("ACTIVOS:\n");
    for(Map.Entry<CuentaContable, Double> entry : balance.entrySet()) {
        if(entry.getKey().getTipo().equals("Activo") && entry.getValue() > 0) {
            sb.append(String.format("  %s: $%,.2f\n", entry.getKey().getNombre(), entry.getValue()));
        }
    }
    
    sb.append("\nPASIVOS:\n");
    for(Map.Entry<CuentaContable, Double> entry : balance.entrySet()) {
        if(entry.getKey().getTipo().equals("Pasivo") && entry.getValue() > 0) {
            sb.append(String.format("  %s: $%,.2f\n", entry.getKey().getNombre(), entry.getValue()));
        }
    }
    
    sb.append("\nPATRIMONIO:\n");
    for(Map.Entry<CuentaContable, Double> entry : balance.entrySet()) {
        if(entry.getKey().getTipo().equals("Patrimonio") && entry.getValue() > 0) {
            sb.append(String.format("  %s: $%,.2f\n", entry.getKey().getNombre(), entry.getValue()));
        }
    }
    
    JTextArea textArea = new JTextArea(sb.toString());
    textArea.setEditable(false);
    textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    JScrollPane scroll = new JScrollPane(textArea);
    scroll.setPreferredSize(new Dimension(500, 400));
    JOptionPane.showMessageDialog(this, scroll, "Balance General", 
        JOptionPane.INFORMATION_MESSAGE);
}

public void generarReporteCombined() {
 String[] opciones = {
     " Ventas Diarias",
     " Ventas Mensuales", 
     " Ventas Anuales",
     " Productos Más Vendidos",
     " Clientes Top Compradores",
     " Ventas por Forma de Pago",
     " Inventario Valorizado",
     " Utilidad Bruta",
     " Resumen Contable"
 };
 
 String seleccion = (String) JOptionPane.showInputDialog(this,
     "Seleccione el tipo de reporte:",
     "Generar Reporte",
     JOptionPane.QUESTION_MESSAGE,
     null,
     opciones,
     opciones[0]);
 
 if(seleccion == null) return;
 
 if(seleccion.equals(opciones[0])) {
     generarReporteVentasDiarias();
 } else if(seleccion.equals(opciones[1])) {
     generarReporteVentasMensuales();
 } else if(seleccion.equals(opciones[2])) {
     generarReporteVentasAnuales();
 } else if(seleccion.equals(opciones[3])) {
     generarReporteProductosMasVendidos();
 } else if(seleccion.equals(opciones[4])) {
     generarReporteClientesTop();
 } else if(seleccion.equals(opciones[5])) {
     generarReporteVentasPorFormaPago();
 } else if(seleccion.equals(opciones[6])) {
     generarReporteInventarioValorizado();
 } else if(seleccion.equals(opciones[7])) {
     generarReporteUtilidadBruta();
 } else if(seleccion.equals(opciones[8])) {
     generarReporteResumenContable();
 }
}


public void generarReporteVentasDiarias() {
 String fechaStr = JOptionPane.showInputDialog(this, 
     "Ingrese la fecha (YYYY-MM-DD):", 
     LocalDate.now().toString());
 
 if(fechaStr != null) {
     try {
         LocalDate fecha = LocalDate.parse(fechaStr);
         var reporte = TiendaConfig.getInstancia()
             .getGestionVenta().generarReporteVentasDiarias(fecha);
         
         StringBuilder sb = new StringBuilder();
         sb.append("=== REPORTE DE VENTAS DIARIAS ===\n");
         sb.append("Fecha: ").append(fecha).append("\n");
         sb.append("Total Ventas: $").append(String.format("%,.2f", reporte.getTotalVentas())).append("\n");
         sb.append("Total IVA: $").append(String.format("%,.2f", reporte.getTotalIVA())).append("\n");
         sb.append("Número Facturas: ").append(reporte.getNumeroFacturas()).append("\n\n");
         
         sb.append("--- Ventas por Forma de Pago ---\n");
         for(Map.Entry<String, Double> entry : reporte.getVentasPorFormaPago().entrySet()) {
             sb.append("  ").append(entry.getKey()).append(": $")
               .append(String.format("%,.2f", entry.getValue())).append("\n");
         }
         
         sb.append("\n--- Top 10 Productos Más Vendidos ---\n");
         for(Object[] p : reporte.getProductosMasVendidos()) {
             sb.append("  ").append(p[0]).append(": ").append(p[1]).append(" unidades\n");
         }
         
         sb.append("\n--- Top 10 Clientes ---\n");
         for(Object[] c : reporte.getClientesTop()) {
             sb.append("  ").append(c[0]).append(": $").append(String.format("%,.2f", c[1])).append("\n");
         }
         
         mostrarReporte(sb.toString(), "Reporte de Ventas Diarias");
         
     } catch(Exception e) {
         JOptionPane.showMessageDialog(this, "Fecha inválida. Use formato YYYY-MM-DD");
     }
 }
}


public void generarReporteVentasMensuales() {
 JPanel panel = new JPanel(new GridLayout(2, 2));
 JTextField txtAnio = new JTextField(String.valueOf(LocalDate.now().getYear()));
 JTextField txtMes = new JTextField(String.valueOf(LocalDate.now().getMonthValue()));
 
 panel.add(new JLabel("Año:"));
 panel.add(txtAnio);
 panel.add(new JLabel("Mes (1-12):"));
 panel.add(txtMes);
 
 int result = JOptionPane.showConfirmDialog(this, panel, "Reporte Mensual", 
     JOptionPane.OK_CANCEL_OPTION);
 
 if(result == JOptionPane.OK_OPTION) {
     try {
         int anio = Integer.parseInt(txtAnio.getText());
         int mes = Integer.parseInt(txtMes.getText());
         
         var reporte = TiendaConfig.getInstancia()
             .getGestionVenta().generarReporteVentasMensuales(anio, mes);
         
         StringBuilder sb = new StringBuilder();
         sb.append("=== REPORTE DE VENTAS MENSUALES ===\n");
         sb.append("Período: ").append(anio).append("-").append(String.format("%02d", mes)).append("\n");
         sb.append("Total Ventas: $").append(String.format("%,.2f", reporte.getTotalVentas())).append("\n");
         sb.append("Total IVA: $").append(String.format("%,.2f", reporte.getTotalIVA())).append("\n");
         sb.append("Número Facturas: ").append(reporte.getNumeroFacturas()).append("\n\n");
         
         sb.append("--- Ventas por Forma de Pago ---\n");
         for(Map.Entry<String, Double> entry : reporte.getVentasPorFormaPago().entrySet()) {
             sb.append("  ").append(entry.getKey()).append(": $")
               .append(String.format("%,.2f", entry.getValue())).append("\n");
         }
         
         mostrarReporte(sb.toString(), "Reporte de Ventas Mensuales");
         
     } catch(Exception e) {
         JOptionPane.showMessageDialog(this, "Valores inválidos");
     }
 }
}


public void generarReporteVentasAnuales() {
 String anioStr = JOptionPane.showInputDialog(this, 
     "Ingrese el año:", 
     String.valueOf(LocalDate.now().getYear()));
 
 if(anioStr != null) {
     try {
         int anio = Integer.parseInt(anioStr);
         var reporte = TiendaConfig.getInstancia()
             .getGestionVenta().generarReporteVentasAnuales(anio);
         
         StringBuilder sb = new StringBuilder();
         sb.append("=== REPORTE DE VENTAS ANUALES ===\n");
         sb.append("Año: ").append(anio).append("\n");
         sb.append("Total Ventas: $").append(String.format("%,.2f", reporte.getTotalVentas())).append("\n");
         sb.append("Total IVA: $").append(String.format("%,.2f", reporte.getTotalIVA())).append("\n");
         sb.append("Número Facturas: ").append(reporte.getNumeroFacturas()).append("\n\n");
         
         sb.append("--- Ventas por Forma de Pago ---\n");
         for(Map.Entry<String, Double> entry : reporte.getVentasPorFormaPago().entrySet()) {
             sb.append("  ").append(entry.getKey()).append(": $")
               .append(String.format("%,.2f", entry.getValue())).append("\n");
         }
         
         sb.append("\n--- Top 10 Productos Más Vendidos del Año ---\n");
         for(Object[] p : reporte.getProductosMasVendidos()) {
             sb.append("  ").append(p[0]).append(": ").append(p[1]).append(" unidades\n");
         }
         
         mostrarReporte(sb.toString(), "Reporte de Ventas Anuales");
         
     } catch(Exception e) {
         JOptionPane.showMessageDialog(this, "Año inválido");
     }
 }
}


public void generarReporteProductosMasVendidos() {
 JPanel panel = new JPanel(new GridLayout(3, 2));
 JTextField txtInicio = new JTextField(LocalDate.now().withDayOfMonth(1).toString());
 JTextField txtFin = new JTextField(LocalDate.now().toString());
 JTextField txtTop = new JTextField("10");
 
 panel.add(new JLabel("Fecha Inicio:"));
 panel.add(txtInicio);
 panel.add(new JLabel("Fecha Fin:"));
 panel.add(txtFin);
 panel.add(new JLabel("Top N:"));
 panel.add(txtTop);
 
 int result = JOptionPane.showConfirmDialog(this, panel, "Productos Más Vendidos", 
     JOptionPane.OK_CANCEL_OPTION);
 
 if(result == JOptionPane.OK_OPTION) {
     try {
         LocalDate inicio = LocalDate.parse(txtInicio.getText());
         LocalDate fin = LocalDate.parse(txtFin.getText());
         int top = Integer.parseInt(txtTop.getText());
         
         var productos = TiendaConfig.getInstancia()
             .getGestionVenta().obtenerProductosMasVendidos(top, inicio, fin);
         
         StringBuilder sb = new StringBuilder();
         sb.append("=== TOP ").append(top).append(" PRODUCTOS MÁS VENDIDOS ===\n");
         sb.append("Período: ").append(inicio).append(" al ").append(fin).append("\n\n");
         sb.append("Código | Producto | Cantidad | Precio | Total\n");
         sb.append("-----------------------------------------------\n");
         
         for(Object[] p : productos) {
             sb.append(String.format("%s | %s | %d | $%,.2f | $%,.2f\n", 
                 p[0], p[1], p[2], p[3], p[4]));
         }
         
         mostrarReporte(sb.toString(), "Productos Más Vendidos");
         
     } catch(Exception e) {
         JOptionPane.showMessageDialog(this, "Datos inválidos");
     }
 }
}


public void generarReporteClientesTop() {
 JPanel panel = new JPanel(new GridLayout(3, 2));
 JTextField txtInicio = new JTextField(LocalDate.now().withDayOfMonth(1).toString());
 JTextField txtFin = new JTextField(LocalDate.now().toString());
 JTextField txtTop = new JTextField("10");
 
 panel.add(new JLabel("Fecha Inicio:"));
 panel.add(txtInicio);
 panel.add(new JLabel("Fecha Fin:"));
 panel.add(txtFin);
 panel.add(new JLabel("Top N:"));
 panel.add(txtTop);
 
 int result = JOptionPane.showConfirmDialog(this, panel, "Clientes Top Compradores", 
     JOptionPane.OK_CANCEL_OPTION);
 
 if(result == JOptionPane.OK_OPTION) {
     try {
         LocalDate inicio = LocalDate.parse(txtInicio.getText());
         LocalDate fin = LocalDate.parse(txtFin.getText());
         int top = Integer.parseInt(txtTop.getText());
         
         var clientes = TiendaConfig.getInstancia()
             .getGestionVenta().obtenerClientesTopCompradores(top, inicio, fin);
         
         StringBuilder sb = new StringBuilder();
         sb.append("=== TOP ").append(top).append(" CLIENTES COMPRADORES ===\n");
         sb.append("Período: ").append(inicio).append(" al ").append(fin).append("\n\n");
         sb.append("Código | Cliente | Tipo | Total Comprado\n");
         sb.append("---------------------------------------------\n");
         
         for(Object[] c : clientes) {
             sb.append(String.format("%s | %s | %s | $%,.2f\n", c[0], c[1], c[2], c[3]));
         }
         
         mostrarReporte(sb.toString(), "Clientes Top Compradores");
         
     } catch(Exception e) {
         JOptionPane.showMessageDialog(this, "Datos inválidos");
     }
 }
}


public void generarReporteVentasPorFormaPago() {
 JPanel panel = new JPanel(new GridLayout(2, 2));
 JTextField txtInicio = new JTextField(LocalDate.now().withDayOfMonth(1).toString());
 JTextField txtFin = new JTextField(LocalDate.now().toString());
 
 panel.add(new JLabel("Fecha Inicio:"));
 panel.add(txtInicio);
 panel.add(new JLabel("Fecha Fin:"));
 panel.add(txtFin);
 
 int result = JOptionPane.showConfirmDialog(this, panel, "Ventas por Forma de Pago", 
     JOptionPane.OK_CANCEL_OPTION);
 
 if(result == JOptionPane.OK_OPTION) {
     try {
         LocalDate inicio = LocalDate.parse(txtInicio.getText());
         LocalDate fin = LocalDate.parse(txtFin.getText());
         
         var ventasPorPago = TiendaConfig.getInstancia()
             .getGestionVenta().obtenerVentasPorFormaPago(inicio, fin);
         
         StringBuilder sb = new StringBuilder();
         sb.append("=== VENTAS POR FORMA DE PAGO ===\n");
         sb.append("Período: ").append(inicio).append(" al ").append(fin).append("\n\n");
         
         double totalGeneral = 0;
         for(Map.Entry<String, Double> entry : ventasPorPago.entrySet()) {
             sb.append(String.format("%s: $%,.2f\n", entry.getKey(), entry.getValue()));
             totalGeneral += entry.getValue();
         }
         
         sb.append("\n").append("TOTAL GENERAL: $").append(String.format("%,.2f", totalGeneral));
         
         mostrarReporte(sb.toString(), "Ventas por Forma de Pago");
         
     } catch(Exception e) {
         JOptionPane.showMessageDialog(this, "Datos inválidos");
     }
 }
}


public void generarReporteInventarioValorizado() {
 var inventario = TiendaConfig.getInstancia()
     .getGestionVenta().obtenerInventarioValorizado();
 
 double valorTotal = TiendaConfig.getInstancia()
     .getGestionVenta().obtenerValorInventarioTotal();
 
 StringBuilder sb = new StringBuilder();
 sb.append("=== INVENTARIO VALORIZADO ===\n");
 sb.append("Fecha: ").append(LocalDate.now()).append("\n\n");
 sb.append("Código | Producto | Stock | Precio Compra | Valor Total\n");
 sb.append("--------------------------------------------------------\n");
 
 for(Object[] item : inventario) {
     sb.append(String.format("%s | %s | %d | $%,.2f | $%,.2f\n", 
         item[0], item[1], item[2], item[3], item[4]));
 }
 
 sb.append("\n").append("VALOR TOTAL INVENTARIO: $").append(String.format("%,.2f", valorTotal));
 
 mostrarReporte(sb.toString(), "Inventario Valorizado");
}


public void generarReporteUtilidadBruta() {
 JPanel panel = new JPanel(new GridLayout(2, 2));
 JTextField txtInicio = new JTextField(LocalDate.now().withDayOfMonth(1).toString());
 JTextField txtFin = new JTextField(LocalDate.now().toString());
 
 panel.add(new JLabel("Fecha Inicio:"));
 panel.add(txtInicio);
 panel.add(new JLabel("Fecha Fin:"));
 panel.add(txtFin);
 
 int result = JOptionPane.showConfirmDialog(this, panel, "Utilidad Bruta", 
     JOptionPane.OK_CANCEL_OPTION);
 
 if(result == JOptionPane.OK_OPTION) {
     try {
         LocalDate inicio = LocalDate.parse(txtInicio.getText());
         LocalDate fin = LocalDate.parse(txtFin.getText());
         
         double utilidadBruta = TiendaConfig.getInstancia()
             .getGestionVenta().calcularUtilidadBruta(inicio, fin);
         
         StringBuilder sb = new StringBuilder();
         sb.append("=== UTILIDAD BRUTA ===\n");
         sb.append("Período: ").append(inicio).append(" al ").append(fin).append("\n\n");
         sb.append("Utilidad Bruta: $").append(String.format("%,.2f", utilidadBruta));
         
         mostrarReporte(sb.toString(), "Utilidad Bruta");
         
     } catch(Exception e) {
         JOptionPane.showMessageDialog(this, "Datos inválidos");
     }
 }
}


public void generarReporteResumenContable() {
 JPanel panel = new JPanel(new GridLayout(2, 2));
 JTextField txtInicio = new JTextField(LocalDate.now().withDayOfMonth(1).toString());
 JTextField txtFin = new JTextField(LocalDate.now().toString());
 
 panel.add(new JLabel("Fecha Inicio:"));
 panel.add(txtInicio);
 panel.add(new JLabel("Fecha Fin:"));
 panel.add(txtFin);
 
 int result = JOptionPane.showConfirmDialog(this, panel, "Resumen Contable", 
     JOptionPane.OK_CANCEL_OPTION);
 
 if(result == JOptionPane.OK_OPTION) {
     try {
         LocalDate inicio = LocalDate.parse(txtInicio.getText());
         LocalDate fin = LocalDate.parse(txtFin.getText());
         
         var resumen = TiendaConfig.getInstancia()
             .getGestionContable().obtenerResumenContable(inicio, fin);
         
         StringBuilder sb = new StringBuilder();
         sb.append("=== RESUMEN CONTABLE ===\n");
         sb.append("Período: ").append(inicio).append(" al ").append(fin).append("\n\n");
         sb.append(String.format("Ingresos: $%,.2f\n", resumen.get("Ingresos")));
         sb.append(String.format("Egresos: $%,.2f\n", resumen.get("Egresos")));
         sb.append("-----------------------------------\n");
         sb.append(String.format("UTILIDAD: $%,.2f\n", resumen.get("Utilidad")));
         
         mostrarReporte(sb.toString(), "Resumen Contable");
         
     } catch(Exception e) {
         JOptionPane.showMessageDialog(this, "Datos inválidos");
     }
 }
}


private void mostrarReporte(String contenido, String titulo) {
 JTextArea textArea = new JTextArea(contenido);
 textArea.setEditable(false);
 textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
 JScrollPane scroll = new JScrollPane(textArea);
 scroll.setPreferredSize(new Dimension(700, 500));
 JOptionPane.showMessageDialog(this, scroll, titulo, JOptionPane.INFORMATION_MESSAGE);
}

    // ========== MAIN ==========
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch(Exception e) {
                e.printStackTrace();
            }
            new VentanaPrincipal().setVisible(true);
        });
    }
}