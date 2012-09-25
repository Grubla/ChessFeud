package se.chalmers.chessfeud.model.pieces;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.chessfeud.model.utils.Position;

/**
 * The piece Bishop
 * @author Arvid
 *
 */

//Även kallad löpare
public class Bishop extends Piece{

	protected Bishop(int team, Position p) {
		super(team, p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<List<Position>> canMove() {
		List<List<Position>> posList = new ArrayList<List<Position>>();
		for(int x = -1; x < 2; x++){
			for(int y = -1; y < 2; y++){
				if((x != 0 || y != 0)){
					List<Position> moveList = moveDirection(x, y);
					if(moveList.size()!=0)
						posList.add(moveList);
				}
			}
		}
		return posList;
	}

	private List<Position> moveDirection(int dx, int dy) {
		List<Position> moveList = new ArrayList<Position>();
		int x = this.getPosition().getX() + dx;
		int y = this.getPosition().getY() + dy;
		while(0 <= x && x < 8 && 0 <= y && y < 8){
			moveList.add(new Position(x, y));
			x += dx;
			y += dy;
		}
		return moveList;
	}
}
