package co.uptc.edu.tienda.interfaces;

import co.uptc.edu.tienda.modelo.Usuario;

public interface IGestionUsuario {
    Usuario buscarPorCorreo(String correo);
}