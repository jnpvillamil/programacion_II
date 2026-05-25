package co.edu.uptc.ventanas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import co.edu.uptc.dao.ClienteDao;
import co.edu.uptc.vo.ClienteVo;

@SuppressWarnings("serial")
public class VentanaConsultarCliente extends JInternalFrame {

    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private ClienteDao miClienteDao;
    private JButton btnModificar, btnRefrescar;

    public VentanaConsultarCliente() {
        super("Listado General de Clientes Activos", true, true, true, true);
        setSize(850, 450);
        
        miClienteDao = new ClienteDao();
        
        String[] columnas = {"Código", "Nombre", "Apellido", "Documento", "Teléfono", "ID Ciudad", "Dirección", "Correo"};
        modeloTabla = new DefaultTableModel(null, columnas);
        tablaClientes = new JTable(modeloTabla);
        getContentPane().add(new JScrollPane(tablaClientes), BorderLayout.CENTER);
  
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRefrescar = new JButton("Refrescar Tabla");
        btnModificar = new JButton("Modificar Seleccionado");
        
        panelBotones.add(btnRefrescar);
        panelBotones.add(btnModificar);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);
        
        btnModificar.addActionListener(e -> ejecutarModificacion());
        btnRefrescar.addActionListener(e -> cargarDatosEnTabla());
        
        cargarDatosEnTabla();
    }

    public void cargarDatosEnTabla() {
        modeloTabla.setRowCount(0);
        ArrayList<ClienteVo> lista = miClienteDao.consultarTodosLosClientes();
        for (ClienteVo c : lista) {
            modeloTabla.addRow(new Object[] {
                c.getCodigo(), c.getNombre(), c.getApellido(), c.getNumeroDocumento(),
                c.getTelefono(), c.getIdCiudad(), c.getDireccionDetallada(), c.getCorreo()
            });
        }
    }

    private void ejecutarModificacion() {
        if (tablaClientes.isEditing()) {
            tablaClientes.getCellEditor().stopCellEditing();
        }

        int filaSel = tablaClientes.getSelectedRow();
        if (filaSel == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente de la tabla primero.");
            return;
        }
        
        try {
            ClienteVo c = new ClienteVo();
            c.setCodigo(modeloTabla.getValueAt(filaSel, 0).toString().trim());
            c.setNombre(modeloTabla.getValueAt(filaSel, 1).toString().trim());
            c.setApellido(modeloTabla.getValueAt(filaSel, 2).toString().trim());
            c.setNumeroDocumento(modeloTabla.getValueAt(filaSel, 3).toString().trim());
            c.setTelefono(modeloTabla.getValueAt(filaSel, 4).toString().trim());
            c.setIdCiudad(Integer.parseInt(modeloTabla.getValueAt(filaSel, 5).toString().trim()));
            c.setDireccionDetallada(modeloTabla.getValueAt(filaSel, 6).toString().trim());
            c.setCorreo(modeloTabla.getValueAt(filaSel, 7).toString().trim());
            c.setTipoDocumento("C.C"); c.setTipoCliente("Frecuente"); c.setActivo(true);

            miClienteDao.actualizarCliente(c);
            JOptionPane.showMessageDialog(this, "¡Cliente actualizado con éxito en la Base de Datos!");
            cargarDatosEnTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar: Verifique que el ID Ciudad sea un número entero válido.");
        }
    }
}