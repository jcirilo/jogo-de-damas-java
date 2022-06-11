package com.damas;

import com.damas.gui.JogoGUI;

public class App {
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JogoGUI().setVisible(true);
            }
        });

    }
}
