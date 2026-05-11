package co.edu.uptc.gui;

import co.edu.uptc.utilidades.ConstructorComponentes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelProducto extends JPanel {
    
    public PanelProducto() {
        // Configuración principal del panel con márgenes uniformes
        setLayout(new BorderLayout(20, 20));
        setBackground(ConstructorComponentes.COLOR_FONDO_GRIS); 
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título de la sección 
        JLabel titulo = ConstructorComponentes.crearEtiquetaNegrita("GESTIÓN DE PRODUCTOS E INVENTARIO");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // Contenido del formulario
        // GridBagLayout para tener control total sobre las proporciones
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 10, 8, 10); // Espaciado entre componentes
        gbc.weightx = 0.5;

        // Fila 1: Código y Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Código Interno:"), gbc);
        gbc.gridx = 1;
        panelForm.add(ConstructorComponentes.crearCampoTexto(), gbc);

        gbc.gridx = 2;
        panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Nombre Producto:"), gbc);
        gbc.gridx = 3;
        panelForm.add(ConstructorComponentes.crearCampoTexto(), gbc);

        // Fila 2: Categoría y Precio Compra 
        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Categoría:"), gbc);
        gbc.gridx = 1;
        // Se sugiere JComboBox para categorías según los enums (Viveres, Aseo, Papelería)
        panelForm.add(ConstructorComponentes.crearCampoTexto(), gbc);

        gbc.gridx = 2;
        panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Precio Compra ($):"), gbc);
        gbc.gridx = 3;
        panelForm.add(ConstructorComponentes.crearCampoTexto(), gbc);

        // Fila 3: Precio Venta y Stock Inicial
        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Precio Venta ($):"), gbc);
        gbc.gridx = 1;
        panelForm.add(ConstructorComponentes.crearCampoTexto(), gbc);

        gbc.gridx = 2;
        panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Stock Inicial:"), gbc);
        gbc.gridx = 3;
        panelForm.add(ConstructorComponentes.crearCampoTexto(), gbc);

        // Fila 4: Stock Mínimo y Botón de Acción 
        gbc.gridx = 0; gbc.gridy = 3;
        panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Stock Mínimo:"), gbc);
        gbc.gridx = 1;
        panelForm.add(ConstructorComponentes.crearCampoTexto(), gbc);

        gbc.gridx = 3;
        // Botón destacado en color verde para guardar 
        JButton btnGuardar = ConstructorComponentes.crearBotonAccion("Guardar Producto", ConstructorComponentes.COLOR_VERDE_GUARDAR);
        panelForm.add(btnGuardar, gbc);

        // Envoltura para que el formulario no se estire verticalmente en el centro
        JPanel formWrapper = new JPanel(new BorderLayout());
        formWrapper.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        formWrapper.add(panelForm, BorderLayout.NORTH);
        add(formWrapper, BorderLayout.CENTER);

        // --- ÁREA DE VISUALIZACIÓN (TABLA) ---
        // Columnas requeridas: Código, Nombre, Categoría, Precio Venta, Stock y Alerta Mínima
        String[] columnas = {"Código", "Nombre", "Categoría", "Precio Venta", "Stock", "Alerta Mínima"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tablaProductos = new JTable(modelo);
        ConstructorComponentes.darEstiloTabla(tablaProductos); // Aplica encabezados oscuros
        
        JScrollPane scroll = new JScrollPane(tablaProductos);
        scroll.setPreferredSize(new Dimension(0, 350)); // Altura fija para la tabla
        add(scroll, BorderLayout.SOUTH);
    }
}