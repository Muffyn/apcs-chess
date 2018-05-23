
import java.util.ArrayList;


public class King extends Piece
{
	private static int moves = 0;
	private static boolean wasMoved;
	private Rook rightRook;
	private Rook leftRook;
	
	public King(int x, int y) {
		super(x, y);
	}
	
	public King(Position pos) {
		super(pos);
	}

	public boolean getWasMoved(){
		return wasMoved;
	}
	public King reverse() {
		return new King(super.reverse());
	}
	
	public static int getMoves(){
		return moves;
	}
	
	@Override 
	public boolean move(Position position, Player otherPlayer) {
		
		if(getPossiblePositions().contains(position)){
			if(position.getX() == 6 && !wasMoved){	//castles from the right
				rightRook.move(new Position(5, this.getY()), otherPlayer);
			}
			else if (leftRook != null && position.getX() == 2 && !wasMoved){//castles from the left
				leftRook.move(new Position(3, this.getY()), otherPlayer);
			}
			wasMoved = true;
			otherPlayer.remove(position);
			set(position);
			moves++;
			return true;
		}
		return false;
	}
	
	@Override
	public ArrayList<Position> setPossiblePositions(ArrayList<Piece> allies, ArrayList<Piece> enemies) {
		ArrayList<Position> positions = getPossiblePositions();
		int rightRookInd = allies.indexOf(new Position(7,7));
		int leftRookInd = allies.indexOf(new Position(0,7));
		
		if(rightRookInd != -1 && allies.get(rightRookInd) instanceof Rook && !wasMoved){	//puts in possible position if available for castling
			rightRook = (Rook)(allies.get(rightRookInd));
			if(canMoveRight(rightRook, allies, enemies)){	//castling from right side
				positions.add(new Position(6,this.getY()));
			}
		}
		if (leftRookInd != -1 && allies.get(leftRookInd) instanceof Rook && !wasMoved){
			leftRook = (Rook)(allies.get(leftRookInd));
			if(canMoveLeft(leftRook, allies, enemies)){ //castling from left side
				positions.add(new Position(2,this.getY()));
			}
		}
		for (int x = 0; x < 8; x++) {
			for(Position position : Piece.getPositions(this, x, 1)) {
				if(allies.contains(position)){
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
		boolean x = (!rook.getWasMoved() && !wasMoved && rook.getX() == 7 && !allies.contains(new Position(6, this.getY())) && !allies.contains(new Position(5,this.getY())) && 
				!willBeInCheck(new Position(4,this.getY()), enemies) &&  !willBeInCheck(new Position(5,this.getY()), enemies) && !willBeInCheck(new Position(6,this.getY()), enemies) &&
				!enemies.contains(new Position(6, this.getY())) && !enemies.contains(new Position(5,this.getY())));
		return x;
	}
	public boolean canMoveLeft (Rook rook, ArrayList<Piece> allies, ArrayList<Piece> enemies){//even more conditions to be checked for castling in the left direciton
		return (!rook.getWasMoved() && !wasMoved && rook.getX() == 0 && !allies.contains(new Position(1, this.getY())) && !allies.contains(new Position(2,this.getY())) && 
				!allies.contains(new Position(3,this.getY())) && !willBeInCheck(new Position(4,this.getY()), enemies) &&  !willBeInCheck(new Position(3,this.getY()), enemies) && 
				!willBeInCheck(new Position(2,this.getY()), enemies) && !enemies.contains(new Position(3,this.getY())) && !enemies.contains(new Position(2,this.getY())) && !enemies.contains(new Position(1, this.getY())));
	}
}
