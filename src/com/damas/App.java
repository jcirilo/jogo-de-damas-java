package com.damas;

import com.damas.gui.JanelaPrincipal;

/**
 * Classe principal do jogo de damas
 * @author João Victor da S. Cirilo joao.cirilo@academico.ufpb.br
*/
public class App {
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JanelaPrincipal().setVisible(true);
            }
        });
    }
}
