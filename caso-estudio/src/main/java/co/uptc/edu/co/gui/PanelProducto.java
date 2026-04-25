package co.uptc.edu.co.gui;

import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.enums.CategoriaProductoEnum;
import co.uptc.edu.co.modelo.enums.EstadoEnum;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelProducto extends PanelCentral {

    private static final String TITULO_PANEL = "Gestión de Productos";
    private static final String TEXTO_TOTAL_INICIAL = "Total de productos: 0";
    private static final String TEXTO_TOTAL = "Total de productos: ";

    private static final String TEXTO_BOTON_CAMBIAR_ESTADO = "Cambiar Estado";
    private static final String TEXTO_BOTON_INACTIVAR = "Inactivar";
    private static final String TEXTO_BOTON_ACTIVAR = "Activar";

    private static final String OPCION_TODOS = "Todos";

    private static final Object[] COLUMNAS = {
            "Código",
            "Nombre",
            "Categoría",
            "Precio Compra",
            "Precio Venta",
            "Stock Actual",
            "Stock Mínimo",
            "Stock Máximo",
            "Estado"
    };

    private static final int COLUMNA_CODIGO = 0;
    private static final int COLUMNA_ESTADO = 8;

    private JButton botonNuevo;
    private JButton botonEditar;
    private JButton botonEstadoProducto;
    private JButton botonActualizarPrecio;
    private JButton botonMovimientoInventario;

    private JTextField campoBuscar;
    private JComboBox<String> comboCategoria;

    private List<Producto> productosCargados;

    public PanelProducto() {
        super();
        productosCargados = new ArrayList<>();
        inicializarComponentesProducto();
        configurarPanelProducto();
        agregarComponentesProducto();
        inicializarFiltros();
    }

    @Override
    protected String obtenerTituloPanel() {
        return TITULO_PANEL;
    }

    @Override
    protected String obtenerTextoTotalInicial() {
        return TEXTO_TOTAL_INICIAL;
    }

    @Override
    protected Object[] obtenerColumnas() {
        return COLUMNAS;
    }

    private void inicializarComponentesProducto() {
        botonNuevo = new JButton("Nuevo");
        botonEditar = new JButton("Editar");
        botonEstadoProducto = new JButton(TEXTO_BOTON_CAMBIAR_ESTADO);
        botonActualizarPrecio = new JButton("Actualizar Precio");
        botonMovimientoInventario = new JButton("Movimiento Inventario");

        campoBuscar = new JTextField(20);

        comboCategoria = new JComboBox<>();
        comboCategoria.addItem(OPCION_TODOS);
        comboCategoria.addItem(CategoriaProductoEnum.ASEO.toString());
        comboCategoria.addItem(CategoriaProductoEnum.ALIMENTOS.toString());
        comboCategoria.addItem(CategoriaProductoEnum.BEBIDAS.toString());
        comboCategoria.addItem(CategoriaProductoEnum.PAPELERIA.toString());
    }

    private void configurarPanelProducto() {
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                actualizarTextoBotonSegunSeleccion();
            }
        });

        botonNuevo.setBackground(Color.WHITE);
        botonEditar.setBackground(Color.WHITE);
        botonEstadoProducto.setBackground(Color.WHITE);
        botonActualizarPrecio.setBackground(Color.WHITE);
        botonMovimientoInventario.setBackground(Color.WHITE);
    }

    private void agregarComponentesProducto() {
        panelBotones.add(botonNuevo);
        panelBotones.add(botonEditar);
        panelBotones.add(botonEstadoProducto);
        panelBotones.add(botonActualizarPrecio);
        panelBotones.add(botonMovimientoInventario);

        panelFiltros.add(new JLabel("Buscar:"));
        panelFiltros.add(campoBuscar);
        panelFiltros.add(new JLabel("Categoría:"));
        panelFiltros.add(comboCategoria);
    }

    private void inicializarFiltros() {
        campoBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                aplicarFiltros();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                aplicarFiltros();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                aplicarFiltros();
            }
        });

        comboCategoria.addActionListener(e -> aplicarFiltros());
    }

    public void inicializarEventos(Evento evento) {
        botonNuevo.setActionCommand(Evento.CMD_NUEVO_PRODUCTO);
        botonNuevo.addActionListener(evento);

        botonEditar.setActionCommand(Evento.CMD_EDITAR_PRODUCTO);
        botonEditar.addActionListener(evento);

        botonEstadoProducto.setActionCommand(Evento.CMD_ESTADO_PRODUCTO);
        botonEstadoProducto.addActionListener(evento);

        botonActualizarPrecio.setActionCommand(Evento.CMD_ACTUALIZAR_PRECIO_PRODUCTO);
        botonActualizarPrecio.addActionListener(evento);

        botonMovimientoInventario.setActionCommand(Evento.CMD_MOVIMIENTO_INVENTARIO);
        botonMovimientoInventario.addActionListener(evento);
    }

    public void cargarProductos(List<Producto> productos) {
        productosCargados = new ArrayList<>(productos);
        aplicarFiltros();
        restablecerTextoBotonEstado();
    }

    private void aplicarFiltros() {
        limpiarTabla();

        String textoBusqueda = campoBuscar.getText().trim().toLowerCase();
        String categoriaSeleccionada = comboCategoria.getSelectedItem().toString();

        int totalFiltrados = 0;

        for (Producto producto : productosCargados) {
            boolean coincideBusqueda =
                    producto.getCodigoProducto().toLowerCase().contains(textoBusqueda) ||
                    producto.getNombreProducto().toLowerCase().contains(textoBusqueda);

            boolean coincideCategoria =
                    categoriaSeleccionada.equals(OPCION_TODOS) ||
                    producto.getCategoria().toString().equalsIgnoreCase(categoriaSeleccionada);

            if (coincideBusqueda && coincideCategoria) {
                Object[] fila = {
                        producto.getCodigoProducto(),
                        producto.getNombreProducto(),
                        producto.getCategoria(),
                        producto.getPrecioCompra(),
                        producto.getPrecioVenta(),
                        producto.getStockActual(),
                        producto.getStockMinimo(),
                        producto.getStockMaximo(),
                        producto.getEstado()
                };
                modeloTabla.addRow(fila);
                totalFiltrados++;
            }
        }

        actualizarTextoTotal(TEXTO_TOTAL, totalFiltrados);
    }

    private void actualizarTextoBotonSegunSeleccion() {
        String estado = obtenerEstadoSeleccionado();
        actualizarTextoBotonEstado(estado);
    }

    public String obtenerEstadoSeleccionado() {
        Object valor = obtenerValorSeleccionado(COLUMNA_ESTADO);
        return valor == null ? null : valor.toString();
    }

    public void actualizarTextoBotonEstado(String estado) {
        if (estado == null) {
            botonEstadoProducto.setText(TEXTO_BOTON_CAMBIAR_ESTADO);
            return;
        }

        if (estado.equalsIgnoreCase(EstadoEnum.ACTIVO.name())) {
            botonEstadoProducto.setText(TEXTO_BOTON_INACTIVAR);
        } else {
            botonEstadoProducto.setText(TEXTO_BOTON_ACTIVAR);
        }
    }

    public void restablecerTextoBotonEstado() {
        botonEstadoProducto.setText(TEXTO_BOTON_CAMBIAR_ESTADO);
    }

    public String obtenerCodigoSeleccionado() {
        Object valor = obtenerValorSeleccionado(COLUMNA_CODIGO);
        return valor == null ? null : valor.toString();
    }
}