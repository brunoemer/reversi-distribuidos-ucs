package server;

import java.io.Serializable;

public class Piece implements Serializable {
	public static final int PLAYER_1 = 1; //PRETO
	public static final int PLAYER_2 = 2; //BRANCO

	private int x;
	private int y;
	private int player;
	
	public Piece(int x, int y, int player) {
		this.x = x;
		this.y = y;
		this.player = player;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public String toString() {
//		System.out.println("x="+this.x+" y="+this.y+" player="+this.player);
		return "x="+this.x+" y="+this.y+" player="+this.player;
	}
	
}
