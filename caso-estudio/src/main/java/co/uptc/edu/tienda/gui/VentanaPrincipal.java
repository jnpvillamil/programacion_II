package co.uptc.edu.tienda.gui;

import javax.swing.*;

import co.uptc.edu.tienda.negocio.GestionSeguridad;
import co.uptc.edu.tienda.negocio.Proveedor;
import co.uptc.edu.tienda.negocio.ProveedorConfig;
import co.uptc.edu.tienda.negocio.Producto;
import co.uptc.edu.tienda.negocio.dto.CredencialDto;

import java.awt.*;

public class VentanaPrincipal extends JFrame {
	
private PanelLogin pLogin;
private PanelPadreProveedor pCentral;
private PanelPadreProducto pProducto;

private JPanel contenedor;
private JButton btnProveedor, btnProducto;

private GestionSeguridad seguridad;
private DialogoProveedor nuevoProveedor;
private DialogoProducto nuevoProducto;

private Evento evento;
private ProveedorConfig proveedorConfig;

public VentanaPrincipal() {
    setSize(400,300);
    setTitle("Tienda Minorista");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());
    
    evento = new Evento(this);
    pLogin = new PanelLogin(evento);
    pCentral = new PanelPadreProveedor(evento);
    pProducto = new PanelPadreProducto(evento);

    seguridad = new GestionSeguridad();
    proveedorConfig = new ProveedorConfig();

    add(pLogin, BorderLayout.CENTER);
}

public static void main(String[] args) {
    VentanaPrincipal login = new VentanaPrincipal();
    login.setVisible(true);
}

public void loguear() {
    try {
        CredencialDto validar = pLogin.getCredencialesUsuario();

        if(validar != null && seguridad.validarLogueo(validar)) {

            remove(pLogin);

            JPanel panelBotones = new JPanel();
            btnProveedor = new JButton("Proveedor");
            btnProducto = new JButton("Producto");

            panelBotones.add(btnProveedor);
            panelBotones.add(btnProducto);

            add(panelBotones, BorderLayout.NORTH);

            contenedor = new JPanel(new BorderLayout());
            add(contenedor, BorderLayout.CENTER);

            contenedor.add(pCentral);

            btnProveedor.addActionListener(e -> {
                contenedor.removeAll();
                contenedor.add(pCentral);
                contenedor.repaint();
                contenedor.revalidate();
            });

            btnProducto.addActionListener(e -> {
                contenedor.removeAll();
                contenedor.add(pProducto);
                contenedor.repaint();
                contenedor.revalidate();
            });

            this.setSize(900,500);
            repaint();
            revalidate();

        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña no válida");
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}

// ===================== PROVEEDOR =====================

public void lanzarDialogoProveedor() {
    nuevoProveedor = new DialogoProveedor(evento, "Crear Proveedor", true);
    nuevoProveedor.setSize(400,400);
    nuevoProveedor.setLocationRelativeTo(null);
    nuevoProveedor.setVisible(true);
}

public void cerrarDialogoProveedor() {
    if(nuevoProveedor != null){
        nuevoProveedor.setVisible(false);
        nuevoProveedor = null;
    }
}

public void crearProveedor() {    
    proveedorConfig.getGestProveedor().agregarProveedor(nuevoProveedor.capturarDatos()); 
    cerrarDialogoProveedor();
    pCentral.poblarTabla(proveedorConfig.getGestProveedor().listar());
}

public void lanzarDialogoModificarProveedor() {
    int codigo = pCentral.getItemSeleccionado();
    if(codigo == -1) return;

    Proveedor p = proveedorConfig.getGestProveedor().buscarProveedorPorCodigo(codigo);

    nuevoProveedor = new DialogoProveedor(evento, "Modificar Proveedor", false);
    nuevoProveedor.cargarDatos(p);
    nuevoProveedor.setSize(400,400);
    nuevoProveedor.setLocationRelativeTo(null);
    nuevoProveedor.setVisible(true);
}

public void modificarProveedor() {
    Proveedor p = nuevoProveedor.capturarDatos();
    proveedorConfig.getGestProveedor().modificarProveedor(p);
    cerrarDialogoProveedor();
    pCentral.poblarTabla(proveedorConfig.getGestProveedor().listar());
}

public void eliminarProveedor() {
    int codigo = pCentral.getItemSeleccionado();
    if(codigo == -1) return;

    int respuesta = JOptionPane.showConfirmDialog(
        this, 
        "¿Está seguro de eliminar el proveedor " + codigo + "?", 
        "Confirmar", 
        JOptionPane.YES_NO_OPTION
    );

    if (respuesta == JOptionPane.YES_OPTION) {
        proveedorConfig.getGestProveedor().eliminarProveedor(codigo);
        pCentral.poblarTabla(proveedorConfig.getGestProveedor().listar());
    }
}

// ===================== PRODUCTO =====================

public void lanzarDialogoProducto() {
    nuevoProducto = new DialogoProducto(evento, "Crear Producto", true);
    nuevoProducto.setSize(400,400);
    nuevoProducto.setLocationRelativeTo(null);
    nuevoProducto.setVisible(true);
}

public void cerrarDialogoProducto() {
    if(nuevoProducto != null){
        nuevoProducto.setVisible(false);
        nuevoProducto = null;
    }
}

public void crearProducto() {    
    pProducto.getGestionProducto().guardar(nuevoProducto.capturarDatos()); 
    cerrarDialogoProducto();
    pProducto.poblarTabla(pProducto.getGestionProducto().listar());
}

public void lanzarDialogoModificarProducto() {
    int codigo = pProducto.getItemSeleccionado();
    if(codigo == -1) return;

    Producto p = pProducto.getGestionProducto().buscar(codigo);

    nuevoProducto = new DialogoProducto(evento, "Modificar Producto", false);
    nuevoProducto.cargarDatos(p);
    nuevoProducto.setSize(400,400);
    nuevoProducto.setLocationRelativeTo(null);
    nuevoProducto.setVisible(true);
}

public void modificarProducto() {
    Producto p = nuevoProducto.capturarDatos();
    pProducto.getGestionProducto().actualizar(p);
    cerrarDialogoProducto();
    pProducto.poblarTabla(pProducto.getGestionProducto().listar());
}

public void eliminarProducto() {
    int codigo = pProducto.getItemSeleccionado();
    if(codigo == -1) return;

    int respuesta = JOptionPane.showConfirmDialog(
        this, 
        "¿Está seguro de eliminar el producto " + codigo + "?", 
        "Confirmar", 
        JOptionPane.YES_NO_OPTION
    );

    if (respuesta == JOptionPane.YES_OPTION) {
        pProducto.getGestionProducto().eliminar(codigo);
        pProducto.poblarTabla(pProducto.getGestionProducto().listar());
    }
}
public void buscarProducto() {
 try {
     String input = JOptionPane.showInputDialog(this, "Ingrese código del producto:");

     if (input == null || input.isEmpty()) return;

     int codigo = Integer.parseInt(input);

     Producto p = pProducto.getGestionProducto().buscar(codigo);

     if (p != null) {
         pProducto.poblarTabla(java.util.List.of(p)); 
     } else {
         JOptionPane.showMessageDialog(this, "Producto no encontrado");
     }

 } catch (Exception e) {
     JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
 }
}

public void limpiarProducto() {
 pProducto.poblarTabla(pProducto.getGestionProducto().listar());
}

public void verProducto() {
 int codigo = pProducto.getItemSeleccionado();
 if (codigo == -1) return;

 Producto p = pProducto.getGestionProducto().buscar(codigo);

 if (p != null) {
     JOptionPane.showMessageDialog(this,
         "Código: " + p.getCodigoProducto() +
         "\nNombre: " + p.getNombreProducto() +
         "\nCategoría: " + p.getCategoria() +
         "\nPrecio Compra: " + p.getPrecioCompra() +
         "\nPrecio Venta: " + p.getPrecioVenta() +
         "\nStock: " + p.getStockActual()
     );
 }
}
}