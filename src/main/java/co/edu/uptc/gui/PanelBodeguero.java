package co.edu.uptc.gui;

<<<<<<< HEAD
import co.edu.uptc.dto.ProductoResumenDTO;
=======
import co.edu.uptc.dto.BodegueroDTO;
import co.edu.uptc.enums.RolUsuario;
>>>>>>> 7d4245951cf20ce153a582cbbc5a372a9ca9ae72
import co.edu.uptc.interfaces.ManejadorEventoBodeguero;
import co.edu.uptc.utilidades.ConstructorComponentes;

import javax.swing.*;
<<<<<<< HEAD
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
=======
import java.awt.*;
>>>>>>> 7d4245951cf20ce153a582cbbc5a372a9ca9ae72

public class PanelBodeguero extends JPanel {

    private final ManejadorEventoBodeguero manejadorEvento;

<<<<<<< HEAD
    private DefaultTableModel modeloTablaCritico;
    private JTable tablaInventarioCritico;
=======
    private JTextField txtLogin;
    private JPasswordField txtClave;
    private JTextField txtZonaBodega;
>>>>>>> 7d4245951cf20ce153a582cbbc5a372a9ca9ae72

    public PanelBodeguero(ManejadorEventoBodeguero manejadorEvento) {
        this.manejadorEvento = manejadorEvento;

        setLayout(new BorderLayout(20, 20));
        ConstructorComponentes.aplicarFondoPanel(this);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

<<<<<<< HEAD
        JLabel titulo = ConstructorComponentes.crearTituloModulo("Alertas de Inventario Crítico");
        add(titulo, BorderLayout.NORTH);
        add(construirPanelInventarioCritico(), BorderLayout.SOUTH);

        actualizarTablaInventarioCritico();
=======
        JLabel titulo = ConstructorComponentes.crearTituloModulo("Registro de Bodeguero");
        add(titulo, BorderLayout.NORTH);
        add(construirFormulario(), BorderLayout.CENTER);
    }

    private JPanel construirFormulario() {
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        ConstructorComponentes.aplicarFondoPanel(panelFormulario);
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Bodeguero"));

        GridBagConstraints restriccion = new GridBagConstraints();
        restriccion.fill = GridBagConstraints.HORIZONTAL;
        restriccion.insets = new Insets(8, 10, 8, 10);
        restriccion.weightx = 1.0;

        txtLogin = ConstructorComponentes.crearCampoTexto();
        txtClave = new JPasswordField(15);
        txtClave.setBorder(txtLogin.getBorder());
        txtZonaBodega = ConstructorComponentes.crearCampoTexto();

        restriccion.gridy = 0;
        restriccion.gridx = 0;
        restriccion.weightx = 0.3;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Login:"), restriccion);

        restriccion.gridx = 1;
        restriccion.weightx = 0.7;
        panelFormulario.add(txtLogin, restriccion);

        restriccion.gridy = 1;
        restriccion.gridx = 0;
        restriccion.weightx = 0.3;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Contraseña:"), restriccion);

        restriccion.gridx = 1;
        restriccion.weightx = 0.7;
        panelFormulario.add(txtClave, restriccion);

        restriccion.gridy = 2;
        restriccion.gridx = 0;
        restriccion.weightx = 0.3;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Zona de Bodega:"), restriccion);

        restriccion.gridx = 1;
        restriccion.weightx = 0.7;
        panelFormulario.add(txtZonaBodega, restriccion);

        restriccion.gridy = 3;
        restriccion.gridx = 0;
        restriccion.gridwidth = 2;
        restriccion.fill = GridBagConstraints.NONE;
        restriccion.anchor = GridBagConstraints.EAST;

        JButton btnRegistrar = ConstructorComponentes.crearBotonGuardar("Registrar Bodeguero");
        btnRegistrar.addActionListener(evento -> registrarBodeguero());
        panelFormulario.add(btnRegistrar, restriccion);

        return panelFormulario;
    }

    private void registrarBodeguero() {
        String login = txtLogin.getText().trim();
        String clave = new String(txtClave.getPassword()).trim();
        String zonaBodega = txtZonaBodega.getText().trim();

        BodegueroDTO dto = new BodegueroDTO(
                login,
                RolUsuario.BODEGUERO.name(),
                zonaBodega);

        boolean exito = manejadorEvento.registrarBodeguero(dto, clave);

        if (exito) {
            JOptionPane.showMessageDialog(this,
                    "Bodeguero registrado correctamente.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No fue posible registrar el bodeguero. Verifique login, contraseña y zona de bodega.",
                    "Error de registro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtLogin.setText("");
        txtClave.setText("");
        txtZonaBodega.setText("");
>>>>>>> 7d4245951cf20ce153a582cbbc5a372a9ca9ae72
    }

    private JPanel construirPanelInventarioCritico() {
        JPanel panelInventario = new JPanel(new BorderLayout(10, 10));
        ConstructorComponentes.aplicarFondoPanel(panelInventario);
        panelInventario.setBorder(BorderFactory.createTitledBorder("Productos con Stock Crítico"));

        String[] columna = {
                "Código",
                "Nombre",
                "Categoría",
                "Precio Venta",
                "Stock Actual",
                "Alerta",
                "Estado"
        };

        modeloTablaCritico = new DefaultTableModel(columna, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaInventarioCritico = new JTable(modeloTablaCritico);
        ConstructorComponentes.darEstiloTabla(tablaInventarioCritico);
        tablaInventarioCritico.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollTabla = new JScrollPane(tablaInventarioCritico);
        scrollTabla.setPreferredSize(new Dimension(0, 280));
        panelInventario.add(scrollTabla, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        ConstructorComponentes.aplicarFondoPanel(panelBoton);

        JButton botonRefrescar = ConstructorComponentes.crearBotonInformativo("Refrescar");
        botonRefrescar.addActionListener(evento -> actualizarTablaInventarioCritico());
        panelBoton.add(botonRefrescar);

        panelInventario.add(panelBoton, BorderLayout.SOUTH);
        return panelInventario;
    }

    private void actualizarTablaInventarioCritico() {
        modeloTablaCritico.setRowCount(0);

        List<ProductoResumenDTO> inventarioCritico = manejadorEvento.obtenerInventarioCritico();

        if (inventarioCritico.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay producto con inventario crítico en este momento.",
                    "Inventario crítico",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (ProductoResumenDTO producto : inventarioCritico) {
            modeloTablaCritico.addRow(new Object[]{
                    producto.codigo(),
                    producto.nombre(),
                    producto.categoria(),
                    "$" + producto.precioVenta(),
                    producto.stockActual(),
                    producto.alertaMinima(),
                    producto.estado()
            });
        }
    }
}