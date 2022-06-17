package com.damas.objetos;

/**
 * Interface com os métodos abstratos das peças
 * @author João Victor da S. Cirilo {@link joao.cirilo@academico.ufpb.br}
 */
public interface Peca {
    
    public static final int PEDRA_BRANCA = 0;
    public static final int DAMA_BRANCA = 1;
    public static final int PEDRA_VERMELHA = 2;
    public static final int DAMA_VERMELHA = 3;

    /**
     * Movimenta a peca para uma nova casa. Antes de trocar de casa a Peca armazena a casa no
     * campo ultimaCasa, isso servirá para verificar qual sentido em que a peça se moveu para
     * implementar a lógica de comer peças.
     * @param destino nova casa que ira conter esta peca.
     */
    public void mover(Casa destino);
    
    /**
     * Implementa a regra de movimento da peça
     * @param destino - tipo {@code Casa} destino da peça
     * @return {@code boolean}
     */
    public boolean isMovimentoValido(Casa destino);

    /**
     * Retorna o tipo da peça
     * @return
     * {@code int} 0 - {@code Pedra} branca
     * <li>{@code int} 1 - {@code Dama} branca</li>
     * <li>{@code int} 2 - {@code Pedra} vermelha</li>
     * <li>{@code int} 3 - {@code Dama} verelha</li>
     */
    public int getTipo();
}
