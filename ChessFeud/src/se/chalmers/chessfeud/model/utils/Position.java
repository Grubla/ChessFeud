package se.chalmers.chessfeud.model.utils;

public class Position {
	private int x, y;
	
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Position(Position p){
		this.x = p.x;
		this.y = p.y;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public String toString(){
		return "x: " + this.x + " y: " + this.y;
	}
	
	public boolean inBounds(){
		return(0 <= this.getX() && this.getX() <= 7 && 0 <= this.getY() && this.getY() <= 7);
	}
	
	public static boolean inBounds(int x, int y) {
		return(0 <= x && x <= 7 && 0 <= y && y <= 7);
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Position){
			Position p = (Position) o;
			if(this.x == p.getX() && this.y == p.getY())
				return true;
		}
		return false;
	}
}
