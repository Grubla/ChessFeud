package se.chalmers.chessfeud.model.pieces;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.chessfeud.model.utils.Position;

/**
 * The piece Knight
 * @author Arvid
 *
 */

//Även kallad Springare eller Häst

public class Knight extends Piece{

	protected Knight(int team, Position p) {
		super(team, p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<List<Position>> canMove() {
		List<List<Position>> posList = new ArrayList<List<Position>>();
		List<Position> moveList = moveDirection(this.getPosition().getX(), this.getPosition().getY());
		posList.add(moveList);
		return posList;
	}

	private List<Position> moveDirection(int x, int y) {
		List<Position> moveList = new ArrayList<Position>();
		for(int)
		return moveList;
	}

}
