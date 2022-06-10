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
    public boolean podeMover(Casa destino) {
        int deltaY = destino.getY() - casa.getY();
        int deltaX = Math.abs((destino.getX() - casa.getX()));
        
        // Regra de movimento das pedras posicionadas na parte inferior
        if ((tipo == Peca.PEDRA_BRANCA) || (tipo == Peca.DAMA_BRANCA)) {
            if (deltaY == 1 && deltaX == 1) return true;
            return false;
        }
        // Regra de movimento das pedras posicionadas na parte superior 
        else {
            if (deltaY == -1 && deltaX == 1) return true;
            return false;
        }
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
