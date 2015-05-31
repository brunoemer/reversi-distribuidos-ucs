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
	private int player_id;

	public Client() {
		
	}
	
	public void start() {
		Socket server;
		try{			
			server = new Socket("127.0.0.1", Server.PORT);
			
			//recebe o id do player
			ObjectInputStream in = new ObjectInputStream(server.getInputStream());
			try {
				this.player_id = (int) in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			//aguarda e recebe tabuleiro inicial
			in = new ObjectInputStream(server.getInputStream());
			ArrayList arr = null;
			try {
				arr = (ArrayList) in.readObject();
//				System.out.println(arr.toString());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			//carrega interface
			Tabuleiro tab = new Tabuleiro();
			tab.doChanges(arr);
			tab.setVisible(true);
			
			while (true) {
				//aguarda sua vez
				in = new ObjectInputStream(server.getInputStream());
				try {
					int pl = (int) in.readObject();
					System.out.println("Sua vez");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				//// teste
				 Scanner ins = new Scanner(System.in);
				 System.out.print("x=");
				 int x = ins.nextInt();
				 System.out.print("y=");
				 int y = ins.nextInt();
				//// teste
				//faz jogada e envia para servidor
				ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
				Piece p = new Piece(x, y, this.player_id);
				out.writeObject(p);
				
				//recebe atualizacao do tabuleiro
				in = new ObjectInputStream(server.getInputStream());
				try {
					arr = (ArrayList) in.readObject();
					tab.doChanges(arr);
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
	
	public static void main(String[] args) {
		Client c = new Client();
		c.start();
	}

}
