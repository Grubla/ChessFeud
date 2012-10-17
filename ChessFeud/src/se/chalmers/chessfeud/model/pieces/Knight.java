package se.chalmers.chessfeud.model.pieces;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.model.utils.Position;

/**
 * The piece Knight. Represents the Knight on the chessborad. Handles the logic
 * for the Knights movement and what team the Knight is on.
 * 
 * @author Arvid
 * 
 *         Copyright © 2012 Arvid Karlsson
 * 
 */

public class Knight extends Piece {

	public Knight(int team) {
		super(team, C.PIECE_KNIGHT);
	}

	/**
	 * 
	 * Returns a list of all the theoretical moves the Knight can do. The rules
	 * class will check if there is a piece on the same team in one of these
	 * positions. Gets a List of positions from the method moveDirection. That
	 * list gets stacked in a new list that is returned.
	 * 
	 * @param p
	 *            the piece current position.
	 * @return posList A list that contains Lists of possible positions in the
	 *         diffrent directions.
	 */
	@Override
	public List<List<Position>> theoreticalMoves(Position p) {
		List<List<Position>> posList = new ArrayList<List<Position>>();
		int[] x = C.KNIGHT_X;
		int[] y = C.KNIGHT_Y;
		for (int i = 0; i < x.length; i++) {
			List<Position> moveList = new ArrayList<Position>();
			moveList.add(new Position(p.getX() + x[i], p.getY() + y[i]));
			if (Position.inBounds(p.getX() + x[i], p.getY() + y[i])){
				posList.add(moveList);
			}	
		}
		return posList;
	}

	@Override
	public String toString() {
		return "Piece: Knight " + "Team: " + getTeam();
	}

}
