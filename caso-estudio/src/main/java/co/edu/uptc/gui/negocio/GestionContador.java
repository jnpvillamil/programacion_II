package co.edu.uptc.gui.negocio;

import co.edu.uptc.gui.interfaces.IGestionContador;
import co.edu.uptc.gui.modelo.Contador;
import java.util.List;

public class GestionContador {

	private IGestionContador iContador;

	public GestionContador(IGestionContador iContador) {
		this.iContador = iContador;

	}

	public void registrarContador(Contador contador) {
		this.iContador.guardar(contador);

	}

	public void modificarContador(Contador contador) {
		this.iContador.actualizar(contador);

	}

	public void darDeBajaContador(Long id) {
		this.iContador.eliminar(id);
	}

	public void consultarContador(Long id) {
		return;
	}

	public List <Contador>listarContador() {
		return this.iContador.listar();
	}


}