package co.edu.uptc.dto;

import co.edu.uptc.modelo.Producto;

public record CarritoVentaDTO(Producto producto, int cantidad, double precioUnitario) {

    public double subtotal() {
        return cantidad * precioUnitario;
    }
}
