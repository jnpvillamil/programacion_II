package co.edu.uptc.sistienda.productos.gui;

import java.util.List;

import javax.swing.JPanel;

import co.edu.uptc.sistienda.comun.gui.PanelCrudAbstracto;
import co.edu.uptc.sistienda.gui.Evento;
import co.edu.uptc.sistienda.modelo.Producto;

 //Panel concreto de Productos
 //Hereda el algoritmo de PanelCrudAbstracto y solo implementa lo que es propio de productos
public class PanelProductos extends PanelCrudAbstracto {

	public PanelProductos(Evento evento) {
		super(evento);
	}

	@Override
	public String obtenerTituloPanel() {
		return "Gestión de Productos";
	}

	@Override
	public void asignarComandosBotones() {
		botonNuevo.setActionCommand(Evento.NUEVO_PRODUCTO);
		botonEditar.setActionCommand(Evento.EDITAR_PRODUCTO);
		botonInactivar.setActionCommand(Evento.INACTIVAR_PRODUCTO);
		botonActivar.setActionCommand(Evento.ACTIVAR_PRODUCTO);
		botonBuscar.setActionCommand(Evento.BUSCAR_PRODUCTO);
		botonLimpiar.setActionCommand(Evento.LIMPIAR_PRODUCTO);
	}

	@Override
	public void agregarColumnasTabla() {
		modeloTabla.addColumn("Código");
		modeloTabla.addColumn("Nombre");
		modeloTabla.addColumn("Categoría");
		modeloTabla.addColumn("Tipo Impuesto");
		modeloTabla.addColumn("P. Compra");
		modeloTabla.addColumn("P. Venta");
		modeloTabla.addColumn("Stock Act.");
		modeloTabla.addColumn("Stock Mín.");
		modeloTabla.addColumn("Estado");
	}

	@Override
	public void poblarTabla(List<?> listaRegistros) {
		modeloTabla.setRowCount(0);
		for (Object obj : listaRegistros) {
			Producto producto = (Producto) obj;
			String estado = producto.isActivo()
					? (producto.tieneStockBajoMinimo() ? "BAJO MÍN." : "Normal")
					: "Inactivo";
			Object[] fila = {
				producto.getCodigoInterno(),
				producto.getNombreProducto(),
				producto.getCategoria(),
				producto.getTipoImpuesto(),
				"$" + String.format("%,.0f", producto.getPrecioCompra()),
				"$" + String.format("%,.0f", producto.getPrecioVenta()),
				producto.getStockActual(),
				producto.getStockMinimo(),
				estado
			};
			modeloTabla.addRow(fila);
		}
	}
}