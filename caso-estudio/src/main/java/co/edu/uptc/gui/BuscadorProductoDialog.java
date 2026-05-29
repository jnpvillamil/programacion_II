package co.edu.uptc.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.negocio.GestionProducto;
import co.edu.uptc.negocio.dto.productoDto;

public class BuscadorProductoDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField tBusqueda;
	private JTable tablaResultados;
	private DefaultTableModel modeloTabla;
	private JButton bSeleccionar;
	private JButton bCancelar;

	private GestionProducto negocioProducto;
	private int idSeleccionado = -1;
	private String nombreSeleccionado = "";

	public BuscadorProductoDialog(java.awt.Window owner, GestionProducto negocioProducto) {
		super(owner, "Búsqueda Avanzada de Productos", ModalityType.APPLICATION_MODAL);
		this.negocioProducto = negocioProducto;

		setSize(500, 350);
		setLocationRelativeTo(owner);
		setLayout(new BorderLayout());

		// 1. Panel Norte: Barra de Búsqueda
		JPanel panelNorte = new JPanel(new FlowLayout());
		panelNorte.add(new JLabel("Buscar (Nombre o Código):"));
		tBusqueda = new JTextField(25);
		panelNorte.add(tBusqueda);
		// Ya no necesitamos el botón "Buscar", el texto hará todo el trabajo.

		// 2. Panel Central: Tabla de Resultados
		modeloTabla = new DefaultTableModel(new String[] { "Código", "Nombre", "Precio", "Stock" }, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tablaResultados = new JTable(modeloTabla);
		tablaResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(tablaResultados);

		// 3. Panel Sur: Botones de Acción
		JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bSeleccionar = new JButton("Seleccionar");
		bCancelar = new JButton("Cancelar");
		panelSur.add(bCancelar);
		panelSur.add(bSeleccionar);

		add(panelNorte, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		add(panelSur, BorderLayout.SOUTH);

		// --- ¡NUEVO! LÓGICA DE BÚSQUEDA EN TIEMPO REAL ---
		tBusqueda.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				realizarBusqueda();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				realizarBusqueda();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				realizarBusqueda();
			}
		});

		// --- LOGICA DE LOS BOTONES ---
		bSeleccionar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				seleccionarProducto();
			}
		});

		bCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				idSeleccionado = -1;
				dispose();
			}
		});

		// Cargar todos los productos al abrir la ventana por primera vez
		realizarBusqueda();
	}

	private void realizarBusqueda() {
		String texto = tBusqueda.getText().trim();
		modeloTabla.setRowCount(0); // Limpia la tabla

		// Si el texto está vacío, buscarMulticriterio debería devolver todos (si tu SQL
		// usa LIKE '%%')
		List<productoDto> encontrados = negocioProducto.buscarMulticriterio(texto);
		for (productoDto p : encontrados) {
			modeloTabla.addRow(
					new Object[] { p.getCodigoProducto(), p.getNombre(), p.getPrecioVenta(), p.getStockActual() });
		}
	}

	private void seleccionarProducto() {
		int fila = tablaResultados.getSelectedRow();
		if (fila >= 0) {
			idSeleccionado = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
			// ¡NUEVO! Guardamos también el nombre (que está en la columna 1)
			nombreSeleccionado = modeloTabla.getValueAt(fila, 1).toString();
			dispose();
		} else {
			JOptionPane.showMessageDialog(BuscadorProductoDialog.this, "Por favor seleccione un producto.");
		}
	}

	public int getIdSeleccionado() {
		return idSeleccionado;
	}

	public String getNombreSeleccionado() {
		return nombreSeleccionado;
	}
}