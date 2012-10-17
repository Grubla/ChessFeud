package se.chalmers.chessfeud.model.pieces;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.model.utils.Position;

/**
 * The piece Bishop Reprecents the rook on the board. Contains the movement
 * pattern and what team the piece is on.
 * 
 * @author Arvid modifiedby Henrik Alburg
 * 
 *         Copyright © 2012 Arvid Karlsson, Henrik Alburg
 */

public class Bishop extends Piece {

	public Bishop(int team) {
		super(team, C.PIECE_BISHOP);
	}

	/**
	 * Returns a list of all the theoretical moves the Bishop can do. The moves
	 * that are out of bounds will not be included. Gets a List of positions
	 * from the method moveDirection. That list gets stacked in a new list that
	 * is returned.
	 * 
	 * @param p
	 *            the bishops current position.
	 * @return posList A list that contains Lists of possible positions in the
	 *         different directions.
	 * 
	 */
	@Override
	public List<List<Position>> theoreticalMoves(Position p) {
		List<List<Position>> posList = new ArrayList<List<Position>>();
		for (int x = -1; x < 2; x++) {
			for (int y = -1; y < 2; y++) {
				if (inBounds(x, y)) {
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
	 * Takes the Bishops current position and returns all the possible squares
	 * it can go on in the directions the Bishops goes (diagonally).
	 * 
	 * @param dx the x-Position value
	 * 
	 * @param dy the y-Position value
	 * 
	 * @return moveList a List with all the possible moves in each direction.
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

	/* Checks so the Bishop is insite the game board */
	private boolean inBounds(int x, int y) {
		return x != 0 && y != 0;
	}

	@Override
	public String toString() {
		return "Piece: Bishop " + "Team: " + getTeam();
	}
}
