package com.damas.objetos;

public class Dama extends Pedra {
    
    public static final int DAMA_BRANCA = 1;
    public static final int DAMA_VERMELHA = 3;

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
    public boolean podeMover(Casa destino) {
        if (destino.getPeca() != null) return false;

        int deltaY = destino.getY() - casa.getY();
        int deltaX = destino.getX() - casa.getX();
 
        if (Math.abs(deltaY) == Math.abs(deltaX)) {
            return true;
        }
        return false;
    }
}
