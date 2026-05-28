package co.edu.uptc.utilidades;

import co.edu.uptc.dto.CarritoItemDTO;
import co.edu.uptc.dto.VentaDTO;
import co.edu.uptc.dto.UsuarioDTO;
import co.edu.uptc.enums.FormaPago;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.modelo.Usuario;
import co.edu.uptc.modelo.Venta;

import java.util.ArrayList;
import java.util.List;

public final class MapeadorDTO {

    private MapeadorDTO() {
    }

    public static Venta aVenta(VentaDTO dto, Cliente cliente) {
        List<DetalleVenta> detalles = new ArrayList<>();
        if (dto.getItems() != null) {
            for (CarritoItemDTO item : dto.getItems()) {
                detalles.add(new DetalleVenta(
                        item.getProducto(),
                        item.getCantidad(),
                        item.getPrecioUnitario(),
                        item.getSubtotal()));
            }
        }
        Venta venta = new Venta();
        venta.setNumeroFactura(dto.getNumeroFactura());
        venta.setCliente(cliente);
        venta.setProductosVendidos(detalles);
        venta.setFormaPago(FormaPago.desdeTexto(dto.getFormaPago()));
        if (dto.getFecha() != null) {
            venta.setFechaHora(dto.getFecha());
        }
        return venta;
    }

    public static UsuarioDTO desdeUsuario(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getNombre(),
                usuario.getUsuario(),
                usuario.obtenerRol());
    }
}
