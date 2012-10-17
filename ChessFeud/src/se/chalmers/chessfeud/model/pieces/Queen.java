package se.chalmers.chessfeud.model.pieces;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.model.utils.Position;

/**
 * The piece Queen. Reprecents the Queen on the chessborad. Handles the logic
 * for the Queens movement and what team the queen is on.
 * 
 * @author Arvid modifiedby Henrik Alburg
 * 
 *         Copyright � 2012 Arvid Karlsson, Henrik Alburg
 * 
 */

public class Queen extends Piece {

	public Queen(int team) {
		super(team, C.PIECE_QUEEN);
	}

	/**
	 * Returns a list of all the theoretical moves the Queen can do. The rules
	 * class will check if there is any piece in the way. Gets a List of
	 * positions from the method moveDirection. That list gets stacked in a new
	 * list that is returned.
	 * 
	 * @param p
	 *            the piece current position.
	 * @return posList A list that contains Lists of possible positions in the
	 *         diffrent directions.
	 */
	@Override
	public List<List<Position>> theoreticalMoves(Position p) {
		List<List<Position>> posList = new ArrayList<List<Position>>();
		for (int x = -1; x < 2; x++) {
			for (int y = -1; y < 2; y++) {
				if (!inBounds(x, y)) {
					List<Position> moveList = moveDirection(x, y, p);
					if (moveList.size() != 0) {
						posList.add(moveList);
					}
				}
			}
		}
		return posList;
	}

	/*
	 * Takes the Queens current position and finds out which squares it can go
	 * on in all possible directions (vertical, horizontal, diagonally) 8 steps
	 * forward.
	 * 
	 * @param dx the current x-Position value
	 * 
	 * @param dy the current y-Position value
	 * 
	 * @return moveList a List for each direction with all the positions.
	 */
	private List<Position> moveDirection(int dx, int dy, Position p) {
		List<Position> moveList = new ArrayList<Position>();
		int x = p.getX() + dx;
		int y = p.getY() + dy;
		while (Position.inBounds(x, y)) {
			moveList.add(new Position(x, y));
			x += dx;
			y += dy;
		}
		return moveList;
	}

	/* Checks so the Queen is insidwe the game board */
	private boolean inBounds(int x, int y) {
		return x * y == x + y;
	}

	@Override
	public String toString() {
		return "Piece: Queen " + "Team: " + getTeam();
	}
}
