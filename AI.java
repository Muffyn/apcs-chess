import java.util.ArrayList;

public class AI
{
	private Player comp;
	
	public AI()
	{
		comp = Player.PLAYER2;
	
	}
	
	public void move()
	{
		for(Piece piece : comp.getPieces()) 
		{
			piece.setPossiblePositions(comp.getPieces(), comp.getNext().getPieces());
		}
		
		Piece selected = comp.getPieces().get((int)(Math.random() * comp.getPieces().size()));
		
		Position newLoc = selected.getPossiblePositions().get((int)(Math.random() * selected.getPossiblePositions().size()));
		
		selected.move(newLoc, comp.getNext().getPieces());
		
		int totalPossiblePositions= 0;
		for(Piece piece : comp.getPieces()) {
			piece.setPossiblePositions(comp.getPieces(), comp.getNext().getPieces());
			//piece.checkPossiblePositions(player.getPieces(), player.getNext().getPieces());
			totalPossiblePositions+= piece.getPossiblePositions().size();
		}
		
		
		
		
		
		
		
		//Chess.setPlayer(comp.getNext()); //switch turn
	}
}
