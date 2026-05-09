package co.uptc.edu.tienda.gui;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

import co.uptc.edu.tienda.modelo.Proveedor;

public abstract class PanelCentral<T> extends JPanel {
	
	protected String tituloPanel;
	protected JButton btnActivar;
	protected JButton btnInactivar;
	protected JButton btnVer;
	protected JButton btnActualizar;
	protected JButton btnRegistrar;
	protected JButton btnBuscar;
	protected JButton btnLimpiar;
	protected JTable tblGenerica;
	protected DefaultTableModel modelo;
	


	public PanelCentral(Evento evento) {
		agregarTituloPanel();
		JPanel pSuperior = new JPanel();
		pSuperior.setLayout(new BorderLayout());
		setLayout(new BorderLayout());

		JPanel pTitulo = new JPanel();
		pTitulo.add(new JLabel(tituloPanel));

		JPanel pBtnFuncion = new JPanel();
		btnActivar = new JButton(Evento.ACTIVAR);
		btnInactivar = new JButton(Evento.ELIMINAR);
		btnVer = new JButton(Evento.VER);
		btnActualizar = new JButton(Evento.ACTUALIZAR);
		btnRegistrar = new JButton(Evento.CREAR);
		
        btnActivar.addActionListener(evento);
		btnInactivar.addActionListener(evento);
		btnVer.addActionListener(evento);
		btnActualizar.addActionListener(evento);
		btnRegistrar.addActionListener(evento);
		
		pBtnFuncion.add(btnActivar);
		pBtnFuncion.add(btnInactivar);
		pBtnFuncion.add(btnVer);
		pBtnFuncion.add(btnActualizar);
		pBtnFuncion.add(btnRegistrar);

		JPanel pFiltros = new JPanel();
		btnBuscar = new JButton(Evento.BUSCAR);
		btnLimpiar = new JButton(Evento.LIMPIAR);

		btnLimpiar.addActionListener(evento);
		btnBuscar.addActionListener(evento);
		
		
		pFiltros.add(btnBuscar);
		pFiltros.add(btnLimpiar);

		pSuperior.add(pTitulo, BorderLayout.NORTH);
		pSuperior.add(pBtnFuncion, BorderLayout.CENTER);
		pSuperior.add(pFiltros, BorderLayout.SOUTH);

		modelo = new DefaultTableModel();
		tblGenerica = new JTable(modelo);
		
		agregarCabeceraTabla();
		
		JScrollPane spTabla = new JScrollPane(tblGenerica);

		add(pSuperior, BorderLayout.NORTH);
		add(spTabla, BorderLayout.CENTER);
		agregarIdentificadorComandoBoton();
	}
	
	public abstract void agregarTituloPanel();

	public abstract void agregarIdentificadorComandoBoton();
	public abstract void agregarCabeceraTabla();

	public void poblarTabla(List<T> lista) {
		// TODO Auto-generated method stub	
	}
}

