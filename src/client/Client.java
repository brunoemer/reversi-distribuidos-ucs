package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import server.Piece;

public class Client {
	private int player_id;

	public Client() {
		
	}
	
	public void start() {
		Socket server;
		try{			
			server = new Socket("127.0.0.1",Integer.parseInt("8004"));
			
			//recebe o id do player
			ObjectInputStream in = new ObjectInputStream(server.getInputStream());
			try {
				this.player_id = (int) in.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//aguarda e recebe tabuleiro inicial
			in = new ObjectInputStream(server.getInputStream());
			try {
				ArrayList arr = (ArrayList) in.readObject();
				arr.toString();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			while (true) {
				//faz jogada e envia para servidor
				ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
				Piece p = new Piece(4, 3, this.player_id);
				out.writeObject(p);
				
				//recebe atualizacao do tabuleiro
				in = new ObjectInputStream(server.getInputStream());
				try {
					ArrayList arr = (ArrayList) in.readObject();
					arr.toString();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
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
	
	public static void main(String[] args) {
		Client c = new Client();
		c.start();
	}

}
