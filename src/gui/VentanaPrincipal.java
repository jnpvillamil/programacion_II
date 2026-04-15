package gui;

import negocio.NegocioControl;
import negocio.NegocioProveedor;
import modelo.Proveedor;
import modelo.Producto;
import negocio.NegocioCliente;
import modelo.Cliente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import guisubpanel.PanelRegistrarCliente;
import guisubpanel.PanelRegistrarCompra;
import guisubpanel.PanelRegistrarProducto;
import guisubpanel.PanelRegistrarProveedor;
import guisubpanel.PanelRegistrarVenta;
import negocio.NegocioProducto;
import java.awt.*;
import modelo.Factura;
import negocio.NegocioFactura;

public class VentanaPrincipal extends JFrame {

    private Evento evento;
    private NegocioFactura negFactura;
    private NegocioProducto negProducto;
    
    
    private NegocioCliente negCliente;
    private NegocioControl negControl;
    private NegocioProveedor negProveedor;
    private PanelLogin panelLogin;
    private PanelCentral panelCentral;
    private PanelProductos panelProductos;
    private PanelRegistrarProducto panelRegistrarProducto;

    private PanelFacturas panelFacturas;
    
    private PanelClientes panelClientes;
    private PanelRegistrarCliente panelRegistrarCliente;

    private PanelVentas panelVentas;
    private PanelRegistrarVenta panelRegistrarVenta;

    private PanelCompras panelCompras;
    private PanelRegistrarCompra panelRegistrarCompra;

    private PanelReportes panelReportes;

    private PanelProveedores panelProveedores;
    private PanelRegistrarProveedor panelRegistrarProveedor;

    public VentanaPrincipal() {

        evento = new Evento(this);

        setTitle("Sistema de Gestión");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        inicializarPaneles();
        mostrarPanelLogin();
    }

    private void inicializarPaneles() {
    	negControl = new NegocioControl();
    	negProveedor = new NegocioProveedor();
    	negFactura = new NegocioFactura();
    	negProducto = new NegocioProducto();
        panelLogin = new PanelLogin(evento);
        panelCentral = new PanelCentral(evento);

        panelProductos = new PanelProductos(evento);
        panelRegistrarProducto = new PanelRegistrarProducto(evento);

        panelClientes = new PanelClientes(evento);
        panelRegistrarCliente = new PanelRegistrarCliente(evento);
        negCliente = new NegocioCliente();
        
        panelVentas = new PanelVentas(evento);
        panelRegistrarVenta = new PanelRegistrarVenta(evento);

        panelCompras = new PanelCompras(evento);
        panelRegistrarCompra = new PanelRegistrarCompra(evento);

        panelReportes = new PanelReportes(evento);

        panelProveedores = new PanelProveedores(evento);
        panelRegistrarProveedor = new PanelRegistrarProveedor(evento);
      
        panelFacturas = new PanelFacturas(evento);
    }

    private void cambiarPanel(JPanel panel) {
        getContentPane().removeAll();

        if (panel instanceof PanelLogin) {
            JPanel fondo = new JPanel(new GridBagLayout());
            fondo.add(panel);
            add(fondo, BorderLayout.CENTER);
        } else {
            add(panel, BorderLayout.CENTER);
        }

        revalidate();
        repaint();
    }

    public void mostrarPanelLogin() {
        cambiarPanel(panelLogin);
    }

    public void mostrarPanelCentral() {
        cambiarPanel(panelCentral);
    }

    public void mostrarPanelProductos() {
        cambiarPanel(panelProductos);
        cargarTablaProductos(); 
    }

    public void mostrarPanelRegistrarProducto() {
        cambiarPanel(panelRegistrarProducto);
    }

    public void mostrarPanelClientes() {
        cambiarPanel(panelClientes);
        cargarTablaClientes(); 
    }

    public void mostrarPanelRegistrarCliente() {
        cambiarPanel(panelRegistrarCliente);
    }

    public void mostrarPanelVentas() {
        cambiarPanel(panelVentas);
    }

    public void mostrarPanelRegistrarVenta() {
        cambiarPanel(panelRegistrarVenta);
    }

    public void mostrarPanelCompras() {
        cambiarPanel(panelCompras);
    }

    public void mostrarPanelRegistrarCompra() {
        cambiarPanel(panelRegistrarCompra);
    }

    public void mostrarPanelReportes() {
        cambiarPanel(panelReportes);
    }
    public void mostrarPanelFacturas() {
        cambiarPanel(panelFacturas);
        cargarTablaFacturas();
    }
    public void mostrarPanelProveedores() {
        cargarTablaProveedores(); 
        cambiarPanel(panelProveedores);
    }

    public void cargarTablaProveedores() {

        DefaultTableModel modelo = panelProveedores.getModelo();
        modelo.setRowCount(0); 

        for (Proveedor p : negProveedor.getLista()) {

            if (p.isActivo()) {

                Object[] fila = {
                    p.getCodigo(),
                    p.getNombre(),
                    p.getNit(),
                    p.getTelefono()
                };

                modelo.addRow(fila);
            }
        }
    }
		
    public void cargarTablaFacturas() {

        DefaultTableModel modelo = panelFacturas.getModelo();
        modelo.setRowCount(0);

        for (Factura f : negFactura.getFacturas()) {

            Object[] fila = {
                f.getCodigoFactura(),
                f.getCodigoFactura(), 
                "-", 
                f.getTotal(),
                "-" 
            };

            modelo.addRow(fila);
        }
    }

	public void mostrarPanelRegistrarProveedor() {
        cambiarPanel(panelRegistrarProveedor);
    }



    public void loguear() {
        String usuario = panelLogin.getTxtUsuario().getText();
        String pass = new String(panelLogin.getTxtPassword().getPassword());

        if (usuario.equals("admin") && pass.equals("1234")) {
            JOptionPane.showMessageDialog(this, "Bienvenido");
            mostrarPanelCentral();
        } else {
            JOptionPane.showMessageDialog(this, "Datos incorrectos");
        }
    }

    public void guardarProducto() {

        try {

            String codigo = panelRegistrarProducto.getTxtCodigo().getText().trim();
            String nombre = panelRegistrarProducto.getTxtNombre().getText().trim();
            String categoria = panelRegistrarProducto.getTxtCategoria().getText().trim();

            String txtPrecioCompra = panelRegistrarProducto.getTxtPrecioCompra().getText().trim();
            String txtPrecioVenta = panelRegistrarProducto.getTxtPrecioVenta().getText().trim();
            String txtStockActual = panelRegistrarProducto.getTxtStockActual().getText().trim();
            String txtStockMinimo = panelRegistrarProducto.getTxtStockMinimo().getText().trim();

            if (codigo.isEmpty() || nombre.isEmpty() || categoria.isEmpty()
                    || txtPrecioCompra.isEmpty() || txtPrecioVenta.isEmpty()
                    || txtStockActual.isEmpty() || txtStockMinimo.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Debe llenar todos los campos");
                return;
            }
            if (negProducto.existeProducto(codigo)) {
                JOptionPane.showMessageDialog(this, "El producto ya existe");
                return;
            }
            if (!nombre.matches("[a-zA-Z0-9 ]+")) {
                JOptionPane.showMessageDialog(this, "Nombre inválido");
                return;
            }
            double precioCompra = Double.parseDouble(txtPrecioCompra);
            double precioVenta = Double.parseDouble(txtPrecioVenta);
            int stockActual = Integer.parseInt(txtStockActual);
            int stockMinimo = Integer.parseInt(txtStockMinimo);

            if (precioCompra <= 0 || precioVenta <= 0) {
                JOptionPane.showMessageDialog(this, "Los precios deben ser mayores a 0");
                return;
            }

            if (precioVenta <= precioCompra) {
                JOptionPane.showMessageDialog(this, "El precio de venta debe ser mayor al de compra");
                return;
            }

            if (stockActual < 0 || stockMinimo < 0) {
                JOptionPane.showMessageDialog(this, "Stock inválido");
                return;
            }

           
            Producto p = new Producto(codigo, nombre, categoria,
                    precioCompra, precioVenta, stockActual, stockMinimo);

            negProducto.agregarProducto(p);

            JOptionPane.showMessageDialog(this, "Producto registrado correctamente");

            panelRegistrarProducto.limpiarCampos();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos");
        }
    }
    public void historialProductos() {

        DefaultTableModel modelo = panelProductos.getModelo();
        modelo.setRowCount(0);

        for (Producto p : negProducto.getLista()) {

            Object[] fila = {
                p.getCodigo(),
                p.getNombre(),
                p.getCategoria(),
                p.getPrecioVenta(),
                p.getStockActual(),
                p.isActivo() ? "Activo" : "Inactivo"
            };
            modelo.addRow(fila);
        }
            
        }
   
       
    public void consultarProducto() {

        String codigo = JOptionPane.showInputDialog("Ingrese código del producto");

        Producto p = negProducto.buscarProducto(codigo);

        if (p != null && p.isActivo()) {

            JOptionPane.showMessageDialog(this,
                    "Código: " + p.getCodigo() +
                    "\nNombre: " + p.getNombre() +
                    "\nCategoría: " + p.getCategoria() +
                    "\nPrecio Compra: " + p.getPrecioCompra() +
                    "\nPrecio Venta: " + p.getPrecioVenta() +
                    "\nStock Actual: " + p.getStockActual() +
                    "\nStock Mínimo: " + p.getStockMinimo() +
                    "\nEstado: Activo");

        } else {
            JOptionPane.showMessageDialog(this, "Producto no encontrado o inactivo");
        }
    }
    public void modificarProducto() {

        int fila = panelProductos.getTabla().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto");
            return;
        }

        String codigo = panelProductos.getTabla().getValueAt(fila, 0).toString();

        Producto p = negProducto.buscarProducto(codigo);

        if (p != null && p.isActivo()) {

            JTextField txtNombre = new JTextField(p.getNombre());
            JTextField txtCategoria = new JTextField(p.getCategoria());
            JTextField txtPrecioCompra = new JTextField(String.valueOf(p.getPrecioCompra()));
            JTextField txtPrecioVenta = new JTextField(String.valueOf(p.getPrecioVenta()));
            JTextField txtStockActual = new JTextField(String.valueOf(p.getStockActual()));
            JTextField txtStockMinimo = new JTextField(String.valueOf(p.getStockMinimo()));

            Object[] mensaje = {
                "Nombre:", txtNombre,
                "Categoría:", txtCategoria,
                "Precio Compra:", txtPrecioCompra,
                "Precio Venta:", txtPrecioVenta,
                "Stock Actual:", txtStockActual,
                "Stock Mínimo:", txtStockMinimo
            };

            int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Modificar Producto", JOptionPane.OK_CANCEL_OPTION);

            if (opcion == JOptionPane.OK_OPTION) {

                p.setNombre(txtNombre.getText());
                p.setCategoria(txtCategoria.getText());
                p.setPrecioCompra(Double.parseDouble(txtPrecioCompra.getText()));
                p.setPrecioVenta(Double.parseDouble(txtPrecioVenta.getText()));
                p.setStockActual(Integer.parseInt(txtStockActual.getText()));
                p.setStockMinimo(Integer.parseInt(txtStockMinimo.getText()));

                cargarTablaProductos();

                JOptionPane.showMessageDialog(this, "Producto modificado");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Producto no encontrado o inactivo");
        }
    }
    public void inactivarProducto() {

        int fila = panelProductos.getTabla().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto");
            return;
        }

        String codigo = panelProductos.getTabla().getValueAt(fila, 0).toString();

        Producto p = negProducto.buscarProducto(codigo);

        if (p != null && p.isActivo()) {

            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Desea inactivar el producto?",
                    "Confirmar", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {

                negProducto.inactivarProducto(codigo);

                cargarTablaProductos();

                JOptionPane.showMessageDialog(this, "Producto inactivado");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Producto no encontrado o ya inactivo");
        }
    }
    public void cargarTablaProductos() {

        DefaultTableModel modelo = panelProductos.getModelo();
        modelo.setRowCount(0);

        for (Producto p : negProducto.getLista()) {

            Object[] fila = {
                p.getCodigo(),
                p.getNombre(),
                p.getPrecioVenta(),
                "Activo" 
            };

            modelo.addRow(fila);
        }
    }
    public void consultarCliente() {

        String codigo = JOptionPane.showInputDialog("Ingrese código del cliente");

        Cliente c = negCliente.buscarCliente(codigo);

        if (c != null && c.isActivo()) {

            JOptionPane.showMessageDialog(this,
                    "Código: " + c.getCodigo() +
                    "\nNombre: " + c.getNombre() +
                    "\nIdentificación: " + c.getNumeroIdentificacion() +
                    "\nTeléfono: " + c.getTelefono() +
                    "\nTipo Cliente: " + c.getTipoCliente());

        } else {
            JOptionPane.showMessageDialog(this, "Cliente no encontrado o inactivo");
        }
    }
    public void modificarCliente() {

        int fila = panelClientes.getTabla().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente");
            return;
        }

        String codigo = panelClientes.getTabla().getValueAt(fila, 0).toString();

        Cliente c = negCliente.buscarCliente(codigo);

        if (c != null && c.isActivo()) {

            JTextField txtNombre = new JTextField(c.getNombre());
            JTextField txtTelefono = new JTextField(c.getTelefono());
            JTextField txtDireccion = new JTextField(c.getDireccion());
            JTextField txtTipoCliente = new JTextField(c.getTipoCliente());

            Object[] mensaje = {
                "Nombre:", txtNombre,
                "Teléfono:", txtTelefono,
                "Dirección:", txtDireccion,
                "Tipo Cliente:", txtTipoCliente
            };

            int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Modificar Cliente", JOptionPane.OK_CANCEL_OPTION);

            if (opcion == JOptionPane.OK_OPTION) {

                c.setNombre(txtNombre.getText());
                c.setTelefono(txtTelefono.getText());
                c.setDireccion(txtDireccion.getText());
                c.setTipoCliente(txtTipoCliente.getText());

                cargarTablaClientes();

                JOptionPane.showMessageDialog(this, "Cliente modificado");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Cliente no encontrado o inactivo");
        }
    }
    
    public void inactivarCliente() {

        int fila = panelClientes.getTabla().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente");
            return;
        }

        String codigo = panelClientes.getTabla().getValueAt(fila, 0).toString();

        Cliente c = negCliente.buscarCliente(codigo);

        if (c != null && c.isActivo()) {

            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Desea inactivar el cliente?",
                    "Confirmar", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {

                negCliente.inactivarCliente(codigo);

                cargarTablaClientes();

                JOptionPane.showMessageDialog(this, "Cliente inactivado");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Cliente no encontrado o ya inactivo");
        }
    }
    public void historialClientes() {

        DefaultTableModel modelo = panelClientes.getModelo();
        modelo.setRowCount(0);

        for (Cliente c : negCliente.getLista()) {

            Object[] fila = {
                c.getCodigo(),
                c.getNombre(),
                c.getTelefono(),
               
                c.isActivo() ? "Activo" : "Inactivo"
            };

            modelo.addRow(fila);
        }

        JOptionPane.showMessageDialog(this, "Historial cargado");
    }
    public void guardarCliente() {

        String codigo = panelRegistrarCliente.getTxtCodigo().getText().trim();
        String nombre = panelRegistrarCliente.getTxtNombre().getText().trim();
        String tipoId = panelRegistrarCliente.getTxtTipoIdentificacion().getText().trim();
        String numeroId = panelRegistrarCliente.getTxtNumeroIdentificacion().getText().trim();
        String direccion = panelRegistrarCliente.getTxtDireccion().getText().trim();
        String telefono = panelRegistrarCliente.getTxtTelefono().getText().trim();
        String tipoCliente = panelRegistrarCliente.getTxtTipoCliente().getText().trim();


        if (codigo.isEmpty() || nombre.isEmpty() || tipoId.isEmpty()
                || numeroId.isEmpty() || direccion.isEmpty()
                || telefono.isEmpty() || tipoCliente.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Debe llenar todos los campos");
            return;
        }
        if (negCliente.existeCliente(codigo)) {
            JOptionPane.showMessageDialog(this, "El cliente ya existe");
            return;
        } 
        if (!nombre.matches("[a-zA-Z ]+")) {
            JOptionPane.showMessageDialog(this, "El nombre solo debe contener letras");
            return;
        }
        if (!telefono.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "El teléfono solo debe contener números");
            return;
        }
        if (!numeroId.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "La identificación solo debe contener números");
            return;
        } 
        if (telefono.length() < 7) {
            JOptionPane.showMessageDialog(this, "Teléfono inválido");
            return;
        }
        Cliente c = new Cliente(codigo, nombre, tipoId,
                numeroId, direccion, telefono, tipoCliente);

        negCliente.agregarCliente(c);

        JOptionPane.showMessageDialog(this, "Cliente registrado correctamente");

        panelRegistrarCliente.limpiarCampos();
    }
    public void cargarTablaClientes() {

        DefaultTableModel modelo = panelClientes.getModelo();
        modelo.setRowCount(0);

        for (Cliente c : negCliente.getLista()) {

            if (c.isActivo()) {

            	Object[] fila = {
            		    c.getCodigo(),
            		    c.getNombre(),
            		    c.getTelefono(),
            		    c.isActivo() ? "Activo" : "Inactivo"
            		};

                modelo.addRow(fila);
                
              
            }
        
        }
       
    }
    public void calcularVenta() {
        try {
            int cantidad = Integer.parseInt(panelRegistrarVenta.getTxtCantidad().getText().trim());
            double precio = Double.parseDouble(panelRegistrarVenta.getTxtPrecioUnitario().getText().trim());

            double subtotal = cantidad * precio;
            double iva = subtotal * 0.19;
            double total = subtotal + iva;

            panelRegistrarVenta.getTxtSubtotal().setText(String.valueOf(subtotal));
            panelRegistrarVenta.getTxtIva().setText(String.valueOf(iva));
            panelRegistrarVenta.getTxtTotal().setText(String.valueOf(total));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en los datos numéricos");
        }
    }

    public void guardarVenta() {

        try {

            String codVenta = panelRegistrarVenta.getTxtCodigoVenta().getText().trim();
            String codCliente = panelRegistrarVenta.getTxtCodigoCliente().getText().trim();
            String codProducto = panelRegistrarVenta.getTxtCodigoProducto().getText().trim();
            String txtCantidad = panelRegistrarVenta.getTxtCantidad().getText().trim();
            String txtPrecio = panelRegistrarVenta.getTxtPrecioUnitario().getText().trim();

            if (codVenta.isEmpty() || codCliente.isEmpty() || codProducto.isEmpty()
                    || txtCantidad.isEmpty() || txtPrecio.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Complete todos los campos");
                return;
            }

            Cliente cliente = negCliente.buscarCliente(codCliente);

            if (cliente == null || !cliente.isActivo()) {
                JOptionPane.showMessageDialog(this, "Cliente no existe o está inactivo");
                return;
            }

            Producto producto = negProducto.buscarProducto(codProducto);

            if (producto == null || !producto.isActivo()) {
                JOptionPane.showMessageDialog(this, "Producto no existe o está inactivo");
                return;
            }

            int cantidad = Integer.parseInt(txtCantidad);
            double precio = Double.parseDouble(txtPrecio);

            if (cantidad <= 0 || precio <= 0) {
                JOptionPane.showMessageDialog(this, "Cantidad y precio deben ser mayores a 0");
                return;
            }

            if (cantidad > producto.getStockActual()) {
                JOptionPane.showMessageDialog(this, "Stock insuficiente");
                return;
            }
            if (precio != producto.getPrecioVenta()) {
                JOptionPane.showMessageDialog(this, "El precio no coincide con el producto");
                return;
            }
            producto.setStockActual(producto.getStockActual() - cantidad);

            JOptionPane.showMessageDialog(this, "Venta registrada correctamente");

            generarFactura();

            panelRegistrarVenta.limpiarCampos();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Datos numéricos inválidos");
        }
    }
    public void mostrarReporteVentas() {

        double totalVentas = 0;
        int cantidadFacturas = negFactura.getFacturas().size();

        for (Factura f : negFactura.getFacturas()) {
            totalVentas += f.getTotal();
        }

        JOptionPane.showMessageDialog(this,
                "REPORTE DE VENTAS\n\n" +
                "Cantidad de facturas: " + cantidadFacturas +
                "\nTotal vendido: $" + totalVentas
        );
    }

    public void calcularCompra() {
        try {
            int cantidad = Integer.parseInt(panelRegistrarCompra.getTxtCantidad().getText().trim());
            double precio = Double.parseDouble(panelRegistrarCompra.getTxtPrecio().getText().trim());

            double subtotal = cantidad * precio;
            double iva = subtotal * 0.19;
            double total = subtotal + iva;

            panelRegistrarCompra.getTxtSubtotal().setText(String.valueOf(subtotal));
            panelRegistrarCompra.getTxtIva().setText(String.valueOf(iva));
            panelRegistrarCompra.getTxtTotal().setText(String.valueOf(total));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en los datos");
        }
    }

    public void guardarCompra() {
        if (panelRegistrarCompra.getTxtCodigoCompra().getText().trim().isEmpty()
                || panelRegistrarCompra.getTxtCodigoProveedor().getText().trim().isEmpty()
                || panelRegistrarCompra.getTxtCodigoProducto().getText().trim().isEmpty()
                || panelRegistrarCompra.getTxtCantidad().getText().trim().isEmpty()
                || panelRegistrarCompra.getTxtPrecio().getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Complete todos los campos");
        } else {
            JOptionPane.showMessageDialog(this, "Compra registrada correctamente");
            panelRegistrarCompra.limpiarCampos();
        }
    }

    public void guardarProveedor() {

        String codigo = panelRegistrarProveedor.getTxtCodigo().getText().trim();
        String nombre = panelRegistrarProveedor.getTxtNombre().getText().trim();
        String nit = panelRegistrarProveedor.getTxtNit().getText().trim();
        String direccion = panelRegistrarProveedor.getTxtDireccion().getText().trim();
        String telefono = panelRegistrarProveedor.getTxtTelefono().getText().trim();
        String email = panelRegistrarProveedor.getTxtEmail().getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty() || nit.isEmpty()
                || direccion.isEmpty() || telefono.isEmpty() || email.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Debe llenar todos los campos");

        } else {

        	Proveedor p = new Proveedor(codigo, nombre, nit, direccion, telefono, email);

        	negProveedor.agregarProveedor(p);
        	negControl.registrarCambio("CREAR PROVEEDOR", codigo);
        
        	JOptionPane.showMessageDialog(this, "Proveedor registrado correctamente");
        	panelRegistrarProveedor.limpiarCampos();
        	mostrarPanelProveedores();
        }
    }
    public void modificarProveedor() {

        int fila = panelProveedores.getTabla().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor");
            return;
        }

        String codigo = panelProveedores.getTabla().getValueAt(fila, 0).toString();

        Proveedor p = negProveedor.buscarProveedor(codigo);

        if (p != null) {

            JTextField txtNombre = new JTextField(p.getNombre());
            JTextField txtNit = new JTextField(p.getNit());
            JTextField txtDireccion = new JTextField(p.getDireccion());
            JTextField txtTelefono = new JTextField(p.getTelefono());
            JTextField txtEmail = new JTextField(p.getEmail());

            Object[] mensaje = {
                "Nombre:", txtNombre,
                "NIT:", txtNit,
                "Dirección:", txtDireccion,
                "Teléfono:", txtTelefono,
                "Email:", txtEmail
            };

            int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Modificar Proveedor", JOptionPane.OK_CANCEL_OPTION);

            if (opcion == JOptionPane.OK_OPTION) {

                p.setNombre(txtNombre.getText());
                p.setNit(txtNit.getText());
                p.setDireccion(txtDireccion.getText());
                p.setTelefono(txtTelefono.getText());
                p.setEmail(txtEmail.getText());

                cargarTablaProveedores();

                JOptionPane.showMessageDialog(this, "Proveedor modificado");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Proveedor no encontrado");
        }
    }
    public void consultarProveedor() {

        int fila = panelProveedores.getTabla().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor");
            return;
        }

        String codigo = panelProveedores.getTabla().getValueAt(fila, 0).toString();

        Proveedor p = negProveedor.buscarProveedor(codigo);

        if (p != null && p.isActivo()) {

            JOptionPane.showMessageDialog(this,
                    "Código: " + p.getCodigo() +
                    "\nNombre: " + p.getNombre() +
                    "\nNIT: " + p.getNit() +
                    "\nDirección: " + p.getDireccion() +
                    "\nTeléfono: " + p.getTelefono() +
                    "\nEmail: " + p.getEmail());

        } else {
            JOptionPane.showMessageDialog(this, "Proveedor no encontrado o inactivo");
        }
    }

   
    
   
    public void eliminarProveedor() {

        int fila = panelProveedores.getTabla().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor");
            return;
        }

        String codigo = panelProveedores.getTabla().getValueAt(fila, 0).toString();

        Proveedor p = negProveedor.buscarProveedor(codigo);

        if (p != null && p.isActivo()) {

            int confirmar = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que desea inactivar el proveedor?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION);

            if (confirmar == JOptionPane.YES_OPTION) {

                negProveedor.eliminarProveedor(codigo);

                cargarTablaProveedores();

                JOptionPane.showMessageDialog(this, "Proveedor inactivado");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Proveedor no encontrado o ya inactivo");
        }
    }
    public void generarFactura() {

        String codigoFactura = "F" + System.currentTimeMillis();
        String codigoVenta = panelRegistrarVenta.getTxtCodigoVenta().getText();

        double subtotal = Double.parseDouble(panelRegistrarVenta.getTxtSubtotal().getText());
        double iva = Double.parseDouble(panelRegistrarVenta.getTxtIva().getText());
        double total = Double.parseDouble(panelRegistrarVenta.getTxtTotal().getText());

        String metodoPago = panelRegistrarVenta.getCbFormaPago().getSelectedItem().toString();

        String fecha = java.time.LocalDate.now().toString();

        Factura f = new Factura(codigoFactura, codigoVenta, fecha, subtotal, iva, total, metodoPago);

        negFactura.agregarFactura(f);

        JOptionPane.showMessageDialog(this, "Factura generada correctamente");
    }
 
   
    public void salirSistema() {
        System.exit(0);
    }
}