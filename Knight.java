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
				for(Piece en:enemies)
				{
					for(Piece al:allies)
					{
						for (int x = 1; x < 8; x+=2) 
						{
							if(x==1)
							{
								if(al.getX()==this.getX()+1 && al.getY()==this.getY()-2 || al.getX()==this.getX()+2 && al.getY()==this.getY()-1) 
								{
									break;
								}
								else if(en.getX()==this.getX()+1 && en.getY()==this.getY()-2) //up 2 right 1
								{
									positions.add(new Position(this.getX()+1,this.getY()-2));
									break;
								} 
								else if(en.getX()==this.getX()+2 && en.getY()==this.getY()-1)//up 1 right 2
								{
									positions.add(new Position(this.getX()+2,this.getY()-1));
									break;
								}
								else
								{
									if(super.inBounds(new Position(this.getX()+1,this.getY()-2)))
										positions.add(new Position(this.getX()+1,this.getY()-2));
									
									if(super.inBounds(new Position(this.getX()+2,this.getY()-1)))
										positions.add(new Position(this.getX()+2,this.getY()-1));
								}
							}//end of up right check
							
							if(x==3)//down right check
							{
								if(al.getX()==this.getX()+1 && al.getY()==this.getY()+2 || al.getX()==this.getX()+2 && al.getY()==this.getY()+1) 
								{
									break;
								}
								else if(en.getX()==this.getX()+1 && en.getY()==this.getY()+2) //down 2 right 1
								{
									positions.add(new Position(this.getX()+1,this.getY()+2));
									break;
								} 
								else if(en.getX()==this.getX()+2 && en.getY()==this.getY()+1) //down 1 right 2
								{
									positions.add(new Position(this.getX()+2,this.getY()+1));
									break;
								}
								else
								{
									if(super.inBounds(new Position(this.getX()+2,this.getY()+1)))
										positions.add(new Position(this.getX()+2,this.getY()+1));
									
									if(super.inBounds(new Position(this.getX()+1,this.getY()+2)))
										positions.add(new Position(this.getX()+1,this.getY()+2));
								}
							}
							
							if(x==5)//down left
							{
								if(al.getX()==this.getX()-1 && al.getY()==this.getY()+2 || al.getX()==this.getX()-2 && al.getY()==this.getY()+1) 
								{
									break;
								}
								else if(en.getX()==this.getX()-1 && en.getY()==this.getY()+2) //down 2 left 1
								{
									positions.add(new Position(this.getX()-1,this.getY()+2));
									break;
								} 
								else if(en.getX()==this.getX()-2 && en.getY()==this.getY()+1) //down 1 left 2
								{
									positions.add(new Position(this.getX()-2,this.getY()+1));
									break;
								}
								else
								{
									if(super.inBounds(new Position(this.getX()-1,this.getY()+2)))
										positions.add(new Position(this.getX()-1,this.getY()+2));
									
									if(super.inBounds(new Position(this.getX()-2,this.getY()+1)))
										positions.add(new Position(this.getX()-2,this.getY()+1));
								}
							}
							
							if(x==7)//up left
							{
								if(al.getX()==this.getX()-1 && al.getY()==this.getY()-2 || al.getX()==this.getX()-2 && al.getY()==this.getY()-1) 
								{
									break;
								}
								else if(en.getX()==this.getX()-1 && en.getY()==this.getY()-2) //up 2 left 1
								{
									positions.add(new Position(this.getX()-1,this.getY()-2));
									break;
								} 
								else if(en.getX()==this.getX()-2 && en.getY()==this.getY()-1)//up 1 left 2
								{
									positions.add(new Position(this.getX()-2,this.getY()-1));
									break;
								}
								else
								{
									if(super.inBounds(new Position(this.getX()-1,this.getY()-2)))
										positions.add(new Position(this.getX()-1,this.getY()-2));
									
									if(super.inBounds(new Position(this.getX()-2,this.getY()-1)))
										positions.add(new Position(this.getX()-2,this.getY()-1));
								}
								
							}//end of up Left
							
						}//end of direction for loop
					}//end of allies loop
				}//end of enemies loop

		return positions;
	}
}
