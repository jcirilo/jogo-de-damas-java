package com.damas.objetos;

/**
 * O Tabuleiro do jogo. 
 * Responsável por armazenar as 64 casas.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public class Tabuleiro {
    
    public static final int MAX_LINHAS = 8;
    public static final int MAX_COLUNAS = 8;
 
    private Jogador jogadorUm; // controla as pedras brancas
    private Jogador jogadorDois; // controla as pedras vermelhas
    private Casa[][] casas;
    private int jogada;

    public Tabuleiro() {
        montarTabuleiro();
        jogada = 0;
    }

    /**
     * Comanda uma Peça na posicão (origemX, origemY) fazer um movimento 
     * para (destinoX, destinoY).
     * 
     * @param origemX linha da Casa de origem.
     * @param origemY coluna da Casa de origem.
     * @param destinoX linha da Casa de destino.
     * @param destinoY coluna da Casa de destino.
     */
    public void moverPeca(int origemX, int origemY, int destinoX, int destinoY) {
        Casa origem = getCasa(origemX, origemY);
        Casa destino = getCasa(destinoX, destinoY);
        Pedra peca = origem.getPeca();

        // REGRAS DA PEÇA BRANCA
        if (vezDe() == 1 && (peca.getTipo() == Peca.PEDRA_BRANCA || peca.getTipo() == Peca.PEDRA_BRANCA ) ) {
            
            if (destino.getPeca() == null) {
                if (peca.podeMover(destino)) {
                    peca.mover(destino);
                    jogada++;
                }

            } else {
                if (podeComer(origem, destino)){
                    comerPeca(origem, destino);
                    jogadorUm.addPonto();
                    jogada++;
                }
            }
        }

        // REGRAS DA PEÇA VERMELHA
        if (vezDe() == 2 && (peca.getTipo() == Peca.PEDRA_VERMELHA || peca.getTipo() == Peca.DAMA_VERMELHA ) ) {

            if (destino.getPeca() == null) {
                if (peca.podeMover(destino)) {
                    peca.mover(destino);
                    jogada++;
                }
            } else {
                if (podeComer(origem, destino)){
                    comerPeca(origem, destino);
                    jogadorDois.addPonto();
                    jogada++;
                }
            }
        }
        
    }

    // TODO virar dama

    // TODO Modificar o movimento da dama

    /**
     * Faz a função de comer a peca e posiciona-las
     * @param casaOrigem
     * @param casaDestino
     */
    private void comerPeca(Casa casaOrigem, Casa casaDestino) {

        Casa origem = getCasa(casaOrigem.getX(), casaOrigem.getY());
        Casa destino = getCasa(casaDestino.getX(), casaDestino.getY());
        Pedra pecaOrigem = origem.getPeca();

        int posX = (destino.getX() - origem.getX()) + destino.getX();
        int posY = (destino.getY() - origem.getY()) + destino.getY();
        
        Casa proximaCasa = getCasa(posX, posY);        
        pecaOrigem.mover(proximaCasa);
        destino.removerPeca();
    }

    /**
     * Verifica se pode comer a peca na casa indicada
     * @param origem casa de origem
     * @param destino casa de destino
     * @return verdadeiro se a proxima casa na mesma direção estiver vazia, caso contrario falso
     */
    private boolean podeComer(Casa casaOrigem, Casa casaDestino) {

        Casa origem = getCasa(casaOrigem.getX(), casaOrigem.getY());
        Casa destino = getCasa(casaDestino.getX(), casaDestino.getY());
        Pedra pedraOrigem = origem.getPeca();
        Pedra pedraDestino = destino.getPeca();

        // VERIFICA SE AS PEÇAS SÃO DO MESMO TIPO
        if (pedraDestino == null) return false;
        if (pedraOrigem.getTipo() == pedraDestino.getTipo()) return false;
        if (pedraOrigem.getTipo() == Peca.PEDRA_BRANCA && pedraDestino.getTipo() == Peca.DAMA_BRANCA) return false;
        if (pedraOrigem.getTipo() == Peca.PEDRA_VERMELHA && pedraDestino.getTipo() == Peca.DAMA_VERMELHA) return false;

        if (destino.getPeca() !=  null) {
            // Sentido apontado + uma casa
            int posX = (destino.getX() - origem.getX() + destino.getX());
            int posY = (destino.getY() - origem.getY() + destino.getY());

            if (getCasa(posX, posY).getPeca() == null) return true;

        }

        return false;
    }

    /**
     * Calcula de quem é a vez de fazer a jogada, jogador um fica com a vez quando o número de jogadas
     * for par e o jogador dois quando o número de jogadas for impar
     * @return Número do jogao do que está com a vez (1 = Jogador 1, 2 = Jogador 2)
     */
    public int vezDe() {
        if ((jogada % 2) == 0) {
            return 1;
        }    
        return 2;
    }

    /**
     * Adiciona as Casas no tabuleiro
     */
    private void montarTabuleiro() {
        casas = new Casa[MAX_LINHAS][MAX_COLUNAS];
        for (int x = 0; x < MAX_LINHAS; x++) {
            for (int y = 0; y < MAX_COLUNAS; y++) {
                Casa casa = new Casa(x, y);
                casas[x][y] = casa;
            }
        }
    }

    /**
     * Posiciona peças no tabuleiro.
     * Utilizado na inicialização do jogo.
     */
    public void criarPecas() {

        /**
         * Cria e as poe as pecas brancas na parte inferior do tabuleiro 
        */
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 3; y++) {
                if((x % 2 == 0) && (y % 2 == 0)) {
                    Casa casa = getCasa(x, y);
                    new Pedra(casa, Peca.PEDRA_BRANCA);
                }
                else if ((x % 2 != 0) && (y % 2 != 0)){
                    Casa casa = getCasa(x, y);
                    new Pedra(casa, Peca.PEDRA_BRANCA);
                }
            }
        }

        /**
         * Cria e as poe as pecas vermelhas na parte superior do tabuleiro
        */
        for (int x = 0; x < 8; x++) {
            for (int y = 5; y < 8; y++) {
                if ((x % 2 != 0) && (y % 2 != 0)) {
                    Casa casa = getCasa(x, y);
                    new Pedra(casa, Peca.PEDRA_VERMELHA);
                }
                else if ((x % 2 == 0) && (y % 2 == 0)) {
                    Casa casa = getCasa(x, y);
                    new Pedra(casa, Peca.PEDRA_VERMELHA);
                }
            }
        }
    }

    /**
     * @param x linha
     * @param y coluna
     * @return Casa na posicao (x,y)
     */
    public Casa getCasa(int x, int y) {
        return casas[x][y];
    }

    public void setJogadorUm(Jogador jogador) {
        jogadorUm = jogador;
    }

    public void setJogadorDois(Jogador jogador) {
        jogadorDois = jogador;
    }

    public Jogador getJogadorUm() {
        return jogadorUm;
    }

    public Jogador getJogadorDois() {
        return jogadorDois;
    }

    public int getJogada() {
        return jogada;
    }
}