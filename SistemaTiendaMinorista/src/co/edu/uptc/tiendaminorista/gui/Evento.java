package co.edu.uptc.tiendaminorista.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Evento implements ActionListener {
    
    public final static String SALIR = "Salir";
    public final static String ENTRAR = "Entrar";
    public final static String REGISTRARCLIENTE = "Registrar cliente";
    public final static String CANCELAR = "Cancelar";
    public final static String REGISTRAR = "Registrar";
    public final static String MODIFICARCLIENTE = "Modificar cliente";
    public final static String ACTUALIZARCLI = "Actualizar cliente";
    public final static String DESACTIVARCLI = "Desactivar cliente";
    public final static String ACTIVARCLI = "Activar cliente";
    public final static String REGISTRARPROVEDORES = "Registrar Proveedores";
    public final static String REGISTRARPROV = "Registrar Proveedor";
    public final static String ACTUALIZARPRO = "Actualizar Proveedores";
    public final static String ACTUALIZARPRO1 = "Actualizar Proveedor";
    public final static String DESACTIVARPRO = "Desactivar proveedor";
    public final static String ACTIVARPRO = "Activar proveedor";
    public final static String COMPRASPRO = "Compras realizadas a los proveedores";
    public final static String CANCELARPRO = "Cancelar";
    public final static String REGISTRAREM = "Registar Empleado";
    public final static String ACTUALIZAREM = "Actualizar Empleado";
    public final static String ELIMINAREM = "Eliminar";
    public final static String COMPRASCLI = "Realizar compra"; 
    public final static String REALIZARCOM = "Ejecutar Compra Desde Panel"; 
    public final static String VOLVER = "Volver de Compra"; 
    public final static String HISTORIALCLIENTE = "Historial de compra cliente";
    public final static String BUSCAR_HISTORIAL_CLI = "Buscar Historial Cliente";
    public final static String ENVIA = "Buscar Historial Cliente";
    public final static String EJECUTAR_CONS_COMPRA_PROV = "Ejecutar Consulta Compra Proveedor";
    public final static String MOSTRAR_VENTAS_FECHA = "Mostrar Ventas Por Fecha";
    public final static String MOSTRAR_COMPRA_PROV = "Mostrar Compra Por Proveedor";
    public final static String MOSTRAR_STOCK_MIN = "Mostrar Productos Stock Minimo";
    public final static String MOSTRAR_HISTORIAL_CLI_CONS = "Mostrar Historial Cliente Consulta";
    public final static String MOSTRAR_MOV_CONTABLE = "Mostrar Movimiento Contable";

    public final static String EJECUTAR_CONS_VENTAS_FECHA = "Ejecutar Consulta Ventas Fecha";
    public final static String EJECUTAR_CONS_STOCK_MIN = "Ejecutar Consulta Stock Minimo";
    public final static String EJECUTAR_CONS_HISTORIAL_CLI = "Ejecutar Consulta Historial Cliente";
    public final static String EJECUTAR_CONS_MOV_CONTABLE = "Ejecutar Consulta Movimientos Contables";
    public final static String ENVIAR = "Enviar datos de practica";
    public final static String ACTUALIZARDTO = "Actualizardto";
    public final static String ELIMINARDTO = "Eliminar practica";
    

    private PanelPrincipal ventana;

    public Evento(PanelPrincipal V) {
        this.ventana = V;
    }

    
    public PanelPrincipal getVentana() {
        return this.ventana;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.equals(SALIR)) {
            System.exit(0);
        } else if (comando.equals(ENTRAR)) {
            ventana.loguear();
        } else if (comando.equals(REGISTRARCLIENTE)) {
            ventana.mostrarRegistroCliente();
        } else if (comando.equals(CANCELAR)) {
            ventana.regresarAlInicial();
        } else if (comando.equals(REGISTRAR)) {
            ventana.registrarCliente();
        } else if (comando.equals(MODIFICARCLIENTE)) {
            ventana.mostrarActualizarCliente();
        } else if (comando.equals(ACTUALIZARCLI)) {
            ventana.actualizarCliente();
        } else if (comando.equals(DESACTIVARCLI)) {
            ventana.desactivarCliente();
        } else if (comando.equals(ACTIVARCLI)) {
            ventana.activarCliente();
        } else if (comando.equals(REGISTRARPROVEDORES)) {
            ventana.mostrarRegistrarProveedor();
        } else if (comando.equals(REGISTRARPROV)) {
            ventana.registrarProveedor();
        } else if (comando.equals(ACTUALIZARPRO)) {
            ventana.mostrarActualizarProveedor();
        } else if (comando.equals(ACTUALIZARPRO1)) {
            ventana.actualizarProveedor();
        } else if (comando.equals(DESACTIVARPRO)) {
            ventana.desactivarProveedor();
        } else if (comando.equals(ACTIVARPRO)) {
            ventana.activarProveedor();
        } else if (comando.equals(COMPRASPRO)) {
            ventana.mostrarComprasPro();    
        } else if (comando.equals(REGISTRAREM)) {
            ventana.registrarEmpleado();
        } else if (comando.equals(ACTUALIZAREM)) {
            ventana.actualizarEmpleado();
        } else if (comando.equals(ELIMINAREM)) { 
            ventana.eliminarEmpleado();
        } else if (comando.equals(COMPRASCLI)) {
            ventana.mostrarCompraCliente(); 
        } else if (comando.equals(HISTORIALCLIENTE)) {
            ventana.mostrarPantallaHistorial();
        } else if (comando.equals(BUSCAR_HISTORIAL_CLI)) {
            ventana.buscarHistorialCliente();
        } else if (comando.equals(VOLVER)) {
            ventana.regresarAlInicial();
        } else if (comando.equals(REALIZARCOM)) {
            ventana.ejecutarCompraCliente(); 
        } 
        else if (comando.equals(EJECUTAR_CONS_COMPRA_PROV)) {
            ventana.ejecutarConsultaCompraProveedor(); 
        } else if (comando.equals(EJECUTAR_CONS_VENTAS_FECHA)) {
            ventana.ejecutarConsultaVentasPorFecha();
        } else if (comando.equals(EJECUTAR_CONS_STOCK_MIN)) {
            ventana.ejecutarConsultaStockMinimo();
        } else if (comando.equals(EJECUTAR_CONS_HISTORIAL_CLI)) {
            ventana.ejecutarConsultaHistorialClienteConsulta();
        } else if (comando.equals(EJECUTAR_CONS_MOV_CONTABLE)) {
            ventana.ejecutarConsultaMovimientosContables();
        } else if (comando.equals(MOSTRAR_VENTAS_FECHA)) {
            ventana.getPanelInicial().getPanelConsultas().conmutarVista("VENTAS_FECHA");
        } else if (comando.equals(MOSTRAR_COMPRA_PROV)) {
            ventana.getPanelInicial().getPanelConsultas().conmutarVista("COMPRA_PROVEEDOR");
        } else if (comando.equals(MOSTRAR_STOCK_MIN)) {
            ventana.mostrarPanelStockMinimo();
        } else if (comando.equals(MOSTRAR_HISTORIAL_CLI_CONS)) {
            ventana.mostrarHistorialClienteConsulta();
        } else if (comando.equals(MOSTRAR_MOV_CONTABLE)) {
            ventana.getPanelInicial().getPanelConsultas().conmutarVista("MOV_CONTABLE");
        } else if (comando.equals(MOSTRAR_MOV_CONTABLE)) {
            ventana.getPanelInicial().getPanelConsultas().conmutarVista("MOV_CONTABLE");
        } 
        else if (comando.equals(ENVIAR)) {
            ventana.ejecutarGuardarPractica();
        }else if (comando.equals(ELIMINARDTO)) {
            ventana.ejecutarEliminarPractica();
        }
    }
}