package se.chalmers.chessfeud.model.pieces;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.model.utils.Position;

/**
 * The piece Bishop
 * @author Arvid
 *
 */

//Även kallad löpare
public class Bishop extends Piece{

	protected Bishop(int team) {
		super(team,C.PIECE_BISHOP);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<List<Position>> theoreticalMoves(Position p) {
		List<List<Position>> posList = new ArrayList<List<Position>>();
		for(int x = -1; x < 2; x++){
			for(int y = -1; y < 2; y++){
				if((x != 0 || y != 0)){
					List<Position> moveList = moveDirection(x, y, p);
					if(moveList.size()!=0)
						posList.add(moveList);
				}
			}
		}
		return posList;
	}

	private List<Position> moveDirection(int dx, int dy, Position p) {
		List<Position> moveList = new ArrayList<Position>();
		int x = p.getX() + dx;
		int y = p.getY() + dy;
		while(0 <= x && x < 8 && 0 <= y && y < 8){
			moveList.add(new Position(x, y));
			x += dx;
			y += dy;
		}
		return moveList;
	}
}
