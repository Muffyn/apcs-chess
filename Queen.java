import java.util.ArrayList;

public class Queen extends Piece {

	public Queen(int x, int y) {
		super(x, y);
	}
	
	public Queen(Position pos) {
		super(pos);
	}
	
	public Queen reverse() {
		return new Queen(this);
	}
	
	public ArrayList<Position> setPossiblePositions(ArrayList<Piece> allies, ArrayList<Piece> enemies) {
		ArrayList<Position> positions = getPossiblePositions();
		for (int x = 0; x < 8; x++) {
			for(Position position : Piece.getPositions(this, x, 7)) {
				if(allies.contains(position)) {
					break;
				}
				else if(enemies.contains(position)) {
					positions.add(position);
					break;
				} else {
					positions.add(position);
				}
			}
		}
		return positions;
	}
}
