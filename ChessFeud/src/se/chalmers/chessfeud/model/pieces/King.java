package se.chalmers.chessfeud.model.pieces;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.model.utils.Position;

/**
 * 
 * @author Arvid
 *
 */

//Även kallad Kung
public class King extends Piece{

	protected King(int team) {
		super(team, C.PIECE_KING);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<List<Position>> theoreticalMoves(Position p) {
		List<List<Position>> posList = new ArrayList<List<Position>>();
		for(int x = -1; x < 3; x++){
			for(int y = -1; y < 3; y++){
				if((x != 0 && y != 0) && (p.getX() + x < 8 && p.getY() + y < 8)){
					List<Position> tmp = new LinkedList<Position>();
					tmp.add(new Position(p.getX() + x, p.getY() + y));
					posList.add(tmp);
				}	
			}	
		}
		return posList;
	}
}
