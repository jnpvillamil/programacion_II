package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.GestionCompra;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.config.TiendaConfig;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class LocalCompra implements GestionCompra {
    
    private Map<String, Compra> compras;
    
    public LocalCompra() {
        compras = new HashMap<>();
        
    }
    
    @Override
    public void crearCompra(Compra compra) {
        compras.put(compra.getNumeroFacturaProveedor(), compra);
    }
    
    @Override
    public Compra buscarCompra(String numeroFacturaProveedor) {
        return compras.get(numeroFacturaProveedor);
    }
    
    @Override
    public List<Compra> listarCompras() {
        return new ArrayList<>(compras.values());
    }
    
    @Override
    public void anularCompra(String numeroFacturaProveedor) {
        Compra compra = compras.get(numeroFacturaProveedor);
        if(compra != null && "Activa".equals(compra.getEstado())) {
            compra.anular();
        }
    }
    
    @Override
    public List<Compra> listarComprasPorProveedor(String codigoProveedor) {
        return compras.values().stream()
            .filter(c -> c.getProveedor() != null && c.getProveedor().getCodigo().equals(codigoProveedor))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Compra> listarComprasPorPeriodo(LocalDate inicio, LocalDate fin) {
        return compras.values().stream()
            .filter(c -> {
                LocalDate fechaCompra = c.getFechaHora().toLocalDate();
                return (fechaCompra.isEqual(inicio) || fechaCompra.isAfter(inicio)) &&
                       (fechaCompra.isEqual(fin) || fechaCompra.isBefore(fin));
            })
            .collect(Collectors.toList());
    }
    
    @Override
    public double calcularTotalComprasPeriodo(LocalDate inicio, LocalDate fin) {
        return listarComprasPorPeriodo(inicio, fin).stream()
            .mapToDouble(Compra::getTotal)
            .sum();
    }
    
    @Override
    public List<Object[]> obtenerProductosMasComprados() {
        Map<String, Integer> cantidadPorProducto = new HashMap<>();
        
        for(Compra c : compras.values()) {
            if("Activa".equals(c.getEstado())) {
                for(var detalle : c.getDetalles()) {
                    String codigo = detalle.getProducto().getCodigo();
                    cantidadPorProducto.put(codigo, 
                        cantidadPorProducto.getOrDefault(codigo, 0) + detalle.getCantidad());
                }
            }
        }
        
        
        return cantidadPorProducto.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(10)
            .map(entry -> {
                Producto p = TiendaConfig.getInstancia().getGestionProducto().buscar(entry.getKey());
                return new Object[]{p.getCodigo(), p.getNombre(), entry.getValue()};
            })
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Compra> listarComprasPorFecha(LocalDate fecha) {
        return compras.values().stream()
            .filter(c -> c.getFechaHora().toLocalDate().equals(fecha))
            .collect(Collectors.toList());
    }
}