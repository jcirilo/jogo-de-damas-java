package com.damas.gui;

import java.awt.Color;
import javax.swing.JPanel;

import com.damas.objetos.Casa;
import com.damas.objetos.Dama;
import com.damas.objetos.Jogo;
import com.damas.objetos.Pedra;
import com.damas.objetos.Tabuleiro;

/**
 * Interface Grafica do Tabuleiro do jogo.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public class TabuleiroGUI extends JPanel {

    private JanelaPrincipal janela;
    private CasaGUI[][] casas;

    public TabuleiroGUI() {
    }

    public TabuleiroGUI(JanelaPrincipal janela) {
        this.janela = janela;
        initComponents();
        criarCasas();
    }

    /**
     * Preenche o tabuleiro com 64 casas
     */
    private void criarCasas() {
        casas = new CasaGUI[8][8];
        // De cima para baixo
        for (int y = 7; y >= 0; y--) {
            // Da esquerda para a direita
            for (int x = 0; x < 8; x++) {
                Color cor = calcularCor(x, y);
                CasaGUI casa = new CasaGUI(x, y, cor, this);
                casas[x][y] = casa;
                add(casa);
            }
        }
    }

    private Color calcularCor(int x, int y) {
        // linha par
        if (x % 2 == 0) {
            // coluna �mpar
            if (y % 2 == 0) {
                return CasaGUI.COR_ESCURA;
            }
            // coluna impar
            else {
                return CasaGUI.COR_CLARA;
            }
        }
        // linha �mpar
        else {
            // coluna par
            if (y % 2 == 0) {
                return CasaGUI.COR_CLARA;
            }
            // coluna �mpar
            else {
                return CasaGUI.COR_ESCURA;
            }
        }

        // codigo acima em uma linha
        // return (i%2 + j%2)%2 == 0 ? CasaGUI.COR_ESCURA : CasaGUI.COR_CLARA;
    }

    public void atualizar(Jogo jogo) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                CasaGUI casaGUI = casas[x][y];
                
                Tabuleiro tabuleiro = jogo.getTabuleiro();
                Casa casa = tabuleiro.getCasa(x, y);
                if (casa.possuiPeca()) {
                    Pedra peca = casa.getPeca();

                    switch (peca.getTipo()) {
                        case Pedra.PEDRA_BRANCA:
                            casaGUI.desenharPedraBranca();
                            break;
                        case Dama.DAMA_BRANCA:
                            casaGUI.desenharDamaBranca();
                            break;
                        case Pedra.PEDRA_VERMELHA:
                            casaGUI.desenharPedraVermelha();
                            break;
                        case Dama.DAMA_VERMELHA:
                            casaGUI.desenharDamaVermelha();
                            break;
                    }
                }
                else {
                    casaGUI.apagarPeca();
                }
            }
        }
    }

    public JanelaPrincipal getJanela() {
        return janela;
    }

    private void initComponents() {
        setLayout(new java.awt.GridLayout(8, 8));
    }
}
