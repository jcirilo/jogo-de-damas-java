package com.damas.objetos;

/**
 * O Tabuleiro do jogo. 
 * Responsável por armazenar as 64 casas e possuir a lógica das regras da partida.
 */
public class Tabuleiro {
    
    public static final int MAX_LINHAS = 8;
    public static final int MAX_COLUNAS = 8;
 
    private Jogador jogadorBranco; // controla as pedras brancas
    private Jogador jogadorVermelho; // controla as pedras vermelhas
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
        if (vezDe() == 1 && (peca.getTipo() == Peca.PEDRA_BRANCA || peca.getTipo() == Peca.DAMA_BRANCA ) ) {
            
            if (destino.getPeca() == null) {

                // ENTRA NA REGRA DE MOVIMENTO DA PEÇA
                if (peca.podeMover(destino)) {
                    peca.mover(destino);
                    if (podeTransformarParaDama(destino)) transformarPedraParaDama(destino);
                    jogada++;
                }

            } else {
                if (podeComer(origem, destino)){
                    comerPeca(origem, destino);
                    jogadorBranco.addPonto();
                    jogada++;
                }
            }
        }

        // REGRAS DA PEÇA VERMELHA
        if (vezDe() == 2 && (peca.getTipo() == Peca.PEDRA_VERMELHA || peca.getTipo() == Peca.DAMA_VERMELHA ) ) {

            if (destino.getPeca() == null) {
                
                // ENTRA NA REGRA DE MOVIMENTO DA PEÇA
                if (peca.podeMover(destino)) {
                    peca.mover(destino);
                    jogada++;
                    if (podeTransformarParaDama(destino)) transformarPedraParaDama(destino);
                }
            } else {
                if (podeComer(origem, destino)){
                    comerPeca(origem, destino);
                    jogadorVermelho.addPonto();
                    jogada++;
                }
            }
        }
        
    }

    /**
     * Verifica se a pedra pode virar dama.
     * @param origem casa onde a pedra que vai ser verificada está.
     * @return verdadeiro se pode virar, falso se não pode virar.
     */
    private boolean podeTransformarParaDama(Casa casa) {
        
        // Regra para pecas brancas
        if (casa.getPeca().getTipo() == Peca.PEDRA_BRANCA) {
            if (casa.getY() == 7) return true;
            return false;
        }
        
        // Regra para epcas vermelhas
        if (casa.getPeca().getTipo() == Peca.PEDRA_VERMELHA) {
            if (casa.getY() == 0) return true;
            return false;
        }

        return false;
    }

    /**
     * Transforma a pedra da casa passada como parametro em dama
     * @param casa casa contendo a dama para ser transformada.
     */
    private void transformarPedraParaDama(Casa casa) {
        int tipoDePedra = casa.getPeca().getTipo();
        casa.removerPeca();

        if (tipoDePedra == Peca.PEDRA_BRANCA) casa.colocarPeca(new Dama(casa, Peca.DAMA_BRANCA));
        if (tipoDePedra == Peca.PEDRA_VERMELHA) casa.colocarPeca(new Dama(casa, Peca.DAMA_VERMELHA));
    }

    /**
     * Faz a função de comer a a peça e posicionar corretamente;
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
        if (podeTransformarParaDama(proximaCasa)) transformarPedraParaDama(proximaCasa);
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
                    new Dama(casa, Peca.DAMA_BRANCA);
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

    public void setJogadorBranco(Jogador jogador) {
        jogadorBranco = jogador;
    }

    public void setJogadorVermelho(Jogador jogador) {
        jogadorVermelho = jogador;
    }

    public Jogador getJogadorBranco() {
        return jogadorBranco;
    }

    public Jogador getJogadorVermelho() {
        return jogadorVermelho;
    }

    public int getJogada() {
        return jogada;
    }
}