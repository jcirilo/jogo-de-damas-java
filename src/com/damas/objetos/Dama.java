package com.damas.objetos;


/**
 * @author João Victor da S. Cirilo joao.cirilo@academico.ufpb.br
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
    public boolean podeMover(Casa destino) {
        int deltaX = Math.abs((destino.getX() - casa.getX()));
        int deltaY = Math.abs((destino.getY() - casa.getY()));

        if (deltaX == deltaY) return true;

        return false;
    }

    public Casa temPecaParaComer(Casa destino) {
        return destino;
    }
}
