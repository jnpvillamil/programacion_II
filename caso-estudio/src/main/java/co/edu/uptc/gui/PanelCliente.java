package co.edu.uptc.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.negocio.dto.clienteDto;

public class PanelCliente extends JPanel {

	private static final long serialVersionUID = 1L;

	public JButton bRegistrar, bModificar, bInactivar, bBuscar, bVolver, bLimpiar;
	public JTextField tDocumento, tNombre, tTelefono, tDireccion; // Agregado tDocumento

	protected DefaultTableModel modeloTabla;
	protected JTable tablaClientes;

	private int codigoSeleccionado = -1;

	public PanelCliente() {
		construirPanel();
	}

	private void construirPanel() {
		setLayout(new BorderLayout(8, 8));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Cambiado a 4 filas para incluir Documento
		JPanel Campos = new JPanel(new GridLayout(4, 2, 10, 8));
		Campos.setBorder(BorderFactory.createTitledBorder("Datos del cliente"));

		Campos.add(new JLabel("DOCUMENTO:"));
		Campos.add(tDocumento = new JTextField(10));
		Campos.add(new JLabel("NOMBRE:"));
		Campos.add(tNombre = new JTextField(10));
		Campos.add(new JLabel("TELEFONO:"));
		Campos.add(tTelefono = new JTextField(10));
		Campos.add(new JLabel("DIRECCION:"));
		Campos.add(tDireccion = new JTextField(10));

		// Columnas de la tabla (incluye Documento)
		modeloTabla = new DefaultTableModel(new String[] { "Codigo", "Documento", "Nombre", "Telefono", "Direccion" },
				0) {
			@Override
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		tablaClientes = new JTable(modeloTabla);
		tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaClientes.setRowHeight(20);
		tablaClientes.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting())
				cargarSeleccionEnCampos();
		});

		JScrollPane scroll = new JScrollPane(tablaClientes);
		scroll.setBorder(BorderFactory.createTitledBorder("Lista de clientes"));
		scroll.setPreferredSize(new Dimension(600, 150));

		bRegistrar = new JButton(Eventos.cREGISTRAR);
		bModificar = new JButton(Eventos.cMODIFICAR);
		bInactivar = new JButton(Eventos.cINACTIVAR);
		bBuscar = new JButton(Eventos.cBUSCAR);
		bLimpiar = new JButton(Eventos.cLIMPIAR);
		bVolver = new JButton(Eventos.VOLVER);

		JPanel botones = new JPanel(new GridLayout(1, 5, 8, 8));
		botones.add(bRegistrar);
		botones.add(bModificar);
		botones.add(bInactivar);
		botones.add(bBuscar);
		botones.add(bLimpiar);

		JPanel panelVolver = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelVolver.add(bVolver);

		JPanel sur = new JPanel(new BorderLayout());
		sur.add(botones, BorderLayout.NORTH);
		sur.add(panelVolver, BorderLayout.SOUTH);

		add(Campos, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		add(sur, BorderLayout.SOUTH);
	}

	public void setControlador(ActionListener controlador) {
		bRegistrar.addActionListener(controlador);
		bRegistrar.setActionCommand("REGISTRAR");
		bModificar.addActionListener(controlador);
		bModificar.setActionCommand("MODIFICAR");
		bInactivar.addActionListener(controlador);
		bInactivar.setActionCommand("INACTIVAR");
		bBuscar.addActionListener(controlador);
		bBuscar.setActionCommand("BUSCAR");
		bVolver.addActionListener(controlador);
		bVolver.setActionCommand("VOLVER");
		bLimpiar.addActionListener(controlador);
		bLimpiar.setActionCommand("LIMPIAR");
	}

	private void cargarSeleccionEnCampos() {
		int fila = tablaClientes.getSelectedRow();

		// Validar que la fila exista y no sea -1
		if (fila < 0)
			return;

		// Obtenemos el valor de la columna 0 (Codigo)
		Object objCodigo = modeloTabla.getValueAt(fila, 0);

		// Verificamos que no sea nulo y que no esté vacío antes de convertir
		if (objCodigo != null && !objCodigo.toString().trim().isEmpty()) {
			try {
				codigoSeleccionado = Integer.parseInt(objCodigo.toString());

				// Si el código es válido, cargamos el resto
				tDocumento.setText(
						modeloTabla.getValueAt(fila, 1) != null ? modeloTabla.getValueAt(fila, 1).toString() : "");
				tNombre.setText(
						modeloTabla.getValueAt(fila, 2) != null ? modeloTabla.getValueAt(fila, 2).toString() : "");
				tTelefono.setText(
						modeloTabla.getValueAt(fila, 3) != null ? modeloTabla.getValueAt(fila, 3).toString() : "");
				tDireccion.setText(
						modeloTabla.getValueAt(fila, 4) != null ? modeloTabla.getValueAt(fila, 4).toString() : "");

			} catch (NumberFormatException e) {
				// Si por alguna razón extraña no es un número, reiniciamos
				codigoSeleccionado = -1;
				System.err.println("Error al parsear el código: " + e.getMessage());
			}
		}
	}

	public void poblarTabla(List<clienteDto> lista) {
		modeloTabla.setRowCount(0);
		for (clienteDto c : lista) {
			modeloTabla.addRow(new Object[] { c.getCodigoCliente(), c.getDocumento(), c.getNombre(), c.getTelefono(),
					c.getDireccion() });
		}
	}

	public void limpiarCampos() {
		codigoSeleccionado = -1;
		tDocumento.setText(""); // Limpiar nuevo campo
		tNombre.setText("");
		tTelefono.setText("");
		tDireccion.setText("");
		tablaClientes.clearSelection();
	}

	public clienteDto getDatosCliente() {
		if (tNombre.getText().isBlank()) {
			JOptionPane.showMessageDialog(this, "El nombre es requerido");
			return null;
		}
		clienteDto cliente = new clienteDto();
		cliente.setDocumento(tDocumento.getText()); // Guardar documento
		cliente.setNombre(tNombre.getText());
		try {
			cliente.setTelefono(Long.parseLong(tTelefono.getText()));
		} catch (NumberFormatException e) {
			cliente.setTelefono(0L);
		}
		cliente.setDireccion(tDireccion.getText());
		return cliente;
	}

	public clienteDto getDatosClienteModificar() {
		if (codigoSeleccionado == -1) {
			JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla");
			return null;
		}
		clienteDto cliente = getDatosCliente();
		if (cliente != null)
			cliente.setCodigoCliente(codigoSeleccionado);
		return cliente;
	}

	public int getCodigoCliente() {
		return codigoSeleccionado;
	}
}