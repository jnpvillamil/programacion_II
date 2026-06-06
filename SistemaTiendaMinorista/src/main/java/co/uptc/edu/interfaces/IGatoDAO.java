package co.uptc.edu.interfaces;

import java.util.List;

import co.uptc.edu.modelo.Gato;

public interface IGatoDAO {

    boolean guardarGato(Gato gato);

    List<Gato> obtenerGatos();
