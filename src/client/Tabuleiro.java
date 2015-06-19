package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

import server.Piece;
import server.Server;

public class Tabuleiro extends Frame {
    
	private Client client;
	public BotaoTab btAnt;
	public Panel painel;
	private boolean suaVez;
	
    public int linhas = Server.LENGTH;
    public int colunas = Server.LENGTH;
    
	public BufferedImage imgBrancas;
	public BufferedImage imgPretas;
	public BufferedImage imgFundoBranca;
	public BufferedImage imgFundoPreta;
	
    public Tabuleiro() {
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        this.painel = new Panel(new GridLayout(linhas, colunas));
//        this.painel.setSize(600, 600);
//        this.painel.setPreferredSize(new Dimension(500, 500));
        
        initTab();
        
        this.setLayout(new BorderLayout());
        this.add(BorderLayout.CENTER, this.painel);
        
        WindowListener listener = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Object origem = e.getSource();
                if (origem == Tabuleiro.this) {
                    System.exit(0);
                }
            }
        };
        this.addWindowListener(listener);
    }

	public void initTab(){
        try {
            imgBrancas = ImageIO.read(getClass().getClassLoader().getResource("img/branca.jpg"));
    	} catch (IOException e) {
        }
        try {
            imgPretas = ImageIO.read(getClass().getClassLoader().getResource("img/preta.jpg"));
    	} catch (IOException e) {
        }
        try {
            imgFundoBranca = ImageIO.read(getClass().getClassLoader().getResource("img/fundo_branco.jpg"));
    	} catch (IOException e) {
        }
        try {
            imgFundoPreta = ImageIO.read(getClass().getClassLoader().getResource("img/fundo_escuro.jpg"));
    	} catch (IOException e) {
        }
        
		JButton bt = null;
		boolean tipo = true;
		
		for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
            	if (tipo) {
            		bt = new BotaoTab(new ImageIcon(imgFundoPreta), j, i);
            	} else {
                	bt = new BotaoTab(new ImageIcon(imgFundoBranca), j, i);
            	}
            	this.painel.add(bt);
            	tipo = !tipo;
            }
            tipo = !tipo;
        }
	}
	
	public void doChanges(ArrayList<Piece> arr) {
		if (arr.size() <= 0) {
			if (suaVez) {
				JOptionPane.showMessageDialog(this, "Campo invalido", "Erro", JOptionPane.ERROR_MESSAGE);
			}
    		return;
		}
		Component[] c = this.painel.getComponents();
		for (Piece p : arr) {
			int pos = (p.getY() * colunas) + p.getX();
			
			BotaoTab b = (BotaoTab) c[pos];
			if (p.getPlayer() == Piece.PLAYER_1) {
				b.setIcon(new ImageIcon(imgPretas));
			} else {
				b.setIcon(new ImageIcon(imgBrancas));
			}
		}
	}
	
	public void setClient(Client c) {
		this.client = c;
	}
	
	public void setSuaVez(boolean s) {
    	this.setTitle("Jogo Reversi - Player "+client.getPlayer()+(s?" - Sua Vez":""));
		this.suaVez = s;
	}
	
	public class BotaoTab extends JButton implements MouseListener {  
	    
	    private int x;
	    private int y;
	    
	    //usa o construtor da classe super (JButton), e adiciona o mouselistener ao objeto  
	    BotaoTab(ImageIcon img, int x, int y) {  
	        this.setIcon(img);
	        this.x = x;
	        this.y = y;
	        
	        this.setBackground(Color.WHITE);
	        this.setBorder(new LineBorder(Color.WHITE, 0));
	        
	        this.setFocusPainted(false);
	        
	        addMouseListener(this);  
	    }
	    
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (suaVez) {
				client.sendPlay(x, y);
			} else {
	    		JOptionPane.showMessageDialog(this, "Aguarde sua vez", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
		@Override
		public void mouseEntered(MouseEvent arg0) {
			this.setBorder(new LineBorder(Color.BLACK, 6));
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
	        this.setBorder(new LineBorder(Color.WHITE, 0));
		}

		@Override
		public void mousePressed(MouseEvent arg0) {

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {

		}  
	}
	
}