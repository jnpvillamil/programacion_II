package co.uptc.edu.tienda.negocio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import co.uptc.edu.tienda.interfaces.IGestionVenta;
import co.uptc.edu.tienda.modelo.DetalleVenta;
import co.uptc.edu.tienda.modelo.Venta;
import co.uptc.edu.co.tienda.configs.TiendaConfig;
import co.uptc.edu.tienda.enums.EstadoVentaEnum;

public class GestionVenta {

    private List<Venta> listaVentas;
    private IGestionVenta iVenta;
    private int consecutivo;
    private static final String PREFIJO_FACTURA = "FACT-";

    public GestionVenta(IGestionVenta iVenta) {
        this.iVenta = iVenta;
        this.listaVentas = iVenta.leerVentas();
        this.consecutivo = calcularSiguienteConsecutivo(); // ← corregido
    }

    // =====================================
    // CONSECUTIVO SEGURO
    // =====================================
    private int calcularSiguienteConsecutivo() {
        int max = 0;
        for (Venta v : listaVentas) {
            try {
                String numero = v.getNumeroFactura()
                        .replace(PREFIJO_FACTURA, "");
                int n = Integer.parseInt(numero);
                if (n > max) max = n;
            } catch (Exception e) {
                // formato inesperado, se ignora
            }
        }
        return max + 1;
    }

    // =====================================
    // CALCULAR TOTAL
    // =====================================
    public void calcularTotal(Venta venta) {
        double sumaBases = 0;
        double sumaIva = 0;

        for (DetalleVenta d : venta.getDetalles()) {
            // La base es: Cantidad * Precio Unitario (SIN IVA)
            double baseLinea = (double) d.getCantidad() * d.getPrecioUnitario();
            
            // El IVA es: lo que ya guardó el detalle (o lo recalculas sobre la base)
            double ivaLinea = d.getImpuestos(); 

            sumaBases += baseLinea;
            sumaIva += ivaLinea;
        }

        venta.setImpuestos(sumaIva);
        venta.setTotal(sumaBases + sumaIva);
    }

    // =====================================
    // GUARDAR VENTA
    // =====================================
    public void guardarVenta(Venta venta) throws Exception {
        if (venta.getCliente() == null) {
            throw new Exception("La venta debe tener un cliente");
        }
        if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
            throw new Exception("La venta debe tener al menos un producto");
        }
        if (venta.getFormaPago() == null) {
            throw new Exception("Debe seleccionar una forma de pago");
        }
        venta.setNumeroFactura(PREFIJO_FACTURA + consecutivo++);
        venta.setFechaHora(LocalDateTime.now().toString());
        calcularTotal(venta);
        listaVentas.add(venta);
        iVenta.guardar(venta);
    }

    // =====================================
    // BUSCAR POR FACTURA
    // =====================================
    public Venta buscarPorFactura(String numeroFactura) {
        for (Venta v : listaVentas) {
            if (v.getNumeroFactura().equalsIgnoreCase(numeroFactura)) {
                return v;
            }
        }
        return null;
    }

    // =====================================
    // ANULAR VENTA
    // Solo cambia estado — stock lo maneja GestionInventario
    // =====================================
    public void anularVenta(String numeroFactura, String motivo) throws Exception {
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new Exception("Debe ingresar un motivo de anulación");
        }

        Venta venta = buscarPorFactura(numeroFactura);

        if (venta == null) {
            throw new Exception("No existe una venta con factura: " + numeroFactura);
        }
        if (venta.getEstado() == EstadoVentaEnum.ANULADA) {
            throw new Exception("La venta " + numeroFactura + " ya está anulada");
        }

        venta.setEstado(EstadoVentaEnum.ANULADA);
        venta.setMotivoAnulacion(motivo.trim());

        // Actualizar en memoria
        for (int i = 0; i < listaVentas.size(); i++) {
            if (listaVentas.get(i).getNumeroFactura()
                    .equalsIgnoreCase(numeroFactura)) {
                listaVentas.set(i, venta);
                break;
            }
        }

        // Persistir
        iVenta.actualizar(venta);
    }

    // =====================================
    // LISTAR
    // =====================================
    public List<Venta> listarVentas() {
        return listaVentas;
    }

    public List<Venta> listarVentasActivas() {
        List<Venta> activas = new ArrayList<>();
        for (Venta v : listaVentas) {
            if (v.getEstado() == EstadoVentaEnum.ACTIVA) {
                activas.add(v);
            }
        }
        return activas;
    }
}
