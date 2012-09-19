package se.chalmers.chessfeud.model.pieces;

import java.util.List;

import se.chalmers.chessfeud.model.utils.Position;

public abstract class Piece {
	private int team;
	private Position pos;
	
	protected Piece(int team, Position p){
		this.team = team;
		this.pos = p;
	}

	public abstract List<List<Position>> canMove();
	
	public int getTeam(){
		return this.team;
	}
	
	public Position getPosition(){
		return new Position(pos);
	}
	
	protected void setNewPos(Position p){
		this.pos = p;
	}
}
