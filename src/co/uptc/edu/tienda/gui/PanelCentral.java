package co.uptc.edu.tienda.gui;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public abstract class PanelCentral extends JPanel {
	
	protected String tituloPanel;
	protected JButton btnEliminar;
	protected JButton btnVer;
	protected JButton btnActualizar;
	protected JButton btnCrear;
	protected JButton btnBuscar;
	protected JButton btnLimpiar;
	protected JTable tblProveedores;
	protected DefaultTableModel modelo;
	


	public PanelCentral(Evento evento) {
		JPanel pSuperior = new JPanel();
		pSuperior.setLayout(new BorderLayout());
		setLayout(new BorderLayout());

		JPanel pTitulo = new JPanel();
		pTitulo.add(new JLabel(tituloPanel));

		JPanel pBtnFuncion = new JPanel();
		btnEliminar = new JButton(Evento.ELIMINAR);
		btnVer = new JButton(Evento.VER);
		btnActualizar = new JButton(Evento.ACTUALIZAR);
		btnCrear = new JButton(Evento.CREAR);

		btnEliminar.addActionListener(evento);
		btnVer.addActionListener(evento);
		btnActualizar.addActionListener(evento);
		btnCrear.addActionListener(evento);
		
		pBtnFuncion.add(btnEliminar);
		pBtnFuncion.add(btnVer);
		pBtnFuncion.add(btnActualizar);
		pBtnFuncion.add(btnCrear);

		JPanel pFiltros = new JPanel();
		JTextField txBuscar = new JTextField();
		txBuscar.setPreferredSize(new Dimension(145, 30));
		btnBuscar = new JButton(Evento.BUSCAR);
		btnLimpiar = new JButton(Evento.LIMPIAR);

		btnLimpiar.addActionListener(evento);
		btnBuscar.addActionListener(evento);
		
		pFiltros.add(txBuscar);
		pFiltros.add(btnBuscar);
		pFiltros.add(btnLimpiar);

		pSuperior.add(pTitulo, BorderLayout.NORTH);
		pSuperior.add(pBtnFuncion, BorderLayout.CENTER);
		pSuperior.add(pFiltros, BorderLayout.SOUTH);

		modelo = new DefaultTableModel();
		tblProveedores = new JTable();
		agregarCabeceraTabla();
		
		JScrollPane spTabla = new JScrollPane(tblProveedores);

		add(pSuperior, BorderLayout.NORTH);
		add(spTabla, BorderLayout.CENTER);
		agregarIdentificadorComandoBoton();
	}
	
	public abstract void agregarTituloPanel();

	public abstract void agregarIdentificadorComandoBoton();
	public abstract void agregarCabeceraTabla();
	public abstract void poblarTabla(List<?> listaProveedores);
}
