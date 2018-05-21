import java.util.ArrayList;

public class Rook extends Piece 
{
	public boolean wasMoved;
	
	public Rook(int x, int y)
	{
		super(x,y);
	}

	public Rook(Position pos)
	{
		super(pos);
	}
	
	public Rook reverse()
	{
		return new Rook(super.reverse());
	}

	public boolean getWasMoved(){
		return wasMoved;
	}
	
	public void setWasMoved(boolean moved){
		wasMoved = moved;
	}
	
	@Override
	public ArrayList<Position> setPossiblePositions(ArrayList<Piece> allies, ArrayList<Piece> enemies) 
	{
		ArrayList<Position> positions = getPossiblePositions();
		
		for(int x = 0; x < 8; x += 2)
		{
			for(Position pos : getPositions(this, x, 7))
			{
				if(allies.contains(pos))
					break;
				else if(enemies.contains(pos))
				{
					positions.add(pos);
					break;
				}
				else
					positions.add(pos);
			}// end for each
		}// end for
		
		return positions;
	}	
}
