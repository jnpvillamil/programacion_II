package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Evento implements ActionListener {

    public static final String LOGIN = "Login";
    public static final String PRODUCTOS = "Productos";
    public static final String CLIENTES = "Clientes";
    public static final String VENTAS = "Ventas";
    public static final String COMPRAS = "Compras";
    public static final String REPORTES = "Reportes";
    public static final String PROVEEDORES = "Proveedores";
    public static final String SALIR = "Salir";

    
    public static final String REGISTRAR_PRODUCTO = "Registrar Producto";
    public static final String MODIFICAR_PRODUCTO = "Modificar Producto";
    public static final String INACTIVAR_PRODUCTO = "Inactivar Producto";
    public static final String CONSULTAR_PRODUCTO = "Consultar Producto";
    public static final String HISTORIAL_PRODUCTO = "Historial Producto";
    public static final String VOLVER_PRODUCTOS = "Volver Productos";
    public static final String GUARDAR_PRODUCTO = "Guardar Producto";
    public static final String CANCELAR_PRODUCTO = "Cancelar Producto";

   
    public static final String REGISTRAR_CLIENTE = "Registrar Cliente";
    public static final String MODIFICAR_CLIENTE = "Modificar Cliente";
    public static final String INACTIVAR_CLIENTE = "Inactivar Cliente";
    public static final String CONSULTAR_CLIENTE = "Consultar Cliente";
    public static final String HISTORIAL_CLIENTE = "Historial Cliente";
    public static final String VOLVER_CLIENTES = "Volver Clientes";
    public static final String GUARDAR_CLIENTE = "Guardar Cliente";
    public static final String CANCELAR_CLIENTE = "Cancelar Cliente";

 
    public static final String REGISTRAR_VENTA = "Registrar Venta";
    public static final String CONSULTAR_VENTA = "Consultar Venta";
    public static final String ANULAR_VENTA = "Anular Venta";
    public static final String VOLVER_VENTAS = "Volver Ventas";
    public static final String CALCULAR_VENTA = "Calcular Venta";
    public static final String GUARDAR_VENTA = "Guardar Venta";
    public static final String CANCELAR_VENTA = "Cancelar Venta";

  
    public static final String REGISTRAR_COMPRA = "Registrar Compra";
    public static final String CONSULTAR_COMPRA = "Consultar Compra";
    public static final String ANULAR_COMPRA = "Anular Compra";
    public static final String VOLVER_COMPRAS = "Volver Compras";
    public static final String CALCULAR_COMPRA = "Calcular Compra";
    public static final String GUARDAR_COMPRA = "Guardar Compra";
    public static final String CANCELAR_COMPRA = "Cancelar Compra";

  
    public static final String REGISTRAR_PROVEEDOR = "Registrar Proveedor";
    public static final String CONSULTAR_PROVEEDOR = "Consultar Proveedor";
    public static final String MODIFICAR_PROVEEDOR = "Modificar Proveedor";
    public static final String ELIMINAR_PROVEEDOR = "Eliminar Proveedor";
    public static final String VOLVER_PROVEEDORES = "Volver Proveedores";
    public static final String GUARDAR_PROVEEDOR = "Guardar Proveedor";
    public static final String CANCELAR_PROVEEDOR = "Cancelar Proveedor";


    public static final String REPORTE_VENTAS_GENERAL = "Reporte Ventas General";
    public static final String REPORTE_COMPRAS_GENERAL = "Reporte Compras General";
    public static final String REPORTE_INVENTARIO = "Reporte Inventario";
    public static final String VOLVER_REPORTES = "Volver Reportes";

    private VentanaPrincipal ventana;

    public Evento(VentanaPrincipal v) {
        ventana = v;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String evento = e.getActionCommand();

       
        if (evento.equals(LOGIN)) {
            ventana.loguear();

        } else if (evento.equals(PRODUCTOS)) {
            ventana.mostrarPanelProductos();

        } else if (evento.equals(CLIENTES)) {
            ventana.mostrarPanelClientes();

        } else if (evento.equals(VENTAS)) {
            ventana.mostrarPanelVentas();

        } else if (evento.equals(COMPRAS)) {
            ventana.mostrarPanelCompras();

        } else if (evento.equals(REPORTES)) {
            ventana.mostrarPanelReportes();

        } else if (evento.equals(PROVEEDORES)) {
            ventana.mostrarPanelProveedores();

        } else if (evento.equals(SALIR)) {
            ventana.salirSistema();
        }

      
        else if (evento.equals(REGISTRAR_PRODUCTO)) {
            ventana.mostrarPanelRegistrarProducto();

        } else if (evento.equals(VOLVER_PRODUCTOS)) {
            ventana.mostrarPanelCentral();

        } else if (evento.equals(GUARDAR_PRODUCTO)) {
            ventana.guardarProducto();

        } else if (evento.equals(CANCELAR_PRODUCTO)) {
            ventana.mostrarPanelProductos();
        }
        else if (evento.equals(MODIFICAR_PRODUCTO)) {
            ventana.modificarProducto();
        }
   
        else if (evento.equals(INACTIVAR_PRODUCTO)) {
            ventana.inactivarProducto();
        }
        else if (evento.equals(HISTORIAL_PRODUCTO)) {
            ventana.historialProductos();
        }
        else if (evento.equals(CONSULTAR_PRODUCTO)) {
            ventana.consultarProducto();
        }
    
        
        else if (evento.equals(REGISTRAR_CLIENTE)) {
            ventana.mostrarPanelRegistrarCliente();
        }
        else if (evento.equals(MODIFICAR_CLIENTE)) {
            ventana.modificarCliente();
        }
        else if (evento.equals(INACTIVAR_CLIENTE)) {
            ventana.inactivarCliente();
        }
        else if (evento.equals(CONSULTAR_CLIENTE)) {
            ventana.consultarCliente();
        }
        else if (evento.equals(HISTORIAL_CLIENTE)) {
            ventana.historialClientes();
        }
        else if (evento.equals(VOLVER_CLIENTES)) {
            ventana.mostrarPanelCentral();

        } else if (evento.equals(GUARDAR_CLIENTE)) {
            ventana.guardarCliente();

        } else if (evento.equals(CANCELAR_CLIENTE)) {
            ventana.mostrarPanelClientes();
        }

      
        else if (evento.equals(REGISTRAR_VENTA)) {
            ventana.mostrarPanelRegistrarVenta();

        } else if (evento.equals(VOLVER_VENTAS)) {
            ventana.mostrarPanelCentral();

        } else if (evento.equals(CALCULAR_VENTA)) {
            ventana.calcularVenta();

        } else if (evento.equals(GUARDAR_VENTA)) {
            ventana.guardarVenta();

        } else if (evento.equals(CANCELAR_VENTA)) {
            ventana.mostrarPanelVentas();
        }

        
        else if (evento.equals(REGISTRAR_COMPRA)) {
            ventana.mostrarPanelRegistrarCompra();

        } else if (evento.equals(VOLVER_COMPRAS)) {
            ventana.mostrarPanelCentral();

        } else if (evento.equals(CALCULAR_COMPRA)) {
            ventana.calcularCompra();

        } else if (evento.equals(GUARDAR_COMPRA)) {
            ventana.guardarCompra();

        } else if (evento.equals(CANCELAR_COMPRA)) {
            ventana.mostrarPanelCompras();
        }

      
        else if (evento.equals(REGISTRAR_PROVEEDOR)) {
            ventana.mostrarPanelRegistrarProveedor();

        } else if (evento.equals(VOLVER_PROVEEDORES)) {
            ventana.mostrarPanelCentral();

        } else if (evento.equals(GUARDAR_PROVEEDOR)) {
            ventana.guardarProveedor();

        } else if (evento.equals(CANCELAR_PROVEEDOR)) {
            ventana.mostrarPanelProveedores();
        }
        else if (evento.equals(MODIFICAR_PROVEEDOR)) {
            ventana.modificarProveedor();
        }
        else if (evento.equals(CONSULTAR_PROVEEDOR)) {
            ventana.consultarProveedor();
        }
      
        else if (evento.equals(ELIMINAR_PROVEEDOR)) {
            ventana.eliminarProveedor();
        }
        else if (evento.equals(REPORTE_VENTAS_GENERAL)) {
        	 ventana.mostrarPanelFacturas();
        }
        else if (evento.equals(VOLVER_REPORTES)) {
            ventana.mostrarPanelCentral();
        }
    }
}