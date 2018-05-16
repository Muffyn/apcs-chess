package pieces;

import java.util.ArrayList;

public class Piece extends Position{
	private ArrayList<Position> possiblePositions;
	
	public static final int UP= 		0;
	public static final int UP_RIGHT= 	1;
	public static final int RIGHT= 		2;
	public static final int DOWN_RIGHT= 3;
	public static final int DOWN= 		4;
	public static final int DOWN_LEFT= 	5;
	public static final int LEFT= 		6;
	public static final int UP_LEFT= 	7;
	
	public static ArrayList<Position> getPositions(Position position, int direction, int spaces) {
		ArrayList<Position> positions= new ArrayList<Position>();
		position= new Position(position);
		int x= 0;
		int y= 0;
		
		if(direction % 4 == 0) {
			y= direction < 4 ? -1 : 1;
		} else if(direction % 2 == 0) {
			x= direction < 4 ? 1 : -1;
		} else {
			x= direction < 4 ? 1 : -1;
			y= direction % 3 == 1 ? -1 : 1;
		}
				
		for(int i= 0; i < spaces; i++) {
			position.set(position.getX() + x, position.getY() + y);
			if(!inBounds(position)) {
				break;
			}
			positions.add(new Position(position));
		}
		return positions;
	}
	
	public static Position getPosition(Position position, int direction) {
		ArrayList<Position> positions= getPositions(position, direction, 1);
		return positions.size() > 0 ? positions.get(0) : null;
	}
	
	public static ArrayList<Piece> reverse(ArrayList<Piece> pieces) {
		ArrayList<Piece> newPieces= new ArrayList<Piece>();
		pieces.forEach(piece -> {
			newPieces.add(piece.reverse());
		});
		return newPieces;
	}
	
	public static boolean inBounds(Position position) {
		return position.getX() >= 0 && position.getY() >= 0 && position.getX() < 8 && position.getY() < 8;
	}
	
	public Piece() {
		this(0, 0);
	}
	
	public Piece(int x, int y) {
		super(x, y);
		setPossiblePositions(new ArrayList<Position>());
	}
	
	public Piece(Position position) {
		this(position.getX(), position.getY());
	}
	
	public boolean move(Position position, ArrayList<Piece> enemies) {
		if(possiblePositions.contains(position)) {
			enemies.remove(position);
			set(position);
			return true;
		}
		return false;
	}
	
	public Piece reverse() {
		return new Piece(super.reverse());
	}
	
	public void checkPositions(ArrayList<Piece> allies, ArrayList<Piece> enemies) {		
		allies= reverse(allies);
		enemies= reverse(enemies);
		
		Position thisPosition= allies.get(allies.indexOf(reverse()));		
		final Position KING= allies.get(0);
		
		for(int i= possiblePositions.size() - 1; i >= 0; i--) {
			thisPosition.set(possiblePositions.get(i).reverse());
			for(Piece piece : enemies) {
				if(!piece.equals(thisPosition)) {
					if(piece.setPossiblePositions(enemies, allies).contains(KING)) {
						possiblePositions.remove(i);
						break;
					}
				}
			}
		}
	}
	
	public ArrayList<Position> getPossiblePositions() {
		return possiblePositions;
	}
	
	public void setPossiblePositions(ArrayList<Position> positions) {
		possiblePositions= positions;
	}
	
	public ArrayList<Position> setPossiblePositions(ArrayList<Piece> allies, ArrayList<Piece> enemies) {
		possiblePositions= new ArrayList<Position>();
		return possiblePositions;
	}
}