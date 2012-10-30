package se.chalmers.chessfeud.model.pieces;

import java.util.List;

import se.chalmers.chessfeud.model.utils.Position;

/**
 * An abstract class that describes the pieces. Each piece shall extend this
 * abstract class.
 * 
 * @author Arvid Karlsson, Henrik Alburg Copyright (c) Arvid Karlsson, Henrik
 *         Alburg
 */
public abstract class Piece {
	private int team;
	private int id;

	protected Piece(int team, int id) {
		this.team = team;
		this.id = id;

	}

	public abstract List<List<Position>> theoreticalMoves(Position p);

	/**
	 * Makes it able to get the Pieces team.
	 * 
	 * @return the current team as an int. 0 for White, 1 for Black.
	 */
	public int getTeam() {
		return this.team;
	}

	/**
	 * Makes it able to get the Pieces id.
	 * 
	 * @return the current team from the Piece
	 */
	public int getId() {
		return this.id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + team;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Piece other = (Piece) obj;
		if (id != other.id)
			return false;
		if (team != other.team)
			return false;
		return true;
	}
	
	
}
