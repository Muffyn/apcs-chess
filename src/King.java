
import java.util.ArrayList;


public class King extends Piece
{
	private boolean wasMoved;
	private Rook rightRook;
	private Rook leftRook;
	
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
		return wasMoved;
	}
	public King reverse() {
		return new King(super.reverse());
	}
	
	@Override 
	public boolean move(Position position, ArrayList<Piece> enemies) {
		if(getPossiblePositions().contains(position)){
			if(position.getX() == 6 && !wasMoved){	//castles from the right
				rightRook.set(new Position(5, 0));
			}
			else if (position.getX() == 2 && !wasMoved){//castles from the left
				leftRook.set(new Position(3, 0));
			}
			enemies.remove(position);
			set(position);
			wasMoved = true;
			return true;
		}
		return false;
	}
	
	@Override
	public ArrayList<Position> setPossiblePositions(ArrayList<Piece> allies, ArrayList<Piece> enemies) {
		ArrayList<Position> positions = getPossiblePositions();
		for (int x = 0; x < 8; x++) {
			for(Position position : Piece.getPositions(this, x, 1)) {
				if(allies.contains(position)){
					for(int y = 0; y < allies.size(); y++){
					if(allies.get(y) instanceof Rook && position.getY() == 7 && !wasMoved){	//puts in possible position if available for castling
						Rook rook = (Rook)(allies.get(y));
						if(canMoveRight(rook, allies, enemies) && position.getX() == 7){	//castling from right side
							positions.add(new Position(6,0));
							rightRook = rook;
						}
						else if(canMoveLeft(rook, allies, enemies) && position.getX() == 0){ //castling from left side
							positions.add(new Position(0,0));
							leftRook = rook;
						}
					}
					}
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
	
	public boolean willBeInCheck(Position position, ArrayList<Piece> enemies){ //checks if the king will be or is in check, depending on input
		for(Piece enemy: enemies){
			if(enemy.getPossiblePositions().contains(position)){
				return true;
			}
		}
		return false;
	}
	
	public boolean canMoveRight (Rook rook, ArrayList<Piece> allies, ArrayList<Piece> enemies){	//a whole lot of conditions to be checked for castling in the right direciton
		return (!rook.getWasMoved() && !wasMoved && rook.getX() == 7 && !allies.contains(new Position(6, 0)) && !allies.contains(new Position(5,0)) && 
				!willBeInCheck(new Position(4,0), enemies) &&  !willBeInCheck(new Position(5,0), enemies) && !willBeInCheck(new Position(6,0), enemies) && 
				!enemies.contains(new Position(6, 0)) && !enemies.contains(new Position(5,0)));
	}
	public boolean canMoveLeft (Rook rook, ArrayList<Piece> allies, ArrayList<Piece> enemies){//even more conditions to be checked for castling in the left direciton
		return (!rook.getWasMoved() && !wasMoved && rook.getX() == 0 && !allies.contains(new Position(1, 0)) && !allies.contains(new Position(2,0)) && 
				!allies.contains(new Position(3,0)) && !willBeInCheck(new Position(4,0), enemies) &&  !willBeInCheck(new Position(3,0), enemies) && 
				!willBeInCheck(new Position(2,0), enemies) && !enemies.contains(new Position(3, 0)) && !enemies.contains(new Position(2,0)) && !enemies.contains(new Position(1, 0)));
	}
}
