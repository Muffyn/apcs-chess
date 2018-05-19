
import java.util.ArrayList;
private boolean wasMoved;

public class King extends Piece
{
	public King(int x, int y) {
		super(x, y);
	}
	
	public King(Position pos) {
		super(pos);
	}
	public void setWasMoved(boolean moved){
		wasMoved = moved;
	}
	public boolean getWasMoved(){
		wasMoved = true;
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
	
	public boolean willBeInCheck(Position position, ArrayList<Piece> enemies){
		for(Piece enemy: enemies){
			if(enemy.getPossiblePositions().contains(position)){
				return true;
			}
		}
		return false;
	}
	public boolean inCheck(ArrayList<Pieces>){
		if(enemies.getPossiblePositions.contains(this.getPosition))
			return true;
		else
			return false;
	}
}
