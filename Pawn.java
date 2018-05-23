import java.util.ArrayList;

public class Pawn extends Piece
{
    private boolean wasMoved;

    public Pawn(int x, int y) {
        super(x, y);
    }

    public Pawn(Position pos) {
        super(pos);
    }

    public Pawn reverse() {
        return new Pawn(super.reverse());
    }

    @Override
    public boolean move(Position position, Player otherPlayer)
    {
        if(getPossiblePositions().contains(position)) {
            otherPlayer.remove(position);
            set(position);
            wasMoved = true;
            return true;
        }
        return false;
    }
@Override
    public ArrayList<Position> setPossiblePositions(ArrayList<Piece> allies, ArrayList<Piece> enemies) {
        ArrayList<Position> positions = getPossiblePositions();
        for (int x = 0; x <= 7; x = 1 + (x*2)) {
            if(x == 1 || x == 7) {
                for(Position position : Piece.getPositions(this, x, 1)) {
                    if(enemies.contains(position))
                        positions.add(position);
                    else
                        break;
                }
            }
            else if(x == 0 && wasMoved == false) {
                for(int y = 1; y <= 2; y++) {
                    for(Position position : Piece.getPositions(this, 0, y)) {
                        if(allies.contains(position)) {
                            break;
                        }
                        else if(enemies.contains(position)) {
                            break;
                        } else {
                            positions.add(position);
                        }
                    }
                }
            }
            else
            {
            	for(Position position : Piece.getPositions(this, 0, 1)) {
            		if(allies.contains(position)) {
                		break;
                	}
                	else if(enemies.contains(position)) {
                    	break;
                	} else {
                		positions.add(position);
                	}
            	}
            }
        }
        return positions;
    }
}
