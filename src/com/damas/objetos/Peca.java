package com.damas.objetos;


/**
 * @author João Victor da S. Cirilo joao.cirilo@academico.ufpb.br
 */
public interface Peca {
    
    public static final int PEDRA_BRANCA = 0;
    public static final int DAMA_BRANCA = 1;
    public static final int PEDRA_VERMELHA = 2;
    public static final int DAMA_VERMELHA = 3;

    /**
     * Implementa a regra de movimento
     * @param destino
     * @return
     */
    boolean podeMover(Casa destino);
}
