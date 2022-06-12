package com.damas.objetos;

/**
 * Armazena o tabuleiro e responsavel por posicionar as pecas.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public class Jogo {

    private Tabuleiro tabuleiro;
    private Jogador jogadorUm;
    private Jogador jogadorDois;

    public Jogo() {
        tabuleiro = new Tabuleiro();
        jogadorUm = new Jogador("Jogador 1");
        jogadorDois = new Jogador("Jogador 2");

        tabuleiro.criarPecas();
        tabuleiro.setJogadorBranco(jogadorUm);
        tabuleiro.setJogadorVermelho(jogadorDois);
    }

    public int temGanhador() {
        if (jogadorUm.getPontos() == 12) return 1;
        if (jogadorDois.getPontos() == 12) return 2;
        return 0;
    }

    /**
     * @return o Tabuleiro em jogo.
     */
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }
}
