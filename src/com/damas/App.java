package com.damas;

import com.damas.gui.JanelaPrincipalGUI;

public class App {
    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JanelaPrincipalGUI().setVisible(true);
            }
        });
    }
}
