import java.util.ArrayList;

public class Bishop extends Piece{

	private Position position = new Position();
	private boolean sur, sul, sdr, sdl = false; //Stop Up Right, Stop Up Left, Stop Down Right, Stop Down Left
	
	public Bishop(int x, int y) {
		super(new Position(x,y));
		// TODO Auto-generated constructor stub
	}
	
	public Bishop(Position position) {
		super(position);
	}
	
	public Bishop reverse() {
		return new Bishop(super.reverse());
	}
	
	@Override
	public ArrayList<Position> setPossiblePositions(ArrayList<Piece> allies, ArrayList<Piece> enemies) {
		// TODO Auto-generated method stub
		ArrayList<Position> returned = super.getPossiblePositions();
		
		for (Piece ally : allies)
		{
			for (Piece enemy : enemies)
			{
				for (int x = 0; x < 8; x++) //8 is the amount of squares for each row and column, bishops only move in 1 diagonal line, so using the same value is ok
				{
					//If there are no allies on the tile, and position is in bounds, add it to the list. Stop checking if an enemy is found
					//Up Right
					if (this.getX() != this.getX()+x && this.getY() != this.getY()+x && ally.getX() != this.getX()+x && ally.getY() != this.getY()+x && super.inBounds(new Position(this.getX()+x, this.getY()+x)) && !sur)
					{
						returned.add(new Position(this.getX()+x, this.getY()+x));
						if (enemy.getX() == this.getX()+x && enemy.getY() == this.getY()+x) //The location an enemy is in has already been added, stop checking after it
							sur = true;
					}
					//Up Left
					if (this.getX() != this.getX()+x && this.getY() != this.getY()+x && ally.getX() != this.getX()-x && ally.getY() != this.getY()+x && super.inBounds(new Position(this.getX()-x, this.getY()+x)) && !sul)
					{
						returned.add(new Position(this.getX()-x, this.getY()+x));
						if (enemy.getX() == this.getX()+x && enemy.getY() == this.getY()+x)
							sul = true;
					}
					
					//Down Right
					if (this.getX() != this.getX()+x && this.getY() != this.getY()+x && ally.getX() != this.getX()+x && ally.getY() != this.getY()-x && super.inBounds(new Position(this.getX()+x, this.getY()-x)) && !sdr)
					{
						returned.add(new Position(this.getX()+x, this.getY()-x));
						if (enemy.getX() == this.getX()+x && enemy.getY() == this.getY()+x)
							sdr = true;
					}
					//Down Left
					if (this.getX() != this.getX()+x && this.getY() != this.getY()+x && ally.getX() != this.getX()-x && ally.getY() != this.getY()-x && super.inBounds(new Position(this.getX()-x, this.getY()-x)) && !sdl)
					{
						returned.add(new Position(this.getX()-x, this.getY()-x));
						if (enemy.getX() == this.getX()+x && enemy.getY() == this.getY()+x)
							sdl = true;
					}
				}
			}
		}
		/*
		for (Position x : returned)
		{
			System.out.println("(" + x.getX() +"," + x.getY() + ")");
		}
		*/
		return returned;
	}

}
