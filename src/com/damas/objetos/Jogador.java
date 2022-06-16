package com.damas.objetos;

/**
 * Jogador, recebe um nome e armazena os pontos
 * @author João Victor da S. Cirilo {@link joao.cirilo@academico.ufpb.br}
 */
public class Jogador {
    public static final String DEFAULT_NAME = "Anônimo";
    
    private String nome;
    private int pontos;

    public Jogador (String nome) {

        if (validarNome(nome)) {
            this.nome = nome;
        } else {
            this.nome = DEFAULT_NAME;
        }

        pontos = 0;
    }

    private boolean validarNome(String nome) {
        if (nome.length() > 16) return false;
        return true;
    }

    public void addPonto() {
        pontos++;
    }

    public void addPonto(int pontos) {
        this.pontos += pontos;
    }

    public int getPontos() {
        return pontos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        validarNome(nome);
    }

}
