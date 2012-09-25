package se.chalmers.chessfeud.model.pieces;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.chessfeud.model.utils.Position;


/**
 * The piece pawn
 * @author Arvid
 *
 */

//Även kallad Bonde
public class Pawn extends Piece{
	
	private boolean hasMoved = false;
	
	protected Pawn(int team, Position p) {
		super(team, p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<List<Position>> canMove() {
		List<List<Position>> posList = new ArrayList<List<Position>>();
		
		int y = 1;
		List<Position> moveList;
		for(int x = -1; x < 2; x++){
			if(this.getPosition().getX() == 7){
				while(y < 3){
					moveList = moveDirection(x, y);
					y++;
				}
			} else {
				moveList = moveDirection(x, y);	
			}
		posList.add(moveList);	
		}
		return posList;
		
	}

	private List<Position> moveDirection(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

}
