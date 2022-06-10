package com.damas.objetos;


/**
 * @author João Victor da S. Cirilo joao.cirilo@academico.ufpb.br
 */
public interface Peca {
    /**
     * Implementa a regra de movimento
     * @param destino
     * @return
     */
    boolean podeMover(Casa destino);
}
