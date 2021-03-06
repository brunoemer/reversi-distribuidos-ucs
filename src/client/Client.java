package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import server.Piece;
import server.Server;

public class Client {
	private Socket server;
	private Tabuleiro tab;
	private int player_id;

	public Client() {
		
	}
	
	public void start(String ip) {
		try{
			this.server = new Socket(ip, Server.PORT);
			
			//recebe o id do player
			ObjectInputStream in = new ObjectInputStream(this.server.getInputStream());
			try {
				this.player_id = (int) in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			//aguarda e recebe tabuleiro inicial
			in = new ObjectInputStream(this.server.getInputStream());
			ArrayList arr = null;
			try {
				arr = (ArrayList) in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			//carrega interface
			this.tab = new Tabuleiro();
			this.tab.setClient(this);
			this.tab.doChanges(arr);
			this.tab.setVisible(true);
			
			while (true) {
				//recebe player que deve fazer a jogada, ou termino do jogo com player > 2
				in = new ObjectInputStream(this.server.getInputStream());
				try {
					int player = (int) in.readObject();
					if (player == Piece.PLAYER_1 || player == Piece.PLAYER_2) {
						//atualiza o player que vai jogar
						this.tab.setSuaVez(player == this.player_id);
					} else {
						//recebe o player que ganhou ou perdeu
						if (player == this.player_id + 2) {
							JOptionPane.showMessageDialog(this.tab, "Você ganhou", "Fim de jogo", JOptionPane.INFORMATION_MESSAGE);
							break;
						//verifica se player nao possui jogada disponivel
						} else if (player >= 5 && player <= 6) {
							if (player == this.player_id + 4) {
								JOptionPane.showMessageDialog(this.tab, "Você não possui jogada disponível, aguardo outro jogador", "", JOptionPane.INFORMATION_MESSAGE);
								this.tab.setSuaVez(false);
							} else {
								JOptionPane.showMessageDialog(this.tab, "Outro jogador não possui jogada, jogue outra vez", "", JOptionPane.INFORMATION_MESSAGE);
								this.tab.setSuaVez(true);
							}
						} else if (player == -1) {
							JOptionPane.showMessageDialog(this.tab, "Empatou", "Fim de jogo", JOptionPane.INFORMATION_MESSAGE);
							break;
						} else {
							JOptionPane.showMessageDialog(this.tab, "Você perdeu", "Fim de jogo", JOptionPane.INFORMATION_MESSAGE);
							break;
						}
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				//recebe atualizacao do tabuleiro
				in = new ObjectInputStream(this.server.getInputStream());
				try {
					arr = (ArrayList) in.readObject();
					this.tab.doChanges(arr);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

			}
			
			this.server.close();
		}catch(IOException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.tab, "Erro na conexão com servidor", "Erro", JOptionPane.ERROR_MESSAGE);
		}

		System.exit(0);
	}
	
	public void sendPlay(int x, int y) {
		//faz jogada e envia para servidor
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(this.server.getOutputStream());
			Piece p = new Piece(x, y, this.player_id);
			out.writeObject(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getPlayer()
	{
		return this.player_id;
	}
	
	public static void main(String[] args) {
		String ip = "127.0.0.1";
		if (args.length > 0) {
			ip = args[0];
		}
		Client c = new Client();
		c.start(ip);
	}

}
