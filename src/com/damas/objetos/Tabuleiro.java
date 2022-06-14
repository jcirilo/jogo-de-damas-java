package com.damas.objetos;

import java.util.ArrayList;

// TODO - Refatorar!
// TODO - Método para verificar se há mais peças adjacentes depois de uma jogada com ponto marcado

/**
 * O Tabuleiro do jogo.
 * Responsável por armazenar as 64 casas do jogo e por possuir as regras de: 
 * comer, adicionar pontos caso um jogador coma uma peça, movimento NO tabuleiro (Não da peca)
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 * @author João Victor da S. Cirilo joao.cirilo@academico.ufpb.br
 */
public class Tabuleiro {
    
    public static final int MAX_LINHAS = 8;
    public static final int MAX_COLUNAS = 8;
 
    private Jogador jogadorBranco;
    private Jogador jogadorVermelho;
    private Casa[][] casas;
    private int vezAtual;
    private int jogadas;
    private int jogadasSemComerPeca;
    private ArrayList<Casa> pecasAComer;

    public Tabuleiro() {
        montarTabuleiro();
        vezAtual = 1;
        jogadasSemComerPeca = 0;
        jogadas = 0;
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

        if (vezAtual == 1 && (peca.getTipo() == Pedra.PEDRA_BRANCA || peca.getTipo() == Pedra.DAMA_BRANCA)) {
            
            if (peca.isMovimentoValido(destino)) {
                        
                if (podePercorrerNoTabuleiro(origem, destino)) {
                    peca.mover(destino);
                    jogadas++;
            
                    if (podeTransformarParaDama(destino)) transformarPedraParaDama(destino);

                    trocarDeVez();
                }
            }
        } else {
            if (vezAtual == 2 && (peca.getTipo() == Pedra.PEDRA_VERMELHA || peca.getTipo() == Pedra.DAMA_VERMELHA)) {

                if (peca.isMovimentoValido(destino)) {
                    
                    if (podePercorrerNoTabuleiro(origem, destino)) {
                        peca.mover(destino);
                        jogadas++;
                        
                        if (podeTransformarParaDama(destino)) transformarPedraParaDama(destino);
                        trocarDeVez();
                    }
                }
            }
        }
    }

    /**
     * Simula o caminho do destino até a origem e verifica se:
     * - Há peça de mesma cor no caminho
     * - Se há mais de uma peça em sequência no caminho
     * - Se há peças para comer no caminho, se verdadeiro come e adiciona pontos ao jogador 
     * @param origem Casa de origem
     * @param destino Casa de destino
     * @return false se há peça de mesma cor no caminho, false se há mais de uma peça em sequência no caminho
     */
    private boolean podePercorrerNoTabuleiro(Casa origem, Casa destino) {
        Pedra peca = origem.getPeca();
        int casasComPecaSeguidas = 0;

        if (destino.getPeca() != null) return false;

        // SENTIDO DO MOVIMENTO E DISTÂNCIA DO MOVIMENTO
        int sentidoX = (destino.getX() - origem.getX());
        int sentidoY = (destino.getY() - origem.getY());
        int distanciaX = Math.abs(sentidoX); 
        int distanciaY = Math.abs(sentidoY); 
        
        if ((distanciaX == 0) || (distanciaY == 0)) return false;

        sentidoX = sentidoX/distanciaX;
        sentidoY = sentidoY/distanciaY;

        if ((distanciaX == 2 && distanciaY == 2) && (peca.getTipo() == Pedra.PEDRA_BRANCA) || (peca.getTipo() == Pedra.PEDRA_VERMELHA)) {
            Casa casa = getCasa((destino.getX() - sentidoX), (destino.getY() -sentidoY));
            if (casa.getPeca() == null) return false;
        }

        //PERCORRER AS CASAS E VERIFICAR:
        // 1 - SE HÁ MAIS DE UMA PEÇA SEGUIDA NO CAMINHO (VERDADEIRO RETORNA FALSO)
        // 2 - SE HÁ UMA PEÇA NO CAMINHO E É DA MESMA COR (VERDADEIRO RETORNA FALSO)
        // 3 - SE O MOVIMENTO É DE COMER PEÇA
        int i = origem.getX();
        int j = origem.getY();

        while (!((i == destino.getX()) || (j == destino.getY()))) {
            i += sentidoX;
            j += sentidoY;

            Casa alvo = getCasa(i, j);
            Pedra pecaAlvo = alvo.getPeca();

            if (!(pecaAlvo == null)) {
                casasComPecaSeguidas += 1;

                // VE SE TEM UMA PECA DO MESMO TIPO NO CAMNHO, CASO TENHA, RETORNA FALSE
                if ((peca.getTipo() == Peca.PEDRA_BRANCA || peca.getTipo() == Peca.DAMA_BRANCA) && (pecaAlvo.getTipo() == Pedra.PEDRA_BRANCA || pecaAlvo.getTipo() == Pedra.DAMA_BRANCA)) {
                    return false;
                }

                if ((peca.getTipo() == Peca.PEDRA_VERMELHA || peca.getTipo() == Peca.DAMA_VERMELHA) && (pecaAlvo.getTipo() == Pedra.PEDRA_VERMELHA || pecaAlvo.getTipo() == Pedra.DAMA_VERMELHA)) {
                    return false;
                }

            } else {

                // VE SE HÁ PEÇA PARA COMER NO CAMINHO E PASSAR A CASA À COLEÇÃO pecasAComer() PARA DEPOIS COME-LAS
                if (casasComPecaSeguidas == 1) {
                    Casa casa = getCasa((alvo.getX() - sentidoX), (alvo.getY() - sentidoY));
                    //TODO - peçasAComer.add(casa);
                }
                casasComPecaSeguidas = 0;
            }

            if (casasComPecaSeguidas == 2) {
                return false;
            }
        }
        return true;
    }

    /**
     * Limpa as peças a comer na variável "pecasAComer", e retorna a quantidade de peças comidas.
     * @return int quantidade de peças comidas.
     */
    private int comerPecas() {
        int pecasComidas = pecasAComer.size();
        return pecasComidas;
    }

    /**
     * Verifica se a pedra pode virar dama.
     * @param origem casa onde a pedra que vai ser verificada está.
     * @return verdadeiro se pode virar, falso se não pode virar.
     */
    private boolean podeTransformarParaDama(Casa casa) {
        
        // REGRA PARA PEÇAS BRANCAS
        if (casa.getPeca().getTipo() == Peca.PEDRA_BRANCA) {
            if (casa.getY() == 7) return true;
        }
        
        // REGRA PARA PEÇAS VERMELHAS
        if (casa.getPeca().getTipo() == Peca.PEDRA_VERMELHA) {
            if (casa.getY() == 0) return true;
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

    public void trocarDeVez() {
        if (vezAtual == 1) {
            vezAtual = 2;
        } else {
            vezAtual = 1;
        }
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

        // CRIA E PÕE AS PEÇAS NA PARTE INFERIOR DO TABULEIRO
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 3; y++) {
                if((x % 2 == 0) && (y % 2 == 0)) {
                    Casa casa = getCasa(x, y);
                    new Pedra(casa, Pedra.PEDRA_BRANCA);
                }
                
                else if ((x % 2 != 0) && (y % 2 != 0)){
                    Casa casa = getCasa(x, y);
                    new Pedra(casa, Peca.PEDRA_BRANCA);
                }
            }

        }


        // CRIA E POE AS PEÇAS NA PARTE SUPERIOR DO TABULEIRO
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

    /**
     * @return Número do jogador do que está com a vez (1 = Jogador 1, 2 = Jogador 2)
     */
    public int getVez() {
        return vezAtual;
    }

    public int getJogadasSemComerPecas() {
        return jogadasSemComerPeca;
    }

    public int getJogada() {
        return jogadas;
    }
}