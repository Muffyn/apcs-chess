package pieces;

public class Position {
	private int x, y;
	
 	public Position() {
		set(0, 0);
	}
	
	public Position(int x, int y) {
		set(x, y);
	}
	
	public Position(Position position) {
		set(position.x, position.y);
	}
	
	public void set(int x, int y) {
		this.x= x;
		this.y= y;
	}
	
	public void set(Position position) {
		set(position.x, position.y);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Position reverse() {
		return new Position(7 - x, 7 - y);
	}
	
	public boolean equals(Object object) {
		Position other= (Position)object;
		return x == other.x && y == other.y;
	}
}
