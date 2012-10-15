package se.chalmers.chessfeud.model.pieces;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.model.utils.Position;

/**
 * The Piece King. Reprecents the King on the chessborad. Handles the logic for
 * the Kings movement and what team the King is on.
 * 
 * @author Arvid modifiedby Henrik Alburg
 * 
 *         Copyright © 2012 Arvid Karlsson, Henrik Alburg
 */

public class King extends Piece {

	public King(int team) {
		super(team, C.PIECE_KING);
	}

	/**
	 * Returns a list with a lsit of all the theoretical moves the King can do.
	 * The moves that are out of bounds will not be included. The list shall
	 * contain every position one square away from the king.
	 * 
	 * @param p
	 *            the kings current position.
	 * @return posList A list that contains Lists of possible positions in the
	 *         different directions.
	 */
	@Override
	public List<List<Position>> theoreticalMoves(Position p) {
		List<List<Position>> posList = new ArrayList<List<Position>>();
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				if (!(x * y == x + y) && p.getX() + x <= C.BOARD_LENGTH
						&& p.getY() + y <= C.BOARD_LENGTH && 0 <= p.getX() + x
						&& 0 <= p.getY() + y) {
					List<Position> tmp = new LinkedList<Position>();
					tmp.add(new Position(p.getX() + x, p.getY() + y));
					posList.add(tmp);
				}
			}
		}
		return posList;
	}

	@Override
	public String toString() {
		return "Piece: King " + "Team: " + getTeam();
	}
}
