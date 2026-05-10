package co.edu.uptc.interfazGrafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class Evento implements ActionListener {
    
    private VentanaPrincipal ventana;
    
    // Navegación
    public static final String LOGIN = "LOGIN";
    public static final String SALIR = "SALIR";
    
    // Mostrar paneles
    public static final String MOSTRAR_PRODUCTOS = "MOSTRAR_PRODUCTOS";
    public static final String MOSTRAR_CLIENTES = "MOSTRAR_CLIENTES";
    public static final String MOSTRAR_PROVEEDORES = "MOSTRAR_PROVEEDORES";
    public static final String MOSTRAR_VENTAS = "MOSTRAR_VENTAS";
    public static final String MOSTRAR_COMPRAS = "MOSTRAR_COMPRAS";
    
    // CLIENTES
    public static final String NUEVO_CLIENTE = "NUEVO_CLIENTE";
    public static final String ACTUALIZAR_CLIENTE = "ACTUALIZAR_CLIENTE";
    public static final String ELIMINAR_CLIENTE = "ELIMINAR_CLIENTE";
    public static final String BUSCAR_CLIENTE = "BUSCAR_CLIENTE";
    public static final String LIMPIAR_CLIENTE = "LIMPIAR_CLIENTE";
    
    // PRODUCTOS
    public static final String NUEVO_PRODUCTO = "NUEVO_PRODUCTO";
    public static final String ACTUALIZAR_PRODUCTO = "ACTUALIZAR_PRODUCTO";
    public static final String ELIMINAR_PRODUCTO = "ELIMINAR_PRODUCTO";
    public static final String BUSCAR_PRODUCTO = "BUSCAR_PRODUCTO";
    public static final String LIMPIAR_PRODUCTO = "LIMPIAR_PRODUCTO";
    
    // PROVEEDORES
    public static final String NUEVO_PROVEEDOR = "NUEVO_PROVEEDOR";
    public static final String ACTUALIZAR_PROVEEDOR = "ACTUALIZAR_PROVEEDOR";
    public static final String ELIMINAR_PROVEEDOR = "ELIMINAR_PROVEEDOR";
    public static final String BUSCAR_PROVEEDOR = "BUSCAR_PROVEEDOR";
    public static final String LIMPIAR_PROVEEDOR = "LIMPIAR_PROVEEDOR";
    
    // VENTA
    public static final String NUEVA_VENTA = "NUEVA_VENTA";
    public static final String CONSULTAR_VENTA = "CONSULTAR_VENTA";
    public static final String ANULAR_VENTA = "ANULAR_VENTA";
    public static final String LIMPIAR_VENTA = "LIMPIAR_VENTA";
   
    // ========== COMPRAS ==========
    public static final String NUEVA_COMPRA = "NUEVA_COMPRA";          
    public static final String CONSULTAR_COMPRA = "CONSULTAR_COMPRA";   
    public static final String ANULAR_COMPRA = "ANULAR_COMPRA";         
    public static final String LIMPIAR_COMPRA = "LIMPIAR_COMPRA";       
    
    
    // REPORTES
    public static final String REPORTE_VENTAS = "REPORTE_VENTAS";
    public static final String REPORTE_COMPRAS = "REPORTE_COMPRAS";
    public static final String REPORTE_INVENTARIO = "REPORTE_INVENTARIO";
    public static final String REPORTE_CONTABLE = "REPORTE_CONTABLE";
    public static final String GENERAR_JSON_DIARIO = "GENERAR_JSON_DIARIO";
    public static final String MOSTRAR_CONTABILIDAD = "MOSTRAR_CONTABILIDAD";
    public static final String BALANCE_GENERAL = "BALANCE_GENERAL";
    public static final String MOSTRAR_REPORTES = "MOSTRAR_REPORTES";
    
    public Evento(VentanaPrincipal ventana) {
        this.ventana = ventana;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        
        // ========== LOGIN CON VALIDACIÓN ==========
        if(comando.equals(LOGIN)) {
          
            ventana.login();
        }
        
        // Navegación por toolbar
        else if(comando.equals(MOSTRAR_PRODUCTOS)) ventana.mostrarPanelProductos();
        else if(comando.equals(MOSTRAR_CLIENTES)) ventana.mostrarPanelClientes();
        else if(comando.equals(MOSTRAR_PROVEEDORES)) ventana.mostrarPanelProveedores();
        else if(comando.equals(MOSTRAR_VENTAS)) ventana.mostrarPanelVentas();
        else if(comando.equals(MOSTRAR_COMPRAS)) ventana.mostrarPanelCompras();
        else if(comando.equals(SALIR)) ventana.salir();
        
        // CLIENTES
        else if(comando.equals(NUEVO_CLIENTE)) ventana.nuevoCliente();
        else if(comando.equals(ACTUALIZAR_CLIENTE)) ventana.actualizarCliente();
        else if(comando.equals(ELIMINAR_CLIENTE)) ventana.eliminarCliente();
        else if(comando.equals(BUSCAR_CLIENTE)) ventana.buscarCliente();
        else if(comando.equals(LIMPIAR_CLIENTE)) ventana.limpiarClientes();
        
        // PRODUCTOS
        else if(comando.equals(NUEVO_PRODUCTO)) ventana.nuevoProducto();
        else if(comando.equals(ACTUALIZAR_PRODUCTO)) ventana.actualizarProducto();
        else if(comando.equals(ELIMINAR_PRODUCTO)) ventana.eliminarProducto();
        else if(comando.equals(BUSCAR_PRODUCTO)) ventana.buscarProducto();
        else if(comando.equals(LIMPIAR_PRODUCTO)) ventana.limpiarProductos();
        
        // PROVEEDORES
        else if(comando.equals(NUEVO_PROVEEDOR)) ventana.nuevoProveedor();
        else if(comando.equals(ACTUALIZAR_PROVEEDOR)) ventana.actualizarProveedor();
        else if(comando.equals(ELIMINAR_PROVEEDOR)) ventana.eliminarProveedor();
        else if(comando.equals(BUSCAR_PROVEEDOR)) ventana.buscarProveedor();
        else if(comando.equals(LIMPIAR_PROVEEDOR)) ventana.limpiarProveedores();
        
        // VENTA
        else if(comando.equals(NUEVA_VENTA)) ventana.nuevaVenta();
        else if(comando.equals(ANULAR_VENTA)) ventana.anularVenta();
        else if(comando.equals(CONSULTAR_VENTA)) ventana.consultarVenta();
        else if(comando.equals(LIMPIAR_VENTA)) ventana.limpiarVentas();
        
        // COMPRA
        else if(comando.equals(NUEVA_COMPRA)) ventana.nuevaCompra();
        else if(comando.equals(ANULAR_COMPRA)) ventana.anularCompra();
        else if(comando.equals(CONSULTAR_COMPRA)) ventana.consultarCompra();
        else if(comando.equals(LIMPIAR_COMPRA)) ventana.limpiarCompras();
        
        // REPORTES
        else if(comando.equals(REPORTE_VENTAS)) ventana.generarReporteVentas();
        else if(comando.equals(REPORTE_INVENTARIO)) ventana.generarReporteInventario();
        else if(comando.equals(REPORTE_CONTABLE)) ventana.generarReporteContable();
      
        else if(comando.equals(MOSTRAR_CONTABILIDAD)) ventana.mostrarPanelContabilidad();
        else if(comando.equals(BALANCE_GENERAL)) ventana.generarBalanceGeneral();
        else if(comando.equals(MOSTRAR_REPORTES)) ventana.generarReporteCombined();
    }
}