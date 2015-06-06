package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	public static final int PORT = 8000;
	public static final int LENGTH = 8;
	private int[][] board;

	public Server() {
		
	}
	
	public ArrayList initPlay() {
		this.board = new int[Server.LENGTH][Server.LENGTH];
		for (int i = 0; i < Server.LENGTH; i++) {
			for (int j = 0; j < Server.LENGTH; j++) {
				this.board[i][j] = 0;
			}
		}
		
		ArrayList tab = new ArrayList();
		Piece p = new Piece(3, 3, Piece.PLAYER_2);
		tab.add(p);
		this.pieceToBoard(p);
		p = new Piece(4, 3, Piece.PLAYER_1);
		tab.add(p);
		this.pieceToBoard(p);
		p = new Piece(3, 4, Piece.PLAYER_1);
		tab.add(p);
		this.pieceToBoard(p);
		p = new Piece(4, 4, Piece.PLAYER_2);
		tab.add(p);
		this.pieceToBoard(p);
		return tab;
	}

	public ArrayList getChanges(Piece p) {
		ArrayList<Piece> tab = new ArrayList<Piece>();
		int x = p.getX(), y = p.getY();
		//verifica se esta sem peca
		if (this.getPlayerBoard(x, y) != 0) {
			return tab;
		}
		
		ArrayList<Piece> t1 = new ArrayList<Piece>(), t2 = new ArrayList<Piece>();
		int cx = 1, cy = 0, f1 = 0, f2 = 0; 
		//f1-f2= 0 - procurando, 1 - encontrou peca adversario, 2 - encontrou peca propria, 3 - chegou no fim e nao encontrou
		//horizontal
		while (true) {
			if (f1 == 0 || f1 == 1) {
				if (x+cx < 0 || x+cx >= Server.LENGTH || y+cy < 0 || y+cy >= Server.LENGTH) {
					f1 = 3;
				} else if (this.getPlayerBoard(x+cx, y+cy) == p.getPlayer()) {
					if (f1 == 1) {
						f1 = 2;
					} else if (f1 == 0) {
						f1 = 3;
					}
				} else if (this.getPlayerBoard(x+cx, y+cy) != 0) { //peca adversario
					f1 = 1;
					t1.add(new Piece(x+cx, y+cy, p.getPlayer()));
				} else {
					f1 = 3;
				}
			}
			
			if (f2 == 0 || f2 == 1) {
				if (x-cx < 0 || x-cx >= Server.LENGTH || y-cy < 0 || y-cy >= Server.LENGTH) {
					f2 = 3;
				} else if (this.getPlayerBoard(x-cx, y-cy) == p.getPlayer()) {
					if (f2 == 1) {
						f2 = 2;
					} else if (f2 == 0) {
						f2 = 3;
					}
				} else if (this.getPlayerBoard(x-cx, y-cy) != 0) { //peca adversario
					f2 = 1;
					t2.add(new Piece(x-cx, y-cy, p.getPlayer()));
				} else {
					f2 = 3;
				}
			}
			
			if ((f1 == 2 || f1 == 3) && (f2 == 2 || f2 == 3)) {
				break;
			}
			cx++;
		}
		if (f1 == 2 || f2 == 2) {
			tab.add(p);
			if (f1 == 2) {
				tab.addAll(t1);
			}
			if (f2 == 2) {
				tab.addAll(t2);
			}
		}
		
		//vertical
		cx = 0; cy = 1; f1 = 0; f2 = 0;
		t1.clear();
		t2.clear();
		while (true) {
			if (f1 == 0 || f1 == 1) {
				if (x+cx < 0 || x+cx >= Server.LENGTH || y+cy < 0 || y+cy >= Server.LENGTH) {
					f1 = 3;
				} else if (this.getPlayerBoard(x+cx, y+cy) == p.getPlayer()) {
					if (f1 == 1) {
						f1 = 2;
					} else if (f1 == 0) {
						f1 = 3;
					}
				} else if (this.getPlayerBoard(x+cx, y+cy) != 0) { //peca adversario
					f1 = 1;
					t1.add(new Piece(x+cx, y+cy, p.getPlayer()));
				} else {
					f1 = 3;
				}
			}
			
			if (f2 == 0 || f2 == 1) {
				if (x-cx < 0 || x-cx >= Server.LENGTH || y-cy < 0 || y-cy >= Server.LENGTH) {
					f2 = 3;
				} else if (this.getPlayerBoard(x-cx, y-cy) == p.getPlayer()) {
					if (f2 == 1) {
						f2 = 2;
					} else if (f2 == 0) {
						f2 = 3;
					}
				} else if (this.getPlayerBoard(x-cx, y-cy) != 0) { //peca adversario
					f2 = 1;
					t2.add(new Piece(x-cx, y-cy, p.getPlayer()));
				} else {
					f2 = 3;
				}
			}
			
			if ((f1 == 2 || f1 == 3) && (f2 == 2 || f2 == 3)) {
				break;
			}
			cy++;
		}
		if (f1 == 2 || f2 == 2) {
			tab.add(p);
			if (f1 == 2) {
				tab.addAll(t1);
			}
			if (f2 == 2) {
				tab.addAll(t2);
			}
		}
		
		//diagonal crescente
		cx = 1; cy = -1; f1 = 0; f2 = 0;
		t1.clear();
		t2.clear();
		while (true) {
			if (f1 == 0 || f1 == 1) {
				if (x+cx < 0 || x+cx >= Server.LENGTH || y+cy < 0 || y+cy >= Server.LENGTH) {
					f1 = 3;
				} else if (this.getPlayerBoard(x+cx, y+cy) == p.getPlayer()) {
					if (f1 == 1) {
						f1 = 2;
					} else if (f1 == 0) {
						f1 = 3;
					}
				} else if (this.getPlayerBoard(x+cx, y+cy) != 0) { //peca adversario
					f1 = 1;
					t1.add(new Piece(x+cx, y+cy, p.getPlayer()));
				} else {
					f1 = 3;
				}
			}
			
			if (f2 == 0 || f2 == 1) {
				if (x-cx < 0 || x-cx >= Server.LENGTH || y-cy < 0 || y-cy >= Server.LENGTH) {
					f2 = 3;
				} else if (this.getPlayerBoard(x-cx, y-cy) == p.getPlayer()) {
					if (f2 == 1) {
						f2 = 2;
					} else if (f2 == 0) {
						f2 = 3;
					}
				} else if (this.getPlayerBoard(x-cx, y-cy) != 0) { //peca adversario
					f2 = 1;
					t2.add(new Piece(x-cx, y-cy, p.getPlayer()));
				} else {
					f2 = 3;
				}
			}
			
			if ((f1 == 2 || f1 == 3) && (f2 == 2 || f2 == 3)) {
				break;
			}
			cx++;
			cy--;
		}
		if (f1 == 2 || f2 == 2) {
			tab.add(p);
			if (f1 == 2) {
				tab.addAll(t1);
			}
			if (f2 == 2) {
				tab.addAll(t2);
			}
		}
		
		//diagonal decrescente
		cx = 1; cy = 1; f1 = 0; f2 = 0;
		t1.clear();
		t2.clear();
		while (true) {
			if (f1 == 0 || f1 == 1) {
				if (x+cx < 0 || x+cx >= Server.LENGTH || y+cy < 0 || y+cy >= Server.LENGTH) {
					f1 = 3;
				} else if (this.getPlayerBoard(x+cx, y+cy) == p.getPlayer()) {
					if (f1 == 1) {
						f1 = 2;
					} else if (f1 == 0) {
						f1 = 3;
					}
				} else if (this.getPlayerBoard(x+cx, y+cy) != 0) { //peca adversario
					f1 = 1;
					t1.add(new Piece(x+cx, y+cy, p.getPlayer()));
				} else {
					f1 = 3;
				}
			}
			
			if (f2 == 0 || f2 == 1) {
				if (x-cx < 0 || x-cx >= Server.LENGTH || y-cy < 0 || y-cy >= Server.LENGTH) {
					f2 = 3;
				} else if (this.getPlayerBoard(x-cx, y-cy) == p.getPlayer()) {
					if (f2 == 1) {
						f2 = 2;
					} else if (f2 == 0) {
						f2 = 3;
					}
				} else if (this.getPlayerBoard(x-cx, y-cy) != 0) { //peca adversario
					f2 = 1;
					t2.add(new Piece(x-cx, y-cy, p.getPlayer()));
				} else {
					f2 = 3;
				}
			}
			
			if ((f1 == 2 || f1 == 3) && (f2 == 2 || f2 == 3)) {
				break;
			}
			cx++;
			cy++;
		}
		if (f1 == 2 || f2 == 2) {
			tab.add(p);
			if (f1 == 2) {
				tab.addAll(t1);
			}
			if (f2 == 2) {
				tab.addAll(t2);
			}
		}
		
		return tab;
	}
	
	public ArrayList doPlay(Piece p) {
		ArrayList<Piece> tab = this.getChanges(p);
		
		//atualiza tabuleiro
		if (tab.size() > 0) {
			for (Piece pp : tab) {
				this.pieceToBoard(pp);
			}
		}
		
		return tab;
	}
	
	public int checkPlay(int player) {
		// verifica se jogo terminou, se o jogador tem jogadas para fazer
		int pwin = 0;
		ArrayList tab;
		boolean endedGame = true;
		for (int y = 0; y < Server.LENGTH; y++) {
			for (int x = 0; x < Server.LENGTH; x++) {
				if (this.getPlayerBoard(x, y) == 0) {
					tab = this.getChanges(new Piece(x, y, player));
					if (tab.size() > 0) {
						endedGame = false;
						break;
					}
				}
			}
		}
		if (endedGame) {
			int p1 = 0, p2 = 0;
			for (int y = 0; y < Server.LENGTH; y++) {
				for (int x = 0; x < Server.LENGTH; x++) {
					if (this.getPlayerBoard(x, y) == Piece.PLAYER_1) {
						p1++;
					} else if (this.getPlayerBoard(x, y) == Piece.PLAYER_1) {
						p2++;
					}
				}
			}
			pwin = -1;
			if (p1 > p2) {
				pwin = Piece.PLAYER_1 + 2;
			} else if (p2 > p1) {
				pwin = Piece.PLAYER_2 + 2;
			}
		}
		//se 0 jogo nao terminou, se -1 empate, se 3 player 1 ganhou, se 4 player 2
		return pwin;
	}
	
	public void pieceToBoard(Piece p) {
		this.board[p.getY()][p.getX()] = p.getPlayer();
	}
	
	public int getPlayerBoard(int x, int y) {
		return this.board[y][x];
	}
	
	public void printBoard() {
		for (int i = 0; i < Server.LENGTH; i++) {
			for (int j = 0; j < Server.LENGTH; j++) {
				System.out.print(this.board[i][j]+" ");
			}
			System.out.println("");
		}
	}
	
	public void start() {
		ServerSocket servidor;
		try {
			servidor = new ServerSocket(Server.PORT);

			// aguarda player 1 = preto
			Socket cliente = servidor.accept(); // bloqueado no accept
			ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
			out.writeObject(Piece.PLAYER_1); // send
			
			// aguarda player 2 = branco
			Socket cliente2 = servidor.accept(); //bloqueado no accept
			ObjectOutputStream out2 = new ObjectOutputStream(cliente2.getOutputStream());
			out2.writeObject(Piece.PLAYER_2); // send
			
			// gera tabuleiro e envia para players
			ArrayList tab = this.initPlay();
			out = new ObjectOutputStream(cliente.getOutputStream());
			out.writeObject(tab); // send
			out2 = new ObjectOutputStream(cliente2.getOutputStream());
			out2.writeObject(tab); // send

			int playerWin = 0;
			int current_player = Piece.PLAYER_1;
			while (true) {
				try {
					ObjectInputStream in;
					//envia player que vai fazer a jogada ou fim de jogo - current_player
					out = new ObjectOutputStream(cliente.getOutputStream());
					out.writeObject(current_player); // send
					out = new ObjectOutputStream(cliente2.getOutputStream());
					out.writeObject(current_player); // send
					
					if (playerWin != 0) {
						break;
					}
					
					try {
						if (current_player == Piece.PLAYER_1) {
							in = new ObjectInputStream(cliente.getInputStream());
						} else {
							in = new ObjectInputStream(cliente2.getInputStream());
						}
						// recebe a jogada do player e valida o player
						Piece p = (Piece) in.readObject();
						if (p.getPlayer() != current_player) {
							continue;
						}
						// faz jogada
						tab = this.doPlay(p);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}

					// envia alteracao do tabuleiro
					out = new ObjectOutputStream(cliente.getOutputStream());
					out.writeObject(tab);
					out = new ObjectOutputStream(cliente2.getOutputStream());
					out.writeObject(tab);

					if (tab.size() > 0) {
						// altera a vez do jogador
						if (current_player == Piece.PLAYER_1) {
							current_player = Piece.PLAYER_2;
						} else {
							current_player = Piece.PLAYER_1;
						}

						// verifica se jogo terminou e envia fim de jogo
						playerWin = this.checkPlay(current_player);
						if (playerWin != 0) {
							current_player = playerWin;
						}
					}

				} catch (IOException e) {
					e.printStackTrace();
					break;
				}
			}
			
			cliente.close();
			cliente2.close();

		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} // connect, bind, listen
	}
	
	public static void main(String[] args) {
		Server s = new Server();
		s.start();
		
//		s.initPlay();
//		s.printBoard();
//		Piece p = new Piece(2, 3, 1);
//		s.doPlay(p);
//		s.printBoard();
		
	}

}
