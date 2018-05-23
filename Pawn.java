import java.util.ArrayList;

public class Pawn extends Piece{

	private boolean first = true;
	
	public Pawn(int x, int y) {
		super(x, y);
	}
	
	public Pawn(Position pos) {
		super(pos);
	}
	
	public Pawn reverse() {
		Pawn pawn= new Pawn(this);
		pawn.first= first;
		return pawn;
	}
	
	public ArrayList<Position> setPossiblePositions(ArrayList<Piece> allies, ArrayList<Piece> enemies) {
		ArrayList<Position> positions = getPossiblePositions();
		if (first) {
			for (Position position : Piece.getPositions(this, 0, 2)) {
				if (allies.contains(position))
					break;
				else if (enemies.contains(position)) {
					positions.add(position);
				}
				else
					positions.add(position);
			}
		}
		else {
			if (!allies.contains(Piece.getPosition(this, 0)) && !enemies.contains(Piece.getPosition(this, 0))) {
				positions.add(Piece.getPosition(this, 0));
			}
			else if (enemies.contains(Piece.getPosition(this, 1)))
					positions.add(Piece.getPosition(this, 1));
			else if (enemies.contains(Piece.getPosition(this, 7)))
					positions.add(Piece.getPosition(this,  7));
		}	
		return positions;
	}
	
	public boolean move(Position position, ArrayList<Piece> enemies) {
		first = false;
		return super.move(position, enemies);
	}
}
