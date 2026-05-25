package co.edu.uptc.ventanas;

import javax.swing.*;
import java.awt.event.*;
import co.edu.uptc.dao.ProveedorDao;
import co.edu.uptc.vo.ProveedorVo;

public class VentanaActualizarProveedor extends JFrame implements ActionListener {
    private JTextField txtNit, txtNombre, txtTel, txtDir;
    private JButton btnActualizar;

    public VentanaActualizarProveedor() {
        setTitle("Actualizar Proveedor"); setSize(400, 300); setLayout(null); setLocationRelativeTo(null);
        
        JLabel lbl = new JLabel("NIT a Buscar:"); lbl.setBounds(30, 20, 100, 25); add(lbl);
        txtNit = new JTextField(); txtNit.setBounds(140, 20, 200, 25); add(txtNit);
        
        JLabel lblN = new JLabel("Nuevo Nombre:"); lblN.setBounds(30, 60, 100, 25); add(lblN);
        txtNombre = new JTextField(); txtNombre.setBounds(140, 60, 200, 25); add(txtNombre);

        JLabel lblT = new JLabel("Nuevo Teléfono:"); lblT.setBounds(30, 100, 100, 25); add(lblT);
        txtTel = new JTextField(); txtTel.setBounds(140, 100, 200, 25); add(txtTel);

        JLabel lblD = new JLabel("Nueva Dirección:"); lblD.setBounds(30, 140, 100, 25); add(lblD);
        txtDir = new JTextField(); txtDir.setBounds(140, 140, 200, 25); add(txtDir);

        btnActualizar = new JButton("Actualizar"); btnActualizar.setBounds(140, 200, 110, 30);
        btnActualizar.addActionListener(this); add(btnActualizar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnActualizar) {
            ProveedorVo prov = new ProveedorVo();
            prov.setNitProveedor(txtNit.getText());
            prov.setNombreEmpresa(txtNombre.getText());
            prov.setTelefono(txtTel.getText());
            prov.setDireccion(txtDir.getText());

            ProveedorDao dao = new ProveedorDao();
            dao.actualizarProveedor(prov);
        }
    }
}