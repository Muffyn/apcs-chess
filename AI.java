import java.util.ArrayList;

public class AI
{
	private Player comp;
	
	public AI()
	{
		comp = Player.PLAYER2;
		
		for(Piece piece : comp.getPieces()) 
		{
			piece.setPossiblePositions(comp.getPieces(), comp.getNext().getPieces());
		}
	}
	
	public static void move()
	{
		
		
		
		
		
		
		
		
		
		
		
		
		Chess.setPlayer(comp.getNext()); //switch turn
	}
}
