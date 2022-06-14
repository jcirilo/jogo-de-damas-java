package com.damas.objetos;

/**
 * Representa uma Peça do jogo.
 * Possui uma casa e um tipo associado.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 * @author João Victor da S. Cirilo joao.cirilo@academico.ufpb.br
 */
public class Pedra implements Peca {

    protected Casa casa;
    protected int tipo;

    /**
     * @param casa Objeto Casa
     * @param tipo int tipo de peça (0 = Pedra Branca, 2 = Peddra vermelha) 
     */
    public Pedra(Casa casa, int tipo) {
        this.casa = casa;
        this.tipo = tipo;
        casa.colocarPeca(this);
    }
    
    /**
     * Movimenta a peca para uma nova casa. Antes de trocar de casa a Peca armazena a casa no
     * campo ultimaCasa, isso servirá para verificar qual sentido em que a peça se moveu para
     * implementar a lógica de comer peças.
     * @param destino nova casa que ira conter esta peca.
     */
    public void mover(Casa destino) {
        casa.removerPeca();
        destino.colocarPeca(this);
        casa = destino;
    }

    /**
     * Verifica se a posição de destino está de acordo com o movimento da Pedra e retorna um boolean.
     * Calcula a distância de x e de y da casa atual até a casa de destino e verifica se é igual a
     * uma casa, se sim retorna true caso contrário retorna false. E dependendo do tipo da Peca o y só pode
     * ter um valor já que o tabuleiro está ordenado verticalmente.
     * @param destino
     * @return boolean, se a distância for igual a uma casa, true caso contrário false.
     */
    @Override
    public boolean isMovimentoValido(Casa destino) {

        // SENTIDO UNITÁRIO E DISTANCIA X E Y DA CASA ATUAL ATÉ A CASA DE DESTINO
        int sentidoX = (destino.getX() - casa.getX());
        int sentidoY = (destino.getY() - casa.getY());
        int distanciaX = Math.abs(sentidoX);
        int distanciaY = Math.abs(sentidoY);

        if ((distanciaX == 0) || (distanciaY == 0)) return false;
        
        sentidoX = sentidoX/distanciaX;
        sentidoY = sentidoY/distanciaY;

        // REGRA DE MOVIMENTO NO CASO DA DISTÂNCIA SER DE 2 CASAS (MOVIMENTO DE COMER PEÇA)
        if ((distanciaX == 2 || distanciaY == 2) && (distanciaX == distanciaY)) {
            return true;
        }

        // REGRA DE MOVIMENTO NO CASO DA DISTÂNCIA SER ADJACENTE (MOVIMENTO PADRÃO).
        // NESTE CASO A PEÇA VERMELHA NÃO PODE VOLTAR NEM A BRANCA.
        // REGRA DE MOVIMENTO PARA AS PEDRAS BRANCAS
        if (tipo == Pedra.PEDRA_BRANCA) {
            if ((distanciaX == 1 || distanciaY == 1) && (distanciaX == distanciaY) && sentidoY == 1) {
                return true;
            } else {
                return false;
            }
        }

        // REGRA DE MOVIMENTO DAS PEDRAS VERMELHAS
        if (tipo == Pedra.PEDRA_VERMELHA) {
            if ((distanciaX == 1 || distanciaY == 1) && (distanciaX == distanciaY) && sentidoY == -1) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

        /**
     * Valor    Tipo
     *   0   Branca (Pedra)
     *   1   Branca (Dama)
     *   2   Vermelha (Pedra)
     *   3   Vermelha (Dama)
     * @return o tipo da peca.
     */
    public int getTipo() {
        return tipo;
    }
}
