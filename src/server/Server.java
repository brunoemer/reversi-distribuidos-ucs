package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	public Server() {
		
	}
	
	public ArrayList initPlay() {
		ArrayList tab = new ArrayList();
		tab.add(new Piece(4, 4, Piece.PLAYER_2));
		tab.add(new Piece(5, 4, Piece.PLAYER_1));
		tab.add(new Piece(4, 5, Piece.PLAYER_1));
		tab.add(new Piece(5, 5, Piece.PLAYER_2));
		return tab;
	}

	public ArrayList<Piece> doPlay() {
		ArrayList<Piece> tab = new ArrayList<Piece>();
		
		return tab;
	}
	
	public void start() {
		ServerSocket servidor;
		try {
			servidor = new ServerSocket(Integer.parseInt("8004"));

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
			
			while (true) {
				try {
					//envia sua vez
					
					// recebe as jogas e valida
					ObjectInputStream in = new ObjectInputStream(cliente.getInputStream());
					try {
						Piece p = (Piece) in.readObject();
						System.out.println(p);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// envia alteracao do tabuleiro

					
					// verifica se jogo terminou e envia fim de jogo

					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			// exemplo send object
//			ArrayList arr = new ArrayList<Piece>();
//			arr.add(new Piece(1, 2, 1));
//			arr.add(new Piece(1, 3, 2));
//			System.out.println(arr.toString());
//			ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
//			out.writeObject(arr); // send

			// PrintWriter out = new PrintWriter(new BufferedWriter( new OutputStreamWriter(cliente.getOutputStream())),true);
			// out.println("server2client"); //send

			// BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
			// String str = in.readLine(); //recv
			// System.out.println("Server recv: "+str);

		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // connect, bind, listen
	}
	
	public static void main(String[] args) {
		Server s = new Server();
		s.start();
	}

}
