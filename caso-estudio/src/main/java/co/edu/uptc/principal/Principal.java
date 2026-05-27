package co.edu.uptc.principal;

import co.edu.uptc.gui.LoginGUI;

public class Principal {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginGUI login = new LoginGUI();
                login.setVisible(true);
            }
        });
    }
}