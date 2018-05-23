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
		Pawn pawn = new Pawn(super.reverse());
		pawn.first = this.first;
		return pawn;
	}
	
	public ArrayList<Position> setPossiblePositions(ArrayList<Piece> allies, ArrayList<Piece> enemies) {
		ArrayList<Position> positions = getPossiblePositions();
		
		if (!allies.contains(Piece.getPosition(this, 0)) && !enemies.contains(Piece.getPosition(this, 0))) 
			positions.add(Piece.getPosition(this, 0));
		if (enemies.contains(Piece.getPosition(this, 1)))
				positions.add(Piece.getPosition(this, 1));
		if (enemies.contains(Piece.getPosition(this, 7)))
				positions.add(Piece.getPosition(this,  7));
			
		if (first) {
			for (Position position : Piece.getPositions(this, 0, 2)) {
				if (allies.contains(position) || enemies.contains(position))
					break;
				else 
					positions.add(position);
			}
		}
		return positions;
	}
	
	public boolean move(Position position, Player otherPlayer) {
		first = false;
		return super.move(position, otherPlayer);
	}
}