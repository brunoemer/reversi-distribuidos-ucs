# reversi-distribuidos-ucs
Universidade de Caxias do Sul
Centro de Ciências Exatas e da Tecnologia
INF0213A - Sistemas Distribuídos e Programação Paralela
Período 2015/2
Professor: André Luis Martinotto
Descrição do Trabalho
1) Objetivo
O objetivo desse trabalho consiste na implementação de um jogo de tabuleiro chamado Othello, também, conhecido como Reversi. A implementação deverá ser desenvolvida utilizando Sockets ou RMI/RPC.
O trabalho deverá empregar o modelo de cliente-servidor.
O lado servidor deverá:
● Iniciar o tabuleiro e distribuir aos dois participantes.
● Controlar o tabuleiro do jogo dos dois participantes;
● Receber dos clientes as coordenadas da jogada, verificar se a jogada é válida e atualizar o tabuleiro do jogo;
● Enviar para os clientes as atualizações do tabuleiro;
● Ao final do jogo enviar a ambos participantes do jogo a mensagem indicando quem foi o vencedor e quem foi o perdedor da batalha;
O lado cliente deverá:
● Receber o tabuleiro inicial;
● Enviar as coordenadas da jogada;
● Receber o tabuleiro atualizado;
Na Seção 2 tem-se uma breve descrição do jogo e alguns exemplos de jogada. A descrição do jogo e os exemplos de jogadas foram obtidos do endereço:
http://othelloclassic.blogspot.com.br/2010/06/regras-do-jogo.html.
Já no endereço http://www.othelloonline.org/reversi.php tem-se uma versão online do jogo.
