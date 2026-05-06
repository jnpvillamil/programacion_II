package co.edu.uptc.sistienda.persistencia;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import co.edu.uptc.sistienda.interfaces.IGestionProducto;
import co.edu.uptc.sistienda.modelo.Producto;
import co.edu.uptc.sistienda.modelo.enums.CategoriaProductoEnum;
import co.edu.uptc.sistienda.modelo.enums.TipoImpuestoEnum;

public class LocalProducto implements IGestionProducto {

	private List<Producto> listaProductos;

	public LocalProducto() {
		listaProductos = new ArrayList<>();
		cargarDatosIniciales();
	}

	private void cargarDatosIniciales() {
		Producto p1 = new Producto("P001", "Arroz 1kg", CategoriaProductoEnum.VIVERES, 2500, 3200, 3, 10);
		p1.setTipoImpuesto(TipoImpuestoEnum.EXLUIDO);
		Producto p2 = new Producto("P002", "Aceite 1L", CategoriaProductoEnum.VIVERES, 7000, 9500, 5, 15);
		p2.setTipoImpuesto(TipoImpuestoEnum.EXLUIDO);
		Producto p3 = new Producto("P003", "Azúcar 1kg", CategoriaProductoEnum.VIVERES, 2800, 3500, 2, 10);
		p3.setTipoImpuesto(TipoImpuestoEnum.EXLUIDO);
		Producto p4 = new Producto("P004", "Jabón de baño", CategoriaProductoEnum.ASEO, 1800, 2500, 20, 5);
		p4.setTipoImpuesto(TipoImpuestoEnum.IVA);
		Producto p5 = new Producto("P005", "Cuaderno 100 hojas", CategoriaProductoEnum.PAPELERIA, 3500, 5000, 15, 8);
		p5.setTipoImpuesto(TipoImpuestoEnum.IVA);
		listaProductos.add(p1);
		listaProductos.add(p2);
		listaProductos.add(p3);
		listaProductos.add(p4);
		listaProductos.add(p5);
	}

	@Override
	public void guardarProducto(Producto producto) {
		listaProductos.add(producto);
	}

	@Override
	public void actualizarProducto(Producto productoActualizado) {
		Producto existente = buscarProductoPorCodigo(productoActualizado.getCodigoInterno());
		if (existente != null) {
			existente.setNombreProducto(productoActualizado.getNombreProducto());
			existente.setCategoria(productoActualizado.getCategoria());
			existente.setTipoImpuesto(productoActualizado.getTipoImpuesto());
			existente.setPrecioCompra(productoActualizado.getPrecioCompra());
			existente.setPrecioVenta(productoActualizado.getPrecioVenta());
			existente.setStockActual(productoActualizado.getStockActual());
			existente.setStockMinimo(productoActualizado.getStockMinimo());
		}
	}

	@Override
	public void inactivarProducto(String codigoInterno) {
		Producto existente = buscarProductoPorCodigo(codigoInterno);
		if (existente != null) {
			existente.setActivo(false);
		}
	}
	
	@Override
	public void activarProducto(String codigoInterno) {
		Producto existente = buscarProductoPorCodigo(codigoInterno);
		if (existente != null) {
			existente.setActivo(true);
		}
	}

	@Override
	public Producto buscarProductoPorCodigo(String codigoInterno) {
		for (Producto producto : listaProductos) {
			if (producto.getCodigoInterno().equalsIgnoreCase(codigoInterno)) {
				return producto;
			}
		}
		return null;
	}

	@Override
	public List<Producto> obtenerListaProductos() {
		return listaProductos;
	}

	@Override
	public List<Producto> obtenerProductosConStockBajoMinimo() {
		return listaProductos.stream()
				.filter(p -> p.isActivo() && p.tieneStockBajoMinimo())
				.collect(Collectors.toList());
	}
}