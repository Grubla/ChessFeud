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
}
