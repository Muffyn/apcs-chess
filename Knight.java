import java.util.ArrayList;

public class Knight extends Piece
{
	public Knight(int x, int y)
	{
		super(x,y);
	}

	public Knight(Position pos)
	{
		super(pos);
	}
	
	public Knight reverse()
	{
		return new Knight(super.reverse());
	}
	
	public ArrayList<Position> setPossiblePositions(ArrayList<Piece> allies, ArrayList<Piece> enemies) 
	{
		ArrayList<Position> positions = getPossiblePositions();


						for (int x = 1; x <= 8; x++) 
						{
							if(x==1)
							{
								if(!allies.contains(new Position(this.getX()+1, this.getY()-2)) && super.inBounds(new Position(this.getX()+1, this.getY()-2))){
									positions.add(new Position(this.getX()+1, this.getY()-2));
								}
							}//end of up right check
							if(x == 2){
								if(!allies.contains(new Position(this.getX()+2, this.getY()-1)) && super.inBounds(new Position(this.getX()+2, this.getY()-1)))
									positions.add(new Position(this.getX()+2, this.getY()-1));
							}
							if(x==3)//down right check
							{
								if(!allies.contains(new Position(this.getX()+1, this.getY()+2)) && super.inBounds(new Position(this.getX()+1, this.getY()+2)))
									positions.add(new Position(this.getX()+1, this.getY()+2));
							}
							if (x == 4){
								if(!allies.contains(new Position(this.getX()+2, this.getY()+1)) && super.inBounds(new Position(this.getX()+2, this.getY()+1)))
									positions.add(new Position(this.getX()+2, this.getY()+1));
							}
							if(x==5)//down left
							{
								if(!allies.contains(new Position(this.getX()-1, this.getY()+2)) && super.inBounds(new Position(this.getX()-1, this.getY()+2)))
									positions.add(new Position(this.getX()-1, this.getY()+2));
							}
							if(x == 6){
								if(!allies.contains(new Position(this.getX()-2, this.getY()+1)) && super.inBounds(new Position(this.getX()-2, this.getY()+1)))
									positions.add(new Position(this.getX()-2, this.getY()+1));
							}
							if(x==7)//up left
							{
								if(!allies.contains(new Position(this.getX()-1, this.getY()-2)) && super.inBounds(new Position(this.getX()-1, this.getY()-2)))
										positions.add(new Position(this.getX()-1, this.getY()-2));
							}//end of up Left
							if(x==8){
								if(!allies.contains(new Position(this.getX()-2, this.getY()-1)) && super.inBounds(new Position(this.getX()-2, this.getY()-1)))
										positions.add(new Position(this.getX()-2, this.getY()-1));
							}
						}//end of direction for loop
	

		return positions;
	}
}
