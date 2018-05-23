import java.util.ArrayList;

public class Bishop extends Piece{

	public Bishop(int x, int y) {
		super(x, y);
	}
	
	public Bishop(Position position) {
		super(position);
	}
	
	public Bishop reverse() {
		return new Bishop(super.reverse());
	}
	
	public ArrayList<Position> setPossiblePositions(ArrayList<Piece> allies, ArrayList<Piece> enemies) {
		ArrayList<Position> positions = getPossiblePositions();
		for (int x = 1; x < 8; x+=2) {
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
