package com.damas.objetos;

import java.util.ArrayList;

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
 
    private Casa[][] casas;

    private Jogador jogadorBranco;
    private Jogador jogadorVermelho;
    private int vezAtual;
    private int jogadas;
    private int jogadasSemComerPeca;
    private ArrayList<Casa> pecasAComer;

    public Tabuleiro() {
        montarTabuleiro();
        pecasAComer = new ArrayList<Casa>();
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

        if (getVez() == 1 && (peca.getTipo() == Pedra.PEDRA_BRANCA || peca.getTipo() == Pedra.DAMA_BRANCA)) {
            
            if (peca.isMovimentoValido(destino)) {
                        
                if (percorrerNoTabuleiro(origem, destino)) {
                    
                    peca.mover(destino);

                    if (pecasAComer.size() > 0) {
                        comerPecas();
                        System.out.println(pecasAComer);
                        if (!podeContinuarJogado(destino)) trocarDeVez();
                    } else {
                        trocarDeVez();
                    }

                    jogadas++;
                    if (podeTransformarParaDama(destino)) transformarPedraParaDama(destino);
                }
            }
        } else {
            if (getVez() == 2 && (peca.getTipo() == Pedra.PEDRA_VERMELHA || peca.getTipo() == Pedra.DAMA_VERMELHA)) {

                if (peca.isMovimentoValido(destino)) {
                        
                    if (percorrerNoTabuleiro(origem, destino)) {

                        peca.mover(destino);
                        if (pecasAComer.size() > 0) {
                            comerPecas();
                            if (!podeContinuarJogado(destino)) trocarDeVez();
                        } else {
                            trocarDeVez();
                        }
    
                        jogadas++;
                        if (podeTransformarParaDama(destino)) transformarPedraParaDama(destino);
                    }
                }
            }
        }
    }

    /**
     * <p>
     * Percorre as casas da casa de origem clicada até a casa de destino clicada e
     * verifica se o caminho é valido.
     * </p> 
     * 
     * <p>
     * Também adiciona objetos a lista de casas com peça disponivel para comer, mas caso
     * o movimento seja invalidado pelo método, se houve casas adicionadas a essa lista, serão removidas.
     * </p>
     * 
     * @param origem Casa de origem
     * @param destino Casa de destino
     * @return
     *  <ul>
     *      <li>{@code false} Se o destino clicado tiver uma peça</li>  
     *      <li>{@code false} Se tiver uma pedra de mesma cor no caminho</li>
     *      <li>{@code false} Se tiver mais de uma pedra em sequência no caminho</li>
     *      <li>{@code false} Se o tipo for Pedra:
     *          <ul>
     *              <li>{@code false} Se a distância for de uma casa voltando</li>
     *              <li>{@code false} Se a distância for de duas casas e não tiver peça para comer</li>
     *          </ul>
        </ul>
     */
    private boolean percorrerNoTabuleiro(Casa origem, Casa destino) {
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

        // REGRA DE MOVIMENTO DAS PEDRAS NO TABULEIRO CASO A DISTÂNCIA ATÉ A CASA CLICADA SEJA DE 2 BLOCOS
        if ((distanciaX == 2 && distanciaY == 2) && (peca.getTipo() == Pedra.PEDRA_BRANCA) || (peca.getTipo() == Pedra.PEDRA_VERMELHA)) {
            Casa casa = getCasa((destino.getX() - sentidoX), (destino.getY() -sentidoY));
            if (casa.getPeca() == null) return false;
        } else {
            
            // REGRA DE MOVIMENTO DAS PEDRAS NO TABULEIRO CASO A DISTÂNCIA ATÉ A CASA CLICADA SEJA DE 1 BLOCO
            if (peca.getTipo() == Pedra.PEDRA_BRANCA) {
                if ((distanciaX == 1 || distanciaY == 1) && (distanciaX == distanciaY) && sentidoY == 1) {
                    return true;
                } else {
                    return false;
                }
            }

            // REGRA DE MOVIMENTO DAS PEDRAS VERMELHAS
            if (peca.getTipo() == Pedra.PEDRA_VERMELHA) {
                if ((distanciaX == 1 || distanciaY == 1) && (distanciaX == distanciaY) && sentidoY == -1) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        //PERCORRER AS CASAS E VERIFICAR:
        // 1 - SE HÁ MAIS DE UMA PEÇA SEGUIDA NO CAMINHO (VERDADEIRO RETORNA FALSO)
        // 2 - SE HÁ UMA PEÇA NO CAMINHO E É DA MESMA COR (VERDADEIRO RETORNA FALSO)
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
                    if (pecasAComer.size() > 0) pecasAComer.removeAll(pecasAComer);
                    return false;
                }

                if ((peca.getTipo() == Peca.PEDRA_VERMELHA || peca.getTipo() == Peca.DAMA_VERMELHA) && (pecaAlvo.getTipo() == Pedra.PEDRA_VERMELHA || pecaAlvo.getTipo() == Pedra.DAMA_VERMELHA)) {
                    if (pecasAComer.size() > 0) pecasAComer.removeAll(pecasAComer);
                    return false;
                }

            } else {

                // VE SE HÁ PEÇA PARA COMER NO CAMINHO E PASSAR A CASA À COLEÇÃO pecasAComer() PARA DEPOIS COME-LAS
                if (casasComPecaSeguidas == 1) {
                    Casa casa = getCasa((alvo.getX() - sentidoX), (alvo.getY() - sentidoY));
                    pecasAComer.add(casa);
                }
                casasComPecaSeguidas = 0;
            }

            if (casasComPecaSeguidas == 2) {
                if (pecasAComer.size() > 0) pecasAComer.removeAll(pecasAComer);
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Percorre as casas do tabuleirio a partir da casa de origem indicada no sentido dado
     * por {@code sentidoX} e {@code sentidoY}.
     * </p>
     * @param origem Casa de origem da peça
     * @param sentidoX Delta X
     * @param sentidoY Delta Y
     * @return 
     *  <ul>
     *      <li>{@code false} Se há uma pedra de mesma cor no caminho</li>
     *      <li>{@code false} Se há mais de uma pedra em sequência no caminho</li>
     *  </ul>
     */
    private boolean verificarSeTemPecaParaComer(Casa origem, int sentidoX, int sentidoY) {

        Pedra peca = origem.getPeca();
        int x = origem.getX();
        int y = origem.getY();
        int pecasSeguidasNoCaminho = 0;

        while (!((x == 0 || x == 7) || (y == 0 || y == 7))) {
            x += sentidoX;
            y += sentidoY;

            Pedra pecaAtual = getCasa(x, y).getPeca();

            if (!( pecaAtual == null)) {
                
                pecasSeguidasNoCaminho += 1;

                // VERIFICA SE HÁ ALGUMA PEÇA DO MESMO TIPO NO CAMINHO SE SIM, RETORNA FALSE;
                if ((peca.getTipo() == Pedra.PEDRA_BRANCA || peca.getTipo() == Pedra.DAMA_BRANCA) && 
                    (pecaAtual.getTipo() == peca.getTipo()) ) {
                        
                        return false;
                } else {
                    if ((peca.getTipo() == Pedra.PEDRA_VERMELHA || peca.getTipo() == Pedra.DAMA_VERMELHA) && 
                        (pecaAtual.getTipo() == peca.getTipo()) ) {
                        
                            return false;
                    }   
                }
            } else {

                if (pecasSeguidasNoCaminho == 1) {
                    return true;
                }

                if (pecasSeguidasNoCaminho == 2) {
                    return false;
                }
            }
        }

        return false;
    }

    /**
     * <p>
     * Dispara o método {@code verificarSeTemPecaParaComer()} no sentido
     * das quatro diagonais a partir da casa indicada.
     * </p>
     * @param origem Casa de onde vai partir a verifição
     * @return {@code true} Se há peça para comer em alguma diagonal
     */
    private boolean podeContinuarJogado(Casa origem) {

        if (verificarSeTemPecaParaComer(origem, -1, 1)) return true;
        if (verificarSeTemPecaParaComer(origem, 1, 1)) return true;
        if (verificarSeTemPecaParaComer(origem, 1, -1)) return true;
        if (verificarSeTemPecaParaComer(origem, -1, -1)) return true;

        return false;
    } 

    /**
     * Limpa as peças a comer na variável "pecasAComer", e retorna a quantidade de peças comidas.
     * @return int quantidade de peças comidas.
     */
    private int comerPecas() {
        int pecasComidas = pecasAComer.size();

        if (getVez() == 1) jogadorBranco.addPonto(pecasComidas);
        if (getVez() == 2) jogadorVermelho.addPonto(pecasComidas);

        for (Casa casa : pecasAComer) {
            casa.removerPeca();
        }

        pecasAComer.removeAll(pecasAComer);

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