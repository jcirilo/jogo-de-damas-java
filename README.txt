A Classe principal é a "App.java" no diretório "./src/com/damas/App.java"

@Author: João Victor da Silva Cirilo <joao.cirilo@academico.ufpb.br>
@Author: José Alisson Rocha da Silva <jose.alisson2@academico.ufpb.br>
@Author: Arthur Miranda Tavares <arthur.miranda@academico.ufpb.br>

@Version: beta-1.16.6.2022

REGRAS DE JOGO:

- A quantidade de total de jogadores é 2
- A primeira peça a se mover é a peça branca.
- Cada jogador possui 12 peças.
- Se alguma peça chegar no lado oposto do tabuleiro se transforma em dama
- Pedras não podem voltar uma casa
- Pedras podem comer voltando
- O jogo acaba quando algum jogador fizer 12 pontos.
- O jogo acaba quando durante 20 jogadas nenhum jogador tiver feito pontos
- O jogador continua com a vez se, logo após marcar ponto, tiver outra peça para comer em alguma diagonal
- Bloqueio de casa se a regra acima for validada
- Sopro liberado

FALTANDO:

- Implementar regra para remoção sopro em geral

EXLICAÇÃO DO CÓDIGO:

- INTERFACE Peca -
    Possui os métodos de movimentos abstratos das peças e variáveis publicas dos tipos das peças

- CLASSE Pedra IMPLEMENTS Peca -
    Implementa os métodos de movimento da interface Peca

- CLASSE Dama EXTENDS Pedra -
    Subclasse da classe pedra que sobreescreve a regra de movimento da pedra

- CLASSE Jogo
    Contém a lógica das regras do jogo de damas, esta classe receberá

    CAMPOS
        private Tabuleiro tabuleiro
            Tabuleiro que será utilizado e modificado durante o jogo

        private Jogador jogadorUm;
            Jogador que receberá os pontos das peças brancas

        private Jogador jogadorDois;
            Jogador que receberá os pontos das peças vermelhas

        private int vezAtual = 1;
            Variável que armazena a vez. Os valores podem ser 1 ou 2. 

        private int jogadas = 0;
            Variável que armazena o número de jogadas totais

        private int jogadasSemComerPeca = 0;
            Variável que armazena a quantidade de jogadas seguidas sem alguem marcar ponto, caso alguém marque um ponto, ela voltará para 0.
        
        private ArrayList<Casa> pecasAComer;
            Variável que armazena casas com peças para comer depois do jogador fazer movimento de comer.

        private Casa casaBloqueadaOrigem;
            Variável que armazena uma casa que está bloqueada, se o valor for diferente de "null" o jogador da vez poderá apenas mover a peça desta casa.

