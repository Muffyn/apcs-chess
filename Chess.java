import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

enum Player {
	PLAYER1("White"), PLAYER2("Black");
	
	private String color;
	private ArrayList<Piece> pieces;
	private ArrayList<Piece> capturedPieces;
	
	private Player(String color) {
		this.color= color;
		pieces= new ArrayList<Piece>();
		capturedPieces= new ArrayList<Piece>();
	}
	
	public Player getNext() {
		switch(this) {
		case PLAYER1:
			return PLAYER2;
		default:
			return PLAYER1;
		}
	}
	
	public String getColor() {
		return color;
	}
	
	public ArrayList<Piece> getPieces() {
		return pieces;
	}
	
	public void setPieces(ArrayList<Piece> pieces) {
		this.pieces= pieces;
	}
	
	public ArrayList<Piece> getCapturedPieces() {
		return capturedPieces;
	}
	
	public void setCapturedPieces(ArrayList<Piece> pieces) {
		capturedPieces= pieces;
	}
	
	public void remove(Position position) {
		if(pieces.contains(position)) {
			Piece piece= pieces.remove(pieces.indexOf(position));
			piece.set(-capturedPieces.size() / 4 - 1, 7 - capturedPieces.size() % 4);
			capturedPieces.add(piece);
		}
	}
}

public class Chess extends JPanel {
	
	private final static int PIECE_SCALE= 6;
	private final static int MARKER_SCALE= 6;
	private Player player;
	private Piece selectedPiece;
	private boolean inCheck;
	
	private JLabel label;
	
	private AI ai;
	
	private final boolean multiplayer;
	
	/**
	 * Draws the board
	 * @param g
	 * @param length
	 * @param translation
	 */
	public static void drawBoard(Graphics g, int length, int translation) {
		for(int x= 0; x < 8; x++) {
			for(int y= 0; y < 8; y++) {
				if((x + y) % 2 == 1) {
					g.setColor(Color.DARK_GRAY.brighter());
				} else {
					g.setColor(Color.GRAY.brighter());
				}
				g.fill3DRect(length * x + translation, length * y, length + 1, length + 1, true);
			}
		}
	}
	
	/**
	 * Draws a marker at a given position
	 * @param g
	 * @param position
	 * @param color
	 * @param length
	 * @param translation
	 */
	public static void drawMarker(Graphics g, Position position, Color color, int length, int translation) {
		g.setColor(color);
		g.fillRect(length * position.getX() + translation, length * position.getY(), (length - 1) / MARKER_SCALE + 1, (length - 1) / MARKER_SCALE + 1);
	}
	
	/**
	 * Draws all of the pieces
	 * @param pieces
	 * @param g
	 * @param color
	 * @param length
	 * @param translation
	 */
	public static void drawPieces(ArrayList<Piece> pieces, Graphics g, String color, int length, int translation) {
		pieces.forEach(piece -> {
			Image img= null;
			try {
			    img = ImageIO.read(new File(piece.getClass().getSimpleName() + "_" + color + ".png"));
			} catch (IOException e) {}
			g.drawImage(img, length * piece.getX() + translation + length / PIECE_SCALE, length * piece.getY() + length / PIECE_SCALE
						   , (length - 1) - length / (PIECE_SCALE / 2), (length - 1) - length / (PIECE_SCALE / 2), null);
		});
	}
	
	public Chess(boolean multiplayer) {
		
		this.multiplayer= multiplayer;
		player= Player.PLAYER1;
		setPieces();
		
		player.getPieces().forEach(piece -> 
			piece.setPossiblePositions(player.getPieces(), player.getNext().getPieces())
		);
		
		if(multiplayer) {
			label= new JLabel();
			label.setText(player + "\'s turn");
			add(label);
		}
		else
			ai = new AI();
		
		addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent arg0) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {
				Point point = getMousePosition();
				if(point != null) {
					int squareLength = getHeight() / 8;
					int positionX = (int)(point.getX() - (getWidth() / 2 - squareLength * 4)) / squareLength;
					int positionY = (int)(point.getY() / squareLength);
					Position position= new Position(positionX, positionY);
					if(e.getButton() == MouseEvent.BUTTON1)
				    {
						ArrayList<Piece> pieces= player.getPieces();
						int index= pieces.indexOf(position);
						if(index >= 0 && pieces.get(index).getPossiblePositions().size() > 0)
						{
							selectedPiece= pieces.get(index);
						}
						else { 
							if(selectedPiece != null)
							{
								if(selectedPiece.move(position, player.getNext())) 
								{
									//promote a pawn to a queen if it reaches the end of the board
									for(Piece piece : pieces)
									{
										int j = pieces.indexOf(piece);
										int x = piece.getX();
										int y = piece.getY();
										
										if(piece instanceof Pawn && piece.getY() == 0)
										{
											String input = JOptionPane.showInputDialog("Enter what piece you want to promote to (Q for Queen), (K for Knight), \n (B for Bishop), and (R for Rook). Click on the piece again to confirm your selection:");
											pieces.remove(j);
											switch(input){
											case "Q":
												pieces.add(j, new Queen(x, y));
												break;
											case "K":
												pieces.add(j, new Knight(x, y));
												break;
											case "B":
												pieces.add(j, new Bishop(x, y));
												break;
											case "R":
												pieces.add(j, new Rook(x, y));
												break;
											default:
													pieces.add(j, new Queen(x, y));
											}
										}
									}
									nextTurn();
									
									//Ends the game if no moves are possible
									int totalPossiblePositions= 0;
									for(Piece piece : player.getPieces()) {
										totalPossiblePositions+= piece.getPossiblePositions().size();
									}
									if(totalPossiblePositions == 0) {
										endGame();
									//makes a move using AI
									} else if(!multiplayer) {
										ai.move();
										nextTurn();
									}
								}
								selectedPiece= null;
							}
						}
				    }
				    else if(e.getButton() == MouseEvent.BUTTON3)
				    {
				        selectedPiece= null;
				    }
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {}
		});
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int length= getHeight() / 8;
		int translation= getWidth() / 2 - (length * 4);
		
		if(multiplayer) {
			label.setBounds(5, 0, translation, translation / 5);
			label.setFont(new Font(null, 0, translation / 8));
		}
		
		drawBoard(g, length, translation);
		
		for(Player p : Player.values()) {
			drawPieces(p.getPieces(), g, p.getColor(), length, translation);
			drawPieces(p.getCapturedPieces(), g, p.getColor(), length, translation);
		}
		
		if(inCheck) {
			drawMarker(g, player.getPieces().get(0), Color.MAGENTA, length, translation);
		}
		
		if(selectedPiece != null) {
			drawMarker(g, selectedPiece, Color.BLUE, length, translation);
			selectedPiece.getPossiblePositions().forEach(position -> {
				if(player.getNext().getPieces().contains(position)) {
					drawMarker(g, position, Color.RED, length, translation);
				} else {
					drawMarker(g, position, Color.GREEN, length, translation);
				}
			});
		}
		repaint();
	}
	
	public void endGame() {
		JLabel newLabel= new JLabel();
		
		newLabel.setOpaque(true);
		add(newLabel);
		
		if(multiplayer) {
			label.setText("");
		}
		
		if(inCheck) {
			newLabel.setText(player.getNext() + " wins");
		} else {
			newLabel.setText("It's a tie");
		}
		repaint();
		
		Container panel= this;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Player.PLAYER1.getPieces().clear();
				Player.PLAYER1.getCapturedPieces().clear();
				Player.PLAYER2.getPieces().clear();
				Player.PLAYER2.getCapturedPieces().clear();
				Container frame= getParent();
				frame.remove(panel);
				//frame.add(new Menu());
				frame.repaint();
			}
		});
	}
	
	public void nextTurn() {
		
		//Checks to see if the next player is in check
		inCheck= false;
		for(Piece piece : player.getPieces()) {
			if(piece.setPossiblePositions(player.getPieces(), player.getNext().getPieces())
					.contains(player.getNext().getPieces().get(0))) {
				inCheck= true;
				break;
			}
		}
		
		//Reverses all of the pieces
		for(Player p : Player.values()) {
			p.setPieces(Piece.reverse(p.getPieces()));
			p.setCapturedPieces(Piece.reverse(p.getCapturedPieces()));
		}
		
		//Sets up for the next player's turn
		player= player.getNext();
		for(Piece piece : player.getPieces()) {
			piece.setPossiblePositions(player.getPieces(), player.getNext().getPieces());
			piece.checkPossiblePositions(player.getPieces(), player.getNext().getPieces());
		}
		
		if(multiplayer) {
			label.setText(player + "\'s turn");
		}
	}
	
	/**
	 * Adds all of the pieces to their original positions
	 */
	public void setPieces() {
		ArrayList<Piece> whitePieces= Player.PLAYER1.getPieces();
		ArrayList<Piece> blackPieces= Player.PLAYER2.getPieces();
		
		//Note: Kings must be added first
		
		//the top left of the board is (0, 0)
		
		whitePieces.add(new King(4, 7));
		blackPieces.add(new King(4, 0));
		
		whitePieces.add(new Queen(3, 7));
		blackPieces.add(new Queen(3, 0));
		
		whitePieces.add(new Knight(6, 7));
		whitePieces.add(new Knight(1, 7));
		blackPieces.add(new Knight(1, 0));
		blackPieces.add(new Knight(6, 0));
		
		whitePieces.add(new Rook(0, 7));
		whitePieces.add(new Rook(7, 7));
		blackPieces.add(new Rook(0, 0));
		blackPieces.add(new Rook(7, 0));
		
		whitePieces.add(new Bishop(2, 7));
		whitePieces.add(new Bishop(5, 7));
		blackPieces.add(new Bishop(2, 0));
		blackPieces.add(new Bishop(5, 0));
		
		whitePieces.add(new Pawn(0, 6));
		whitePieces.add(new Pawn(1, 6));
		whitePieces.add(new Pawn(2, 6));
		whitePieces.add(new Pawn(3, 6));
		whitePieces.add(new Pawn(4, 6));
		whitePieces.add(new Pawn(5, 6));
		whitePieces.add(new Pawn(6, 6));
		whitePieces.add(new Pawn(7, 6));
		
		blackPieces.add(new Pawn(0, 1));
		blackPieces.add(new Pawn(1, 1));
		blackPieces.add(new Pawn(2, 1));
		blackPieces.add(new Pawn(3, 1));
		blackPieces.add(new Pawn(4, 1));
		blackPieces.add(new Pawn(5, 1));
		blackPieces.add(new Pawn(6, 1));
		blackPieces.add(new Pawn(7, 1));
		

	}
	
	public static void main(String[] args) {
		JFrame frame= new JFrame();
		frame.add(new Chess(true));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		frame.setVisible(true);
	}
}
