
import java.util.ArrayList;

public class King extends Piece
{
	public King(int x, int y) {
		super(x, y);
	}
	
	public King(Position pos) {
		super(pos);
	}
	
	public King reverse() {
		return new King(super.reverse());
	}
	
	@Override
	public ArrayList<Position> setPossiblePositions(ArrayList<Piece> allies, ArrayList<Piece> enemies) {
		ArrayList<Position> positions = getPossiblePositions();
		for (int x = 0; x < 8; x++) {
			for(Position position : Piece.getPositions(this, x, 1)) {
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
