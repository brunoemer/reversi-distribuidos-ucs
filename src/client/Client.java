package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import server.Piece;
import server.Server;

public class Client {
	private Socket server;
	private Tabuleiro tab;
	private int player_id;

	public Client() {
		
	}
	
	public void start() {
		try{			
			this.server = new Socket("127.0.0.1", Server.PORT);
			
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
			this.tab = new Tabuleiro(this.player_id);
			this.tab.setClient(this);
			this.tab.doChanges(arr);
			this.tab.setVisible(true);
			
			while (true) {
				//recebe player que deve fazer a jogada
				in = new ObjectInputStream(this.server.getInputStream());
				try {
					int player = (int) in.readObject();
					this.tab.setSuaVez(player == this.player_id);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				//recebe atualizacao do tabuleiro
				in = new ObjectInputStream(this.server.getInputStream());
				try {
					arr = (ArrayList) in.readObject();
					this.tab.doChanges(arr);
//					System.out.println(arr.toString());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			
//			BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
//			String str = in.readLine(); //recv
//			System.out.println("Client recv: "+str);
			
//			PrintWriter out = new PrintWriter(new BufferedWriter( new OutputStreamWriter(cliente.getOutputStream())),true);
//			out.println("client2server"); //send
			
			//exemplo send object
//			ObjectInputStream in = new ObjectInputStream(cliente.getInputStream());
//			try {
//				ArrayList arr = (ArrayList) in.readObject();
//				System.out.println(arr.toString());
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
//			server.close();
		}catch(IOException e){
			e.printStackTrace();
		}

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
	
	public static void main(String[] args) {
		Client c = new Client();
		c.start();
	}

}
