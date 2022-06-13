package com.damas;

import com.damas.gui.JogoGUI;


/**
 * TODO - ADICIONAR ELEMENTOS AO GUI:
 * - gui do jogador que está com a vez
 * - gui nº da jogada
 * - gui dos pontos dos jogadores branco e vermelho
 * - gui tela de fim de jogo
 */
public class App {
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JogoGUI().setVisible(true);
            }
        });

    }
}
