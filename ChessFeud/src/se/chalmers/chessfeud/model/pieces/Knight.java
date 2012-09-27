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

//Även kallad Springare eller Häst

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
		List<Position> moveList = moveDirection(p.getX(), p.getY());
		posList.add(moveList);
		return posList;
	}

	/*
	 * Returns a List with all the theoretical moves the Knight
	 * can do, even those that are out of bounds. The list will be checked in the
	 * rules class so no strange things happens.
	 *
	 * @param px the piece current x-position value.
	 * @param py the piece current y-position value
	 * @return a List of possible moves.
	 */
	private List<Position> moveDirection(int px, int py) {
		List<Position> moveList = new ArrayList<Position>();
		int[] x = {-2, -1, 1, 2, 2, 1, -1, -2};
		int[] y = {1, 2, 2, 1, -1, -2, -2, -1};
				
		for(int i=0; i<x.length; i++){
			if(0 <= px + x[i] && px + x[i] <=7 && 0 <= py + y[i] && py + y[i] <= 7)
				moveList.add(new Position(px + x[i], py + y[i]));
		}	
		return moveList;
		
	}
	
	@Override
	public String toString(){
		return "Piece: Knight " + "Team: " + getTeam();
	}

}
