package se.chalmers.chessfeud.model.pieces;

import java.util.List;

import se.chalmers.chessfeud.model.utils.Position;

public abstract class Piece {
	private int team;
	private int id;
	
	protected Piece(int team, int id){
		this.team = team;
		this.id = id;
		
	}

	public abstract List<List<Position>> theoreticalMoves(Position p);
	
	public int getTeam(){
		return this.team;
	}
	
	public int getId(){
		return this.id;
	}
}
