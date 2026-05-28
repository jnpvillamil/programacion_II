package co.uptc.edu.co.interfaces;

import java.util.List;

import co.uptc.edu.co.modelo.Compra;

public interface CompraDAO {
	void guardarComprar(Compra compra) throws Exception;

	void actualizarCompra(Compra compra) throws Exception;

	Compra buscarComprarpornumero(String numeroFactura) throws Exception;
	
	List<Compra> listarCompra()throws Exception;
}
