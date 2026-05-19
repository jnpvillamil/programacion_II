package co.edu.uptc.interfaces;

import java.util.List;

public interface Repositorio<T> {
    void guardar(T objeto);
    void eliminar(String id);
    T buscarPorId(String id);
    List<T> listar();
    void actualizar(T objeto);
}