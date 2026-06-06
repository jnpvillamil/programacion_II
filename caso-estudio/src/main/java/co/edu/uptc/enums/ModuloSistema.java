package co.edu.uptc.enums;

public enum ModuloSistema {

    INVENTARIO,
    CLIENTES,
    VENTAS,
    COMPRAS,
    PROVEEDORES,
    REPORTES,
    CONSULTAS,
    ROLES,
    CERRAR_SESION;

    public static ModuloSistema desdeTexto(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        try {
            return valueOf(valor.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
