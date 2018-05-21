
import java.util.ArrayList;

public abstract class Piece extends Position{
	private ArrayList<Position> possiblePositions;
	
	public static final int UP= 		0;
	public static final int UP_RIGHT= 	1;
	public static final int RIGHT= 		2;
	public static final int DOWN_RIGHT= 3;
	public static final int DOWN= 		4;
	public static final int DOWN_LEFT= 	5;
	public static final int LEFT= 		6;
	public static final int UP_LEFT= 	7;
	
	/**
	 * 
	 * @param position
	 * @param direction
	 * @param spaces
	 * @return ArrayList of position in a given direction that are in bounds
	 */
	public static ArrayList<Position> getPositions(Position position, int direction, int spaces) {
		ArrayList<Position> positions= new ArrayList<Position>();
		position= new Position(position);
		int x= 0;
		int y= 0;
		
		switch(direction) {
		case 0:
			y= -1;
			break;
		case 1:
			y= -1;
			x= 1;
			break;
		case 2:
			x= 1;
			break;
		case 3:
			y= 1;
			x= 1;
			break;
		case 4:
			y= 1;
			break;
		case 5:
			y= 1;
			x= -1;
			break;
		case 6:
			x= -1;
			break;
		default:
			y= -1;
			x= -1;
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
	
	/**
	 * 
	 * @param position
	 * @param direction
	 * @return Single position given a direction
	 */
	public static Position getPosition(Position position, int direction) {
		ArrayList<Position> positions= getPositions(position, direction, 1);
		if(positions.size() > 0) {
			return positions.get(0);
		}
		return null;
	}
	
	/**
	 * 
	 * @param pieces
	 * @return Reverses a given ArrayList of pieces
	 */
	public static ArrayList<Piece> reverse(ArrayList<Piece> pieces) {
		ArrayList<Piece> newPieces= new ArrayList<Piece>();
		pieces.forEach(piece -> {
			newPieces.add(piece.reverse());
		});
		return newPieces;
	}
	
	/**
	 * 
	 * @param position
	 * @return True if position is in bounds, false otherwise
	 */
	public static boolean inBounds(Position position) {
		return position.getX() >= 0 && position.getY() >= 0 && position.getX() < 8 && position.getY() < 8;
	}
	
	public Piece(int x, int y) {
		super(x, y);
		possiblePositions= new ArrayList<Position>();
	}
	
	public Piece(Position position) {
		this(position.getX(), position.getY());
	}

	public Piece reverse() {
		return new Piece(super.reverse()) {
			@Override
			public ArrayList<Position> setPossiblePositions(ArrayList<Piece> allies, ArrayList<Piece> enemies) {
				return null;
			}
		};
	}
	
	/**
	 * Moves a piece
	 * @param position
	 * @param enemies
	 * @return True if move was successful, false otherwise
	 */
	public boolean move(Position position, Player player) {
		if(possiblePositions.contains(position)) {
			player.remove(position);
			set(position);
			return true;
		}
		return false;
	}
	
	/**
	 * Removes any possible positions that would result in putting piece in check
	 * @param allies
	 * @param enemies
	 */
	public void checkPossiblePositions(ArrayList<Piece> allies, ArrayList<Piece> enemies) {		
		ArrayList<Piece> reverseAllies= reverse(allies);
		ArrayList<Piece> reverseEnemies= reverse(enemies);
		
		Position checkPosition= reverseAllies.get(reverseAllies.indexOf(reverse()));
		Position king= reverseAllies.get(0);
				
		for(int i= possiblePositions.size() - 1; i >= 0; i--) {
			checkPosition.set(possiblePositions.get(i).reverse());
			
			Piece temp= null;
			if(reverseEnemies.contains(checkPosition)) {
				temp= reverseEnemies.remove(reverseEnemies.indexOf(checkPosition));
			}
			
			for(Piece piece : reverseEnemies) {
				piece.clearPossiblePositions();
				if(piece.setPossiblePositions(reverseEnemies, reverseAllies).contains(king)) {
					possiblePositions.remove(i);
					break;
				}
			}
			
			if(temp != null) {
				reverseEnemies.add(temp);
			}
		}
	}
	
	/**
	 * Clears possible positions
	 */
	public void clearPossiblePositions() {
		possiblePositions.clear();
	}
	
	public ArrayList<Position> getPossiblePositions() {
		return possiblePositions;
	}
	
	/**
	 * Sets possible positions
	 * @param allies
	 * @param enemies
	 * @return Reference to possiblePositions
	 */
	public abstract ArrayList<Position> setPossiblePositions(ArrayList<Piece> allies, ArrayList<Piece> enemies);
}
