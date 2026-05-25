package co.edu.uptc.ventanas;

import javax.swing.*;
import java.awt.event.*;
import co.edu.uptc.dao.ClienteDao;
import co.edu.uptc.vo.ClienteVo;

@SuppressWarnings("serial")
public class VentanaActualizarCliente extends JFrame implements ActionListener {
    private JTextField txtCodigo, txtNombre, txtApellido, txtTel, txtDir, txtCorreo; 
    private JButton btnActualizar;

    public VentanaActualizarCliente() {
        setTitle("Actualizar Cliente"); setSize(400, 400); setLayout(null); setLocationRelativeTo(null);
        
        JLabel lbl = new JLabel("Cod a Modificar:"); lbl.setBounds(30, 20, 110, 25); add(lbl);
        txtCodigo = new JTextField(); txtCodigo.setBounds(150, 20, 190, 25); add(txtCodigo);
        
        JLabel lblN = new JLabel("Nuevo Nombre:"); lblN.setBounds(30, 60, 110, 25); add(lblN);
        txtNombre = new JTextField(); txtNombre.setBounds(150, 60, 190, 25); add(txtNombre);

        JLabel lblA = new JLabel("Nuevo Apellido:"); lblA.setBounds(30, 100, 110, 25); add(lblA);
        txtApellido = new JTextField(); txtApellido.setBounds(150, 100, 190, 25); add(txtApellido);
        
        JLabel lblT = new JLabel("Nuevo Teléfono:"); lblT.setBounds(30, 140, 110, 25); add(lblT);
        txtTel = new JTextField(); txtTel.setBounds(150, 140, 190, 25); add(txtTel);

        JLabel lblD = new JLabel("Nueva Dirección:"); lblD.setBounds(30, 180, 110, 25); add(lblD);
        txtDir = new JTextField(); txtDir.setBounds(150, 180, 190, 25); add(txtDir);

        // --- DISEÑO NUEVO CAMPO CORREO ---
        JLabel lblC = new JLabel("Nuevo Correo:"); lblC.setBounds(30, 220, 110, 25); add(lblC);
        txtCorreo = new JTextField(); txtCorreo.setBounds(150, 220, 190, 25); add(txtCorreo);

        btnActualizar = new JButton("Actualizar"); btnActualizar.setBounds(150, 280, 110, 30);
        btnActualizar.addActionListener(this); add(btnActualizar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnActualizar) {
            ClienteVo cliente = new ClienteVo();
            cliente.setCodigo(txtCodigo.getText());
            cliente.setNombre(txtNombre.getText());
            cliente.setApellido(txtApellido.getText());
            cliente.setTelefono(txtTel.getText());
            cliente.setDireccionDetallada(txtDir.getText());
            cliente.setCorreo(txtCorreo.getText());

            ClienteDao dao = new ClienteDao();
            dao.actualizarCliente(cliente);
        }
    }
}