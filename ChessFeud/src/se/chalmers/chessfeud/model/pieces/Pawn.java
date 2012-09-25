package se.chalmers.chessfeud.model.pieces;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.model.utils.Position;


/**
 * The piece pawn
 * @author Arvid
 *
 */

//Även kallad Bonde
public class Pawn extends Piece{
	
	private boolean hasMoved = false;
	private int team;
	
	
	public Pawn(int team) {
		super(team, C.PIECE_PAWN);
		this.team = team;
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<List<Position>> theoreticalMoves(Position p) {
		List<List<Position>> posList = new ArrayList<List<Position>>();
		List<Position> moveList = new ArrayList<Position>();
		moveList = moveDirection(p.getX(), p.getY(), this.team);
		posList.add(moveList);
		return posList;
	}


	private List<Position> moveDirection(int px, int py, int team) {
		List<Position> moveList = new ArrayList<Position>();
		int[] x = {-1, 0, 1, 0};
		int[] whiteY = {1, 1, 1, 2};
		int[] blackY = {-1, -1, -1, -2};
		List<Integer> xIntList = new ArrayList<Integer>();
		List<Integer> yIntList = new ArrayList<Integer>();
		for(int i = 0; i < x.length; i++){
			xIntList.add(x[i]);
			if(team == 0){
				yIntList.add(whiteY[i]);
			} else {
				yIntList.add(blackY[i]);
			}
		}
		if(hasMoved == false){
			hasMoved = true;
		} else{
			xIntList.remove(xIntList.size() - 1);
			yIntList.remove(yIntList.size() - 1);
		}
		
		for(int i=0; i<x.length; i++){
			moveList.add(new Position(px + xIntList.get(i), py + yIntList.get(i)));
		}	
		return moveList;
		
	}
}

