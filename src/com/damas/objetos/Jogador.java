package com.damas.objetos;

/**
 * @author João Victor da S. Cirilo joao.cirilo@academico.ufpb
 */
public class Jogador {
    public static final String DEFAULT_NAME = "Anônimo";
    
    private String nome;
    private int pecasComidas;

    public Jogador (String nome) {

        if (validarNome(nome)) {
            this.nome = nome;
        } else {
            this.nome = DEFAULT_NAME;
        }

        pecasComidas = 0;
    }

    private boolean validarNome(String nome) {
        if (nome.length() > 16) return false;
        return true;
    }

    public int getPecasComidas() {
        return pecasComidas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        validarNome(nome);
    }

}
