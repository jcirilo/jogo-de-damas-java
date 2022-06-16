# Jogo de Damas - Java

## Sobre

Último projeto em grupo da turma de Introdução à Linguagem de Programação 2021.2 da Universidade Federal da Paraíba.

O projeto consiste em desenvolver um jogo simples com interface gráfica utilizando conceitos de Programação Orientada à Objetos, o jogo escolhido pelo grupo foi um jogo de Damas. A Classe contendo o método principal é a `App.java` no diretório `./src/com/damas/App.java`

### Regras do jogo

- A quantidade de jogadores é 2
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

### Faltando

- [ ] Implementar regra para remoção do sopro em geral
- [ ] Adicionar alguns elementos ao GUI do jogo
