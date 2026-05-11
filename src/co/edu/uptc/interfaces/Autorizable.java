package co.edu.uptc.interfaces;

import co.edu.uptc.enums.RolUsuario;

public interface Autorizable {
    boolean verificarPermiso(RolUsuario rol, String modulo);
}