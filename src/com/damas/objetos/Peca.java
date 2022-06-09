package com.damas.objetos;

/**
 * Representa uma Peça do jogo.
 * Possui uma casa e um tipo associado.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 * @author João Victor da S. Cirilo joao.cirilo@academico.ufpb.br
 */
public class Peca {

    public static final int PEDRA_BRANCA = 0;
    public static final int PEDRA_VERMELHA = 2;

    protected Casa casa;
    protected int tipo;

    /**
     * @param casa Objeto Casa
     * @param tipo int tipo de peça (0 = Pedra Branca, 2 = Peddra vermelha) 
     */
    public Peca(Casa casa, int tipo) {
        this.casa = casa;
        this.tipo = tipo;
        casa.colocarPeca(this);
    }
    
    /**
     * Movimenta a peca para uma nova casa.
     * @param destino nova casa que ira conter esta peca.
     */
    public void mover(Casa destino) {
        casa.removerPeca();
        destino.colocarPeca(this);
        casa = destino;
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

    /**
     * Dita a regra de movimento
     * @param destino Casa de destino
     * @return
     */
    public boolean podeMover(Casa destino) {
        return podeMoverPedra(destino);
    }

    /**
     * Verifica se a posição de destino está de acordo com o movimento da Pedra e retorna um boolean.
     * Calcula a distância de x e de y da casa atual até a casa de destino e verifica se é igual a
     * uma casa, se sim retorna true caso contrário retorna false. E dependendo do tipo da Peca o y só pode
     * ter um valor já que o tabuleiro está ordenado verticalmente.
     * @param destino
     * @return boolean, se a distância for igual a uma casa, true caso contrário false.
     */
    private boolean podeMoverPedra(Casa destino) {
        int deltaY = destino.getY() - casa.getY();
        int deltaX = Math.abs((destino.getX() - casa.getX()));

        if (destino.getPeca() != null) return false;

        if ((tipo == 0) || (tipo == 1)) { // Movimento das pedras posicionadas na parte inferior
            if (deltaY == 1 && deltaX == 1) {
                return true;
            } else {
                return false;
            }
        } else { // Movimento das pedras posicionadas na parte superior
            if (deltaY == -1 && deltaX == 1) {
                return true;
            } else {
                return false;
            }
        }
    }
}
