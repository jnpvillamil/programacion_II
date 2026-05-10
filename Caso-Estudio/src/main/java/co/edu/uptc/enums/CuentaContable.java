package co.edu.uptc.enums;

public enum CuentaContable {
    CAJA("Caja", "Activo"),
    BANCOS("Bancos", "Activo"),
    INVENTARIO("Inventario", "Activo"),
    IVA_DESCONTABLE("IVA Descontable", "Activo"),
    PROVEEDORES("Proveedores", "Pasivo"),
    IVA_GENERADO("IVA Generado", "Pasivo"),
    INGRESOS_VENTAS("Ingresos por Ventas", "Ingreso"),
    COSTO_VENTAS("Costo de Ventas", "Egreso"),
    UTILIDAD("Utilidad", "Patrimonio");
    
    private String nombre;
    private String tipo;
    
    CuentaContable(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }
    
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
}