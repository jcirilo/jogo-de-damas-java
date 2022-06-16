package com.damas.objetos;

/**
 * Dama do jogo.
 * <p>Recebe uma casa e um tipo associado</p>
 * @author João Victor da S. Cirilo {@link joao.cirilo@academico.ufpb.br}
 */
public class Dama extends Pedra{

    /**
     * @param casa Objeto Casa
     * @param tipo int tipo de peça (1 = Dama Branca, 3 = Dama vermelha) 
     */
    public Dama(Casa casa, int tipo) {
        super(casa, tipo);
    }

    /**
     * Movimento da Dama que pode andar várias casas na diagonal
     * @param destino
     * @return boolean. True se puder ser movida e false se não 
     */
   @Override
    public boolean isMovimentoValido(Casa destino) {
        int distanciaX = Math.abs((destino.getX() - casa.getX()));
        int distanciaY = Math.abs((destino.getY() - casa.getY()));

        if (distanciaX == distanciaY) return true;

        return false;
    }
}
