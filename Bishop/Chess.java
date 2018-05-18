import java.awt.Color;
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
import javax.swing.JPanel;

enum Player {
	PLAYER1("White"), PLAYER2("Black");
	
	private String color;
	private ArrayList<Piece> pieces;
	
	private Player(String color) {
		this.color= color;
		pieces= new ArrayList<Piece>();
		
		if (color.equals("Black")) //Adds Black Bishops
		{
			pieces.add(new Bishop(2,0)); //Top Left corner is 0,0
			pieces.add(new Bishop(5,0));
		}
		else
		{
			pieces.add(new Bishop(2, 7)); //Adds White Bishops
			pieces.add(new Bishop(5, 7));
		}
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
}

public class Chess extends JPanel {

	private final static int PIECE_SCALE= 6;
	private final static int MARKER_SCALE= 6;
	private Player player;
	private Piece selectedPiece;
	
	public static void drawBoard(Graphics g, int length, int translation) {
		for(int x= 0; x < 8; x++) {
			for(int y= 0; y < 8; y++) {
				if((x + y) % 2 == 0) {
					g.setColor(Color.DARK_GRAY.darker().darker());
				} else {
					g.setColor(Color.WHITE);
				}
				g.fill3DRect(length * x + translation, length * y, length - 1, length - 1, true);
			}
		}
	}
	
	public static void drawMarker(Graphics g, Position position, Color color, int length, int translation) {
		g.setColor(color);
		g.fill3DRect(length * position.getX() + translation, length * position.getY(), (length - 1) / MARKER_SCALE, (length - 1) / MARKER_SCALE, true);
	}
	
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
	
	public Chess() {
		player= Player.PLAYER1;
		
		for(Piece piece : player.getPieces()) {
			piece.setPossiblePositions(player.getPieces(), player.getNext().getPieces());
			piece.checkPossiblePositions(player.getPieces(), player.getNext().getPieces());
		}
		
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
				int squareLength = getHeight() / 8;
				int positionX = (int)(point.getX() - (getWidth() / 2 - squareLength * 4)) / squareLength;
				int positionY = (int)(point.getY() / squareLength);
				Position position= new Position(positionX, positionY);
				if(e.getButton() == MouseEvent.BUTTON1)
			    {
					ArrayList<Piece> pieces= player.getPieces();
					int index= pieces.indexOf(position);
					if(index >= 0)
					{
						selectedPiece= pieces.get(index);
					}
					else { 
						if(selectedPiece != null)
						{
							if(selectedPiece.move(position, player.getNext().getPieces())) {
								for(Player p : Player.values()) {
									p.setPieces(Piece.reverse(p.getPieces()));
								}
								
								player= player.getNext();
								int totalPossiblePositions= 0;
								for(Piece piece : player.getPieces()) {
									piece.setPossiblePositions(player.getPieces(), player.getNext().getPieces());
									piece.checkPossiblePositions(player.getPieces(), player.getNext().getPieces());
									totalPossiblePositions+= piece.getPossiblePositions().size();
								}
							}
							selectedPiece= null;
						}
					}
			    }
			    else if(e.getButton() == MouseEvent.BUTTON3) //If Right Click is pressed, deselect piece
			    {
			        selectedPiece= null;
			    }
			}

			@Override
			public void mouseReleased(MouseEvent e) {}
		});
	}
	
	public void paintComponent(Graphics g) {
		super.repaint();
		int length= getHeight() / 8;
		int translation= getWidth() / 2 - (length * 4);
		
		drawBoard(g, length, translation);
		
		for(Player p : Player.values()) {
			drawPieces(p.getPieces(), g, p.getColor(), length, translation);
		}
		
		if(selectedPiece != null) {
			drawMarker(g, selectedPiece, Color.BLUE, length, translation);
			selectedPiece.getPossiblePositions().forEach(position -> {
				drawMarker(g, position, Color.GREEN, length, translation);
			});
		}
		
		repaint();
	}
	
	public static void main(String[] args) {
		JFrame frame= new JFrame();
		frame.add(new Chess());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		frame.setVisible(true);
	}
}
