import java.util.ArrayList;

public class Pawn extends Piece{

	private boolean first = true;
	private boolean justJumped = false;
	
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
	
	public boolean getJustJumped()
	{
		return justJumped;
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
		int var = canEnPassant(allies, enemies);
		if(var != -1)
			positions.add(enemies.get(var));
		return positions;
	}
	
	public boolean move(Position position, Player otherPlayer) {
		int prev = this.getY();
		boolean end;
		first = false;
		end = super.move(position, otherPlayer);
		if(prev - this.getY() == 2 || prev - this.getY() == -2)
			justJumped = true;
		else
			justJumped = false;
		return end;
	}
	
	public int canEnPassant(ArrayList<Piece> allies, ArrayList<Piece> enemies) {
		int count = 0;
		int xCord = 0;
		int yCord = 0;
		for(int z = 0; z < enemies.size(); z++) {
			if(enemies.get(z) instanceof Pawn) {
				if(((Pawn)enemies.get(z)).getJustJumped() == true && ((xCord == enemies.get(z).getX() + 1 || xCord == enemies.get(z).getX() - 1) && (yCord == enemies.get(z).getY() + 1 || yCord == enemies.get(z).getY() - 1)))
					count = z;
				else
					count = -1;
			}
			else
				count = -1;
		}
		return count;
	}
}