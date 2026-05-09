package co.edu.uptc.Tiendaminorista.Gui;

import java.awt.*;
import javax.swing.*;

import edu.uptc.edu.Tiendaminorista.negociodto.CredencialDto;

public class PanelLogin extends JPanel {

    private JTextField txUsuario;
    private JTextField contrasena;

    public PanelLogin(Evento e) {

        setLayout(new GridBagLayout());

        JPanel plogin = new JPanel();
        plogin.setLayout(new BoxLayout(plogin, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Inicio de sesión");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        plogin.add(titulo);

        plogin.add(Box.createVerticalStrut(15));

        txUsuario = new JTextField();
        txUsuario.setMaximumSize(new Dimension(200, 30));
        plogin.add(txUsuario);

        plogin.add(Box.createVerticalStrut(10));

        contrasena = new JTextField();
        contrasena.setMaximumSize(new Dimension(200, 30));
        plogin.add(contrasena);

        plogin.add(Box.createVerticalStrut(15));

        JPanel botones = new JPanel();
        JButton btncalcelar =new JButton(Evento.SALIR);
        JButton btnentrar = new JButton(Evento.ENTRAR);
        btncalcelar.addActionListener(e);
        btncalcelar.setActionCommand(Evento.SALIR);
        btnentrar.setActionCommand(Evento.ENTRAR);
        btnentrar.addActionListener(e);
        botones.add(btncalcelar);
        botones.add(btnentrar);

        plogin.add(botones);

        add(plogin);
    }
    
    public CredencialDto getCredencialusuario() {
    	
    	CredencialDto nuevo= new CredencialDto();
    	String Usuario=txUsuario.getText();
    	if (Usuario != null && !Usuario.isBlank()){
    		nuevo.setUsuario(Usuario);
    		
    		nuevo.setPassword(contrasena.getText().getBytes());
    		return nuevo;
    	}else {
    		JOptionPane.showMessageDialog(this,"el campo debe de ser diligenciado");
    	}
    			return null;
    
    }
}