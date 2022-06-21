# Jogo de Damas - Java

## Sobre

 Último projeto em grupo da turma de Linguagem de Programação - 1 2021.2 da Universidade Federal da Paraíba.

 O projeto consiste em desenvolver um jogo simples com interface gráfica utilizando conceitos de Programação Orientada à Objetos. O jogo escolhido pelo grupo foi um jogo de Damas e a Classe contendo o método principal é a `App.java` no diretório `./src/com/damas/App.java`

 Para abrir e rodar o executável `.jar` em sua máquina, você precisará ter instalado o **Java Runtime Environment**. Você pode baixa-lo pelo [site oficial do Java](https://www.java.com/en/download/manual.jsp).

### Regras do jogo

- A quantidade de jogadores é 2
- A primeira peça a se mover é a peça branca.
- Cada jogador possui 12 peças.
- Se alguma peça chegar no lado oposto do tabuleiro se transforma em dama
- Pedras não podem voltar uma casa
- Pedras podem comer voltando
- O jogo acaba quando algum jogador fizer 12 pontos.
- O jogo acaba se depois de 20 jogadas ninguém ter comido alguma peça.
- Se após marcar um ponto, houver outra peça disponível para comer, o jogador é obrigado a comê-la.
- Sopro liberado.

### Controles e atalhos do jogo

 Para movimentar a peça clique primeiro na casa de origem depois na casa de destino.

- "**Ctrl + I**" - Status do jogo.
- "**Ctrl + N**" - Novo jogo.
- "**Ctrl + S**" - Sair.

### Faltando

- [ ] Implementar regra para remoção do sopro em geral
- [ ] Adicionar alguns elementos ao GUI do jogo
