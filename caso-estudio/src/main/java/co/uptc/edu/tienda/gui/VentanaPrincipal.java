package co.uptc.edu.tienda.gui;

import javax.swing.*;

import co.uptc.edu.tienda.enums.EstadoEnum;
import co.uptc.edu.tienda.modelo.Cliente;
import co.uptc.edu.tienda.modelo.Producto;
import co.uptc.edu.tienda.modelo.Proveedor;
import co.uptc.edu.tienda.negocio.ClienteConfig;
import co.uptc.edu.tienda.negocio.GestionSeguridad;
import co.uptc.edu.tienda.negocio.ProductoConfig;
import co.uptc.edu.tienda.negocio.ProveedorConfig;
import co.uptc.edu.tienda.negocio.dto.CredencialDto;

import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private PanelLogin pLogin;

    private PanelPadreProveedor pProveedor;
    private PanelPadreProducto pProducto;
    private PanelPadreCliente pCliente;

    private JPanel contenedor;

    private JButton btnProveedor;
    private JButton btnProducto;
    private JButton btnCliente;

    private GestionSeguridad seguridad;

    private DialogoProveedor nuevoProveedor;
    private DialogoProducto nuevoProducto;
    private DialogoCliente nuevoCliente;

    private Evento evento;

    private ProveedorConfig proveedorConfig;
    private ProductoConfig productoConfig;
    private ClienteConfig clienteConfig;

    public VentanaPrincipal() {

        setSize(400,300);
        setTitle("Tienda Minorista");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        evento = new Evento(this);

        pLogin = new PanelLogin(evento);

        pProveedor = new PanelPadreProveedor(evento);
        pProducto = new PanelPadreProducto(evento);
        pCliente = new PanelPadreCliente(evento);

        seguridad = new GestionSeguridad();

        proveedorConfig = new ProveedorConfig();
        productoConfig = new ProductoConfig();
        clienteConfig = new ClienteConfig();

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
                btnCliente = new JButton("Cliente");

                panelBotones.add(btnProveedor);
                panelBotones.add(btnProducto);
                panelBotones.add(btnCliente);

                add(panelBotones, BorderLayout.NORTH);

                contenedor = new JPanel(new BorderLayout());

                add(contenedor, BorderLayout.CENTER);

                contenedor.add(pProveedor);

                pProveedor.poblarTabla(
                    proveedorConfig.getGestProveedor().leerProveedores()
                );

                pProducto.poblarTabla(
                    productoConfig.getGestProducto().listar()
                );

                pCliente.poblarTabla(
                    clienteConfig.getGestCliente().leerClientes()
                );

                btnProveedor.addActionListener(e -> {

                    contenedor.removeAll();

                    contenedor.add(pProveedor);

                    pProveedor.poblarTabla(
                        proveedorConfig.getGestProveedor().leerProveedores()
                    );

                    contenedor.repaint();
                    contenedor.revalidate();
                });

                btnProducto.addActionListener(e -> {

                    contenedor.removeAll();

                    contenedor.add(pProducto);

                    pProducto.poblarTabla(
                        productoConfig.getGestProducto().listar()
                    );

                    contenedor.repaint();
                    contenedor.revalidate();
                });

                btnCliente.addActionListener(e -> {

                    contenedor.removeAll();

                    contenedor.add(pCliente);

                    pCliente.poblarTabla(
                        clienteConfig.getGestCliente().leerClientes()
                    );

                    contenedor.repaint();
                    contenedor.revalidate();
                });

                this.setSize(900,500);

                repaint();
                revalidate();

            } else {

                JOptionPane.showMessageDialog(
                    this,
                    "Usuario o contraseña no válida"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                this,
                e.getMessage()
            );
        }
    }

    // ===================== PROVEEDOR =====================

    public void lanzarDialogoProveedor() {

        nuevoProveedor = new DialogoProveedor(
            evento,
            "Crear Proveedor",
            true
        );

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

        try {

            proveedorConfig.getGestProveedor().agregarProveedor(
                nuevoProveedor.capturarDatos()
            );

            cerrarDialogoProveedor();

            pProveedor.poblarTabla(
                proveedorConfig.getGestProveedor().leerProveedores()
            );

        } catch(Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                this,
                e.getMessage()
            );
        }
        JOptionPane.showMessageDialog(this, "Proveedor agregado exitosamente.");
    }

    public void lanzarDialogoModificarProveedor() {

        int codigo = pProveedor.getItemSeleccionado();

        if(codigo == -1) {
        	JOptionPane.showMessageDialog(this, "Por favor, seleccione un proveedor de la tabla.");
        	return;
        }

        Proveedor p = proveedorConfig
                .getGestProveedor()
                .buscarProveedorPorCodigo(codigo);

        nuevoProveedor = new DialogoProveedor(
            evento,
            "Modificar Proveedor",
            false
        );

        nuevoProveedor.cargarDatos(p);

        nuevoProveedor.setSize(400,400);
        nuevoProveedor.setLocationRelativeTo(null);
        nuevoProveedor.setVisible(true);
    }

    public void modificarProveedor() {

        int codigo = pProveedor.getItemSeleccionado();

        if(codigo == -1) return;
        
        try {

            Proveedor p = nuevoProveedor.capturarDatos();

            proveedorConfig.getGestProveedor().modificarProveedor(p);


            pProveedor.poblarTabla(
                proveedorConfig.getGestProveedor().leerProveedores()
            );
            JOptionPane.showMessageDialog(this, "Proveedor modificado exitosamente.");
            cerrarDialogoProveedor();

        } catch(Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                this,
                e.getMessage()
            );
        }
        
    }

    public void eliminarProveedor() {

        int codigo = pProveedor.getItemSeleccionado();

        if(codigo == -1) {
        	JOptionPane.showMessageDialog(this,"Por favor, seleccione un proveedor de la tabla.");
        	return;
        }
 try {
            
            Proveedor p = proveedorConfig.getGestProveedor().buscarProveedorPorCodigo(codigo);
            
            if (p != null && p.getEstado() == EstadoEnum.INACTIVO) {
                JOptionPane.showMessageDialog(this, "El proveedor ya se encuentra inactivo.");
                return; // 
            }

            
            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro de inactivar el proveedor " + codigo + "?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
                );

                if (respuesta == JOptionPane.YES_OPTION) {

                    proveedorConfig.getGestProveedor().eliminarProveedor(codigo);

                    pProveedor.poblarTabla(
                        proveedorConfig.getGestProveedor().leerProveedores()
                    );
                }
                JOptionPane.showMessageDialog(this, "Proveedor inactivado exitosamente.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
        
    }

    public void verProveedor() {

        int codigo = pProveedor.getItemSeleccionado();

        if (codigo == -1) {
        	JOptionPane.showMessageDialog(this,"Por favor, seleccione un proveedor de la tabla.");
        	return;
        }

        Proveedor p = proveedorConfig
                .getGestProveedor()
                .buscarProveedorPorCodigo(codigo);

        if (p != null) {

            JOptionPane.showMessageDialog(
                this,
                "Código: " + p.getCodigoProveedor() +
                "\nRazón social: " + p.getRazonSocial() +
                "\nNIT: " + p.getNit() +
                "\nDirección: " + p.getDireccionP() +
                "\nTeléfono: " + p.getTelefonoP() +
                "\nCorreo electrónico: " + p.getCorreoP()
            );
        }
    }

    public void buscarProveedor() {

        try {

            String input = JOptionPane.showInputDialog(
                this,
                "Ingrese código del proveedor:"
            );

            if (input == null || input.isEmpty()) return;

            int codigo = Integer.parseInt(input);

            Proveedor p = proveedorConfig
                    .getGestProveedor()
                    .buscarProveedorPorCodigo(codigo);

            if (p != null) {

                pProveedor.poblarTabla(java.util.List.of(p));

            } else {

                JOptionPane.showMessageDialog(
                    this,
                    "Proveedor no encontrado"
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                this,
                "Error: " + e.getMessage()
            );
        }
    }
    
    public void limpiarProveedor() {

        pProveedor.poblarTabla(
            proveedorConfig.getGestProveedor().leerProveedores()
        );
    }
    
    public void activarProveedor() {
    	int codigo = pProveedor.getItemSeleccionado();

        if (codigo == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un proveedor de la tabla");
            return;
        }

        try {
            
            Proveedor p = proveedorConfig.getGestProveedor().buscarProveedorPorCodigo(codigo);
            
            if (p != null && p.getEstado() == EstadoEnum.ACTIVO) {
                JOptionPane.showMessageDialog(this, "El proveedor ya se encuentra activo.");
                return; // 
            }
            
            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro de activar el proveedor " + codigo + "?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
                );

                if (respuesta == JOptionPane.YES_OPTION) {

                    proveedorConfig.getGestProveedor().activarProveedor(codigo);

                    pProveedor.poblarTabla(
                        proveedorConfig.getGestProveedor().leerProveedores()
                    );
                }
                JOptionPane.showMessageDialog(this, "Proveedor activado exitosamente.");

            
           

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ===================== CLIENTE =====================

    public void lanzarDialogoCliente() {

        nuevoCliente = new DialogoCliente(
            evento,
            "Crear Cliente",
            true
        );

        nuevoCliente.setSize(400,400);
        nuevoCliente.setLocationRelativeTo(null);
        nuevoCliente.setVisible(true);
    }

    public void cerrarDialogoCliente() {

        if(nuevoCliente != null){

            nuevoCliente.setVisible(false);
            nuevoCliente = null;
        }
    }

    public void crearCliente() {

        try {

            clienteConfig.getGestCliente().agregarCliente(
                nuevoCliente.capturarDatos()
            );

            cerrarDialogoCliente();

            pCliente.poblarTabla(
                clienteConfig.getGestCliente().leerClientes()
            );

        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                this,
                e.getMessage()
            );
        }
    }

    public void lanzarDialogoModificarCliente() {

        int codigo = pCliente.getItemSeleccionado();

        if(codigo == -1) return;

        Cliente c = clienteConfig
                .getGestCliente()
                .buscarClientePorCodigo(codigo);

        nuevoCliente = new DialogoCliente(
            evento,
            "Modificar Cliente",
            false
        );

        nuevoCliente.cargarDatos(c);

        nuevoCliente.setSize(400,400);
        nuevoCliente.setLocationRelativeTo(null);
        nuevoCliente.setVisible(true);
    }

    public void modificarCliente() {

        try {

            Cliente c = nuevoCliente.capturarDatos();

            clienteConfig.getGestCliente().modificarCliente(c);

            cerrarDialogoCliente();

            pCliente.poblarTabla(
                clienteConfig.getGestCliente().leerClientes()
            );

        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                this,
                e.getMessage()
            );
        }
    }

    public void eliminarCliente() {

        int codigo = pCliente.getItemSeleccionado();

        if (codigo == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un cliente de la tabla");
            return;
        }

        try {

            Cliente c = clienteConfig.getGestCliente().buscarClientePorCodigo(codigo);

            if (c == null) {
                JOptionPane.showMessageDialog(this,
                    "Cliente no encontrado");
                return;
            }

            if (c.getEstado() == EstadoEnum.INACTIVO) {
                JOptionPane.showMessageDialog(this,
                    "El cliente ya está inactivo");
                return;
            }

            int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de inactivar el cliente " + codigo + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
            );

            if (respuesta == JOptionPane.YES_OPTION) {

                // 🔴 NO BORRAMOS, SOLO INACTIVAMOS (como proveedor)
                c.setEstado(EstadoEnum.INACTIVO);

                clienteConfig.getGestCliente().modificarCliente(c);

                pCliente.poblarTabla(
                    clienteConfig.getGestCliente().leerClientes()
                );

                JOptionPane.showMessageDialog(this,
                    "Cliente inactivado exitosamente.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void buscarCliente() {

        try {

            String input = JOptionPane.showInputDialog(
                this,
                "Ingrese código del cliente:"
            );

            if (input == null || input.isEmpty()) return;

            int codigo = Integer.parseInt(input);

            Cliente c = clienteConfig
                    .getGestCliente()
                    .buscarClientePorCodigo(codigo);

            if (c != null) {

                pCliente.poblarTabla(java.util.List.of(c));

            } else {

                JOptionPane.showMessageDialog(
                    this,
                    "Cliente no encontrado"
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                this,
                "Error: " + e.getMessage()
            );
        }
    }

    public void verCliente() {

        int codigo = pCliente.getItemSeleccionado();

        if (codigo == -1) return;

        Cliente c = clienteConfig
                .getGestCliente()
                .buscarClientePorCodigo(codigo);

        if (c != null) {

            JOptionPane.showMessageDialog(
                this,
                "Código: " + c.getIdCliente() +
                "\nNombre: " + c.getNombreCompleto() +
                "\nDirección: " + c.getDireccionC() +
                "\nTeléfono: " + c.getTelefonoC()
            );
        }
    }
        public void limpiarCliente() {

            pCliente.poblarTabla(
                clienteConfig.getGestCliente().leerClientes()
            );
        }
        public void activarCliente() {

            int codigo = pCliente.getItemSeleccionado();

            if (codigo == -1) {
                JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione un cliente de la tabla");
                return;
            }

            try {

                Cliente c = clienteConfig.getGestCliente().buscarClientePorCodigo(codigo);

                if (c != null && c.getEstado() == EstadoEnum.ACTIVO) {
                    JOptionPane.showMessageDialog(this,
                        "El cliente ya se encuentra activo.");
                    return;
                }

                int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro de activar el cliente " + codigo + "?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
                );

                if (respuesta == JOptionPane.YES_OPTION) {

                    clienteConfig.getGestCliente().activarCliente(codigo);

                    pCliente.poblarTabla(
                        clienteConfig.getGestCliente().leerClientes()
                    );
                }

                JOptionPane.showMessageDialog(this,
                    "Cliente activado exitosamente.");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
      

    // ===================== PRODUCTO =====================

    public void lanzarDialogoProducto() {

        nuevoProducto = new DialogoProducto(
            evento,
            "Crear Producto",
            true
        );

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

        productoConfig.getGestProducto().guardar(
            nuevoProducto.capturarDatos()
        );

        cerrarDialogoProducto();

        pProducto.poblarTabla(
            productoConfig.getGestProducto().listar()
        );
    }

    public void lanzarDialogoModificarProducto() {

        int codigo = pProducto.getItemSeleccionado();

        if(codigo == -1) return;

        Producto p = productoConfig
                .getGestProducto()
                .buscar(codigo);

        nuevoProducto = new DialogoProducto(
            evento,
            "Modificar Producto",
            false
        );

        nuevoProducto.cargarDatos(p);

        nuevoProducto.setSize(400,400);
        nuevoProducto.setLocationRelativeTo(null);
        nuevoProducto.setVisible(true);
    }

    public void modificarProducto() {

        Producto p = nuevoProducto.capturarDatos();

        productoConfig.getGestProducto().actualizar(p);

        cerrarDialogoProducto();

        pProducto.poblarTabla(
            productoConfig.getGestProducto().listar()
        );
    }

    public void inactivarProducto() {

        int codigo = pProducto.getItemSeleccionado();

        if(codigo == -1) return;

        productoConfig.getGestProducto().inactivar(codigo);

        pProducto.poblarTabla(
            productoConfig.getGestProducto().listar()
        );
    }

    public void activarProducto() {

        int codigo = pProducto.getItemSeleccionado();

        if(codigo == -1) return;

        productoConfig.getGestProducto().activar(codigo);

        pProducto.poblarTabla(
            productoConfig.getGestProducto().listar()
        );
    }

    public void buscarProducto() {

        try {

            String input = JOptionPane.showInputDialog(
                this,
                "Ingrese código del producto:"
            );

            if (input == null || input.isEmpty()) return;

            int codigo = Integer.parseInt(input);

            Producto p = productoConfig
                    .getGestProducto()
                    .buscar(codigo);

            if (p != null) {

                pProducto.poblarTabla(java.util.List.of(p));

            } else {

                JOptionPane.showMessageDialog(
                    this,
                    "Producto no encontrado"
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                this,
                "Error: " + e.getMessage()
            );
        }
    }

    public void limpiarProducto() {

        pProducto.poblarTabla(
            productoConfig.getGestProducto().listar()
        );
    }

    public void verProducto() {

        int codigo = pProducto.getItemSeleccionado();

        if (codigo == -1) return;

        Producto p = productoConfig
                .getGestProducto()
                .buscar(codigo);

        if (p != null) {

            JOptionPane.showMessageDialog(
                this,
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