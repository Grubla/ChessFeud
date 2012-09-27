package se.chalmers.chessfeud.model.pieces;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.model.utils.Position;

/**
 * The piece Knight.
 * Reprecents the Knight on the chessborad. Handles the logic for the
 * Knights movement.
 * @author Arvid
 *
 */

//�ven kallad Springare eller H�st

public class Knight extends Piece{

	public Knight(int team) {
		super(team, C.PIECE_KNIGHT);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * Returns a list of all the theoretical moves the Knight can do.
	 * Even the moves that are out of bounds. That will be checked in
	 * the rules class.
	 * Gets a List of positions from the method moveDirection. That list gets
	 * stacked in a new list that is returned.
	 * @param p the piece current position.
	 * @return posList A list that contains Lists of possible positions in the diffrent directions.
	 */
	@Override
	public List<List<Position>> theoreticalMoves(Position p) {
		List<List<Position>> posList = new ArrayList<List<Position>>();
		int[] x = {-2, -1, 1, 2, 2, 1, -1, -2};
		int[] y = {1, 2, 2, 1, -1, -2, -2, -1};
		for(int i=0; i<x.length; i++){
			List<Position> moveList = new ArrayList<Position>();
			moveList.add(new Position(p.getX() + x[i], p.getY() + y[i]));
			if(0 <= p.getX() + x[i] && p.getX() + x[i] <=7 && 0 <= p.getY() + y[i] && p.getY() + y[i] <= 7)
				posList.add(moveList);
		}
		return posList;
	}
	
	@Override
	public String toString(){
		return "Piece: Knight " + "Team: " + getTeam();
	}

}
