package co.edu.uptc.interfaces;

import java.util.List;

public interface Repositorio<T> {
    void guardar(T objeto);
    void eliminar(String id);
    List<T> listar();
    T buscarPorId(String id);
}