package co.edu.uptc.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class Evento implements ActionListener {

    public final static String SALIR = "Salir";
    public final static String ENTRAR = "Entrar";
    public final static String REGISTRARCLIENTE = "Registrar cliente";
    public final static String MODIFICARCLIENTE = "Modificar cliente";
    public final static String HISTORIALCLIENTE = "Historial de compra cliente";
    public final static String BUSQUEDACLI = "busquedacliente";
    public final static String CANCELAR = "Cancelar";
    public final static String REGISTRAR = "Registrar";
    public final static String ACTUALIZARCLI = "Actualizar cliente";
    public final static String DESACTIVARCLI = "Desactivar";
    public final static String REGISTRARPROVEDORES = "Registrar Proveedores";
    public final static String ACTUALIZARPRO = "Actualizar Proveedores";
    public final static String COMPRASPRO = "Compras realizadas a los proveedores";
    public final static String CANCELARPRO = "Cancelar";
    public final static String REGISTRARPROV = "Registrar Proveedor";
    public final static String ACTUALIZARPRO1 = "Actualizar Proveedor";

    private Panel_principal ventana;
    private PanelProductos panelProductos;

    public Evento(Panel_principal V) {
        ventana = V;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String evento = e.getActionCommand();

        if (evento.equals(SALIR)) {

            JOptionPane.showMessageDialog(null, "pelo");

        } else if (evento.equals(ENTRAR)) {

            ventana.loguear();

        } else if (evento.equals(REGISTRARCLIENTE)) {

            ventana.mostrarRegistroCliente();

        } else if (evento.equals(CANCELAR)) {

            ventana.regresarcli();

        } else if (evento.equals(MODIFICARCLIENTE)) {

            ventana.Actualizarcli();

        }
   
        else if (evento.equals(REGISTRAR)) {

            panel_registrocli panel = ventana.getPanelRegistro();

            String nombre = panel.getNombre();
            String tipoDoc = panel.getTipoDoc();
            String numero = panel.getNumeroDoc();
            String direccion = panel.getDireccion();
            String telefono = panel.getTelefono();
            String tipoCliente = panel.getTipoCliente();

            co.edu.uptc.Datos.Clientedt cliente = new co.edu.uptc.Datos.Clientedt(
                    nombre, tipoDoc, numero, direccion, telefono, tipoCliente
            );

            co.edu.uptc.negocio.Cliente_negocio.getInstance().agregarCliente(cliente);

            ventana.getPanelCliente().cargarClientes(
                    co.edu.uptc.negocio.Cliente_negocio.getInstance().getListaClientes()
            );

            JOptionPane.showMessageDialog(null, "Registro exitoso");

            ventana.mostrarPanelCliente();
        }
   
        else if (evento.equals(ACTUALIZARCLI)) {

            Panel_Actualizarcli panel = ventana.getPanelActualizar();

            String doc = panel.getClienteSeleccionado();
            String nombre = panel.getNombre();
            String direccion = panel.getDireccion();
            String telefono = panel.getTelefono();

            co.edu.uptc.Datos.Clientedt cliente = new co.edu.uptc.Datos.Clientedt(
                    nombre, "", doc, direccion, telefono, ""
            );

            co.edu.uptc.negocio.Cliente_negocio.getInstance().ActualizarCliente(cliente);

            JOptionPane.showMessageDialog(null, "Actualizado correctamente");

            ventana.mostrarPanelCliente();
        }
        else if (evento.equals(REGISTRARPROVEDORES)) {

            ventana.registrarpro();

        } else if (evento.equals(REGISTRARPROV)) {

            JOptionPane.showMessageDialog(null, "Registro exitoso");
            ventana.regresarcli();

        } else if (evento.equals(ACTUALIZARPRO)) {

            ventana.Actualizarpro1();

        } else if (evento.equals(ACTUALIZARPRO1)) {

            JOptionPane.showMessageDialog(null, "Actualización de proveedor exitosa");
            ventana.regresarcli();
        }

        if (evento.equals(REGISTRARCLIENTE)) {
            ventana.mostrarRegistroCliente();
        }
        if (evento.equals(MODIFICARCLIENTE)) {
            JOptionPane.showMessageDialog(null, "Modificar cliente en construccion");
        }
        if (evento.equals(HISTORIALCLIENTE)) {
            JOptionPane.showMessageDialog(null, "Historial de cliente en construccion");
        }
        if (evento.equals(BUSQUEDACLI)) {
            JOptionPane.showMessageDialog(null, "Busqueda de cliente en construccion");
        }

        if (evento.equals(REGISTRAR)) {
            JOptionPane.showMessageDialog(null, "Cliente registrado");
        }
        
        if (evento.equals("RegistrarProducto")) {
            registrarProducto();
        }
        if (evento.equals("BorrarProducto")) {
            borrarCampos();
        }
        if (evento.equals("ActualizarProducto")) {
            actualizarProducto();
        }
        if (evento.equals("ActualizarPrecios")) {
            actualizarPrecios();
        }
        if (evento.equals("ControlarStock")) {
            controlarStock();
        }
        
    }
    public void registrarProducto() {
        String nombre = panelProductos.textoDelNombre.getText();
        String categoria = panelProductos.textoCategoria.getText();
        String precioC = panelProductos.textoPrecioCompra.getText();
        String precioV = panelProductos.textoPrecioVenta.getText();
        String stock = panelProductos.textoStockActual.getText();
 
        if (nombre.equals("")) {
            JOptionPane.showMessageDialog(panelProductos, "El nombre no puede estar vacio");
            return;
        }
        if (categoria.equals("")) {
            JOptionPane.showMessageDialog(panelProductos, "La categoria no puede estar vacia");
            return;
        }
        
        String linea = nombre + "   " + categoria + "   " + precioC + "UN" + "   " + precioV + "UN" + "   " + stock + "   " + "ENTRADA" + "\n";
        panelProductos.areaListaProductos.append(linea);
 
        String infoActual = panelProductos.textoMostrarInfo.getText();
        panelProductos.textoMostrarInfo.setText(infoActual + " | " + nombre);
        
        borrarCampos();
    }
 
    public void borrarCampos() {
        panelProductos.textoDelNombre.setText("");
        panelProductos.textoCategoria.setText("");
        panelProductos.textoPrecioCompra.setText("");
        panelProductos.textoPrecioVenta.setText("");
        panelProductos.textoStockActual.setText("");
        panelProductos.textoStockMinimo.setText("");
        panelProductos.textoDelCodigo.setText("");
    }
 
    public void actualizarProducto() {
        JOptionPane.showMessageDialog(panelProductos, "Funcion de actualizar producto en construccion.");
    }
 
    public void actualizarPrecios() {
        String nombre = panelProductos.textoDelNombre.getText();
        String precioC = panelProductos.textoPrecioCompra.getText();
        String precioV = panelProductos.textoPrecioVenta.getText();
 
        JOptionPane.showMessageDialog(panelProductos, "Precios actualizados para: " + nombre
                + "\nNuevo precio compra: " + precioC
                + "\nNuevo precio venta: " + precioV);
    }
 
    public void controlarStock() {
        String nombre = panelProductos.textoDelNombre.getText();
        String stockActual = panelProductos.textoStockActual.getText();
        String stockMinimo = panelProductos.textoStockMinimo.getText();
 
        if (!stockActual.equals("") && !stockMinimo.equals("")) {
            int actual = Integer.parseInt(stockActual);
            int minimo = Integer.parseInt(stockMinimo);
            
            if (actual < minimo) {
                JOptionPane.showMessageDialog(panelProductos, "¡ALERTA! Stock bajo para: " + nombre
                        + "\nStock actual: " + stockActual
                        + "\nStock minimo: " + stockMinimo);
            } else {
                JOptionPane.showMessageDialog(panelProductos, "Producto: " + nombre
                        + "\nStock actual: " + stockActual
                        + "\nStock minimo: " + stockMinimo
                        + "\nStock OK");
            }
        } else {
            JOptionPane.showMessageDialog(panelProductos, "Producto: " + nombre
                    + "\nStock actual: " + stockActual
                    + "\nStock minimo: " + stockMinimo);
        }
    }
}
